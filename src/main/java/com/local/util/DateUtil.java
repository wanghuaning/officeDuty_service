package com.local.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static Date stringToDate(String dateStr) throws Exception {
        String[] arr = null;
        if (dateStr == null && "".equals(dateStr) && "null".equals(dateStr)) {
            return null;
        }
        String format = fm;
        if (dateStr.contains("-")){
            arr = dateStr.split("\\-");
            if (arr != null && arr.length == 2) {
                return new SimpleDateFormat(format).parse(dateStr.trim() + "-01");
            } else {
                return new SimpleDateFormat(format).parse(dateStr);
            }
        } else if (dateStr.contains(":") && dateStr.contains(" ")) {
            arr = dateStr.split(":");
            format = fm3;
        } else if (dateStr.contains(":")) {
            arr = dateStr.split("\\:");
            format = fm5;
            if (arr != null && arr.length == 2) {
                return new SimpleDateFormat(format).parse(dateStr.trim() + ":01");
            } else {
                return new SimpleDateFormat(format).parse(dateStr);
            }
        } else if (dateStr.contains("/")) {
            arr = dateStr.split("\\/");
            format = fm8;
            if (arr != null && arr.length == 2) {
                return new SimpleDateFormat(format).parse(dateStr.trim() + "/01");
            } else {
                return new SimpleDateFormat(format).parse(dateStr);
            }
        } else if (dateStr.contains(".")) {
            arr = dateStr.split("\\.");
            format = fm9;
            if (arr != null && arr.length == 2) {
                return new SimpleDateFormat(format).parse(dateStr.trim() + ".01");
            } else {
                return new SimpleDateFormat(format).parse(dateStr);
            }
        }else  if (dateStr.contains("年") && dateStr.contains("月") && dateStr.contains("日")){
            format=fm7;
        }else  if (dateStr.contains("年") && dateStr.contains("月") && !dateStr.contains("日")){
            format=fm7;
            dateStr=dateStr.trim() + "1日";
        }else  if (dateStr.length()==8){
            format=fm2;
        }else  if (dateStr.length()==6){
            format=fm2;
            dateStr=dateStr.trim() + "01";
        }else {
            return null;
        }
        if (arr != null && arr.length == 2) {
            return new SimpleDateFormat(format).parse(dateStr.trim() + "-01");
        } else {
            return new SimpleDateFormat(format).parse(dateStr);
        }
    }

    public static Date stringToDateMM(String dateStr) throws Exception {
        String[] arr = null;
        if (dateStr == null && "".equals(dateStr) && "null".equals(dateStr)) {
            return null;
        }
         if (dateStr.contains(":") && dateStr.contains(" ")) {
            arr = dateStr.split(":");
        }
        if (arr != null && arr.length == 2) {
            return new SimpleDateFormat(fm3).parse(dateStr.trim() + "-01");
        } else {
            return new SimpleDateFormat(fm3).parse(dateStr);
        }
    }
    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static Date timeStamp2Date(String seconds)throws Exception {
        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(seconds);
        return date;
    }
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(fm).format(date);
    }

    public static String dateMMToString(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(fm3).format(date);
    }
    public static Date addYears(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, num);
        return calendar.getTime();
    }

    public static Date addMonths(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    public static Date toDate(int year, int month,int day) throws Exception{
        String dateStr=year+ "-"+ month + "-" + day;
        return new SimpleDateFormat(fm).parse(dateStr);
    }

    public static Date addDates(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 1);
        return calendar.getTime();
    }

    public static Date parseDateYMD(Date date) {
        if (date != null && !"".equals(date)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String s = sdf.format(date);
                Date date1 = sdf.parse(s);
                return date1;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.MONTH)+1;
        return day;
    }

    public static String getMonStr(Date date) {
        String mon = String.format("%tm", date);
        return mon;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.YEAR);
        return day;
    }

    /**
     * 功能描述：返回小
     *
     * @param date 日期
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
     * @param date 日期
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
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取当日0点
     *
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
     *
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

    /**
     * 时间序号字符串
     * @param date
     * @return
     */
    public static String getDateNum(Date date) {
        return String.valueOf(getYear(date)) + getMonth(date) + getDay(date) + getHour(date) + getMinute(date) + getSecond(date);
    }

    /**
     * 获取年龄
     * @param
     */
    public static int getAgeByBirth(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    /**
     * 获取某月年龄
     * @param
     */
    public static int getAgeByMonth(Date birthDay,Calendar cal) throws ParseException {
        int age = 0;
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH)+1;  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH)+1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
    public static void main(String[] args) {
        System.out.println(getMonth(new Date()));
        String mon = String.format("%tm", new Date());
        System.out.println(getDateNum(new Date()));
        System.out.println(mon);
    }
}
