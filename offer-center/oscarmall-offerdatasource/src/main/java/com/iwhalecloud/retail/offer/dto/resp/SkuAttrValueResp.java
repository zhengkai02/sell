package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author fanxiaofei
 * @date 2019/05/27
 */
@Data
public class SkuAttrValueResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性id")
    private String attrId;

    @ApiModelProperty(value = "属性值")
    private String attrValue;

}

