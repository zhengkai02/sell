package com.iwhalecloud.retail.offer.es;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.offer.dto.client.req.OfferSearchReq;
import com.iwhalecloud.retail.offer.es.entity.GoodsDoc;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xh on 2019/5/16.
 */
@Service
@Slf4j
public class GoodsDocService {

    private static final String GOODS_INDEX = "goods";

    private static final String GOODS_INDEX_TYPE = "_doc";

    @Autowired
    private RestHighLevelClient client;

    private static final String[] Q_FIELDS = {
        "name", "simple_name", "search_key", "intro"
    };

    // 降低复杂度

    public List<GoodsDoc> release(SearchResponse response) {
        List<GoodsDoc> list = new ArrayList<>();
        if (response != null) {
            SearchHits hits = response.getHits();
            SearchHit[] docs = hits.getHits();
            if (docs != null && docs.length > 0) {
                for (SearchHit doc : docs) {
                    list.add(JSON.parseObject(JSON.toJSONString(doc.getSourceAsMap()), GoodsDoc.class));
                }
            }
        }

        return list;
    }

    public void offerRights(OfferSearchReq req, BoolQueryBuilder bool) {
        if (StringUtils.isNotEmpty(req.getCouponId()) || StringUtils.isNotEmpty(req.getCouponSpecId())) {
            if (CollectionUtils.isNotEmpty(req.getOfferIdList())) {
                bool.must(QueryBuilders.termsQuery("goods_id", req.getOfferIdList()));
            }
            if (CollectionUtils.isNotEmpty(req.getOfferTypeList())) {
                bool.filter(QueryBuilders.termsQuery("type_id", req.getOfferTypeList()));
            }
        }
    }

    public void offerSort(OfferSearchReq req, SearchSourceBuilder search) {
        if (StringUtils.isNotEmpty(req.getSort())) {
            SortOrder sortOrder = SortOrder.ASC;
            String sortField = req.getSort();
            if (req.getSort().startsWith("-")) {
                sortOrder = SortOrder.DESC;
                sortField = req.getSort().substring(1);
            }
            else if (req.getSort().startsWith("+")) {
                sortField = req.getSort().substring(1);
            }
            search.sort(sortField, sortOrder);
        }
    }

    public List<GoodsDoc> searchGoods(OfferSearchReq req) {
        log.info("GoodsDocService.searchGoods start, req = [{}]", req);

        SearchRequest searchRequest = new SearchRequest(GOODS_INDEX);
        searchRequest.types(GOODS_INDEX_TYPE);
        SearchSourceBuilder search = new SearchSourceBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        // 根据req构造查询条件 不同条件之间是 and 关系

        // 推荐
        if (StringUtils.isNotEmpty(req.getRecommend())) {
            bool.filter(QueryBuilders.termQuery("is_recommend", req.getRecommend().toLowerCase()));
        }

        // tenant_id
        if (HttpSessionUtil.get() != null) {
            bool.filter(QueryBuilders.termQuery("tenant_id", HttpSessionUtil.get().getTenantId()));
        }

        // offerType = 6|7|8
        if (StringUtils.isNotEmpty(req.getOfferType())) {
            bool.filter(QueryBuilders.termQuery("type_id", req.getOfferType()));
        }

        // brand
        if (StringUtils.isNotEmpty(req.getBrandId())) {
            bool.filter(QueryBuilders.termQuery("brand_id", req.getBrandId()));
        }

        // catId = xxx
        if (StringUtils.isNotEmpty(req.getCatId())) {
            bool.filter(QueryBuilders.termsQuery("cat_ids", req.getCatId()));
        }

        // contactChannelId = xxx
        if (StringUtils.isNotEmpty(req.getContactChannelId())) {
            bool.filter(QueryBuilders.termsQuery("sale_contact_channel_ids", req.getContactChannelId()));
        }

        // rootCatId = xxx
        if (StringUtils.isNotEmpty(req.getRootCatId())) {
            bool.filter(QueryBuilders.termsQuery("parent_cat_ids", req.getRootCatId()));
        }

        // isHot y/n
        if ("Y".equalsIgnoreCase(req.getIsHot())) {
            bool.filter(QueryBuilders.termQuery("is_hot", "y"));
        }

        // q multi fields match 查询使用ik_smart
        if (StringUtils.isNotEmpty(req.getQ())) {
            bool.must(QueryBuilders.multiMatchQuery(req.getQ(), Q_FIELDS).analyzer("ik_smart").operator(Operator.AND));
        }

        // filter
        if (StringUtils.isNotEmpty(req.getFilter())) {
            List<String> filters = Arrays.asList(StringUtils.split(req.getFilter(), ";"));
            BoolQueryBuilder boolFilter = QueryBuilders.boolQuery();
            filters.forEach(filter -> boolFilter.must(QueryBuilders.wildcardQuery("attr_values", "*" + filter + "*")));
            bool.filter(boolFilter);
        }

        // 价格区间
        if (StringUtils.isNotEmpty(req.getReservePrice())) {
            String[] reservePrice = StringUtils.split(req.getReservePrice(), ",");
            bool.filter(QueryBuilders.rangeQuery("price").gte(reservePrice[0]).lte(reservePrice[1]));
        }

        // 隐藏条件
        // 权益类商品 couponId couponSpecId
        offerRights(req, bool);

        // marketing_begin_time 小于等于当前时间
        bool.filter(QueryBuilders.rangeQuery("marketing_begin_time").lte(DBDateUtil.getCurrentDBDateTime()));

        // marketing_end_time 为空或者大于当前时间
        bool.filter(QueryBuilders.boolQuery()
            .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("marketing_end_time")))
            .should(QueryBuilders.rangeQuery("marketing_end_time").gt(DBDateUtil.getCurrentDBDateTime())));

        // 如果没有指定查询某个商品,即查询条件 catId为空且offerCode为空且offerId为空 那么默认查询已发布的商品 即state = C
        // if (StringUtils.isEmpty(req.getCatId()) && StringUtils.isEmpty(req.getRootCatId())) {
        bool.filter(QueryBuilders.termQuery("state", "c"));
        // }

        // 排序 暂时只支持一个
        offerSort(req, search);

        // rows and offset
        if (req.getOffset() != null) {
            search.from(req.getOffset().intValue());
        }
        if (req.getRows() != null) {
            search.size(req.getRows().intValue());
        }

        search.query(bool);

        searchRequest.source(search);
        SearchResponse response = null;
        try {
            response = client.search(searchRequest);
        }
        catch (IOException e) {
            log.error("GoodsDocService.searchGoods error. {}", e);
        }
        List<GoodsDoc> result = release(response);

        log.info("GoodsDocService.searchGoods end, result = [{}] ", result);
        return result;
    }
}
