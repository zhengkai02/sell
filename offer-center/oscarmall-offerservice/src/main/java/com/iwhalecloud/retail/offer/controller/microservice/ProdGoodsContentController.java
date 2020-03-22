package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.AddGoodsContentReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryContentReq;
import com.iwhalecloud.retail.offer.dto.resp.AddGoodsContentResp;
import com.iwhalecloud.retail.offer.manager.ProdGoodsContentManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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
@Api(tags = "商品内容")
@Slf4j
@RestController
@RequestMapping("/offer/goodscontent")
public class ProdGoodsContentController {

    @Autowired
    private ProdGoodsContentManager prodGoodsContentManager;

    @PostMapping(value = "/add")
    public AddGoodsContentResp addGoodsContent(@RequestBody AddGoodsContentReq request) throws BaseException {
        log.info("ProdGoodsContentController addGoodsContent start");
        AddGoodsContentResp response = prodGoodsContentManager.addGoodsContent(request);
        log.info("ProdGoodsContentController addGoodsContent end");
        return response;
    }

    @PostMapping(value = "/del/{goodsContentId}")
    public void delGoodsContent(@ApiParam(value = "商品内容ID") @PathVariable String goodsContentId) throws BaseException {
        log.info("ProdGoodsContentController delGoodsContent start");
        prodGoodsContentManager.delGoodsContent(goodsContentId);
        log.info("ProdGoodsContentController delGoodsContent end");
    }

    @PostMapping(value = "/qry/list")
    public ResultVO<ArrayList<String>> getContentIds(@RequestBody ProdGoodsQueryContentReq req) {
        return ResultVOCheckUtil.buildResultVO(prodGoodsContentManager::getContentIds, req);
    }

}
