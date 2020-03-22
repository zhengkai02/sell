package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询目标商品信息入参
 * @author fanxiaofei
 * @date 2019-03-22
 */
@Data
public class ProdGoodsQueryTargetReq extends AbstractPageReq {


    @ApiModelProperty(value = "商品ID列表")
    private List<String> ids;

    @ApiModelProperty(value = "商品名称或者商品编码")
    private String qt;

    @ApiModelProperty(value = "商品关系类型 1:互斥关系")
    private String relType;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

}

