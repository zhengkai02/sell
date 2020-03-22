package indi.zk.mall.order.service.impl;

import indi.zk.mall.order.DO.OrderDetail;
import indi.zk.mall.order.DO.OrderMaster;
import indi.zk.mall.order.DO.ProductInfo;
import indi.zk.mall.order.DTO.CarDTO;
import indi.zk.mall.order.DTO.OrderDTO;
import indi.zk.mall.order.client.ProductClient;
import indi.zk.mall.order.enums.OrderStatusEnum;
import indi.zk.mall.order.enums.PayStatusEnum;
import indi.zk.mall.order.enums.ResultEnum;
import indi.zk.mall.order.exception.OrderException;
import indi.zk.mall.order.repository.OrderDetailRepository;
import indi.zk.mall.order.repository.OrderMasterRepository;
import indi.zk.mall.order.service.OrderService;
import indi.zk.mall.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:40
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        // TODO 查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);

        // 读redis
        // 减库存并将新值重新设置进redis
        // 订单入库异常，手动回滚redis

        // TODO 计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    // 单价*数量
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    // 订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        // TODO 扣库存（调用商品服务）
        List<CarDTO> carDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CarDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(carDTOList);

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO finish(String orderId) {
        //1.查询订单
        Optional<OrderMaster> optionalOrderMaster = orderMasterRepository.findById(orderId);
        if (!optionalOrderMaster.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.判断订单状态
        OrderMaster orderMaster = optionalOrderMaster.get();
        if (OrderStatusEnum.NEW.getCode() != orderMaster.getOrderStatus()) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        //4. 查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
