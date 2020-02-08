package com.local.util;

import com.local.controller.PeopleController;
import com.local.entity.sys.SYS_UNIT;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityUtil {
    private final static Logger log = LoggerFactory.getLogger(EntityUtil.class);

    /**
     * 获取实体类型的属性名和类型 从json中
     *
     * @param model 为实体类
     * @author kou  为传入参数
     */
    public static void setReflectModelValue(Object model, JSONObject key) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, Exception {
        // 获取实体类的所有属性，返回Field数组
        Field[] field = model.getClass().getDeclaredFields();
        // 获取属性的名字
        String[] modelName = new String[field.length];
        String[] modelType = new String[field.length];
        for (int i = 0; i < field.length; i++) {
            // 获取属性的名字
            String name = field[i].getName();
            modelName[i] = name;
            // 获取属性类型
            String type = field[i].getGenericType().toString();
            modelType[i] = type;
            //关键。。。可访问私有变量
            field[i].setAccessible(true);
            // 将属性的首字母大写
            if (type.equals("class java.lang.String")) {
                if (key.has(name)) {//给属性设置
                    Object str = key.get(name);
                    if (!StrUtils.isBlank(str)) {
                        try {
                            field[i].set(model, field[i].getType().getConstructor(field[i].getType()).newInstance(String.valueOf(str)));
                        } catch (Exception e) {
                            field[i].set(model, field[i].getType().getConstructor(field[i].getType()).newInstance(""));
                            e.printStackTrace();
//                            throw new Exception("null:=>"+name+"=>"+str);
                        }
                    }
                }
            }
            if (type.equals("class java.lang.Integer") || type.equals("int")) {
                if (key.has(name)) {
                    Integer sd = Integer.valueOf(String.valueOf(key.get(name)));
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null || value == 0) {
                        m = model.getClass().getMethod("set" + name, Integer.class);
                        m.invoke(model, sd);
                    }
                }
            }
            if (type.equals("long") || type.equals("class java.lang.Long")) {
                if (key.has(name)) {
                    Long sd = Long.valueOf(String.valueOf(key.get(name)));
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    Method m = model.getClass().getMethod("get" + name);
                    Long value = (Long) m.invoke(model);
                    if (value == null || value == 0) {
                        m = model.getClass().getMethod("set" + name, Long.class);
                        m.invoke(model, sd);
                    }
                }
            }
            if (type.equals("class java.lang.Boolean")) {
                if (key.has(name)) {
                    Boolean sd = Boolean.valueOf(String.valueOf(key.get(name)));
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Boolean.class);
                        m.invoke(model, sd);
                    }
                }
            }
            if (type.equals("class java.lang.Double")) {
                if (key.has(name)) {
                    Double sd = Double.valueOf(String.valueOf(key.get(name)));
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null || value == 0) {
                        m = model.getClass().getMethod("set" + name, Integer.class);
                        m.invoke(model, sd);
                    }
                }
            }
            if (type.equals("class java.util.Date")) {
                if (key.has(name)) {//给属性设置
                    Date date = null;
                    String str = String.valueOf(key.get(name));
                    if (StrUtils.parseDate(str) != null) {
                        try {
                            date = DateUtil.stringToDate(str);
                        } catch (Exception e) {
                            throw new Exception("=>" + str);
                        }
                    }
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    Method m = model.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Date.class);
                        /// m.invoke(model, new Date());
                        m.invoke(model, date);
                    }
                }
            }
        }
    }

    /**
     * 获取实体类值
     *
     * @param model
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static void getReflectModelValue(Object model) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 获取实体类的所有属性，返回Field数组
        Field[] field = model.getClass().getDeclaredFields();
        // 获取属性的名字
        String[] modelName = new String[field.length];
        String[] modelType = new String[field.length];
        for (int i = 0; i < field.length; i++) {
            // 获取属性的名字
            String name = field[i].getName();
            modelName[i] = name;
            // 获取属性类型
            String type = field[i].getGenericType().toString();
            modelType[i] = type;
            //关键。。。可访问私有变量
            field[i].setAccessible(true);
            // 将属性的首字母大写
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
            if (type.equals("class java.lang.String")) {
                // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = model.getClass().getMethod("get" + name);
                // 调用getter方法获取属性值
                String value = (String) m.invoke(model);
                if (value != null) {
                    System.out.println(name + ":" + value);
                }

            }
            if (type.equals("class java.lang.Integer")) {
                Method m = model.getClass().getMethod("get" + name);
                Integer value = (Integer) m.invoke(model);
                if (value != null) {
                    System.out.println(name + ":" + value);
                }
            }
            if (type.equals("class java.lang.Short")) {
                Method m = model.getClass().getMethod("get" + name);
                Short value = (Short) m.invoke(model);
                if (value != null) {
                    System.out.println(name + ":" + value);
                }
            }
            if (type.equals("class java.lang.Double")) {
                Method m = model.getClass().getMethod("get" + name);
                Double value = (Double) m.invoke(model);
                if (value != null) {
                    System.out.println(name + ":" + value);
                }
            }
            if (type.equals("class java.lang.Boolean")) {
                Method m = model.getClass().getMethod("get" + name);
                Boolean value = (Boolean) m.invoke(model);
                if (value != null) {
                    System.out.println(name + ":" + value);
                }
            }
            if (type.equals("class java.util.Date")) {
                Method m = model.getClass().getMethod("get" + name);
                Date value = (Date) m.invoke(model);
                if (value != null) {
                    System.out.println(name + ":" + value.toLocaleString());
                }
            }
        }
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据属性名设置属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static void setFieldValueByFieldName(String fieldName, Object object, String value) {
        try {
            // 获取obj类的字节文件对象
            Class c = object.getClass();
            // 获取该类的成员变量
            Field f = c.getDeclaredField(fieldName);
            // 取消语言访问检查
            f.setAccessible(true);
            // 给变量赋值
            f.set(object, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {

    }
}
