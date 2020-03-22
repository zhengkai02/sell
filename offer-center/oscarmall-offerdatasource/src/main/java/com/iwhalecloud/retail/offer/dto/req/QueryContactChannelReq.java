package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接触渠道(域)
 * 
 * @author fanxiaofei
 * @date 2019-07-01
 */
@Data
public class QueryContactChannelReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道id")
    private String contactChannelId;

}
