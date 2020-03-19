package indi.zk.mall.order.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:28
 */
@Getter
public enum PayStatusEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功");

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
