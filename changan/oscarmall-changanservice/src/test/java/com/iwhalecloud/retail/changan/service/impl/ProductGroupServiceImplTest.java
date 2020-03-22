package com.iwhalecloud.retail.changan.service.impl;

import com.iwhalecloud.retail.changan.ChanganApplicationTests;
import com.iwhalecloud.retail.changan.DTO.ProductGroupDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.service.ProductGroupService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author ZhengKai
 * @data 2019-11-16 14:16
 */
public class ProductGroupServiceImplTest extends ChanganApplicationTests {

    @Autowired
    private ProductGroupService productGroupService;

    @Test
    public void createProductGroup() {
        ProductGroupDTO productGroupDTO = new ProductGroupDTO();
        productGroupDTO.setGroupId("201911161");
        productGroupDTO.setGroupCode("20001");
        productGroupDTO.setGroupName("长安产品分类1");
        productGroupDTO.setPid("1");
        ResultVO<String> result = productGroupService.createProductGroup(productGroupDTO);
        Assert.assertTrue(result != null);
    }

    @Test
    public void updateProductGroup() {
        ProductGroupDTO productGroupDTO = new ProductGroupDTO();
        productGroupDTO.setGroupId("201911161");
        productGroupDTO.setGroupCode("20001");
        productGroupDTO.setGroupName("长安产品分类1");
        productGroupDTO.setPid("2");
        ResultVO<String> result = productGroupService.updateProductGroup(productGroupDTO);
        Assert.assertTrue(result.getData() != null);
    }

    @Test
    public void deleteProductGroup() {
        String groupId = "201911161";
        ResultVO<String> resultVO = productGroupService.deleteProductGroup(groupId);
        ResultVO<String> result = resultVO;
        Assert.assertTrue(result != null);

    }
}