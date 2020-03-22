package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.AbstractAttrDef;
import com.iwhalecloud.retail.offer.consts.BossContactChannelDef;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.PageDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsActiveDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsCatTypeDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsStateDef;
import com.iwhalecloud.retail.offer.dto.client.req.OfferQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.QueryProductListReq;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferQryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.ProductList;
import com.iwhalecloud.retail.offer.dto.client.resp.QueryProductListResp;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsContentReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsTextInfoReq;
import com.iwhalecloud.retail.offer.dto.req.ModProdGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsAttrValueReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryContentReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrValueResp;
import com.iwhalecloud.retail.offer.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.offer.dto.resp.ManagementProdGoodsDetailByIdResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsCatMemResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesConditionResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesRuleResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsStockWaveResp;
import com.iwhalecloud.retail.offer.dto.resp.TagResp;
import com.iwhalecloud.retail.offer.entity.Attr;
import com.iwhalecloud.retail.offer.entity.AttrValue;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.ProdGoodsAttrValue;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatMem;
import com.iwhalecloud.retail.offer.entity.ProdGoodsContent;
import com.iwhalecloud.retail.offer.entity.ProdTagsRel;
import com.iwhalecloud.retail.offer.entity.TblGoodsBrand;
import com.iwhalecloud.retail.offer.entity.TblStoreCatgMem;
import com.iwhalecloud.retail.offer.mapper.AttrMapper;
import com.iwhalecloud.retail.offer.mapper.AttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsAttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatAttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMemMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsContentMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsSalesConditionMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsStockWaveMapper;
import com.iwhalecloud.retail.offer.mapper.ProdTagsRelMapper;
import com.iwhalecloud.retail.offer.mapper.TblGoodsBrandMapper;
import com.iwhalecloud.retail.offer.mapper.TblStoreCatgMemMapper;
import com.iwhalecloud.retail.offer.service.ContentService;
import com.iwhalecloud.retail.offer.service.ProductService;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wu.bo3
 * @date 2019/02/25
 */
@Slf4j
@Service
public class ProdGoodsManagerHelper {

    @Value("${fdfs.showUrl}")
    private String showUrl;

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private ProdGoodsStockWaveMapper prodGoodsStockWaveMapper;

    @Autowired
    private CommonManager commonManager;

    @Autowired
    private ICache redisCache;

    @Autowired
    private ProdGoodsCatMemMapper prodGoodsCatMemMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private ProdGoodsSalesConditionMapper prodGoodsSalesConditionMapper;

    @Autowired
    private TblGoodsBrandMapper tblGoodsBrandMapper;

    @Autowired
    private TblStoreCatgMemMapper storeCatgMemMapper;

    @Autowired
    private ProdGoodsCatAttrValueMapper prodGoodsCatAttrValueMapper;

    @Resource
    private ProdGoodsAttrValueMapper prodGoodsAttrValueMapper;

    @Autowired
    private AttrMapper attrMapper;

    @Resource
    private ProdTagsRelMapper prodTagsRelMapper;

    @Autowired
    private ProdGoodsContentMapper prodGoodsContentMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ContentService contentService;

    public ProdGoods checkGoodState(String goodsId) throws BaseException {
        log.info("ProdGoodsManagerHelper checkGoodState start, goodsId = [{}]", goodsId);
        AssertUtil.isNotNull(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        else if (ProdGoodsStateDef.INACTIVE.equals(prodGoods.getState())) {
            throw new BaseException(OfferBaseMessageCodeEnum.GOOD_IS_EXPIRED);
        }

        log.info("ProdGoodsManagerHelper checkGoodState end");
        return prodGoods;
    }

    public int checkGoodsStock(ProductList productList, ProdGoods prodGoods, boolean sumWave) throws BaseException {
        log.info("goods stock qty, inventroy = [{}]", productList.getInventory());
        Integer sum = prodGoods.getStockQty().intValue();
        log.info("goods stock qty, sum = [{}]", sum);
        sum += getWaveSum(prodGoods.getGoodsId());
        log.info("goods stock qty wave, sum = [{}]", sum);
        if (sumWave) {
            OfferQryReq req = new OfferQryReq();
            req.setProductCode(productList.getProductCode());
            req.setState(ProdGoodsStateDef.PUT_ON_SALE);
            List<OfferQryResp> prodGoodsList = prodGoodsMapper.qryOfferList(req);
            log.info("goods stock qty, prodGoodsList = [{}]", prodGoodsList);
            log.info("goods stock qty, prodGoodsList size = [{}]", prodGoodsList.size());
            if (CollectionUtils.isNotEmpty(prodGoodsList)) {
                // 累积已经发布商品的库存数量 实时库存和待上架的库存
                for (OfferQryResp resp : prodGoodsList) {
                    if (null == redisCache.getNumber(CacheKeyDef.GOODS_QTY, resp.getOfferCode())) {
                        continue;
                    }
                    Integer count = Integer.parseInt(redisCache.getNumber(CacheKeyDef.GOODS_QTY, resp.getOfferCode()));
                    sum += count;
                    sum += getWaveSum(resp.getOfferId());
                }
            }
        }
        log.info("goods stock checkGoodsStock, sum = [{}]", sum);
        if (sum > productList.getInventory()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_STOCK_NOT_ENOUGH);
        }

        return productList.getInventory() - sum;
    }

    private int getWaveSum(String goodsId) throws BaseException {
        log.info("goods getWaveSum, goodsId = [{}]", goodsId);

        int result = 0;
        List<ProdGoodsStockWaveResp> waveResps = prodGoodsStockWaveMapper.qryActiveStockWaveListByProdGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(waveResps)) {
            for (int i = waveResps.size() - 1; i >= 0; i--) {
                result += Integer.parseInt(waveResps.get(i).getQty());
            }
        }
        // 清空缓存
        commonManager.cacheClear(goodsId);

        log.info("goods getWaveSum, result = [{}]", result);
        return result;
    }

    public void releaseForNoSku(List<AttrValueResp> attrValueResps, List<AttrValueResp> skuAttrValue,
        List<AttrValueResp> privateAttrValue, List<AttrValueResp> threeAttrValue) {
        if (CollectionUtils.isNotEmpty(attrValueResps)) {
            attrValueResps.forEach(attrValueResp -> {
                List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(attrValueResp.getAttrId());
                log.info("ProdGoodsManagerHelper releaseForNoSku attrValues = [{}]", attrValues);
                attrValueResp.setAttrValueList(attrValues);
                if (AbstractAttrDef.ATTR_TYPE_C.equals(attrValueResp.getAttrType())) {
                    skuAttrValue.add(attrValueResp);
                }
                else if (!AbstractAttrDef.ABSTRACT_ATTR_DEF_Y.equals(attrValueResp.getInheritedFlag())) {
                    privateAttrValue.add(attrValueResp);
                }
                else {
                    threeAttrValue.add(attrValueResp);
                }
            });
        }
    }

    public void releaseFprContents(String goodsId, ManagementProdGoodsDetailByIdResp result) {
        List<ProdGoodsCatMemResp> prodGoodsCatMemRespList = prodGoodsCatMemMapper.listProdGoodsCatByGoodsId(goodsId);
        result.setProdGoodsCatList(prodGoodsCatMemRespList);
        if (CollectionUtils.isNotEmpty(prodGoodsCatMemRespList)) {
            for (ProdGoodsCatMemResp prodGoodsCatMemResp : prodGoodsCatMemRespList) {
                if (ProdGoodsCatTypeDef.SALE_CAT.equals(prodGoodsCatMemResp.getCatType())
                    && !ProdGoodsActiveDef.ACTIVE_CATALOG.equals(prodGoodsCatMemResp.getActiveFlag())) {
                    result.setSaleCatId(prodGoodsCatMemResp.getCatId());
                    result.setSaleCatName(prodGoodsCatMemResp.getName());
                }
            }
        }
    }

    public void releaseForPic(ManagementProdGoodsDetailByIdResp result) throws BaseException {
        if (StringUtils.isNotEmpty(result.getThumbnail())) {
            result.setRealThumbnail(result.getThumbnail());
            String substring = result.getThumbnail().substring(result.getThumbnail().indexOf('/') + 1);
            String token = FastdfsTokenUtil.getToken(substring);
            if (StringUtils.isEmpty(token)) {
                result.setThumbnail(showUrl + result.getThumbnail());
            }
            else {
                result.setThumbnail(showUrl + result.getThumbnail() + "?" + token);
            }
        }
    }

    public List<ProdGoodsSalesRuleResp> appendProdGoodsSalesCondition(String goodsId) {
        log.info("ProdGoodsManagerHelper appendProdGoodsSalesCondition start, goodsId = [{}]", goodsId);
        List<ProdGoodsSalesConditionResp> prodGoodsSalesConditions = prodGoodsSalesConditionMapper
            .listProdGoodsSalesConditionByGoodsId(goodsId);
        List<ProdGoodsSalesRuleResp> prodGoodsSalesRuleRespList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(prodGoodsSalesConditions)) {

            for (ProdGoodsSalesConditionResp prodGoodsSalesConditionResp : prodGoodsSalesConditions) {

                Boolean containFlag = false;

                for (ProdGoodsSalesRuleResp target : prodGoodsSalesRuleRespList) {
                    if (target.getSalesRuleId().equals(prodGoodsSalesConditionResp.getSalesRuleId())) {
                        containFlag = true;
                        target.getObjIdList().add(prodGoodsSalesConditionResp.getObjId());
                        break;
                    }
                }
                if (!containFlag) {
                    ProdGoodsSalesRuleResp target = new ProdGoodsSalesRuleResp();
                    target.setSalesRuleId(prodGoodsSalesConditionResp.getSalesRuleId());
                    target.setName(prodGoodsSalesConditionResp.getName());
                    target.setComments(prodGoodsSalesConditionResp.getComments());
                    List<String> objIdList = new ArrayList<>();
                    objIdList.add(prodGoodsSalesConditionResp.getObjId());
                    target.setObjIdList(objIdList);
                    prodGoodsSalesRuleRespList.add(target);
                }

            }
        }
        log.info("ProdGoodsManagerHelper appendProdGoodsSalesCondition end");
        return prodGoodsSalesRuleRespList;
    }

    public void checkGoodsBrand(String brandId) throws BaseException {
        // 校验商品品牌
        if (StringUtils.isNotEmpty(brandId)) {
            TblGoodsBrand tblGoodsBrand = tblGoodsBrandMapper.selectById(brandId);
            if (null == tblGoodsBrand) {
                throw new BaseException(OfferBaseMessageCodeEnum.TBL_GOODS_BRAND_NOT_EXIST);
            }
        }
    }

    /**
     * 校验sn
     * 
     * @param newSn String
     * @param oldSn String
     */
    public void checkGoodsSn(String newSn, String oldSn) throws BaseException {
        if (StringUtils.isEmpty(newSn)) {
            return;
        }
        if (StringUtils.isNotEmpty(oldSn) && oldSn.equals(newSn)) {
            return;
        }
        ProdGoods prodGoods = prodGoodsMapper.qryGoodsBySn(newSn, null);
        if (null != prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.GOOD_SN_EXIST);
        }
    }

    // 降低复杂度

    public void releaseForProdGoods(ModProdGoodsReq request, ProdGoods prodGoods) throws BaseException {
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
    }

    public void modSaleGoodsCat(String goodsId, ModProdGoodsReq request) {
        log.info("ProdGoodsManagerHelper modSaleGoodsCat start, goodsId = [{}], ModProdGoodsReq = [{}]", goodsId,
            request);

        List<ProdGoodsCatMemResp> prodGoodsCatMemRespList = prodGoodsCatMemMapper.listProdGoodsCatByGoodsId(goodsId);
        ProdGoodsCatMemResp oldSaleGoodsCat = null;
        if (CollectionUtils.isNotEmpty(prodGoodsCatMemRespList)) {
            for (ProdGoodsCatMemResp prodGoodsCatMemResp : prodGoodsCatMemRespList) {
                if (ProdGoodsCatTypeDef.SALE_CAT.equals(prodGoodsCatMemResp.getCatType())) {
                    oldSaleGoodsCat = prodGoodsCatMemResp;
                    break;
                }
            }
        }

        if (null != oldSaleGoodsCat) {
            prodGoodsCatMemMapper.deleteById(oldSaleGoodsCat.getCatMemId());
        }

        ProdGoodsCatMem saleGoodsCatMem = new ProdGoodsCatMem();
        saleGoodsCatMem.setCatMemId(UidGeneator.getUIDStr());
        saleGoodsCatMem.setCatId(request.getSaleCatId());
        saleGoodsCatMem.setGoodsId(goodsId);
        prodGoodsCatMemMapper.insert(saleGoodsCatMem);
        log.info("ProdGoodsManagerHelper modSaleGoodsCat end");
    }

    public void modStoreCat(String goodsId, ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsManagerHelper modStoreCat start, goodsId = [{}], ModProdGoodsReq = [{}]", goodsId, request);
        Map<String, Object> qryMap = new HashMap<>(1);
        qryMap.put(ColumnNameDef.GOODS_ID, goodsId);
        qryMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<TblStoreCatgMem> storeCatgMems = storeCatgMemMapper.selectByMap(qryMap);
        if (CollectionUtils.isNotEmpty(storeCatgMems)) {
            TblStoreCatgMem storeCatgMem = storeCatgMems.get(0);
            if (StringUtils.isEmpty(request.getStoreCatId())) {
                storeCatgMemMapper.deleteStoreCatgMemById(storeCatgMem.getCatMemId(), getUerId());
            }
            else {
                storeCatgMem.setCatId(request.getStoreCatId());
                storeCatgMem.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                storeCatgMem.setModifyBy(getUerId());
                storeCatgMemMapper.updateById(storeCatgMem);
            }
        }
        else if (StringUtils.isNotEmpty(request.getStoreCatId())) {
            TblStoreCatgMem storeCatgMem = new TblStoreCatgMem();
            storeCatgMem.setCatId(request.getStoreCatId());
            storeCatgMem.setGoodsId(goodsId);
            storeCatgMem.setCatMemId(UidGeneator.getUIDStr());
            storeCatgMem.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            storeCatgMem.setCreateBy(getUerId());
            storeCatgMem.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            storeCatgMem.setModifyBy(getUerId());
            storeCatgMem.setState(CommonStateDef.ACTIVE);
            storeCatgMemMapper.insert(storeCatgMem);
        }

        log.info("ProdGoodsManagerHelper modStoreCat end");
    }

    public void buildGoodsAttrList(String turnOnSku, String saleCatId, String goodsId,
        List<ProdGoodsAttrValueReq> prodGoodsAttrValueList) throws BaseException {
        log.info(
            "ProdGoodsManagerHelper buildGoodsAttrList start, turnOnSku = [{}], goodsId = [{}], ModProdGoodsReq = [{}]",
            turnOnSku, goodsId, prodGoodsAttrValueList);
        if (CollectionUtils.isNotEmpty(prodGoodsAttrValueList)) {
            // 先删
            // SKU关闭的时候，后台对sku属性不做处理
            releaseForSkuOff(turnOnSku, goodsId);
            // 后增
            releaseForOnSku(turnOnSku, goodsId, prodGoodsAttrValueList);
        }
        else {
            // 添加默认的目录属性
            log.info("ProdGoodsManager buildGoodsAttrList saleCatId = [{}]", saleCatId);
            List<GoodsCatAttrResp> goodsCatAttrResps = prodGoodsCatAttrValueMapper.qrySkuGoodsCatAttrByCatId(saleCatId);
            log.info("ProdGoodsManager buildGoodsAttrList goodsCatAttrResps = [{}]", goodsCatAttrResps);
            if (CollectionUtils.isNotEmpty(goodsCatAttrResps)) {
                int priority = goodsCatAttrResps.size();
                for (GoodsCatAttrResp attr : goodsCatAttrResps) {
                    ProdGoodsAttrValue prodGoodsAttrValue = new ProdGoodsAttrValue();
                    BeanUtils.copyProperties(attr, prodGoodsAttrValue);
                    prodGoodsAttrValue.setGoodsAttrId(UidGeneator.getUID());
                    prodGoodsAttrValue.setGoodsId(goodsId);
                    prodGoodsAttrValue.setPriority(priority);
                    prodGoodsAttrValue.setInheritedFlag(AbstractAttrDef.ABSTRACT_ATTR_DEF_Y);
                    prodGoodsAttrValue.setState(CommonStateDef.ACTIVE);
                    priority--;
                    prodGoodsAttrValueMapper.insert(prodGoodsAttrValue);
                }
            }
        }

        log.info("ProdGoodsManagerHelper buildGoodsAttrList end");
    }

    private void releaseForOnSku(String turnOnSku, String goodsId, List<ProdGoodsAttrValueReq> prodGoodsAttrValueList)
        throws BaseException {
        String createBy = getUerId();
        int priority = prodGoodsAttrValueList.size();
        for (ProdGoodsAttrValueReq prodGoodsAttrValueReq : prodGoodsAttrValueList) {
            if (AbstractAttrDef.TURN_ON_SKU_N.equals(turnOnSku)) {
                String attrId = prodGoodsAttrValueReq.getAttrId();
                Attr attr = attrMapper.selectById(attrId);
                if (AbstractAttrDef.ATTR_TYPE_C.equals(attr.getAttrType())) {
                    priority--;
                    continue;
                }
            }
            ProdGoodsAttrValue prodGoodsAttrValue = new ProdGoodsAttrValue();
            BeanUtils.copyProperties(prodGoodsAttrValueReq, prodGoodsAttrValue);
            prodGoodsAttrValue.setGoodsAttrId(UidGeneator.getUID());
            prodGoodsAttrValue.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsAttrValue.setCreateBy(createBy);
            prodGoodsAttrValue.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsAttrValue.setModifyBy(createBy);
            prodGoodsAttrValue.setGoodsId(goodsId);
            prodGoodsAttrValue.setPriority(priority);
            prodGoodsAttrValue.setState(CommonStateDef.ACTIVE);

            priority--;
            prodGoodsAttrValueMapper.insert(prodGoodsAttrValue);
        }
    }

    private void releaseForSkuOff(String turnOnSku, String goodsId) throws BaseException {
        String modifyBy = getUerId();
        if (AbstractAttrDef.TURN_ON_SKU_N.equals(turnOnSku)) {
            Map<String, Object> delMap = new HashMap<>();
            delMap.put(ColumnNameDef.GOODS_ID, goodsId);
            delMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            List<ProdGoodsAttrValue> prodGoodsAttrValues = prodGoodsAttrValueMapper.selectByMap(delMap);

            for (ProdGoodsAttrValue prodGoodsAttrValue : prodGoodsAttrValues) {
                String attrId = prodGoodsAttrValue.getAttrId();
                Attr attr = attrMapper.selectById(attrId);
                if (!AbstractAttrDef.ATTR_TYPE_C.equals(attr.getAttrType())) {
                    prodGoodsAttrValueMapper.deleteProdGoodsAttrValue(goodsId, attrId, modifyBy);
                }
            }
        }
        else {
            prodGoodsAttrValueMapper.deleteProdGoodsAttrValueByGoodsId(goodsId, modifyBy);
        }
    }

    public void modGoodTag(String goodsId, ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsManagerHelper modGoodTag start, goodsId = [{}], ModProdGoodsReq = [{}]", goodsId, request);

        String modifyBy = getUerId();
        prodTagsRelMapper.deleteByGoodsId(goodsId, modifyBy);

        if (CollectionUtils.isNotEmpty(request.getTagList())) {
            for (TagResp tag : request.getTagList()) {
                ProdTagsRel prodTagsRel = new ProdTagsRel();
                prodTagsRel.setProdTagId(UidGeneator.getUIDStr());
                prodTagsRel.setGoodsId(goodsId);
                prodTagsRel.setTagId(tag.getTagId());
                prodTagsRel.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setCreateBy(modifyBy);
                prodTagsRel.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                prodTagsRel.setModifyBy(modifyBy);
                prodTagsRel.setState(CommonStateDef.ACTIVE);
                prodTagsRelMapper.insert(prodTagsRel);
            }
        }

        log.info("ProdGoodsManagerHelper modGoodTag end");
    }

    public void modGoodsContent(ModGoodsContentReq request) throws BaseException {
        log.info("ProdGoodsManagerHelper modGoodsContent start, ModGoodsContentReq = [{}]", request);

        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(request.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(request.getGoodsId(), null);

        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        if (StringUtils.isNotEmpty(request.getContentId())) {
            ProdGoodsContent prodGoodsContent = new ProdGoodsContent();
            prodGoodsContent.setGoodsContentId(UidGeneator.getUIDStr());
            prodGoodsContent.setGoodsId(prodGoods.getGoodsId());
            prodGoodsContent.setContentId(request.getContentId());
            prodGoodsContent.setState(CommonStateDef.ACTIVE);
            prodGoodsContent.setStateDate(DBDateUtil.getCurrentDBDateTime());
            prodGoodsContent.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsContent.setModifyBy(request.getUserId());
            prodGoodsContent.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsContent.setGoodsContentType(request.getGoodsContentType());
            prodGoodsContent.setCreateBy(request.getUserId());
            prodGoodsContentMapper.insert(prodGoodsContent);
        }
        else {
            ProdGoodsQueryContentReq qryParam = new ProdGoodsQueryContentReq();
            qryParam.setGoodsContentType(1L);
            qryParam.setGoodsId(request.getGoodsId());
            List<ProdGoodsContent> prodGoodsContents = prodGoodsContentMapper.selectGoodsContentByGoodsId(qryParam);
            if (CollectionUtils.isEmpty(prodGoodsContents)) {
                throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_CONTENT_NOT_EXIST);
            }
            ProdGoodsContent prodGoodsContent = prodGoodsContents.get(0);
            prodGoodsContent.setModifyBy(request.getUserId());
            prodGoodsContent.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsContentMapper.updateById(prodGoodsContent);
        }

        // 清空缓存
        commonManager.cacheClear(prodGoods.getGoodsId());
        log.info("ProdGoodsManagerHelper modGoodsContent end");
    }

    public void modGoodsTextInfo(ModGoodsTextInfoReq request) throws BaseException {
        log.info("ProdGoodsManagerHelper modGoodsTextInfo start, ModGoodsTextInfoReq = [{}]", request);

        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(request.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(request.getGoodsId(), null);

        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        Date now = DBDateUtil.getCurrentDBDateTime();

        UserDTO tmpUserDTO = request.getUserDTO();
        if (null == tmpUserDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
        }

        prodGoods.setModifyTime(now);
        prodGoods.setModifyBy(tmpUserDTO.getUserId().toString());
        if (null != request.getIntro()) {
            prodGoods.setIntro(FastdfsTokenUtil.repairContent(request.getIntro()));
        }
        if (null != request.getAfterSale()) {
            prodGoods.setAfterSale(FastdfsTokenUtil.repairContent(request.getAfterSale()));
        }

        prodGoodsMapper.updateProdGoodsWithOutBuyCount(prodGoods);

        // 清空缓存
        commonManager.cacheClear(prodGoods.getGoodsId());
        log.info("ProdGoodsManagerHelper modGoodsTextInfo end");
    }

    public int checkGoodsQty(ProdGoods prodGoods, boolean sumWave) throws BaseException {
        log.info("ProdGoodsManagerHelper checkGoodsQty start, prodGoods = [{}], sumWave = [{}]", prodGoods, sumWave);
        QueryProductListReq queryProductListReq = new QueryProductListReq();
        queryProductListReq.setRequestId(UidGeneator.getUID());
        queryProductListReq.setPageSize(PageDef.PAGE_SIZE);
        queryProductListReq.setPageNo(PageDef.PAGE_NO);
        queryProductListReq.setChannel(BossContactChannelDef.TSOP);
        queryProductListReq.setProductCode(prodGoods.getProductCode());

        ResultVO<QueryProductListResp> result = productService.listProduct(queryProductListReq);

        if (!"000_0000_0000".equals(result.getCode())) {
            throw new BaseException(result.getCode(), result.getMessage());
        }

        if (null == result.getData() || CollectionUtils.isEmpty(result.getData().getProductList())) {
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_NOT_EXIST);
        }

        ProductList productList = result.getData().getProductList().get(0);

        if (null == productList.getInventory()
            || prodGoods.getStockQty().intValue() > productList.getInventory().intValue()) {
            log.info("prodGoods getStockQty() = [{}], productList.getInventory() = [{}]", prodGoods.getStockQty(),
                productList.getInventory());
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_STOCK_NOT_ENOUGH);
        }

        // 产品下所有已经发布商品实时库存和待上架库存 + 待发布商品基础库存和待上架库存 不大于 产品库存
        int i = checkGoodsStock(productList, prodGoods, sumWave);
        log.info("ProdGoodsManagerHelper checkGoodsQty end");
        return i;
    }

    public String getUerId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }
}
