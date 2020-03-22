package com.iwhalecloud.retail.common.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/4/1 <br>
 * @see com.iwhalecloud.retail.operation.dto <br>
 * @since IOT <br>
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 5772528606194116933L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户编码")
    private String userCode;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "机构id")
    private String orgId;

    @ApiModelProperty(value = "角色")
    private String roles;

    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "是否是平台用户")
    private Boolean isPlatformUser;

    @ApiModelProperty(value = "渠道类型")
    private Long channelType;
}
