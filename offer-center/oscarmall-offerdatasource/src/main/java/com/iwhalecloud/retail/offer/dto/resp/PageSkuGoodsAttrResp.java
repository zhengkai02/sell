package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Data
public class PageSkuGoodsAttrResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则")
    private String attrId;

    @ApiModelProperty(value = "规则")
    private String attrName;

    @ApiModelProperty(value = "规则")
    private String attrValue;

}
