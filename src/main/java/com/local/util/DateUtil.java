package com.local.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static String fm = "yyyy-MM-dd";
    private static String fm1 = "yyyy-MM";
    private static String fm2 = "yyyyMMdd";
    private static String fm3 = "yyyy-MM-dd HH:mm:ss";
    private static String fm4 = "yyyyMM";
    private static String fm5 = "yyyy-MM-ddHH:mm:ss";
    private static String fm6 = "yyyy年MM月";
    private static String fm7 = "yyyy年MM月dd日";
    public static Date stringToDate(String dateStr)throws  Exception{
        String[] arr =null;
        if(dateStr != null){
            arr=dateStr.split("-");
        }
        String format =fm;
        if(dateStr.contains(":") && dateStr.contains(" ")){
            format=fm3;
        } else if(dateStr.contains(":")){
            format=fm5;
        }
        if(arr != null && arr.length == 2) {
            return new SimpleDateFormat(format).parse(dateStr+"-01");
        }else{
            return new SimpleDateFormat(format).parse(dateStr);
        }
    }
    public static String dateToString(Date date){
        if (date==null){
            return null;
        }
        return  new SimpleDateFormat(fm).format(date);
    }
    public static Date addMonths(Date date,int num){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,num);
        return calendar.getTime();
    }
    public static Date addDates(Date date,int num){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,num);
        return calendar.getTime();
    }
    public static Date addSecond(Date date,int num){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND,1);
        return calendar.getTime();
    }
}
