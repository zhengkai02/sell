package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价计算日志
 * 
 * @author fanxiaofei
 * @date 2019-05-07
 */
@Data
@TableName("tbl_evaluation_log")
public class EvaluationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "评价计算日志ID")
    private Long logId;

    @ApiModelProperty(value = "评价对象 A 商品, B 内容")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;

    @ApiModelProperty(value = "日志时间")
    private Date logDate;

    @ApiModelProperty(value = "上一次计算的评分")
    private Integer preRate;

    @ApiModelProperty(value = "上一次计算时间")
    private Date preRateDate;

    @ApiModelProperty(value = "上一次计算总用户数")
    private Integer preUserCount;

    @ApiModelProperty(value = "计算后的评价")
    private Integer afterRate;

    @ApiModelProperty(value = "计算后总评价用户数")
    private Integer afterUserCount;

    @ApiModelProperty(value = "本时间段内评分")
    private Integer rate;

    @ApiModelProperty(value = "本时间段内评价用户数")
    private Integer userCount;

    @ApiModelProperty(value = "上次计算评分的日志ID")
    private Long preLogId;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}