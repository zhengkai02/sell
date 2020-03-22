package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 *
 * @author fanxiaofei
 * @date 2019-07-22
 */
@Data
public class QueryEvaluationByUserIdAndObjReq implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "评价对象")
    private String objType;

    @ApiModelProperty(value = "评价对象id")
    private String objId;

    @ApiModelProperty(value = "用户id")
    private String userId;

}
