package com.iwhalecloud.retail.offer.manager;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.SpuStateDef;
import com.iwhalecloud.retail.offer.dto.req.AddSpuGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.AddSpuRelGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.DeleteSpuGoods;
import com.iwhalecloud.retail.offer.dto.req.PageSpuGoods;
import com.iwhalecloud.retail.offer.dto.req.PageSpuRelGoodsReq;
import com.iwhalecloud.retail.offer.dto.resp.PageSkuGoodsAttrResp;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuRelGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.SkuAttrValueResp;
import com.iwhalecloud.retail.offer.dto.resp.SpuGoodsResp;
import com.iwhalecloud.retail.offer.entity.Attr;
import com.iwhalecloud.retail.offer.entity.Spu;
import com.iwhalecloud.retail.offer.entity.SpuGoods;
import com.iwhalecloud.retail.offer.mapper.AttrMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsAttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsSalesConditionMapper;
import com.iwhalecloud.retail.offer.mapper.SpuGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.SpuMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * SPU关联商品
 * 
 * @author fanxiaofei
 * @date 2019/05/27
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SpuGoodsManager {

    private static final int MIN_SIZE = 2;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuGoodsMapper spuGoodsMapper;

    @Autowired
    private ProdGoodsAttrValueMapper prodGoodsAttrValueMapper;

    @Autowired
    private AttrMapper attrMapper;

    @Autowired
    private ProdGoodsSalesConditionMapper prodGoodsSalesConditionMapper;

    /**
     * SPU关联商品分页
     * 
     * @param req PageSpuRelGoodsReq
     * @return Page<PageSpuRelGoodsResp>
     */
    public Page<PageSpuRelGoodsResp> pageSpuRelGoods(PageSpuRelGoodsReq req) throws BaseException {
        log.info("SpuGoodsManager pageSpuRelGoods start, PageSpuRelGoodsReq = [{}]", req);

        String endInfo = "SpuGoodsManager pageSpuRelGoods end";
        AssertUtil.isNotNull(req.getSpuId(), OfferBaseMessageCodeEnum.SPU_ID_IS_NULL);
        Page<PageSpuRelGoodsResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<PageSpuRelGoodsResp> result = spuGoodsMapper.pageSpuRelGoods(page, req);
        List<PageSpuRelGoodsResp> pageSpuRelGoodsResps = result.getRecords();
        if (CollectionUtils.isEmpty(pageSpuRelGoodsResps)) {
            log.info(endInfo);
            return result;
        }
        // 构建 商品sku属性拼接和商品sku属性ID
        for (PageSpuRelGoodsResp pageSpuRelGoodsResp : pageSpuRelGoodsResps) {
            String goodsId = pageSpuRelGoodsResp.getGoodsId();
            List<SkuAttrValueResp> list = prodGoodsAttrValueMapper.listSkuAttrValueByProdGoodsId(goodsId);
            log.info(endInfo + " = [{}]", list);
            if (CollectionUtils.isNotEmpty(list)) {
                List<String> attrSkuIds = new ArrayList<>(list.size());
                StringBuilder attrSpuName = new StringBuilder();
                StringBuilder skuAttrIds = new StringBuilder();
                JSONObject ruleJsonObject = new JSONObject();
                for (SkuAttrValueResp skuAttrValueResp : list) {
                    attrSpuName.append(skuAttrValueResp.getAttrValue());
                    attrSpuName.append(",");
                    skuAttrIds.append(skuAttrValueResp.getAttrId());
                    skuAttrIds.append(",");
                    ruleJsonObject.put(skuAttrValueResp.getAttrId(), skuAttrValueResp.getAttrValue());
                    attrSkuIds.add(skuAttrValueResp.getAttrId());
                }
                attrSpuName = new StringBuilder(attrSpuName.substring(0, attrSpuName.length() - 1));
                pageSpuRelGoodsResp.setAttrSkuName(attrSpuName.toString());
                skuAttrIds = new StringBuilder(skuAttrIds.substring(0, skuAttrIds.length() - 1));
                pageSpuRelGoodsResp.setSkuAttrIds(skuAttrIds.toString());
                pageSpuRelGoodsResp.setRule(ruleJsonObject.toString());
                pageSpuRelGoodsResp.setAttrSkuIds(attrSkuIds);
            }
            // 销售渠道名称
            List<String> salesNames = prodGoodsSalesConditionMapper.listProdGoodsSalesConditionOneByGoodsId(goodsId);
            log.info("SpuGoodsManager listProdGoodsSalesConditionOneByGoodsId salesNames = [{}]", salesNames);
            if (CollectionUtils.isNotEmpty(salesNames)) {
                StringBuilder salesName = new StringBuilder();
                for (String name : salesNames) {
                    salesName.append(name).append(" ");
                }
                pageSpuRelGoodsResp.setProdGoodsSalesConditionName(salesName.toString().trim());
            }
        }

        log.info(endInfo);
        return result;
    }

    /**
     * 新增
     * 
     * @param req AddSpuGoodsReq
     */
    public void addSpuGoods(AddSpuGoodsReq req) throws BaseException {
        log.info("SpuGoodsManager addSpuGoods start, AddSpuGoodsReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        List<AddSpuRelGoodsReq> addSpuRelGoodsReqs = req.getAddSpuRelGoodsReqs();
        AssertUtil.isNotNull(addSpuRelGoodsReqs, OfferBaseMessageCodeEnum.SPU_GOODS_IS_NULL);
        String createBy = req.getCreateBy();
        AssertUtil.isNotNull(createBy, OfferBaseMessageCodeEnum.CREATED_STAFF_ID_IS_NULL);
        String spuId = req.getSpuId();
        AssertUtil.isNotNull(spuId, OfferBaseMessageCodeEnum.SPU_ID_IS_NULL);
        Spu spu = spuMapper.selectById(spuId);
        AssertUtil.isNotNull(spu, OfferBaseMessageCodeEnum.SPU_ID_IS_ERROR);
        // 销售渠道名称
        List<List<String>> saleNames = new ArrayList<>(addSpuRelGoodsReqs.size());
        for (AddSpuRelGoodsReq  addSpuRelGoods : addSpuRelGoodsReqs) {
            if (StringUtils.isNotEmpty(addSpuRelGoods.getGoodsId())) {
                List<String> salesNames = prodGoodsSalesConditionMapper.listProdGoodsSalesConditionOneByGoodsId(addSpuRelGoods.getGoodsId());
                if (CollectionUtils.isNotEmpty(salesNames)) {
                    saleNames.add(salesNames);
                }
            }
        }
        AssertUtil.isNotNull(saleNames, OfferBaseMessageCodeEnum.GOODS_SALES_IS_NOT_MATCH);

        // sku属性列表 存在即修改,不存在即新增
        String skuAttrIds = spu.getSkuAttrIds();
        if (StringUtils.isEmpty(skuAttrIds)) {
            checkSalesConditionDifferent(saleNames);
            // 如果只有一个商品直接新增,反之需要比较每个商品sku属性是否相同,sku属性顺序可以不同
            if (addSpuRelGoodsReqs.size() == 1) {
                checkAndAddSpuGoods(addSpuRelGoodsReqs.get(0), spuId, createBy);
            }
            else {
                checkSkuAttrIsMatch(addSpuRelGoodsReqs);
                for (AddSpuRelGoodsReq addSpuRelGoodsReq : addSpuRelGoodsReqs) {
                    checkAndAddSpuGoods(addSpuRelGoodsReq, spuId, createBy);
                }
            }
            // 填充SPU表 sku属性列表
            spu.setSkuAttrIds(addSpuRelGoodsReqs.get(0).getSkuAttrIds());
            spuMapper.updateById(spu);
        }
        else {
            List<SpuGoodsResp> spuGoodsList = spuGoodsMapper.listSpuGoodsBySpuId(spuId);
            AssertUtil.isNotNull(spuGoodsList, OfferBaseMessageCodeEnum.SPU_ID_IS_ERROR);
            log.info("SpuGoodsManager listSpuGoodsBySpuId spuGoodsList = [{}]", spuGoodsList);
            String goodsId = spuGoodsList.get(0).getGoodsId();
            List<String> oldSaleName = prodGoodsSalesConditionMapper.listProdGoodsSalesConditionOneByGoodsId(goodsId);
            checkSalesConditionDifferentByUpdate(oldSaleName, saleNames);
            String[] str = skuAttrIds.split(",");
            List<String> list = Arrays.asList(str);
            for (AddSpuRelGoodsReq addSpuRelGoodsReq : addSpuRelGoodsReqs) {
                checkDifferent(list, addSpuRelGoodsReq.getAttrSkuIds());
                checkAndAddSpuGoods(addSpuRelGoodsReq, spuId, createBy);
            }
        }

        log.info("SpuGoodsManager addSpuGoods end");
    }

    /**
     * 新增关联表
     * 
     * @param addSpuRelGoodsReq AddSpuRelGoodsReq
     * @param spuId String
     * @param createBy Long
     */
    private void checkAndAddSpuGoods(AddSpuRelGoodsReq addSpuRelGoodsReq, String spuId, String createBy)
        throws BaseException {
        log.info(
            "SpuGoodsManager checkAndAddSpuGoods start, addSpuRelGoodsReq = [{}], spuId = [{}], createdStaffId = [{}]",
            addSpuRelGoodsReq, spuId, createBy);

        AssertUtil.isNotNull(addSpuRelGoodsReq.getRule(), OfferBaseMessageCodeEnum.RULE_IS_NULL);
        AssertUtil.isNotNull(addSpuRelGoodsReq.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        AssertUtil.isNotNull(addSpuRelGoodsReq.getAttrSkuIds(), OfferBaseMessageCodeEnum.ATTR_SKU_IDS_IS_NULL);
        AssertUtil.isNotNull(addSpuRelGoodsReq.getSkuAttrIds(), OfferBaseMessageCodeEnum.SKU_ATTR_IDS_IS_NULL);
        // 前端渲染要求，目前sku属性不要超过2个
        if (addSpuRelGoodsReq.getAttrSkuIds().size() > MIN_SIZE) {
            throw new BaseException(OfferBaseMessageCodeEnum.SKU_NUM_OUT_MAX);
        }
        SpuGoods spuGoods = new SpuGoods();
        spuGoods.setSkuId(UidGeneator.getUIDStr());
        spuGoods.setSpuId(spuId);
        spuGoods.setRule(addSpuRelGoodsReq.getRule());
        spuGoods.setGoodsId(addSpuRelGoodsReq.getGoodsId());
        spuGoods.setState(SpuStateDef.STATE_A);
        Date now = DBDateUtil.getCurrentDBDateTime();
        spuGoods.setStateDate(now);
        spuGoods.setCreateTime(now);
        spuGoods.setCreateBy(createBy);
        spuGoods.setModifyBy(createBy);
        spuGoods.setModifyTime(now);
        spuGoodsMapper.insert(spuGoods);

        log.info("SpuGoodsManager checkAndAddSpuGoods end");
    }

    /**
     * 校验关联商品的sku属性是否相同
     * 
     * @param addSpuRelGoodsReqs List<AddSpuRelGoodsReq>
     */
    private void checkSkuAttrIsMatch(List<AddSpuRelGoodsReq> addSpuRelGoodsReqs) throws BaseException {
        for (int i = 0; i < addSpuRelGoodsReqs.size(); i++) {
            List<String> list1 = addSpuRelGoodsReqs.get(i).getAttrSkuIds();
            for (int j = 1; j < addSpuRelGoodsReqs.size(); j++) {
                checkDifferent(list1, addSpuRelGoodsReqs.get(j).getAttrSkuIds());
            }
        }
    }

    /**
     * 新增校验销售规则是否相等
     */
    private void checkSalesConditionDifferent(List<List<String>> saleNames) throws BaseException {
        log.info("SpuGoodsManager checkSalesConditionDifferent start, saleNames = [{}]", saleNames);

        List<String> oldList = new ArrayList<>(saleNames.get(0));
        List<String> list1 = saleNames.get(0);
        saleNames.forEach(list1::retainAll);
        log.info("SpuGoodsManager checkSalesConditionDifferent list1 = [{}]", list1);
        if (oldList.size() != list1.size()) {
            throw new BaseException(OfferBaseMessageCodeEnum.GOODS_SALES_IS_NOT_MATCH);
        }
        list1.sort(Comparator.comparing(String::hashCode));
        oldList.sort(Comparator.comparing(String::hashCode));
        boolean flag = list1.toString().equals(oldList.toString());
        if (!flag) {
            throw new BaseException(OfferBaseMessageCodeEnum.GOODS_SALES_IS_NOT_MATCH);
        }

        log.info("SpuGoodsManager checkSalesConditionDifferent end");
    }


    /**
     * 修改校验销售规则是否相等
     */
    private void checkSalesConditionDifferentByUpdate(List<String> oldSaleName, List<List<String>> saleNames) throws BaseException {
        log.info("SpuGoodsManager checkSalesConditionDifferentByUpdate start, oldSaleName = [{}], saleNames = [{}]", oldSaleName, saleNames);

        List<String> list1 = saleNames.get(0);
        saleNames.forEach(list1::retainAll);
        log.info("SpuGoodsManager checkSalesConditionDifferentByUpdate list1 = [{}]", list1);
        if (oldSaleName.size() != list1.size()) {
            throw new BaseException(OfferBaseMessageCodeEnum.GOODS_SALES_IS_NOT_MATCH);
        }
        list1.sort(Comparator.comparing(String::hashCode));
        oldSaleName.sort(Comparator.comparing(String::hashCode));
        boolean flag = list1.toString().equals(oldSaleName.toString());
        if (!flag) {
            throw new BaseException(OfferBaseMessageCodeEnum.GOODS_SALES_IS_NOT_MATCH);
        }

        log.info("SpuGoodsManager checkSalesConditionDifferentByUpdate end");
    }


    /**
     * list转成字符串进行比较是否相等
     * 
     * @param list1 List<String>
     * @param list2 List<String>
     */
    private void checkDifferent(List<String> list1, List<String> list2) throws BaseException {
        log.info("SpuGoodsManager checkDifferent start, list1 = [{}], list1 = [{}]", list1, list2);

        AssertUtil.isNotNull(list1, OfferBaseMessageCodeEnum.ATTR_SKU_IDS_IS_NULL);
        AssertUtil.isNotNull(list2, OfferBaseMessageCodeEnum.ATTR_SKU_IDS_IS_NULL);
        list1.sort(Comparator.comparing(String::hashCode));
        list2.sort(Comparator.comparing(String::hashCode));
        boolean flag = list1.toString().equals(list2.toString());
        if (!flag) {
            throw new BaseException(OfferBaseMessageCodeEnum.SPU_GOODS_IS_NOT_MATCH);
        }

        log.info("SpuGoodsManager checkDifferent end");
    }

    /**
     * SPU关联商品分页
     * 
     * @param req PageSpuGoods
     * @return Page<PageSpuGoodsResp>
     */
    public Page<PageSpuGoodsResp> pageSpuGoods(PageSpuGoods req) throws BaseException {
        log.info("SpuGoodsManager pageSpuGoods start, PageSpuGoods = [{}]", req);

        AssertUtil.isNotNull(req.getSpuId(), OfferBaseMessageCodeEnum.SPU_ID_IS_NULL);
        Page<PageSpuGoodsResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<PageSpuGoodsResp> result = spuGoodsMapper.pageSpuGoods(page, req);
        if (CollectionUtils.isEmpty(result.getRecords())) {
            log.info("SpuGoodsManager pageSpuRelGoods end");
            return result;
        }
        result.getRecords().forEach(pageSpuGoodsResp -> {
            String rule = pageSpuGoodsResp.getRule();
            JSONObject ruleObject = JSONObject.parseObject(rule);
            List<PageSkuGoodsAttrResp> list = new ArrayList<>();
            for (Map.Entry<String, Object> entry : ruleObject.entrySet()) {
                PageSkuGoodsAttrResp pageSkuGoodsAttrResp = new PageSkuGoodsAttrResp();
                pageSkuGoodsAttrResp.setAttrId(entry.getKey());
                pageSkuGoodsAttrResp.setAttrValue(entry.getValue().toString());
                Attr att = attrMapper.selectById(entry.getKey());
                pageSkuGoodsAttrResp.setAttrName(att.getAttrName());
                list.add(pageSkuGoodsAttrResp);
            }
            pageSpuGoodsResp.setAttrList(list);
        });

        log.info("SpuGoodsManager pageSpuGoods end");
        return result;
    }

    /**
     * SPU关联商品删除
     * 
     * @param req DeleteSpuGoods
     */
    public void deleteSpuGoods(DeleteSpuGoods req) throws BaseException {
        log.info("SpuGoodsManager deleteSpuGoods start, DeleteSpuGoods = [{}]", req);

        AssertUtil.isNotNull(req.getSkuId(), OfferBaseMessageCodeEnum.SKU_ID_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);

        SpuGoods spuGoods = spuGoodsMapper.selectById(req.getSkuId());
        String spuId = spuGoods.getSpuId();
        int spuGoodsNum = spuGoodsMapper.countSpuGoodsBySpuId(spuId);
        // 如果此时spu关联的商品sku只有一条,需要把spu的SkuAttrIds属性设置null
        final int spuNum = 2;
        if (spuGoodsNum < spuNum) {
            String modifyBy = getUerId();
            spuMapper.updateSkuAttrIdsBySpuId(spuId, modifyBy);
        }
        Date now = DBDateUtil.getCurrentDBDateTime();
        spuGoods.setState(SpuStateDef.STATE_X);
        spuGoods.setModifyTime(now);
        spuGoods.setModifyBy(req.getModifyBy());
        spuGoodsMapper.updateById(spuGoods);

        log.info("SpuGoodsManager deleteSpuGoods end");
    }

    private String getUerId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

}
