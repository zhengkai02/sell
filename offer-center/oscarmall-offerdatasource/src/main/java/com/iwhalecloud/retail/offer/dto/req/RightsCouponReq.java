package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 领取权益优惠券入参
 * @author fanxiaofei
 * @date 2019-03-07
 */
@Data
public class RightsCouponReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权益编码")
    private String rightsCode;

    @ApiModelProperty(value = "优惠券规格ID")
    private Long couponSpecId;
}
