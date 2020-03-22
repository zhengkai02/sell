package indi.zk.mall.order.controller;

import indi.zk.mall.order.DO.ProductInfo;
import indi.zk.mall.order.DTO.CarDTO;
import indi.zk.mall.order.DTO.OrderDTO;
import indi.zk.mall.order.client.ProductClient;
import indi.zk.mall.order.conveter.OrderFormToOrderDTOConveter;
import indi.zk.mall.order.enums.ResultEnum;
import indi.zk.mall.order.exception.OrderException;
import indi.zk.mall.order.form.OrderForm;
import indi.zk.mall.order.service.OrderService;
import indi.zk.mall.product.VO.ResultVO;
import indi.zk.mall.product.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:47
 */
@RestController
@RequestMapping(value = "/order",produces = "application/json")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;

    /**
     * 1、参数校验
     * 2、查询商品信息
     * 3、计算总价
     * 4、扣库存（调用商品服务）
     * 5、订单入库
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@RequestBody @Valid OrderForm orderForm, BindingResult bindingResult) {
        // 校验参数
        if (bindingResult.hasErrors()) {
            log.info("【创建订单】参数不正确，orderForm = {}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderFormToOrderDTOConveter.conveter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CAR_EMPTY);
        }

        // 创建订单
        OrderDTO result = orderService.createOrder(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);

    }

    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder() {
        return productClient.listForOrder(Arrays.asList("157875196366160022", "157875227953464068"));
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock() {
        productClient.decreaseStock(Arrays.asList(new CarDTO("164103465734242707", 2)));
    }
}
