package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 属性
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
@TableName("tbl_attr")
public class Attr implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 属性id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "属性id")
    private String attrId;

    /**
     * 属性名称
     */
    @ApiModelProperty(value = "属性名称")
    private String attrName;

    /**
     * 属性编码
     */
    @ApiModelProperty(value = "属性编码")
    private String attrCode;

    /**
     * 属性类型
     */
    @ApiModelProperty(value = "属性类型 A:规格属性 B:实例化属性 C:SKU属性")
    private String attrType;

    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private Integer priority;

    /**
     * 录入类型
     */
    @ApiModelProperty(value = "录入类型")
    private Integer inputType;

    /**
     * 是否为空
     */
    @ApiModelProperty(value = "Y:可空 N:非空")
    private String nullable;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String comments;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建用户标识
     */
    @ApiModelProperty(value = "创建用户标识")
    private String createBy;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    private Date modifyTime;

    /**
     * 更新用户标识
     */
    @ApiModelProperty(value = "更新用户标识")
    private String modifyBy;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态: A可用 X失效")
    private String state;

    /**
     * 状态日期
     */
    @ApiModelProperty(value = "状态日期")
    private Date stateDate;

    /**
     * 系统预置
     */
    @ApiModelProperty(value = "Y:系统预置 N:非系统预置")
    private String isReserved;

    /**
     * 私有属性类型
     */
    @ApiModelProperty(value = "私有属性类型")
    private String privateType;

    /**
     * 私有属性对象
     */
    @ApiModelProperty(value = "私有属性对象")
    private String privateObjId;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;


}
