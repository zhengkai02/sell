package indi.zk.mall.order.conveter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import indi.zk.mall.order.DO.OrderDetail;
import indi.zk.mall.order.DTO.OrderDTO;
import indi.zk.mall.order.enums.ResultEnum;
import indi.zk.mall.order.exception.OrderException;
import indi.zk.mall.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:45
 */
@Slf4j
public class OrderFormToOrderDTOConveter {
    public static OrderDTO conveter(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenId(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.info("【json转换】错误，String = {}", orderForm.getItems());
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
