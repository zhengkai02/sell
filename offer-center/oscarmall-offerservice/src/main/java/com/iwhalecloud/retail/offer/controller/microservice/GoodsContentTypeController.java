package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.offer.entity.GoodsContentType;
import com.iwhalecloud.retail.offer.manager.GoodsContentTypeManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 * @since V9.0C<br>
 */
@Api(tags = "商品内容类型")
@Slf4j
@RestController
@RequestMapping("/offer/goodscontenttype")
public class GoodsContentTypeController {

    @Autowired
    private GoodsContentTypeManager goodsContentTypeManager;

    @GetMapping("/list")
    public List<GoodsContentType> qryGoodsContentType() {
        log.info("GoodsContentTypeController qryGoodsContentType start");
        List<GoodsContentType> goodsContentTypeList = goodsContentTypeManager.qryGoodsContentType();
        log.info("GoodsContentTypeController qryGoodsContentType end");
        return goodsContentTypeList;
    }
}
