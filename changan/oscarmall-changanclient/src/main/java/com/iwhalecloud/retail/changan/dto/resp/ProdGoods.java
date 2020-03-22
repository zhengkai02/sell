package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/2/15 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since IOT <br>
 */
@Data
public class ProdGoods implements Serializable {

    /**表名常量*/
    public static final String TNAME = "prod_goods";

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;

    /**
     * 商品目录
     */
    @ApiModelProperty(value = "商品目录")
    private String catId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码")
    private String sn;

    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private String brandId;

    /**
     * 状态 1上架 0下架
     */
    @ApiModelProperty(value = "状态 1上架 0下架")
    private Integer marketEnable;

    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID")
    private String typeId;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String intro;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private Double weight;

    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Double price;

    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktprice;

    /**
     * 成本价
     */
    @ApiModelProperty(value = "成本价")
    private Double cost;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date lastModify;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**
     * 购买数
     */
    @ApiModelProperty(value = "购买数")
    private Long buyCount;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    /**
     * 放入回收站时为1，正常为0
     */
    @ApiModelProperty(value = "放入回收站时为1，正常为0")
    private Long disabled;

    /**
     * sord
     */
    @ApiModelProperty(value = "优先级")
    private Long sord;

    /**
     * 商品发布人
     */
    @ApiModelProperty(value = "商品发布人")
    private String creatorUser;

    /**
     * 供货商id
     */
    @ApiModelProperty(value = "供货商id")
    private String supperId;

    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String auditState;

    /**
     * 产品简称
     */
    @ApiModelProperty(value = "产品简称")
    private String simpleName;

    /**
     * 最小量
     */
    @ApiModelProperty(value = "最小量")
    private Long minNim;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * _商家_分类_品牌_商品名称_ search_key like %%
     */
    @ApiModelProperty(value = "_商家_分类_品牌_商品名称_ search_key like %%")
    private String searchKey;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;

    /**
     * 地市编码
     */
    @ApiModelProperty(value = "地市编码")
    private String regionId;

    /**
     * 地市名称
     */
    @ApiModelProperty(value = "地市名称")
    private String regionName;

    /**
     * 是否串码 1串码 0非串码
     */
    @ApiModelProperty(value = "是否串码 1串码 0非串码")
    private Integer isSerialNo;


    //属性 end

    public enum FieldNames {
        /** 商品id */
        GOODS_ID,
        /** 商品名称 */
        NAME,
        /** 商品编码 */
        SN,
        /** 品牌id */
        BRAND_ID,
        /** 商品目录 */
        CAT_ID,
        /** 商品类型 */
        TYPE_ID,
        /** 重量 */
        WEIGHT,
        /** 状态 1上架 0下架 */
        MARKET_ENABLE,
        /** 介绍 */
        INTRO,
        /** 销售价 */
        PRICE,
        /** 成本价 */
        COST,
        /** 市场价 */
        MKT_PRICE,
        /** 创建时间 */
        CREATE_TIME,
        /** 更新时间 */
        LAST_MODIFY,
        /** 查看次数 */
        VIEW_COUNT,
        /** 购买数 */
        BUY_COUNT,
        /** 放入回收站时为1，正常为0 */
        DISABLED,
        /** sord */
        SORD,
        /** 商品发布人 */
        CREATOR_USER,
        /** 供货商id */
        SUPPER_ID,
        /** 审批状态 */
        AUDIT_STATE,
        /** 产品简称 */
        SIMPLE_NAME,
        /** 最小量 */
        MIN_NIM,
        /** 单位 */
        UNIT,
        /** _商家_分类_品牌_商品名称_ search_key like %% */
        SEARCH_KEY,
        /** 卖点 */
        SELLING_POINT,
        /** 地市编码 */
        REGION_ID,
        /** 地市名称 */
        REGION_NAME,
        /** 是否串号 */
        IS_SERIAL_NO
    }

}

