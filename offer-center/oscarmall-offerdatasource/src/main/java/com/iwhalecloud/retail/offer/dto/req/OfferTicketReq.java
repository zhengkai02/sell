package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询服务卷列表入参
 * 
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class OfferTicketReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源id")
    private String resId;

    @ApiModelProperty(value = "ticketId")
    private String resCode;

    @ApiModelProperty(value = "优惠券状态，不传查询所有")
    private String state;

    @ApiModelProperty(value = "记录数，默认10")
    private Integer rows;

    @ApiModelProperty(value = "开始位置默认 0")
    private Integer offset;

}
