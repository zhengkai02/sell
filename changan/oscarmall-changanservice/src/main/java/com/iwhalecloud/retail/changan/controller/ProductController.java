package com.iwhalecloud.retail.changan.controller;

import com.iwhalecloud.retail.changan.DTO.req.ProductRequestDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhengKai
 * @data 2019-10-28 14:58
 */
@RestController
@RequestMapping(value = "/huservice/api", produces = "application/json")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 根据产品ID（商品ID）同步产品给长安.
     * @param productId
     * @return
     */
    @PostMapping("/sysProduct/{productId}")
    public ResultVO<String> syncProduct(@PathVariable("productId") String productId){
        ResultVO<String> result = productService.syncProduct(productId);
        return result;
    }

    /**
     * 产品删除接口
     * @param productId
     * @return
     */
    @PostMapping("/product/delete")
    public ResultVO<String> deleteProduct(String productId) {
        log.info("ProductController deleteProduct start, productId = [{}]", productId);
        ResultVO<String> result = productService.deleteProduct(productId);
        log.info("ProductController deleteProduct end, result = [{}]", result);
        return result;
    }
}
