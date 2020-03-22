package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.manager.ProdTagsRelManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品标签
 *
 * @author jimaowei
 * @date 2019/10/09
 */
@Api(tags = "商品标签")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/offer/prodTagsRel")
public class ProdTagsRelController {

    @Autowired
    private ProdTagsRelManager prodTagsManager;

    @ApiOperation(value = "根据tagId，批量查询商品Id，用于分库查询")
    @PostMapping(value = "/goodsId/list")
    public List<String> queryProdGoodsIdListByTagId(@RequestBody String tagId) throws BaseException {
        log.info("prodTagsRelController queryProdGoodsIdListByTagId start");
        List<String> prodGoodsIdList = prodTagsManager.queryProdGoodsIdListByTagId(tagId);
        log.info("prodTagsRelController queryProdGoodsIdListByTagId end");
        return prodGoodsIdList;
    }
}
