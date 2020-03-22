package com.iwhalecloud.retail.offer.dto.client.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 产品列表数据
 * 调用亚信接口
 *
 * @author fanxiaofei
 * @date 2019-03-13
 */
@Data
public class QueryProductListResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品列表")
    private List<ProductList> productList;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页")
    private Integer pageNo;

    @ApiModelProperty(value = "总条数")
    private Integer totalCount;

    @ApiModelProperty(value = "总页数")
    private Integer totalPage;

}
