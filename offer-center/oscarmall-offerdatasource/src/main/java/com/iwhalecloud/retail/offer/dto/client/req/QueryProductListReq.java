package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品列表入参 调用亚信接口 M:必传 0:选传
 * 
 * @author fanxiaofei
 * @date 2019-03-13
 */
@Data
public class QueryProductListReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一请求号 M")
    private Long requestId;

    @ApiModelProperty(value = "每页显示条数 M")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页 M")
    private Integer pageNo;

    @ApiModelProperty(value = "渠道编码 00001:TSOP商城 M")
    private String channel;

    @ApiModelProperty(value = "产品名称 O")
    private String productName;

    @ApiModelProperty(value = "产品编码 O")
    private String productCode;

    @ApiModelProperty(value = "机构id（店铺机构id）")
    private String orgId;

}
