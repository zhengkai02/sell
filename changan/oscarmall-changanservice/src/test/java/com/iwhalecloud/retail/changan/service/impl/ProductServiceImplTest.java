package com.iwhalecloud.retail.changan.service.impl;

import com.iwhalecloud.retail.changan.ChanganApplicationTests;
import com.iwhalecloud.retail.changan.DTO.req.ProductRequestDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author ZhengKai
 * @data 2019-11-16 13:53
 */
public class ProductServiceImplTest extends ChanganApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void createProduct() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setProductId("201911161");
        productRequestDTO.setGroupId("201911161");
        productRequestDTO.setBrandId(0);
        productRequestDTO.setAppId("201911162");
        productRequestDTO.setProductName("长安产品1");
        productRequestDTO.setAlias("蜜汁鸡翅");
        productRequestDTO.setUrl("http://fuss10.elemecdn.com/7/4a/f307f56216b03f067155aec8b124ejpeg.jpeg");
        productRequestDTO.setPrice("9.9");
        productRequestDTO.setPriceUnit("RMB");
        productRequestDTO.setNumber(10L);
        productRequestDTO.setEffectiveDate("2019-11-16");
        productRequestDTO.setExpiryDate("2019-11-16");
        productRequestDTO.setDescription("tsop发过来的测试商品");
        productRequestDTO.setUsageUnit("M");
        productRequestDTO.setUsageValue("100");

        ResultVO<String> result = productService.createProduct(productRequestDTO);
        Assert.assertTrue(result.getData() != null);
    }

    @Test
    public void updateProduct() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setProductId("201911161");
        productRequestDTO.setGroupId("201911161");
        productRequestDTO.setBrandId(0);
        productRequestDTO.setAppId("201911162");
        productRequestDTO.setProductName("长安产品1");
        productRequestDTO.setAlias("蜜汁鸡翅");
        productRequestDTO.setUrl("http://fuss10.elemecdn.com/7/4a/f307f56216b03f067155aec8b124ejpeg.jpeg");
        productRequestDTO.setPrice("10.9");
        productRequestDTO.setPriceUnit("RMB");
        productRequestDTO.setNumber(10L);
        productRequestDTO.setEffectiveDate("2019-11-16");
        productRequestDTO.setExpiryDate("2019-11-16");
        productRequestDTO.setDescription("tsop发过来的测试商品");
        productRequestDTO.setUsageUnit("M");
        productRequestDTO.setUsageValue("100");

        ResultVO<String> result = productService.updateProduct(productRequestDTO);
        Assert.assertTrue(result.getData() != null);
    }

    @Test
    public void deleteProduct() {
        String productId = "201911161";
        ResultVO<String> result = productService.deleteProduct(productId);
        Assert.assertTrue(result.getData() != null);
    }
}