package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.consts.ChannelTypeDef;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.common.utils.StringUtil;
import com.iwhalecloud.retail.offer.consts.AbstractOscarMallAuditResultDef;
import com.iwhalecloud.retail.offer.consts.ContentTypeDef;
import com.iwhalecloud.retail.offer.consts.CouponSpecConst;
import com.iwhalecloud.retail.offer.consts.CouponSpecRuleRelaDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.OfferStateDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsStateDef;
import com.iwhalecloud.retail.offer.dto.client.req.OfferDetailMobileQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.OfferMobileQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.OfferQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.OfferSearchReq;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBasePersonalDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseResponseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentMaterialDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferMobileQryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferQryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.TagDTO;
import com.iwhalecloud.retail.offer.dto.req.*;
import com.iwhalecloud.retail.offer.dto.resp.*;
import com.iwhalecloud.retail.offer.entity.*;
import com.iwhalecloud.retail.offer.es.GoodsDocService;
import com.iwhalecloud.retail.offer.es.entity.GoodsDoc;
import com.iwhalecloud.retail.offer.mapper.AttrMapper;
import com.iwhalecloud.retail.offer.mapper.AttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.CarBrandMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsAttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsContentMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsGroupRelMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsTypeMapper;
import com.iwhalecloud.retail.offer.mapper.ProdTagsRelMapper;
import com.iwhalecloud.retail.offer.mapper.SpuGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.SpuMapper;
import com.iwhalecloud.retail.offer.mapper.StoreMapper;
import com.iwhalecloud.retail.offer.mapper.TblStoreCatgMapper;
import com.iwhalecloud.retail.offer.service.ContentService;
import com.iwhalecloud.retail.offer.service.CouponService;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @version 1.0
 * @ClassName OfferManager
 * @Author wangzhongbao
 * @Date 2019/3/20 12:17
 **/
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OfferManager {

    private static final long TYPE_SET = 5L;

    @Resource
    private ProdGoodsMapper prodGoodsMapper;

    @Resource
    private ProdTagsRelMapper prodTagsRelMapper;

    @Resource
    private ProdGoodsTypeMapper prodGoodsTypeMapper;

    @Resource
    private ProdGoodsGroupRelMapper prodGoodsGroupRelMapper;

    @Resource
    private ProdGoodsAttrValueMapper prodGoodsAttrValueMapper;

    @Resource
    private ProdGoodsManager prodGoodsManager;

    @Resource
    private CarBrandMapper carBrandMapper;

    @Resource
    private AttrValueMapper attrValueMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private ICache redisCache;

    @Autowired
    private ProdGoodsContentMapper prodGoodsContentMapper;

    @Autowired
    private ContentService contentService;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private GoodsDocService goodsDocService;

    @Autowired
    private TblStoreCatgMapper tblStoreCatgMapper;

    @Autowired
    private SpuGoodsMapper spuGoodsMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private AttrMapper attrMapper;

    @Autowired
    private ContactChannelManager contactChannelManager;

    @Autowired
    private ICache cache;

    public ResultVO<ArrayList<OfferQryResp>> qryOfferList(OfferQryReq req) {
        log.info("OfferManager qryOfferList start, request = [{}]", req);

        ResultVO<ArrayList<OfferQryResp>> resultVO = new ResultVO<>();

        if (req == null || null == req.getRequestId()) {
            resultVO.setCode(OfferBaseMessageCodeEnum.REQUEST_ID_IS_EXPIRED.getStatus());
            resultVO.setMessage(OfferBaseMessageCodeEnum.REQUEST_ID_IS_EXPIRED.getMessage());
            return resultVO;
        }
        try {
            List<OfferQryResp> offerQryRespList = prodGoodsMapper.qryOfferListNoTenantId(req);
            log.info("OfferManager qryOfferListNoTenantId offerQryRespList = [{}]", offerQryRespList);
            if (CollectionUtils.isNotEmpty(offerQryRespList)) {
                offerQryRespList.forEach(offer -> {
                    //单个补充商品库存
                    offer.setStockQty(cache.incrBy(CacheKeyDef.GOODS_QTY, offer.getOfferCode(), 0));
                });
            }
            resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            resultVO.setData((ArrayList<OfferQryResp>) offerQryRespList);
        }
        catch (Exception e) {
            log.error("OfferManager qryOfferList failed, Exception = [{}]", e);
            resultVO.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }

        log.info("OfferManager qryOfferList end, response = [{}]", resultVO);
        return resultVO;

    }

    public ArrayList<OfferMobileQryResp> qryOfferListForMobile(OfferMobileQryReq req) throws BaseException {
        log.info("OfferManager qryOfferListForMobile start, request = [{}]", req);

        // 权益类商品条件
        List<String> couponGoodsIds = new ArrayList<>();
        List<String> offerTypes = new ArrayList<>();
        // List<Long> salesConditionIds = new ArrayList<>();
        // 店铺下目录的所有商品id
        List<String> catGoods = new ArrayList<>();
        if (StringUtils.isNotEmpty(req.getStoreCatId())) {
            catGoods = tblStoreCatgMapper.queryGoodsIdByStoreCatgId(req.getStoreCatId());
        }

        processCouponReq(couponGoodsIds, offerTypes, req.getOfferId(), req.getCouponId(), req.getCouponSpecId());

        // 不含有分页条件
        // List<OfferMobileQryResp> respList = prodGoodsMapper.qryMobileOfferList(req);
        //
        // List<ProdGoodsSalesCondition> prodGoodsSalesConditionList = prodGoodsSalesConditionMapper.selectList(null);
        // log.info("OfferManager qryOfferListForMobile prodGoodsSalesConditionMapper.selectList,
        // prodGoodsSalesConditionList = [{}]", prodGoodsSalesConditionList);
        // filterProdGoodsByContactChannel(respList, prodGoodsSalesConditionList, req.getContactChannelId(),
        // salesConditionIds);

        // 带有分页条件
        List<OfferMobileQryResp> respPageList = prodGoodsMapper.qryMobileOfferPageList(req, couponGoodsIds, offerTypes,
            catGoods);

        formartPicUrl(respPageList);
        log.info("OfferManager qryOfferListForMobile end, response = [{}]", respPageList);

        if (CollectionUtils.isNotEmpty(respPageList)) {
            getTagsAndState(respPageList);
            return (ArrayList<OfferMobileQryResp>) respPageList;
        }
        else {
            return new ArrayList<>();
        }
    }

    /**
     * CPSP商品列表查询
     * 
     * @param req CpspQueryOfferListReq
     * @return CpspQueryOfferListResp
     */
    public CpspQueryOfferListResp qryCpspOfferList(CpspQueryOfferListReq req) throws BaseException {
        log.info("OfferManager qryCpspOfferList start, req = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getRequestId(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getChannelCode(), OfferBaseMessageCodeEnum.CHANNEL_IS_NULL);

        UserDTO userInfo = HttpSessionUtil.get();
        AssertUtil.isNotNull(userInfo, OfferBaseMessageCodeEnum.USER_ID_IS_NULL);
        AssertUtil.isNotNull(userInfo.getTenantId(), OfferBaseMessageCodeEnum.TENANT_ID_IS_NULL);
        req.setTenantId(userInfo.getTenantId());

        Page<CpspQueryOfferResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        // 封装商品标识
        if (StringUtils.isNotEmpty(req.getOfferIds())) {
            String[] str = req.getOfferIds().split(",");
            List<String> offerIdList = Arrays.asList(str);
            req.setOfferIdList(offerIdList);
        }
        if(StringUtils.isEmpty(req.getIsRecommend())){
            req.setIsRecommend("N");
        }else if("A".equals(req.getIsRecommend())){
            req.setIsRecommend(null);
        }
        if(StringUtils.isEmpty(req.getSortName())){
            req.setSortName("t.create_time");
            req.setSortOrder("desc");
        }

        log.info("OfferManager qryCPSPOfferList, pageSize = [{}], pageNo = [{}]", req.getPageSize(), req.getPageNo());
        Page<CpspQueryOfferResp> pageOfferList = prodGoodsMapper.qryCPSPOfferList(page, req);
        log.info("OfferManager qryCPSPOfferList, pageOfferList = [{}]", pageOfferList.getRecords());

        //适配天际补充商品图片信息
        if(null!=req.getIsGetContentInfo()&& req.getIsGetContentInfo().equals("1")  && null!=pageOfferList && pageOfferList.getSize()>0) {
            addContentInfo(pageOfferList.getRecords());
        }
        // 构造返回值
        CpspQueryOfferListResp result = new CpspQueryOfferListResp();
        result.setOfferList(pageOfferList.getRecords());
        result.setPageNo(pageOfferList.getCurrent());
        result.setPageSize(pageOfferList.getSize());
        result.setTotalCount(pageOfferList.getTotal());
        result.setTotalPage(pageOfferList.getPages());

        log.info("OfferManager qryCpspOfferList end, result = [{}]", result);
        return result;
    }

    private void addContentInfo(List<CpspQueryOfferResp> records)  throws BaseException{
        if(CollectionUtils.isEmpty(records)){
            return ;
        }
        List<String> offerIds = new ArrayList();
        for (CpspQueryOfferResp record : records) {
            offerIds.add(record.getOfferId());
        }
        if(CollectionUtils.isEmpty(offerIds)){
            return;
        }
        List<ProdGoodsContent> prodGoodsContents = prodGoodsContentMapper
                .selectGoodsContentByGoodsIds(offerIds);
        for(CpspQueryOfferResp record:records){
            List<CpspOfferContentResp>  contentList=  new ArrayList<CpspOfferContentResp>();
            for(ProdGoodsContent prodGoodsContent:prodGoodsContents){
                if(record.getOfferId().equals(prodGoodsContent.getGoodsId())){
                    CpspOfferContentResp cpspOfferContentResp=new CpspOfferContentResp();
                    cpspOfferContentResp.setGoodsContentType(prodGoodsContent.getGoodsContentType()+"");
                    cpspOfferContentResp.setDeviceType(prodGoodsContent.getDeviceType());
                    List<Map<String, String>> list= contentService.queryMaterialAndText(Long.parseLong(prodGoodsContent.getContentId()));
                    cpspOfferContentResp.setValueList(list);
                    List<CpspOfferContentResp>  contentResps=record.getContentList();
                    if(null==contentResps){
                        contentResps=new ArrayList<CpspOfferContentResp>();
                        record.setContentList(contentResps);
                    }
                    contentResps.add(cpspOfferContentResp);

                }
            }


            }
        }


    // 降低复杂度
    private void releaseForCoupoon(CouponSpecRuleRelaResp couponSpecRuleRelaResp, List<String> couponGoodsIds,
        List<String> offerTypes) {
        if (CouponSpecRuleRelaDef.COUPON_SPEC_RULE_APPLY_OFFER.equals(couponSpecRuleRelaResp.getCouponSpecRuleId())) {
            couponGoodsIds.add(couponSpecRuleRelaResp.getObjId());
        }
        if (CouponSpecRuleRelaDef.COUPON_SPEC_RULE_APPLY_OFFER_TYPE
            .equals(couponSpecRuleRelaResp.getCouponSpecRuleId())) {
            offerTypes.add(couponSpecRuleRelaResp.getObjId());
        }
    }

    private void processCouponReq(List<String> couponGoodsIds, List<String> offerTypes, String offerId, String couponId,
        String couponSpecId) {
        boolean exsits = StringUtils.isEmpty(offerId)
            && (StringUtils.isNotEmpty(couponId) || StringUtils.isNotEmpty(couponSpecId));
        if (exsits) {
            CouponSpecRuleRelaReq couponReq = new CouponSpecRuleRelaReq();
            if (StringUtils.isNotEmpty(couponId)) {
                couponReq.setCouponId(Long.valueOf(couponId));
            }
            if (StringUtils.isNotEmpty(couponSpecId)) {
                couponReq.setCouponSpecId(Long.valueOf(couponSpecId));
            }

            ResultVO<ArrayList<CouponSpecRuleRelaResp>> couponSpecRuleRelaRespList = couponService
                .qryCouponRelaList(couponReq);

            if (CollectionUtils.isNotEmpty(couponSpecRuleRelaRespList.getData())) {
                for (CouponSpecRuleRelaResp couponSpecRuleRelaResp : couponSpecRuleRelaRespList.getData()) {
                    releaseForCoupoon(couponSpecRuleRelaResp, couponGoodsIds, offerTypes);
                }
            }
        }
    }

    // 降低复杂度
    private void releaseForSrc(OfferMobileQryResp respDto, GoodsDoc goods) {
        respDto.setMktPrice(goods.getMktprice());

        if (goods.getEvaluationRate() != null) {
            respDto.setEvaluationRate(Long.valueOf(goods.getEvaluationRate()));
        }
        if (goods.getEvaluationCount() != null) {
            respDto.setEvaluationCount(Long.valueOf(goods.getEvaluationCount()));
        }
    }

    /**
     * 功能同qryOfferListForMobile
     * 
     * @param req OfferSearchReq
     * @return ArrayList<OfferMobileQryResp>
     * @throws BaseException <></>
     */
    public ArrayList<OfferMobileQryResp> searchOfferList(OfferSearchReq req) throws BaseException {
        log.info("OfferManager searchOfferList start, request = [{}]", req);

        // 权益类商品条件
        List<String> couponGoodsIds = new ArrayList<>();
        List<String> offerTypes = new ArrayList<>();
        processCouponReq(couponGoodsIds, offerTypes, null, req.getCouponId(), req.getCouponSpecId());
        req.setOfferIdList(couponGoodsIds);
        req.setOfferTypeList(offerTypes);

        ArrayList<OfferMobileQryResp> respList = new ArrayList<>();
        List<GoodsDoc> list = goodsDocService.searchGoods(req);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(goods -> {
                OfferMobileQryResp respDto = new OfferMobileQryResp();
                BeanUtils.copyProperties(goods, respDto);
                respDto.setOfferId(goods.getGoodsId());
                respDto.setOfferName(goods.getName());
                respDto.setOfferCode(goods.getSn());
                respDto.setOfferType(goods.getTypeId());
                List<ProdGoodsContent> prodGoodsContents = getReleaseForGoods(req, goods, respDto);

                if (CollectionUtils.isNotEmpty(prodGoodsContents)) {
                    releaseForLines2(respDto, prodGoodsContents);
                }
                respList.add(respDto);
            });
        }

        // 补充缩略图的信息，目前改接口只有手机端用
        String deviceType = "1";
        if (CollectionUtils.isNotEmpty(respList)) {
            for (OfferMobileQryResp offerMobileQryResp : respList) {
                String thumbUrl = prodGoodsManager.getThumbUrlByGoodsIdAndDeviceType(offerMobileQryResp.getOfferId(),
                        deviceType);
                String thumbnail = FastdfsTokenUtil.buildShowUrl(thumbUrl);
                offerMobileQryResp.setThumbnail(thumbnail);
            }
            // 封装状态
            getTagsAndState(respList);
        }

        log.info("OfferManager searchOfferList end, respList = [{}]", respList);
        return respList;
    }

    private void releaseForLines2(OfferMobileQryResp respDto, List<ProdGoodsContent> prodGoodsContents) {
        for (ProdGoodsContent prodGoodsContent : prodGoodsContents) {
            ContentBaseResponseDTO contentDeatil = contentService
                .queryContentDeatil(Long.valueOf(prodGoodsContent.getContentId()));
            if (null != contentDeatil) {
                releaseForLines(respDto, contentDeatil);
            }
        }
    }

    private void releaseForLines(OfferMobileQryResp respDto, ContentBaseResponseDTO contentDeatil) {
        ContentBasePersonalDTO contentBasePersonal = contentDeatil.getContentBasePersonal();
        if (null != contentBasePersonal) {
            List<ContentMaterialDTO> contentMaterials = contentBasePersonal.getContentMaterials();
            if (CollectionUtils.isNotEmpty(contentMaterials)) {
                ContentMaterialDTO contentMaterialDTO = contentMaterials.get(0);
                respDto.setThumbnail(contentMaterialDTO.getShowUrl() + contentMaterialDTO.getThumbUrl());
            }
        }
    }

    private List<ProdGoodsContent> getReleaseForGoods(OfferSearchReq req, GoodsDoc goods, OfferMobileQryResp respDto) {
        // 金额类
        releaseForSrc(respDto, goods);

        // tag cat
        String attrValues = goods.getAttrValues();
        if (StringUtils.isNotEmpty(attrValues)) {
            List<String> ids = Arrays.asList(org.apache.commons.lang.StringUtils.split(attrValues, ";"));
            List<String> tagIds = new ArrayList<>();
            ids.forEach(tag -> {
                if (StringUtils.indexOf(tag, ":") < 0) {
                    tagIds.add(tag);
                }
            });
            respDto.setTags(tagIds);
        }
        // 缩略图
        ProdGoodsQueryContentReq prodGoodsQueryContentReq = new ProdGoodsQueryContentReq();
        prodGoodsQueryContentReq.setGoodsId(goods.getGoodsId());
        prodGoodsQueryContentReq.setDeviceType(req.getDevice());
        prodGoodsQueryContentReq.setGoodsContentType(TYPE_SET);
        log.info("OfferManager searchOfferList selectGoodsContentByGoodsId prodGoodsQueryContentReq = [{}]",
            prodGoodsQueryContentReq);
        List<ProdGoodsContent> prodGoodsContents = prodGoodsContentMapper
            .selectGoodsContentByGoodsId(prodGoodsQueryContentReq);
        log.info("OfferManager searchOfferList selectGoodsContentByGoodsId prodGoodsContents = [{}]",
            prodGoodsContents);
        return prodGoodsContents;
    }

    // private void releaseForChannel(Boolean delFlag, List<Long> salesConditionIds, OfferMobileQryResp
    // offerMobileQryResp) {
    // if (delFlag) {
    // salesConditionIds.add(offerMobileQryResp.getOfferId());
    // }
    // }
    //
    // private void filterProdGoodsByContactChannel(List<OfferMobileQryResp> respList, List<ProdGoodsSalesCondition>
    // prodGoodsSalesConditionList,
    // String contactChannelId, List<Long> salesConditionIds) {
    // log.info("OfferManager filterProdGoodsByContactChannel start, respList = [{}]", respList);
    //
    // if (StringUtils.isEmpty(contactChannelId)) {
    // return;
    // }
    //
    // if (CollectionUtils.isEmpty(respList)) {
    // return;
    // }
    //
    // if (CollectionUtils.isEmpty(prodGoodsSalesConditionList)) {
    // return;
    // }
    //
    // Iterator<OfferMobileQryResp> iter = respList.iterator();
    //
    // while (iter.hasNext()) {
    // OfferMobileQryResp offerMobileQryResp = iter.next();
    //
    // Boolean delFlag = false;
    // for (ProdGoodsSalesCondition prodGoodsSalesCondition : prodGoodsSalesConditionList) {
    // if (ProdGoodsSaleRuleDef.CONTACT_CHANNEL.equals(prodGoodsSalesCondition.getSalesRuleId().longValue())
    // && offerMobileQryResp.getOfferId().toString().equals(prodGoodsSalesCondition.getGoodsId())) {
    // delFlag = true;
    //
    // if (contactChannelId.equals(prodGoodsSalesCondition.getObjId())) {
    // delFlag = false;
    // break;
    // }
    // }
    // }
    // releaseForChannel(delFlag, salesConditionIds, offerMobileQryResp);
    // }
    //
    // log.info("OfferManager filterProdGoodsByContactChannel end, salesConditionIds = [{}]", salesConditionIds);
    // log.info("OfferManager filterProdGoodsByContactChannel end, respList = [{}]", respList);
    // }

    private void formartPicUrl(List<OfferMobileQryResp> respList) throws BaseException {
        if (CollectionUtils.isNotEmpty(respList)) {
            for (OfferMobileQryResp offerMobileQryResp : respList) {
                String thumbUrl = prodGoodsManager.getThumbUrlByGoodsIdAndDeviceType(offerMobileQryResp.getOfferId(),
                    "1");
                String thumbnail = FastdfsTokenUtil.buildShowUrl(thumbUrl);
                offerMobileQryResp.setThumbnail(thumbnail);
            }
        }

    }

    private void getTagsAndState(List<OfferMobileQryResp> respList) throws BaseException {
        for (OfferMobileQryResp offerMobileQryResp : respList) {
            // 封装tag
            List<String> tagIds = prodTagsRelMapper
                .listTagIdByProdGoodsId(String.valueOf(offerMobileQryResp.getOfferId()));

            if (CollectionUtils.isNotEmpty(tagIds)) {
                offerMobileQryResp.setTags(tagIds);

                // 调用content中心拿到tag的信息
                QryTagReq req = new QryTagReq();
                req.setTagIds(tagIds);
                ResultVO<ArrayList<TagDTO>> resultVO = contentService.qryTagListByCond(req);

                List<TagResp> tagResps = new ArrayList<>();
                ResultVOCheckUtil.checkResultVO(resultVO);
                List<TagDTO> tagDTOS = resultVO.getData();
                for (TagDTO tagDto : tagDTOS) {
                    TagResp tagResp = new TagResp();
                    BeanUtils.copyProperties(tagDto, tagResp);
                    tagResps.add(tagResp);
                }
                offerMobileQryResp.setTagList(tagResps);
            }

            // 封装State
            String state = getOfferState(offerMobileQryResp.getState(), offerMobileQryResp.getMarketingBeginTime(),
                offerMobileQryResp.getMarketingEndTime(), null);
            offerMobileQryResp.setState(state);
        }
    }

    public static final long CONTENT_FOR_ID = 3L;

    public static final long CONTENT = 2L;

    public ProdGoodsDetailByIdResp qryOfferDetail(OfferDetailMobileQryReq req) throws BaseException {
        log.info("OfferManager qryOfferListForMobile start, OfferDetailMobileQryReq = [{}]", req);

        ProdGoods prodGoods;
        String goodsId = req.getOfferId();
        if (StringUtils.isEmpty(goodsId)) {
            prodGoods = prodGoodsMapper.qryGoodsBySn(req.getOfferCode(), req.getAllFlag());
            goodsId = prodGoods.getGoodsId();
        }
        else {
            prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, req.getAllFlag());
        }
        log.info("OfferManager qryOfferListForMobile prodGoods = [{}]", prodGoods);
        AssertUtil.isNotNull(prodGoods, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        UserDTO tmpUserDTO = HttpSessionUtil.get();
        Long channelType = null;
        if (StringUtils.isEmpty(req.getShareFlag())) {
            AssertUtil.isNotNull(tmpUserDTO, OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
            AssertUtil.isNotNull(tmpUserDTO.getChannelType(), OfferBaseMessageCodeEnum.CHANNEL_IS_NULL);
            channelType = tmpUserDTO.getChannelType();
        }
        else {
            if (req.getShareFlag().equals("Y")) {
                if (StringUtils.isEmpty(req.getChannel())) {
                    throw new BaseException(OfferBaseMessageCodeEnum.CHANNEL_IS_NULL);
                }
                // 通过channel 获取channelType
                ContactChannel contactChannel = contactChannelManager.qryContactChannel(req.getChannel());
                if (contactChannel == null) {
                    throw new BaseException(OfferBaseMessageCodeEnum.CHANNEL_IS_NULL);
                }
                channelType = contactChannel.getChannelType();
            }
        }

        ProdGoodsDetailByIdResp resp = releaseForLines1(prodGoods);
        releaseForLines2(prodGoods, goodsId, resp);
        // 商品内容修改
        String deviceType;
        // 渠道为4 车机端 否则默认手机端
        deviceType = releaseForLines3(prodGoods, resp, channelType);

        // 缩略图
        String thumbUrl = prodGoodsManager.getThumbUrlByGoodsIdAndDeviceType(goodsId, deviceType);
        String thumbnail = FastdfsTokenUtil.buildShowUrl(thumbUrl);
        resp.setThumbnail(thumbnail);

        // 后付费
        final long saleContentType = 3L;
        List<String> postSalecontentIds = getContentIds(prodGoods.getGoodsId(), deviceType, saleContentType);
        if (CollectionUtils.isNotEmpty(postSalecontentIds)) {
            ContentBaseResponseDTO contentDeatil = contentService
                .queryContentDeatil(Long.valueOf(postSalecontentIds.get(0)));
            final boolean contentDivision = (null != contentDeatil && null != contentDeatil.getContentBasePersonal())
                && CollectionUtils.isNotEmpty(contentDeatil.getContentBasePersonal().getContentTexts());
            if (contentDivision) {
                resp.setPostSale(contentDeatil.getContentBasePersonal().getContentTexts().get(0).getContent());
            }
        }
        List<String> picUrls = formartPictures(resp.getGoodsId(), deviceType);
        if (CollectionUtils.isNotEmpty(picUrls)) {
            resp.setPics(picUrls);
        }

        Long stockQty;
        if (null == redisCache.getNumber(CacheKeyDef.GOODS_QTY, resp.getSn())) {
            stockQty = 0L;
        }
        else {
            stockQty = Long.valueOf(Integer.parseInt(redisCache.getNumber(CacheKeyDef.GOODS_QTY, resp.getSn())));
        }
        resp.setStockQty(stockQty);
        if (StringUtils.isNotEmpty(resp.getIntro())) {
            resp.setIntro(FastdfsTokenUtil.repairContent(resp.getIntro()));
        }
        if (StringUtils.isNotEmpty(resp.getAfterSale())) {
            resp.setAfterSale(FastdfsTokenUtil.repairContent(resp.getAfterSale()));
        }
        // 封装状态
        resp.setState(
            getOfferState(resp.getState(), resp.getMarketingBeginTime(), resp.getMarketingEndTime(), stockQty));
        // 封装tags
        List<String> tagIds = prodTagsRelMapper.listTagIdByProdGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(tagIds)) {

            // 调用content中心拿到tag的信息
            QryTagReq request = new QryTagReq();
            request.setTagIds(tagIds);
            ResultVO<ArrayList<TagDTO>> resultVO = contentService.qryTagListByCond(request);
            ResultVOCheckUtil.checkResultVO(resultVO);
            log.info("OfferManager qryOfferListForMobile deal query prod tags, results=[{}]", resultVO.getData());
            List<TagDTO> tagDTOS = resultVO.getData();
            List<TagResp> tagResps = new ArrayList<>();
            for (TagDTO tagDto : tagDTOS) {
                TagResp tagResp = new TagResp();
                BeanUtils.copyProperties(tagDto, tagResp);
                tagResps.add(tagResp);
            }
            resp.setTags(tagResps);
        }
        // 封装spu
        buildSpuByGoodsId(goodsId, resp);

        log.info("OfferManager qryOfferListForMobile end resp = [{}]", resp);
        return resp;
    }

    /**
     * CPSP查询商品详情
     * 
     * @param req CpspQueryOfferDetailsReq
     * @return CpspQueryOfferDetailsResp
     */
    public CpspQueryOfferDetailsResp qryCpspOfferDetail(CpspQueryOfferDetailsReq req) throws BaseException {
        log.info("OfferManager qryCpspOfferDetail start, req = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getOfferId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        log.info("OfferManager qryCpspOfferDetail qryCPSPDetailByGoodsId req = [{}]", req);
        CpspQueryOfferDetailsResp result = prodGoodsMapper.qryCPSPDetailByGoodsId(req);
        // 商品属性值
        List<CpspQueryOfferDetailsAttrListResp> attrValueList = prodGoodsAttrValueMapper.listCPSPAttrByProdGoodsId(req);
        if (CollectionUtils.isNotEmpty(attrValueList)) {
            for (CpspQueryOfferDetailsAttrListResp attrValue : attrValueList) {
                List<CpspQueryOfferDetailsAttrResp> attrValues = attrValueMapper
                    .queryCPSPAttrValueByAttrId(attrValue.getAttrId());
                attrValue.setAttrValue(attrValues);
            }
            result.setAttrList(attrValueList);
        }
        if(null!=req.getIsGetContentInfo() && "1".equals(req.getIsGetContentInfo())){
            List<String> offerIds=new  ArrayList<String>();
            offerIds.add(req.getOfferId());

            List<ProdGoodsContent> prodGoodsContents = prodGoodsContentMapper
                    .selectGoodsContentByGoodsIds(offerIds);
            if(CollectionUtils.isNotEmpty(prodGoodsContents)){
                List<CpspOfferContentResp>  contentList=  new ArrayList<CpspOfferContentResp>();
                for(ProdGoodsContent prodGoodsContent:prodGoodsContents){
                    CpspOfferContentResp cpspOfferContentResp=new CpspOfferContentResp();
                    cpspOfferContentResp.setGoodsContentType(prodGoodsContent.getGoodsContentType()+"");
                    cpspOfferContentResp.setDeviceType(prodGoodsContent.getDeviceType());
                    List<Map<String, String>> list= contentService.queryMaterialAndText(Long.parseLong(prodGoodsContent.getContentId()));
                    cpspOfferContentResp.setValueList(list);
                    contentList.add(cpspOfferContentResp);
                }
                result.setContentList(contentList);
            }





        }


        log.info("OfferManager qryCpspOfferDetail end result = [{}]", result);
        return result;
    }

    private String releaseForLines3(ProdGoods prodGoods, ProdGoodsDetailByIdResp resp, Long channelType)
        throws BaseException {
        String deviceType;
        if (channelType != null && ChannelTypeDef.CAR.equals(channelType.toString())) {
            deviceType = "2";
            // 详情 intro
            final long cType = 2L;
            List<String> detailContentIds = getContentIds(prodGoods.getGoodsId(), deviceType, cType);
            releaseForLines4(resp, detailContentIds);

            // aftersale
            List<String> afterSaleContentIds = getContentIds(prodGoods.getGoodsId(), deviceType, CONTENT_FOR_ID);
            if (CollectionUtils.isNotEmpty(afterSaleContentIds)) {
                ContentBaseResponseDTO contentDeatil = contentService
                    .queryContentDeatil(Long.valueOf(afterSaleContentIds.get(0)));
                boolean exists = (null != contentDeatil && null != contentDeatil.getContentBasePersonal())
                    && (CollectionUtils.isNotEmpty(contentDeatil.getContentBasePersonal().getContentTexts()));
                if (exists) {
                    resp.setAfterSale(contentDeatil.getContentBasePersonal().getContentTexts().get(0).getContent());
                }
            }
        }
        else {
            deviceType = "1";
            // 详情
            List<String> detailContentIds = getContentIds(prodGoods.getGoodsId(), deviceType, CONTENT);
            releaseForLines5(resp, detailContentIds);
        }
        return deviceType;
    }

    private void releaseForLines5(ProdGoodsDetailByIdResp resp, List<String> detailContentIds) {
        if (CollectionUtils.isNotEmpty(detailContentIds)) {
            ContentBaseResponseDTO contentDeatil = contentService
                .queryContentDeatil(Long.valueOf(detailContentIds.get(0)));
            boolean exists = (null != contentDeatil && null != contentDeatil.getContentBasePersonal())
                && (CollectionUtils.isNotEmpty(contentDeatil.getContentBasePersonal().getContentTexts()));
            if (exists) {
                resp.setDetail(contentDeatil.getContentBasePersonal().getContentTexts().get(0).getContent());
            }
        }
    }

    private void releaseForLines4(ProdGoodsDetailByIdResp resp, List<String> detailContentIds) {
        if (CollectionUtils.isNotEmpty(detailContentIds)) {
            ContentBaseResponseDTO contentDeatil = contentService
                .queryContentDeatil(Long.valueOf(detailContentIds.get(0)));
            boolean exists = (null != contentDeatil && null != contentDeatil.getContentBasePersonal())
                && (CollectionUtils.isNotEmpty(contentDeatil.getContentBasePersonal().getContentTexts()));
            if (exists) {
                resp.setDetail(contentDeatil.getContentBasePersonal().getContentTexts().get(0).getContent());
                resp.setIntro(contentDeatil.getContentBasePersonal().getContentTexts().get(0).getContent());
            }
        }
    }

    private void releaseForLines2(ProdGoods prodGoods, String goodsId, ProdGoodsDetailByIdResp resp) {
        // 商品类型名称
        String typeId = prodGoods.getTypeId();
        if (StringUtils.isNotEmpty(typeId)) {
            ProdGoodsType prodGoodsType = prodGoodsTypeMapper.selectById(typeId);
            if (null != prodGoodsType) {
                resp.setTypeName(prodGoodsType.getTypeName());
            }
        }
        // 商品品牌
        if (StringUtils.isNotEmpty(prodGoods.getBrandId())) {
            CarBrand carBrand = carBrandMapper.selectById(prodGoods.getBrandId());
            if (null != carBrand) {
                resp.setBrandName(carBrand.getCarBrandName());
            }
        }
        // 商品包
        String isPackage = prodGoods.getIsPackage();
        resp.setIsPackage(isPackage);
        if (CouponSpecConst.YES.equals(isPackage)) {
            List<ProdGoods> prodGoodsList = prodGoodsGroupRelMapper.listProdGoodsByPackageId(goodsId);
            if (CollectionUtils.isNotEmpty(prodGoodsList)) {
                resp.setPkgMemList(prodGoodsList);
            }
        }
        // 商品属性值
        List<AttrValueResp> attrValueResps = prodGoodsAttrValueMapper.listAttrValueRespByProdGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(attrValueResps)) {
            for (AttrValueResp attrValueResp : attrValueResps) {
                List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(attrValueResp.getAttrId());
                attrValueResp.setAttrValueList(attrValues);
            }
            resp.setAttrValueRespList(attrValueResps);
        }
        // 商品店铺
        Store store = storeMapper.selectById(resp.getStoreId());
        if (store != null) {
            resp.setStoreName(store.getStoreName());
        }
    }

    private ProdGoodsDetailByIdResp releaseForLines1(ProdGoods prodGoods) {
        // 构造返回值
        ProdGoodsDetailByIdResp resp = new ProdGoodsDetailByIdResp();
        BeanUtils.copyProperties(prodGoods, resp);
        // 评分调整值
        Long evaluationRate = prodGoods.getEvaluationRate() + prodGoods.getAdjEvaluationRate();
        final int hundred = 100;
        evaluationRate = evaluationRate < 0 ? 0 : evaluationRate;
        evaluationRate = evaluationRate > hundred ? hundred : evaluationRate;
        resp.setEvaluationRate(evaluationRate);
        // 评论数量调整值
        Long evaluationCount = prodGoods.getEvaluationCount() + prodGoods.getAdjEvaluationCount();
        evaluationCount = evaluationCount < 0 ? 0 : evaluationCount;
        resp.setEvaluationCount(evaluationCount);
        // 新增销量
        Long buyCount = prodGoods.getBuyCount() + prodGoods.getAdjBuyCount();
        buyCount = buyCount < 0 ? 0 : buyCount;
        resp.setBuyCount(buyCount);
        return resp;
    }

    /**
     * 封装Spu
     * 
     * @param goodsId 商品id
     * @param resp 返回值
     */
    private void buildSpuByGoodsId(String goodsId, ProdGoodsDetailByIdResp resp) throws BaseException {
        log.info("OfferManager buildSpuByGoodsId start, ProdGoodsDetailByIdResp = [{}]", resp);

        SpuGoodsResp spuGoodsResp = spuGoodsMapper.qrySpuGoodsByGoodsId(goodsId);

        if (null == spuGoodsResp) {
            return;
        }

        SpusResp spusResp = new SpusResp();
        String spuId = spuGoodsResp.getSpuId();
        spusResp.setSpuId(spuId);
        Spu spu = spuMapper.selectById(spuId);
        AssertUtil.isNotNull(spu, OfferBaseMessageCodeEnum.SPU_ID_IS_ERROR);
        spusResp.setSpuName(spu.getSpuName());
        // 封装spu属性
        String skuAttrIds = spu.getSkuAttrIds();
        if (StringUtils.isNotEmpty(skuAttrIds)) {
            String[] skuAttrIdList = skuAttrIds.split(",");
            List<SpuAttrsResp> attrs = new ArrayList<>(skuAttrIdList.length);
            for (int i = 0; i < skuAttrIdList.length; i++) {
                SpuAttrsResp spuAttrsResp = new SpuAttrsResp();
                Attr attr = attrMapper.selectById(skuAttrIdList[i]);
                spuAttrsResp.setAttrId(attr.getAttrId());
                spuAttrsResp.setAttrName(attr.getAttrName());
                List<SpuAttrsValueResp> spuAttrsValueList = new ArrayList<>();
                List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(attr.getAttrId());
                for (AttrValue attrValue : attrValues) {
                    SpuAttrsValueResp spuAttrsValue = new SpuAttrsValueResp();
                    spuAttrsValue.setValue(attrValue.getValue());
                    spuAttrsValue.setValueMark(attrValue.getValueMark());
                    spuAttrsValueList.add(spuAttrsValue);
                }
                spuAttrsResp.setAttrValue(spuAttrsValueList);
                attrs.add(spuAttrsResp);
            }
            spusResp.setAttrs(attrs);
        }
        // 封装sku
        List<SpuGoodsResp> spuGoodsResps = spuGoodsMapper.listSpuGoodsBySpuId(spuId);
        if (CollectionUtils.isNotEmpty(spuGoodsResps)) {
            List<SkusResp> skus = new ArrayList<>();
            for (SpuGoodsResp spuGoods : spuGoodsResps) {
                SkusResp skusResp = new SkusResp();
                skusResp.setOfferId(spuGoods.getGoodsId());
                ProdGoods goods = prodGoodsMapper.selectById(spuGoods.getGoodsId());
                AssertUtil.isNotNull(spu, OfferBaseMessageCodeEnum.THE_OFFER_ID_IS_ERROR);
                skusResp.setOfferName(goods.getName());
                if (null != goods.getPrice()) {
                    skusResp.setPrice(goods.getPrice().toString());
                }
                String state = getOfferState(goods.getState(), goods.getMarketingBeginTime(),
                    goods.getMarketingEndTime(), null);
                skusResp.setState(state);
                String thumbnailUrl = FastdfsTokenUtil.buildShowUrl(goods.getThumbnail());
                skusResp.setThumbnail(thumbnailUrl);
                List<SkuAttrValueResp> skuAttrValueList = prodGoodsAttrValueMapper
                    .listSkuAttrValueByProdGoodsIdForMobile(goods.getGoodsId());
                skusResp.setAttrValue(skuAttrValueList);
                skus.add(skusResp);
            }
            spusResp.setSkus(skus);
        }
        resp.setSpu(spusResp);

        log.info("OfferManager buildSpuByGoodsId end");
    }

    // 查询手机端
    private List<String> getContentIds(String goodsId, String deviceType, Long contentType) throws BaseException {
        ProdGoodsQueryContentReq prodGoodsQueryContentReq = new ProdGoodsQueryContentReq();
        prodGoodsQueryContentReq.setGoodsId(goodsId);
        prodGoodsQueryContentReq.setDeviceType(deviceType);
        prodGoodsQueryContentReq.setGoodsContentType(contentType);
        return qryGoodsContentList(prodGoodsQueryContentReq);
    }

    public List<String> qryGoodsContentList(ProdGoodsQueryContentReq req) throws BaseException {
        log.info("OfferManager qryGoodsContentList start, List<QryGoodsContentListResp> = [{}]", req);

        AssertUtil.isNotEmpty(req.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        List<String> contentIds = new ArrayList<>();
        List<ProdGoodsContent> prodGoodsContents = prodGoodsContentMapper.selectGoodsContentByGoodsId(req);
        if (CollectionUtils.isNotEmpty(prodGoodsContents)) {
            prodGoodsContents.forEach(prodGoodsContent -> {
                contentIds.add(prodGoodsContent.getContentId());
            });
        }

        log.info("OfferManager qryGoodsContentList end");
        return contentIds;
    }

    private void releaseForPic(ContentBaseResponseDTO contentBaseResponseDTO, List<String> pics) {
        List<ContentMaterialDTO> contentMaterials = contentBaseResponseDTO.getContentBasePersonal()
            .getContentMaterials();
        if (CollectionUtils.isNotEmpty(contentMaterials)) {
            contentMaterials.forEach(contentMaterialDTO -> {
                if (StringUtils.isNotEmpty(contentMaterialDTO.getUrl())) {
                    String path = contentMaterialDTO.getShowUrl() + contentMaterialDTO.getUrl();

                    if (StringUtils.isNotEmpty(contentMaterialDTO.getPathToken())) {
                        path = path + "?" + contentMaterialDTO.getPathToken();
                    }

                    pics.add(path);
                }

            });

        }
    }

    private List<String> formartPictures(String goodsId, String deviceType) {
        List<String> pics = new ArrayList<>();
        ProdGoodsQueryContentReq prodGoodsQueryContentReq = new ProdGoodsQueryContentReq();
        prodGoodsQueryContentReq.setGoodsId(goodsId);
        prodGoodsQueryContentReq.setDeviceType(deviceType);
        // 写死 只去查询轮播图
        prodGoodsQueryContentReq.setGoodsContentType(1L);
        List<ProdGoodsContent> prodGoodsContentList = prodGoodsContentMapper
            .selectGoodsContentByGoodsId(prodGoodsQueryContentReq);

        if (CollectionUtils.isNotEmpty(prodGoodsContentList)) {
            prodGoodsContentList.forEach(prodGoodsContent -> {
                ContentBaseResponseDTO contentBaseResponseDTO = contentService
                    .queryContentDeatil(Long.valueOf(prodGoodsContent.getContentId()));

                if (null != contentBaseResponseDTO && null != contentBaseResponseDTO.getContentBase()) {

                    ContentBaseDTO contentBaseDTO = contentBaseResponseDTO.getContentBase();

                    if (!ContentTypeDef.CONTENT_TEXT.equals(contentBaseDTO.getType().toString())
                        && null != contentBaseResponseDTO.getContentBasePersonal()) {
                        releaseForPic(contentBaseResponseDTO, pics);
                    }

                }
            });

        }

        return pics;
    }

    /**
     * 注意此时状态和数据库商品状态不是同一的，后台自行封装的
     */
    private String getOfferState(String prodGoodsState, Date marketBeginDate, Date marketEndDate, Long goodsQty) {
        log.info("OfferManager getOfferState prodGoodsState = [{}], marketBeginDate = [{}]", prodGoodsState,
            marketBeginDate);
        log.info("OfferManager getOfferState marketEndDate = [{}], goodsQty = [{}]", marketEndDate, goodsQty);

        String offerState = OfferStateDef.OFF_SALE;

        if (ProdGoodsStateDef.CREATED.equals(prodGoodsState) || ProdGoodsStateDef.AUDIT.equals(prodGoodsState)
            || ProdGoodsStateDef.AUDIT_FAIL.equals(prodGoodsState)
            || ProdGoodsStateDef.INACTIVE.equals(prodGoodsState)) {
            offerState = OfferStateDef.OFF_SALE;
        }
        else if (ProdGoodsStateDef.PUT_ON_SALE.equals(prodGoodsState)) {
            if (DBDateUtil.getCurrentDBDateTime().before(marketBeginDate)) {
                offerState = OfferStateDef.OFF_SALE;
                log.info("OfferManager getOfferState offerState = [{}]", offerState);
                return offerState;
            }
            if (null != marketEndDate && DBDateUtil.getCurrentDBDateTime().after(marketEndDate)) {
                offerState = OfferStateDef.OFF_SALE;
                log.info("OfferManager getOfferState offerState = [{}]", offerState);
                return offerState;
            }

            if (null == goodsQty || goodsQty > 0) {
                offerState = OfferStateDef.ON_SALE;
                log.info("OfferManager getOfferState offerState = [{}]", offerState);
                return offerState;
            }
            else {
                offerState = OfferStateDef.NO_STOCK;
                log.info("OfferManager getOfferState offerState = [{}]", offerState);
                return offerState;
            }
        }

        log.info("OfferManager getOfferState offerState = [{}]", offerState);
        return offerState;
    }

    /**
     * MQ通知审批商品结果
     * 
     * @param req OscarMallAuditResultReq
     */
    public void auditOffer(OscarMallAuditResultReq req) throws BaseException {
        log.info("OfferManager auditOffer start, req = [{}]", req);

        String businessId = req.getBusinessId();
        AssertUtil.isNotNull(businessId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        String result = req.getResult();
        AssertUtil.isNotNull(result, OfferBaseMessageCodeEnum.RESULT_IS_NULL);
        String state = ProdGoodsStateDef.AUDIT_SUCCESS;
        if (AbstractOscarMallAuditResultDef.RESULT_ZERO.equals(result)) {
            state = ProdGoodsStateDef.AUDIT_FAIL;
        }
        prodGoodsMapper.updateStateByGoodsId(businessId, state);

        log.info("OfferManager auditOffer end");
    }

}
