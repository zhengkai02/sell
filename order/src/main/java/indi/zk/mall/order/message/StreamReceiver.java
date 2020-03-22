//package indi.zk.mall.order.message;
//
//import indi.zk.mall.order.DTO.OrderDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Component;
//
///**
// * @author ZhengKai
// * @data 2019-10-20 15:44
// */
//@Component
//@EnableBinding(StreamClient.class)
//@Slf4j
//public class StreamReceiver {
//
////    @StreamListener(StreamClient.INPUT)
////    public void process(Object message) {
////        log.info("StreamReceiver : {}", message);
////    }
//
//    /**
//     * 接收orderDTO对象 消息
//     * @param message
//     */
////    @StreamListener(value = StreamClient.INPUT)
////    @SendTo(StreamClient.INPUT2)
////    public String receiver(OrderDTO message) {
////        log.info("StreamReceiver : {}", message);
////        return "received";
////    }
//
////    @StreamListener(value = StreamClient.INPUT2)
////    public void receiver2(String message) {
////        log.info("StreamReceiver2 : {}", message);
////        // 发送mq消息
////    }
//}
