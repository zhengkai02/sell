package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.common.consts.CommonFiledDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.consts.MqErrorMessageStateDef;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.entity.TblMqErrorMessage;
import com.iwhalecloud.retail.offer.mapper.TblMqErrorMessageMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/7 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MqErrorMessageManager {

    @Autowired
    private TblMqErrorMessageMapper tblMqErrorMessageMapper;

    public void insertErrorMessage(TblMqErrorMessage tblMqErrorMessage) throws BaseException {
        AssertUtil.isNotNull(tblMqErrorMessage, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(tblMqErrorMessage.getMessageId(), OfferBaseMessageCodeEnum.MESSAGE_ID_IS_NULL);
        AssertUtil.isNotEmpty(tblMqErrorMessage.getTopic(), OfferBaseMessageCodeEnum.TOPIC_IS_NULL);
        AssertUtil.isNotEmpty(tblMqErrorMessage.getExchange(), OfferBaseMessageCodeEnum.EXCHANGE_IS_NULL);
        AssertUtil.isNotEmpty(tblMqErrorMessage.getQueue(), OfferBaseMessageCodeEnum.QUEUE_IS_EMPTY);
        AssertUtil.isNotEmpty(tblMqErrorMessage.getMessageContent(), OfferBaseMessageCodeEnum.MESSAGE_CONTENT_IS_NULL);

        tblMqErrorMessage.setState(MqErrorMessageStateDef.ERROR);

        TblMqErrorMessage recordMessage = tblMqErrorMessageMapper.selectById(tblMqErrorMessage.getMessageId());
        if (null == recordMessage) {
            tblMqErrorMessage.setRehandleTimes(0L);
            tblMqErrorMessage.setDealDate(DBDateUtil.getCurrentDBDateTime());
            tblMqErrorMessage.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            tblMqErrorMessage.setCreateBy(CommonFiledDef.SYSTEM);
            tblMqErrorMessage.setModifyBy(CommonFiledDef.SYSTEM);
            tblMqErrorMessage.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            tblMqErrorMessage.setState(CommonStateDef.ACTIVE);
            tblMqErrorMessageMapper.insertErrorMessage(tblMqErrorMessage);
        }
        else {
            tblMqErrorMessage.setModifyBy(CommonFiledDef.SYSTEM);
            tblMqErrorMessageMapper.retryFail(tblMqErrorMessage);
        }

    }

    public void finishErrorMessage(String messageId) throws BaseException {
        AssertUtil.isNotEmpty(messageId, OfferBaseMessageCodeEnum.MESSAGE_ID_IS_NULL);

        TblMqErrorMessage tblMqErrorMessage = tblMqErrorMessageMapper.selectById(messageId);
        if (null == tblMqErrorMessage) {
            return;
        }

        tblMqErrorMessageMapper.finishErrorMessage(messageId, CommonFiledDef.SYSTEM);

    }
}
