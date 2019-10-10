package com.local.util;

import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;

/**
 *
 * String工具类
 *
 */
public class StrUtils {
    private  static String randomArr[]= new String[]{"0","1","2","3","4","5","6","7","8"
            ,"9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v"
            ,"w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V"
            ,"W","X","Y","Z"};

    /**
     * 主要用于对比
     * 比较源集合 得出sourceList在compList没有的选项
     * @param sourceList 比较的源b
     * @param compList 参照的源
     * @return
     */
    public static  List compSourceListDiff(List sourceList,List compList){
        //循环compList：得出需要sourceList元素中在compList中没有集合
        List sourceDiffList=new ArrayList(); //最终异同的
        for (Object source:sourceList){
            if(!compList.contains(source)){
                sourceDiffList.add(source);
            }
        }
        return  sourceDiffList;
    }

    /**
     * 判断用户是否为空
     * @param str 判断字符串
     * @return
     */
    public static boolean isBlank(String str){
        if(str==null||"".equals(str)||str.trim().length()==0){
            return true;
        }
        return false;
    }

    /**
     * 随机数
     * @param strLength 返回的字符串长度
     * @return  返回指定长度随机的字符串
     */
    public static String randomStr(Integer strLength){
        Random random = new Random();
        if (StrUtils.isBlank(strLength)) {
            return "";
        }
        if (strLength instanceof Integer) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < strLength; i++) {
                str.append(randomArr[random.nextInt(randomArr.length)]);
            }
            return str.toString();
        } else {
            return "";
        }
    }

    /**
     * 获得uuid，去除-符号的标志位
     * @return
     */
    public static String randomUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 是否为空
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj){
        if(obj==null||"".equals(obj.toString())||obj.toString().trim().length()==0){
            return true;
        }
        return false;
    }

    /**
     * 转化为String
     * @param obj
     * @return
     */
    public static String toString(Object obj){
        if(obj==null||"".equals(obj.toString())||obj.toString().trim().length()==0){
            return "";
        }
        return obj.toString();
    }

    /**
     * 获得访问的ip地址
     * @param request
     * @return ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip != null&&ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 验证是否是ip地址
     * @param ip
     * @return ture or false
     */
    public static  boolean isIpAddr(String ip){
        if (StrUtils.isBlank(ip)){
            return false;
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        // 判断ip地址是否与正则表达式匹配
        if (!ip.matches(regex)) {
              return false;
        }

        return true;
    }
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        // 先判空
        if (isBlank(str)) {
            return false;
        }
        // 用JAVA自带的函数
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        /*
        // 用ascii码
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }*/

        return true;
    }

    /**
     * String转成int的值， 若无法转换，默认返回0
     */
    public static int strToInt(String string) {
        return stoi(string, 0);
    }

    public static int stoi(String string, int defaultValue) {
        if ((string == null) || (string.equalsIgnoreCase(""))) {
            return defaultValue;
        }
        int id;
        try {
            Double d=Double.parseDouble(string);
            id = (new Double(d)).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
        return id;
    }
    /**
     * String转成long的值， 若无法转换，默认返回0
     */
    public static long strToLong(String string) {
        return stol(string, 0);
    }

    public static long stol(String string, long defaultValue) {
        if ((string == null) || (string.equalsIgnoreCase(""))) {
            return defaultValue;
        }
        long ret;
        try {
            ret = Long.parseLong(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }

        return ret;
    }

    /**
     * String转成double的值， 若无法转换，默认返回0.00
     */
    public static double strToDouble(String string) {
        return stod(string, 0.00);
    }

    public static double stod(String string, double defaultValue) {
        if ((string == null) || (string.equalsIgnoreCase(""))) {
            return defaultValue;
        }
        double ret;
        try {
            ret = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }

        return ret;
    }
    private static String[] parsePatterns = {"yyyy-MM-dd","yyyy年MM月dd日",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    public static Date parseDate(String string) {
        if (string == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(string, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }
    public static void main(String[] args) {
        List<String> attr=new ArrayList<String>();
        for(int i=0;i<10000;i++){
            String str= StrUtils.randomStr(4);
            if(attr.contains(str)){
                System.out.println("已经存在"+str);
            }
            attr.add(str);
        }
        System.out.println(attr);
    }
}
