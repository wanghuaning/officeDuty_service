package com.local.cell;

import com.local.entity.sys.*;
import com.local.service.*;
import com.local.util.DateUtil;
import com.local.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PeopleManager {
    private final static Logger logger= LoggerFactory.getLogger(PeopleManager.class);

    public static List<SYS_People> getPeopleDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                        UnitService unitService,String fullImport)throws Exception {
        List<SYS_People> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_People people = new SYS_People();
                people.setUnitId(unit.getId());
                people.setUnitName(unit.getName());
                if (!StrUtils.isBlank(map.get("姓名"))) {
                    people.setName(map.get("姓名").toString());
                    if (!StrUtils.isBlank(map.get("身份证号"))) {
                        people.setIdcard(map.get("身份证号").toString());
                        String birthdayStr=String.valueOf(map.get("出生年月"));
                        if (!StrUtils.isBlank(birthdayStr)){
                            people.setBirthday(DateUtil.stringToDate(birthdayStr));
                        }
                        people.setSex(StrUtils.toNullStr(map.get("性别")));
                        people.setBirthplace(StrUtils.toNullStr(map.get("籍贯")));
                        people.setNationality(StrUtils.toNullStr(map.get("民族 ")));
                        String workdayStr=String.valueOf(map.get("参加工作时间"));
                        if (!StrUtils.isBlank(workdayStr)){
                            people.setWorkday(DateUtil.stringToDate(workdayStr));
                        }
                        people.setParty(StrUtils.toNullStr(map.get("政治面貌")));
                        String partyTimeStr=String.valueOf(map.get("入党（团）时间"));
                        if (!StrUtils.isBlank(partyTimeStr)){
                            people.setPartyTime(DateUtil.stringToDate(partyTimeStr));
                        }
                        people.setSecondParty(StrUtils.toNullStr(map.get("第二党派")));
                        people.setThirdParty(StrUtils.toNullStr(map.get("第三党派")));
                        people.setPosition(StrUtils.toNullStr(map.get("现职务层次")));
                        String positionTimeStr=String.valueOf(map.get("任现职务层次时间"));
                        if (!StrUtils.isBlank(positionTimeStr)){
                            people.setPositionTime(DateUtil.stringToDate(positionTimeStr));
                        }
                        people.setPositionLevel(String.valueOf(map.get("现职级")));
                        String positionLevelTimeStr=String.valueOf(map.get("任现职级时间"));
                        if (!StrUtils.isBlank(positionLevelTimeStr)){
                            people.setPositionLevelTime(DateUtil.stringToDate(positionLevelTimeStr));
                        }
                        people.setBaseWorker(StrUtils.toNullStr(map.get("是否具有两年以上基层工作经历")));
                        people.setPoliticalStatus(StrUtils.toNullStr(map.get("编制类型")));
                        String createTimeStr=String.valueOf(map.get("公务员登记时间"));
                        if (!StrUtils.isBlank(createTimeStr)){
                            people.setPositionLevelTime(DateUtil.stringToDate(createTimeStr));
                        }
                        String enableStr=StrUtils.toNullStr(map.get("现职"));
                        if ("否".equals(enableStr)){
                            people.setEnabled("1");
                        }else {
                            people.setEnabled("0");
                        }
                        people.setDetail(StrUtils.toNullStr(map.get("备注")));
                        SYS_People people1 =service.selectPeopleByIdcardAndUnitId(people.getIdcard(), unit.getId());
                        if (people1 != null) {
                            if ("1".equals(fullImport)){
                                people.setId(people1.getId());
                                people.setPeopleOrder(people1.getPeopleOrder());
                                service.updatePeople(people);
                            }else {
                                stringBuffer.append("人员表：第" + list.indexOf(map) + "行;身份证号重复，请检查！");
                                logger.error("人员表：第" + list.indexOf(map) + "行;身份证号重复，请检查！");
                            }
                        } else {
                                String uuid = UUID.randomUUID().toString();
                                people.setId(uuid);
                                service.insertPeoples(people);
                                peopleList.add(people);
                        }
                    } else {
                        stringBuffer.append("人员表：第" + list.indexOf(map) + "行;身份证号为空！");
                        logger.error("人员表：第" + list.indexOf(map) + "行;身份证号为空！");
                    }
                } else {
                    stringBuffer.append("人员表：第" + list.indexOf(map) + "行;姓名为空！");
                    logger.error("人员表：第" + list.indexOf(map) + "行;姓名为空！");
                }
                people.setUnitId(unit.getId());
            } else {
                logger.error("人员表：第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("人员表：第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }

    /**
     * 职务表导入
     * @param list
     * @param service
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static List<SYS_Duty> getPeopleDutyDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                          UnitService unitService, String fullImport, DutyService dutyService)throws Exception {
        List<SYS_Duty> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_Duty duty = new SYS_Duty();
                if (!StrUtils.isBlank(map.get("姓名"))) {
                    if (!StrUtils.isBlank(map.get("身份证号"))) {
                        SYS_People people=service.selectPeopleByIdcardAndUnitId(String.valueOf(map.get("身份证号")),unit.getId());
                        if (people!=null){
                            duty.setPeopleId(people.getId());
                            duty.setPeopleName(people.getName());
                            duty.setUnitId(people.getUnitId());
                            String creatTime=String.valueOf(map.get("任职时间"));
                            if (!StrUtils.isBlank(creatTime)){
                                duty.setCreateTime(DateUtil.stringToDate(creatTime));
                            }
                            duty.setName(StrUtils.toNullStr(map.get("职务名称")));
                            duty.setSelectionMethod(StrUtils.toNullStr(map.get("选拔任用方式")));
                            duty.setStatus(StrUtils.toNullStr(map.get("任职状态")));
                            String serveTime=String.valueOf(map.get("免职时间"));
                            if (!StrUtils.isBlank(serveTime)){
                                duty.setServeTime(DateUtil.stringToDate(serveTime));
                            }
                            duty.setDocumentNumber(StrUtils.toNullStr(map.get("免职文号")));
                            SYS_Duty duty1=dutyService.selectDutyByNameAndTime(duty.getName(),people.getId(),duty.getCreateTime());
                            if ("1".equals(fullImport)){//覆盖导入
                                if (duty1!=null){
                                    duty.setId(duty1.getId());
                                    dutyService.updateDuty(duty);
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    duty.setId(uuid);
                                    dutyService.insertDuty(duty);
                                }
                            }else {
                                if (duty1!=null){
                                    stringBuffer.append("职务表：第" + list.indexOf(map) + "行;该职务已存在，请勿重复导入！");
                                    logger.error("职务表：第" + list.indexOf(map) + "行;该职务已存在，请勿重复导入！");
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    duty.setId(uuid);
                                    dutyService.insertDuty(duty);
                                }
                            }
                        }else {
                            stringBuffer.append("职务表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                            logger.error("职务表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                        }
                    } else {
                        stringBuffer.append("职务表：第" + list.indexOf(map) + "行;身份证号为空！");
                        logger.error("职务表：第" + list.indexOf(map) + "行;身份证号为空！");
                    }
                } else {
                    stringBuffer.append("职务表：第" + list.indexOf(map) + "行;姓名为空！");
                    logger.error("职务表：第" + list.indexOf(map) + "行;姓名为空！");
                }
            } else {
                logger.error("职务表：第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("职务表：第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }

    /**
     * 职级表导入
     * @param list
     * @param service
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static List<SYS_Rank> getPeopleRankDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                          UnitService unitService, String fullImport, RankService rankService)throws Exception {
        List<SYS_Rank> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_Rank rank = new SYS_Rank();
                if (!StrUtils.isBlank(map.get("姓名"))) {
                    if (!StrUtils.isBlank(map.get("身份证号"))) {
                        SYS_People people=service.selectPeopleByIdcardAndUnitId(String.valueOf(map.get("身份证号")),unit.getId());
                        if (people!=null){
                            rank.setPeopleId(people.getId());
                            rank.setPeopleName(people.getName());
                            rank.setUnitId(people.getUnitId());
                            rank.setName(StrUtils.toNullStr(map.get("职级层次")));
                            String creatTime=String.valueOf(map.get("任职时间"));
                            if (!StrUtils.isBlank(creatTime)){
                                rank.setCreateTime(DateUtil.stringToDate(creatTime));
                            }
                            rank.setStatus(StrUtils.toNullStr(map.get("状态")));
                            rank.setBatch(StrUtils.toNullStr(map.get("批次")));
                            rank.setDemocracy(StrUtils.toNullStr(map.get("民主测评结果")));
                            String serveTime=String.valueOf(map.get("终止日期"));
                            if (!StrUtils.isBlank(serveTime)){
                                rank.setServeTime(DateUtil.stringToDate(serveTime));
                            }
                            rank.setDocumentNumber(StrUtils.toNullStr(map.get("批准文号")));
                            String approvalTime=String.valueOf(map.get("审批日期"));
                            if (!StrUtils.isBlank(serveTime)){
                                rank.setApprovalTime(DateUtil.stringToDate(approvalTime));
                            }
                            SYS_Rank rank1=rankService.selectRankByNameAndTime(rank.getName(),people.getId(),rank.getCreateTime());
                            if ("1".equals(fullImport)){//覆盖导入
                                if (rank1!=null){
                                    rank.setId(rank1.getId());
                                    rankService.updateRank(rank);
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    rank.setId(uuid);
                                    rankService.insertRank(rank);
                                }
                            }else {
                                if (rank1!=null){
                                    stringBuffer.append("职级表：第" + list.indexOf(map) + "行;该职级已存在，请勿重复导入！");
                                    logger.error("职级表：第" + list.indexOf(map) + "行;该职级已存在，请勿重复导入！");
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    rank.setId(uuid);
                                    rankService.insertRank(rank);
                                }
                            }
                        }else {
                            stringBuffer.append("职级表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                            logger.error("职级表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                        }
                    } else {
                        stringBuffer.append("职级表：第" + list.indexOf(map) + "行;身份证号为空！");
                        logger.error("职级表：第" + list.indexOf(map) + "行;身份证号为空！");
                    }
                } else {
                    stringBuffer.append("职级表：第" + list.indexOf(map) + "行;姓名为空！");
                    logger.error("职级表：第" + list.indexOf(map) + "行;姓名为空！");
                }
            } else {
                logger.error("职级表：第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("职级表：第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }

    /**
     * 学历表导入
     * @param list
     * @param service
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static List<SYS_Education> getPeopleEducationDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                          UnitService unitService, String fullImport, EducationService educationService)throws Exception {
        List<SYS_Education> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_Education education = new SYS_Education();
                if (!StrUtils.isBlank(map.get("姓名"))) {
                    if (!StrUtils.isBlank(map.get("身份证号"))) {
                        SYS_People people=service.selectPeopleByIdcardAndUnitId(String.valueOf(map.get("身份证号")),unit.getId());
                        if (people!=null){
                            education.setPeopleId(people.getId());
                            education.setPeopleName(people.getName());
                            education.setUnitId(people.getUnitId());
                            education.setName(StrUtils.toNullStr(map.get("学历名称")));
                            String creatTime=String.valueOf(map.get("入学时间"));
                            if (!StrUtils.isBlank(creatTime)){
                                education.setCreateTime(DateUtil.stringToDate(creatTime));
                            }
                            education.setDegree(StrUtils.toNullStr(map.get("学位名称")));
                            String serveTime=String.valueOf(map.get("毕（肄）业时间"));
                            if (!StrUtils.isBlank(serveTime)){
                                education.setEndTime(DateUtil.stringToDate(serveTime));
                            }
                            String degreeTime=String.valueOf(map.get("学位授予时间"));
                            if (!StrUtils.isBlank(degreeTime)){
                                education.setDegreeTime(DateUtil.stringToDate(degreeTime));
                            }
                            SYS_Education education1=educationService.selectEducationByNameAndTime(education.getName(),people.getId(),education.getCreateTime());
                            if ("1".equals(fullImport)){//覆盖导入
                                if (education1!=null){
                                    education.setId(education1.getId());
                                    educationService.updateEducation(education);
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    education.setId(uuid);
                                    educationService.insertEducation(education);
                                }
                            }else {
                                if (education1!=null){
                                    stringBuffer.append("学历表：第" + list.indexOf(map) + "行;该学历已存在，请勿重复导入！");
                                    logger.error("学历表：第" + list.indexOf(map) + "行;该学历已存在，请勿重复导入！");
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    education.setId(uuid);
                                    educationService.insertEducation(education);
                                }
                            }
                        }else {
                            stringBuffer.append("学历表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                            logger.error("学历表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                        }
                    } else {
                        stringBuffer.append("第" + list.indexOf(map) + "行;身份证号为空！");
                        logger.error("第" + list.indexOf(map) + "行;身份证号为空！");
                    }
                } else {
                    stringBuffer.append("学历表：第" + list.indexOf(map) + "行;姓名为空！");
                    logger.error("学历表：第" + list.indexOf(map) + "行;姓名为空！");
                }
            } else {
                logger.error("学历表：第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("学历表：第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }
    /**
     * 奖惩表导入
     * @param list
     * @param service
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static List<SYS_Reward> getPeopleRewardDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                                    UnitService unitService, String fullImport, RewardService rewardService)throws Exception {
        List<SYS_Reward> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_Reward reward = new SYS_Reward();
                if (!StrUtils.isBlank(map.get("姓名"))) {
                    if (!StrUtils.isBlank(map.get("身份证号"))) {
                        SYS_People people=service.selectPeopleByIdcardAndUnitId(String.valueOf(map.get("身份证号")),unit.getId());
                        if (people!=null){
                            reward.setPeopleId(people.getId());
                            reward.setPeopleName(people.getName());
                            reward.setUnitId(people.getUnitId());
                            reward.setName(StrUtils.toNullStr(map.get("奖惩名称")));
                            String creatTime=String.valueOf(map.get("批准日期"));
                            if (!StrUtils.isBlank(creatTime)){
                                reward.setCreateTime(DateUtil.stringToDate(creatTime));
                            }
                            reward.setNameType(StrUtils.toNullStr(map.get("奖惩名称代码")));
                            String serveTime=String.valueOf(map.get("撤销日期"));
                            if (!StrUtils.isBlank(serveTime)){
                                reward.setRevocationDate(DateUtil.stringToDate(serveTime));
                            }
                            reward.setApprovalUnit(StrUtils.toNullStr(map.get("批准机关")));
                            reward.setApprovalUnit(StrUtils.toNullStr(map.get("受奖惩时职务层次")));
                            reward.setApprovalUnit(StrUtils.toNullStr(map.get("批准机关性质")));
                            SYS_Reward reward1=rewardService.selectRewardByNameAndTime(reward.getName(),people.getId(),reward.getCreateTime());
                            if ("1".equals(fullImport)){//覆盖导入
                                if (reward1!=null){
                                    reward.setId(reward1.getId());
                                    rewardService.updateReward(reward);
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    reward.setId(uuid);
                                    rewardService.insertReward(reward);
                                }
                            }else {
                                if (reward1!=null){
                                    stringBuffer.append("奖惩表：第" + list.indexOf(map) + "行;该奖惩已存在，请勿重复导入！");
                                    logger.error("奖惩表：第" + list.indexOf(map) + "行;该奖惩已存在，请勿重复导入！");
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    reward.setId(uuid);
                                    rewardService.insertReward(reward);
                                }
                            }
                        }else {
                            stringBuffer.append("奖惩表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                            logger.error("奖惩表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                        }
                    } else {
                        stringBuffer.append("奖惩表：第" + list.indexOf(map) + "行;身份证号为空！");
                        logger.error("奖惩表：第" + list.indexOf(map) + "行;身份证号为空！");
                    }
                } else {
                    stringBuffer.append("奖惩表：第" + list.indexOf(map) + "行;姓名为空！");
                    logger.error("奖惩表：第" + list.indexOf(map) + "行;姓名为空！");
                }
            } else {
                logger.error("奖惩表：第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("奖惩表：第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }
    /**
     * 奖惩表导入
     * @param list
     * @param service
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static List<SYS_Assessment> getPeopleAssessmentDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                              UnitService unitService, String fullImport, AssessmentService assessmentService)throws Exception {
        List<SYS_Assessment> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_Assessment assessment = new SYS_Assessment();
                if (!StrUtils.isBlank(map.get("姓名"))) {
                    if (!StrUtils.isBlank(map.get("身份证号"))) {
                        SYS_People people=service.selectPeopleByIdcardAndUnitId(String.valueOf(map.get("身份证号")),unit.getId());
                        if (people!=null){
                            assessment.setPeopleId(people.getId());
                            assessment.setPeopleName(people.getName());
                            assessment.setUnitId(people.getUnitId());
                            assessment.setName(StrUtils.toNullStr(map.get("考核结论")));
                            assessment.setYear(Integer.parseInt(String.valueOf(map.get("考核年度"))));
                            SYS_Assessment assessment1=assessmentService.selectAssessmentByNameAndTime(assessment.getName(),people.getId(),assessment.getYear());
                            if ("1".equals(fullImport)){//覆盖导入
                                if (assessment1!=null){
                                    assessment.setId(assessment1.getId());
                                    assessmentService.updateAssessment(assessment);
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    assessment.setId(uuid);
                                    assessmentService.insertAssessment(assessment);
                                }
                            }else {
                                if (assessment1!=null){
                                    stringBuffer.append("考核表：第" + list.indexOf(map) + "行;该奖惩已存在，请勿重复导入！");
                                    logger.error("考核表：第" + list.indexOf(map) + "行;该奖惩已存在，请勿重复导入！");
                                }else {
                                    String uuid=UUID.randomUUID().toString();
                                    assessment.setId(uuid);
                                    assessmentService.insertAssessment(assessment);
                                }
                            }
                        }else {
                            stringBuffer.append("考核表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                            logger.error("考核表：第" + list.indexOf(map) + "行;人员不存在，无法导入！");
                        }
                    } else {
                        stringBuffer.append("考核表：第" + list.indexOf(map) + "行;身份证号为空！");
                        logger.error("考核表：第" + list.indexOf(map) + "行;身份证号为空！");
                    }
                } else {
                    stringBuffer.append("考核表：第" + list.indexOf(map) + "行;姓名为空！");
                    logger.error("考核表：第" + list.indexOf(map) + "行;姓名为空！");
                }
            } else {
                logger.error("考核表：第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("考核表：第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }
    /**
     * 获取批次号
     */
    public static String getGetBatchNumber() {
        Date date = new Date();
        String day = String.valueOf(DateUtil.getDay(date));
        String month = String.valueOf(DateUtil.getMonth(date))+1;
        String year = String.valueOf(DateUtil.getYear(date));
        String hour=String.valueOf(DateUtil.getHour(date));
        String minute=String.valueOf(DateUtil.getMinute(date));
        String num = year + month + day + "_" + hour+minute;
        return num;
    }
}
