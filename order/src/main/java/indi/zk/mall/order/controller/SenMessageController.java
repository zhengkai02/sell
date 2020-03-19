//package indi.zk.mall.order.controller;
//
//import indi.zk.mall.order.DTO.OrderDTO;
//import indi.zk.mall.order.message.StreamClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
///**
// * @author ZhengKai
// * @data 2019-10-20 15:46
// */
//@RestController
//@RequestMapping("/order")
//public class SenMessageController {
//
//    @Autowired
//    private StreamClient streamClient;
//
////    @GetMapping("/sendMessage")
////    public void process() {
////        String message = "now " + new Date();
////        streamClient.output().send(MessageBuilder.withPayload(message).build());
////    }
//
//    // 发送方的orderDTO对象
//
//    /**
//     *
//     */
//    @GetMapping("/sendMessage")
//    public void process() {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setOrderId("123456");
//        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
//    }
//}
