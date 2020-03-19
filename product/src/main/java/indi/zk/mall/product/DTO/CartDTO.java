package indi.zk.mall.product.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhengKai
 * @data 2019-09-18 00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "购物车数据传输对象")
public class CartDTO {

    /**
     * 商品ID.
     */
    @ApiModelProperty(value = "商品ID")
    private String productId;

    /**
     * 商品数量.
     */
    @ApiModelProperty(value = "商品数量")
    private Integer productQuantity;

}
