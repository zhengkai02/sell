package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddAuditChkhis implements Serializable {

    private static final long serialVersionUID = 8466906273072130729L;

    @ApiModelProperty(value = "对象类型 A商品 B内容 C优惠券")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "审核结果 1 通过 0 不通过")
    private Long result;

    @ApiModelProperty(value = "审核描述")
    private String comments;

    @ApiModelProperty(value = "创建人")
    private String commitStaffId;

    @ApiModelProperty(value = "tenantId")
    private String tenantId;
}
