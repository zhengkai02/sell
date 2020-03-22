package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品详情封装的PackageMember
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Data
public class PackageMemberResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private String offerId;

    @ApiModelProperty(value = "商品名称")
    private String offerName;

    @ApiModelProperty(value = "数量")
    private String qty;

    @ApiModelProperty(value = "缩略图地址")
    private String thumbnail;

}

