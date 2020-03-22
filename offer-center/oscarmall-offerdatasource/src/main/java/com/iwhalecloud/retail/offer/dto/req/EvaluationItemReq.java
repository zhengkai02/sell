package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by xh on 2019/6/25.
 */
@Data
public class EvaluationItemReq implements Serializable {

    @ApiModelProperty(value = "评价对象")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "评价描述")
    private String evaluationComments;

    @ApiModelProperty(value = "评分")
    private String rate;

    @ApiModelProperty(value = "是否匿名评价")
    private String anonymous;
}
