package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品适用规则
 * 
 * @author fanxiaofei
 * @date 2019-03-11
 */
@Data
public class UpdateProdGoodsSalesConditionReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售规则标识
     */
    @ApiModelProperty(value = "销售规则标识")
    private Integer salesRuleId;

    /**
     * 销售规则对象标识
     */
    @ApiModelProperty(value = "销售规则对象标识")
    private List<String> objIds;

}
