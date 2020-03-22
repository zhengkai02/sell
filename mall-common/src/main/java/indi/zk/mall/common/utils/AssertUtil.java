/**
 * Copyright 2010 ZTEsoft Inc. All Rights Reserved.
 * <p>
 * This software is the proprietary information of ZTEsoft Inc.
 * Use is subject to license terms.
 * <p>
 * $Tracker List
 * <p>
 * $TaskId: $ $Date: 9:24:36 AM (May 9, 2008) $comments: create
 * $TaskId: $ $Date: 3:56:36 PM (SEP 13, 2010) $comments: upgrade jvm to jvm1.5
 */
package com.iwhalecloud.retail.common.utils;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import com.iwhalecloud.retail.common.consts.BaseMessageIntf;
import com.iwhalecloud.retail.common.exception.BaseException;

/**
 * <Description> <br>
 *
 * @author chen.gehui <br>
 * @version 1.0 <br>
 * @date Mar 24, 2018 <br>
 * @since V8.0<br>
 */
public interface AssertUtil {

    /**
     * Description: 判断参数是否为空<br>
     *
     * @param <T> <br>
     * @param obj <br>
     * @param msg String
     * @author hu.minghang<br>
     */
    static <T> void isNotNull(T obj, String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    static <T> void isNotNull(T obj, BaseMessageIntf exception) throws BaseException {
        if (obj == null) {
            throw new BaseException(exception.getStatus(), exception.getMessage());
        }
    }

    static <T> void isNotNull(T obj, String code, String msg) throws BaseException {
        if (obj == null) {
            throw new BaseException(code, msg);
        }
    }


    static <T extends CharSequence> T isNotEmpty(T chars, String message) {
        if (chars == null || chars.length() == 0) {
            throw new IllegalArgumentException(message);
        }
        else {
            return chars;
        }
    }

    static <T extends Collection> void isNotEmpty(T collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    static <T extends CharSequence> T isNotEmpty(T chars, BaseMessageIntf exception) throws BaseException {
        if (chars == null || chars.length() == 0) {
            throw new BaseException(exception.getStatus(), exception.getMessage());
        }
        else {
            return chars;
        }
    }

    static <T extends Collection> void isNotEmpty(T collection, BaseMessageIntf exception) throws BaseException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseException(exception.getStatus(), exception.getMessage());
        }
    }

}
