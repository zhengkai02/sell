package com.iwhalecloud.retail.changan.controller;

import com.iwhalecloud.retail.changan.DTO.ProductGroupDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.service.ProductGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhengKai
 * @data 2019-10-28 14:48
 */
@RestController
@RequestMapping(value = "/huservice/api", produces = "application/json")
@Slf4j
public class ProductGroupConrtoller {


    @Autowired
    private ProductGroupService productGroupService;

    /**
     * 根据产品分类ID(店铺目录ID)同步产品分类给长安.
     * @param groupId
     * @return
     */
    @PostMapping("/syncProductGroup/{groupId}")
    public ResultVO<String> syncProductGroup(@PathVariable("groupId") String groupId) {
        ResultVO<String> result = productGroupService.syncProductGroup(groupId);
        return result;
    }
    /**
     * 产品分类删除接口
     * @return
     */
    @PostMapping("/productGroup/delete")
    public ResultVO<String> deleteProductGroup(String groupId) {
        log.info("ProductGroupController deleteProductGroup start, productGroupId = [{}]", groupId);
        ResultVO<String> result = productGroupService.deleteProductGroup(groupId);
        log.info("ProductGroupController deleteProductGroup end, result = [{}]", result);
        // TODO 删除产品分类
        return result;
    }

}
