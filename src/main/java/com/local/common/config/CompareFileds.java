package com.local.common.config;

import com.local.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

public class CompareFileds {
    private static Logger logger = LoggerFactory.getLogger(CompareFileds.class);
    public final static String [] USERARR={"createTime","userAccount","userPassword","lastTime","enabled","peopleId","unitId","unitName"};
    public final static String [] PEOPLEARR={"name","idcard","birthday","sex","birthplace","nationality","workday","party","partyTime",
            "secondParty","thirdParty","position","positionTime","positionLevel","positionLevelTime","turnRank","turnRankTime",
            "baseWorker","politicalStatus","createTime","detail","education","enabled"};
    public final static String [] DUTYARR={"name","createTime","leader","leaderType","selectionMethod","status","documentNumber","djunct","documentduty","realName"};
    public final static String [] RANKARR={"name","createTime","rankType","status","serveTime","documentNumber","batch","democracy","detail","approvalTime","dutyTime"};
    public final static String [] EDUCATIONARR={"name","createTime","degree","endTime","degreeTime","schoolType","school","profession"};
    public final static String [] REWARDARR={"name","nameType","createTime","approvalUnit","duty","revocationDate","unitType"};
    public final static String [] ASSESSMENTARR={"name","year"};

    public static Map<String,String> getPeopleMaps(){
        Map<String,String> map=new HashMap<>();
        map.put("name","姓名");
        map.put("idcard","身份证号");
        map.put("birthday","出生年月");
        map.put("sex","性别");
        map.put("birthplace","籍贯");
        map.put("nationality","民族");
        map.put("workday","参加工作时间");
        map.put("party","政治面貌");
        map.put("partyTime","入党时间");
        map.put("secondParty","第二党派");
        map.put("thirdParty","第三党派");
        map.put("position","现职务层次");
        map.put("positionTime","任现职务层次时间");
        map.put("positionLevel","现职级");
        map.put("positionLevelTime","现职级时间");
        map.put("turnRank","套转职级");
        map.put("turnRankTime","套转职级时间");
        map.put("baseWorker","姓名");
        map.put("baseWorker","是否具有两年以上基层工作经历");
        map.put("politicalStatus","编制类型");
        map.put("createTime","公务员登记时间");
        map.put("detail","特殊人员");
        map.put("education","最新学历");
        map.put("enabled","是否兼职 1:是；0：否");
        return map;
    }
    public static Map<String,String> getDutyMaps(){
        Map<String,String> map=new HashMap<>();
        map.put("name","职务名称");
        map.put("createTime","任职时间");
        map.put("leader","是否领导班子成员");
        map.put("leaderType","成员类别");
        map.put("selectionMethod","选拔任用方式");
        map.put("status","任职状态");
        map.put("serveTime","免职时间");
        map.put("documentNumber","免职文号");
        map.put("djunct","是否兼任");
        map.put("documentduty","任职文号");
        map.put("realName","是否纳入实名制管理");
        return map;
    }
    public static Map<String,String> getRankMaps(){
        Map<String,String> map=new HashMap<>();
        map.put("name","职级层次");
        map.put("createTime","任职日期");
        map.put("rankType","类别（职级标志）");
        map.put("status","状态");
        map.put("serveTime","终止日期");
        map.put("documentNumber","批准文号");
        map.put("batch","批次");
        map.put("democracy","民主测评结果");
        map.put("detail","备注");
        map.put("approvalTime","审批时间");
        map.put("dutyTime","任同职务时间");
        return map;
    }
    public static Map<String,String> getAssessmentMaps(){
        Map<String,String> map=new HashMap<>();
        map.put("name","考核结论");
        map.put("year","考核年度");
        return map;
    }
    public static Map<String,String> getEducationMaps(){
        Map<String,String> map=new HashMap<>();
        map.put("name","学历名称");
        map.put("createTime","入学时间");
        map.put("degree","学位名称");
        map.put("endTime","毕（肄）业时间");
        map.put("degreeTime","学位授予时间");
        map.put("schoolType","学校类型");
        map.put("school","毕业学校");
        map.put("profession","专业");
        return map;
    }
    public static Map<String,String> getRewardMaps(){
        Map<String,String> map=new HashMap<>();
        map.put("name","奖惩名称");
        map.put("nameType","奖惩名称代码");
        map.put("createTime","批准日期");
        map.put("approvalUnit","批准机关");
        map.put("duty","受奖惩时职务层次");
        map.put("revocationDate","撤销日期");
        map.put("unitType","批准机关性质");
        return map;
    }
    public static Map<String,String> getUserMaps(){
        Map<String,String> map=new HashMap<>();
        map.put("userAccount","账号");
        map.put("lastTime","最后修改密码的日期");
        return map;
    }
    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个list分别存obj1,obj2此属性名的值
     * @param obj1 进行属性比较的对象1
     * @param obj2 进行属性比较的对象2
     * @param compareArr 选择要比较的属性数组
     * @return 属性差异比较结果map
     */
    public static Map<String, List<Object>> compareFields(Object obj1, Object obj2, String[] compareArr) {
        try {
            Map<String, List<Object>> map = new HashMap<>();
            List<String> ignoreList = null;
            if (compareArr != null && compareArr.length > 0) {
                // array转化为list
                ignoreList = Arrays.asList(compareArr);
            }
            // 只有两个对象都是同一类型的才有可比性
            if (obj1.getClass() == obj2.getClass()) {
                Class clazz = obj1.getClass();
                // 获取object的属性描述
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
                        Object.class).getPropertyDescriptors();
                // 这里就是所有的属性了
                for (PropertyDescriptor pd : pds) {
                    // 属性名
                    String name = pd.getName();
                    // 如果当前属性选择进行比较，跳到下一次循环
                    if (ignoreList != null && ignoreList.contains(name)) {
                        // get方法
                        Method readMethod = pd.getReadMethod();
                        // 在obj1上调用get方法等同于获得obj1的属性值
                        Object objBefore = readMethod.invoke(obj1);
                        // 在obj2上调用get方法等同于获得obj2的属性值
                        Object objAfter = readMethod.invoke(obj2);
                        if (null==objBefore || "null".equals(objBefore)){
                            objBefore="";
                        }
                        if (null==objAfter || "null".equals(objAfter)){
                            objAfter="";
                        }
                        if (objBefore instanceof Timestamp) {
                            objBefore = new Date(((Timestamp) objBefore).getTime());
                        }
                        if (objBefore instanceof Date) {
                            objBefore = new Date(((Date) objBefore).getTime());
                            Date dateBefore=DateUtil.timeStamp2Date(String.valueOf(objBefore));
                            objBefore = DateUtil.dateToString(dateBefore);
                        }
                        if (objAfter instanceof Timestamp) {
                            objAfter = new Date(((Timestamp) objAfter).getTime());
                        }
                        if (objAfter instanceof Date) {
                            objAfter = new Date(((Date) objAfter).getTime());
                            Date dateAfter=DateUtil.timeStamp2Date(String.valueOf(objAfter));
                            objAfter = DateUtil.dateToString(dateAfter);
                        }
                        if (objBefore == null && objAfter == null) {
                            continue;
                        } else if (objBefore == null && objAfter != null) {
                            List<Object> list = new ArrayList<Object>();
                            list.add(objBefore);
                            list.add(objAfter);
                            map.put(name, list);
                            continue;
                        }
                        // 比较这两个值是否相等,不等则放入map
//                        System.out.println(objBefore +"=>"+objAfter);
                        if (!objBefore.equals(objAfter)) {
                            List<Object> list = new ArrayList<Object>();
                            list.add(objBefore);
                            list.add(objAfter);
                            map.put(name, list);
                        }
                    }
                }
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
