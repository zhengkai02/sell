package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.exception.BaseException;

@FunctionalInterface
public interface CustomNoParamFunction<R> {
    R apply() throws BaseException;
}
