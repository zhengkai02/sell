package com.iwhalecloud.retail.changan.dto.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 属性值
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
public class AttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性值id
     */
    @ApiModelProperty(value = "属性值id")
    private String attrValueId;

    /**
     * 属性id
     */
    @ApiModelProperty(value = "属性id")
    private String attrId;

    /**
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String value;

    /**
     * 属性标志
     */
    @ApiModelProperty(value = "属性标志")
    private String valueMark;

}
