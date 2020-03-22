package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 赞踩
 * 
 * @author fanxiaofei
 * @date 2019-05-07
 */
@Data
@TableName("tbl_useful_useless")
public class UsefulUseless implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long usefulUselessId;

    @ApiModelProperty(value = "评价对象 C 内容  E 评价")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "评价用户ID")
    private Long userId;

    @ApiModelProperty(value = "类型 1 赞 2 踩")
    private String type;

    @ApiModelProperty(value = "状态 A 有效  X 无效")
    private String state;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String tenantId;

    private String createBy;

    private String modifyBy;

    private Date modifyTime;

}
