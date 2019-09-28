package com.local.util;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {


    private static Logger log = LoggerFactory.getLogger(Result.class);

    /**
     * 字符串
     */
    private static  final String EMPTY_STRING="";
    /**
     * 返回状态码 ： 如 200
     */
    private static  final String CODE="code";
    /**
     * 返回提示信息：如 更新成功
     */
    private static  final String MSG="msg";
    /**
     * 返回数据集合：如list对象等
     */
    private static  final String RESULT="result";
    /**
     * 额外参数：如Map个格式的其他数据
     */
    private static  final String EXT="ext";
    /**
     * 返回状态代码
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 返回结果map
     */
    private Map resultMap;

    /**
     * 返回结果
     * @param code 状态码
     * @param msg  提示信息
     * @param data  结果集合
     * @param param 额外参数
     */
    public Result(String code, String msg, Object data, Object param){
        this.msg=msg;
        this.data=data;
        resultMap=new HashMap();
        resultMap.put(CODE,code);
        resultMap.put(MSG,this.msg);
        if(!StrUtils.isBlank(data)){
            resultMap.put(RESULT,this.data);
        }
        if(!StrUtils.isBlank(param)){
            resultMap.put(EXT,param);
        }
        this.resultMap=resultMap;
    }

    /**
     * 正确的返回结果
     * @param data
     */
    public Result(Object data){
        this.msg=msg;
        this.data=data;
        resultMap=new HashMap();
        resultMap.put(CODE,200);
        resultMap.put(MSG,"success");
        if(!StrUtils.isBlank(data)){
            resultMap.put(RESULT,this.data);
        }
        this.resultMap=resultMap;
    }



    /**
     * 返回结果格式化
     * @param code 状态值 200成功 500错误
     * @param msg 提示信息
     * @param data 返回数据
     * @param param ext其他参数
     * @return  调用Result.getJson()返回json数据
     */
    public final static Result getInstance(String code, String msg, Object data, Object param) {
        return new Result(code,msg,data,param);
    }

    /**
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj) {
        if (StrUtils.isBlank(obj)) {
            return EMPTY_STRING;
        }
        return Json.toJson(obj, JsonFormat.full());
    }

    /**
     * 返回resultMap的json格式数据
     * @return
     */
    public String getJson() {
        if (StrUtils.isBlank(this.resultMap)) {
            return null;
        }
        //定义返回JSON字符串
        String retrunJson = "";
        try {
            //1、格式化时间
//            long startTime = System.currentTimeMillis();
            retrunJson = Json.toJson(this.resultMap, JsonFormat.full().setDateFormat("yyyy-MM-dd HH:mm:ss"));
//            long endTime = System.currentTimeMillis();
//            System.out.println(this.resultMap.get("ext")+"程序运行时间：" + (endTime - startTime) + "ms");
        } catch (Exception e) {
            log.error("转换json异常",e);
        }
        return retrunJson;
    }

    public String getSimpleJson() {
        if (StrUtils.isBlank(this.resultMap)) {
            return null;
        }
        //定义返回JSON字符串
        String retrunJson = "";
        try {
            //1、格式化时间
            retrunJson = Json.toJson(this.resultMap, JsonFormat.nice().setDateFormat("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            log.error("转换json异常",e);
        }
        return retrunJson;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
    }
}
