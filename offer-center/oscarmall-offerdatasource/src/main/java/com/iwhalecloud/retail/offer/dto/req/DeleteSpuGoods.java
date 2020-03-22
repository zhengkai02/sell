package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SPU关联商品
 * 
 * @author fanxiaofei
 * @date 2019-05-26
 */
@Data
public class DeleteSpuGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku标识")
    private String skuId;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;

}
