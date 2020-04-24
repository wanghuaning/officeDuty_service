package com.local.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 *
 * @Description: 基于com.google.code.gson封装的json转换工具类
 * @author whn
 * @date 2018年5月30日
 *
 */
public class GsonUtil {
    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 对象转成json
     *
     * @param object
     * @return json
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        try {
            if (gson != null) {
                gsonString = gson.toJson(object);
            }
            return gsonString;
        }catch (Exception e){
            return gsonString;
        }
    }

    /**
     * Json转成对象
     *
     * @param gsonString
     * @param cls
     * @return 对象
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        try {
            if (gson != null) {
                t = gson.fromJson(gsonString, cls);
            }
            return t;
        }catch (Exception e){
            return t;
        }
    }

    /**
     * json转成list<T>
     *
     * @param gsonString
     * @param cls
     * @return list<T>
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            if (gson != null) {
                list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
                }.getType());
            }
            return list;
        }catch (Exception e){
            return list;
        }
    }

    /**
     * json转成list中有map的
     *
     * @param gsonString
     * @return List<Map<String, T>>
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            if (gson != null) {
                list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
                }.getType());
            }
            return list;
        }catch (Exception e){
            return list;
        }
    }

    /**
     * json转成map的
     *
     * @param gsonString
     * @return Map<String, T>
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            if (gson != null) {
                map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
                }.getType());
            }
            return map;
        }catch (Exception e){
            return map;
        }
    }
}
