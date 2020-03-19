package indi.zk.mall.product.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-09-18 00:13
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXSIT(1, "商品不存在"),
    PRODUCT_STAOCK_ERROR(2, "存库不足");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
