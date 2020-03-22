package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * SPU关联商品
 * 
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Data
public class AddSpuRelGoodsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则")
    private String rule;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品sku属性ID")
    private List<String> attrSkuIds;

    @ApiModelProperty(value = "SPU表 sku属性列表 字段")
    private String skuAttrIds;

}
