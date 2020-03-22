package com.iwhalecloud.retail.changan.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-11-19 11:03
 */
@Getter
public enum ProductEnum {

    PRODUCT_NOT_EXIST(0,"产品不存在"),
    PRODUCT_NAME_NOT_NULL(1,"产品名称不能为空");

    private Integer code;
    private String message;

    ProductEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }


}
