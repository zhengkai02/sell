package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddProdBuyCountReq implements Serializable {

    private static final long serialVersionUID = -346059233143544409L;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "库存")
    private Long qty;
}
