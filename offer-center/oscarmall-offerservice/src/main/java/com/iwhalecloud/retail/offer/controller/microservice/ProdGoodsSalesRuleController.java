package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.offer.dto.ProdGoodsSalesRuleDTO;
import com.iwhalecloud.retail.offer.manager.ProdGoodsSalesRuleManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品销售规则
 * @author fanxiaofei
 * @date 2019/03/22
 */
@Api(tags = "商品销售规则")
@Slf4j
@RestController
@RequestMapping("/offer/prodgoodssalesrule")
public class ProdGoodsSalesRuleController {

    @Autowired
    private ProdGoodsSalesRuleManager prodGoodsSalesRuleManager;


    /**
     * 查询商品销售规则
     * @return List<ProdGoodsSalesRule>
     */
    @ApiOperation(value = "查询商品销售规则")
    @PostMapping(value = "/list")
    public List<ProdGoodsSalesRuleDTO> listProdGoodsSalesRule() {
        log.info("ProdGoodsSalesRuleWebappController listProdGoodsSalesRule start");
        List<ProdGoodsSalesRuleDTO> result = prodGoodsSalesRuleManager.listProdGoodsSalesRule();
        log.info("ProdGoodsSalesRuleWebappController listProdGoodsSalesRule end");
        return result;
    }

}
