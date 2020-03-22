package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QryProdGoodsListReq implements Serializable {

    private static final long serialVersionUID = -7775386870606268624L;

    /**
     *  商品id列表
     */
    @ApiModelProperty(value = "商品id列表")
    private List<String> goodsIds;
}
