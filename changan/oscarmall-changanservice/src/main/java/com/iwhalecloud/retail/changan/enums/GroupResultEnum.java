package com.iwhalecloud.retail.changan.enums;

import lombok.Getter;

/**
 * @author ZhengKai
 * @data 2019-11-19 10:43
 */
@Getter
public enum GroupResultEnum {

    GROUP_NOT_EXIST(0, "产品分类不存在"),
    GROUP_CODE_NOT_NULL(1, "产品分类编码编码不能为空"),
    GROUP_NAME_NOT_NULL(2, "产品分类名不能为空"),
    GROUP_SYNC_FINESHED(4, "状态为已同步状态");

    private Integer code;
    private String message;

    GroupResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
