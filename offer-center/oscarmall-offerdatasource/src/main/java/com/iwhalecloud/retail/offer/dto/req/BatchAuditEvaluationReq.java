package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量审核
 * 
 * @author fanxiaofei
 * @date 2019-06-01
 */
@Data
public class BatchAuditEvaluationReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "审核状态 通过:A 不通过:D")
    private String state;

    @ApiModelProperty(value = "评价ID集合")
    private List<String> ids;

    @ApiModelProperty(value = "更新人")
    private String modifyBy;

}
