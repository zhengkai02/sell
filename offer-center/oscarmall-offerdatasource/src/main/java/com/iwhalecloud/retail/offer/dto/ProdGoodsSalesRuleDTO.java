package com.iwhalecloud.retail.offer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品销售规则
 *
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
@ApiModel(value = "对应模型prod_goods_sales_rule, 对应实体ProdGoodsSalesRule类")
public class ProdGoodsSalesRuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 商品销售规则id
     */
    @ApiModelProperty(value = "商品销售规则id")
    private Integer salesRuleId;

    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    private String name;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String comments;

}
