package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryProductObjResp implements Serializable {

    private static final long serialVersionUID = -5426160287470574399L;

    @ApiModelProperty(value = "产品列表")
    private List<ProductObj> productList;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页")
    private Integer pageNo;

    @ApiModelProperty(value = "总条数")
    private Integer totalCount;

    @ApiModelProperty(value = "总页数")
    private Integer totalPage;
}
