package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品销售规则
 * 
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
public class ProdGoodsSalesRuleResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品销售规则id")
    private Integer salesRuleId;

    @ApiModelProperty(value = "规则名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String comments;

    @ApiModelProperty(value = "商品适用规则")
    private List<String> objIdList;

}
