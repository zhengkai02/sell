package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 领取用户权益商品入参
 * 
 * @author fanxiaofei
 * @date 2019-03-07
 */
@Data
public class RightsOfferReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权益编码")
    private String rightsCode;

    @ApiModelProperty(value = "商品ID")
    private String offerId;
}
