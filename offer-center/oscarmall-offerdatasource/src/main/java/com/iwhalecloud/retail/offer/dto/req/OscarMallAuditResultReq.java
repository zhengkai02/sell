package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 提交审批MQ
 * 
 * @author fanxiaofei
 * @date 2019-07-10
 */
@Data
public class OscarMallAuditResultReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "A则该值为商品id B则该值为文章id C则该值为优惠券id")
    private String businessId;

    @ApiModelProperty(value = "1：审批成功 0: 审批失败")
    private String result;

    @ApiModelProperty(value = "成功或审批失败原因 非必传")
    private String message;

}
