package indi.zk.mall.order.service;

import indi.zk.mall.order.DO.OrderDetail;
import indi.zk.mall.order.DTO.OrderDTO;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:39
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO createOrder(OrderDTO orderDTO);

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
