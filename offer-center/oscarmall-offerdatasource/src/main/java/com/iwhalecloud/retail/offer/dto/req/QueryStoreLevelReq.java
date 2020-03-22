package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺等级
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
@Data
public class QueryStoreLevelReq extends AbstractPageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺等级名称")
    private String levelName;

}
