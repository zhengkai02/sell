package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询用户权益入参
 * 
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class RightsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一请求号,保证每次请求唯一")
    private String requestId;

    @ApiModelProperty(value = "用户id")
    private String userId;
}
