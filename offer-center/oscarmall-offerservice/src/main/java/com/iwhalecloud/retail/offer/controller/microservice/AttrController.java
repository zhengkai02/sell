package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.AttrDelReq;
import com.iwhalecloud.retail.offer.dto.req.QryAttrReq;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsAttrListReq;
import com.iwhalecloud.retail.offer.dto.req.QueryAttrListReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrResp;
import com.iwhalecloud.retail.offer.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.offer.entity.Attr;
import com.iwhalecloud.retail.offer.entity.AttrValue;
import com.iwhalecloud.retail.offer.manager.AttrManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "属性")
@RestController("attrMicroService")
@RequestMapping("/offer/attr")
@Slf4j
public class AttrController {

    @Autowired
    private AttrManager attrManager;


    /**
     * 查询所有属性
     *
     * @return List<AttrResp>
     * @author fanxiaofei
     */
    @ApiOperation(value = "查询所有属性")
    @PostMapping("/list")
    public Page<AttrResp> qryAttrList(@RequestBody QryAttrReq request) throws BaseException {
        log.info("AttrController qryAttrList start request = [{}]", request);
        Page<AttrResp> result = attrManager.qryAttrList(request);
        log.info("AttrController qryAttrList end");
        return result;
    }


    /**
     * 根据商品id过滤当前商品已有属性的其他所有属性
     *
     * @param goodsId 商品id
     * @return List<AttrResp>
     * @author fanxiaofei
     */
    @ApiOperation(value = "根据商品id过滤当前商品已有属性的其他所有属性")
    @GetMapping("/out/{goodsId}")
    public List<AttrResp> qryOutAttrListByGoodsId(@ApiParam(value = "商品ID") @PathVariable String goodsId) {
        log.info("AttrController qryOutAttrListByGoodsId start");
        List<AttrResp> result = attrManager.qryOutAttrListByGoodsId(goodsId);
        log.info("AttrController qryOutAttrListByGoodsId end");
        return result;
    }


    /**
     * 根据目录id,商品id,sku三者过滤查询属性
     *
     * @param req 商品id QryGoodsAttrListReq
     * @return List<GoodsCatAttrResp>
     */
    @ApiOperation(value = "根据目录id,商品id,sku三者过滤查询属性")
    @PostMapping("/filter")
    public List<GoodsCatAttrResp> qryAttrListByFilter(@RequestBody QryGoodsAttrListReq req) {
        log.info("AttrController qryAttrListByFilter start");
        List<GoodsCatAttrResp> result = attrManager.qryAttrListByFilter(req);
        log.info("AttrController qryAttrListByFilter end");
        return result;
    }


    @PostMapping("/add")
    public AttrResp addAttr(@RequestBody AttrReq request) throws BaseException {
        log.info("AttrController addAttr start");
        AttrResp result = attrManager.addAttr(request);
        log.info("AttrController addAttr end");
        return result;
    }


    @PostMapping("/mod/{attrId}")
    public AttrResp modAttr(@ApiParam(value = "属性ID") @PathVariable String attrId, @RequestBody AttrReq request) throws BaseException {
        log.info("AttrController modAttr start");
        AttrResp result = attrManager.modAttr(attrId, request);
        log.info("AttrController modAttr end");
        return result;
    }

    @PostMapping("/del")
    public void delAttr(@RequestBody AttrDelReq request) throws BaseException {
        log.info("AttrController delAttr start request = [{}]", request);
        attrManager.delAttr(request);
        log.info("AttrController delAttr end");
    }


    @PostMapping(value = "/{attrId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Attr queryAttrById(@ApiParam(value = "属性ID") @PathVariable String attrId) {
        log.info("AttrController queryAttrById start");
        Attr result = attrManager.queryAttrById(attrId);
        log.info("AttrController queryAttrById end");
        return result;
    }

    @PostMapping("/attrcode/{attrCode}")
    public ResultVO<Attr> selectAttrByCode(@ApiParam(value = "属性编码") @PathVariable(value = "attrCode") String attrCode) {
        log.info("AttrController selectAttrByCode start");
        ResultVO<Attr> result = ResultVOCheckUtil.buildResultVO(attrManager::queryAttrByCode, attrCode);
        log.info("AttrController selectAttrByCode end");
        return result;
    }

    @PostMapping("/attrcodeNoTenantId/{attrCode}")
    public ResultVO<Attr> selectByCodeNoTenantId(@ApiParam(value = "属性编码") @PathVariable(value = "attrCode") String attrCode) {
        log.info("AttrController selectByCodeNoTenantId start");
        ResultVO<Attr> result = ResultVOCheckUtil.buildResultVO(attrManager::selectByCodeNoTenantId, attrCode);
        log.info("AttrController selectByCodeNoTenantId end");
        return result;
    }

    @PostMapping(value = "/attrvalue/{attrId}")
    public List<AttrValue> queryAttrValueByAttrId(@ApiParam(value = "属性ID") @PathVariable String attrId) {
        log.info("AttrController queryAttrValueByAttrId start");
        List<AttrValue> result = attrManager.queryAttrValueByAttrId(attrId);
        log.info("AttrController queryAttrValueByAttrId end");
        return result;
    }

    /**
     * 查询所有属性，供其他中心补充attr相关信息用
     *
     * @return List<AttrResp>
     * @author wu.bo3
     */
    @ApiOperation(value = "查询属性")
    @PostMapping("/array")
    public ResultVO<ArrayList<AttrResp>> qryAttrListByCond(@RequestBody QueryAttrListReq request) throws BaseException {
        log.info("AttrController qryAttrListByCond start request = [{}]", request);
        ResultVO<ArrayList<AttrResp>> result = ResultVOCheckUtil.buildResultVO(attrManager::qryAttrListByCond, request);
        log.info("AttrController qryAttrListByCond end, result= [{}]", result);
        return result;
    }

}
