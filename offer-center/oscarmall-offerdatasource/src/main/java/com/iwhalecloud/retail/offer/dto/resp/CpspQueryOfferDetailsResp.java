package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fanxiaofei
 * @date 2019-07-30
 */
@Data
public class CpspQueryOfferDetailsResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品标识 M")
    private String offerId;

    @ApiModelProperty(value = "商品名称 M")
    private String offerName;

    @ApiModelProperty(value = "商品编码 O")
    private String offerCode;

    @ApiModelProperty(value = "副标题 O")
    private String simpleName;

    @ApiModelProperty(value = "品牌标识 O")
    private String brandId;

    @ApiModelProperty(value = "品牌名称 O")
    private String brandName;

    @ApiModelProperty(value = "状态 A:新建 B:待审批 C:已上架 D:审批不通过 E:已下架 F:审批通过 X:失效 M")
    private String state;

    @ApiModelProperty(value = "市场价（划线价） O")
    private Long mktPrice;

    @ApiModelProperty(value = "售价 M")
    private Long price;

    @ApiModelProperty(value = "属性列表 M")
    private List<CpspQueryOfferDetailsAttrListResp> attrList;

    @ApiModelProperty(value="商品内容信息 O")
    private List<CpspOfferContentResp> contentList;;

}
