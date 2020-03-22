package indi.zk.mall.product.service.impl;

import indi.zk.mall.product.DO.ProductInfo;
import indi.zk.mall.product.DTO.CartDTO;
import indi.zk.mall.product.ProductApplicationTests;
import indi.zk.mall.product.common.DecreaseStockInput;
import indi.zk.mall.product.repository.ProductInfoRepository;
import indi.zk.mall.product.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


/**
 * @author ZhengKai
 * @data 2019-09-15 19:59
 */
@Component
public class ProductServiceImplTest extends ProductApplicationTests {
    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertTrue(productInfoList.size() > 0);
    }

    @Test
    public void findByProductIdIn() throws Exception {
        List<ProductInfo> productInfoList = productService.findProductsByIdList(Arrays.asList("157875227953464068", "164103465734242707"));
        Assert.assertTrue(productInfoList.size() > 0);
    }

    @Test
    public void decreaseStock() throws Exception {
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput("157875196366160022", 20);
        productService.decreaseStack(Arrays.asList(decreaseStockInput));
    }
}