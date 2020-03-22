package indi.zk.mall.product.controller;

import indi.zk.mall.common.consts.MessageDef;
import indi.zk.mall.common.message.MessageProducer;
import indi.zk.mall.product.DO.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author ZhengKai
 * @data 2019-09-15 12:24
 */
@RestController
@Slf4j
@RequestMapping("/msg")
public class ServiceController {

    @GetMapping("")
    public String msg() {
        return "this is server.";
    }

    @GetMapping("/send")
    public void sendMessage() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("102928389");
        productInfo.setProductStock(10);
        productInfo.setCategoryType(1);
        LocalDate localDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.format(date);
        log.info("date = [{}]", date);
        productInfo.setCreateTime(date);
        productInfo.setProductDescription("消息队列测试");

        log.info("send message start , productInfo = [{}]", productInfo);
        MessageProducer.sendMessage(MessageDef.ORDER_LOG_SYNC_EXCHANGE, MessageDef.FLOW_OPEN_NOTIFY_TOPIC,productInfo);
        log.info("send message end");
    }
}
