package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询属性
 * @author fanxiaofei
 * @date 2019-06-06
 */
@Data
public class QryGoodsAttrListReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "是否查询sku属性，存在即查询")
    private String sku;
}
