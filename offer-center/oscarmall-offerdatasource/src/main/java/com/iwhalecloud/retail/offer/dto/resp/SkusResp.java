package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author fanxiaofei
 * @date 2019-06-10
 */
@Data
public class SkusResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku 的商品ID")
    private String offerId;

    @ApiModelProperty(value = "商品名称")
    private String offerName;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "状态 A 可以销售 B 不可销售 C 下架")
    private String state;

    @ApiModelProperty(value = "属性值")
    private List<SkuAttrValueResp> attrValue;

}
