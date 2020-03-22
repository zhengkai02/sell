package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品销售规则
 * @author fanxiaofei
 * @date 2019-03-01
 */
@Data
public class ProdGoodsSalesRuleResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "销售规则id")
    private Integer salesRuleId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String comments;

    @ApiModelProperty(value = "对象集合")
    private List<String> objIdList;

}
