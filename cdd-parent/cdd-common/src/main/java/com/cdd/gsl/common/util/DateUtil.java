package com.cdd.gsl.common.util;

import java.util.Date;

public class DateUtil {
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
}