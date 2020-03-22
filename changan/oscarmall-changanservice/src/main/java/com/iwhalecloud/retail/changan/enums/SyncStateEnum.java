package com.iwhalecloud.retail.changan.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-11-19 10:15
 */
@Getter
public enum SyncStateEnum {

    ADD_NOT_SYNC("A", "新增未同步"),
    UPDATE_NOT_SYNC("U", "修改未同步"),
    SYNCED("Y","已同步");
    private String code;
    private String message;

    SyncStateEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}