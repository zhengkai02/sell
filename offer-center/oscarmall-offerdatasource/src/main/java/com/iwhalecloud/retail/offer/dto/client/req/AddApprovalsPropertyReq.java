package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 提交审批 属性名
 * 
 * @author fanxiaofei
 * @date 2019-06-26
 */
@Data
public class AddApprovalsPropertyReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名")
    private String property;

    @ApiModelProperty(value = "旧值 非必填")
    private String oldValue;

    @ApiModelProperty(value = "新值")
    private String newValue;
}
