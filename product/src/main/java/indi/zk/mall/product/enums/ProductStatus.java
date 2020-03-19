package indi.zk.mall.product.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-09-15 11:47
 */
@Getter
public enum ProductStatus {
    UP(0, "在架"),
    DOWN(1, "下架");

    Integer code;
    String message;

    ProductStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
