package com.iwhalecloud.retail.offer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/2/15 <br>
 * @see com.iwhalecloud.retail.offer.dto <br>
 * @since IOT <br>
 */
@Data
@ApiModel(value = "对应模型prod_goods, 对应实体ProdGoods类")
public class ProdGoodsDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;

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
     * 商品目录
     */
    @ApiModelProperty(value = "商品目录")
    private String catId;

    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID")
    private String typeId;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private Double weight;

    /**
     * 状态 1上架 0下架
     */
    @ApiModelProperty(value = "状态 1上架 0下架")
    private Integer marketEnable;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String intro;

    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Double price;

    /**
     * 成本价
     */
    @ApiModelProperty(value = "成本价")
    private Double cost;

    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktprice;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date modifyTime;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    /**
     * 购买数
     */
    @ApiModelProperty(value = "购买数")
    private Long buyCount;

    /**
     * sord
     */
    @ApiModelProperty(value = "sord")
    private Long sord;

    /**
     * 商品新增操作人
     */
    @ApiModelProperty(value = "商品新增操作人")
    private String createBy;

    /**
     * 商品修改操作人
     */
    @ApiModelProperty(value = "商品修改操作人")
    private String modifyBy;

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
     * 默认图片
     */
    @ApiModelProperty(value = "默认图片")
    private String defaultImage;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supperName;

    /**
     * 好评率
     */
    @ApiModelProperty(value = "好评率")
    private BigDecimal goodsCommentsRate;
    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private Integer goodsCommentsNum;

    /**
     * 是否串码 1串码 0非串码
     */
    @ApiModelProperty(value = "是否串号 1串码 0非串码")
    private Integer isSerialNo;

    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    /**
     * 完整地址
     */
    @ApiModelProperty(value = "完整地址")
    private String showUrl;

    /**
     * 上架时间
     */
    @ApiModelProperty(value = "上架时间")
    private Date marketingBeginTime;

    /**
     * 下架时间
     */
    @ApiModelProperty(value = "下架时间")
    private Date marketingEndTime;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    private String typeName;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称")
    private String saleCatName;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Long stockQty;

    /**
     * 税费
     */
    @ApiModelProperty(value = "税费")
    private Long tax;

    /**
     * 所属店铺标识
     */
    @ApiModelProperty(value = "所属店铺标识")
    private String storeId;

    /**
     * 所属店铺名称
     */
    @ApiModelProperty(value = "所属店铺名称")
    private String storeName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String state;

    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称")
    private List<String> contactChannelNameList;

}

