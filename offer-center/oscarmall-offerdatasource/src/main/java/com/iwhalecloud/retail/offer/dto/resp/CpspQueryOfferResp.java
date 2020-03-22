package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fanxiaofei
 * @date 2019-07-30
 */
@Data
public class CpspQueryOfferResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品标识 M")
    private String offerId;

    @ApiModelProperty(value = "商品名称 M")
    private String offerName;

    @ApiModelProperty(value = "商品编码 O")
    private String offerCode;

    @ApiModelProperty(value = "商品副标题 O")
    private String desc;

    @ApiModelProperty(value = "商品副标题 O desc为sql关键字")
    private String simpleName;

    @ApiModelProperty(value = "类型 M")
    private String offerType;

    @ApiModelProperty(value = "类型名称 M")
    private String offerTypeName;

    @ApiModelProperty(value = "产品编码 M")
    private String productCode;

    @ApiModelProperty(value="销售价格")
    private Long price;

    @ApiModelProperty(value="市场价格")
    private Long mktprice;

    @ApiModelProperty(value="商品内容信息 O")
    private List<CpspOfferContentResp> contentList;






}
