package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 关联商品分页
 * 
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Data
public class PageSpuRelGoodsResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "类型id 1.终端 2.套餐 3.配件 4.合约计划 5.其它 6.券类 7.内容会员 8 权益")
    private String typeName;

    @ApiModelProperty(value = "库存数量")
    private Long stockQty;

    @ApiModelProperty(value = "销售价")
    private Long price;

    @ApiModelProperty(value = "销售目录名称")
    private String catName;

    @ApiModelProperty(value = "商品sku属性拼接")
    private String attrSkuName;

    @ApiModelProperty(value = "商品sku属性ID")
    private List<String> attrSkuIds;

    @ApiModelProperty(value = "SPU表 sku属性列表 字段")
    private String skuAttrIds;

    @ApiModelProperty(value = "SPU关联商品表 的规则 字段")
    private String rule;

    @ApiModelProperty(value = "销售渠道名称")
    private String prodGoodsSalesConditionName;

}
