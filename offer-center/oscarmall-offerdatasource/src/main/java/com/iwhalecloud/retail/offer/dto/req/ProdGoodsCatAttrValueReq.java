package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品属性值
 * 
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
public class ProdGoodsCatAttrValueReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 目录属性id
     */
    @ApiModelProperty(value = "目录属性id")
    private String catAttrValueId;

    /**
     * 目录id
     */
    @ApiModelProperty(value = "目录id")
    private String catId;

    /**
     * 属性id
     */
    @ApiModelProperty(value = "属性id")
    private String attrId;

    /**
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String attrValue;

    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private Long priority;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String modifyBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    /**
     * tenantId
     */
    @ApiModelProperty(value = "tenantId")
    private String tenantId;

    /**
     * 继承属性标识
     */
    @ApiModelProperty(value = "继承属性标识")
    private String inheritedFlag;
}
