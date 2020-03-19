package indi.zk.mall.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import indi.zk.mall.common.consts.MessageDef;
import indi.zk.mall.order.utils.JsonUtil;
import indi.zk.mall.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author ZhengKai
 * @data 2019-11-17 22:28
 */
@Slf4j
@Component
@Transactional
public class ProductInfoReceiver  {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {

        log.info("receive message started, message = [{}]", message);
        // message -> productInfoOutput
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message, new TypeReference<List<ProductInfoOutput>>() {});
        log.info("从队列 【{}】 接收到消息： [{}]", "productInfo", productInfoOutputList);

        for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
            // 存入redis
            redisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
        }
        // 存储到redis中
//        stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId()),
//                String.valueOf(productInfoOutput.getProductStock()));
    }
}
