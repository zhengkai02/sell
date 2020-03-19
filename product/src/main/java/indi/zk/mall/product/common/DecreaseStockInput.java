package indi.zk.mall.product.common;

import lombok.Data;

/**
 * @author ZhengKai
 * @data 2019-09-15 16:19
 */
@Data
public class DecreaseStockInput {

    private String productId;

    private Integer productQuantity;

    public DecreaseStockInput() {
    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
