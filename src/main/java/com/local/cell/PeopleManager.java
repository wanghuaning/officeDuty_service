package com.local.cell;

import com.local.entity.sys.*;
import com.local.model.AssessmentModel;
import com.local.service.*;
import com.local.util.DateUtil;
import com.local.util.EntityUtil;
import com.local.util.HanyuPinyinUtil;
import com.local.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
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
                        people.setIsEnable(enableStr);
                        people.setDetail(StrUtils.toNullStr(map.get("备注")));
                        people.setChineseEncoder(HanyuPinyinUtil.toHanyuPinyin(people.getName()));
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
                            rank.setDocumentNumber(StrUtils.toNullStr(map.get("批准文号")));
                            String approvalTime=String.valueOf(map.get("审批日期"));
                            if (!StrUtils.isBlank(approvalTime)){
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

//    public SYS_Rank saveTurnRank(String name, String createTime){
//        SYS_Rank rank=new SYS_Rank();
//        rank.setId();
//    }
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

    public static List<AssessmentModel> getAssessmentModel(List<SYS_Assessment> sys_assessmentList){
        List<AssessmentModel> assessmentModelList=new ArrayList<>();
        List<List<SYS_Assessment>> assessmentList= EntityUtil.createList(sys_assessmentList,3);
        for (List<SYS_Assessment> assessments:assessmentList){
            AssessmentModel model=new AssessmentModel();
            if (assessments.size()>0){
                model.setId(assessments.get(0).getId());
                model.setName(assessments.get(0).getName());
                model.setPeopleName(assessments.get(0).getPeopleName());
                model.setYear(String.valueOf(assessments.get(0).getYear()));
            }
            if (assessments.size()>1){
                model.setId1(assessments.get(1).getId());
                model.setName1(assessments.get(1).getName());
                model.setPeopleName1(assessments.get(1).getPeopleName());
                model.setYear1(String.valueOf(assessments.get(1).getYear()));
            }
            if (assessments.size()>2){
                model.setId2(assessments.get(2).getId());
                model.setName2(assessments.get(2).getName());
                model.setPeopleName2(assessments.get(2).getPeopleName());
                model.setYear2(String.valueOf(assessments.get(2).getYear()));
            }
            assessmentModelList.add(model);
        }
        return assessmentModelList;
    }
    public static List<SYS_People> getRetireInfoData(List<SYS_People> peopleList, String[] arr, String states,PeopleService peopleService,UnitService unitService) throws Exception {
        List<SYS_People> peoples = peopleService.selectPeoplesByUnitIds(arr, "在职");
        if (peoples != null) {
            for (SYS_People people : peoples) {
                if (people.getBirthday() != null) {
                    if (StrUtils.isBlank(people.getUnitName())) {
                        SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
                        if (unit != null) {
                            people.setUnitName(unit.getName());
                        }
                    }
                    int bmonth = DateUtil.getMonth(people.getBirthday());
                    int nmonth = DateUtil.getMonth(new Date());
                    int age = 0, age1 = 0, age2 = 0, age3 = 0;
                    //获取前月的最后一天
                    if ("1".equals(states) || "全部".equals(states)) {
                        Calendar ca = Calendar.getInstance();
                        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
                        age = DateUtil.getAgeByMonth(people.getBirthday(), ca);
                    }
                    if ("2".equals(states) || "全部".equals(states)) {
                        Calendar ca1 = Calendar.getInstance();
                        int month = ca1.get(Calendar.MONTH);
                        ca1.set(Calendar.MONTH, month + 1);
                        ca1.set(Calendar.DAY_OF_MONTH, ca1.getActualMaximum(Calendar.DAY_OF_MONTH));
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        age1 = DateUtil.getAgeByMonth(people.getBirthday(), ca1);
                    }
                    if ("3".equals(states) || "全部".equals(states)) {
                        Calendar ca2 = Calendar.getInstance();
                        int month2 = ca2.get(Calendar.MONTH);
                        ca2.set(Calendar.MONTH, month2 + 2);
                        ca2.set(Calendar.DAY_OF_MONTH, ca2.getActualMaximum(Calendar.DAY_OF_MONTH));
                        age2 = DateUtil.getAgeByMonth(people.getBirthday(), ca2);
                    }
                    if ("4".equals(states) || "全部".equals(states)) {
                        Calendar ca3 = Calendar.getInstance();
                        int month3 = ca3.get(Calendar.MONTH);
                        ca3.set(Calendar.MONTH, month3 + 3);
                        ca3.set(Calendar.DAY_OF_MONTH, ca3.getActualMaximum(Calendar.DAY_OF_MONTH));
                        age3 = DateUtil.getAgeByMonth(people.getBirthday(), ca3);
                    }
                    if (people.getSex().contains("男")) {
                        people.setRetireDate(DateUtil.addYears(people.getBirthday(), 60));
                        if (age == 60) {
                            peopleList.add(people);
                        }
                        if (age1 == 60 && bmonth == (nmonth + 1)) {
                            peopleList.add(people);
                        }
                        if (age2 == 60 && bmonth == (nmonth + 2)) {
                            peopleList.add(people);
                        }
                        if (age3 == 60 && bmonth == (nmonth + 3)) {
                            peopleList.add(people);
                        }
                    } else {
                        if (!StrUtils.isBlank(people.getPosition())){
                            if (people.getPosition().contains("县处级正职") || people.getPosition().contains("县处级副职")) {
                                people.setRetireDate(DateUtil.addYears(people.getBirthday(), 60));
                                if (age == 60) {
                                    peopleList.add(people);
                                }
                                if (age1 == 60 && bmonth == (nmonth + 1)) {
                                    peopleList.add(people);
                                }
                                if (age2 == 60 && bmonth == (nmonth + 2)) {
                                    peopleList.add(people);
                                }
                                if (age3 == 60 && bmonth == (nmonth + 3)) {
                                    peopleList.add(people);
                                }
                            } else if (people.getPosition().contains("乡科级正职") || people.getPosition().contains("乡科级副职") || people.getPosition().contains("科员")) {
                                people.setRetireDate(DateUtil.addYears(people.getBirthday(), 55));
                                if (age == 55 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                                if (age1 == 55 && bmonth == (nmonth + 1)) {
                                    peopleList.add(people);
                                }
                                if (age2 == 55 && bmonth == (nmonth + 2)) {
                                    peopleList.add(people);
                                }
                                if (age3 == 55 && bmonth == (nmonth + 3)) {
                                    peopleList.add(people);
                                }
                            } else {
                                people.setRetireDate(DateUtil.addYears(people.getBirthday(), 60));
                                if (age == 60 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                                if (age1 == 60 && bmonth == (nmonth + 1)) {
                                    peopleList.add(people);
                                }
                                if (age2 == 60 && bmonth == (nmonth + 2)) {
                                    peopleList.add(people);
                                }
                                if (age3 == 60 && bmonth == (nmonth + 3)) {
                                    peopleList.add(people);
                                }
                            }
                        }else if (!StrUtils.isBlank(people.getPositionLevel())){
                            if (people.getPositionLevel().contains("二级调研员") || people.getPositionLevel().contains("一级调研员") || people.getPositionLevel().contains("四级调研员") || people.getPositionLevel().contains("三级调研员")) {
                                if (age == 60) {
                                    peopleList.add(people);
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    peopleList.add(people);
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    peopleList.add(people);
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    peopleList.add(people);
                                }
                            } else if (people.getPositionLevel().contains("二级主任科员") || people.getPositionLevel().contains("一级主任科员") || people.getPositionLevel().contains("四级主任科员") || people.getPositionLevel().contains("三级主任科员") ||
                                    people.getPositionLevel().contains("一级科员") || people.getPositionLevel().contains("二级科员")) {
                                if (people.getSex().contains("男")) {
                                    if (age == 60 && bmonth == nmonth) {
                                        peopleList.add(people);
                                    }
                                    if (age1 == 60 && bmonth == (nmonth+1)) {
                                        peopleList.add(people);
                                    }
                                    if (age2 == 60 && bmonth == (nmonth+2)) {
                                        peopleList.add(people);
                                    }
                                    if (age3 == 60 && bmonth == (nmonth+3)) {
                                        peopleList.add(people);
                                    }
                                } else {
                                    if (age == 55 && bmonth == nmonth) {
                                        peopleList.add(people);
                                    }
                                    if (age1 == 55 && bmonth == (nmonth+1)) {
                                        peopleList.add(people);
                                    }
                                    if (age2 == 55 && bmonth == (nmonth+2)) {
                                        peopleList.add(people);
                                    }
                                    if (age3 == 55 && bmonth == (nmonth+3)) {
                                        peopleList.add(people);
                                    }
                                }
                            } else {
                                if (age == 60 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    peopleList.add(people);
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    peopleList.add(people);
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    peopleList.add(people);
                                }
                            }
                        }
                    }
                }
            }
        }
        return peopleList;
    }
    public static void savePeopleDetails(String createDate,String detail,String name,PeopleService peopleService,SYS_UNIT unit,SYS_UNIT oldunit,SYS_People people)throws Exception{
        SYS_Detail sys_detail=new SYS_Detail();
        sys_detail.setId(UUID.randomUUID().toString());
        Date createTime=new Date();
        if (!StrUtils.isBlank(createDate)){
            createTime=DateUtil.stringToDate(createDate);
        }
        sys_detail.setCreateDate(createTime);
        sys_detail.setFlag("0");
        sys_detail.setName(name);
        sys_detail.setUnitId(unit.getId());
        sys_detail.setPeopleId(people.getId());
        sys_detail.setUnitName(unit.getName());
        sys_detail.setDetail(detail);
        sys_detail.setOldUnitId(oldunit.getId());
        sys_detail.setOldUnitName(oldunit.getName());
        sys_detail.setPeopleName(people.getName());
        peopleService.insertPeopleDetail(sys_detail);
    }
}
