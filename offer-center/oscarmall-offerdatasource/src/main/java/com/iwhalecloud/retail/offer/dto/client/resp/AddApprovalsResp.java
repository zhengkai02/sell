package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 提交审批
 * 
 * @author fanxiaofei
 * @date 2019-06-26
 */
@Data
public class AddApprovalsResp implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否审批 0-否 1-是")
    private Integer isApproval;

    @ApiModelProperty(value = "审批id IsApproval=1时，回传")

    private String approvalId;

}
