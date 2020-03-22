package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.dto.UserDTO;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/18 <br>
 * @see com.iwhalecloud.retail.mobile.utils <br>
 * @since IOT <br>
 */
public abstract class HttpSessionUtil {
    private HttpSessionUtil() {
    }

    private static final ThreadLocal<UserDTO> LOCAL = new ThreadLocal<>();

    public static void set(UserDTO tmpUserDTO) {
        LOCAL.set(tmpUserDTO);
    }

    public static UserDTO get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
