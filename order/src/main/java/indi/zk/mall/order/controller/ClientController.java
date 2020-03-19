package indi.zk.mall.order.controller;

import indi.zk.mall.order.DO.ProductInfo;
import indi.zk.mall.order.DTO.CarDTO;
import indi.zk.mall.order.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-09-15 15:12
 */
@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        String response = productClient.productMsg();
        log.info("response = {}", response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("157875227953464068", "164103465734242707"));
        log.info("response = {}", productInfoList);
        return "OK";
    }

    @GetMapping("/decreaseStock")
    public String productDecreaseStock() {
        productClient.decreaseStock(Arrays.asList(new CarDTO("157875227953464068", 60)));
        return "OK";
    }

//    @GetMapping("/getProductMsg")
//    public String getProductMsg() {

    // 第一种方式
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:10001/msg",String.class);
//        log.info("response = {}",response);
//        return response;

    // 第二种方式
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        String url = String.format("http://%s:%s/msg",serviceInstance.getHost(),serviceInstance.getPort());
//        String response = restTemplate.getForObject(url,String.class);
//        log.info("respnse = {}",response);
//        return response;
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
//        log.info("response = {}", response);
//        return response;
//    }
}
