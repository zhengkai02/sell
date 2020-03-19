package indi.zk.mall.order.exception;

import indi.zk.mall.order.enums.ResultEnum;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:34
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
