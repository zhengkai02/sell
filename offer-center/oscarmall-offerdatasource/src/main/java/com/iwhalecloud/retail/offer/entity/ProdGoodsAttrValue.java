package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品属性值
 * 
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
@TableName("tbl_prod_goods_attr_value")
public class ProdGoodsAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long goodsAttrId;

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

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
