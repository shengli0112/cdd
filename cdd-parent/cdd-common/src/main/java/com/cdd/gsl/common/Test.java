package com.cdd.gsl.common;

import com.cdd.gsl.common.util.DateUtil;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String url = "http://gz.ooooo.org.cn/52/CN/mb/ed300C/index.html?plan_id=933578&keyword_id={keywordid}";
        String url1 = "";
        if(url.contains("{keywordid}")){
            url1 = url.replace("{keywordid}","12");
        }
//        Date d = DateUtil.getSomeDay(new Date(),5);
        System.out.println(url1);
    }

    public static Long getBeginDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar. getInstance();
        calendar.setTime( new Date());
        calendar.set(Calendar. HOUR_OF_DAY, 0);
        calendar.set(Calendar. MINUTE, 0);
        calendar.set(Calendar. SECOND, 0);
        System.out.println(sdf.format(calendar.getTime()));
        return calendar.getTime().getTime();
    }

    private static String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days){
            return days + "天，"+hours+"小时，"+minutes+"分，"+second+"秒";
        }else {
            return hours+"小时，"+minutes+"分，"+second+"秒";
        }
    }

    static class DurationInitBolt{
        private boolean autoAck = false;
        private boolean autoAnchor = false;
        private String name;

        public DurationInitBolt(String name, boolean autoAck, boolean autoAnchor){
            this.name = name;
            this.autoAck = autoAck;
            this.autoAnchor = autoAnchor;
        }

        public void m(){
            System.out.println(name);
        }
    }
}
