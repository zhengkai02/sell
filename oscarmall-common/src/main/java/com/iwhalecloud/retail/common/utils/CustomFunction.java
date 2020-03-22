package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.exception.BaseException;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/19 <br>
 * @see com.iwhalecloud.retail.common.utils <br>
 * @since IOT <br>
 */
@FunctionalInterface
public interface CustomFunction<T, R> {
    R apply(T t) throws BaseException;
}
