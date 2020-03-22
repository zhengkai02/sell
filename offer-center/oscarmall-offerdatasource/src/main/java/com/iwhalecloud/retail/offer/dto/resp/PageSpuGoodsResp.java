package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * SPU关联商品分页
 * 
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Data
public class PageSpuGoodsResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku标识")
    private String skuId;

    @ApiModelProperty(value = "spu标识")
    private String spuId;

    @ApiModelProperty(value = "商品标识")
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "规则")
    private String rule;

    @ApiModelProperty(value = "属性集合")
    private List<PageSkuGoodsAttrResp> attrList;
}
