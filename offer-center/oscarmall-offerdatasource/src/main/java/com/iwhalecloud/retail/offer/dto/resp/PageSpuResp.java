package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SPU分页
 * 
 * @author fanxiaofei
 * @date 2019-05-26
 */
@Data
public class PageSpuResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu标识")
    private String spuId;

    @ApiModelProperty(value = "spu名称")
    private String spuName;

    @ApiModelProperty(value = "sku属性列表")
    private String skuAttrIds;

    @ApiModelProperty(value = "sku属性列表名称")
    private String skuAttrIdName;

    @ApiModelProperty(value = "描述")
    private String comments;

}
