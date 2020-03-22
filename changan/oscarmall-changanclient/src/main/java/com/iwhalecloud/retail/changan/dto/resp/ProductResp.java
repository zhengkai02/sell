package com.iwhalecloud.retail.changan.dto.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品
 * @author fanxiaofei
 * @date 2019-03-13
 */
@Data
public class ProductResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "成本价")
    private Integer cost;

    @ApiModelProperty(value = "存货")
    private Integer inventory;

    @ApiModelProperty(value = "生效时间")
    private Date validityStartDate;

    @ApiModelProperty(value = "过期时间")
    private Date validityEndDate;

    @ApiModelProperty(value = "用途")
    private Integer useWay;

    @ApiModelProperty(value = "用途名")
    private String useWayName;

    @ApiModelProperty(value = "是否实名认证 0-否 1-是")
    private Integer isCertification;
}
