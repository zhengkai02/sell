package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺适用规则(域)
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
@Data
public class QueryStoreRuleResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则标识")
    private Integer ruleId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String comments;

    @ApiModelProperty(value = "适用对象标识")
    private List<String> objIds;

}
