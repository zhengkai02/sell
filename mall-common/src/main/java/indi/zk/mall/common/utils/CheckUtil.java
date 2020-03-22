package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.consts.BaseMessageIntf;
import com.iwhalecloud.retail.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by xh on 2019/7/26.
 */
public interface CheckUtil {
    static void checkParamLength(String value, int len, BaseMessageIntf exception) throws BaseException {
        if (StringUtils.isNotEmpty(value) && value.length() != len) {
            throw new BaseException(exception.getStatus(), exception.getMessage());
        }
    }
}
