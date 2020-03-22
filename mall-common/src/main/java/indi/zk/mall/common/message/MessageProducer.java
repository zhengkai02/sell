package indi.zk.mall.common.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;

import com.iwhalecloud.retail.common.utils.JsonUtil;
import indi.zk.mall.common.utils.SpringContext;
import com.iwhalecloud.retail.common.utils.UidGeneator;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xh on 2019/3/15.
 */
@Slf4j
public final class MessageProducer {

    // 批处理BatchingRabbitTemplate?
    private static RabbitTemplate rabbitTemplate;

    static {
        rabbitTemplate = SpringContext.getBean(RabbitTemplate.class);
    }

    public static void sendMessage(String exchange, String routingKey, Object messageObj) {
        sendMessage(exchange, routingKey, null, messageObj);
    }

    /**
     * 发送信息
     *
     * @param exchange   String
     * @param routingKey String topic
     * @param messageId  消息标志ID  为空的话自动生成一个唯一标志 如果业务需要自动生成就传入messageId,不需要就不传
     * @param messageObj 消息对象.这个方法内部会转json
     */
    public static void sendMessage(String exchange, String routingKey, String messageId, Object messageObj) {
        log.info("MessageProducer.sendMessage start");
        MessageProperties messageProperties = new MessageProperties();
        String str = messageId;
        if (StringUtils.isEmpty(str)) {
            str = UidGeneator.getUIDStr();
        }
        messageProperties.setMessageId(str);
        messageProperties.setHeader("eid","123");

        String messageStr = null;
        if (String.class.isInstance(messageObj)) {
            messageStr = (String) messageObj;
        }
        else {
            messageStr = JsonUtil.object2Json(messageObj);
        }

        if (log.isDebugEnabled()) {
            log.debug("Send message information:[{}]", messageStr);
        }

        log.info("exchange = [{}]", exchange);
        log.info("routingKey = [{}]", routingKey);
        log.info("messageId = [{}]", messageId);
        log.info("messageObj = [{}]", messageObj);
        log.info("messageStr = [{}]", messageStr);

        Message message = rabbitTemplate.getMessageConverter().toMessage(messageStr, messageProperties);
        rabbitTemplate.send(exchange, routingKey, message);
        log.info("MessageProducer.sendMessage end");
    }

    private MessageProducer() {

    }

}
