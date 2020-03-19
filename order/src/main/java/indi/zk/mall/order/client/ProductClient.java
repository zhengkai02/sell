package indi.zk.mall.order.client;

import indi.zk.mall.order.DO.ProductInfo;
import indi.zk.mall.order.DTO.CarDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @author ZhengKai
 * @data 2019-09-15 14:48
 */
@FeignClient(name = "product")      // 访问微服务的名称
public interface ProductClient {

    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);

    /**
     * 扣减库存
     *
     * @param carDTOList
     * @return
     */
    @PostMapping("/product/decreaseStock")
    String decreaseStock(@RequestBody List<CarDTO> carDTOList);

}
