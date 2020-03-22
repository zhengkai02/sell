package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.dto.client.resp.TagDTO;
import com.iwhalecloud.retail.offer.dto.req.QryTagReq;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.consts.MessageDef;
import com.iwhalecloud.retail.common.consts.OperLogEventDef;
import com.iwhalecloud.retail.common.consts.OperTypeDef;
import com.iwhalecloud.retail.common.dto.OperObjDetail;
import com.iwhalecloud.retail.common.dto.OperObjLog;
import com.iwhalecloud.retail.common.dto.RecordOperLogObj;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.message.MessageProducer;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.DateUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.IPUtil;
import com.iwhalecloud.retail.common.utils.OperLogUtil;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.BossContactChannelDef;
import com.iwhalecloud.retail.offer.consts.CouponSpecConst;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.PageDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsCatTypeDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsStateDef;
import com.iwhalecloud.retail.offer.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.offer.dto.ProdGoodsRedisDto;
import com.iwhalecloud.retail.offer.dto.client.req.QueryProductListReq;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBasePersonalDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseResponseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentMaterialDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.QueryProductListResp;
import com.iwhalecloud.retail.offer.dto.req.AddGoodsContentReq;
import com.iwhalecloud.retail.offer.dto.req.AddProdBuyCountReq;
import com.iwhalecloud.retail.offer.dto.req.AddProdGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.ContentAddReq;
import com.iwhalecloud.retail.offer.dto.req.InvoiceCatgDetail;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsContentReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsTextInfoReq;
import com.iwhalecloud.retail.offer.dto.req.ModProdGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsContentReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryContentReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryTargetReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsRelReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsStockWaveReq;
import com.iwhalecloud.retail.offer.dto.req.PublishGoodReq;
import com.iwhalecloud.retail.offer.dto.req.QryProdGoodsListReq;
import com.iwhalecloud.retail.offer.dto.req.SynMgtInvoiceCatgNameReq;
import com.iwhalecloud.retail.offer.dto.req.UpdateProdGoodsSalesConditionReq;
import com.iwhalecloud.retail.offer.dto.resp.AddProdGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.AttrValueResp;
import com.iwhalecloud.retail.offer.dto.resp.GoodsAttrThreeTypeResp;
import com.iwhalecloud.retail.offer.dto.resp.ManagementProdGoodsDetailByIdResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsDetailByIdResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsInfoResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsRelResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesConditionResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesRuleResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsStockWaveResp;
import com.iwhalecloud.retail.offer.dto.resp.ProductResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatMemListResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsContentListResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsTextInfoResp;
import com.iwhalecloud.retail.offer.dto.resp.SynMgtInvoiceCatgNameResp;
import com.iwhalecloud.retail.offer.dto.resp.TagResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatMem;
import com.iwhalecloud.retail.offer.entity.ProdGoodsContent;
import com.iwhalecloud.retail.offer.entity.ProdGoodsType;
import com.iwhalecloud.retail.offer.entity.ProdTagsRel;
import com.iwhalecloud.retail.offer.entity.Store;
import com.iwhalecloud.retail.offer.entity.TblStoreCatg;
import com.iwhalecloud.retail.offer.entity.TblStoreCatgMem;
import com.iwhalecloud.retail.offer.mapper.ContactChannelMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsAttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMemMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsContentMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsGroupRelMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsRelMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsSalesConditionMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsStockWaveMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsTypeMapper;
import com.iwhalecloud.retail.offer.mapper.ProdTagsRelMapper;
import com.iwhalecloud.retail.offer.mapper.StoreMapper;
import com.iwhalecloud.retail.offer.mapper.TblStoreCatgMapper;
import com.iwhalecloud.retail.offer.mapper.TblStoreCatgMemMapper;
import com.iwhalecloud.retail.offer.repository.UserRepository;
import com.iwhalecloud.retail.offer.service.ContentService;
import com.iwhalecloud.retail.offer.service.ProductService;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fanxiaofei
 * @date 2019/02/25
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsManager {

    @Value("${fdfs.showUrl}")
    private String showUrl;

    @Resource
    private ProdGoodsMapper prodGoodsMapper;

    @Resource
    private ProdGoodsContentMapper prodGoodsContentMapper;

    @Resource
    private ProdGoodsTypeMapper prodGoodsTypeMapper;

    @Resource
    private ProdGoodsGroupRelMapper prodGoodsGroupRelMapper;

    @Resource
    private ProdGoodsCatMapper prodGoodsCatMapper;

    @Resource
    private ProdGoodsCatMemMapper prodGoodsCatMemMapper;

    @Resource
    private ProdGoodsRelMapper prodGoodsRelMapper;

    @Resource
    private ProdGoodsAttrValueMapper prodGoodsAttrValueMapper;

    @Resource
    private ProdGoodsSalesConditionMapper prodGoodsSalesConditionMapper;

    @Resource
    private ProdTagsRelMapper prodTagsRelMapper;

    @Resource
    private ProdGoodsStockWaveMapper prodGoodsStockWaveMapper;

    @Autowired
    private ICache redisCache;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private TblStoreCatgMapper storeCatgMapper;

    @Autowired
    private TblStoreCatgMemMapper storeCatgMemMapper;

    @Autowired
    private ProductService productService;

    public static final String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private CommonManager commonManager;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ProdGoodsContentManager prodGoodsContentManager;

    @Autowired
    private ProdGoodsSalesConditionManager prodGoodsSalesConditionManager;

    @Autowired
    private ProdGoodsRelManager prodGoodsRelManager;

    @Autowired
    private ProdGoodsStockWaveManager prodGoodsStockWaveManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProdGoodsManagerHelper prodGoodsManagerHelper;

    @Autowired
    private ContactChannelMapper contactChannelMapper;

    /**
     * 查询商品详情
     * 
     * @param goodsId 商品id
     * @return ProdGoodsDetailByIdResp
     */
    public ManagementProdGoodsDetailByIdResp queryProdGoodsDetailById(String goodsId) throws BaseException {
        log.info("ProdGoodsManager queryProdGoodsDetailById start, id = [{}]", goodsId);

        // 校验参数
        if (StringUtils.isEmpty(goodsId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        AssertUtil.isNotNull(prodGoods, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        // 构造返回值
        ManagementProdGoodsDetailByIdResp result = new ManagementProdGoodsDetailByIdResp();
        BeanUtils.copyProperties(prodGoods, result);

        // 产品
        QueryProductListReq req = new QueryProductListReq();
        req.setProductCode(result.getProductCode());
        req.setRequestId(UidGeneator.getUID());
        req.setPageSize(PageDef.PAGE_SIZE);
        req.setPageNo(PageDef.PAGE_NO);
        req.setChannel(BossContactChannelDef.TSOP);
        ResultVO<QueryProductListResp> respList = productService.listProduct(req);
        ResultVOCheckUtil.checkResultVO(respList);

        if (null == respList.getData() || CollectionUtils.isEmpty(respList.getData().getProductList())) {
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_NOT_EXIST);
        }
        ProductResp productResp = new ProductResp();
        BeanUtils.copyProperties(respList.getData().getProductList().get(0), productResp);
        result.setProductResp(productResp);

        // 缩略图
        prodGoodsManagerHelper.releaseForPic(result);

        // 商品类型
        String typeId = prodGoods.getTypeId();
        if (StringUtils.isNotEmpty(typeId)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            queryWrapper.eq(ColumnNameDef.TYPE_ID, typeId);
            ProdGoodsType prodGoodsType = prodGoodsTypeMapper.selectOne(queryWrapper);
            if (null != prodGoodsType) {
                result.setTypeName(prodGoodsType.getTypeName());
            }
        }

        // 商品目录
        prodGoodsManagerHelper.releaseFprContents(goodsId, result);

        // 商品包成员
        String isPackage = prodGoods.getIsPackage();
        if (CouponSpecConst.YES.equals(isPackage)) {
            List<ProdGoods> prodGoodsList = prodGoodsGroupRelMapper.listProdGoodsByPackageId(goodsId);
            result.setPkgMemList(prodGoodsList);
        }

        // 商品关系类型
        List<ProdGoodsRelResp> prodGoodsRels = prodGoodsRelMapper.listProdGoodsRelByProdGoodsId(goodsId);
        result.setProdGoodsRelList(prodGoodsRels);

        // 商品适用规则
        List<ProdGoodsSalesRuleResp> prodGoodsSalesRuleRespList = prodGoodsManagerHelper
            .appendProdGoodsSalesCondition(goodsId);
        result.setProdGoodsSaleRuleList(prodGoodsSalesRuleRespList);

        // 商品标签
        List<String> tagIds = prodTagsRelMapper.listTagIdByProdGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(tagIds)) {
            result.setTagIds(tagIds);

            // 调用content中心拿到tag的信息
            QryTagReq request = new QryTagReq();
            request.setTagIds(tagIds);
            ResultVO<ArrayList<TagDTO>> resultVO = contentService.qryTagListByCond(request);
            ResultVOCheckUtil.checkResultVO(resultVO);
            List<TagDTO> tagDTOS = resultVO.getData();
            List<TagResp> tagResps = new ArrayList<>();
            BeanUtils.copyProperties(tagDTOS, tagResps);
            result.setTagList(tagResps);
        }

        // 商品店铺
        if (StringUtils.isNotEmpty(result.getStoreId())) {
            Store store = storeMapper.selectById(result.getStoreId());
            if (store != null) {
                result.setStoreName(store.getStoreName());
            }
        }

        // 商品店铺目录
        TblStoreCatg storeCatg = storeCatgMapper.queryStoreCatByGoodsId(result.getGoodsId());
        if (storeCatg != null) {
            result.setStoreCatId(storeCatg.getCatId());
            result.setStoreCatName(storeCatg.getName());
        }

        // 商品属性值
        List<AttrValueResp> attrValueResps = prodGoodsAttrValueMapper.listAttrValueRespByProdGoodsId(goodsId);
        log.info("ProdGoodsManager listAttrValueRespByProdGoodsId attrValueResps= [{}]", attrValueResps);
        GoodsAttrThreeTypeResp goodsAttrThreeTypeResp = new GoodsAttrThreeTypeResp();
        // sku属性
        List<AttrValueResp> skuAttrValue = new ArrayList<>();
        // 私有属性
        List<AttrValueResp> privateAttrValue = new ArrayList<>();
        // 非sku非私有属性
        List<AttrValueResp> threeAttrValue = new ArrayList<>();
        prodGoodsManagerHelper.releaseForNoSku(attrValueResps, skuAttrValue, privateAttrValue, threeAttrValue);
        goodsAttrThreeTypeResp.setSkuGoodsAttr(skuAttrValue);
        goodsAttrThreeTypeResp.setPrivateGoodsAttr(privateAttrValue);
        goodsAttrThreeTypeResp.setThreeGoodsAttr(threeAttrValue);
        result.setGoodsAttr(goodsAttrThreeTypeResp);

        // 库存上下架
        List<ProdGoodsStockWaveResp> prodGoodsStockWaves = prodGoodsStockWaveMapper
            .listProdGoodsStockWaveByProdGoodsId(goodsId);
        result.setProdGoodsStockWaveList(prodGoodsStockWaves);

        // 商品内容
        ProdGoodsQueryContentReq prodGoodsQueryContentReq = new ProdGoodsQueryContentReq();
        prodGoodsQueryContentReq.setGoodsId(goodsId);
        List<QryGoodsContentListResp> goodsContent = qryGoodsContentList(prodGoodsQueryContentReq);
        if (CollectionUtils.isNotEmpty(goodsContent)) {
            for (QryGoodsContentListResp qryGoodsContentResp : goodsContent) {
                ContentBaseResponseDTO contentDeatil = contentService
                    .queryContentDeatil(Long.valueOf(qryGoodsContentResp.getContentId()));
                qryGoodsContentResp.setContentDeatil(contentDeatil);
            }
            result.setGoodsContent(goodsContent);
        }

        log.info("ProdGoodsManager queryProdGoodsDetailById end");
        return result;
    }

    public ArrayList<ProdGoods> queryProdGoodsListByIds(QryProdGoodsListReq qryProdGoodsListReq) throws BaseException {
        log.info("ProdGoodsManager queryProdGoodsListByIds start qryProdGoodsListReq = [{}]", qryProdGoodsListReq);
        ArrayList<ProdGoods> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(qryProdGoodsListReq.getGoodsIds())) {
            result = prodGoodsMapper.queryProdGoodsListByIds(qryProdGoodsListReq);
        }
        log.info("ProdGoodsManager queryProdGoodsListByIds end");
        return result;
    }

    public List<QryGoodsContentListResp> qryProdGoodsContentByIdAndType(ProdGoodsQueryContentReq queryContentReq)
        throws BaseException {
        log.info("ProdGoodsManager qryProdGoodsContentByIdAndType start queryContentReq = [{}]", queryContentReq);
        AssertUtil.isNotEmpty(queryContentReq.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        List<QryGoodsContentListResp> goodsContent = qryGoodsContentList(queryContentReq);
        if (CollectionUtils.isNotEmpty(goodsContent)) {
            for (QryGoodsContentListResp qryGoodsContentResp : goodsContent) {
                ContentBaseResponseDTO contentDeatil = contentService
                    .queryContentDeatil(Long.valueOf(qryGoodsContentResp.getContentId()));
                qryGoodsContentResp.setContentDeatil(contentDeatil);
            }
        }
        log.info("ProdGoodsManager qryProdGoodsContentByIdAndType end");
        return goodsContent;
    }

    /**
     * 查询商品名称和缩略图
     * 
     * @param goodsId 商品id
     * @return getProdGoodsNameAndThumbnailById
     */
    public ProdGoodsDetailByIdResp getProdGoodsNameAndThumbnailById(String goodsId) throws BaseException {
        log.info("ProdGoodsManager getProdGoodsNameAndThumbnailById start, goodsId = [{}]", goodsId);

        // 校验参数
        if (StringUtils.isEmpty(goodsId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        // 构造返回值
        ProdGoodsDetailByIdResp result = new ProdGoodsDetailByIdResp();
        BeanUtils.copyProperties(prodGoods, result);

        if (StringUtils.isNotEmpty(result.getThumbnail())) {
            String substring = result.getThumbnail().substring(result.getThumbnail().indexOf('/') + 1);
            result.setThumbnail(showUrl + result.getThumbnail() + "?" + FastdfsTokenUtil.getToken(substring));
        }

        log.info("ProdGoodsManager getProdGoodsNameAndThumbnailById end");
        return result;
    }

    public Page<ProdGoodsDTO> queryGoodsForPage(ProdGoodsQueryReq req) throws BaseException {
        log.info("ProdGoodsManager queryGoodsForPage start, ProdGoodsQueryReq = [{}]", req);

        Page<ProdGoodsDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ProdGoodsDTO> result = prodGoodsMapper.queryGoodsForPage(page, req);
        List<ProdGoodsDTO> prodGoodsDTOS = result.getRecords();
        if (CollectionUtils.isEmpty(prodGoodsDTOS)) {
            return result;
        }
        for (ProdGoodsDTO prodGoodsDTO : prodGoodsDTOS) {
            String thumbnail = prodGoodsDTO.getThumbnail();
            if (StringUtils.isNotEmpty(thumbnail)) {
                String substring = thumbnail.substring(thumbnail.indexOf('/') + 1);
                prodGoodsDTO.setShowUrl(showUrl + thumbnail + "?" + FastdfsTokenUtil.getToken(substring));
            }
            // 渠道名称
            List<String> contactChannelNameList = contactChannelMapper
                .qryContactChannelNameByGoodsId(prodGoodsDTO.getGoodsId());
            prodGoodsDTO.setContactChannelNameList(contactChannelNameList);
        }
        result.setRecords(prodGoodsDTOS);

        log.info("ProdGoodsManager queryGoodsForPage end");
        return result;
    }

    /**
     * 查询目标商品信息 searchKey 前端传过来是商品名称或者商品编码,写sql时注意
     * 
     * @param goodsId 商品id
     * @param req ProdGoodsQueryTargetReq
     * @return List<ProdGoodsDTO>
     */
    public Page<QryGoodsCatMemListResp> qryTargetGoodsList(String goodsId, ProdGoodsQueryTargetReq req)
        throws BaseException {
        log.info("ProdGoodsManager qryTargetGoodsList start, goodsId = [{}], ProdGoodsQueryTargetReq = [{}]", goodsId,
            req);

        AssertUtil.isNotNull(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        AssertUtil.isNotNull(req.getRelType(), OfferBaseMessageCodeEnum.PROD_GOODS_ID_NULL);
        // 根据商品id查找所有互斥关系的商品
        ProdGoodsRelReq prodGoodsRelReq = new ProdGoodsRelReq();
        prodGoodsRelReq.setAgoodsId(goodsId);
        prodGoodsRelReq.setRelType(req.getRelType());
        List<ProdGoods> prodGoodsList = prodGoodsRelMapper.qryExcludeProdGoods(prodGoodsRelReq);
        List<String> ids = new ArrayList<>();
        ids.add(goodsId);
        prodGoodsList.forEach(prodGoods -> ids.add(prodGoods.getGoodsId()));
        req.setIds(ids);
        Page<QryGoodsCatMemListResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<QryGoodsCatMemListResp> result = prodGoodsMapper.qryTargetGoodsList(page, req);

        log.info("ProdGoodsManager qryTargetGoodsList end");
        return result;
    }

    public List<ProdGoodsDTO> queryGoodsList(ProdGoodsQueryReq req) {
        log.info("ProdGoodsManager queryGoodsList start, ProdGoodsQueryReq = [{}]", req);
        List<ProdGoodsDTO> result = prodGoodsMapper.queryGoodsList(req);

        if (null == result) {
            result = new ArrayList<>();
        }
        log.info("ProdGoodsManager queryGoodsList end, result = [{}]", result);
        return result;
    }


    public List<ProdGoodsDTO> queryGoodsListNoTenantId(ProdGoodsQueryReq req) {
        log.info("ProdGoodsManager queryGoodsListNoTenantId start, ProdGoodsQueryReq = [{}]", req);

        List<ProdGoodsDTO> result = prodGoodsMapper.queryGoodsListNoTenantId(req);
        if (null == result) {
            result = new ArrayList<>();
        }

        log.info("ProdGoodsManager queryGoodsList end, result = [{}]", result);
        return result;
    }

    public int goodsAuditRequest(String goodsId, String state) throws BaseException {
        log.info("ProdGoodsManager goodsAuditRequest start, goodsId = [{}], state = [{}]", goodsId, state);

        // 校验商品，商品不存在和失效商品无法审核
        ProdGoods prodGoods = prodGoodsManagerHelper.checkGoodState(goodsId);
        String productCode = prodGoods.getProductCode();

        if (StringUtils.isEmpty(productCode)) {
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_CODE_IS_NULL);
        }

        if (null != prodGoods.getStockQty()) {
            checkGoodsQty(prodGoods, true);
        }

        prodGoods.setState(state);
        prodGoods.setModifyBy(HttpSessionUtil.get().getUserId().toString());
        prodGoods.setModifyTime(DBDateUtil.getCurrentDBDateTime());

        prodGoodsMapper.updateById(prodGoods);

        log.info("ProdGoodsManager goodsAuditRequest end");
        return 1;
    }

    public int goodsUnpublish(String goodsId, PublishGoodReq req) throws BaseException {
        log.info("ProdGoodsManager goodsUnpublish start, goodsId = [{}]", goodsId);

        // 校验商品，商品不存在和失效商品无法取消发布
        prodGoodsManagerHelper.checkGoodState(goodsId);
        UserDTO tmpUserDTO = req.getUserDTO();
        if (null == tmpUserDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
        }
        int result = prodGoodsMapper.goodsUnpublish(goodsId, tmpUserDTO.getUserId().toString());

        // 清空缓存
        commonManager.cacheClear(goodsId);
        log.info("ProdGoodsManager goodsUnpublish end");
        return result;
    }

    /**
     * 新增商品
     * 
     * @param request AddProdGoodsReq
     * @return AddProdGoodsResp
     */
    public AddProdGoodsResp addProdGoods(AddProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsManager addProdGoods start, AddProdGoodsReq = [{}]", request);

        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotNull(request.getUserDTO(), OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
        AssertUtil.isNotEmpty(request.getName(), OfferBaseMessageCodeEnum.GOODS_NAME_IS_NULL);
        AssertUtil.isNotEmpty(request.getProductCode(), OfferBaseMessageCodeEnum.PRODUCT_CODE_IS_NULL);
        AssertUtil.isNotEmpty(request.getTypeId(), OfferBaseMessageCodeEnum.GOODS_TYPE_IS_NULL);
        AssertUtil.isNotEmpty(request.getSaleCatId(), OfferBaseMessageCodeEnum.SALE_CAT_IS_NULL);
        AssertUtil.isNotNull(request.getIsCertification(), OfferBaseMessageCodeEnum.IS_CERTIFICATION_IS_NULL);
        // 校验商品编码
        prodGoodsManagerHelper.checkGoodsSn(request.getSn(), null);
        // 校验商品品牌
        prodGoodsManagerHelper.checkGoodsBrand(request.getBrandId());
        // 新增商品
        ProdGoods prodGoods = new ProdGoods();
        BeanUtils.copyProperties(request, prodGoods);

        // 同步实名认证
        if (request.getIsCertification() > 0) {
            prodGoods.setIsCertification(ProdGoodsStateDef.IS_CERTIFICATION_Y);
        }
        else {
            prodGoods.setIsCertification(ProdGoodsStateDef.IS_CERTIFICATION_N);
        }
        String goodsId = UidGeneator.getUIDStr();
        prodGoods.setGoodsId(goodsId);
        prodGoods.setState(ProdGoodsStateDef.CREATED);
        prodGoods.setThumbnail(request.getThumbnail());
        prodGoods.setTurnOnSku(request.getTurnOnSku());

        String userId = request.getUserDTO().getUserId().toString();
        prodGoods.setCreateBy(userId);
        prodGoods.setModifyBy(userId);

        Date now = DBDateUtil.getCurrentDBDateTime();
        prodGoods.setCreateTime(now);
        prodGoods.setModifyTime(now);
        // 校验基础库存和波次库存总和与产品库存
        checkGoodsQty(prodGoods, false);
        prodGoodsMapper.insert(prodGoods);

        // 商品关系
        List<ProdGoodsRelReq> prodGoodsRelList = request.getProdGoodsRelList();
        if (CollectionUtils.isNotEmpty(prodGoodsRelList)) {
            for (ProdGoodsRelReq prodGoodsRelReq : prodGoodsRelList) {
                prodGoodsRelReq.setAgoodsId(goodsId);
                prodGoodsRelManager.updateProdGoodsRel(prodGoodsRelReq);
            }
        }

        // 商品适用规则
        List<ProdGoodsSalesRuleResp> prodGoodsSaleRuleList = request.getProdGoodsSaleRuleList();
        if (CollectionUtils.isNotEmpty(prodGoodsSaleRuleList)) {
            for (ProdGoodsSalesRuleResp prodGoodsSalesRuleResp : prodGoodsSaleRuleList) {
                Integer salesRuleId = prodGoodsSalesRuleResp.getSalesRuleId();
                UpdateProdGoodsSalesConditionReq updateProdGoodsSalesConditionReq = new UpdateProdGoodsSalesConditionReq();
                updateProdGoodsSalesConditionReq.setSalesRuleId(salesRuleId);
                updateProdGoodsSalesConditionReq.setObjIds(prodGoodsSalesRuleResp.getObjIdList());
                prodGoodsSalesConditionManager.update(goodsId, updateProdGoodsSalesConditionReq);
            }
        }

        // 商品属性
        prodGoodsManagerHelper.buildGoodsAttrList(request.getTurnOnSku(), request.getSaleCatId(), goodsId,
            request.getProdGoodsAttrValueList());

        // 商品内容 此时offer中心调用content中心新增内容返回contentId
        List<ProdGoodsContentReq> prodGoodsContentList = request.getProdGoodsContentList();
        if (CollectionUtils.isNotEmpty(prodGoodsContentList)) {
            for (ProdGoodsContentReq prodGoodsContent : prodGoodsContentList) {
                ContentAddReq contentAdd = prodGoodsContent.getContentAdd();
                contentAdd.getContentBase().setCreateBy(userId);
                ContentBaseDTO contentBaseDTO = contentService.addContentBase(contentAdd);
                AssertUtil.isNotNull(contentBaseDTO, OfferBaseMessageCodeEnum.GOODS_CONTENT_NOT_EXIST);
                AddGoodsContentReq addGoodsContentReq = new AddGoodsContentReq();
                addGoodsContentReq.setContentId(contentBaseDTO.getContentId().toString());
                addGoodsContentReq.setDeviceType(prodGoodsContent.getDeviceType());
                addGoodsContentReq.setGoodsContentType(prodGoodsContent.getGoodsContentType());
                addGoodsContentReq.setGoodsId(goodsId);
                addGoodsContentReq.setUserId(userId);
                prodGoodsContentManager.addGoodsContent(addGoodsContentReq);
            }
        }

        // 销售目录
        ProdGoodsCatMem saleGoodsCatMem = new ProdGoodsCatMem();
        saleGoodsCatMem.setModifyBy(userId);
        saleGoodsCatMem.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        saleGoodsCatMem.setState(CommonStateDef.ACTIVE);
        saleGoodsCatMem.setCatId(request.getSaleCatId());
        saleGoodsCatMem.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        saleGoodsCatMem.setCreateBy(userId);
        saleGoodsCatMem.setCatMemId(UidGeneator.getUIDStr());
        saleGoodsCatMem.setGoodsId(prodGoods.getGoodsId());
        prodGoodsCatMemMapper.insert(saleGoodsCatMem);

        // 商品店铺
        storeCatgMemMapper.deleteStoreCatgMemByGoodsId(goodsId, userId);

        if (StringUtils.isNotEmpty(request.getStoreCatId())) {
            TblStoreCatgMem storeCatgMem = new TblStoreCatgMem();
            storeCatgMem.setCatId(request.getStoreCatId());
            storeCatgMem.setCatMemId(UidGeneator.getUIDStr());
            storeCatgMem.setGoodsId(prodGoods.getGoodsId());
            storeCatgMem.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            storeCatgMem.setState(CommonStateDef.ACTIVE);
            storeCatgMem.setCreateBy(userId);
            storeCatgMem.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            storeCatgMem.setModifyBy(userId);
            storeCatgMemMapper.insert(storeCatgMem);
        }

        if (CollectionUtils.isNotEmpty(request.getTagList())) {
            for (TagResp tag : request.getTagList()) {
                ProdTagsRel prodTagsRel = new ProdTagsRel();
                prodTagsRel.setProdTagId(UidGeneator.getUIDStr());
                prodTagsRel.setCreateBy(userId);
                prodTagsRel.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setGoodsId(prodGoods.getGoodsId());
                prodTagsRel.setTagId(tag.getTagId());
                prodTagsRel.setModifyBy(userId);
                prodTagsRel.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setState(CommonStateDef.ACTIVE);

                prodTagsRelMapper.insert(prodTagsRel);
            }
        }

        AddProdGoodsResp response = new AddProdGoodsResp();
        response.setGoodsId(goodsId);

        log.info("ProdGoodsManager addProdGoods end");
        return response;
    }

    /**
     * 修改商品
     * 
     * @param goodsId String
     * @param request ModProdGoodsReq
     */
    public void modProdGoods(String goodsId, ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsManager modProdGoods start, goodsId = [{}], ModProdGoodsReq = [{}]", goodsId, request);

        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        AssertUtil.isNotEmpty(request.getName(), OfferBaseMessageCodeEnum.GOODS_NAME_IS_NULL);
        AssertUtil.isNotEmpty(request.getProductCode(), OfferBaseMessageCodeEnum.PRODUCT_CODE_IS_NULL);
        AssertUtil.isNotEmpty(request.getTypeId(), OfferBaseMessageCodeEnum.GOODS_TYPE_IS_NULL);
        AssertUtil.isNotEmpty(request.getSaleCatId(), OfferBaseMessageCodeEnum.SALE_CAT_IS_NULL);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);

        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        // 校验货架期和波次上架时间
        prodGoodsManagerHelper.releaseForProdGoods(request, prodGoods);

        // 校验商品品牌
        prodGoodsManagerHelper.checkGoodsBrand(request.getBrandId());

        prodGoods.setStockQty(request.getStockQty());
        // 校验基础库存和波次库存总和与产品库存
        checkGoodsQty(prodGoods, false);
        prodGoodsManagerHelper.checkGoodsSn(request.getSn(), prodGoods.getSn());
        BeanUtils.copyProperties(request, prodGoods);
        prodGoods.setModifyTime(DBDateUtil.getCurrentDBDateTime());

        UserDTO tmpUserDTO = request.getUserDTO();
        if (null == tmpUserDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
        }
        String userId = tmpUserDTO.getUserId().toString();
        prodGoods.setModifyBy(userId);
        prodGoods.setState(ProdGoodsStateDef.CREATED);
        prodGoodsMapper.updateProdGoodsWithOutBuyCount(prodGoods);

        // 后台现在编辑商品不能修改销售目录
        // prodGoodsManagerHelper.modSaleGoodsCat(goodsId, request);
        prodGoodsManagerHelper.modGoodTag(goodsId, request);

        // 商品店铺目录
        prodGoodsManagerHelper.modStoreCat(goodsId, request);

        // 商品属性
        prodGoodsManagerHelper.buildGoodsAttrList(request.getTurnOnSku(), request.getSaleCatId(), goodsId,
            request.getProdGoodsAttrValueList());

        // 商品销售规则
        List<ProdGoodsSalesRuleResp> prodGoodsSaleRuleList = request.getProdGoodsSaleRuleList();
        if (CollectionUtils.isNotEmpty(prodGoodsSaleRuleList)) {
            for (ProdGoodsSalesRuleResp prodGoodsSalesRuleResp : prodGoodsSaleRuleList) {
                Integer salesRuleId = prodGoodsSalesRuleResp.getSalesRuleId();
                UpdateProdGoodsSalesConditionReq updateProdGoodsSalesConditionReq = new UpdateProdGoodsSalesConditionReq();
                updateProdGoodsSalesConditionReq.setObjIds(prodGoodsSalesRuleResp.getObjIdList());
                updateProdGoodsSalesConditionReq.setSalesRuleId(salesRuleId);
                prodGoodsSalesConditionManager.update(goodsId, updateProdGoodsSalesConditionReq);
            }
        }

        // 商品关系
        List<ProdGoodsRelReq> prodGoodsRelList = request.getProdGoodsRelList();
        if (CollectionUtils.isNotEmpty(prodGoodsRelList)) {
            for (ProdGoodsRelReq prodGoodsRelReq : prodGoodsRelList) {
                prodGoodsRelReq.setAgoodsId(goodsId);
                prodGoodsRelManager.updateProdGoodsRel(prodGoodsRelReq);
            }
        }

        // 商品上下架
        List<ProdGoodsStockWaveReq> prodGoodsStockWaveList = request.getProdGoodsStockWaveList();
        prodGoodsStockWaveManager.updateStockWaveList(goodsId, prodGoodsStockWaveList);

        // 商品标签
        String modifyBy = prodGoodsManagerHelper.getUerId();
        prodTagsRelMapper.deleteByGoodsId(goodsId, modifyBy);
        List<String> tagIds = request.getTagIds();
        if (CollectionUtils.isNotEmpty(tagIds)) {
            for (String tagId : tagIds) {
                ProdTagsRel prodTagsRel = new ProdTagsRel();
                prodTagsRel.setGoodsId(goodsId);
                prodTagsRel.setProdTagId(UidGeneator.getUIDStr());
                prodTagsRel.setTagId(Long.valueOf(tagId));
                prodTagsRel.setCreateBy(modifyBy);
                prodTagsRel.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setModifyBy(modifyBy);
                prodTagsRel.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setState(CommonStateDef.ACTIVE);
                prodTagsRelMapper.insert(prodTagsRel);
            }
        }

        // 商品内容 此时offer中心调用content中心新增内容返回contentId
        List<ProdGoodsContentReq> prodGoodsContentList = request.getProdGoodsContentList();
        if (CollectionUtils.isNotEmpty(prodGoodsContentList)) {
            for (ProdGoodsContentReq prodGoodsContent : prodGoodsContentList) {
                ContentAddReq contentAdd = prodGoodsContent.getContentAdd();
                contentAdd.getContentBase().setCreateBy(userId);
                contentAdd.getContentBase().setModifyBy(userId);
                ContentBaseDTO contentBaseDTO = contentService.addContentBase(contentAdd);
                AssertUtil.isNotNull(contentBaseDTO, OfferBaseMessageCodeEnum.GOODS_CONTENT_NOT_EXIST);
                AddGoodsContentReq addGoodsContentReq = new AddGoodsContentReq();
                addGoodsContentReq.setDeviceType(prodGoodsContent.getDeviceType());
                addGoodsContentReq.setGoodsId(goodsId);
                addGoodsContentReq.setGoodsContentType(prodGoodsContent.getGoodsContentType());
                addGoodsContentReq.setUserId(userId);
                addGoodsContentReq.setContentId(contentBaseDTO.getContentId().toString());
                prodGoodsContentManager.addGoodsContent(addGoodsContentReq);
            }
        }

        // 清空缓存
        commonManager.cacheClear(goodsId);
        log.info("ProdGoodsManager modProdGoods end");
    }

    /**
     * 修改商品运营信息
     * 
     * @param goodsId String
     * @param request ModProdGoodsReq
     */
    public void modProdGoodsOperationInfo(String goodsId, ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsManager modProdGoodsOperationInfo start, goodsId = [{}], ModProdGoodsReq = [{}]", goodsId,
            request);

        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        AssertUtil.isNotNull(prodGoods, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        prodGoods.setModifyTime(DBDateUtil.getCurrentDBDateTime());

        String userId = prodGoodsManagerHelper.getUerId();
        prodGoods.setModifyBy(userId);
        prodGoods.setSord(request.getSord());
        prodGoods.setAdjBuyCount(request.getAdjBuyCount());
        prodGoods.setIsRecommend(request.getIsRecommend());
        prodGoods.setEvaluateAuditMode(request.getEvaluateAuditMode());
        prodGoods.setSearchKey(request.getSearchKey());
        prodGoodsMapper.updateProdGoodsWithOutBuyCount(prodGoods);

        // 商品标签
        prodTagsRelMapper.deleteByGoodsId(goodsId, userId);
        List<String> tagIds = request.getTagIds();
        if (CollectionUtils.isNotEmpty(tagIds)) {
            for (String tagId : tagIds) {
                ProdTagsRel prodTagsRel = new ProdTagsRel();
                prodTagsRel.setProdTagId(UidGeneator.getUIDStr());
                prodTagsRel.setGoodsId(goodsId);
                prodTagsRel.setTagId(Long.valueOf(tagId));
                prodTagsRel.setState(CommonStateDef.ACTIVE);
                prodTagsRel.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setCreateBy(userId);
                prodTagsRel.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setModifyBy(userId);
                prodTagsRelMapper.insert(prodTagsRel);
            }
        }

        // 清空缓存
        commonManager.cacheClear(goodsId);
        log.info("ProdGoodsManager modProdGoodsOperationInfo end");
    }

    /**
     * 修改商品库存信息
     * 
     * @param goodsId String
     * @param request ModProdGoodsReq
     */
    public void modProdGoodsStockInfo(String goodsId, ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsManager modProdGoodsStockInfo start, goodsId = [{}], ModProdGoodsReq = [{}]", goodsId,
            request);

        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);

        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        // 校验货架期和波次上架时间
        List<ProdGoodsStockWaveResp> prodGoodsStockWaveResps = prodGoodsStockWaveMapper
            .qryActiveStockWaveListByProdGoodsId(prodGoods.getGoodsId());
        boolean exists = CollectionUtils.isNotEmpty(prodGoodsStockWaveResps)
            && (prodGoodsStockWaveResps.get(0).getStockInDate().before(request.getMarketingBeginTime())
                || prodGoodsStockWaveResps.get(prodGoodsStockWaveResps.size() - 1).getStockInDate()
                    .after(request.getMarketingEndTime())
                || prodGoodsStockWaveResps.get(prodGoodsStockWaveResps.size() - 1).getStockInDate()
                    .equals(request.getMarketingEndTime()));
        if (exists) {
            throw new BaseException(OfferBaseMessageCodeEnum.GOOD_STOCK_IN_DATE_OUT_ERROR);

        }

        prodGoods.setStockQty(request.getStockQty());
        prodGoods.setMarketingBeginTime(request.getMarketingBeginTime());
        prodGoods.setMarketingEndTime(request.getMarketingEndTime());
        // 校验基础库存和波次库存总和与产品库存
        checkGoodsQty(prodGoods, false);
        prodGoods.setModifyTime(DBDateUtil.getCurrentDBDateTime());

        String userId = prodGoodsManagerHelper.getUerId();
        prodGoods.setModifyBy(userId);
        prodGoodsMapper.updateProdGoodsWithOutBuyCount(prodGoods);

        // 商品上下架
        List<ProdGoodsStockWaveReq> prodGoodsStockWaveList = request.getProdGoodsStockWaveList();
        prodGoodsStockWaveManager.updateStockWaveList(goodsId, prodGoodsStockWaveList);

        // 清空缓存
        commonManager.cacheClear(goodsId);
        log.info("ProdGoodsManager modProdGoodsStockInfo end");
    }

    public int checkGoodsQty(ProdGoods prodGoods, boolean sumWave) throws BaseException {
        log.info("ProdGoodsManager checkGoodsQty start, prodGoods = [{}], sumWave = [{}]", prodGoods, sumWave);
        return prodGoodsManagerHelper.checkGoodsQty(prodGoods, sumWave);
    }

    public void delProdGoods(String goodsId, Long userId) throws BaseException {
        log.info("ProdGoodsManager delProdGoods start, goodsId = [{}], userId=[{}]", goodsId, userId);

        AssertUtil.isNotEmpty(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);

        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        String modifyBy = "";
        if (null == userId) {
            modifyBy = prodGoodsManagerHelper.getUerId();
        }
        else {
            modifyBy = userId.toString();
        }
        //删除商品关联的标签
        prodTagsRelMapper.deleteByGoodsId(goodsId, modifyBy);
        prodGoodsMapper.deleteProdGoods(goodsId, modifyBy);
        // 清空缓存
        commonManager.cacheClear(goodsId);

        log.info("ProdGoodsManager delProdGoods end");
    }

    public void batchDelProdGoods(String goodsIds, Long userId, HttpServletRequest httpServletRequest)
        throws BaseException {
        log.info("ProdGoodsManager batchDelProdGoods start, goodsId = [{}]", goodsIds);

        AssertUtil.isNotEmpty(goodsIds, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        String[] goodsIdArray = goodsIds.split(",");

        if (goodsIdArray.length == 0) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        List<OperObjDetail> operObjDetailList = new ArrayList<>();

        for (String goodsId : goodsIdArray) {
            ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);

            if (null == prodGoods) {
                log.info("The goods is not exist, goodsId = {}", goodsId);
                throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
            }
            // 清空缓存
            commonManager.cacheClear(goodsId);

            ProdGoods newProdGoods = new ProdGoods();
            BeanUtils.copyProperties(prodGoods, newProdGoods);
            newProdGoods.setState(ProdGoodsStateDef.INACTIVE);
            newProdGoods.setModifyBy(userId.toString());
            newProdGoods.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            List<OperObjDetail> operObjDetails = OperLogUtil.getOperInfoList(ProdGoods.class, newProdGoods, prodGoods);
            operObjDetailList.addAll(operObjDetails);
        }

        prodGoodsMapper.batchDeleteProdGoods(goodsIdArray, userId.toString());

        RecordOperLogObj recordOperLogObj = new RecordOperLogObj();
        recordOperLogObj.setRequestId(UidGeneator.getUIDStr());
        OperObjLog operObjLog = new OperObjLog();
        operObjLog.setOperEvent(OperLogEventDef.OPER_EVENT_BATCH_DEL_GOODS);
        operObjLog.setOperType(OperTypeDef.DELETE);
        operObjLog.setCreateDate(DateUtil.formatString(DBDateUtil.getCurrentDBDateTime(), DATE_FORMAT_TIME));
        operObjLog.setIpAddr(IPUtil.getClientIpAddr(httpServletRequest));
        recordOperLogObj.setOperDetailList(operObjDetailList);
        operObjLog.setUserId(userId);
        recordOperLogObj.setOperLog(operObjLog);
        MessageProducer.sendMessage(MessageDef.ORDER_LOG_SYNC_EXCHANGE, MessageDef.OSCAR_MALL_OPER_LOG_TOPIC,
            recordOperLogObj.getRequestId(), recordOperLogObj);

        log.info("ProdGoodsManager batchDelProdGoods end");
    }

    public void batchPutOnProdGoods(String goodsIds, Long userId, HttpServletRequest httpServletRequest)
        throws BaseException {
        log.info("ProdGoodsManager batchPutOnProdGoods start, goodsId = [{}]", goodsIds);

        AssertUtil.isNotEmpty(goodsIds, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        String[] goodsIdArray = goodsIds.split(",");

        if (goodsIdArray.length == 0) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        List<OperObjDetail> operObjDetailList = new ArrayList<>();

        List<ProdGoods> forRedisGoodsList = new ArrayList<>();
        for (String goodsId : goodsIdArray) {

            ProdGoods prodGoods = prodGoodsManagerHelper.checkGoodState(goodsId);
            String productCode = prodGoods.getProductCode();
            if (StringUtils.isEmpty(productCode)) {
                throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_CODE_IS_NULL);
            }

            if (null != prodGoods.getStockQty()) {
                checkGoodsQty(prodGoods, true);
            }

            ProdGoods newProdGoods = new ProdGoods();
            BeanUtils.copyProperties(prodGoods, newProdGoods);
            newProdGoods.setState(ProdGoodsStateDef.PUT_ON_SALE);
            newProdGoods.setModifyBy(HttpSessionUtil.get().getUserId().toString());
            newProdGoods.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            List<OperObjDetail> operObjDetails = OperLogUtil.getOperInfoList(ProdGoods.class, newProdGoods, prodGoods);
            operObjDetailList.addAll(operObjDetails);
            forRedisGoodsList.add(newProdGoods);
        }

        prodGoodsMapper.batchPutOnProdGoods(goodsIdArray, HttpSessionUtil.get().getUserId().toString());

        for (int i = 0; i < forRedisGoodsList.size(); i++) {
            ProdGoodsRedisDto prodGoodsRedisDto = new ProdGoodsRedisDto();
            BeanUtils.copyProperties(forRedisGoodsList.get(i), prodGoodsRedisDto);
            redisCache.set(CacheKeyDef.GOODS, prodGoodsRedisDto.getSn(), prodGoodsRedisDto);

            Long goodsQty = forRedisGoodsList.get(i).getStockQty();
            if (null == goodsQty) {
                goodsQty = 0L;
            }
            log.info("ProdGoodsManager prodGoodsRedisDto start，prodGoodsRedisDto=[{}]，goodsQty = [{}]",
                prodGoodsRedisDto, goodsQty);

            log.info("ProdGoodsManager prodGoodsRedisDto start，GOODS_QTY=[{}]", CacheKeyDef.GOODS_QTY);
            log.info("ProdGoodsManager prodGoodsRedisDto start，sn=[{}]", prodGoodsRedisDto.getSn());

            log.info("ProdGoodsManager prodGoodsRedisDto start，prodGoodsRedisDto=[{}]，goodsQty = [{}]",
                prodGoodsRedisDto, goodsQty);

            redisCache.setNumber(CacheKeyDef.GOODS_QTY, prodGoodsRedisDto.getSn(), goodsQty.toString());

            log.info("ProdGoodsManager  redisCache  start，GOODS_QTY=[{}]", goodsQty);

            commonManager.cacheClear(forRedisGoodsList.get(i).getGoodsId());
        }

        RecordOperLogObj recordOperLogObj = new RecordOperLogObj();
        recordOperLogObj.setRequestId(UidGeneator.getUIDStr());
        OperObjLog operObjLog = new OperObjLog();
        operObjLog.setOperEvent(OperLogEventDef.OPER_EVENT_BATCH_PUT_ON_GOODS);
        operObjLog.setOperType(OperTypeDef.UPDATE);
        operObjLog.setUserId(userId);
        operObjLog.setIpAddr(IPUtil.getClientIpAddr(httpServletRequest));
        operObjLog.setCreateDate(DateUtil.formatString(DBDateUtil.getCurrentDBDateTime(), DATE_FORMAT_TIME));

        recordOperLogObj.setOperLog(operObjLog);
        recordOperLogObj.setOperDetailList(operObjDetailList);

        MessageProducer.sendMessage(MessageDef.ORDER_LOG_SYNC_EXCHANGE, MessageDef.OSCAR_MALL_OPER_LOG_TOPIC,
            recordOperLogObj.getRequestId(), recordOperLogObj);

        log.info("ProdGoodsManager batchPutOnProdGoods end");
    }

    public void batchPullOffProdGoods(String goodsIds, Long userId, HttpServletRequest httpServletRequest)
        throws BaseException {
        log.info("ProdGoodsManager batchPullOffProdGoods start, goodsId = [{}]", goodsIds);

        AssertUtil.isNotEmpty(goodsIds, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        String[] goodsIdArray = goodsIds.split(",");

        if (goodsIdArray.length == 0) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        List<OperObjDetail> operObjDetailList = new ArrayList<>();

        for (String goodsId : goodsIdArray) {
            ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);

            if (null == prodGoods) {
                throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
            }

            ProdGoods newProdGoods = new ProdGoods();
            BeanUtils.copyProperties(prodGoods, newProdGoods);
            newProdGoods.setState(ProdGoodsStateDef.PULL_OFF_SALE);
            newProdGoods.setModifyBy(HttpSessionUtil.get().getUserId().toString());
            newProdGoods.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            List<OperObjDetail> operObjDetails = OperLogUtil.getOperInfoList(ProdGoods.class, newProdGoods, prodGoods);
            operObjDetailList.addAll(operObjDetails);
            commonManager.cacheClear(goodsId);
        }

        prodGoodsMapper.batchPullOffProdGoods(goodsIdArray, HttpSessionUtil.get().getUserId().toString());

        RecordOperLogObj recordOperLogObj = new RecordOperLogObj();
        OperObjLog operObjLog = new OperObjLog();
        recordOperLogObj.setRequestId(UidGeneator.getUIDStr());
        operObjLog.setCreateDate(DateUtil.formatString(DBDateUtil.getCurrentDBDateTime(), DATE_FORMAT_TIME));
        operObjLog.setOperEvent(OperLogEventDef.OPER_EVENT_BATCH_PULL_OFF_GOODS);
        operObjLog.setOperType(OperTypeDef.UPDATE);
        operObjLog.setUserId(userId);
        recordOperLogObj.setOperDetailList(operObjDetailList);
        operObjLog.setIpAddr(IPUtil.getClientIpAddr(httpServletRequest));
        recordOperLogObj.setOperLog(operObjLog);
        MessageProducer.sendMessage(MessageDef.ORDER_LOG_SYNC_EXCHANGE, MessageDef.OSCAR_MALL_OPER_LOG_TOPIC,
            recordOperLogObj.getRequestId(), recordOperLogObj);

        log.info("ProdGoodsManager batchPullOffProdGoods end");
    }

    public void modGoodsTextInfo(ModGoodsTextInfoReq request) throws BaseException {
        log.info("ProdGoodsManager modGoodsTextInfo start, ModGoodsTextInfoReq = [{}]", request);
        prodGoodsManagerHelper.modGoodsTextInfo(request);
        log.info("ProdGoodsManager modGoodsTextInfo end");
    }

    public void modGoodsContent(ModGoodsContentReq request) throws BaseException {
        log.info("ProdGoodsManager modGoodsContent start, ModGoodsContentReq = [{}]", request);
        prodGoodsManagerHelper.modGoodsContent(request);
        log.info("ProdGoodsManager modGoodsContent end");
    }

    /**
     * 查询text
     * 
     * @param goodsId 商品id
     * @return QryGoodsTextInfoResp
     */
    public QryGoodsTextInfoResp queryGoodsTextInfo(String goodsId) throws BaseException {
        log.info("ProdGoodsManager queryGoodsTextInfo start, goodsId = [{}]", goodsId);
        QryGoodsTextInfoResp result = new QryGoodsTextInfoResp();
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        if (StringUtils.isNotEmpty(prodGoods.getAfterSale())) {
            result.setAfterSale(FastdfsTokenUtil.repairContent(prodGoods.getAfterSale()));
        }
        if (StringUtils.isNotEmpty(prodGoods.getIntro())) {
            result.setIntro(FastdfsTokenUtil.repairContent(prodGoods.getIntro()));
        }
        log.info("ProdGoodsManager queryGoodsTextInfo end");
        return result;
    }

    public ProdGoodsInfoResp queryProdGoodsById(String goodsId) throws BaseException {
        log.info("ProdGoodsManager queryProdGoodsById start, goodsId = [{}]", goodsId);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        if (null == prodGoods) {
            return null;
        }
        String storeId = prodGoods.getStoreId();
        AssertUtil.isNotEmpty(storeId, OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);

        ProdGoodsInfoResp prodGoodsInfoResp = new ProdGoodsInfoResp();
        BeanUtils.copyProperties(prodGoods, prodGoodsInfoResp);

        // 补充机构id
        String orgId = storeMapper.queryOrgIdByStoreId(storeId);
        AssertUtil.isNotEmpty(orgId, OfferBaseMessageCodeEnum.ORG_ID_IS_NULL);
        prodGoodsInfoResp.setOrgId(orgId);

        ProdGoodsCat saleCat = prodGoodsCatMapper.qrySaleCatNonCampaignByGoodsId(prodGoods.getGoodsId());
        log.info("ProdGoodsManager qrySaleCatNonCampaignByGoodsId saleCat = [{}]", saleCat);
        if (null != saleCat) {
            prodGoodsInfoResp.setSaleCatId(saleCat.getCatId());

            ProdGoodsCat mgnCat = prodGoodsCatMapper.qryMgnCatBySaleCatId(saleCat.getCatId());
            if (null != mgnCat) {
                prodGoodsInfoResp.setInvoiceFlag(mgnCat.getInvoiceFlag());
                prodGoodsInfoResp.setMgnCatId(mgnCat.getCatId());
            }
        }

        log.info("ProdGoodsManager queryProdGoodsById end, prodGoodsInfoResp = [{}]", prodGoodsInfoResp);
        return prodGoodsInfoResp;
    }

    public ProdGoods queryProdGoodsAllStateById(String goodsId) {
        log.info("ProdGoodsManager queryProdGoodsAllStateById start, goodsId = [{}]", goodsId);

        ProdGoods prodGoods = prodGoodsMapper.selectById(goodsId);

        log.info("ProdGoodsManager queryProdGoodsAllStateById end");
        return prodGoods;
    }

    public List<ProdGoodsSalesConditionResp> listProdGoodsSalesConditionByGoodsId(String goodsId) {
        log.info("ProdGoodsManager listProdGoodsSalesConditionByGoodsId start, goodsId = [{}]", goodsId);

        List<ProdGoodsSalesConditionResp> response = prodGoodsSalesConditionMapper
            .listProdGoodsSalesConditionByGoodsId(goodsId);

        if (null == response) {
            response = new ArrayList<>();
        }
        log.info("ProdGoodsManager listProdGoodsSalesConditionByGoodsId end");
        return response;
    }

    public List<ProdGoodsRelResp> listProdGoodsRelByProdGoodsId(String goodsId) throws BaseException {
        log.info("ProdGoodsManager listProdGoodsRelByProdGoodsId start, goodsId = [{}]", goodsId);

        List<ProdGoodsRelResp> prodGoodsRels = prodGoodsRelMapper.listProdGoodsRelByProdGoodsId(goodsId);

        if (null == prodGoodsRels) {
            prodGoodsRels = new ArrayList<>();
        }

        log.info("ProdGoodsManager listProdGoodsRelByProdGoodsId end");
        return prodGoodsRels;
    }

    public void addProdBuyCount(List<AddProdBuyCountReq> request) throws BaseException {
        log.info("ProdGoodsManager addProdBuyCount start, List<AddProdBuyCountReq> = [{}]", request);

        if (CollectionUtils.isEmpty(request)) {
            return;
        }
        for (AddProdBuyCountReq addProdBuyCountReq : request) {
            AssertUtil.isNotEmpty(addProdBuyCountReq.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
            AssertUtil.isNotNull(addProdBuyCountReq.getQty(), OfferBaseMessageCodeEnum.GOODS_QTY_IS_NULL);
            // 清空缓存
            commonManager.cacheClear(addProdBuyCountReq.getGoodsId());
        }

        int count = prodGoodsMapper.addProdBuyCount(request);

        if (count != request.size()) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        log.info("ProdGoodsManager addProdBuyCount end");
    }

    public void reduceProdBuyCount(List<AddProdBuyCountReq> request) throws BaseException {
        log.info("ProdGoodsManager reduceProdBuyCount start, List<AddProdBuyCountReq> = [{}]", request);

        if (CollectionUtils.isEmpty(request)) {
            return;
        }
        for (AddProdBuyCountReq addProdBuyCountReq : request) {
            AssertUtil.isNotEmpty(addProdBuyCountReq.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
            AssertUtil.isNotNull(addProdBuyCountReq.getQty(), OfferBaseMessageCodeEnum.GOODS_QTY_IS_NULL);
            // 清空缓存
            commonManager.cacheClear(addProdBuyCountReq.getGoodsId());
        }

        int count = prodGoodsMapper.reduceProdBuyCount(request);

        if (count != request.size()) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        log.info("ProdGoodsManager reduceProdBuyCount end");

    }

    public List<QryGoodsContentListResp> qryGoodsContentList(ProdGoodsQueryContentReq req) throws BaseException {
        log.info("ProdGoodsManager qryGoodsContentList start, List<QryGoodsContentListResp> = [{}]", req);

        AssertUtil.isNotEmpty(req.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        List<ProdGoodsContent> prodGoodsContents = prodGoodsContentMapper.selectGoodsContentByGoodsId(req);
        List<QryGoodsContentListResp> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(prodGoodsContents)) {
            prodGoodsContents.forEach(prodGoodsContent -> {
                QryGoodsContentListResp resp = new QryGoodsContentListResp();
                BeanUtils.copyProperties(prodGoodsContent, resp);
                result.add(resp);
            });
        }

        log.info("ProdGoodsManager qryGoodsContentList end");
        return result;
    }

    public List<ProdGoodsDTO> querySaleableGoodsList() {
        log.info("ProdGoodsManager queryPublishedGoodsList start");

        List<ProdGoodsDTO> result = prodGoodsMapper.querySaleableGoodsList();

        log.info("ProdGoodsManager queryPublishedGoodsList end");
        return result;
    }

    public SynMgtInvoiceCatgNameResp synMgtInvoiceCatgName(SynMgtInvoiceCatgNameReq request) throws BaseException {
        log.info("ProdGoodsManager synMgtInvoiceCatgName start");
        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(request.getRequestId(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotEmpty(request.getInvoiceCatgDetail(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        SynMgtInvoiceCatgNameResp result = new SynMgtInvoiceCatgNameResp();

        for (InvoiceCatgDetail item : request.getInvoiceCatgDetail()) {
            AssertUtil.isNotEmpty(item.getGoodsItemSerial(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
            AssertUtil.isNotEmpty(item.getInvoiceCatgName(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);

            ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.qryMgnCatByCatId(item.getGoodsItemSerial());

            if (null == prodGoodsCat || !ProdGoodsCatTypeDef.MANAGEMENT_CAT.equals(prodGoodsCat.getCatType())
                || !"Y".equals(prodGoodsCat.getInvoiceFlag())) {
                throw new BaseException(OfferBaseMessageCodeEnum.MGN_CAT_CAN_NOT_SET_INVOICE_CATG);
            }

            prodGoodsCat.setInvoiceCatgName(item.getInvoiceCatgName());
            prodGoodsCat.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsCatMapper.updateMgnCatByCatId(prodGoodsCat);
        }

        result.setSyncResult("true");

        log.info("ProdGoodsManager synMgtInvoiceCatgName end, result = [{}]", result);
        return result;
    }

    /**
     * 根据商品id和设备类型查询缩略图
     * 
     * @param goodsId String
     * @param deviceType String
     * @return 缩略图
     */
    public String getThumbUrlByGoodsIdAndDeviceType(String goodsId, String deviceType) throws BaseException {
        log.info("ProdGoodsManager getThumbUrlByGoodsIdAndDeviceType goodsId = [{}], deviceType = [{}]", goodsId,
            deviceType);

        AssertUtil.isNotEmpty(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        AssertUtil.isNotEmpty(deviceType, OfferBaseMessageCodeEnum.DEVICE_TYPE_IS_NULL);

        String thumbUrl = null;
        ProdGoodsQueryContentReq prodGoodsQueryContentReq = new ProdGoodsQueryContentReq();
        prodGoodsQueryContentReq.setGoodsId(goodsId);
        prodGoodsQueryContentReq.setDeviceType(deviceType);
        prodGoodsQueryContentReq.setGoodsContentType(5L);
        List<QryGoodsContentListResp> goodsContent = qryGoodsContentList(prodGoodsQueryContentReq);
        log.info("ProdGoodsManager qryGoodsContentList goodsContent = [{}]", goodsContent);
        if (CollectionUtils.isNotEmpty(goodsContent)) {
            for (QryGoodsContentListResp qryGoodsContentResp : goodsContent) {
                ContentBaseResponseDTO contentDeatil = contentService
                    .queryContentDeatil(Long.valueOf(qryGoodsContentResp.getContentId()));
                qryGoodsContentResp.setContentDeatil(contentDeatil);
            }
        }
        log.info("ProdGoodsManager qryGoodsContentList goodsContent = [{}]", goodsContent);
        if (CollectionUtils.isNotEmpty(goodsContent)) {
            QryGoodsContentListResp content = goodsContent.get(0);
            ContentBaseResponseDTO contentBase = content.getContentDeatil();
            if (null != contentBase) {
                ContentBasePersonalDTO contentBasePersonal = contentBase.getContentBasePersonal();
                List<ContentMaterialDTO> contentMaterials = contentBasePersonal.getContentMaterials();
                if (CollectionUtils.isNotEmpty(contentMaterials)) {
                    ContentMaterialDTO contentMaterial = contentMaterials.get(0);
                    thumbUrl = contentMaterial.getThumbUrl();
                }
            }
        }

        log.info("ProdGoodsManager getThumbUrlByGoodsIdAndDeviceType thumbUrl = [{}]", thumbUrl);
        return thumbUrl;
    }

    public void updateSyncStateByGoodsId(String goodsId, String syncState){
        prodGoodsMapper.updateSyncStateByGoodsId(goodsId, syncState);
    }

}
