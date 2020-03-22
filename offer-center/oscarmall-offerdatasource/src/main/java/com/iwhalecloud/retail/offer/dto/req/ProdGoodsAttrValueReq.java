package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性值
 * 
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
public class ProdGoodsAttrValueReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品属性id")
    private String goodsAttrId;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "属性id")
    private String attrId;

    @ApiModelProperty(value = "属性值")
    private String attrValue;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "是否继承属性")
    private String inheritedFlag;

    @ApiModelProperty(value = "tenantId")
    private String tenantId;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

}
