package com.iwhalecloud.retail.offer.dto.client.resp;

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
public class RightsQueryResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权益实例ID")
    private String rightsRelId;

    /**
     * 权益编码
     */
    @ApiModelProperty(value = "权益编码")
    private String code;

    private String offerId;

    /**
     * 权益名称
     */
    @ApiModelProperty(value = "权益名称")
    private String name;

    /**
     * 权益描述
     */
    @ApiModelProperty(value = "权益描述")
    private String description;

    /**
     * 优惠券到期时间
     */
    @ApiModelProperty(value = "优惠券到期时间")
    private String validityTime;

    /**
     * 权益关联商品编号
     */
    @ApiModelProperty(value = "权益关联商品编号")
    private String relationGoodsId;

    /**
     * 优惠券列表
     */
    @ApiModelProperty(value = "优惠券列表")
    private List<RightsCouponQueryResp> couponList;

    /**
     * 商品列表
     */
    @ApiModelProperty(value = "商品列表")
    private List<RightsGoodsQueryResp> goodsList;

    @ApiModelProperty(value = "权益是否有效 0 未生效 1 已生效")
    private Integer isTake;

}
