package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品内容
 * @author fanxiaofei
 * @date 2019-06-05
 */
@Data
public class ProdGoodsContentReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品内容类型 1 商品轮播图 2 商品详情描述 3 售后政策")
    private Long goodsContentType;

    @ApiModelProperty(value = "设备类型 1 手机 2 车机横屏  3 车机竖屏")
    private String deviceType;

    @ApiModelProperty(value = "内容")
    private ContentAddReq contentAdd;
}
