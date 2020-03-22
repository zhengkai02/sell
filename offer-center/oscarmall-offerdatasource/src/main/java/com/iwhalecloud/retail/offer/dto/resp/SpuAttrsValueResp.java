package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fanxiaofei
 * @date 2019-06-10
 */
@Data
public class SpuAttrsValueResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "显示值")
    private String valueMark;

}
