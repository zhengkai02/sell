package indi.zk.mall.product.service;

import indi.zk.mall.product.DO.ProductInfo;
import indi.zk.mall.product.ProductApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;


/**
 * @author ZhengKai
 * @data 2019-09-15 20:49
 */
public class ProductServiceTest extends ProductApplicationTests {
    @Autowired
    private ProductService productService;

    @Test
    public void findProductsByIdList() throws Exception {
        List<ProductInfo> productInfoList = productService.findProductsByIdList(Arrays.asList("157875227953464068", "164103465734242707"));
        Assert.assertTrue(productInfoList.size() > 0);
    }


}