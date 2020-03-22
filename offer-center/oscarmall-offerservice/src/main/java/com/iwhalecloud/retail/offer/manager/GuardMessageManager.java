package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.consts.MqErrorMessageStateDef;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.message.MessageProducer;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.QryErrorMqMessageReq;
import com.iwhalecloud.retail.offer.dto.resp.QryErrorMqMessageResp;
import com.iwhalecloud.retail.offer.entity.TblGuardMessageRule;
import com.iwhalecloud.retail.offer.entity.TblMqErrorMessage;
import com.iwhalecloud.retail.offer.mapper.TblGuardMessageRuleMapper;
import com.iwhalecloud.retail.offer.mapper.TblMqErrorMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/9 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GuardMessageManager {

    @Autowired
    private TblMqErrorMessageMapper tblMqErrorMessageMapper;

    @Autowired
    private TblGuardMessageRuleMapper tblGuardMessageRuleMapper;

    @Autowired
    private ICache redisCache;

    public Page<QryErrorMqMessageResp> qryErrorMqMessage(QryErrorMqMessageReq req) {
        final int pageSize = 20;
        final int pageNo = 1;
        log.info("GuardMessageManager qryErrorMqMessage start, req = [{}]", req);
        if (null == req.getPageSize()) {
            req.setPageSize(Integer.valueOf(pageSize));
        }

        if (null == req.getPageNo()) {
            req.setPageNo(Integer.valueOf(pageNo));
        }

        Page<QryErrorMqMessageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<QryErrorMqMessageResp> result = tblMqErrorMessageMapper.qryErrorMqMessage(page, req);
        log.info("GuardMessageManager qryErrorMqMessage end, result = [{}]", result);
        return result;
    }

    public void deleteErrorMqMessage(String messageId) throws BaseException {
        log.info("GuardMessageManager deleteErrorMqMessage start, messageId = [{}]", messageId);
        AssertUtil.isNotEmpty(messageId, OfferBaseMessageCodeEnum.MESSAGE_ID_IS_NULL);

        TblMqErrorMessage tblMqErrorMessage = tblMqErrorMessageMapper.selectById(messageId);
        if (null == tblMqErrorMessage || MqErrorMessageStateDef.INACTIVE.equals(tblMqErrorMessage.getState())) {
            throw new BaseException(OfferBaseMessageCodeEnum.MESSAGE_NOT_EXIST);
        }

        String modifyBy = null;
        UserDTO userDTO = HttpSessionUtil.get();
        if (userDTO != null) {
            modifyBy = userDTO.getUserId().toString();
        }

        tblMqErrorMessageMapper.deleteMqErrorMessage(messageId, modifyBy);
        log.info("GuardMessageManager deleteErrorMqMessage end");
    }

    public void resendMessage(String messageId) throws BaseException {
        log.info("GuardMessageManager resendMessage start, messageId = [{}]", messageId);
        AssertUtil.isNotEmpty(messageId, OfferBaseMessageCodeEnum.MESSAGE_ID_IS_NULL);

        TblMqErrorMessage tblMqErrorMessage = tblMqErrorMessageMapper.selectById(messageId);
        if (null == tblMqErrorMessage || MqErrorMessageStateDef.INACTIVE.equals(tblMqErrorMessage.getState())) {
            throw new BaseException(OfferBaseMessageCodeEnum.MESSAGE_NOT_EXIST);
        }

        if (!MqErrorMessageStateDef.ERROR.equals(tblMqErrorMessage.getState())) {
            throw new BaseException(OfferBaseMessageCodeEnum.MESSAGE_CAN_NOT_RETRY);
        }

        String modifyBy = null;
        UserDTO userDTO = HttpSessionUtil.get();
        if (userDTO != null) {
            modifyBy = userDTO.getUserId().toString();
        }

        tblMqErrorMessageMapper.resendMqErrorMessage(messageId, modifyBy);

        // 重处理
        redisCache.delete(CacheKeyDef.MQ_DEAL_QTY, tblMqErrorMessage.getMessageId());

        MessageProducer.sendMessage(tblMqErrorMessage.getExchange(), tblMqErrorMessage.getTopic(),
            tblMqErrorMessage.getMessageId(), tblMqErrorMessage.getMessageContent());

        log.info("GuardMessageManager resendMessage end");

    }

    public List<String> qryQueueList() {
        log.info("GuardMessageManager qryQueueList start");
        List<String> result = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<TblGuardMessageRule> tblGuardMessageRuleList = tblGuardMessageRuleMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(tblGuardMessageRuleList)) {
            tblGuardMessageRuleList.forEach(tblGuardMessageRule -> result.add(tblGuardMessageRule.getQueue()));
        }
        log.info("GuardMessageManager qryQueueList end, result = [{}]", result);
        return result;
    }
}
