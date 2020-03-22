package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.offer.entity.ProdGoodsType;
import com.iwhalecloud.retail.offer.manager.ProdGoodsTypeManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * <Description> 商品类型 <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月4日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 */
@Api(tags = "商品类型")
@Slf4j
@RestController("prodGoodsTypeMicroService")
@RequestMapping("/offer/prodgoodstype")
public class ProdGoodsTypeController {
    
    @Autowired
    ProdGoodsTypeManager prodGoodsTypeManager;
    
    @GetMapping("/allprodgoodstype")
    public List<ProdGoodsType> qryAllProdGoodsType() {
        log.info("ProdGoodsTypeController qryAllProdGoodsType start");
        List<ProdGoodsType> result = prodGoodsTypeManager.qryAllProdGoodsType();
        log.info("ProdGoodsTypeController qryAllProdGoodsType end");
        return result;
    }

}
