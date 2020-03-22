package com.iwhalecloud.retail.offer.message;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.dto.GuardMessageRule;
import com.iwhalecloud.retail.common.dto.MqErrorMessage;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.entity.TblMqErrorMessage;
import com.iwhalecloud.retail.offer.manager.GuardMessageRuleManager;
import com.iwhalecloud.retail.offer.manager.MqErrorMessageManager;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/8 <br>
 * @see com.iwhalecloud.retail.order.message <br>
 * @since V9.0C<br>
 */
@Slf4j
@Component
public abstract class MessageConsumerAbstract {

    @Autowired
    private ICache redisCache;

    @Autowired
    private MqErrorMessageManager mqErrorMessageManager;

    @Autowired
    private GuardMessageRuleManager guardMessageRuleManager;

    public void receiveMsg(Message message, String queue) throws BaseException {
        log.info("MessageConsumerAbstract receiveMsg start, message = [{}], queue = [{}]", message, queue);
        GuardMessageRule guardMessageRule = getGuardMessageRule(message, queue);

        try {

            deal(message, queue);
            deleteRedisMessageQty(message);
            finishErrorMessageRecord(message);
        }
        catch (Exception e) {
            log.error("MessageConsumer receive message Exception = [{}]", e);
            if (isRetry(guardMessageRule, message)) {
                log.info("MessageConsumerAbstract retry.");
                throw e;
            }
            else {
                recordMqErrorMessage(message, e, queue);
            }
        }
    }

    private void finishErrorMessageRecord(Message message) throws BaseException {
        //如果抛出异常会导致消息重新进入消息队列，此处不抛出最多消息一直在B状态
        try {
            mqErrorMessageManager.finishErrorMessage(message.getMessageProperties().getMessageId());
        }
        catch (Exception e) {
            log.error("MessageConsumer finishErrorMessageRecord  Exception = [{}]", e);
        }
    }

    public abstract void deal(Message message, String queue) throws BaseException;

    private void deleteRedisMessageQty(Message message) {
        try {
            log.info("MessageConsumerAbstract deleteRedisMessageQty start, message = [{}]", message);
            //删除redis保存的执行次数，失败也无所谓
            redisCache.delete(CacheKeyDef.MQ_DEAL_QTY, message.getMessageProperties().getMessageId());
            log.info("MessageConsumerAbstract deleteRedisMessageQty end, message = [{}]", message);
        }
        catch (Exception e) {
            log.error("deleteRedisMessageQty fail, message = [{}], exception e = [{}]", message, e);
        }
    }

    private Boolean isRetry(GuardMessageRule guardMessageRule, Message message) {
        log.info("MessageConsumerAbstract isRetry start, message = [{}], guardMessageRule = [{}]", message, guardMessageRule);
        Boolean retryFlag = false;
        if (null != guardMessageRule && "N".equals(guardMessageRule.getCanDowngrade())) {
            Long retryNum = guardMessageRule.getRetryNum();
            if (null == retryNum) {
                retryNum = 1L;
            }


            String qtyStr = redisCache.getNumber(CacheKeyDef.MQ_DEAL_QTY, message.getMessageProperties().getMessageId());
            if (StringUtils.isNotEmpty(qtyStr) && Long.valueOf(qtyStr).compareTo(retryNum) < 0) {
                retryFlag = true;
            }

            if (StringUtils.isNotEmpty(qtyStr) && Long.valueOf(qtyStr).compareTo(retryNum) >= 0) {
                redisCache.delete(CacheKeyDef.MQ_DEAL_QTY, message.getMessageProperties().getMessageId());
            }
        }
        log.info("MessageConsumerAbstract isRetry end, retryFlag = [{}]", retryFlag);
        return retryFlag;
    }

    private void recordMqErrorMessage(Message message, Exception e, String queue) throws BaseException {
        log.info("MessageConsumerAbstract recordMqErrorMessage start, message = [{}], e = [{}], queue = [{}]", message, e, queue);
        MqErrorMessage mqErrorMessage = new MqErrorMessage();
        mqErrorMessage.setMessageId(message.getMessageProperties().getMessageId());
        mqErrorMessage.setExchange(message.getMessageProperties().getReceivedExchange());
        mqErrorMessage.setTopic(message.getMessageProperties().getReceivedRoutingKey());
        mqErrorMessage.setQueue(queue);
        mqErrorMessage.setMessageContent(new String(message.getBody()));

        if (BaseException.class.isInstance(e)) {
            BaseException baseException = (BaseException) e;
            mqErrorMessage.setComments(baseException.getDesc());
        }
        else {
            mqErrorMessage.setComments(e.getMessage());
            if (StringUtils.isEmpty(e.getMessage())) {
                mqErrorMessage.setComments(e.getLocalizedMessage());
            }
        }
        TblMqErrorMessage tblMqErrorMessage = new TblMqErrorMessage();
        BeanUtils.copyProperties(mqErrorMessage, tblMqErrorMessage);
        mqErrorMessageManager.insertErrorMessage(tblMqErrorMessage);
        log.info("MessageConsumerAbstract recordMqErrorMessage end, message = [{}], e = [{}], queue = [{}]", message, e, queue);
    }

    private GuardMessageRule getGuardMessageRule(Message message, String queue) throws BaseException {
        log.info("MessageConsumerAbstract getGuardMessageRule start, message = [{}], queue = [{}]", message, queue);
        GuardMessageRule guardMessageRule = null;

        try {
            guardMessageRule = (GuardMessageRule) redisCache.get(CacheKeyDef.MQ_RULE, queue);
            if (null == guardMessageRule) {
                guardMessageRule = guardMessageRuleManager.qryGuardMessageRuleByQueue(queue);
            }

            if (null != guardMessageRule && "N".equals(guardMessageRule.getCanDowngrade())) {
                redisCache.incrBy(CacheKeyDef.MQ_DEAL_QTY, message.getMessageProperties().getMessageId(), 1);
            }
        }
        catch (Exception e) {
            //异常就当作是可降级的处理
            log.error("MessageConsumerAbstract getGuardMessageRule error, e = [{}]", e);
        }

        log.info("MessageConsumerAbstract getGuardMessageRule end, guardMessageRule = [{}]", guardMessageRule);
        return guardMessageRule;
    }
}
