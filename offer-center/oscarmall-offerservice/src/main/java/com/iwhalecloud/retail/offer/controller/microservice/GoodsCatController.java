package com.iwhalecloud.retail.offer.controller.microservice;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.client.req.QueryOfferCatgReq;
import com.iwhalecloud.retail.offer.dto.req.AddGoodsCatReq;
import com.iwhalecloud.retail.offer.dto.req.AddSaleCatReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsCatAttrListReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsCatReq;
import com.iwhalecloud.retail.offer.dto.req.ModRelaBrandCatgReq;
import com.iwhalecloud.retail.offer.dto.req.ModSaleCatReq;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsCatAttrListReq;
import com.iwhalecloud.retail.offer.dto.req.QryProdGoodsCatReq;
import com.iwhalecloud.retail.offer.dto.resp.AddGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.AddSaleCatResp;
import com.iwhalecloud.retail.offer.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.offer.dto.resp.ModSaleCatResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatListResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandTenantResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import com.iwhalecloud.retail.offer.manager.GoodsCatManager;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/25 <br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 * @since V9.0C<br>
 */
@Api(tags = "商品目录")
@RestController("goodsCatMicroService")
@RequestMapping("/offer/goodscat")
@Slf4j
public class GoodsCatController {

    @Autowired
    private GoodsCatManager goodsCatManager;

    @PostMapping("/list")
    public List<QryGoodsCatListResp> qryGoodsCatList(@RequestBody QryProdGoodsCatReq req) throws BaseException {
        log.info("GoodsCatController qryGoodsCatList start.");
        List<QryGoodsCatListResp> response = goodsCatManager.qryGoodsCatList(req);
        log.info("GoodsCatController qryGoodsCatList end.");
        return response;
    }

    @PostMapping("/page")
    public Page<QryGoodsCatListResp> qryGoodsCatPage(@RequestBody QryProdGoodsCatReq req) throws BaseException {
        log.info("GoodsCatController qryGoodsCatPage start.");
        Page<QryGoodsCatListResp> response = goodsCatManager.qryGoodsCatPage(req);
        log.info("GoodsCatController qryGoodsCatPage end.");
        return response;
    }

    @PostMapping("/out/list")
    public List<QryGoodsCatListResp> qryOutGoodsCatList(@RequestBody QryProdGoodsCatReq req) throws BaseException {
        log.info("GoodsCatController qryOutGoodsCatList start.");
        List<QryGoodsCatListResp> response = goodsCatManager.qryOutGoodsCatList(req);
        log.info("GoodsCatController qryOutGoodsCatList end.");
        return response;
    }

    @PostMapping("/format/list/{storeId}")
    public List<QryGoodsCatListResp> qryFormatGoodsCatList(@ApiParam(value = "店铺ID") @PathVariable String storeId) throws BaseException {
        log.info("GoodsCatWebappController qryGoodsCatList start.");
        List<QryGoodsCatListResp> response = goodsCatManager.qryFormatGoodsCatList(storeId);
        log.info("GoodsCatWebappController qryGoodsCatList end.");
        return response;
    }

    @PostMapping("/detail/{catId}")
    public QryGoodsCatListResp qryGoodsCatDetail(@ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        log.info("GoodsCatController qryGoodsCatDetail start.");
        QryGoodsCatListResp response = goodsCatManager.qryGoodsCatDetail(catId);
        log.info("GoodsCatController qryGoodsCatDetail end.");
        return response;
    }

    @PostMapping(value = "/list/catg")
    public List<QryGoodsCatListResp> qryGoodsCatListForMobile(@RequestBody QueryOfferCatgReq req) throws BaseException {
        log.info("GoodsCatController qryGoodsCatListForMobile start");
        List<QryGoodsCatListResp> response = goodsCatManager.listProdGoodsCat(req);
        log.info("GoodsCatController qryGoodsCatListForMobile end");
        return response;
    }

    @PostMapping("")
    public AddGoodsResp addGoodsCat(@RequestBody AddGoodsCatReq req) throws BaseException {
        log.info("GoodsCatController addGoodsCat start.");
        AddGoodsResp resp = goodsCatManager.addGoodsCat(req);
        log.info("GoodsCatController addGoodsCat end.");
        return resp;
    }

    /**
     * 新增销售目录  因为有关联目录和目录属性的处理，因此不再原来的接口上冗余了，单独写个接口
     *
     * @param req
     * @return
     * @throws BaseException
     */
    @PostMapping("/sale")
    public AddSaleCatResp addGoodsSaleCat(@RequestBody AddSaleCatReq req) throws BaseException {
        log.info("GoodsCatController addGoodsSaleCat start req = [{}]", req);
        AddSaleCatResp resp = goodsCatManager.addGoodsSaleCat(req);
        log.info("GoodsCatController addGoodsSaleCat end");
        return resp;
    }

    @PostMapping("/{catId}")
    public ProdGoodsCat modGoodsCat(@ApiParam(value = "目录ID") @PathVariable String catId, @RequestBody ModGoodsCatReq req) throws BaseException {
        log.info("GoodsCatController modGoodsCat start.");
        ProdGoodsCat resp = goodsCatManager.modGoodsCat(catId, req);
        log.info("GoodsCatController modGoodsCat end.");
        return resp;
    }

    @PostMapping("/sale/{catId}")
    public ModSaleCatResp modGoodsSaleCat(@ApiParam(value = "目录ID") @PathVariable String catId, @RequestBody ModSaleCatReq req) throws BaseException {
        log.info("GoodsCatController modGoodsSaleCat start. catId = ", catId);
        ModSaleCatResp resp = goodsCatManager.modGoodsSaleCat(catId, req);
        log.info("GoodsCatController modGoodsSaleCat end.");
        return resp;
    }

    @PostMapping("/delcat/{catId}")
    public void delGoodsCat(@ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        log.info("GoodsCatController delGoodsCat start.");
        goodsCatManager.delGoodsCat(catId);
        log.info("GoodsCatController delGoodsCat end.");
    }


    /**
     * 根据商品id取所有销售类型的目录
     *
     * @param goodsId 商品id
     * @return List<ProdGoodsCat>
     */
    @PostMapping("/list/{goodsId}")
    public List<ProdGoodsCat> qryProdGoodsByGoodsId(@ApiParam(value = "商品ID") @PathVariable String goodsId) {
        log.info("GoodsCatController qryProdGoodsByGoodsId start");
        List<ProdGoodsCat> result = goodsCatManager.qryProdGoodsByGoodsId(goodsId);
        log.info("GoodsCatController qryProdGoodsByGoodsId end");
        return result;
    }

    /**
     * 根据目录id取所有目录属性
     *
     * @param req QryGoodsCatAttrListReq
     * @return List<AttrResp>
     */
    @PostMapping("/attr")
    public Page<GoodsCatAttrResp> qryAttrListByCatId(@RequestBody QryGoodsCatAttrListReq req) {
        log.info("GoodsCatController qryAttrListByCatId start");
        Page<GoodsCatAttrResp> result = goodsCatManager.qryAttrListByCatId(req);
        log.info("GoodsCatController qryAttrListByCatId end");
        return result;
    }


    /**
     * 根据目录id过滤当前目录已有属性的其他所有属性
     *
     * @param req QryGoodsCatAttrListReq
     * @return List<GoodsCatAttrResp>
     */
    @ApiOperation(value = "根据目录id过滤当前目录已有属性的其他所有属性")
    @PostMapping("/attr/out")
    public Page<GoodsCatAttrResp> qryOutAttrListByCatId(@RequestBody QryGoodsCatAttrListReq req) {
        log.info("GoodsCatController qryOutAttrListByCatId start");
        Page<GoodsCatAttrResp> result = goodsCatManager.qryOutAttrListByCatId(req);
        log.info("GoodsCatController qryOutAttrListByCatId end");
        return result;
    }

    /**
     * 修改当前目录id的属性
     *
     * @param req QryGoodsCatAttrListReq
     * @return List<GoodsCatAttrResp>
     */
    @ApiOperation(value = "修改当前目录id的属性")
    @PostMapping("/attr/update")
    public void modGoodsCatAttrList(@RequestBody ModGoodsCatAttrListReq req) throws BaseException {
        log.info("GoodsCatController modGoodsCatAttrList start");
        goodsCatManager.modGoodsCatAttrList(req);
        log.info("GoodsCatController modGoodsCatAttrList end");
    }

    @PostMapping("/list/brand/{catId}")
    public List<TblGoodsBrandTenantResp> qryGoodBrandByCatgId(@ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        log.info("GoodsCatController qryGoodBrandByCatgId catId = [{}]", catId);
        List<TblGoodsBrandTenantResp> result = goodsCatManager.qryGoodBrandByCatgId(catId);
        log.info("GoodsCatController qryGoodBrandByCatgId end");
        return result;
    }

    @PostMapping("/mod/rela/brand/{catId}")
    public void modRelaGoodBrandByCatgId(@ApiParam(value = "目录ID") @PathVariable String catId, @RequestBody ModRelaBrandCatgReq req) throws BaseException {
        log.info("GoodsCatController modRelaGoodBrandByCatgId start catId = [{}]", catId);
        goodsCatManager.modRelaGoodBrandByCatgId(catId, req);
        log.info("GoodsCatController modRelaGoodBrandByCatgId end");
    }

    @ApiOperation(value = "根据目录标识查询目录信息")
    @PostMapping("/simple/{catId}")
    public ResultVO<ProdGoodsCat> qryGoodsCatInfoByCatId(@ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        log.info("GoodsCatController qryGoodsCatInfoByCatId start catId = [{}]", catId);

        ResultVO<ProdGoodsCat> prodGoodsCatResultVO = ResultVOCheckUtil.buildResultVO(goodsCatManager::qryGoodsCatInfoByCatId, catId);
        log.info("GoodsCatController qryGoodsCatInfoByCatId end, prodGoodsCatResultVO = [{}]", prodGoodsCatResultVO);
        return prodGoodsCatResultVO;
    }

    @ApiOperation(value = "查询所有叶子节点的管理目录")
    @PostMapping("/allchildmgncat")
    public ResultVO<ArrayList<ProdGoodsCat>> qryAllChildMgnCat() throws BaseException {
        log.info("GoodsCatController qryAllChildMgnCat start.");

        ResultVO<ArrayList<ProdGoodsCat>> resultVO = ResultVOCheckUtil.buildResultVO(goodsCatManager::qryAllChildMgnCat);
        log.info("GoodsCatController qryAllChildMgnCat end, resultVO = [{}]", resultVO);
        return resultVO;
    }

    @ApiOperation(value = "修改管理目录是否可开发票，只能从不可开发票变成可开发票，不能反过来")
    @PostMapping("/modinvoiceflag/{catId}")
    public ResultVO modInvoiceFlag(@ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        log.info("GoodsCatController modInvoiceFlag start catId = [{}]", catId);

        ResultVO resultVO = ResultVOCheckUtil.buildResultVONoReturn(goodsCatManager::modInvoiceFlag, catId);
        log.info("GoodsCatController modInvoiceFlag end.");
        return resultVO;
    }

    @ApiOperation(value = "查询该销售目录下是否可以创建子目录")
    @GetMapping("/canCreate/{catId}")
    public Boolean canCreateSubs(@ApiParam(value = "目录ID") @PathVariable("catId") String catId) throws BaseException {
        log.info("GoodsCatController canCreateSubs start. catId = [{}]", catId);
        Boolean result = goodsCatManager.canCreateSubs(catId);
        log.info("GoodsCatController canCreateSubs end");
        return result;
    }

}
