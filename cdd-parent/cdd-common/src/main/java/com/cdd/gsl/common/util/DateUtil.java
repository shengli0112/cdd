package com.cdd.gsl.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 获取几天后的日期
     * @param date
     * @param day
     * @return
     */
    public static String getSomeDay(Date date, int day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.DATE, day);

         return sdf.format(calendar.getTime());
    }
}
