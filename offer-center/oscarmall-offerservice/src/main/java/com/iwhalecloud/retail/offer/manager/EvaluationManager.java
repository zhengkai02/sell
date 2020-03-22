package com.iwhalecloud.retail.offer.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.DateUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.IPUtil;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.AbstractEvaluationDef;
import com.iwhalecloud.retail.offer.consts.AbstractEvaluationLogDef;
import com.iwhalecloud.retail.offer.consts.AbstractOperLogDef;
import com.iwhalecloud.retail.offer.consts.AbstractSensitiveWordsDef;
import com.iwhalecloud.retail.offer.consts.AbstractUsefulUselessDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.ObjectTypeDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseResponseDTO;
import com.iwhalecloud.retail.offer.dto.req.BatchAuditEvaluationReq;
import com.iwhalecloud.retail.offer.dto.req.ContentBaseQryReq;
import com.iwhalecloud.retail.offer.dto.req.ContentEvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationItemReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationModeReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsEvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.QueryContactChannelReq;
import com.iwhalecloud.retail.offer.dto.req.QueryEvaluationByUserIdAndObjReq;
import com.iwhalecloud.retail.offer.dto.req.QueryUsefulUselessReq;
import com.iwhalecloud.retail.offer.dto.req.RecordOperLogReq;
import com.iwhalecloud.retail.offer.dto.resp.ContentEvaluationQryResp;
import com.iwhalecloud.retail.offer.dto.resp.EvaluationResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsEvaluationQryResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryContactChannelResp;
import com.iwhalecloud.retail.offer.dto.resp.UserRsp;
import com.iwhalecloud.retail.offer.entity.Evaluation;
import com.iwhalecloud.retail.offer.entity.OperDetail;
import com.iwhalecloud.retail.offer.entity.OperLog;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.mapper.EvaluationMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.StoreMapper;
import com.iwhalecloud.retail.offer.mapper.UsefulUselessMapper;
import com.iwhalecloud.retail.offer.service.ContentService;
import com.iwhalecloud.retail.offer.service.OrderService;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.SensitivewordFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0<br>
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EvaluationManager {

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private ContentService contentService;

    @Autowired
    private SensitivewordFilter sensitivewordFilter;

    @Autowired
    private SensitiveWordsManager sensitiveWordsManager;

    @Autowired
    private OperLogManager operLogManager;

    @Value("${evaluation.audit.flag}")
    private String evaluationAuditFlag;

    @Autowired
    private OrderService orderService;

    @Resource
    private ContactChannelManager contactChannelManager;

    @Resource
    private UsefulUselessMapper usefulUselessMapper;

    @Autowired
    private StoreMapper storeMapper;

    private static final int NUMBER_FOR_PHONE = 3;

    private static final int NUMBER_FOR_PHONESTR = 4;

    // release,降低复杂度
    public void releaseForAdd1(EvaluationReq req) throws BaseException {
        for (EvaluationItemReq evaluationItemReq : req.getObjs()) {
            AssertUtil.isNotNull(evaluationItemReq.getObjType(), OfferBaseMessageCodeEnum.EVALUATION_OBJ_TYPE_IS_NULL);
            AssertUtil.isNotNull(evaluationItemReq.getObjId(), OfferBaseMessageCodeEnum.EVALUATION_OBJ_ID_IS_NULL);
            if (!ObjectTypeDef.ORDER.equalsIgnoreCase(evaluationItemReq.getObjType())) {
                AssertUtil.isNotNull(evaluationItemReq.getRate(), OfferBaseMessageCodeEnum.EVALUATION_RATE_IS_NULL);
            }
        }
    }

    public void releaseForAdd2(String evaluationComments) throws BaseException {
        List<String> words = sensitiveWordsManager.listSensitiveWordsByState(AbstractSensitiveWordsDef.STATE_A);
        for (String word : words) {
            if (evaluationComments.contains(word)) {
                throw new BaseException(OfferBaseMessageCodeEnum.EVALUATION_HAVE_SENSITIVE_WORDS);
            }
        }
    }

    public void releaseForAdd3(ProdGoods prodGoods, Evaluation evaluation) {
        if (AbstractEvaluationDef.EVALUATION_AUDIT_FLAG_A.equals(prodGoods.getEvaluateAuditMode())) {
            evaluation.setState(AbstractEvaluationDef.STATE_A);
        }
        else {
            evaluation.setState(AbstractEvaluationDef.STATE_B);
        }
    }

    public void releaseForAdd4(Evaluation evaluation, ContentBaseResponseDTO contentBaseResponseDTO) {
        if (AbstractEvaluationDef.EVALUATION_AUDIT_FLAG_A
            .equals(contentBaseResponseDTO.getContentBase().getEvaluationAuditMethod())) {
            evaluation.setState(AbstractEvaluationDef.STATE_A);
        }
        else {
            evaluation.setState(AbstractEvaluationDef.STATE_B);
        }
    }

    /**
     * 新增评价
     * 
     * @param req EvaluationReq
     */
    public void addEvaluation(EvaluationReq req) throws BaseException {
        log.info("EvaluationManager addEvaluation start, req = [{}]", req);
        // 入参校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getObjs(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);

        UserRsp userRsp = req.getUserRsp();
        AssertUtil.isNotNull(userRsp, OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
        String userId = userRsp.getUserId();
        AssertUtil.isNotNull(userId, OfferBaseMessageCodeEnum.USER_ID_IS_NULL);

        releaseForAdd1(req);
        Date now = DBDateUtil.getCurrentDBDateTime();
        for (EvaluationItemReq evaluationItemReq : req.getObjs()) {
            String evaluationComments = evaluationItemReq.getEvaluationComments();
            if (StringUtils.isNotEmpty(evaluationComments)) {
                // 查出所有敏感词,评论内容包含敏感词直接驳回
                releaseForAdd2(evaluationComments);
            }
            Evaluation evaluation = new Evaluation();
            String storeId = null;
            if (ObjectTypeDef.GOODS.equals(evaluationItemReq.getObjType())
                || StringUtils.isEmpty(evaluationItemReq.getObjType())) {
                ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(evaluationItemReq.getObjId(), "Y");
                AssertUtil.isNotNull(prodGoods, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
                evaluation.setObjType(AbstractEvaluationDef.OBJ_TYPE_A);
                evaluation.setObjId(prodGoods.getGoodsId());
                AssertUtil.isNotNull(evaluationItemReq.getRate(), OfferBaseMessageCodeEnum.EVALUATION_RATE_IS_NULL);
                evaluation.setRate(Integer.valueOf(evaluationItemReq.getRate()));
                storeId = prodGoods.getStoreId();
                evaluation.setStoreId(storeId);
                releaseForAdd3(prodGoods, evaluation);
            }
            else if (ObjectTypeDef.CONTENT.equals(evaluationItemReq.getObjType())) {
                ContentBaseResponseDTO contentBaseResponseDTO = contentService
                    .queryContentDeatil(Long.valueOf(evaluationItemReq.getObjId()));
                if (null == contentBaseResponseDTO || null == contentBaseResponseDTO.getContentBase()) {
                    throw new BaseException(OfferBaseMessageCodeEnum.GOODS_CONTENT_NOT_EXIST);
                }
                evaluation.setObjType(AbstractEvaluationDef.OBJ_TYPE_B);
                evaluation.setObjId(evaluationItemReq.getObjId());
                evaluation.setRate(Integer.valueOf(evaluationItemReq.getRate()));
                releaseForAdd4(evaluation, contentBaseResponseDTO);

                // 更新评论量
                contentService.updateEvaluationCount(Long.valueOf(evaluationItemReq.getObjId()), req.getUserRsp().getUserId());
            }
            // 订单
            else if (ObjectTypeDef.ORDER.equalsIgnoreCase(evaluationItemReq.getObjType())) {
                evaluation.setObjType(AbstractEvaluationDef.OBJ_TYPE_C);
                evaluation.setObjId(evaluationItemReq.getObjId());
                String rate = evaluationItemReq.getRate();
                if (StringUtils.isEmpty(rate)) {
                    rate = "5";
                }
                evaluation.setRate(Integer.valueOf(rate));

                // 同步修改订单的是否已评价标志
                orderService.orderEvaluated(evaluationItemReq.getObjId());
            }
            evaluation.setUserId(userId);
            evaluation.setPhone(userRsp.getPhone());
            evaluation.setNickname(userRsp.getNickName());
            evaluation.setModifyBy(userId);
            evaluation.setCreateBy(userId);
            evaluation.setModifyTime(now);
            evaluation.setAnonymous(evaluationItemReq.getAnonymous());
            evaluation.setCreateTime(now);
            evaluation.setEvaluationComments(evaluationItemReq.getEvaluationComments());
            evaluation.setEvaluationId(UidGeneator.getUID());
            evaluation.setUsefulCount(0);
            evaluation.setUselessCount(0);
            evaluationMapper.insert(evaluation);

            // 更新店铺评分
            if (StringUtils.isEmpty(evaluationItemReq.getObjType()) || ObjectTypeDef.GOODS.equals(evaluationItemReq.getObjType())) {
                AssertUtil.isNotNull(storeId, OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);
                String timeString = DateUtil.formatString(now,  DateUtil.DATE_FORMAT_1);
                log.info("EvaluationManager formatString timeString = [{}]", timeString);
                Date time = DateUtil.formatDate(timeString, DateUtil.DATE_FORMAT_1);
                log.info("EvaluationManager formatDate time = [{}]", time);
                // 防止刷单，每人每月最多6次
                Long evaluationNum = evaluationMapper.countEvaluationByUserIdAndStoreId(userId, storeId, time, now);
                log.info("EvaluationManager countEvaluationByUserIdAndStoreId evaluationNum = [{}]", evaluationNum);
                if (null != evaluationNum && evaluationNum > 0 && evaluationNum < 6) {
                    updateStoreCreditScore(evaluationItemReq.getRate(), storeId);
                }
            }
        }

        log.info("EvaluationManager addEvaluation end");
    }

    private void updateStoreCreditScore(String rate, String storeId) {
        log.info("EvaluationManager updateStoreCreditScore start rate = [{}], storeId = [{}]", rate, storeId);

        if (!AbstractEvaluationDef.RATE_THREE.equals(rate)) {
            storeMapper.updateCreditScoreByRateAndStoreId(rate, storeId);
        }

        log.info("EvaluationManager updateStoreCreditScore end");
    }


    private void releaseForQry(EvaluationQryReq req) throws BaseException {
        if (null == req || null == req.getOffset() || null == req.getRows()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }
    }

    public Page<EvaluationResp> qryEvaluation(EvaluationQryReq req) throws BaseException {
        log.info("EvaluationManager qryEvaluation start, req = [{}]", req);

        releaseForQry(req);

        QueryContactChannelResp queryContactChannelResp = new QueryContactChannelResp();

        if (null != req.getChannelId()) {
            QueryContactChannelReq queryContactChannelReq = new QueryContactChannelReq();
            queryContactChannelReq.setContactChannelId(req.getChannelId().toString());
            queryContactChannelResp = contactChannelManager.qryByChannelId(queryContactChannelReq);
            if (null != queryContactChannelResp) {
                req.setTenantId(queryContactChannelResp.getTenantId());
            }
            else {
                throw new BaseException(OfferBaseMessageCodeEnum.CHANNEL_IS_NULL);
            }
        }
        Page<EvaluationResp> page = new Page<>(req.getOffset(), req.getRows());
        Page<EvaluationResp> evaluationRespList = evaluationMapper.qryEvaluation(page, req);

        // 对结果进行重新填充name字段，objType为B的话，需要填充content中心的content_base的title字段
        List<String> contentIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(evaluationRespList.getRecords())) {
            for (EvaluationResp resp : evaluationRespList.getRecords()) {
                if (AbstractEvaluationLogDef.OBJ_TYPE_B.equals(resp.getObjType())) {
                    if (!contentIds.contains(resp.getObjId())) {
                        contentIds.add(resp.getObjId());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(contentIds)) {
            ContentBaseQryReq requset = new ContentBaseQryReq();
            requset.setContentIds(contentIds);
            ResultVO<ArrayList<ContentBaseDTO>> contentBaseDtos = contentService.selectContentBaseListByCond(requset);
            ResultVOCheckUtil.checkResultVO(contentBaseDtos);
            for (EvaluationResp resp : evaluationRespList.getRecords()) {
                for (ContentBaseDTO contentBaseDTO : contentBaseDtos.getData()) {
                    if (resp.getObjId().equals(contentBaseDTO.getContentId())) {
                        resp.setObjName(contentBaseDTO.getTitle());
                    }
                }
            }
        }

        log.info("EvaluationManager qryEvaluation evaluationRespList = [{}]", evaluationRespList.getRecords());

        if (CollectionUtils.isNotEmpty(evaluationRespList.getRecords())) {
            for (EvaluationResp evaluationResp : evaluationRespList.getRecords()) {
                AssertUtil.isNotNull(evaluationResp.getObjType(),
                    OfferBaseMessageCodeEnum.USEFULUSELESS_OBJ_TYPE_IS_NULL);
                // 敏感词过滤
                if (StringUtils.isNotEmpty(evaluationResp.getEvaluationComments())) {
                    evaluationResp.setEvaluationComments(sensitivewordFilter.replaceSensitiveWordByTenantId(
                        evaluationResp.getEvaluationComments(), 1, "*", queryContactChannelResp.getTenantId()));
                }

                // 手机号要加*返回
                String phone = evaluationResp.getPhone();
                if (StringUtils.isNotEmpty(phone)) {
                    phone = phone.substring(0, NUMBER_FOR_PHONE) + "****"
                        + phone.substring(phone.length() - NUMBER_FOR_PHONESTR);
                    evaluationResp.setPhone(phone);
                }

                // 封装praiseFlag
                QueryUsefulUselessReq request = new QueryUsefulUselessReq();
                request.setObjId(evaluationResp.getEvaluationId());
                if (AbstractEvaluationDef.OBJ_TYPE_B.equals(evaluationResp.getObjType())) {
                    request.setObjType(AbstractUsefulUselessDef.OBJTYPE_C);
                }
                else {
                    request.setObjType(AbstractUsefulUselessDef.OBJTYPE_E);
                }
                request.setUserId(req.getUserId());
                request.setTenantId(req.getTenantId());
                request.setType(AbstractUsefulUselessDef.TYPE_ONE);
                log.info("EvaluationManager queryByObjTypeAndObjId request = [{}]", request);
                int num = usefulUselessMapper.countByObjTypeAndObjId(request);
                log.info("EvaluationManager queryByObjTypeAndObjId num = [{}]", num);
                if (num > 0) {
                    evaluationResp.setPraiseFlag(AbstractEvaluationDef.EVALUATION_Y);
                }
                else {
                    evaluationResp.setPraiseFlag(AbstractEvaluationDef.EVALUATION_N);
                }
            }
        }

        log.info("EvaluationManager qryEvaluation end");
        return evaluationRespList;
    }

    public Page<ProdGoodsEvaluationQryResp> qryProdGoodsEvaluation(ProdGoodsEvaluationQryReq req) throws BaseException {
        log.info("EvaluationManager qryProdGoodsEvaluation start, req = [{}]", req);
        if (null == req || null == req.getPageNo() || null == req.getPageSize()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }

        Page<ProdGoodsEvaluationQryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ProdGoodsEvaluationQryResp> prodGoodsEvaluation = evaluationMapper.qryProdGoodsEvaluation(page, req);

        // 敏感词过滤
        if (CollectionUtils.isNotEmpty(prodGoodsEvaluation.getRecords())) {
            for (ProdGoodsEvaluationQryResp evaluationResp : prodGoodsEvaluation.getRecords()) {
                if (StringUtils.isNotEmpty(evaluationResp.getEvaluationComments())) {
                    evaluationResp.setEvaluationComments(
                        sensitivewordFilter.replaceSensitiveWord(evaluationResp.getEvaluationComments(), 1, "*"));
                }
            }
        }

        log.info("EvaluationManager qryProdGoodsEvaluation end");
        return prodGoodsEvaluation;
    }

    public Page<ContentEvaluationQryResp> qryContentEvaluation(ContentEvaluationQryReq req) throws BaseException {
        log.info("EvaluationManager qryContentEvaluation start, req = [{}]", req);
        if (null == req || null == req.getPageNo() || null == req.getPageSize()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }

        // 先根据文章名称，把模糊查询的文章名称拿到
        if (StringUtils.isNotEmpty(req.getTitle())) {
            UserDTO userDTO = HttpSessionUtil.get();
            String tenantId = "";
            if (null != userDTO) {
                tenantId = userDTO.getTenantId();
            }

            ContentBaseQryReq contentBaseQryReq = new ContentBaseQryReq();
            contentBaseQryReq.setTitle(req.getTitle());
            contentBaseQryReq.setTenantId(tenantId);
            ResultVO<ArrayList<ContentBaseDTO>> resultVO = contentService
                .selectContentBaseListByCond(contentBaseQryReq);
            ResultVOCheckUtil.checkResultVO(resultVO);

            ArrayList<ContentBaseDTO> contentBaseDTOS = resultVO.getData();
            List<String> objIds = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(contentBaseDTOS)) {
                contentBaseDTOS.forEach(p -> {
                    objIds.add(p.getContentId().toString());
                });
                req.setObjIds(objIds);
            }
        }

        Page<ContentEvaluationQryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ContentEvaluationQryResp> contentEvaluation = evaluationMapper.qryContentEvaluation(page, req);

        List<String> contentIds = new ArrayList<>();
        // 敏感词过滤
        if (CollectionUtils.isNotEmpty(contentEvaluation.getRecords())) {
            for (ContentEvaluationQryResp evaluationResp : contentEvaluation.getRecords()) {
                if (!contentIds.contains(evaluationResp.getObjId())) {
                    contentIds.add(evaluationResp.getObjId());
                }
                if (StringUtils.isNotEmpty(evaluationResp.getEvaluationComments())) {
                    evaluationResp.setEvaluationComments(
                        sensitivewordFilter.replaceSensitiveWord(evaluationResp.getEvaluationComments(), 1, "*"));
                }
            }

            ContentBaseQryReq contentBaseQryReq = new ContentBaseQryReq();
            contentBaseQryReq.setContentIds(contentIds);
            ResultVO<ArrayList<ContentBaseDTO>> resultVO = contentService
                .selectContentBaseListByCond(contentBaseQryReq);
            ResultVOCheckUtil.checkResultVO(resultVO);
            ArrayList<ContentBaseDTO> contentBaseDTOS = resultVO.getData();
            contentEvaluation.getRecords().forEach(p -> {
                contentBaseDTOS.forEach(q -> {
                    if (p.getObjId().equals(q.getContentId().toString())) {
                        p.setTitle(q.getTitle());
                    }
                });
            });
        }

        log.info("EvaluationManager qryContentEvaluation end");
        return contentEvaluation;
    }

    public int batchDelEvaluation(List<String> ids) throws BaseException {
        log.info("EvaluationManager batchDelEvaluation start, req = [{}]", ids);
        String modifyBy = "";
        UserDTO userDTO = HttpSessionUtil.get();
        if (null != userDTO) {
            modifyBy = userDTO.getUserId().toString();
        }
        return evaluationMapper.batchDelEvaluation(ids, modifyBy);
    }

    /**
     * 批量隐藏操作
     * 
     * @param ids List<String>
     * @return int
     */
    public int batchHide(List<String> ids) throws BaseException {
        log.info("EvaluationManager batchHide start, req = [{}]", ids);

        AssertUtil.isNotNull(ids, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        // 只有有效的评价(state = A)才可以隐藏
        for (String id : ids) {
            Evaluation evaluation = evaluationMapper.selectById(id);
            AssertUtil.isNotNull(evaluation, OfferBaseMessageCodeEnum.EVALUATION_ID_IS_ERROR);
            if (!AbstractEvaluationDef.STATE_A.equals(evaluation.getState())) {
                throw new BaseException(OfferBaseMessageCodeEnum.EVALUATION_STATE_IS_NOT_A);
            }
        }

        String modifyBy = "";
        UserDTO userDTO = HttpSessionUtil.get();
        if (null != userDTO) {
            modifyBy = userDTO.getUserId().toString();
        }
        int result = evaluationMapper.batchHide(ids, modifyBy);

        log.info("EvaluationManager batchHide end");
        return result;
    }

    public int batchDisplay(List<String> ids) throws BaseException {
        log.info("EvaluationManager batchDisplay start, req = [{}]", ids);
        String modifyBy = "";
        UserDTO userDTO = HttpSessionUtil.get();
        if (null != userDTO) {
            modifyBy = userDTO.getUserId().toString();
        }
        return evaluationMapper.batchDisplay(ids, modifyBy);
    }

    public int modEvaluation(EvaluationModeReq req) throws BaseException {
        log.info("EvaluationManager modEvaluation start, req = [{}]", req);
        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getEvaluationId(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        if (AbstractEvaluationDef.STATE_D.equals(req.getState())) {
            req.setState(AbstractEvaluationDef.STATE_C);
        }
        UserDTO userDTO = HttpSessionUtil.get();
        if (null != userDTO) {
            req.setModifyBy(userDTO.getUserId().toString());
        }
        else {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return evaluationMapper.modEvaluation(req);
    }

    /**
     * 根据用户id和obj查询评价
     * 
     * @param req QueryEvaluationByUserIdAndObjReq
     * @return EvaluationResp
     */
    public EvaluationResp qryEvaluationByObjAndUserId(QueryEvaluationByUserIdAndObjReq req) throws BaseException {
        log.info("EvaluationManager qryEvaluationByObjAndUserId start, req = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getObjId(), OfferBaseMessageCodeEnum.USEFULUSELESS_OBJ_ID_IS_NULL);
        AssertUtil.isNotNull(req.getObjType(), OfferBaseMessageCodeEnum.USEFULUSELESS_OBJ_TYPE_IS_NULL);
        AssertUtil.isNotNull(req.getUserId(), OfferBaseMessageCodeEnum.USER_ID_IS_NULL);

        EvaluationResp result = evaluationMapper.qryEvaluationByObjAndUserId(req);

        log.info("EvaluationManager qryEvaluationByObjAndUserId end");
        return result;
    }

    /**
     * 批量审核
     * 
     * @param req BatchAuditEvaluationReq
     */
    public void batchAudit(BatchAuditEvaluationReq req, HttpServletRequest request) throws BaseException {
        log.info("EvaluationManager batchAudit start, BatchAuditEvaluationReq = [{}]", req);

        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        String state = req.getState();
        AssertUtil.isNotNull(state, OfferBaseMessageCodeEnum.EVALUATION_STATE_IS_NULL);
        List<String> ids = req.getIds();
        AssertUtil.isNotNull(ids, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);

        List<OperDetail> operDetailList = new ArrayList<>(ids.size());
        Long operLogId = UidGeneator.getUID();
        // 选择的记录只能是C(待审核)或者D(审核不通过)2个状态
        for (String id : ids) {
            Evaluation evaluation = evaluationMapper.selectById(id);
            AssertUtil.isNotNull(evaluation, OfferBaseMessageCodeEnum.EVALUATION_ID_IS_ERROR);
            if (!AbstractEvaluationDef.STATE_C.equals(evaluation.getState())
                && !AbstractEvaluationDef.STATE_D.equals(evaluation.getState())) {
                throw new BaseException(OfferBaseMessageCodeEnum.EVALUATION_STATE_IS_NOT_C_AND_D);
            }
            OperDetail operDetail = new OperDetail();
            operDetail.setOperDetailId(UidGeneator.getUID());
            operDetail.setOperLogId(operLogId);
            operDetail.setTableName(AbstractEvaluationDef.EVALUATION);
            operDetail.setColumnName(AbstractEvaluationDef.STATE);
            operDetail.setPkId(Long.valueOf(id));
            operDetail.setOldValue(evaluation.getState());
            operDetail.setNewValue(req.getState());
            operDetail.setCreateBy(req.getModifyBy());
            operDetail.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            operDetail.setModifyBy(req.getModifyBy());
            operDetail.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            operDetail.setState(CommonStateDef.ACTIVE);
            operDetailList.add(operDetail);
        }
        evaluationMapper.batchAudit(req);
        // 需要同步记录批量操作日志,oper_log_event中的event_id为7
        RecordOperLogReq recordOperLogReq = new RecordOperLogReq();
        OperLog operLog = new OperLog();
        operLog.setOperLogId(operLogId);
        operLog.setUserId(Long.valueOf(req.getModifyBy()));
        Date now = DBDateUtil.getCurrentDBDateTime();
        operLog.setCreateTime(now);
        operLog.setCreateBy(req.getModifyBy());
        operLog.setModifyBy(req.getModifyBy());
        operLog.setModifyTime(now);
        Object jsonObject = JSON.toJSON(req);
        operLog.setOperInfo(JSON.toJSONString(jsonObject));
        operLog.setOperEvent(AbstractOperLogDef.OPER_EVENT_SEVEN);
        operLog.setOperType(AbstractOperLogDef.OPER_TYPE_U);
        String ip = IPUtil.getClientIpAddr(request);
        log.info("EvaluationManager batchAudit getClientIpAddr ip = [{}]", ip);
        operLog.setIpAddr(ip);

        recordOperLogReq.setOperLog(operLog);
        recordOperLogReq.setOperDetailList(operDetailList);
        operLogManager.recordOperLog(recordOperLogReq);

        log.info("EvaluationManager batchAudit end");
    }
}
