package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.exception.BaseException;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/14 <br>
 * @see com.iwhalecloud.retail.common.utils <br>
 * @since V9.0C<br>
 */
@FunctionalInterface
public interface CustomNoReturnFunction<T>  {

    void apply(T t) throws BaseException;
}
