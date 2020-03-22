package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fanxiaofei
 * @date 2019-07-30
 */
@Data
public class CpspQueryOfferDetailsAttrListResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性标识 M")
    private String attrId;

    @ApiModelProperty(value = "属性编码 O")
    private String attrCode;

    @ApiModelProperty(value = "属性名称 M")
    private String attrName;

    @ApiModelProperty(value = "属性类型 A:规格属性 B:实例化属性 C:SKU属性 M")
    private String attrType;

    @ApiModelProperty(value = "录入类型 1:文本 2:单选 3:多选 M")
    private String inputType;

    @ApiModelProperty(value = "是否可为空 M")
    private String nullAble;

    @ApiModelProperty(value = "说明 O")
    private String comments;

    @ApiModelProperty(value = "属性值 O")
    private String value;

    @ApiModelProperty(value = "属性可选值列表 O")
    private List<CpspQueryOfferDetailsAttrResp> attrValue;

}
