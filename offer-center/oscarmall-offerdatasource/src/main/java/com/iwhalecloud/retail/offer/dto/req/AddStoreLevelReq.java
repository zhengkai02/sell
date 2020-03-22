package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺等级
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
@Data
public class AddStoreLevelReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺等级id")
    private String storeLevelId;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "说明")
    private String comments;

    @ApiModelProperty(value = "状态 A:正常 X:失效")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建用户ID")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;
}
