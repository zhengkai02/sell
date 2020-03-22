package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huminghang
 * @date 2019-06-2
 */
@Data
public class QueryGoodsPriceReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private String goodsId;
}
