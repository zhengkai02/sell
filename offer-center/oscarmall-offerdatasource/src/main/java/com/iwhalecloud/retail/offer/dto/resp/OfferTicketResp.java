package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询服务卷列表出参
 * 
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class OfferTicketResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单项资源id")
    private String resId;

    @ApiModelProperty(value = "资源编码")
    private String resCode;

    @ApiModelProperty(value = "资源编号 比如优惠券的编码")
    private String resSn;

    @ApiModelProperty(value = "资源名称")
    private String resName;

    @ApiModelProperty(value = "资源类型 1-免费券 2-折扣券 3-优惠券")
    private String resType;

    @ApiModelProperty(value = "资源生效时间")
    private Date effDate;

    @ApiModelProperty(value = "资源失效时间")
    private Date expDate;

    @ApiModelProperty(value = "A:可用 C:已使用 E:已过期")
    private String state;

    @ApiModelProperty(value = "资源使用时间")
    private Date usedDate;

    @ApiModelProperty(value = "说明")
    private String comments;

    @ApiModelProperty(value = "使用方式 0:二维码 1:兑换码 2:链接")
    private Integer useWay;

    @ApiModelProperty(value = "使用方式名称")
    private String useWayName;

    @ApiModelProperty(value = "券额")
    private Long amount;

    @ApiModelProperty(value = "折扣卷")
    private Long discount;

    @ApiModelProperty(value = "跳转地址")
    private String urlPath;

    @ApiModelProperty(value = "券对应的商品ID")
    private String offerId;

    @ApiModelProperty(value = "券对应的商品名称")
    private String offerName;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "资源对应的供应商")
    private String channel;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "手机号码")
    private String phone;
}
