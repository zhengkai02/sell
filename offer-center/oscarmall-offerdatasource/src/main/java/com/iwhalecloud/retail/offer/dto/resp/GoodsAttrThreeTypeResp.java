package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 根据商品id取三种属性
 * @author fanxiaofei
 * @date 2019/06/04
 */
@Data
public class GoodsAttrThreeTypeResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku属性")
    private List<AttrValueResp> skuGoodsAttr;

    @ApiModelProperty(value = "私有属性")
    private List<AttrValueResp> privateGoodsAttr;

    @ApiModelProperty(value = "非sku非私有属性")
    private List<AttrValueResp> threeGoodsAttr;

}

