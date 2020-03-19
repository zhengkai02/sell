package indi.zk.mall.order.service;

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
}
