package indi.zk.mall.product.controller;

import indi.zk.mall.product.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhengKai
 * @data 2019-12-06 13:11
 */
@RestController
@Slf4j
@RequestMapping("/address")
public class GetAddressController {


    @GetMapping("/get")
    public String getUserAddress(HttpServletRequest request){
        String ip = UserService.getRemoteIp(request);
        return ip;
    }
}
