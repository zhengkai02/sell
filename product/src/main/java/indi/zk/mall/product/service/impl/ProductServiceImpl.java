package indi.zk.mall.product.service.impl;

import indi.zk.mall.common.consts.MessageDef;
import indi.zk.mall.common.message.MessageProducer;
import indi.zk.mall.product.DO.ProductInfo;
import indi.zk.mall.product.DTO.CartDTO;
import indi.zk.mall.product.common.DecreaseStockInput;
import indi.zk.mall.product.common.ProductInfoOutput;
import indi.zk.mall.product.enums.ProductStatus;
import indi.zk.mall.product.enums.ResultEnum;
import indi.zk.mall.product.exception.ProductException;
import indi.zk.mall.product.repository.ProductInfoRepository;
import indi.zk.mall.product.service.ProductService;
import indi.zk.mall.product.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ZhengKai
 * @data 2019-09-15 11:59
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatus.UP.getCode());
    }

    @Override
    public List<ProductInfo> findProductsByIdList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    public void decreaseStack(List<DecreaseStockInput> decreaseStockInputList) {

        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);

        // 发送MQ消息
        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputList));
//            MessageProducer.sendMessage(MessageDef.ORDER_LOG_SYNC_EXCHANGE,MessageDef.ORDER_PAY_CALLBACK_TOPIC, JsonUtil.toJson(productInfoOutputList));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = new LinkedList<>();
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());

            // 判断商品是否存在.
            if (!productInfoOptional.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXSIT);
            }
            ProductInfo productInfo = productInfoOptional.get();

            // 判断库存是否足够.
            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if (result < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STAOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
