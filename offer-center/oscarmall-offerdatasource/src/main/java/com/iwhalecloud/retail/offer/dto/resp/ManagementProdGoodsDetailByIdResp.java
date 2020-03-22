package com.iwhalecloud.retail.offer.dto.resp;

import com.iwhalecloud.retail.offer.entity.ProdGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 后台管理的商品详情
 * @author fanxiaofei
 * @date 2019/06/05
 */
@Data
public class ManagementProdGoodsDetailByIdResp implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品编码")
    private String sn;

    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "类型id 1.终端 2.套餐 3.配件 4.合约计划 5.其它 6.券类 7.内容会员 8 权益")
    private String typeId;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "重量")
    private Double weight;

    @ApiModelProperty(value = "状态 A 新建  B 待发布  C 已发布  X 失效")
    private String state;

    @ApiModelProperty(value = "介绍")
    private String intro;

    @ApiModelProperty(value = "售后")
    private String afterSale;

    @ApiModelProperty(value = "销售价")
    private Long price;

    @ApiModelProperty(value = "成本价")
    private Long cost;

    @ApiModelProperty(value = "税费")
    private Long tax;

    @ApiModelProperty(value = "市场价")
    private Long mktprice;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    @ApiModelProperty(value = "购买数")
    private Long buyCount;

    @ApiModelProperty(value = "优先级")
    private Long sord;

    @ApiModelProperty(value = "商品发布人")
    private String createBy;

    @ApiModelProperty(value = "商品更新人")
    private String modifyBy;

    @ApiModelProperty(value = "副标题")
    private String simpleName;

    @ApiModelProperty(value = "最小量")
    private Long minNim;

    @ApiModelProperty(value = "_商家_分类_品牌_商品名称_ search_key like %%")
    private String searchKey;

    @ApiModelProperty(value = "卖点")
    private String sellingPoint;

    @ApiModelProperty(value = "地市编码")
    private String regionId;

    @ApiModelProperty(value = "地市名称")
    private String regionName;

    @ApiModelProperty(value = "上架时间")
    private Date marketingBeginTime;

    @ApiModelProperty(value = "下架时间")
    private Date marketingEndTime;

    @ApiModelProperty(value = "库存数量")
    private Long stockQty;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "重复订购标志")
    private String duplicateFlag;

    @ApiModelProperty(value = "允许的最大订购数量")
    private Long maxBuyCount;

    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺目录ID")
    private String storeCatId;

    @ApiModelProperty(value = "店铺目录名称")
    private String storeCatName;

    @ApiModelProperty(value = "是否推荐商品")
    private String isRecommend;

    @ApiModelProperty(value = "是否关怀商品")
    private String careFlag;

    @ApiModelProperty(value = "评论数量调整值")
    private String evaluateAuditMode;

    @ApiModelProperty(value = "新增数量")
    private Long adjBuyCount;

    @ApiModelProperty(value = "是否商品包 Y是 N不是 默认N")
    private String isPackage;

    @ApiModelProperty(value = "调度单标识")
    private String dispatchOrderId;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "缩略图")
    private String realThumbnail;

    @ApiModelProperty(value = "销售目录ID")
    private String saleCatId;

    @ApiModelProperty(value = "销售目录名称")
    private String saleCatName;

    /**
     * 好评率
     **/
    @ApiModelProperty(value = "好评率")
    private Long evaluationRate;

    /**
     * 评价数
     **/
    @ApiModelProperty(value = "评价数")
    private Long evaluationCount;

    /**
     * 暂时不管,以后封库封表用
     */
    @ApiModelProperty(value = "tanantId")
    private Long tanantId;

    @ApiModelProperty(value = "商品目录")
    private List<ProdGoodsCatMemResp> prodGoodsCatList;

    @ApiModelProperty(value = "产品")
    private ProductResp productResp;

    @ApiModelProperty(value = "商品包成员")
    private List<ProdGoods> pkgMemList;

    @ApiModelProperty(value = "商品关系")
    private List<ProdGoodsRelResp> prodGoodsRelList;

    @ApiModelProperty(value = "属性")
    private GoodsAttrThreeTypeResp goodsAttr;

    @ApiModelProperty(value = "标签")
    private List<TagResp> tagList;

    @ApiModelProperty(value = "标签Id")
    private List<String> tagIds;

    @ApiModelProperty(value = "商品适用规则")
    private List<ProdGoodsSalesRuleResp> prodGoodsSaleRuleList;

    @ApiModelProperty(value = "自动库存上下架")
    private List<ProdGoodsStockWaveResp> prodGoodsStockWaveList;

    @ApiModelProperty(value = "商品内容")
    private List<ProdGoodsContentResp> contents;

    @ApiModelProperty(value = "商品主图片地址")
    private List<String> pics;

    @ApiModelProperty(value = "商品内容")
    private List<QryGoodsContentListResp> goodsContent;

    @ApiModelProperty(value = "是否开启SKU Y:开启 N:不开启")
    private String turnOnSku;
}

