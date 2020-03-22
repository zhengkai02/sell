package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QryStoreReq implements Serializable {

    private static final long serialVersionUID = -5934069889892062403L;

    @ApiModelProperty(value = "店铺ID")
    private List<String> storeIds;

    /**
     * 可选参数，如果需要带入查询，再代入
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    private List<String> states;

}
