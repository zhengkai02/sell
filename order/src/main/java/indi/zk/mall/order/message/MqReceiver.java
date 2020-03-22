//package indi.zk.mall.order.message;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * MQ接收消息
// *
// * @author ZhengKai
// * @data 2019-10-15 23:27
// */
//@Slf4j
//@Component
//public class MqReceiver {
//
//    // 1. 使用前需要手动在面板创建队列@RabbitListener(queues = "myQueue")
//    /*
//    @RabbitListener(queues = "myQueue")
//    public void send(String message){
//        log.info("message = [{}]", message);
//    }
//    */
//
//    // 2. 自动创建队列
//    /*
//    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
//    public void process(String message){
//        log.info("MqReceiver: {}", message);
//    }
//    */
//
//    // 3. 自动创建，Exchange和Queue自动绑定
////    @RabbitListener(bindings = @QueueBinding(
////            value = @Queue("myQueue"),
////            exchange = @Exchange("myExchange")
////    ))
////    public void process(String message) {
////        log.info("MqReceiver: {}", message);
////    }
////
////    /**
////     * 数码供应商，接收消息
////     *
////     * @param message
////     */
////    @RabbitListener(bindings = @QueueBinding(
////            key = "computer",
////            value = @Queue("computerOrder"),
////            exchange = @Exchange("myOrder")
////    ))
////    public void processComputer(String message) {
////        log.info("computerMqReceiver: {}", message);
////    }
////
////    /**
////     * 水果供应商，接收消息
////     *
////     * @param message
////     */
////    @RabbitListener(bindings = @QueueBinding(
////            key = "fruit",
////            value = @Queue("fruitOrder"),
////            exchange = @Exchange("myOrder")
////    ))
////    public void processFruit(String message) {
////        log.info("fruitMqReceiver: {}", message);
////    }
//}
