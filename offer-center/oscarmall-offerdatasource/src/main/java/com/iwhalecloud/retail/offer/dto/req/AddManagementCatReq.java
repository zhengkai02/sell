package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 租户
 * 
 * @author fanxiaofei
 * @date 2019-05-22
 */
@Data
public class AddManagementCatReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "目录id集合")
    private List<String> catIdList;

    @ApiModelProperty(value = "创建用户ID")
    private String modifyBy;
}
