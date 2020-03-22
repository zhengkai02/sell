package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 提交审批
 * 
 * @author fanxiaofei
 * @date 2019-06-26
 */
@Data
public class AddApprovalsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务id")
    private String businessId;

    @ApiModelProperty(value = "业务类型")
    private Integer businessType;

    @ApiModelProperty(value = "店铺id（机构id） 商品提交审批时带店铺id 非必填")
    private String orgId;

    @ApiModelProperty(value = "记录类型：1新增，2修改，3删除，4投放，5启用，6停用，7出库，8入库")
    private String approvalType;

    @ApiModelProperty(value = "审批不通过时，需要重新提交，跳转至对应提交审批信息的页面的url")
    private String approvalUrl;

    @ApiModelProperty(value = "提交审批人姓名")
    private String userName;

    @ApiModelProperty(value = "修改内容")
    private List<AddApprovalsPropertyReq> changes;

}
