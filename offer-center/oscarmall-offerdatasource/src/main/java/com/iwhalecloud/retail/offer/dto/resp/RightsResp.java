package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询用户权益出参
 * 
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class RightsResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权益描述")
    private String description;

    @ApiModelProperty(value = "优惠券到期时间")
    private String validityTime;

    @ApiModelProperty(value = "权益编码")
    private String rightsCode;

    @ApiModelProperty(value = "权益名称")
    private String rightName;

    @ApiModelProperty(value = "权益对应商品id")
    private String offerId;

    @ApiModelProperty(value = "权益实例ID")
    private String rightsRelId;

    @ApiModelProperty(value = "权益名称")
    private String offerName;

    @ApiModelProperty(value = "优惠券列表")
    private List<RightsCouponResp> coupons;

    @ApiModelProperty(value = "商品列表")
    private List<RightsGoodsResp> offers;

    @ApiModelProperty(value = "权益是否有效 0 未生效 1 已生效")
    private Integer isTake;
}
