package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.offer.dto.ProdGoodsRelTypeDTO;
import com.iwhalecloud.retail.offer.manager.ProdGoodsRelTypeManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品关系类型
 * @author fanxiaofei
 * @date 2019/02/26
 */
@Api(tags = "商品关系类型")
@Slf4j
@RestController("prodGoodsRelTypeMicroService")
@RequestMapping("/offer/prodgoodsreltype")
public class ProdGoodsRelTypeController {

    @Autowired
    private ProdGoodsRelTypeManager manager;


    @ApiOperation(value = "查询所有商品关系类型")
    @GetMapping(value = "/list")
    public List<ProdGoodsRelTypeDTO> listProdGoodsRelType() {
        log.info("ProdGoodsRelTypeController listProdGoodsRelType start");
        List<ProdGoodsRelTypeDTO> result = manager.listProdGoodsRelType();
        log.info("ProdGoodsRelTypeController listProdGoodsRelType end");
        return result;
    }

}