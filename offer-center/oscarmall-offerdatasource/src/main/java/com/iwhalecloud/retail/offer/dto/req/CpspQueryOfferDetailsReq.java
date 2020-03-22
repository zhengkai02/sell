package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author fanxiaofei
 * @date 2019-07-30
 */
@Data
public class CpspQueryOfferDetailsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品标识 M")
    private String offerId;

    @ApiModelProperty(value = "是否需要内容信息 O")
    private String isGetContentInfo;

}
