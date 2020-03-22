package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺适用规则实例
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
@Data
public class QueryStoreRuleInstResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则实例id")
    private String ruleInstId;

    @ApiModelProperty(value = "店铺标识")
    private String storeId;

    @ApiModelProperty(value = "适用规则标识")
    private Integer ruleId;

    @ApiModelProperty(value = "适用对象标识")
    private String objId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String comments;

}
