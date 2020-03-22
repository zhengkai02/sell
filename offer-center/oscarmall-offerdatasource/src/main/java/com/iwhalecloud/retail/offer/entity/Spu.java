package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * SPU
 * 
 * @author fanxiaofei
 * @date 2019-05-24
 */
@Data
@TableName("tbl_spu")
public class Spu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "spu标识")
    private String spuId;

    @ApiModelProperty(value = "spu名称")
    private String spuName;

    @ApiModelProperty(value = "sku属性列表")
    private String skuAttrIds;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "状态 A:正常 X:失效")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "创建用户ID")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "租户")
    private String tenantId;
}
