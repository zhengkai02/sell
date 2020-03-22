package indi.zk.mall.user.utils;

import java.util.Random;

/**
 * @author ZhengKai
 * @data 2019-09-15 14:42
 */
public class KeyUtil {
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
