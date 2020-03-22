package indi.zk.mall.product.exception;

import indi.zk.mall.product.enums.ResultEnum;

/**
 * @author ZhengKai
 * @data 2019-09-18 00:11
 */
public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
