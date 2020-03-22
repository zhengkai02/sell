package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品管理目录封装的attr
 * @author huminghang
 * @date 2019/05/26
 */
@Data
public class GoodsCatAttrResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
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
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String attrValue;

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
     * 系统预置
     */
    @ApiModelProperty(value = "Y:系统预置 N:非系统预置")
    private String isReserved;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String comments;

    /**
     * 私有属性类型
     */
    @ApiModelProperty(value = "私有属性类型")
    private String privateType;

    /**
     * 私有属性对象Id
     */
    @ApiModelProperty(value = "私有属性对象Id")
    private String privateObjId;

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

    @ApiModelProperty(value = "是否是继承属性，用于标识销售目录继承关联的管理目录的属性")
    private String inheritedFlag;

    @ApiModelProperty(value = "属性值")
    private List<AttrValue> attrValueList;

}

