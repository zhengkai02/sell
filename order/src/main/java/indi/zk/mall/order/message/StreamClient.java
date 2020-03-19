//package indi.zk.mall.order.message;
//
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;
//
///**
// * @author ZhengKai
// * @data 2019-10-20 15:41
// */
//public interface StreamClient {
//
//    String INPUT="input";
//    String OUTPUT = "output";
//
//    String INPUT2 = "input2";
//    String OUTPUT2 = "output2";
//
//    @Input(INPUT)
//    SubscribableChannel input();
//
//    @Output(OUTPUT)
//    MessageChannel output();
//
//    @Input(INPUT2)
//    SubscribableChannel input2();
//
//    @Output(OUTPUT2)
//    MessageChannel output2();
//
//}
