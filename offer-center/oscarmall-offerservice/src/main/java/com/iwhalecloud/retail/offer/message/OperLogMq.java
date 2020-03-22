package com.iwhalecloud.retail.offer.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.iwhalecloud.retail.common.consts.CommonFiledDef;
import com.iwhalecloud.retail.common.consts.MessageDef;
import com.iwhalecloud.retail.common.dto.OperObjDetail;
import com.iwhalecloud.retail.common.dto.RecordOperLogObj;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.DateUtil;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.RecordOperLogReq;
import com.iwhalecloud.retail.offer.entity.OperDetail;
import com.iwhalecloud.retail.offer.entity.OperLog;
import com.iwhalecloud.retail.offer.manager.OperLogManager;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/28 <br>
 * @see com.iwhalecloud.retail.offer.message <br>
 * @since V9.0C<br>
 */
@Slf4j
@Component
@DependsOn("MessageDef")
public class OperLogMq extends MessageConsumerAbstract {

    @Autowired
    private OperLogManager operLogManager;

    public static final String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    @RabbitListener(queues = "#{T(com.iwhalecloud.retail.common.consts.MessageDef).OSCAR_MALL_OPER_LOG_QUEUE}")
    public void process(Message message) throws BaseException {
        log.info("OperLogMq deal message start, message = [{}]", message);
        receiveMsg(message, MessageDef.OSCAR_MALL_OPER_LOG_QUEUE);
        log.info("OperLogMq deal messOSCAR_MALL_MAILage end");

    }

    private RecordOperLogReq buildRecordOperLogReq(String messageObj) throws BaseException {
        log.info("OperLogMq buildRecordOperLogReq start, messageObj = [{}]", messageObj);
        RecordOperLogObj recordOperLogObj = JSON.parseObject(messageObj, new TypeReference<RecordOperLogObj>() {
        });
        if (null == recordOperLogObj) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }

        RecordOperLogReq recordOperLogReq = new RecordOperLogReq();
        recordOperLogReq.setRequestId(recordOperLogObj.getRequestId());

        String userId = CommonFiledDef.SYSTEM;
        if (null != recordOperLogObj.getOperLog()) {
            userId = recordOperLogObj.getOperLog().getUserId().toString();
            OperLog operLog = new OperLog();
            operLog.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            operLog.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            if (StringUtils.isNotEmpty(recordOperLogObj.getOperLog().getCreateDate())) {
                operLog.setCreateTime(
                        DateUtil.formatDate(recordOperLogObj.getOperLog().getCreateDate(), DATE_FORMAT_TIME));
                operLog.setModifyTime(operLog.getCreateTime());
            }
            operLog.setIpAddr(recordOperLogObj.getOperLog().getIpAddr());
            operLog.setUserId(recordOperLogObj.getOperLog().getUserId());
            operLog.setOperType(recordOperLogObj.getOperLog().getOperType());
            operLog.setOperLogId(recordOperLogObj.getOperLog().getOperLogId());
            operLog.setOperEvent(recordOperLogObj.getOperLog().getOperEvent());
            operLog.setOperInfo(recordOperLogObj.getOperLog().getOperInfo());
            operLog.setPrivilegeId(recordOperLogObj.getOperLog().getPrivilegeId());
            operLog.setRefObjId(recordOperLogObj.getOperLog().getRefObjId());
            operLog.setCreateBy(recordOperLogObj.getOperLog().getUserId().toString());
            operLog.setModifyBy(recordOperLogObj.getOperLog().getUserId().toString());
            recordOperLogReq.setOperLog(operLog);
        }

        List<OperDetail> operDetailList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(recordOperLogObj.getOperDetailList())) {
            for (OperObjDetail operObjDetail : recordOperLogObj.getOperDetailList()) {
                OperDetail operDetail = new OperDetail();
                org.springframework.beans.BeanUtils.copyProperties(operObjDetail, operDetail);
                operDetail.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                operDetail.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                operDetail.setCreateBy(userId);
                operDetail.setModifyBy(userId);
                operDetail.setState(CommonStateDef.ACTIVE);
                operDetailList.add(operDetail);
            }
        }
        recordOperLogReq.setOperDetailList(operDetailList);

        log.info("SubmitOrderMq buildRecordOperLogReq end");
        return recordOperLogReq;
    }

    @Override
    public void deal(Message message, String queue) throws BaseException {
        String messageObj = new String(message.getBody());
        if (StringUtils.isEmpty(messageObj)) {
            return;
        }
        RecordOperLogReq recordOperLogReq = buildRecordOperLogReq(messageObj);
        operLogManager.recordOperLog(recordOperLogReq);
    }
}
