package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.CmWsResponse;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.CmWsResponseCheckUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.AbstractAttrDef;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.ProdGoodsCatTypeDef;
import com.iwhalecloud.retail.offer.consts.TenantApplyCatgStateDef;
import com.iwhalecloud.retail.offer.dto.client.req.CmGoodsItemInfo;
import com.iwhalecloud.retail.offer.dto.client.req.CmSyncGoodsItemInfoReq;
import com.iwhalecloud.retail.offer.dto.client.req.QueryOfferCatgReq;
import com.iwhalecloud.retail.offer.dto.client.resp.CmSyncGoodsItemInfoResp;
import com.iwhalecloud.retail.offer.dto.req.AddGoodsCatReq;
import com.iwhalecloud.retail.offer.dto.req.AddSaleCatReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsCatAttrListReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsCatReq;
import com.iwhalecloud.retail.offer.dto.req.ModRelaBrandCatgReq;
import com.iwhalecloud.retail.offer.dto.req.ModSaleCatReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsCatAttrValueReq;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsCatAttrListReq;
import com.iwhalecloud.retail.offer.dto.req.QryProdGoodsCatReq;
import com.iwhalecloud.retail.offer.dto.resp.AddGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.AddSaleCatResp;
import com.iwhalecloud.retail.offer.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.offer.dto.resp.ModSaleCatResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatListResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandTenantResp;
import com.iwhalecloud.retail.offer.entity.AttrValue;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatAttrValue;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatRela;
import com.iwhalecloud.retail.offer.entity.TblGoodsCatBrand;
import com.iwhalecloud.retail.offer.entity.TenantApplyCatg;
import com.iwhalecloud.retail.offer.mapper.AttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatAttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMemMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatRelaMapper;
import com.iwhalecloud.retail.offer.mapper.TblGoodsCatBrandMapper;
import com.iwhalecloud.retail.offer.mapper.TenantApplyCatgMapper;
import com.iwhalecloud.retail.offer.service.CmInvoiceService;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/25 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GoodsCatManager {

    @Value("${fdfs.showUrl}")
    private String showUrl;

    @Autowired
    private ProdGoodsCatMapper prodGoodsCatMapper;

    @Autowired
    private ProdGoodsCatAttrValueMapper prodGoodsCatAttrValueMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private TblGoodsCatBrandMapper tblGoodsCatBrandMapper;

    @Autowired
    private ProdGoodsCatRelaMapper prodGoodsCatRelaMapper;

    @Autowired
    private CmInvoiceService cmInvoiceService;

    @Autowired
    private ProdGoodsCatMemMapper prodGoodsCatMemMapper;

    @Autowired
    private TenantApplyCatgMapper tenantApplyCatgMapper;

    // 降低复杂度
    private void releaseForQry1(List<ProdGoodsCat> prodGoodsCatList) throws BaseException {
        for (ProdGoodsCat prodGoodsCat : prodGoodsCatList) {
            if (StringUtils.isNotEmpty(prodGoodsCat.getLogo())) {
                prodGoodsCat.setRealLogo(prodGoodsCat.getLogo());
                String substring = prodGoodsCat.getLogo().substring(prodGoodsCat.getLogo().indexOf('/') + 1);

                String token = FastdfsTokenUtil.getToken(substring);

                if (StringUtils.isNotEmpty(token)) {
                    prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo() + "?" + token);
                }
                else {
                    prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo());
                }
            }

        }
    }

    public void releaseForQry2(QryGoodsCatListResp qryGoodsCatListResp) throws BaseException {
        List<ProdGoodsCat> prodGoodsCats = prodGoodsCatRelaMapper
            .qrySaleCatgRelaMgnCatg(qryGoodsCatListResp.getCatId());
        log.info("GoodsCatManager releaseForQry2 prodGoodsCats = [{}]", prodGoodsCats);

        if (CollectionUtils.isNotEmpty(prodGoodsCats)) {
            qryGoodsCatListResp.setProdGoodsCat(prodGoodsCats.get(0));
            qryGoodsCatListResp.setProdGoodsCatName(prodGoodsCats.get(0).getName());
        }
        List<TblGoodsBrandTenantResp> goodsBrandTenantResps = prodGoodsCatMapper
            .qryGoodBrandByCatgId(qryGoodsCatListResp.getCatId());
        if (CollectionUtils.isNotEmpty(goodsBrandTenantResps)) {
            qryGoodsCatListResp.setGoodsBrands(goodsBrandTenantResps);
            String goodsBrandNames = goodsBrandTenantResps.stream().map(TblGoodsBrandTenantResp::getBrandName)
                .collect(Collectors.joining(","));
            qryGoodsCatListResp.setGoodsBrandNames(goodsBrandNames);
        }
    }

    public List<QryGoodsCatListResp> qryGoodsCatList(QryProdGoodsCatReq req) throws BaseException {
        log.info("GoodsCatManager qryGoodsCatList start, QryProdGoodsCatReq = [{}]", req);

        // 目录类型校验
        checkGoodsCatType(req.getCatType());

        List<ProdGoodsCat> prodGoodsCatList = prodGoodsCatMapper.qryProdGoodsCatList(req);

        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(prodGoodsCatList)) {
            releaseForQry1(prodGoodsCatList);
            qryGoodsCatListRespList = getRootGoodsCat(prodGoodsCatList);
            for (QryGoodsCatListResp qryGoodsCatListResp : qryGoodsCatListRespList) {
                // 如果是销售目录 需要返回关联的管理目录信息 和品牌信息
                if (qryGoodsCatListResp.getCatType() != null
                    && qryGoodsCatListResp.getCatType().equals(ProdGoodsCatTypeDef.SALE_CAT)) {
                    releaseForQry2(qryGoodsCatListResp);
                }
            }
            appendGoodsCatChildren(qryGoodsCatListRespList, prodGoodsCatList);
        }

        log.info("GoodsCatManager qryGoodsCatList end");
        return qryGoodsCatListRespList;
    }

    public Page<QryGoodsCatListResp> qryGoodsCatPage(QryProdGoodsCatReq req) throws BaseException {
        log.info("GoodsCatManager qryGoodsCatList start, QryProdGoodsCatReq = [{}]", req);

        // 目录类型校验
        checkGoodsCatType(req.getCatType());

        List<ProdGoodsCat> prodGoodsCatList = prodGoodsCatMapper.qryProdGoodsCatList(req);
        Page<QryGoodsCatListResp> qryGoodsCatListRespList = new Page<>(req.getPageNo(), req.getPageSize());

        if (CollectionUtils.isNotEmpty(prodGoodsCatList)) {
            releaseForQry1(prodGoodsCatList);
            qryGoodsCatListRespList = getRootGoodsCatPage(qryGoodsCatListRespList, prodGoodsCatList);
            for (QryGoodsCatListResp qryGoodsCatListResp : qryGoodsCatListRespList.getRecords()) {
                // 如果是销售目录 需要返回关联的管理目录信息 和品牌信息
                if (qryGoodsCatListResp.getCatType() != null
                    && qryGoodsCatListResp.getCatType().equals(ProdGoodsCatTypeDef.SALE_CAT)) {
                    releaseForQry2(qryGoodsCatListResp);
                }
            }
            appendGoodsCatChildren(qryGoodsCatListRespList.getRecords(), prodGoodsCatList);
        }

        log.info("GoodsCatManager qryGoodsCatList end");
        return qryGoodsCatListRespList;
    }

    public List<QryGoodsCatListResp> qryOutGoodsCatList(QryProdGoodsCatReq req) throws BaseException {
        log.info("GoodsCatManager qryOutGoodsCatList start, QryProdGoodsCatReq = [{}]", req);

        // 目录类型校验
        checkGoodsCatType(req.getCatType());

        List<ProdGoodsCat> prodGoodsCatList = prodGoodsCatMapper.qryOutProdGoodsCatList(req);
        List<QryGoodsCatListResp> oriOutProdGoodsCatList = new ArrayList<>();
        BeanUtils.copyProperties(prodGoodsCatList, oriOutProdGoodsCatList);

        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(prodGoodsCatList)) {
            qryGoodsCatListRespList = getRootCat(prodGoodsCatList);
            getChildCat(qryGoodsCatListRespList, prodGoodsCatList);
            appendGoodsCatChildren(qryGoodsCatListRespList, prodGoodsCatList);
        }

        log.info("GoodsCatManager qryOutGoodsCatList end");
        return qryGoodsCatListRespList;
    }

    private List<QryGoodsCatListResp> getRootCat(List<ProdGoodsCat> prodGoodsCatList) throws BaseException {

        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(prodGoodsCatList)) {
            for (ProdGoodsCat prodGoodsCat : prodGoodsCatList) {
                ProdGoodsCat parentProdGoodsCat = selectRoot(prodGoodsCat.getCatId());
                QryGoodsCatListResp qryGoodsCatListResp = new QryGoodsCatListResp();
                BeanUtils.copyProperties(parentProdGoodsCat, qryGoodsCatListResp);
                if (!qryGoodsCatListRespList.contains(qryGoodsCatListResp)) {
                    qryGoodsCatListRespList.add(qryGoodsCatListResp);
                }
            }
        }
        return qryGoodsCatListRespList;
    }

    private void getChildCat(List<QryGoodsCatListResp> qryGoodsCatListRespList, List<ProdGoodsCat> outProdGoodsCatList) {
        if (CollectionUtils.isEmpty(qryGoodsCatListRespList) || CollectionUtils.isEmpty(outProdGoodsCatList)) {
            return;
        }

        for (QryGoodsCatListResp qryGoodsCatListResp : qryGoodsCatListRespList) {
            List<String> catIds = new ArrayList<>();
            catIds.add(qryGoodsCatListResp.getCatId());
            selectChild(catIds, outProdGoodsCatList);
        }
    }

    /**
     * 根据子找父节点
     * @param catId
     * @return
     * @throws BaseException
     */
    private ProdGoodsCat selectRoot(String catId) {
        ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectById(catId);

        //查询父级架构
        if (null != prodGoodsCat && StringUtils.isNotEmpty(prodGoodsCat.getParentId())) {
            QueryWrapper<ProdGoodsCat> prodGoodsCatQueryWrapper = new QueryWrapper<>();
            prodGoodsCatQueryWrapper.eq(ColumnNameDef.CAT_ID, prodGoodsCat.getParentId());
            prodGoodsCatQueryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            prodGoodsCat = prodGoodsCatMapper.selectOne(prodGoodsCatQueryWrapper);

            if (null != prodGoodsCat) {
                prodGoodsCat = selectRoot(prodGoodsCat.getCatId());
            }
        }
        return prodGoodsCat;
    }

    private void selectChild(List<String> catIds, List<ProdGoodsCat> outProdGoodsCatList) {
        List<String> tempId = new ArrayList<>();

        List<ProdGoodsCat> prodGoodsCats = new ArrayList<>();
        for (String catId : catIds) {
            QueryWrapper<ProdGoodsCat> prodGoodsQueryWrapper = new QueryWrapper<>();
            prodGoodsQueryWrapper.eq(ColumnNameDef.PARENT_ID, catId);
            prodGoodsQueryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            prodGoodsCats = prodGoodsCatMapper.selectList(prodGoodsQueryWrapper);

            ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectById(catId);
            int allChiledSelected = 0;
            if (CollectionUtils.isNotEmpty(prodGoodsCats)) {
                //不是空，说明是枝
                for (ProdGoodsCat dto : prodGoodsCats) {
                    tempId.add(dto.getCatId());
                    if (outProdGoodsCatList.contains(dto)) {
                        allChiledSelected = allChiledSelected + 1;
                    }
                }
                if (!outProdGoodsCatList.contains(prodGoodsCat) && allChiledSelected > 0) {
                    outProdGoodsCatList.add(prodGoodsCat);
                }
            }
        }
        if (tempId.size() != 0) {
            selectChild(tempId, outProdGoodsCatList);
        }
    }

    // 降低复杂度
    private void releaseForGoods(String token, ProdGoodsCat prodGoodsCat) {
        if (StringUtils.isNotEmpty(token)) {
            prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo() + "?" + token);
        }
        else {
            prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo());
        }
    }

    public QryGoodsCatListResp qryGoodsCatDetail(String catId) throws BaseException {
        log.info("GoodsCatManager qryGoodsCatDetail start, catId = [{}]", catId);
        QryGoodsCatListResp resp = new QryGoodsCatListResp();
        ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectById(catId);
        if (prodGoodsCat != null) {
            if (StringUtils.isNotEmpty(prodGoodsCat.getLogo())) {
                String substring = prodGoodsCat.getLogo().substring(prodGoodsCat.getLogo().indexOf('/') + 1);
                String token = FastdfsTokenUtil.getToken(substring);

                prodGoodsCat.setRealLogo(prodGoodsCat.getLogo());
                releaseForGoods(token, prodGoodsCat);
            }
            BeanUtils.copyProperties(prodGoodsCat, resp);
            // 如果是销售目录 需要返回关联的管理目录信息
            if (prodGoodsCat.getCatType() != null && prodGoodsCat.getCatType().equals(ProdGoodsCatTypeDef.SALE_CAT)) {
                // 是否需要租户条件 后面再说
                List<ProdGoodsCat> prodGoodsCats = prodGoodsCatRelaMapper.qrySaleCatgRelaMgnCatg(catId);
                if (CollectionUtils.isNotEmpty(prodGoodsCats)) {
                    resp.setProdGoodsCat(prodGoodsCats.get(0));
                }
            }
        }
        // 根据目录id取所有目录属性按照sku和非sku属性封装
        List<GoodsCatAttrResp> goodsCatAttrResps = prodGoodsCatAttrValueMapper.qrySkuGoodsCatAttrByCatId(catId);
        if (CollectionUtils.isNotEmpty(goodsCatAttrResps)) {
            List<GoodsCatAttrResp> skuGoodsCatAttr = new ArrayList<>();
            List<GoodsCatAttrResp> noSkuGoodsCatAttr = new ArrayList<>();
            goodsCatAttrResps.forEach(goodsCatAttrResp -> {
                // 类目属性都是继承属性
                goodsCatAttrResp.setInheritedFlag(AbstractAttrDef.ABSTRACT_ATTR_DEF_Y);
                List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(goodsCatAttrResp.getAttrId());
                goodsCatAttrResp.setAttrValueList(attrValues);
                if (AbstractAttrDef.ATTR_TYPE_C.equals(goodsCatAttrResp.getAttrType())) {
                    skuGoodsCatAttr.add(goodsCatAttrResp);
                }
                else {
                    noSkuGoodsCatAttr.add(goodsCatAttrResp);
                }
            });
            resp.setSkuGoodsCatAttr(skuGoodsCatAttr);
            resp.setNoSkuGoodsCatAttr(noSkuGoodsCatAttr);
        }

        log.info("GoodsCatManager qryGoodsCatDetail end");
        return resp;
    }

    /**
     * 按页获取prodGoodsCatList
     */
    private Page<QryGoodsCatListResp> getRootGoodsCatPage(Page<QryGoodsCatListResp> qryGoodsCatListResp,
        List<ProdGoodsCat> prodGoodsCatList) {
        log.info("GoodsCatManager getRootGoodsCatPage start");

        Iterator<ProdGoodsCat> iter = prodGoodsCatList.iterator();
        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();
        Long startNum = (qryGoodsCatListResp.getCurrent() - 1) * qryGoodsCatListResp.getSize();
        Long endNum = startNum + qryGoodsCatListResp.getSize();
        while (iter.hasNext()) {
            ProdGoodsCat prodGoodsCat = iter.next();
            if (StringUtils.isEmpty(prodGoodsCat.getParentId())) {
                qryGoodsCatListResp.setTotal(qryGoodsCatListResp.getTotal() + 1);
                if (qryGoodsCatListResp.getTotal() > startNum && qryGoodsCatListResp.getTotal() <= endNum) {
                    QryGoodsCatListResp goodsCatResp = new QryGoodsCatListResp();
                    BeanUtils.copyProperties(prodGoodsCat, goodsCatResp);
                    qryGoodsCatListRespList.add(goodsCatResp);
                }

                iter.remove();
            }
        }

        qryGoodsCatListResp.setRecords(qryGoodsCatListRespList);
        log.info("GoodsCatManager getRootGoodsCatPage end");
        return qryGoodsCatListResp;
    }

    private List<QryGoodsCatListResp> getRootGoodsCat(List<ProdGoodsCat> prodGoodsCatList) {
        log.info("GoodsCatManager getRootGoodsCat start, List<ProdGoodsCat> = [{}]", prodGoodsCatList);

        Iterator<ProdGoodsCat> iter = prodGoodsCatList.iterator();
        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();
        while (iter.hasNext()) {
            ProdGoodsCat prodGoodsCat = iter.next();
            if (StringUtils.isEmpty(prodGoodsCat.getParentId())) {
                QryGoodsCatListResp goodsCatResp = new QryGoodsCatListResp();
                BeanUtils.copyProperties(prodGoodsCat, goodsCatResp);
                qryGoodsCatListRespList.add(goodsCatResp);
                iter.remove();
            }
        }

        log.info("GoodsCatManager getRootGoodsCat end");
        return qryGoodsCatListRespList;
    }

    /**
     * 降低复杂度
     */
    private void releaseForAppend(QryGoodsCatListResp childrenNode) throws BaseException {
        // 如果是销售目录 需要返回关联的管理目录信息 和品牌信息
        if (childrenNode.getCatType() != null && childrenNode.getCatType().equals(ProdGoodsCatTypeDef.SALE_CAT)) {
            List<ProdGoodsCat> prodGoodsCats = prodGoodsCatRelaMapper.qrySaleCatgRelaMgnCatg(childrenNode.getCatId());
            if (CollectionUtils.isNotEmpty(prodGoodsCats)) {
                childrenNode.setProdGoodsCat(prodGoodsCats.get(0));
                childrenNode.setProdGoodsCatName(prodGoodsCats.get(0).getName());
            }
            List<TblGoodsBrandTenantResp> goodsBrandTenantResps = prodGoodsCatMapper
                .qryGoodBrandByCatgId(childrenNode.getCatId());
            if (CollectionUtils.isNotEmpty(goodsBrandTenantResps)) {
                String goodsBrandNames = goodsBrandTenantResps.stream().map(TblGoodsBrandTenantResp::getBrandName)
                    .collect(Collectors.joining(","));
                childrenNode.setGoodsBrands(goodsBrandTenantResps);
                childrenNode.setGoodsBrandNames(goodsBrandNames);
            }
        }
    }

    private void appendGoodsCatChildren(List<QryGoodsCatListResp> qryGoodsCatListRespList,
        List<ProdGoodsCat> prodGoodsCatList) throws BaseException {
        log.info("GoodsCatManager appendGoodsCatChildren start");

        Iterator<ProdGoodsCat> iter = prodGoodsCatList.iterator();
        if (CollectionUtils.isNotEmpty(qryGoodsCatListRespList) && iter.hasNext()) {
            for (QryGoodsCatListResp parentNode : qryGoodsCatListRespList) {
                List<QryGoodsCatListResp> childrenList = new ArrayList<>();
                iter = prodGoodsCatList.iterator();
                while (iter.hasNext()) {
                    ProdGoodsCat prodGoodsCat = iter.next();
                    if (parentNode.getCatId().equals(prodGoodsCat.getParentId())) {
                        QryGoodsCatListResp childrenNode = new QryGoodsCatListResp();
                        BeanUtils.copyProperties(prodGoodsCat, childrenNode);

                        // 如果是销售目录 需要返回关联的管理目录信息 和品牌信息
                        releaseForAppend(childrenNode);

                        childrenList.add(childrenNode);
                        iter.remove();
                    }
                }
                parentNode.setChildren(childrenList);
                appendGoodsCatChildren(childrenList, prodGoodsCatList);
            }
        }

        log.info("GoodsCatManager appendGoodsCatChildren end");
    }

    /**
     * 获取当前操作用户的UserId
     * 
     * @return
     * @throws BaseException
     */
    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

    public AddGoodsResp addGoodsCat(@RequestBody AddGoodsCatReq req) throws BaseException {
        log.info("GoodsCatManager addGoodsCat start, AddGoodsCatReq = [{}]", req);

        // 目录类型校验
        checkGoodsCatType(req.getCatType());
        checkParentCatExist(req.getParentId(), req.getCatType());
        // 目录名称校验
        if (StringUtils.isEmpty(req.getName())) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_NAME_EMPTY);
        }
        checkSameCatName(req.getName(), req.getCatType());

        String catId = UidGeneator.getUIDStr();

        ProdGoodsCat prodGoodsCat = new ProdGoodsCat();
        BeanUtils.copyProperties(req, prodGoodsCat);
        String createBy = getUserId();
        prodGoodsCat.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsCat.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsCat.setCreateBy(createBy);
        prodGoodsCat.setModifyBy(createBy);
        prodGoodsCat.setCatId(catId);
        prodGoodsCat.setState(CommonStateDef.ACTIVE);

        prodGoodsCatMapper.insert(prodGoodsCat);

        // 创建子目录，属性需要继承
        if (StringUtils.isNotEmpty(req.getParentId())) {
            List<ProdGoodsCatAttrValue> prodGoodsCatAttrValues = prodGoodsCatAttrValueMapper
                .qryCatAttrById(req.getParentId());
            if (CollectionUtils.isNotEmpty(prodGoodsCatAttrValues)) {
                prodGoodsCatAttrValues.forEach(prodGoodsCatAttrValue -> {
                    ProdGoodsCatAttrValue newCatAttr = new ProdGoodsCatAttrValue();
                    BeanUtils.copyProperties(prodGoodsCatAttrValue, newCatAttr);
                    newCatAttr.setCatAttrValueId(UidGeneator.getUIDStr());
                    newCatAttr.setCatId(prodGoodsCat.getCatId());
                    newCatAttr.setInheritedFlag("Y");
                    newCatAttr.setAttrId(prodGoodsCatAttrValue.getAttrId());
                    newCatAttr.setCreateBy(createBy);
                    newCatAttr.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    newCatAttr.setModifyBy(createBy);
                    newCatAttr.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    newCatAttr.setState(CommonStateDef.ACTIVE);
                    newCatAttr.setTenantId(null);
                    prodGoodsCatAttrValueMapper.insertCatAttr(newCatAttr);
                });
            }

            //如果父目录被分配了，子目录一样要被分配
            Map<String, Object> qryMap = new HashMap<>();
            qryMap.put(ColumnNameDef.CAT_ID, req.getParentId());
            qryMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            List<TenantApplyCatg> appliedCatgs = tenantApplyCatgMapper.selectByMap(qryMap);
            if (CollectionUtils.isNotEmpty(appliedCatgs)) {
                for (TenantApplyCatg tenantApplyCatg : appliedCatgs)  {
                    TenantApplyCatg chiildTenantApplyCatg = new TenantApplyCatg();
                    chiildTenantApplyCatg.setCatId(catId);
                    chiildTenantApplyCatg.setStateDate(DBDateUtil.getCurrentDBDateTime());
                    chiildTenantApplyCatg.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    chiildTenantApplyCatg.setTenantId(tenantApplyCatg.getTenantId());
                    chiildTenantApplyCatg.setCreateBy(createBy);
                    chiildTenantApplyCatg.setModifyBy(createBy);
                    chiildTenantApplyCatg.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    chiildTenantApplyCatg.setApplyId(UidGeneator.getUIDStr());
                    chiildTenantApplyCatg.setState(TenantApplyCatgStateDef.STATE_A);
                    tenantApplyCatgMapper.addTenantApplyCatg(chiildTenantApplyCatg);
                }
            }
        }

        AddGoodsResp addGoodsResp = new AddGoodsResp();
        BeanUtils.copyProperties(prodGoodsCat, addGoodsResp);

        log.info("GoodsCatManager addGoodsCat end");
        return addGoodsResp;

    }

    public AddSaleCatResp addGoodsSaleCat(AddSaleCatReq req) throws BaseException {
        log.info("GoodsCatManager addGoodsSaleCat start = [{}]", req);
        // 校验目录类型存在性、目录名称唯一性、父目录存在性
        AssertUtil.isNotNull(req.getName(), OfferBaseMessageCodeEnum.CAT_NAME_EMPTY);
        AddSaleCatResp resp = new AddSaleCatResp();
        checkGoodsCatType(req.getCatType());
        checkSameCatName(req.getName(), req.getCatType());
        if (StringUtils.isNotEmpty(req.getParentId())) {
            checkParentCatExist(req.getParentId(), req.getCatType());
            // 父目录不能绑定商品
            int num = prodGoodsCatMemMapper.countProGoodsByCatId(req.getParentId());
            log.info("GoodsCatManager countProGoodsByCatId num = [{}]", num);
            if (num > 0) {
                throw new BaseException(OfferBaseMessageCodeEnum.STORE_CATG_HAS_EFF_GOODS);
            }
        }

        String createBy = getUserId();
        ProdGoodsCat prodGoodsCat = new ProdGoodsCat();
        BeanUtils.copyProperties(req, prodGoodsCat);
        prodGoodsCat.setCatId(UidGeneator.getUIDStr());
        prodGoodsCat.setModifyBy(createBy);
        prodGoodsCat.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsCat.setCreateBy(createBy);
        prodGoodsCat.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsCat.setState(CommonStateDef.ACTIVE);
        // 新增目录信息
        prodGoodsCatMapper.insert(prodGoodsCat);

        BeanUtils.copyProperties(prodGoodsCat, resp);
        // 关联的管理目录处理
        if (StringUtils.isNotEmpty(req.getRelaCatId())) {
            // 新建销售目录和管理目录的关系
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(ColumnNameDef.CAT_ID, req.getRelaCatId());
            queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            ProdGoodsCat relaProdGoodsCat = prodGoodsCatMapper.selectOne(queryWrapper);
            resp.setProdGoodsCat(relaProdGoodsCat);

            ProdGoodsCatRela prodGoodsCatRela = new ProdGoodsCatRela();
            prodGoodsCatRela.setRelaId(UidGeneator.getUIDStr());
            prodGoodsCatRela.setSaleCatId(prodGoodsCat.getCatId());
            prodGoodsCatRela.setManageCatId(req.getRelaCatId());
            prodGoodsCatRela.setCreateBy(createBy);
            prodGoodsCatRela.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsCatRela.setModifyBy(createBy);
            prodGoodsCatRela.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsCatRela.setState(CommonStateDef.ACTIVE);
            prodGoodsCatRelaMapper.insert(prodGoodsCatRela);

            // 销售目录继承关联的管理目录的属性
            List<ProdGoodsCatAttrValue> relaCatAttrList = prodGoodsCatAttrValueMapper
                .qryCatAttrById(req.getRelaCatId());
            if (CollectionUtils.isNotEmpty(relaCatAttrList)) {
                relaCatAttrList.forEach(prodGoodsCatAttrValue -> {
                    ProdGoodsCatAttrValue catAttr = new ProdGoodsCatAttrValue();
                    BeanUtils.copyProperties(prodGoodsCatAttrValue, catAttr);
                    catAttr.setCatAttrValueId(UidGeneator.getUIDStr());
                    catAttr.setCatId(prodGoodsCat.getCatId());
                    catAttr.setInheritedFlag("Y");
                    catAttr.setCreateBy(createBy);
                    catAttr.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    catAttr.setModifyBy(createBy);
                    catAttr.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    catAttr.setTenantId(null);
                    prodGoodsCatAttrValueMapper.insert(catAttr);
                });
            }
        }
        log.info("GoodsCatManager addGoodsSaleCat end");
        return resp;
    }

    public ProdGoodsCat modGoodsCat(String catId, ModGoodsCatReq req) throws BaseException {
        log.info("GoodsCatManager modGoodsCat start, catId = [{}], ModGoodsCatReq = [{}]", catId, req);

        if (StringUtils.isEmpty(catId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_ID_EMPTY);
        }

        if (null == req) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }

        ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectById(catId);

        List<ProdGoodsCat> prodGoodsCats = prodGoodsCatMapper.qrySameCatNameList(req.getName(),
            prodGoodsCat.getCatType());
        if (CollectionUtils.isNotEmpty(prodGoodsCats)
            && !prodGoodsCats.get(0).getCatId().equals(prodGoodsCat.getCatId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_NAME_ERROR);
        }
        BeanUtils.copyProperties(req, prodGoodsCat);
        String modifyBy = getUserId();
        prodGoodsCat.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsCat.setModifyBy(modifyBy);
        prodGoodsCatMapper.updateById(prodGoodsCat);

        log.info("GoodsCatManager modGoodsCat end");
        return prodGoodsCat;

    }

    public ModSaleCatResp modGoodsSaleCat(String catId, ModSaleCatReq req) throws BaseException {
        log.info("GoodsCatManager modGoodsSaleCat start catId = [{}], req = [{}]", catId, req);
        ModSaleCatResp resp = new ModSaleCatResp();
        if (StringUtils.isEmpty(req.getName())) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_NAME_EMPTY);
        }
        ProdGoodsCat prodGoodsCat = checkGoodsCatExist(catId, ProdGoodsCatTypeDef.SALE_CAT);
        List<ProdGoodsCat> prodGoodsCats = prodGoodsCatMapper.qrySameCatNameList(req.getName(),
            prodGoodsCat.getCatType());
        if (CollectionUtils.isNotEmpty(prodGoodsCats)
            && !prodGoodsCats.get(0).getCatId().equals(prodGoodsCat.getCatId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_NAME_ERROR);
        }

        String modifyBy = getUserId();
        // 1、修改目录信息
        prodGoodsCat.setName(req.getName());
        prodGoodsCat.setLogo(req.getLogo());
        prodGoodsCat.setCatOrder(req.getCatOrder());
        prodGoodsCat.setComments(req.getComments());
        prodGoodsCat.setStoreId(req.getStoreId());
        prodGoodsCat.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsCat.setModifyBy(modifyBy);
        prodGoodsCatMapper.updateById(prodGoodsCat);
        BeanUtils.copyProperties(prodGoodsCat, resp);
        // 2、如果关联的管理目录修改 需要修改关联关系
        List<ProdGoodsCatRela> prodGoodsCatRelas = prodGoodsCatRelaMapper.qryProdGoodsRela(prodGoodsCat.getCatId());
        // 之前存在 现在不存在 删除
        if (CollectionUtils.isNotEmpty(prodGoodsCatRelas) && StringUtils.isEmpty(req.getRelaCatId())) {
            prodGoodsCatRelaMapper.deleteProdGoodsRelaByCatId(prodGoodsCat.getCatId(), modifyBy);
            prodGoodsCatAttrValueMapper.deleteCatAttrListByCatId(prodGoodsCat.getCatId(), modifyBy);
        }
        // 之前存在 现在存在不相等 删除/新增
        if (CollectionUtils.isNotEmpty(prodGoodsCatRelas) && StringUtils.isNotEmpty(req.getRelaCatId())
            && !prodGoodsCatRelas.get(0).getManageCatId().equals(req.getRelaCatId())) {
            prodGoodsCatRelaMapper.deleteProdGoodsRelaByCatId(prodGoodsCat.getCatId(), modifyBy);
            prodGoodsCatAttrValueMapper.deleteCatAttrListByCatId(prodGoodsCat.getCatId(), modifyBy);
            ProdGoodsCat relaProdGoodsCat = prodGoodsCatMapper.selectById(req.getRelaCatId());
            resp.setProdGoodsCat(relaProdGoodsCat);

            ProdGoodsCatRela prodGoodsCatRela = new ProdGoodsCatRela();
            prodGoodsCatRela.setState(CommonStateDef.ACTIVE);
            prodGoodsCatRela.setSaleCatId(prodGoodsCat.getCatId());
            prodGoodsCatRela.setRelaId(UidGeneator.getUIDStr());
            prodGoodsCatRela.setManageCatId(req.getRelaCatId());
            prodGoodsCatRela.setModifyBy(modifyBy);
            prodGoodsCatRela.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsCatRela.setCreateBy(modifyBy);
            prodGoodsCatRela.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsCatRelaMapper.insert(prodGoodsCatRela);

            List<ProdGoodsCatAttrValue> relaCatAttrList = prodGoodsCatAttrValueMapper
                .qryCatAttrById(req.getRelaCatId());
            if (CollectionUtils.isNotEmpty(relaCatAttrList)) {
                relaCatAttrList.forEach(prodGoodsCatAttrValue -> {
                    ProdGoodsCatAttrValue catAttr = new ProdGoodsCatAttrValue();
                    BeanUtils.copyProperties(prodGoodsCatAttrValue, catAttr);
                    catAttr.setCatAttrValueId(UidGeneator.getUIDStr());
                    catAttr.setCatId(prodGoodsCat.getCatId());
                    catAttr.setCreateBy(modifyBy);
                    catAttr.setInheritedFlag("Y");
                    catAttr.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    catAttr.setModifyBy(modifyBy);
                    catAttr.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    catAttr.setTenantId(null);
                    prodGoodsCatAttrValueMapper.insertCatAttr(catAttr);
                });
            }
        }
        // 之前不存在 现在存在 新增
        if (CollectionUtils.isEmpty(prodGoodsCatRelas) && StringUtils.isNotEmpty(req.getRelaCatId())) {
            ProdGoodsCat relaProdGoodsCat = prodGoodsCatMapper.selectById(req.getRelaCatId());
            resp.setProdGoodsCat(relaProdGoodsCat);

            ProdGoodsCatRela prodGoodsCatRela = new ProdGoodsCatRela();
            prodGoodsCatRela.setCreateBy(modifyBy);
            prodGoodsCatRela.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsCatRela.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsCatRela.setModifyBy(modifyBy);
            prodGoodsCatRela.setState(CommonStateDef.ACTIVE);
            prodGoodsCatRela.setRelaId(UidGeneator.getUIDStr());
            prodGoodsCatRela.setSaleCatId(prodGoodsCat.getCatId());
            prodGoodsCatRela.setManageCatId(req.getRelaCatId());
            prodGoodsCatRelaMapper.insert(prodGoodsCatRela);

            List<ProdGoodsCatAttrValue> relaCatAttrList = prodGoodsCatAttrValueMapper
                .qryCatAttrById(req.getRelaCatId());
            if (CollectionUtils.isNotEmpty(relaCatAttrList)) {
                relaCatAttrList.forEach(prodGoodsCatAttrValue -> {
                    ProdGoodsCatAttrValue catAttr = new ProdGoodsCatAttrValue();
                    BeanUtils.copyProperties(prodGoodsCatAttrValue, catAttr);
                    catAttr.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    catAttr.setModifyBy(modifyBy);
                    catAttr.setCreateBy(modifyBy);
                    catAttr.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    catAttr.setTenantId(null);
                    catAttr.setCatAttrValueId(UidGeneator.getUIDStr());
                    catAttr.setCatId(prodGoodsCat.getCatId());
                    catAttr.setInheritedFlag("Y");
                    prodGoodsCatAttrValueMapper.insert(catAttr);
                });
            }
        }

        log.info("GoodsCatManager modGoodsSaleCat end.");
        return null;
    }

    public void delGoodsCat(String catId) throws BaseException {
        log.info("GoodsCatManager delGoodsCat start, catId = [{}]", catId);

        if (StringUtils.isEmpty(catId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_ID_EMPTY);
        }

        ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectById(catId);

        if (null == prodGoodsCat) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_NOT_EXIST);
        }

        List<ProdGoodsCat> prodGoodsCatList = prodGoodsCatMapper.qryChildProdGoodsCatList(catId);
        if (CollectionUtils.isNotEmpty(prodGoodsCatList)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_DEL_HAS_CHILD_CAT);
        }

        // 父目录不能绑定商品
        int num = prodGoodsCatMemMapper.countProGoodsByCatId(catId);
        log.info("GoodsCatManager countProGoodsByCatId num = [{}]", num);
        if (num > 0) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_CATG_HAS_EFF_GOODS);
        }

        String modifyBy = getUserId();
        // 校验管理目录是否有关联销售目录
        if (prodGoodsCat.getCatType().equals(ProdGoodsCatTypeDef.MANAGEMENT_CAT)) {
            List<ProdGoodsCatRela> prodGoodsCatRelas = prodGoodsCatRelaMapper.qryManagementCatgRelaByMgnId(catId);
            if (CollectionUtils.isNotEmpty(prodGoodsCatRelas)) {
                throw new BaseException(OfferBaseMessageCodeEnum.CAT_DEL_HAS_SALE_CAT);
            }
        }
        // 销售目录需要删除与管理目录的关系和继承的属性
        if (prodGoodsCat.getCatType().equals(ProdGoodsCatTypeDef.SALE_CAT)) {
            List<ProdGoodsCatRela> prodGoodsCatRelas = prodGoodsCatRelaMapper.qryProdGoodsRela(catId);
            if (CollectionUtils.isNotEmpty(prodGoodsCatRelas)) {
                prodGoodsCatRelaMapper.deleteProdGoodsRelaByCatId(catId, modifyBy);
                prodGoodsCatAttrValueMapper.deleteCatAttrListByCatId(catId, modifyBy);
            }
        }

        //分配租户表也需要删除
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        queryMap.put(ColumnNameDef.CAT_ID, catId);
        List<TenantApplyCatg> tenantApplyCatgs = tenantApplyCatgMapper.selectByMap(queryMap);

        if (CollectionUtils.isNotEmpty(tenantApplyCatgs)) {
            for (TenantApplyCatg tenantApplyCatg : tenantApplyCatgs) {
                tenantApplyCatg.setModifyBy(modifyBy);
                tenantApplyCatg.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                tenantApplyCatg.setState(CommonStateDef.INACTIVE);
                tenantApplyCatgMapper.updateById(tenantApplyCatg);
            }
        }

        prodGoodsCatMapper.deleteCat(catId, modifyBy);

        log.info("GoodsCatManager delGoodsCat end");
    }

    private ProdGoodsCat checkGoodsCatExist(String catId, String catType) throws BaseException {
        if (StringUtils.isEmpty(catId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_ID_EMPTY);
        }
        ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectById(catId);
        if (null == prodGoodsCat || !prodGoodsCat.getCatType().equals(catType)
            || !CommonStateDef.ACTIVE.equals(prodGoodsCat.getState())) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARENT_CAT_NOT_EXIST);
        }
        return prodGoodsCat;
    }

    private void checkGoodsCatType(String catType) throws BaseException {
        if (StringUtils.isEmpty(catType)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_TYPE_EMPTY);
        }

        if (!ProdGoodsCatTypeDef.MANAGEMENT_CAT.equals(catType) && !ProdGoodsCatTypeDef.SALE_CAT.equals(catType)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_TYPE_ERROR);
        }
    }

    private void checkSameCatName(String catName, String catType) throws BaseException {
        List<ProdGoodsCat> prodGoodsCats = prodGoodsCatMapper.qrySameCatNameList(catName, catType);
        if (CollectionUtils.isNotEmpty(prodGoodsCats)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_NAME_ERROR);
        }
    }

    private void checkParentCatExist(String parentId, String catType) throws BaseException {
        if (StringUtils.isNotEmpty(parentId)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(ColumnNameDef.CAT_ID, parentId);
            queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectOne(queryWrapper);
            if (null == prodGoodsCat || !prodGoodsCat.getCatType().equals(catType)
                || !CommonStateDef.ACTIVE.equals(prodGoodsCat.getState())) {
                throw new BaseException(OfferBaseMessageCodeEnum.PARENT_CAT_NOT_EXIST);
            }
        }
    }

    /**
     * 根据商品id取所有销售类型的目录
     * 
     * @param goodsId 商品id
     * @return List<ProdGoodsCat>
     */
    public List<ProdGoodsCat> qryProdGoodsByGoodsId(String goodsId) {
        log.info("GoodsCatManager qryProdGoodsByGoodsId start, goodsId = [{}]", goodsId);
        List<ProdGoodsCat> result = prodGoodsCatMapper.qryProdGoodsByGoodsId(goodsId);

        if (null == result) {
            result = new ArrayList<>();
        }
        log.info("GoodsCatManager qryProdGoodsByGoodsId end");
        return result;
    }

    /**
     * 根据目录id取所有目录属性
     * 
     * @param req QryGoodsCatAttrListReq
     * @return List<GoodsCatAttrResp>
     */
    public Page<GoodsCatAttrResp> qryAttrListByCatId(QryGoodsCatAttrListReq req) {
        log.info("GoodsCatManager qryAttrListByCatId start, req = [{}]", req);

        Page<GoodsCatAttrResp> page = new Page<>(req.getPageNo(), req.getPageSize());

        Page<GoodsCatAttrResp> prodGoodsCatAttrValues = prodGoodsCatAttrValueMapper.qryProdGoodsCatAttrList(page, req);
        if (CollectionUtils.isNotEmpty(prodGoodsCatAttrValues.getRecords())) {
            prodGoodsCatAttrValues.getRecords().forEach(prodGoodsCatAttrResp -> {
                List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(prodGoodsCatAttrResp.getAttrId());
                prodGoodsCatAttrResp.setAttrValueList(attrValues);
            });
        }

        log.info("GoodsCatManager qryAttrListByCatId end");
        return prodGoodsCatAttrValues;
    }

    /**
     * 根据目录id过滤当前目录已有属性的其他所有属性
     * 
     * @param req QryGoodsCatAttrListReq
     * @return List<GoodsCatAttrResp>
     */
    public Page<GoodsCatAttrResp> qryOutAttrListByCatId(QryGoodsCatAttrListReq req) {
        log.info("GoodsCatManager qryOutAttrListByCatId start, req = [{}]", req);
        Page<GoodsCatAttrResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<GoodsCatAttrResp> prodGoodsCatAttrValues = prodGoodsCatAttrValueMapper.qryOutAttrListByCatId(page, req);
        if (CollectionUtils.isNotEmpty(prodGoodsCatAttrValues.getRecords())) {
            prodGoodsCatAttrValues.getRecords().forEach(prodGoodsCatAttrResp -> {
                List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(prodGoodsCatAttrResp.getAttrId());
                prodGoodsCatAttrResp.setAttrValueList(attrValues);
            });
        }
        log.info("GoodsCatManager qryOutAttrListByCatId end");
        return prodGoodsCatAttrValues;
    }

    public void modGoodsCatAttrList(ModGoodsCatAttrListReq req) throws BaseException {
        log.info("GoodsCatManager modGoodsCatAttrListReq start, req = [{}]", req);
        String modifyBy = getUserId();
        prodGoodsCatAttrValueMapper.deleteCatAttrListByCatId(req.getCatId(), modifyBy);
        if (CollectionUtils.isNotEmpty(req.getProdGoodsCatAttrValueList())) {
            int priority = req.getProdGoodsCatAttrValueList().size();
            for (ProdGoodsCatAttrValueReq prodGoodsCatAttrValueReq : req.getProdGoodsCatAttrValueList()) {
                ProdGoodsCatAttrValue prodGoodsCatAttrValue = new ProdGoodsCatAttrValue();
                prodGoodsCatAttrValueReq.setCatAttrValueId(UidGeneator.getUIDStr());
                prodGoodsCatAttrValueReq.setCatId(req.getCatId());
                prodGoodsCatAttrValueReq.setPriority(Long.valueOf(priority));
                BeanUtils.copyProperties(prodGoodsCatAttrValueReq, prodGoodsCatAttrValue);
                prodGoodsCatAttrValue.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodGoodsCatAttrValue.setCreateBy(modifyBy);
                prodGoodsCatAttrValue.setModifyBy(modifyBy);
                prodGoodsCatAttrValue.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                prodGoodsCatAttrValue.setState(CommonStateDef.ACTIVE);
                priority--;
                prodGoodsCatAttrValueMapper.insert(prodGoodsCatAttrValue);
            }
        }
        log.info("GoodsCatManager modGoodsCatAttrListReq end");
    }

    public List<QryGoodsCatListResp> listProdGoodsCat(QueryOfferCatgReq req) throws BaseException {
        log.info("GoodsCatManager listProdGoodsCat start, QueryOfferCatgReq = [{}]", req);

        // 入参有店铺标识，如果没有店铺标识，查询销售目录列表，如果存在店铺标识，查询店铺的销售目录列表
        List<ProdGoodsCat> prodGoodsCatList;
        if (StringUtils.isEmpty(req.getStoreId())) {
            prodGoodsCatList = prodGoodsCatMapper.listProdGoodsCatByCatTypeIsS();
        }
        else {
            prodGoodsCatList = prodGoodsCatMapper.listProdGoodsCatByStoreId(req.getStoreId());
        }

        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(prodGoodsCatList)) {
            for (ProdGoodsCat prodGoodsCat : prodGoodsCatList) {
                if (StringUtils.isNotEmpty(prodGoodsCat.getLogo())) {
                    prodGoodsCat.setRealLogo(prodGoodsCat.getLogo());
                    String substring = prodGoodsCat.getLogo().substring(prodGoodsCat.getLogo().indexOf('/') + 1);
                    String token = FastdfsTokenUtil.getToken(substring);
                    if (StringUtils.isEmpty(token)) {
                        prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo());
                    }
                    else {
                        prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo() + "?" + token);
                    }
                }
            }
            qryGoodsCatListRespList = getRootGoodsCat(prodGoodsCatList);
            appendGoodsCatChildren(qryGoodsCatListRespList, prodGoodsCatList);
        }

        log.info("GoodsCatManager listProdGoodsCat end");
        return qryGoodsCatListRespList;
    }

    public List<TblGoodsBrandTenantResp> qryGoodBrandByCatgId(String catId) throws BaseException {
        log.info("GoodsCatManager qryGoodBrandByCatgId catId = ", catId);
        AssertUtil.isNotEmpty(catId, OfferBaseMessageCodeEnum.CAT_ID_EMPTY);
        List<TblGoodsBrandTenantResp> result = prodGoodsCatMapper.qryGoodBrandByCatgId(catId);
        if (null == result) {
            result = new ArrayList<>();
        }
        log.info("GoodsCatManager qryGoodBrandByCatgId end");
        return result;
    }

    public void modRelaGoodBrandByCatgId(String catId, ModRelaBrandCatgReq req) throws BaseException {
        log.info("GoodsCatManager modRelaGoodBrandByCatgId start catId = ", catId);
        AssertUtil.isNotEmpty(catId, OfferBaseMessageCodeEnum.CAT_ID_EMPTY);
        // 先删除 后新增
        Map<String, Object> qryMap = new HashMap<>();
        qryMap.put(ColumnNameDef.CAT_ID, catId);
        qryMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<TblGoodsCatBrand> goodsCatRelaBrandList = tblGoodsCatBrandMapper.selectByMap(qryMap);
        List<String> relaIds = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(goodsCatRelaBrandList)) {
            int len = goodsCatRelaBrandList.size();
            for (int i = 0; i < len; i++) {
                relaIds.add(goodsCatRelaBrandList.get(i).getRelaId());
            }
            tblGoodsCatBrandMapper.deleteGoodsCatBrandByIds(relaIds, getUserId());
        }
        // 批量新增
        List<String> relaBrandIds = req.getRelaBrandIds();
        if (CollectionUtils.isNotEmpty(relaBrandIds)) {
            int len = relaBrandIds.size();
            String userId = getUserId();
            for (int i = 0; i < len; i++) {
                TblGoodsCatBrand tblGoodsCatBrand = new TblGoodsCatBrand();
                tblGoodsCatBrand.setRelaId(UidGeneator.getUIDStr());
                tblGoodsCatBrand.setCatId(catId);
                tblGoodsCatBrand.setBrandId(relaBrandIds.get(i));
                tblGoodsCatBrand.setCreateBy(userId);
                tblGoodsCatBrand.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                tblGoodsCatBrand.setModifyBy(userId);
                tblGoodsCatBrand.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                tblGoodsCatBrand.setState(CommonStateDef.ACTIVE);
                tblGoodsCatBrandMapper.insert(tblGoodsCatBrand);
            }
        }
        log.info("GoodsCatManager modRelaGoodBrandByCatgId end");
    }

    public List<QryGoodsCatListResp> qryFormatGoodsCatList(String storeId) throws BaseException {
        log.info("GoodsCatManager qryFormatGoodsCatList start storeId = ", storeId);

        List<String> objIds = prodGoodsCatMapper.qrySaleRuleObj(storeId);

        List<QryGoodsCatListResp> respList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(objIds)) {
            respList = getTreeGoodsCat(objIds);
        }

        log.info("GoodsCatManager qryFormatGoodsCatList end");

        return respList;
    }

    /**
     * 降低复杂度
     */

    private void releaseForTree1(List<String> objIds, List<QryGoodsCatListResp> prodGoodsCatList,
        List<QryGoodsCatListResp> resps, List<String> targetIds) {
        for (String root : objIds) {
            for (QryGoodsCatListResp prodGoodsCat : prodGoodsCatList) {
                if (prodGoodsCat.getCatId().equals(root) && StringUtils.isEmpty(prodGoodsCat.getParentId())) {
                    QryGoodsCatListResp qryGoodsCatListResp = new QryGoodsCatListResp();
                    BeanUtils.copyProperties(prodGoodsCat, qryGoodsCatListResp);
                    resps.add(qryGoodsCatListResp);
                    targetIds.remove(root);
                }
            }
        }
    }

    private void releaseForTree2(List<QryGoodsCatListResp> prodGoodsCatList, List<QryGoodsCatListResp> resps,
        List<String> tempObjIds, int i, List<String> targetIds) {
        for (QryGoodsCatListResp prodGoodsCat : prodGoodsCatList) {
            if (targetIds.get(i).equals(prodGoodsCat.getCatId())) {
                QryGoodsCatListResp tempChild = prodGoodsCat;
                tempObjIds.remove(targetIds.get(i));
                QryGoodsCatListResp treeResp = getTreeNodesFromChild(prodGoodsCatList, tempObjIds, tempChild);
                // 从子节点向上获取根节点
                if (null != treeResp && !resps.contains(treeResp)) {
                    resps.add(getTreeNodesFromChild(prodGoodsCatList, tempObjIds, tempChild));
                }
                if (CollectionUtils.isEmpty(tempObjIds)) {
                    break;
                }
            }
        }
    }

    // 根据子节点寻找父节点
    private List<QryGoodsCatListResp> getTreeGoodsCat(List<String> objIds) {
        QryProdGoodsCatReq req = new QryProdGoodsCatReq();
        req.setCatType(ProdGoodsCatTypeDef.SALE_CAT);

        List<QryGoodsCatListResp> prodGoodsCatList = prodGoodsCatMapper.qryFormatGoodsCatList(req);

        List<QryGoodsCatListResp> resps = new ArrayList<>();

        List<String> targetIds = new ArrayList<>();
        targetIds.addAll(objIds);

        // 取出根节点
        releaseForTree1(objIds, prodGoodsCatList, resps, targetIds);

        // 取出子节点
        List<String> tempObjIds = new ArrayList<>();
        tempObjIds.addAll(targetIds);
        if (CollectionUtils.isNotEmpty(targetIds)) {
            for (int i = targetIds.size() - 1; i >= 0; i--) {
                releaseForTree2(prodGoodsCatList, resps, tempObjIds, i, targetIds);
            }
        }

        return resps;
    }

    private QryGoodsCatListResp getTreeNodesFromChild(List<QryGoodsCatListResp> prodGoodsCatList,
        List<String> targetIds, QryGoodsCatListResp tempChild) {
        if (StringUtils.isNotEmpty(tempChild.getParentId())) {
            QryGoodsCatListResp tempParentCat = new QryGoodsCatListResp();
            for (QryGoodsCatListResp parentCat : prodGoodsCatList) {
                // 判断是否为父节点
                if (parentCat.getCatId().equals(tempChild.getParentId())) {

                    List<QryGoodsCatListResp> tempChildren = new ArrayList<>();
                    getChildren(tempChild, parentCat, tempChildren);

                    tempParentCat = checkByTargetIds(prodGoodsCatList, targetIds, tempParentCat, parentCat);
                    if (StringUtils.isEmpty(parentCat.getParentId())) {
                        return parentCat;
                    }
                    else if (StringUtils.isEmpty(tempParentCat.getCatId())) {
                        tempParentCat = parentCat;
                    }
                }
            }

            return getTreeNodesFromChild(prodGoodsCatList, targetIds, tempParentCat);
        }

        return tempChild;
    }

    private QryGoodsCatListResp checkByTargetIds(List<QryGoodsCatListResp> prodGoodsCatList, List<String> targetIds,
        QryGoodsCatListResp tempParentCat, QryGoodsCatListResp parentCat) {
        if (targetIds.isEmpty()) {
            // 判断父节点下是否有其他子节点
            for (QryGoodsCatListResp childCat : prodGoodsCatList) {
                boolean exists = parentCat.getCatId().equals(childCat.getParentId())
                    && CollectionUtils.isNotEmpty(targetIds) && targetIds.contains(childCat.getCatId());
                if (exists) {
                    List<QryGoodsCatListResp> temp = parentCat.getChildren();
                    if (!temp.contains(childCat)) {
                        temp.add(childCat);
                        parentCat.setChildren(temp);
                        targetIds.remove(childCat.getCatId());
                        tempParentCat = parentCat;
                    }
                }
            }
        }
        return tempParentCat;
    }

    private void getChildren(QryGoodsCatListResp tempChild, QryGoodsCatListResp parentCat,
        List<QryGoodsCatListResp> tempChildren) {
        if (!CollectionUtils.isEmpty(parentCat.getChildren())) {
            tempChildren = parentCat.getChildren();
        }
        if (!tempChildren.contains(tempChild)) {
            tempChildren.add(tempChild);
        }
        parentCat.setChildren(tempChildren);
    }

    public ProdGoodsCat qryGoodsCatInfoByCatId(String catId) throws BaseException {
        AssertUtil.isNotEmpty(catId, OfferBaseMessageCodeEnum.CAT_ID_EMPTY);

        return prodGoodsCatMapper.selectById(catId);

    }

    public ArrayList<ProdGoodsCat> qryAllChildMgnCat() {
        // 只有平台管理员能查看所有的管理目录（管理目录只能是平台管理员创建，平台管理员操作，如果租户管理员进来，就查到的是空的）
        // 目录表里的storeId目前没用到
        ArrayList<ProdGoodsCat> prodGoodsCatList = prodGoodsCatMapper.qryAllChildMgnCat();

        if (null == prodGoodsCatList) {
            prodGoodsCatList = new ArrayList<>();
        }

        return prodGoodsCatList;
    }

    public void modInvoiceFlag(String catId) throws BaseException {
        AssertUtil.isNotEmpty(catId, OfferBaseMessageCodeEnum.CAT_ID_EMPTY);
        ProdGoodsCat prodGoodsCat = prodGoodsCatMapper.selectById(catId);

        if (null == prodGoodsCat || !ProdGoodsCatTypeDef.MANAGEMENT_CAT.equals(prodGoodsCat.getCatType())
            || "Y".equals(prodGoodsCat.getInvoiceFlag())) {
            throw new BaseException(OfferBaseMessageCodeEnum.MGN_CAT_CAN_NOT_MOD_INVOICE_FALG);
        }

        prodGoodsCat.setInvoiceFlag("Y");
        prodGoodsCat.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsCat.setModifyBy(getUserId());
        prodGoodsCatMapper.updateById(prodGoodsCat);

        CmSyncGoodsItemInfoReq cmSyncGoodsItemInfoReq = new CmSyncGoodsItemInfoReq();
        cmSyncGoodsItemInfoReq.setRequestId(UidGeneator.getUIDStr());

        List<CmGoodsItemInfo> goodsItemInfos = new ArrayList<>();
        CmGoodsItemInfo cmGoodsItemInfo = new CmGoodsItemInfo();
        cmGoodsItemInfo.setGoodsItemName(prodGoodsCat.getName());
        cmGoodsItemInfo.setGoodsItemSerial(prodGoodsCat.getCatId());
        goodsItemInfos.add(cmGoodsItemInfo);
        cmSyncGoodsItemInfoReq.setGoodsItemInfos(goodsItemInfos);

        CmWsResponse<List<CmSyncGoodsItemInfoResp>> wsResponse = cmInvoiceService
            .syncGoodsItemInfo(cmSyncGoodsItemInfoReq);
        CmWsResponseCheckUtil.checkResponse(wsResponse);

        List<CmSyncGoodsItemInfoResp> cmSyncGoodsItemInfoResp = wsResponse.getResult();
        CmSyncGoodsItemInfoResp cmSyncGoodsItemInfo = cmSyncGoodsItemInfoResp.get(0);
        if (null == cmSyncGoodsItemInfo.getSyncResult() || !cmSyncGoodsItemInfo.getSyncResult()) {
            throw new BaseException(OfferBaseMessageCodeEnum.SYN_CAT_FAIL);
        }

    }

    public Boolean canCreateSubs(String catId) throws BaseException {
        AssertUtil.isNotEmpty(catId, OfferBaseMessageCodeEnum.CAT_ID_EMPTY);
        int count = prodGoodsCatMapper.selectGoodsCountByCatId(catId);
        if (count > 0) {
            // 销售目录下关联商品, 不可创建
            return false;
        }
        return true;
    }
}
