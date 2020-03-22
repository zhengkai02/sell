package com.iwhalecloud.retail.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <Description> <br>
 *
 * @author zhao.zhengwei162 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/14 <br>
 * @see com.iwhalecloud.retail.common.utils <br>
 * @since R9.0<br>
 */
public abstract class NumberFormatUtil {

    private NumberFormatUtil() {
    }

    private static final int HUN = 100;

    /**
     * 默认取整，前台格式化
     *
     * @param value 计算金额100表示100分即1元
     * @return 格式化取整结果
     */
    public static String format(Double value) {
        DecimalFormat format = new DecimalFormat("#");
        // 四舍五入
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(value);
    }

    public static String showCurrency(Long value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return  df.format((float) value / HUN);
    }


    /**
     * 向上取整
     * @param dividend 被除数
     * @param divisor 除数
     * @return int
     */
    public static int getCeilNum(Integer dividend, Integer divisor) {
        //格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        //返回的是String类型
        String num = df.format((float) dividend * HUN / divisor);
        return (int) Math.ceil(Double.valueOf(num));
    }

}