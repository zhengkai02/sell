package com.iwhalecloud.retail.offer.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/13 <br>
 * @see com.iwhalecloud.retail.offer.dto <br>
 * @since V9.0C<br>
 */
@Data
public class ProdGoodsRedisDto implements Serializable {

    private static final long serialVersionUID = 162364483927826061L;
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
     * 类型id
     */
    private String typeId;

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
     * 销售价
     */
    private Long price;

    /**
     * 成本价
     */
    private Long cost;

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
     * 地市编码
     */
    private String regionId;

    /**
     * 地市名称
     */
    private String regionName;

    /**
     * 上架时间
     */
    private Date marketingBeginTime;

    /**
     * 下架时间
     */
    private Date marketingEndTime;

    /**
     * 库存数量
     */
    private Long stockQty;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 重复订购标志
     */
    private String duplicateFlag;

    /**
     * 允许的最大订购数量
     */
    private Long maxBuyCount;

    /**
     * 店铺id
     */
    private String storeId;

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

    private String afterSale;

    /**
     * 暂时不管,以后封库封表用
     */
    private String tenantId;
}