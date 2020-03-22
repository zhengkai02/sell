package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.iwhalecloud.retail.offer.entity.AttrValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品管理目录封装的attr
 * @author huminghang
 * @date 2019/05/26
 */
@Data
public class GoodsCatAttrResp implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "属性id")
    private String attrId;

    @ApiModelProperty(value = "属性名称")
    private String attrName;

    @ApiModelProperty(value = "属性编码")
    private String attrCode;

    @ApiModelProperty(value = "属性值")
    private String attrValue;

    @ApiModelProperty(value = "属性类型 A:规格属性 B:实例化属性 C:SKU属性")
    private String attrType;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "录入类型")
    private Integer inputType;

    @ApiModelProperty(value = "Y:可空 N:非空")
    private String nullable;

    @ApiModelProperty(value = "Y:系统预置 N:非系统预置")
    private String isReserved;

    @ApiModelProperty(value = "说明")
    private String comments;

    @ApiModelProperty(value = "私有属性类型")
    private String privateType;

    @ApiModelProperty(value = "私有属性对象Id")
    private String privateObjId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建用户标识")
    private String createBy;

    @ApiModelProperty(value = "更新日期")
    private Date modifyTime;

    @ApiModelProperty(value = "更新用户标识")
    private String modifyBy;

    @ApiModelProperty(value = "状态: A可用 X失效")
    private String state;

    @ApiModelProperty(value = "状态日期")
    private Date stateDate;

    @ApiModelProperty(value = "是否是继承属性，用于标识销售目录继承关联的管理目录的属性")
    private String inheritedFlag;

    @ApiModelProperty(value = "属性值")
    private List<AttrValue> attrValueList;

}

