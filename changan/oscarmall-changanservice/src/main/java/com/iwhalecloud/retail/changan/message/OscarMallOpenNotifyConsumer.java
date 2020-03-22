//package com.iwhalecloud.retail.changan.message;
//
//import com.iwhalecloud.retail.common.consts.MessageDef;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//import org.springframework.amqp.core.Message;
//
///**
// * @author ZhengKai
// * @data 2019-11-19 17:26
// */
//@Component
//@Slf4j
//@DependsOn(value = "MessageDef")
//public class OscarMallOpenNotifyConsumer {
//
//    @RabbitListener(queues = "#{T(indi.zk.mall.common.consts.MessageDef).OSCAR_MALL_FLOW_OPEN_NOTIFY_QUEUE}")
//    public void process(Message message){
//        log.info("message received, message = [{}]", MessageDef.OSCAR_MALL_FLOW_OPEN_NOTIFY_QUEUE);
//    }
//}
