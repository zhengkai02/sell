package indi.zk.mall.order.utils;

import org.apache.tomcat.jni.Local;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ZhengKai
 * @data 2019-10-27 14:42
 */
public class DateUtil {
    public static void main(String[] args) {

        /**
         * 日期操作，线程不安全
         */

//        Calendar cal =Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        cal.add(Calendar.DATE,1);
//        Date day = cal.getTime();
//        System.out.println(sdf.format(day));

        /**
         * LocalDateTime日期操作，线程安全
         */
//        LocalDate localDate = LocalDate.now();
//        DayOfWeek day = localDate.getDayOfWeek();
//        LocalTime localTime = LocalTime.now();
//
//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime);

        /**
         * 获取当前时间戳的三种方式
         */
        System.currentTimeMillis();
        Calendar.getInstance().getTimeInMillis();
        new Date().getTime();
    }
}
