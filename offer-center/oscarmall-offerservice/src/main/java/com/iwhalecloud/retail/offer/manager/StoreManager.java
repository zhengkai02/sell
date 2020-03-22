package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.ProdGoodsStateDef;
import com.iwhalecloud.retail.offer.consts.StoreStateDef;
import com.iwhalecloud.retail.offer.dto.req.AddStoreReq;
import com.iwhalecloud.retail.offer.dto.req.ModStoreStateReq;
import com.iwhalecloud.retail.offer.dto.req.QryStoreReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreByOrgIdReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreReq;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreByOrgIdResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreDetailResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreRuleInstResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreRuleResp;
import com.iwhalecloud.retail.offer.entity.Store;
import com.iwhalecloud.retail.offer.entity.StoreRule;
import com.iwhalecloud.retail.offer.entity.StoreRuleInst;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.StoreMapper;
import com.iwhalecloud.retail.offer.mapper.StoreRuleInstMapper;
import com.iwhalecloud.retail.offer.mapper.StoreRuleMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 店铺
 * 
 * @author fanxiaofei
 * @date 2019/04/29
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StoreManager {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreRuleInstMapper storeRuleInstMapper;

    @Autowired
    private StoreRuleMapper storeRuleMapper;

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    @Autowired
    private CommonManager commonManager;

    /**
     * 新增店铺
     * 
     * @param req AddStoreReq
     */
    public void insert(AddStoreReq req) throws BaseException {
        log.info("StoreManager insert start, AddStoreReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getStoreName(), OfferBaseMessageCodeEnum.NAME_IS_NULL);
        AssertUtil.isNotNull(req.getOrgId(), OfferBaseMessageCodeEnum.ORG_ID_IS_NULL);
        checkBankNameAndBankNumber(req.getBankName(), req.getBankNumber());
        QueryStoreResp queryStoreResp = storeMapper.queryStoreByStoreName(req.getStoreName());
        if (queryStoreResp != null) {
            throw new BaseException(OfferBaseMessageCodeEnum.NAME_IS_EXIST);
        }
        AssertUtil.isNotNull(req.getCreateBy(), OfferBaseMessageCodeEnum.CREATED_STAFF_ID_IS_ERROR);
        // 新增店铺
        Store store = new Store();
        BeanUtils.copyProperties(req, store);
        Date date = DBDateUtil.getCurrentDBDateTime();
        store.setStoreId(UidGeneator.getUIDStr());
        store.setState(StoreStateDef.STATE_A);
        store.setStateDate(date);
        store.setCreateTime(date);
        store.setModifyTime(date);
        store.setModifyBy(req.getCreateBy());
        store.setCreateBy(req.getCreateBy());
        storeMapper.insert(store);
        // 新增店铺适用规则实例
        List<QueryStoreRuleResp> storeRules = req.getStoreRules();
        if (CollectionUtils.isNotEmpty(storeRules)) {
            for (QueryStoreRuleResp queryStoreRuleResp : storeRules) {
                List<String> objIds = queryStoreRuleResp.getObjIds();
                AssertUtil.isNotNull(objIds, OfferBaseMessageCodeEnum.STORE_RULES_IS_NULL);
                objIds.forEach(obj -> {
                    StoreRuleInst storeRuleInst = new StoreRuleInst();
                    storeRuleInst.setRuleInstId(UidGeneator.getUIDStr());
                    storeRuleInst.setObjId(obj);
                    storeRuleInst.setStoreId(store.getStoreId());
                    storeRuleInst.setRuleId(queryStoreRuleResp.getRuleId());
                    storeRuleInst.setCreateBy(req.getCreateBy());
                    storeRuleInst.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    storeRuleInst.setModifyBy(req.getCreateBy());
                    storeRuleInst.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    storeRuleInst.setState(CommonStateDef.ACTIVE);
                    storeRuleInstMapper.insert(storeRuleInst);
                });
            }
        }

        log.info("StoreManager insert end");

    }

    /**
     * 查询所有
     * 
     * @return Page<AddStoreLevelReq>
     */
    public List<QueryStoreResp> queryStore() {
        log.info("StoreManager queryStore start");
        List<QueryStoreResp> result = storeMapper.queryStore();
        log.info("StoreManager queryStore end");
        return result;
    }

    /**
     * 分页查询
     * 
     * @param req QueryStoreLevelReq
     * @return Page<AddStoreLevelReq>
     */
    public Page<QueryStoreResp> queryStorePage(QueryStoreReq req) {
        log.info("StoreManager queryStorePage start, QueryStoreReq = [{}]", req);

        Integer pageNo = req.getPageNo();
        if (null == pageNo) {
            pageNo = 1;
        }
        Integer pageSize = req.getPageSize();
        final Integer size = 20;
        if (null == pageSize) {
            pageSize = size;
        }
        Page<QueryStoreResp> page = new Page<>(pageNo, pageSize);
        Page<QueryStoreResp> result = storeMapper.queryStorePage(page, req);

        log.info("StoreManager queryStorePage end");
        return result;
    }

    public ArrayList<QueryStoreResp> qryStoreByCond(QryStoreReq req) throws BaseException {
        log.info("StoreManager qryStoreByCond start, req = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        ArrayList<QueryStoreResp> result = new ArrayList<>();
        List<QueryStoreResp> queryStoreResps = storeMapper.qryStoreByCond(req);
        if (CollectionUtils.isNotEmpty(queryStoreResps)) {
            result.addAll(queryStoreResps);
        }

        log.info("StoreManager qryStoreByCond end, result = [{}]", result);
        return result;
    }

    /**
     * 店铺详情
     * 
     * @param storeId String
     * @return QueryStoreDetailResp
     */
    public QueryStoreDetailResp queryStoreDetail(String storeId) throws BaseException {
        log.info("StoreManager queryStoreDetail start, storeId = [{}]", storeId);

        AssertUtil.isNotNull(storeId, OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
        QueryStoreDetailResp result = storeMapper.queryStoreDetail(storeId);
        AssertUtil.isNotNull(result, OfferBaseMessageCodeEnum.STORE_ID_IS_ERROR);
        // 封装店铺适用规则
        List<QueryStoreRuleResp> storeRules = appendStoreRules(storeId);
        result.setStoreRules(storeRules);
        // 店铺机构名称在创建店铺时候，机构名称和店铺名称一样
        result.setOrgName(result.getStoreName());
        // 封装头像,分装要求按照前端 storeImg展示放整个url,原storeImg封装进realUrl
        String storeImg = result.getStoreImg();
        if (StringUtils.isNotEmpty(storeImg)) {
            result.setRealUrl(storeImg);
            String subsString = storeImg.substring(storeImg.indexOf('/') + 1);
            String token = FastdfsTokenUtil.getToken(subsString);
            if (StringUtils.isNotEmpty(token)) {
                result.setStoreImg(showUrl + storeImg + "?" + token);
            }
            else {
                result.setStoreImg(showUrl + storeImg);
            }
        }

        // 封装附件,分装要求按照前端 certAttachment展示放整个url,原certAttachment封装进realAttachment
        String certAttachment = result.getCertAttachment();
        if (StringUtils.isNotEmpty(certAttachment)) {
            result.setRealAttachment(certAttachment);
            String subsString = certAttachment.substring(certAttachment.indexOf('/') + 1);
            result.setAttachmentName(certAttachment.substring(certAttachment.lastIndexOf('/') + 1));
            String token = FastdfsTokenUtil.getToken(subsString);
            if (StringUtils.isNotEmpty(token)) {
                result.setCertAttachment(showUrl + certAttachment + "?" + token);
            }
            else {
                result.setCertAttachment(showUrl + certAttachment);
            }
        }

        log.info("StoreManager queryStoreDetail end");
        return result;
    }

    /**
     * 根据机构id查询店铺详情
     * 
     * @param orgId String
     * @return QueryStoreDetailResp
     */
    public QueryStoreDetailResp queryStoreDetailByOrgId(String orgId) throws BaseException {
        log.info("StoreManager queryStoreDetailByOrgId start, orgId = [{}]", orgId);

        AssertUtil.isNotNull(orgId, OfferBaseMessageCodeEnum.ORG_ID_IS_NULL);
        QueryStoreDetailResp result = storeMapper.queryStoreDetailByOrgId(orgId);
        AssertUtil.isNotNull(result, OfferBaseMessageCodeEnum.STORE_ID_IS_ERROR);

        // 封装店铺适用规则
        List<QueryStoreRuleResp> storeRules = appendStoreRules(result.getStoreId());
        result.setStoreRules(storeRules);

        // 店铺机构名称在创建店铺时候，机构名称和店铺名称一样
        result.setOrgName(result.getStoreName());

        // 封装头像,分装要求按照前端 storeImg展示放整个url,原storeImg封装进realUrl
        String storeImg = result.getStoreImg();
        if (StringUtils.isNotEmpty(storeImg)) {
            result.setRealUrl(storeImg);
            String subsString = storeImg.substring(storeImg.indexOf('/') + 1);
            String token = FastdfsTokenUtil.getToken(subsString);
            if (StringUtils.isEmpty(token)) {
                result.setStoreImg(showUrl + storeImg);
            }
            else {
                result.setStoreImg(showUrl + storeImg + "?" + token);
            }
        }

        // 封装附件,分装要求按照前端 certAttachment展示放整个url,原certAttachment封装进realAttachment
        String certAttachment = result.getCertAttachment();
        if (StringUtils.isNotEmpty(certAttachment)) {
            result.setRealAttachment(certAttachment);
            String subsString = certAttachment.substring(certAttachment.indexOf('/') + 1);
            result.setAttachmentName(certAttachment.substring(certAttachment.lastIndexOf('/') + 1));
            String token = FastdfsTokenUtil.getToken(subsString);
            if (StringUtils.isEmpty(token)) {
                result.setCertAttachment(showUrl + certAttachment);
            }
            else {
                result.setCertAttachment(showUrl + certAttachment + "?" + token);
            }
        }

        log.info("StoreManager queryStoreDetailByOrgId end");
        return result;
    }

    /**
     * 封装店铺适用规则
     * 
     * @param storeId String
     * @return List<QueryStoreRuleResp>
     */
    private List<QueryStoreRuleResp> appendStoreRules(String storeId) {
        log.info("StoreManager appendStoreRules start, storeId = [{}]", storeId);

        List<QueryStoreRuleResp> storeRuleRespList = new ArrayList<>();

        List<QueryStoreRuleInstResp> storeRuleInstResps = storeRuleInstMapper.listStoreRuleInstByStoreId(storeId);
        if (CollectionUtils.isNotEmpty(storeRuleInstResps)) {
            QueryStoreRuleResp target = new QueryStoreRuleResp();
            final int idForRule = 4;
            target.setRuleId(idForRule);
            List<String> objIdList = new ArrayList<>();
            for (QueryStoreRuleInstResp storeRuleInstResp : storeRuleInstResps) {
                if (storeRuleInstResp.getRuleId() == idForRule) {
                    objIdList.add(storeRuleInstResp.getObjId());
                }
            }
            target.setObjIds(objIdList);
            storeRuleRespList.add(target);
        }

        log.info("StoreManager appendStoreRules end");
        return storeRuleRespList;
    }

    /**
     * 关店
     * 
     * @param req AddStoreReq
     */
    public void delete(AddStoreReq req) throws BaseException {
        log.info("StoreManager delete start, AddStoreReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getStoreId(), OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);

        // 该店铺下有子店铺不允许删除
        int storeNum = storeMapper.countParentStoreById(req.getStoreId());
        if (storeNum > 0) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_HAS_CHILD_STORE);
        }
        Store store = storeMapper.selectById(req.getStoreId());
        AssertUtil.isNotNull(store, OfferBaseMessageCodeEnum.STORE_LEVEL_ID_IS_NULL);
        Date date = DBDateUtil.getCurrentDBDateTime();
        store.setState(StoreStateDef.STATE_E);
        store.setStateDate(date);
        store.setModifyTime(date);
        store.setModifyBy(req.getModifyBy());
        store.setCloseReason(req.getCloseReason());
        storeMapper.updateById(store);

        log.info("StoreManager delete end");
    }

    /**
     * 编辑
     * 
     * @param req AddStoreReq
     */
    public void update(AddStoreReq req) throws BaseException {
        log.info("StoreManager update start, AddStoreReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getStoreName(), OfferBaseMessageCodeEnum.NAME_IS_NULL);
        AssertUtil.isNotNull(req.getStoreId(), OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
        checkBankNameAndBankNumber(req.getBankName(), req.getBankNumber());
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);

        Store store = storeMapper.selectById(req.getStoreId());
        AssertUtil.isNotNull(store, OfferBaseMessageCodeEnum.STORE_LEVEL_ID_IS_NULL);
        QueryStoreResp queryStoreResp = storeMapper.queryStoreByStoreName(req.getStoreName());
        if (queryStoreResp != null && !queryStoreResp.getStoreId().equals(req.getStoreId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.NAME_IS_EXIST);
        }
        BeanUtils.copyProperties(req, store);
        Date date = DBDateUtil.getCurrentDBDateTime();
        store.setModifyTime(date);
        store.setModifyBy(req.getModifyBy());
        store.setState(StoreStateDef.STATE_A);
        storeMapper.updateById(store);
        // 查询所有规则实例
        List<String> ruleInstIds = storeRuleInstMapper.queryIdByStoreId(req.getStoreId());
        // 先删除再新增
        if (CollectionUtils.isNotEmpty(ruleInstIds)) {
            storeRuleInstMapper.deleteStoreRuleInstByIds(ruleInstIds, req.getModifyBy());
        }
        // 新增店铺适用规则实例
        List<QueryStoreRuleResp> storeRules = req.getStoreRules();
        if (CollectionUtils.isNotEmpty(storeRules)) {
            for (QueryStoreRuleResp queryStoreRuleResp : storeRules) {
                List<String> objIds = queryStoreRuleResp.getObjIds();
                AssertUtil.isNotNull(objIds, OfferBaseMessageCodeEnum.STORE_RULES_IS_NULL);
                objIds.forEach(obj -> {
                    StoreRuleInst storeRuleInst = new StoreRuleInst();
                    storeRuleInst.setObjId(obj);
                    storeRuleInst.setStoreId(store.getStoreId());
                    storeRuleInst.setRuleInstId(UidGeneator.getUIDStr());
                    storeRuleInst.setRuleId(queryStoreRuleResp.getRuleId());
                    storeRuleInst.setModifyBy(req.getModifyBy());
                    storeRuleInst.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    storeRuleInst.setCreateBy(req.getModifyBy());
                    storeRuleInst.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    storeRuleInst.setState(CommonStateDef.ACTIVE);
                    storeRuleInstMapper.insert(storeRuleInst);
                });
            }
        }

        // 修改店铺以后店铺上架商品全部下架
        prodGoodsMapper.updateProGoodsStateByStoreId(req.getStoreId(), req.getModifyBy(), ProdGoodsStateDef.PUT_ON_SALE,
            ProdGoodsStateDef.PULL_OFF_SALE);
        // 清空店铺下所有商品在redis数据
        List<String> proGoodsIdList = prodGoodsMapper.queryProGoodsIdByStoreId(req.getStoreId());
        log.info("StoreManager queryProGoodsIdByStoreId proGoodsIdList = [{}]", proGoodsIdList);
        if (CollectionUtils.isNotEmpty(proGoodsIdList)) {
            for (String goodsId : proGoodsIdList) {
                commonManager.cacheClear(goodsId);
            }
        }

        log.info("StoreManager update end");
    }

    /**
     * 提交认证请求
     * 
     * @param req AddStoreReq
     */
    public void submitAuthRequest(AddStoreReq req) throws BaseException {
        log.info("StoreManager submitAuthRequest start, AddStoreReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getStoreId(), OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);

        Store store = storeMapper.selectById(req.getStoreId());
        AssertUtil.isNotNull(store, OfferBaseMessageCodeEnum.STORE_IS_NOT_EXISTS);
        store.setState(StoreStateDef.STATE_B);
        Date date = DBDateUtil.getCurrentDBDateTime();
        store.setModifyTime(date);
        store.setModifyBy(req.getModifyBy());
        store.setCertAttachment(req.getCertAttachment());
        storeMapper.updateById(store);

        log.info("StoreManager submitAuthRequest end");
    }

    /**
     * 认证
     * 
     * @param req AddStoreReq
     */
    public void authentication(AddStoreReq req) throws BaseException {
        log.info("StoreManager authentication start, AddStoreReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getStoreId(), OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
        AssertUtil.isNotNull(req.getState(), OfferBaseMessageCodeEnum.STORE_STATE_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);

        Store store = storeMapper.selectById(req.getStoreId());
        AssertUtil.isNotNull(store, OfferBaseMessageCodeEnum.STORE_IS_NOT_EXISTS);
        Date date = DBDateUtil.getCurrentDBDateTime();
        store.setModifyTime(date);
        store.setModifyBy(req.getModifyBy());
        if (req.getState().equals(StoreStateDef.STATE_B)) {
            store.setState(StoreStateDef.STATE_B);
            store.setAuthReason(req.getAuthReason());
        }
        else {
            store.setState(StoreStateDef.STATE_C);
        }
        storeMapper.updateById(store);

        log.info("StoreManager authentication end");
    }

    /**
     * 查询店铺适用规则
     */
    public List<QueryStoreRuleResp> qryAllStoreRuleList() {
        log.info("StoreManager qryStoreRuleList start");

        List<QueryStoreRuleResp> storeRuleRespList = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<StoreRule> storeRuleResps = storeRuleMapper.selectList(queryWrapper);
        storeRuleResps.forEach(storeRule -> {
            QueryStoreRuleResp resp = new QueryStoreRuleResp();
            BeanUtils.copyProperties(storeRule, resp);
            storeRuleRespList.add(resp);
        });

        log.info("StoreManager qryStoreRuleList end");
        return storeRuleRespList;
    }

    /**
     * 修改状态
     * 
     * @param req ModStoreStateReq
     */
    public void modStoreState(ModStoreStateReq req) throws BaseException {
        log.info("StoreManager modStoreState start, ModStoreStateReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getStoreId(), OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
        AssertUtil.isNotNull(req.getState(), OfferBaseMessageCodeEnum.STATE_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);

        Store store = storeMapper.selectById(req.getStoreId());
        AssertUtil.isNotNull(store, OfferBaseMessageCodeEnum.STORE_IS_NOT_EXISTS);
        Date date = DBDateUtil.getCurrentDBDateTime();
        store.setModifyTime(date);
        store.setState(req.getState());
        store.setModifyBy(req.getModifyBy());
        storeMapper.updateById(store);

        if (StoreStateDef.STATE_E.equals(req.getState())) {
            // 修改店铺以后店铺上架商品全部下架
            prodGoodsMapper.updateProGoodsStateByStoreId(req.getStoreId(), req.getModifyBy(), ProdGoodsStateDef.PUT_ON_SALE, ProdGoodsStateDef.PULL_OFF_SALE);
            // 清空店铺下所有商品在redis数据
            List<String> proGoodsIdList = prodGoodsMapper.queryProGoodsIdByStoreId(req.getStoreId());
            log.info("StoreManager queryProGoodsIdByStoreId proGoodsIdList = [{}]", proGoodsIdList);
            if (CollectionUtils.isNotEmpty(proGoodsIdList)) {
                for (String goodsId : proGoodsIdList) {
                    commonManager.cacheClear(goodsId);
                }
            }
        }

        log.info("StoreManager modStoreState end");
    }

    /**
     * 根据机构Id查询关联的店铺列表 亚信调用
     * 
     * @param req QueryStoreByOrgIdReq
     * @return ArrayList<QueryStoreByOrgIdResp>
     */
    public ArrayList<QueryStoreByOrgIdResp> qryStoreByOrgId(QueryStoreByOrgIdReq req) throws BaseException {
        log.info("StoreManager qryStoreByOrgId start, QueryStoreByOrgIdReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getOrgId(), OfferBaseMessageCodeEnum.ORG_ID_IS_NULL);

        List<QueryStoreByOrgIdResp> result = storeMapper.qryStoreByOrgId(req.getOrgId());

        log.info("StoreManager qryStoreByOrgId end");
        return (ArrayList<QueryStoreByOrgIdResp>) result;
    }

    /**
     * 审批店铺
     * 
     * @param req AddApprovalsReq
     */
    public void auditStore(AddStoreReq req) throws BaseException {
        log.info("StoreManager auditStore start, req = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getStoreId(), OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
        AssertUtil.isNotNull(req.getOrgId(), OfferBaseMessageCodeEnum.ORG_ID_IS_NULL);
        AssertUtil.isNotNull(req.getState(), OfferBaseMessageCodeEnum.STATE_IS_ERROR);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);

        // 更新店铺状态
        Store store = storeMapper.selectById(req.getStoreId());
        AssertUtil.isNotNull(store, OfferBaseMessageCodeEnum.STORE_IS_NOT_EXISTS);
        log.info("StoreManager auditStore selectById, store = [{}]", store);
        Date date = DBDateUtil.getCurrentDBDateTime();
        store.setModifyTime(date);
        store.setState(req.getState());
        store.setModifyBy(req.getModifyBy());
        storeMapper.updateById(store);

        log.info("StoreManager auditStore end");
    }

    /**
     * 银行开户名和银行卡账号格式检验
     */
    private void checkBankNameAndBankNumber(String bankName, String bankNumer) throws BaseException {
        String regex = "^[0-9]+$";
        if (bankName != null && bankName.length() > 120) {
            throw new BaseException(OfferBaseMessageCodeEnum.BANK_NAME_IS_ERROR);
        }
        if (bankNumer != null && (bankNumer.length() > 30 || !bankNumer.matches(regex))) {
            throw new BaseException(OfferBaseMessageCodeEnum.BANK_NUMBER_IS_ERROR);
        }
    }

}
