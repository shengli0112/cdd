package com.cdd.gsl.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static String getDate(int days){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,days);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getMonth(int months){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, months);
        Date m = c.getTime();
        String mon = format.format(m);
        System.out.println("过去一个月："+mon);
        return mon;
    }

    public static String getMonthStart(int months){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, months);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String monthStart = format.format(c.getTime())+" 00:00:00";
        System.out.println(monthStart);
        return monthStart;
    }

    public static String getMonthEnd(int months){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, months);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String monthEnd = format.format(ca.getTime())+" 23:59:59";
        System.out.println(monthEnd);
        return monthEnd;
    }

    public static void main(String[] args)throws Exception{
       System.out.println(getMonthStart(0));
    }
}
