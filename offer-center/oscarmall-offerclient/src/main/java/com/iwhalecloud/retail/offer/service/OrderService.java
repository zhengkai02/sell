package com.iwhalecloud.retail.offer.service;

import com.iwhalecloud.retail.common.exception.BaseException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by xh on 2019/6/25.
 */
@FeignClient(name = "${oscar.rest.order.name}", path = "${oscar.rest.order.path}")
public interface OrderService {

    @PostMapping("/evaluate/{orderId}")
    void orderEvaluated(@PathVariable("orderId") String orderId) throws BaseException;
}
