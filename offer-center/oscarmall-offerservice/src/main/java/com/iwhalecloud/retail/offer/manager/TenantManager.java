package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.TenantApplyCatgStateDef;
import com.iwhalecloud.retail.offer.dto.req.AddManagementCatReq;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatListResp;
import com.iwhalecloud.retail.offer.dto.resp.TenantDetailResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import com.iwhalecloud.retail.offer.entity.Tenant;
import com.iwhalecloud.retail.offer.entity.TenantApplyCatg;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMapper;
import com.iwhalecloud.retail.offer.mapper.TenantApplyCatgMapper;
import com.iwhalecloud.retail.offer.mapper.TenantMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 租户
 * @author fanxiaofei
 * @date 2019/05/22
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TenantManager {


    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private TenantApplyCatgMapper tenantApplyCatgMapper;

    @Autowired
    private ProdGoodsCatMapper prodGoodsCatMapper;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    /**
     * 租户详情
     * @param tenantId String
     * @return TenantDetailResp
     */
    public TenantDetailResp queryTenantDetail(String tenantId) throws BaseException {
        log.info("TenantManager queryTenantDetail start, tenantId = [{}]", tenantId);

        AssertUtil.isNotNull(tenantId, OfferBaseMessageCodeEnum.TENANT_ID_IS_NULL);
        Tenant tenant = tenantMapper.selectById(tenantId);
        AssertUtil.isNotNull(tenant, OfferBaseMessageCodeEnum.TENANT_ID_IS_ERROR);
        TenantDetailResp result = new TenantDetailResp();
        BeanUtils.copyProperties(tenant, result);

        log.info("TenantManager queryTenantDetail end");
        return result;
    }


    /**
     * 租户所有可使用的目录标识
     * @param tenantId String
     * @return ArrayList<String>
     */
    public ArrayList<String> listManagementCatIdByTenantId(String tenantId) throws BaseException {
        log.info("TenantManager listManagementCatIdByTenantId start, tenantId = [{}]", tenantId);

        // 参数校验
        AssertUtil.isNotNull(tenantId, OfferBaseMessageCodeEnum.TENANT_ID_IS_NULL);
        // 构造返回值
        List<String> catIdList = new ArrayList<>();
        List<ProdGoodsCat> prodGoodsCatList = tenantMapper.listManagementCatByTenantId(tenantId);
        // 前端插件：排除所有 有子节点的父节点
        prodGoodsCatList.forEach(prodGoodsCat -> {
            String parentId = prodGoodsCat.getParentId();
            // 无父节点 第一层  字节点为空加入/ 字节点不为空不加入
            if (StringUtils.isEmpty(parentId)) {
                // 且无字节点
                List<ProdGoodsCat> list = prodGoodsCatMapper.qryChildProdGoodsCatList(prodGoodsCat.getCatId());
                if (CollectionUtils.isEmpty(list)) {
                    catIdList.add(prodGoodsCat.getCatId());
                }
            }
            else {
                // 有父节点 中间节点/叶子节点
                catIdList.add(prodGoodsCat.getCatId());
            }
        });

        log.info("TenantManager listManagementCatIdByTenantId end");
        return (ArrayList<String>) catIdList;
    }


    /**
     * 租户所有可使用的商品目录
     * @param tenantId String
     * @return ArrayList<QryGoodsCatListResp>
     */
    public ArrayList<QryGoodsCatListResp> listManagementCatByTenantId(String tenantId) throws BaseException {
        log.info("TenantManager listManagementCatByTenantId start, tenantId = [{}]", tenantId);

        // 参数校验
        AssertUtil.isNotNull(tenantId, OfferBaseMessageCodeEnum.TENANT_ID_IS_NULL);

        // 构造返回值
        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();
        // 查询租户下分配的管理目录
        List<ProdGoodsCat> prodGoodsCatList = tenantMapper.listManagementCatByTenantId(tenantId);
        if (CollectionUtils.isNotEmpty(prodGoodsCatList)) {
            for (ProdGoodsCat prodGoodsCat : prodGoodsCatList) {
                if (StringUtils.isNotEmpty(prodGoodsCat.getLogo())) {
                    prodGoodsCat.setRealLogo(prodGoodsCat.getLogo());
                    prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo());
                    String substring = prodGoodsCat.getLogo().substring(prodGoodsCat.getLogo().indexOf('/') + 1);
                    String token = FastdfsTokenUtil.getToken(substring);
                    if (StringUtils.isNotEmpty(token)) {
                        prodGoodsCat.setLogo(showUrl + prodGoodsCat.getLogo() + "?" + token);
                    }
                }
            }
            qryGoodsCatListRespList = getRootGoodsCat(prodGoodsCatList);
            appendGoodsCatChildren(qryGoodsCatListRespList, prodGoodsCatList);
        }

        log.info("TenantManager listManagementCatByTenantId end");
        return (ArrayList<QryGoodsCatListResp>) qryGoodsCatListRespList;
    }


    /**
     * 得到所有父级目录并剔除
     * @param prodGoodsCatList 租户所有可使用的商品目录
     * @return List<QryGoodsCatListResp> 所有父级目录
     */
    private List<QryGoodsCatListResp> getRootGoodsCat(List<ProdGoodsCat> prodGoodsCatList) {
        log.info("TenantManager getRootGoodsCat start, prodGoodsCatList = [{}]", prodGoodsCatList);

        Iterator<ProdGoodsCat> iterator = prodGoodsCatList.iterator();
        List<QryGoodsCatListResp> qryGoodsCatListRespList = new ArrayList<>();
        while (iterator.hasNext()) {
            ProdGoodsCat prodGoodsCat = iterator.next();
            if (StringUtils.isEmpty(prodGoodsCat.getParentId())) {
                QryGoodsCatListResp goodsCatResp = new QryGoodsCatListResp();
                BeanUtils.copyProperties(prodGoodsCat, goodsCatResp);
                iterator.remove();
                qryGoodsCatListRespList.add(goodsCatResp);
            }
        }

        log.info("TenantManager getRootGoodsCat end");
        return qryGoodsCatListRespList;
    }


    /**
     * 递归填充子级目录
     * 前端要求一次性展示,理论上后台不允许使用递归
     * @param qryGoodsCatListRespList 父级目录
     * @param prodGoodsCatList 租户所有可使用的商品目录已剔除父级目录
     */
    private void appendGoodsCatChildren(List<QryGoodsCatListResp> qryGoodsCatListRespList, List<ProdGoodsCat> prodGoodsCatList) {
        log.info("TenantManager appendGoodsCatChildren start, qryGoodsCatListRespList = [{}], prodGoodsCatList = [{}]",
                qryGoodsCatListRespList, prodGoodsCatList);

        Iterator<ProdGoodsCat> iterator = prodGoodsCatList.iterator();
        if (CollectionUtils.isNotEmpty(qryGoodsCatListRespList) && iterator.hasNext()) {
            for (QryGoodsCatListResp parentNode : qryGoodsCatListRespList) {
                List<QryGoodsCatListResp> childrenList = new ArrayList<>();
                iterator = prodGoodsCatList.iterator();
                while (iterator.hasNext()) {
                    ProdGoodsCat prodGoodsCat = iterator.next();
                    if (prodGoodsCat.getParentId().equals(parentNode.getCatId())) {
                        QryGoodsCatListResp childrenNode = new QryGoodsCatListResp();
                        BeanUtils.copyProperties(prodGoodsCat, childrenNode);
                        childrenList.add(childrenNode);
                        iterator.remove();
                    }
                }
                parentNode.setChildren(childrenList);
                appendGoodsCatChildren(childrenList, prodGoodsCatList);
            }
        }

        log.info("TenantManager appendGoodsCatChildren end");
    }


    /**
     * 新增租户可使用的商品目录
     * @param req AddManagementCatReq
     */
    public void addManagementCat(AddManagementCatReq req) throws BaseException {
        log.info("TenantManager addManagementCat start, AddManagementCatReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        String tenantId = req.getTenantId();
        AssertUtil.isNotEmpty(tenantId, OfferBaseMessageCodeEnum.TENANT_ID_IS_NULL);

        // 先删除再新增
        tenantMapper.deleteManagementCatByTenantId(req);
        String createdStaffId = req.getModifyBy();
        if (CollectionUtils.isNotEmpty(req.getCatIdList())) {
            req.getCatIdList().forEach(catId -> {
                Date now = DBDateUtil.getCurrentDBDateTime();
                TenantApplyCatg tenantApplyCatg = new TenantApplyCatg();
                tenantApplyCatg.setCatId(catId);
                tenantApplyCatg.setStateDate(now);
                tenantApplyCatg.setCreateTime(now);
                tenantApplyCatg.setModifyTime(now);
                tenantApplyCatg.setModifyBy(createdStaffId);
                tenantApplyCatg.setTenantId(tenantId);
                tenantApplyCatg.setCreateBy(createdStaffId);
                tenantApplyCatg.setApplyId(UidGeneator.getUIDStr());
                tenantApplyCatg.setState(TenantApplyCatgStateDef.STATE_A);
                tenantApplyCatgMapper.addTenantApplyCatg(tenantApplyCatg);
            });
        }

        log.info("TenantManager addManagementCat end");
    }

}
