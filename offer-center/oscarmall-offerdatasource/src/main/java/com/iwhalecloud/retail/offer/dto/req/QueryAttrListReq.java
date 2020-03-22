package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryAttrListReq implements Serializable {

    private static final long serialVersionUID = 4722663225369273393L;

    @ApiModelProperty(value = "属性ID")
    private List<String> attrIds;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;
}
