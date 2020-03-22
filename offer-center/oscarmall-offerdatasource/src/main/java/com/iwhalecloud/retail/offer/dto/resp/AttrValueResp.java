package com.iwhalecloud.retail.offer.dto.resp;

import com.iwhalecloud.retail.offer.entity.AttrValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情封装的attrValue
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Data
public class AttrValueResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性id")
    private String attrId;

    @ApiModelProperty(value = "属性名称")
    private String attrName;

    @ApiModelProperty(value = "属性编码")
    private String attrCode;

    @ApiModelProperty(value = "属性类型")
    private String attrType;

    @ApiModelProperty(value = "属性值展示")
    private String valueMark;

    @ApiModelProperty(value = "属性值")
    private String attrValue;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "输入类型")
    private Integer inputType;

    @ApiModelProperty(value = "是否为空 Y:可空 N:非空")
    private String nullable;

    @ApiModelProperty(value = "是否继承属性")
    private String inheritedFlag;

    @ApiModelProperty(value = "私有属性类型")
    private String privateType;

    @ApiModelProperty(value = "私有属性对象Id")
    private String privateObjId;

    @ApiModelProperty(value = "属性值 attr_value表对应")
    private List<AttrValue> attrValueList;


}

