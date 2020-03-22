package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 * 
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
@TableName("tbl_prod_goods")
public class ProdGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 商品id
     */
    @TableId(type = IdType.INPUT)
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
     * 类型id
     */
    @ApiModelProperty(value = "类型id 1.终端 2.套餐 3.配件 4.合约计划 5.其它 6.券类 7.内容会员 8 权益")
    private String typeId;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private Double weight;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态 A 新建  B 待发布  C 已发布  X 失效")
    private String state;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String intro;

    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Long price;

    /**
     * 成本价
     */
    @ApiModelProperty(value = "成本价")
    private Long cost;

    @ApiModelProperty(value = "税费")
    private Long tax;

    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Long mktprice;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    /**
     * 购买数
     */
    @ApiModelProperty(value = "购买数")
    private Long buyCount = 0L;

    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private Long sord;

    /**
     * 商品发布人
     */
    @ApiModelProperty(value = "商品发布人")
    private String createBy;

    /**
     * 商品发布人
     */
    @ApiModelProperty(value = "商品更新人")
    private String modifyBy;

    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    private String simpleName;

    /**
     * 最小量
     */
    @ApiModelProperty(value = "最小量")
    private Long minNim;

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
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Long stockQty;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 重复订购标志
     */
    @ApiModelProperty(value = "重复订购标志")
    private String duplicateFlag;

    /**
     * 允许的最大订购数量
     */
    @ApiModelProperty(value = "允许的最大订购数量")
    private Long maxBuyCount;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    /**
     * 是否商品包 Y是 N不是 默认N
     */
    @ApiModelProperty(value = "是否商品包 Y是 N不是 默认N")
    private String isPackage;

    /**
     * 调度单标识
     */
    @ApiModelProperty(value = "调度单标识")
    private String dispatchOrderId;

    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "售后服务")
    private String afterSale;

    /**
     * 暂时不管,以后封库封表用
     */
    @ApiModelProperty(value = "tenantId")
    private String tenantId;

    /**
     * 好评率
     **/
    @ApiModelProperty(value = "好评率")
    private Integer evaluationRate = 0;

    /**
     * 评价数
     **/
    @ApiModelProperty(value = "好评数")
    private Integer evaluationCount = 0;

    /**
     * 是否热销商品 Y是 N不是 默认N
     */
    @ApiModelProperty(value = "是否热销商品 Y是 N不是 默认N")
    private String isHot;

    /**
     * 是否推荐商品 Y是 N不是 默认N
     */
    @ApiModelProperty(value = "是否推荐商品 Y是 N不是 默认N")
    private String isRecommend;

    @ApiModelProperty(value = "是否开启SKU Y:开启 N:不开启")
    private String turnOnSku;

    /**
     * Y:关怀商品; N/Null：非关怀商品
     */
    @ApiModelProperty(value = "是否关怀商品  Y:关怀商品; N：非关怀商品")
    private String careFlag;

    /**
     * 新增销量
     */
    @ApiModelProperty(value = "新增销量")
    private Long adjBuyCount = 0L;

    /**
     * 评分调整值
     **/
    @ApiModelProperty(value = "评分调整值")
    private Long adjEvaluationRate = 0L;

    /**
     * 评论数量调整值
     **/
    @ApiModelProperty(value = "评论数量调整值")
    private Long adjEvaluationCount = 0L;

    @ApiModelProperty(value = "评价审核方式  A:自动; M：手动")
    private String evaluateAuditMode;

    @ApiModelProperty(value = "实名认证  Y:需要实名认证 N/Null:不需要实名认证")
    private String isCertification;

}
