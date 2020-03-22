package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName OfferDetailMobileQryReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/21 18:54
 **/
@Data
public class OfferDetailMobileQryReq implements Serializable {

    private static final long serialVersionUID = 3512649970371231269L;

    /**
     * offerId
     */
    @ApiModelProperty(value = "商品ID")
    private String offerId;

    /**
     * offerCode
     */
    @ApiModelProperty(value = "商品编码")
    private String offerCode;


    /**
     * Y 全部状态 空为不为X的状态
     *
     */
    @ApiModelProperty(value = "Y 全部状态 空为不为X的状态")
    private String allFlag;

    /**
     *  商品分享标志
     */
    @ApiModelProperty(value = "分享标志 Y : 分享 空为非分享")
    private String shareFlag;

    /**
     *  渠道
     */
    @ApiModelProperty(value = "渠道")
    private String channel;

    /**
     *  用户标识
     */
    @ApiModelProperty(value = "用户标识")
    private String userId;

    /**
     *  租户标识
     */
    @ApiModelProperty(value = "租户标识")
    private String tenantId;

}
