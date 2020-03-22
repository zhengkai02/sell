package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询商品内容信息入参
 * @author huminghang
 * @date 2019-03-22
 */
@Data
public class ProdGoodsQueryContentReq {

    @ApiModelProperty(value = "商品内容类型 1:轮播图")
    private Long goodsContentType;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;


}

