package com.iwhalecloud.retail.changan.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-11-19 09:28
 */
@Getter
public enum AppResultEnum {

    APP_NOT_EXIST(0,"应用不存在"),
    APP_CODE_NOT_NULL(1,"应用编码不能为空"),
    APP_NAME_NOT_NULL(2,"应用名不能为空"),
    APP_BRANDID_NOT_NULL(3,"应用brandId不能为空"),
    APP_SYNC_FINESHED(4,"状态为已同步状态");

    private Integer code;
    private String message;

    AppResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
