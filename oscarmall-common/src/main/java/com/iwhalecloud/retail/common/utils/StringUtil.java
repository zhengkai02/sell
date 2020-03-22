package com.iwhalecloud.retail.common.utils;

import org.springframework.util.StringUtils;

/**
 * Created by anonymous on 2019/3/29.
 */
public final class StringUtil {
    private StringUtil() {

    }

    public static String adjustStringValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }
}
