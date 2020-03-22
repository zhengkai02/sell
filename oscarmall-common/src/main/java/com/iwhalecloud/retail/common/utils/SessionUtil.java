package com.iwhalecloud.retail.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by anonymous on 2019/3/29.
 */
public final class SessionUtil {
    private SessionUtil() {

    }

    public static String getSessionId(HttpServletRequest request) {
        String sessionId = "";
        HttpSession session = request.getSession();
        if (session != null) {
            sessionId = session.getId();
        }
        return sessionId;
    }
}
