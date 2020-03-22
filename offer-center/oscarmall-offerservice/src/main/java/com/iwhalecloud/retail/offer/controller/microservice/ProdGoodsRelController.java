package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsRelReq;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.manager.ProdGoodsRelManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * <Description> 商品关系 <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月7日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 */
@Api(tags = "商品关系")
@Slf4j
@RestController("prodGoodsRelMicroService")
@CrossOrigin
@RequestMapping("/offer")
public class ProdGoodsRelController {
    
    @Autowired
    ProdGoodsRelManager prodGoodsRelManager;
    
    @PostMapping("/addgooodsrela")
    public int createProdGoodsRel(@RequestBody ProdGoodsRelReq req) throws BaseException {
        log.info("ProdGoodsRelController createProdGoodsRel start");
        int result = prodGoodsRelManager.updateProdGoodsRel(req);
        log.info("ProdGoodsRelController createProdGoodsRel end");
        return result;
    }
    
    @PostMapping("/delgooodsrela")
    public int delProdGoodsRel(@RequestBody ProdGoodsRelReq req) throws BaseException {
        log.info("ProdGoodsRelController delProdGoodsRel start");
        int result = prodGoodsRelManager.delProdGoodsRel(req);
        log.info("ProdGoodsRelController delProdGoodsRel end");
        return result;
    }
    
    @PostMapping("/excludeProdgoods")
    public List<ProdGoods> qryExcludeProdGoods(@RequestBody ProdGoodsRelReq req) throws BaseException {
        log.info("ProdGoodsRelController qryExcludeProdGoods start");
        List<ProdGoods> result = prodGoodsRelManager.qryExcludeProdGoods(req);
        log.info("ProdGoodsRelController qryExcludeProdGoods end");
        return result;
    }

}
