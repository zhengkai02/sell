package com.iwhalecloud.retail.offer.utils;

import com.iwhalecloud.retail.common.utils.SpringContext;
import com.iwhalecloud.retail.offer.mapper.UtilsMapper;

import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/6 <br>
 * @see com.iwhalecloud.retail.offer.utils <br>
 * @since V9.0C<br>
 */
public abstract class DBDateUtil {

    private DBDateUtil() {
    }

    private static UtilsMapper utilsMapper = SpringContext.getBean(UtilsMapper.class);

    public static Date getCurrentDBDateTime() {
        return utilsMapper.getCurrentDate();
    }
}
