package indi.zk.mall.user.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2020-03-22 01:13
 */
@Getter
public enum RoleEnum {

    BUYER(1,"买家"),
    SELLER(2, "卖家")
    ;

    private Integer code;
    private String message;

    RoleEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
