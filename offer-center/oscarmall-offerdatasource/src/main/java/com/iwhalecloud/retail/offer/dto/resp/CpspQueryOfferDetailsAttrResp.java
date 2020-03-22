package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author fanxiaofei
 * @date 2019-07-30
 */
@Data
public class CpspQueryOfferDetailsAttrResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性值 M")
    private String value;

    @ApiModelProperty(value = "展示内容 M")
    private String valueMark;

}
