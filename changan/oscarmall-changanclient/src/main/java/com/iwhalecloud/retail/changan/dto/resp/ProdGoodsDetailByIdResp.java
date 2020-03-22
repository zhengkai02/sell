package com.iwhalecloud.retail.changan.dto.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品详情
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Data
public class ProdGoodsDetailByIdResp implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编码
     */
    private String sn;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 类型id
     */
    private String typeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 状态
     */
    private String state;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 售后
     */
    private String afterSale;

    /**
     * 销售价
     */
    private Long price;

    /**
     * 成本价
     */
    private Long cost;

    /**
     * 税费
     */
    private Long tax;

    /**
     * 市场价
     */
    private Long mktprice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 查看次数
     */
    private Long viewCount;

    /**
     * 购买数
     */
    private Long buyCount;

    /**
     * 优先级
     */
    private Long sord;

    /**
     * 商品发布人
     */
    private String createBy;

    /**
     * 商品发布人
     */
    private String modifyBy;

    /**
     * 副标题
     */
    private String simpleName;

    /**
     * 最小量
     */
    private Long minNim;

    /**
     * _商家_分类_品牌_商品名称_ search_key like %%
     */
    private String searchKey;

    /**
     * 卖点
     */
    private String sellingPoint;

    /**
     * 下架时间
     */
    private Date marketingEndTime;

    /**
     * 上架时间
     */
    private Date marketingBeginTime;

    /**
     * 地市编码
     */
    private String regionId;

    /**
     * 地市名称
     */
    private String regionName;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 库存数量
     */
    private Long stockQty;

    /**
     * 允许的最大订购数量
     */
    private Long maxBuyCount;

    /**
     * 重复订购标志
     */
    private String duplicateFlag;

    /**
     * 店铺id
     */
    private String storeId;

    /**
     * 店铺目录Id
     */
    private String storeCatId;

    /**
     * 店铺目录名称
     */
    private String storeCatName;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 是否商品包 Y是 N不是
     * 默认N
     */
    private String isPackage;

    /**
     * 调度单标识
     */
    private String dispatchOrderId;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 缩略图
     */
    private String realThumbnail;

    private String saleCatId;

    private String saleCatName;

    private String careFlag;

    private String evaluateAuditMode;

    private Long adjBuyCount;

    /**
     * 暂时不管,以后封库封表用
     */
    private String tenantId;

    private String isRecommend;

    private List<ProdGoodsCatMemResp> prodGoodsCatList;

    private ProductResp productResp;

    private List<ProdGoods> pkgMemList;

    private List<ProdGoodsRelResp> prodGoodsRelList;

    private GoodsAttrThreeTypeResp goodsAttr;

    private List<TagResp> tagList;

    private List<String> tagIds;

    private List<ProdGoodsSalesRuleResp> prodGoodsSaleRuleList;

    private List<ProdGoodsStockWaveResp> prodGoodsStockWaveList;

    private List<ProdGoodsContentResp> contents;

    private List<String> pics;

    /**
     * 商品内容
     */
    private List<QryGoodsContentListResp> goodsContent;

    /**
     * 是否开启SKU Y:开启 N:不开启
     */
    private String turnOnSku;

    /**
     * 给车企的上品同步状态
     */
    private String syncState;
}

