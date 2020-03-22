package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品列表 调用亚信接口
 * 
 * @author fanxiaofei
 * @date 2019-03-13
 */
@Data
public class ProductList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "售价")
    private Integer cost;

    @ApiModelProperty(value = "库存")
    private Integer inventory;

    @ApiModelProperty(value = "产品调用开始时间")
    private String validityStartDate;

    @ApiModelProperty(value = "产品调用结束时间")
    private String validityEndDate;

    @ApiModelProperty(value = "产品使用方式:0-二维码 1-兑换码 2-链接")
    private Integer useWay;

    @ApiModelProperty(value = "使用方式名称")
    private String useWayName;

    @ApiModelProperty(value = "是否实名认证 0-否 1-是")
    private Integer isCertification;
}
