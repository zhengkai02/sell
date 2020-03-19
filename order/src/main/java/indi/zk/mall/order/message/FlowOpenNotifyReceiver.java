package indi.zk.mall.order.message;

import com.alibaba.fastjson.JSON;
import indi.zk.mall.order.DO.ProductInfo;
import indi.zk.mall.order.utils.JsonUtil;
import indi.zk.mall.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZhengKai
 * @data 2019-11-25 16:42
 */
@Slf4j
@Component
@Transactional
@DependsOn("MessageDef")
public class FlowOpenNotifyReceiver  {

    @RabbitListener(queues = "#{T(indi.zk.mall.common.consts.MessageDef).FLOW_OPEN_NOTIFY_QUEUE}")
    public void process(Message message) {

        log.info("receive message started, message = [{}]", message);
        String body = new String(message.getBody());
        log.info("received message body = [{}]", body);

//        ProductInfo productInfo = JSON.parseObject(body, ProductInfo.class);
//        log.info("receive message end, productInfo = [{}]", productInfo);
        // message -> productInfoOutput

    }
}
