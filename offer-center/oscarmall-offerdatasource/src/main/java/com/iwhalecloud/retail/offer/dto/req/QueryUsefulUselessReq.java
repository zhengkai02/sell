package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 赞踩
 * 
 * @author fanxiaofei
 * @date 2019-05-07
 */
@Data
public class QueryUsefulUselessReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价对象 C 内容  E 评价")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "评价用户ID")
    private String userId;

    @ApiModelProperty(value = "1 赞  2踩")
    private String type;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

}
