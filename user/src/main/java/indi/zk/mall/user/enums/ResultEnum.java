package indi.zk.mall.user.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-09-18 00:13
 */
@Getter
public enum ResultEnum {

    LOGIN_FAIL(1, "登录失败"),
    PRODUCT_STAOCK_ERROR(2, "角色权限有误");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
