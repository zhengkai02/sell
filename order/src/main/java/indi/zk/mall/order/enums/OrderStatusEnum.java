package indi.zk.mall.order.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:19
 */
@Getter
public enum OrderStatusEnum {

    NEW(0, "新订单"),
    FINISHED(1, "完成"),
    CANCEL(2, "取消");

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
