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
public class UsefulUselessReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价对象 C 内容  E 评价")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "评价用户ID")
    private Long userId;

    @ApiModelProperty(value = "类型 1 赞 2 踩")
    private String type;

    @ApiModelProperty(value = "A 新增 D 删除")
    private String action;

}
