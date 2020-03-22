package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户
 * 
 * @author fanxiaofei
 * @date 2019-05-22
 */
@Data
public class TenantDetailResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户标识")
    private String tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;
}
