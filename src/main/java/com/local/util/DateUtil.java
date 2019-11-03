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
    private static String fm8 = "yyyy/MM/dd";
    private static String fm9 = "yyyy.MM.dd";
    public static Date stringToDate(String dateStr)throws  Exception{
        String[] arr =null;
        if(dateStr != null && !"".equals(dateStr) && !"null".equals(dateStr)){
            arr=dateStr.split("-");
        }else {
            return null;
        }
        String format =fm;
        if(dateStr.contains(":") && dateStr.contains(" ")){
            format=fm3;
        } else if(dateStr.contains(":")){
            format=fm5;
        }else if (dateStr.contains("/")){
            format=fm8;
        }else if (dateStr.contains(".")){
            format=fm9;
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

    public static Date parseDateYMD(Date date){
        if (date != null && !"".equals(date)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String s = sdf.format(date);
                Date date1 =  sdf.parse(s);
                return date1;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }
    public static int getDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int  day=calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }
    public static int getMonth(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int  day=calendar.get(Calendar.MONTH);
        return day;
    }
    public static String getMonStr(Date date){
        String mon=String .format("%tm",date);
        return mon;
    }
    public static int getYear(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int  day=calendar.get(Calendar.YEAR);
        return day;
    }
    /**
     * 功能描述：返回小
     *
     * @param date
     *            日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    /**
     * 功能描述：返回分
     *
     * @param date
     *            日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date
     *            Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取当日0点
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取当日23点
     * @return
     */
    public static Date getnowEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getMonth(new Date()));
        String mon=String .format("%tm", new Date());
        System.out.println(mon);
    }
}
