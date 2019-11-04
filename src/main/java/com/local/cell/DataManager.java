package com.local.cell;

import com.local.entity.sys.*;
import com.local.model.ApproalModel;
import com.local.model.RankModel;
import com.local.model.ReimbursementModel;
import com.local.service.*;
import com.local.util.DateUtil;
import com.local.util.EntityUtil;
import com.local.util.ExcelFileGenerator;
import com.local.util.StrUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.Rank;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.lang.random.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DataManager {
    private final static Logger logger = LoggerFactory.getLogger(PeopleManager.class);

    public static List<RankModel> filingList(UnitService unitService, String unitName, HttpServletResponse response,
                                             PeopleService peopleService, RankService rankService) throws Exception {
        SYS_UNIT unit = unitService.selectUnitByName(unitName);
        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0");
        List<RankModel> rankModels = new ArrayList<>();
        if (peoples != null) {
            int order = 0;
            for (SYS_People people : peoples) {
                SYS_Rank ranks = rankService.selectNotAproRanksByPid(people.getId());
                if (ranks != null) {
                    order += 1;
                    SYS_Rank rank = rankService.selectAprodRanksByPid(people.getId());
                    RankModel rankModel = new RankModel();
                    rankModel.setName(people.getName());
                    rankModel.setIdcard(people.getIdcard());
                    rankModel.setSex(people.getSex());
                    rankModel.setNationality(people.getNationality());
                    rankModel.setBirthday(DateUtil.parseDateYMD(people.getBirthday()));
                    rankModel.setEducation(people.getEducation());
                    rankModel.setWorkday(DateUtil.parseDateYMD(people.getWorkday()));
                    rankModel.setDemocracy(ranks.getDemocracy());
                    if (rank != null) {
                        if (rank.getCreateTime() != null) {
                            rankModel.setNowRank(rank.getName() + "\n" + DateUtil.dateToString(rank.getCreateTime()));
                        } else {
                            rankModel.setNowRank(rank.getName());
                        }
                    }
                    rankModel.setNewRank(ranks.getName());
                    if (ranks.getCreateTime() != null) {
                        rankModel.setNewRankTime(DateUtil.parseDateYMD(ranks.getCreateTime()));
                    }
                    rankModel.setDetail(ranks.getDetail());
                    rankModel.setOrder(order);
                    rankModels.add(rankModel);
                }
            }
            if (rankModels.size() > 0) {
                String[] arr = {"order", "name", "idcard", "sex", "nationality", "birthday", "education", "workday", "democracy",
                        "nowRank", "newRank", "newRankTime", "detail"};
                ClassPathResource resource = new ClassPathResource("exportExcel/filingListExport.xls");
                String path = resource.getFile().getPath();
                String[] arr1 = new String[]{unit.getName(), unit.getContactNumber(), unit.getContact()};
                Workbook temp = ExcelFileGenerator.getTeplet(path);
                ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
                excelFileGenerator.setExcleNAME(response, "公务员晋升职级人员备案名册.xls");
                String name = unit.getName() + "公务员晋升职级人员备案名册";
                excelFileGenerator.createTitleExcel(temp.getSheet("备案名册"), name);
                excelFileGenerator.createExcelFileFixedRow(temp.getSheet("备案名册"), 1, new int[]{2, 6, 11}, arr1);
                excelFileGenerator.createExcelFile(temp.getSheet("备案名册"), 6, rankModels, arr);
                excelFileGenerator.createExcelFileFixedMergeRow(temp.getSheet("备案名册"), rankModels.size() + 5, new int[]{0}, new String[]{"填表要求：备注需注明军转干部、实名制管理干部、领导职务干部"}, rankModels.size() + 5, rankModels.size() + 5, 0, 12);
                temp.write(response.getOutputStream());
                temp.close();
                return rankModels;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static ReimbursementModel exportPeopleData(HttpServletResponse response, PeopleService peopleService, String peopleId,
                                                      RankService rankService, EducationService educationService,
                                                      AssessmentService assessmentService, UnitService unitService) throws Exception {
        SYS_People people = peopleService.selectPeopleById(peopleId);
        if (people != null) {
            ReimbursementModel reimbursementModel = new ReimbursementModel();
            reimbursementModel.setName(people.getName());
            reimbursementModel.setSex(people.getSex());
            int startYear = DateUtil.getYear(people.getBirthday());
            int endYear = DateUtil.getYear(new Date());
            int startMonth = DateUtil.getMonth(people.getBirthday());
            int endMonth = DateUtil.getMonth(new Date());
            int year = 0;
            if (endMonth > startMonth) {
                year = endYear - startYear;
            } else {
                year = endYear - startYear - 1;
            }
            String years = DateUtil.dateToString(people.getBirthday()) + "\n(" + year + ")";
            reimbursementModel.setYears(years);
            reimbursementModel.setBirthplace(people.getBirthplace());
            reimbursementModel.setNationality(people.getNationality());
            reimbursementModel.setParty(people.getParty());
            SYS_Rank rank = rankService.selectAprodRanksByPid(peopleId);
            SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
            if (rank != null) {
                String unitAndDuty = unit.getName() + rank.getName();
                reimbursementModel.setUnitAndDuty(unitAndDuty);
                reimbursementModel.setWorkday(DateUtil.dateToString(DateUtil.parseDateYMD(people.getWorkday())));
                reimbursementModel.setDutyAndRank(rank.getName());
                reimbursementModel.setDutyAndRankTime(DateUtil.dateToString(rank.getCreateTime()));
                reimbursementModel.setDeposeRank(rank.getName());
            }
            SYS_Education education = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "全日制教育");
            SYS_Education education1 = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "在职教育");
            if (education != null) {
                reimbursementModel.setFullTimeEducation(education.getName());
                reimbursementModel.setFullTimeSchool(education.getSchool() + "\n" + education.getProfession());
            }
            if (education1 != null) {
                reimbursementModel.setWorkEducation(education1.getName());
                reimbursementModel.setWorkSchool(education1.getSchool() + "\n" + education1.getProfession());
            }
            List<SYS_Assessment> assessment = assessmentService.selectKaoHeByPidAndResult(peopleId, "优秀");
            if (assessment != null) {
                reimbursementModel.setSuperYears(String.valueOf(assessment.size()));
            } else {
                reimbursementModel.setSuperYears("0");
            }
            List<SYS_Assessment> assessment1 = assessmentService.selectKaoHeByPidAndResult(peopleId, "优秀");
            if (assessment != null) {
                reimbursementModel.setSuperYears(String.valueOf(assessment.size()));
            } else {
                reimbursementModel.setSuperYears("0");
            }
            SYS_Rank rank1 = rankService.selectNotAproRanksByPid(peopleId);
            if (rank1 != null) {
                reimbursementModel.setIntendedRank(rank1.getName());
            }
            ClassPathResource resource = new ClassPathResource("exportExcel/intendeAndDepose.xls");
            String path = resource.getFile().getPath();
            Workbook temp = ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response, "公务员职级任免审批表.xls");
            excelFileGenerator.createReimbursementExcel(temp.getSheet("任免审批表"), reimbursementModel);
            temp.write(response.getOutputStream());
            temp.close();
            return reimbursementModel;
        } else {
            return null;
        }
    }

    public static ApproalModel approvalExport(UnitService unitService, String unitName, HttpServletResponse response,
                                              PeopleService peopleService, RankService rankService) throws Exception {
        SYS_UNIT unit = unitService.selectUnitByName(unitName);
        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0");
        ApproalModel approalModel = new ApproalModel();
        approalModel.setUnitName(unitName);
        approalModel.setUnitType("");
        approalModel.setLevel(unit.getLevel());
        if (unit.getOfficialNum() > 0) {
            approalModel.setOfficialNum(String.valueOf(unit.getOfficialNum()));
        }
        int researcherTotal = 0;
        if (unit.getOneTowResearcherNum() > 0) {
            approalModel.setOneTowResearcherNum(String.valueOf(unit.getOneTowResearcherNum()));
            researcherTotal += unit.getOneTowResearcherNum();
        }
        if (unit.getThreeFourResearcherNum() > 0) {
            approalModel.setThreeFourResearcherNum(String.valueOf(unit.getThreeFourResearcherNum()));
            researcherTotal += unit.getThreeFourResearcherNum();
        }
        approalModel.setResearcherTotal(String.valueOf(researcherTotal));
        if (unit.getOneResearcherNum() > 0) {
            approalModel.setOneResearcherNum(String.valueOf(unit.getOneResearcherNum()));
        }
        if (unit.getTowResearcherNum() > 0) {
            approalModel.setTowResearcherNum(String.valueOf(unit.getTowResearcherNum()));
        }
        if (unit.getThreeResearcherNum() > 0) {
            approalModel.setThreeResearcherNum(String.valueOf(unit.getThreeResearcherNum()));
        }
        if (unit.getFourResearcherNum() > 0) {
            approalModel.setFourResearcherNum(String.valueOf(unit.getFourResearcherNum()));
        }
        int clerkTotal = 0;
        if (unit.getOneTowClerkNum() > 0) {
            approalModel.setOneTowClerkNum(String.valueOf(unit.getOneTowClerkNum()));
            clerkTotal += unit.getOneTowClerkNum();
        }
        if (unit.getThreeFourClerkNum() > 0) {
            approalModel.setThreeFourClerkNum(String.valueOf(unit.getThreeFourClerkNum()));
            clerkTotal += unit.getThreeFourClerkNum();
        }
        approalModel.setClerkTotal(String.valueOf(clerkTotal));
        if (unit.getOneClerkNum() > 0) {
            approalModel.setOneClerkNum(String.valueOf(unit.getOneClerkNum()));
        }
        if (unit.getTowClerkNum() > 0) {
            approalModel.setTowClerkNum(String.valueOf(unit.getTowClerkNum()));
        }
        if (unit.getThreeClerkNum() > 0) {
            approalModel.setThreeClerkNum(String.valueOf(unit.getThreeClerkNum()));
        }
        if (unit.getFourClerkNum() > 0) {
            approalModel.setFourClerkNum(String.valueOf(unit.getFourClerkNum()));
        }
        int oneClerkUserNum = 0;//一级主任科员职数使用
        int towClerkUserNum = 0;//二级主任科员职数使用
        int threeClerkUserNum = 0;//三级主任科员职数使用
        int fourClerkUserNum = 0;//四级主任科员职数使用
        int userTotal = 0;//使用合计
        for (SYS_People people : peoples) {
            SYS_Rank rank = rankService.selectAprodRanksByPid(people.getId());
            if (rank != null) {
                if ("一级主任科员".equals(rank.getName())) {
                    oneClerkUserNum += 1;
                } else if ("二级主任科员".equals(rank.getName())) {
                    towClerkUserNum += 1;
                } else if ("三级主任科员".equals(rank.getName())) {
                    threeClerkUserNum += 1;
                } else if ("四级主任科员".equals(rank.getName())) {
                    fourClerkUserNum += 1;
                }
            }
        }
        userTotal = oneClerkUserNum + towClerkUserNum + threeClerkUserNum + fourClerkUserNum;
        approalModel.setUserTotal(String.valueOf(userTotal));
        if (oneClerkUserNum > 0) {
            approalModel.setOneClerkUserNum(String.valueOf(oneClerkUserNum));
        }
        if (towClerkUserNum > 0) {
            approalModel.setTowClerkUserNum(String.valueOf(towClerkUserNum));
        }
        if (threeClerkUserNum > 0) {
            approalModel.setThreeClerkUserNum(String.valueOf(threeClerkUserNum));
        }
        if (fourClerkUserNum > 0) {
            approalModel.setFourClerkUserNum(String.valueOf(fourClerkUserNum));
        }

        int vacancyTotal = 0;
        if (unit.getOneClerkNum() > oneClerkUserNum) {
            approalModel.setOneClerkVacancyNum(String.valueOf(unit.getOneClerkNum() - oneClerkUserNum));
            vacancyTotal += unit.getOneClerkNum() - oneClerkUserNum;
        }
        if (unit.getTowClerkNum() > towClerkUserNum) {
            approalModel.setTowClerkVacancyNum(String.valueOf(unit.getTowClerkNum() - towClerkUserNum));
            vacancyTotal += unit.getTowClerkNum() - towClerkUserNum;
        }
        if (unit.getThreeClerkNum() > threeClerkUserNum) {
            approalModel.setThreeClerkVacancyNum(String.valueOf(unit.getThreeClerkNum() - threeClerkUserNum));
            vacancyTotal += unit.getThreeClerkNum() - threeClerkUserNum;
        }
        if (unit.getFourClerkNum() > fourClerkUserNum) {
            approalModel.setFourClerkVacancyNum(String.valueOf(unit.getFourClerkNum() - fourClerkUserNum));
            vacancyTotal += unit.getFourClerkNum() - fourClerkUserNum;
        }
        approalModel.setVacancyTotal(String.valueOf(vacancyTotal));
        ClassPathResource resource = new ClassPathResource("exportExcel/approveRank.xls");
        String path = resource.getFile().getPath();
        Workbook temp = ExcelFileGenerator.getTeplet(path);
        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
        excelFileGenerator.setExcleNAME(response, "公务员职级职数使用审批表.xls");
        excelFileGenerator.createApprovalExcel(temp.getSheet("职数使用审批表"), approalModel);
        temp.write(response.getOutputStream());
        temp.close();
        return approalModel;
    }

    public static List<SYS_People> getPeopleDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                        UnitService unitService, String fullImport, EducationService educationService, DutyService dutyService,
                                                        RankService rankService,RewardService rewardService) throws Exception {
        List<SYS_People> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_People people = savePeopleExcle(map, unit, service, fullImport, stringBuffer, list, peopleList);
                if (people != null) {
                    saveEducationDataByExcel(map, unit, educationService, fullImport, stringBuffer, list, people);
                    saveDutyDataByExcel(map, list, people, stringBuffer, unitService, fullImport, dutyService);
                    getPeopleRankDataByExcel(map, list, people, stringBuffer, unitService, fullImport, rankService);
                    getPeopleRewardDataByExcel(map, list,people,stringBuffer,unitService,fullImport,rewardService);
                }
            } else {
                logger.error("人员表：第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("人员表：第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }

    /**
     * 人员基本信息
     *
     * @param map
     * @param unit
     * @param service
     * @param fullImport
     * @param stringBuffer
     * @param list
     * @param peopleList
     * @return
     */
    public static SYS_People savePeopleExcle(Map<String, Object> map, SYS_UNIT unit, PeopleService service, String fullImport, StringBuffer stringBuffer,
                                             List<Map<String, Object>> list, List<SYS_People> peopleList) {
        try {
            SYS_People people = new SYS_People();
            people.setUnitId(unit.getId());
            people.setUnitName(unit.getName());
            if (!StrUtils.isBlank(map.get("姓名"))) {
                people.setName(map.get("姓名").toString());
                if (!StrUtils.isBlank(map.get("身份证号"))) {
                    people.setIdcard(map.get("身份证号").toString());
                    String birthdayStr = String.valueOf(map.get("出生年月"));
                    if (!StrUtils.isBlank(birthdayStr)) {
                        people.setBirthday(DateUtil.stringToDate(birthdayStr));
                    }
                    people.setSex(StrUtils.toNullStr(map.get("性别")).trim());
                    people.setBirthplace(StrUtils.toNullStr(map.get("籍贯")));
                    people.setNationality(StrUtils.toNullStr(map.get("民族 ")));
                    String workdayStr = String.valueOf(map.get("参加工作时间"));
                    if (!StrUtils.isBlank(workdayStr)) {
                        people.setWorkday(DateUtil.stringToDate(workdayStr));
                    }
                    people.setParty(StrUtils.toNullStr(map.get("政治面貌")));
                    String partyTimeStr = String.valueOf(map.get("入党时间"));
                    if (!StrUtils.isBlank(partyTimeStr)) {
                        people.setPartyTime(DateUtil.stringToDate(partyTimeStr));
                    }
                    people.setSecondParty(StrUtils.toNullStr(map.get("第二党派")));
                    people.setThirdParty(StrUtils.toNullStr(map.get("第三党派")));
                    people.setPosition(StrUtils.toNullStr(map.get("现任职务")));
                    String positionTimeStr = String.valueOf(map.get("现任职务时间"));
                    if (!StrUtils.isBlank(positionTimeStr)) {
                        people.setPositionTime(DateUtil.stringToDate(positionTimeStr));
                    }
                    people.setPositionLevel(String.valueOf(map.get("现任职级")));
                    String positionLevelTimeStr = String.valueOf(map.get("现任职级时间"));
                    if (!StrUtils.isBlank(positionLevelTimeStr)) {
                        people.setPositionLevelTime(DateUtil.stringToDate(positionLevelTimeStr));
                    }
                    people.setBaseWorker(StrUtils.toNullStr(map.get("是否具有两年以上基层工作经历")));
                    people.setPoliticalStatus(StrUtils.toNullStr(map.get("编制类型")));
                    people.setEducation(StrUtils.toNullStr(map.get("学历名称")));
                    String createTimeStr = String.valueOf(map.get("公务员登记时间"));
                    if (!StrUtils.isBlank(createTimeStr)) {
                        people.setPositionLevelTime(DateUtil.stringToDate(createTimeStr));
                    }
                    people.setTurnRank(StrUtils.toNullStr(map.get("套转职级")));
                    String turnRankTime = String.valueOf(map.get("套转职级时间"));
                    if (!StrUtils.isBlank(turnRankTime)) {
                        people.setTurnRankTime(DateUtil.stringToDate(turnRankTime));
                    }
                    String enableStr = StrUtils.toNullStr(map.get("现职"));
                    if ("否".equals(enableStr)) {
                        people.setEnabled("1");
                    } else {
                        people.setEnabled("0");
                    }
                    people.setDetail(StrUtils.toNullStr(map.get("特殊人员")));
                    SYS_People people1 = service.selectPeopleByIdcardAndUnitId(people.getIdcard(), unit.getId());
                    if (people1 != null) {
                        if ("1".equals(fullImport)) {
                            people.setId(people1.getId());
                            people.setPeopleOrder(people1.getPeopleOrder());
                            service.updatePeople(people);
                        } else {
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
            return people;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 学历导入
     *
     * @param list
     * @param stringBuffer
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static SYS_Education saveEducationDataByExcel(Map<String, Object> map, SYS_UNIT unit, EducationService educationService, String fullImport, StringBuffer stringBuffer,
                                                         List<Map<String, Object>> list, SYS_People people) throws Exception {
        SYS_Education education = new SYS_Education();
        education.setPeopleId(people.getId());
        education.setName(StrUtils.toNullStr(map.get("学历名称")));
        String creatTime = String.valueOf(map.get("入学时间"));
        if (!StrUtils.isBlank(creatTime)) {
            education.setCreateTime(DateUtil.stringToDate(creatTime));
        }
        education.setDegree(StrUtils.toNullStr(map.get("学位名称")));
        String serveTime = String.valueOf(map.get("毕（肄）业时间"));
        if (!StrUtils.isBlank(serveTime)) {
            education.setEndTime(DateUtil.stringToDate(serveTime));
        }
        String degreeTime = String.valueOf(map.get("学位授予时间"));
        if (!StrUtils.isBlank(degreeTime)) {
            education.setDegreeTime(DateUtil.stringToDate(degreeTime));
        }
        education.setSchoolType(StrUtils.toNullStr(map.get("教育类型")));
        education.setSchool(StrUtils.toNullStr(map.get("毕业学校")));
        education.setProfession(StrUtils.toNullStr(map.get("专业")));
        SYS_Education education1 = educationService.selectEducationByNameAndTime(education.getName(), people.getId(), education.getCreateTime());
        if ("1".equals(fullImport)) {//覆盖导入
            if (education1 != null) {
                education.setId(education1.getId());
                educationService.updateEducation(education);
            } else {
                String uuid = UUID.randomUUID().toString();
                education.setId(uuid);
                educationService.insertEducation(education);
            }
        } else {
            if (education1 != null) {
                stringBuffer.append("学历表：第" + list.indexOf(map) + "行;该学历已存在，请勿重复导入！");
                logger.error("学历表：第" + list.indexOf(map) + "行;该学历已存在，请勿重复导入！");
            } else {
                String uuid = UUID.randomUUID().toString();
                education.setId(uuid);
                educationService.insertEducation(education);
            }
        }
        return education;
    }

    /**
     * 职务导入
     *
     * @param list
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static SYS_Duty saveDutyDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                               UnitService unitService, String fullImport, DutyService dutyService) throws Exception {
        SYS_Duty duty = new SYS_Duty();
        duty.setPeopleId(people.getId());
        String creatTime = String.valueOf(map.get("现任职务时间"));
        if (!StrUtils.isBlank(creatTime)) {
            duty.setCreateTime(DateUtil.stringToDate(creatTime));
        }
        duty.setLeaderType(StrUtils.toNullStr(map.get("成员类别")));
        duty.setLeader(StrUtils.toNullStr(map.get("是否领导")));
        duty.setName(StrUtils.toNullStr(map.get("现任职务")));
        duty.setSelectionMethod(StrUtils.toNullStr(map.get("选拔任用方式")));
        duty.setStatus(StrUtils.toNullStr(map.get("任职状态")));
        duty.setDjunct(StrUtils.toNullStr(map.get("是否兼任")));
        duty.setDocumentduty(StrUtils.toNullStr(map.get("任职文号")));
        duty.setRealName(StrUtils.toNullStr(map.get("是否纳入实名制管理")));
        String serveTime = String.valueOf(map.get("免职时间"));
        if (!StrUtils.isBlank(serveTime)) {
            duty.setServeTime(DateUtil.stringToDate(serveTime));
        }
        duty.setDocumentNumber(StrUtils.toNullStr(map.get("免职文号")));
        SYS_Duty duty1 = dutyService.selectDutyByNameAndTime(duty.getName(), people.getId(), duty.getCreateTime());
        if ("1".equals(fullImport)) {//覆盖导入
            if (duty1 != null) {
                duty.setId(duty1.getId());
                dutyService.updateDuty(duty);
            } else {
                String uuid = UUID.randomUUID().toString();
                duty.setId(uuid);
                dutyService.insertDuty(duty);
            }
        } else {
            if (duty1 != null) {
                stringBuffer.append("职务表：第" + list.indexOf(map) + "行;该职务已存在，请勿重复导入！");
                logger.error("职务表：第" + list.indexOf(map) + "行;该职务已存在，请勿重复导入！");
            } else {
                String uuid = UUID.randomUUID().toString();
                duty.setId(uuid);
                dutyService.insertDuty(duty);
            }
        }
        return duty;
    }

    /**
     * 职级表导入
     *
     * @param list
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static SYS_Rank getPeopleRankDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                    UnitService unitService, String fullImport, RankService rankService) throws Exception {
        SYS_Rank rank = new SYS_Rank();
        rank.setPeopleId(people.getId());
        rank.setName(StrUtils.toNullStr(map.get("现任职级")));
        String creatTime = String.valueOf(map.get("现任职级时间"));
        if (!StrUtils.isBlank(creatTime)) {
            rank.setCreateTime(DateUtil.stringToDate(creatTime));
        }
        String dutyTime = String.valueOf(map.get("任同职务时间"));
        if (!StrUtils.isBlank(dutyTime)) {
            rank.setDutyTime(DateUtil.stringToDate(dutyTime));
        }
        rank.setRankType(StrUtils.toNullStr(map.get("类别（职级标志）")));
        rank.setStatus(StrUtils.toNullStr(map.get("状态")));
        rank.setBatch(StrUtils.toNullStr(map.get("批次")));
        rank.setDemocracy(StrUtils.toNullStr(map.get("民主测评结果")));
        String serveTime = String.valueOf(map.get("终止日期"));
        if (!StrUtils.isBlank(serveTime)) {
            rank.setServeTime(DateUtil.stringToDate(serveTime));
        }
        rank.setDocumentNumber(StrUtils.toNullStr(map.get("批准文号")));
        String approvalTime = String.valueOf(map.get("审批日期"));
        if (!StrUtils.isBlank(serveTime)) {
            rank.setApprovalTime(DateUtil.stringToDate(approvalTime));
        }
        SYS_Rank rank1 = rankService.selectRankByNameAndTime(rank.getName(), people.getId(), rank.getCreateTime());
        if ("1".equals(fullImport)) {//覆盖导入
            if (rank1 != null) {
                rank.setId(rank1.getId());
                rankService.updateRank(rank);
            } else {
                String uuid = UUID.randomUUID().toString();
                rank.setId(uuid);
                rankService.insertRank(rank);
            }
        } else {
            if (rank1 != null) {
                stringBuffer.append("职级表：第" + list.indexOf(map) + "行;该职级已存在，请勿重复导入！");
                logger.error("职级表：第" + list.indexOf(map) + "行;该职级已存在，请勿重复导入！");
            } else {
                String uuid = UUID.randomUUID().toString();
                rank.setId(uuid);
                rankService.insertRank(rank);
            }
        }
        return rank;
    }

    /**
     * 奖惩表导入
     *
     * @param list
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static SYS_Reward getPeopleRewardDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                              UnitService unitService, String fullImport, RewardService rewardService) throws Exception {
        SYS_Reward reward = new SYS_Reward();
        reward.setPeopleId(people.getId());
        reward.setName(StrUtils.toNullStr(map.get("奖惩名称")));
        String creatTime = String.valueOf(map.get("批准日期"));
        if (!StrUtils.isBlank(creatTime)) {
            reward.setCreateTime(DateUtil.stringToDate(creatTime));
        }
        reward.setNameType(StrUtils.toNullStr(map.get("奖惩名称代码")));
        String serveTime = String.valueOf(map.get("撤销日期"));
        if (!StrUtils.isBlank(serveTime)) {
            reward.setRevocationDate(DateUtil.stringToDate(serveTime));
        }
        reward.setApprovalUnit(StrUtils.toNullStr(map.get("批准机关")));
        reward.setApprovalUnit(StrUtils.toNullStr(map.get("受奖惩时职务层次")));
        reward.setApprovalUnit(StrUtils.toNullStr(map.get("批准机关性质")));
        SYS_Reward reward1 = rewardService.selectRewardByNameAndTime(reward.getName(), people.getId(), reward.getCreateTime());
        if ("1".equals(fullImport)) {//覆盖导入
            if (reward1 != null) {
                reward.setId(reward1.getId());
                rewardService.updateReward(reward);
            } else {
                String uuid = UUID.randomUUID().toString();
                reward.setId(uuid);
                rewardService.insertReward(reward);
            }
        } else {
            if (reward1 != null) {
                stringBuffer.append("奖惩表：第" + list.indexOf(map) + "行;该奖惩已存在，请勿重复导入！");
                logger.error("奖惩表：第" + list.indexOf(map) + "行;该奖惩已存在，请勿重复导入！");
            } else {
                String uuid = UUID.randomUUID().toString();
                reward.setId(uuid);
                rewardService.insertReward(reward);
            }
        }
        return reward;
    }

    /**
     * 单位数据封装
     * @param resultMap
     * @param unitId
     * @param unitService
     */

    public static List<SYS_UNIT> getUnitJson( Map<String, Object> resultMap,String unitId,UnitService unitService){
        SYS_UNIT unit = unitService.selectUnitById(unitId);
        List<SYS_UNIT> unitList = new ArrayList<>();
        unitList.add(unit);
        List<SYS_UNIT> cunitList  = unitService.selectAllChildUnits(unitId);
        unitList.addAll(cunitList);
        resultMap.put("unitId",unit.getId());
        resultMap.put("date", DateUtil.dateToString(new Date()));
        resultMap.put("unitName",unit.getName());
        JSONArray array = JSONArray.fromObject(unitList);
        resultMap.put("unitList",array);
        return unitList;
    }

    public static List<SYS_USER> getUserJson( Map<String, Object> resultMap,List<SYS_UNIT> units,UserService userService){
        List<SYS_USER> userList=new ArrayList<>();
        for (SYS_UNIT unit:units){
            List<SYS_USER> users=userService.selectUsersByUnitId(unit.getId());
            if (users!=null){
                userList.addAll(users);
            }
        }
        JSONArray array = JSONArray.fromObject(userList);
        resultMap.put("userList",array);
        return userList;
    }

    /**
     * 人员数据json
     * @param resultMap
     * @param units
     * @param peopleService
     */
    public static List<SYS_People> getPeopleJson( Map<String, Object> resultMap,List<SYS_UNIT> units,PeopleService peopleService){
        List<SYS_People> peopleList=new ArrayList<>();
        for (SYS_UNIT unit:units){
            List<SYS_People> peoples=peopleService.selectPeoplesByUnitId(unit.getId(),"0");
            if (peoples!=null){
                peopleList.addAll(peoples);
            }
        }
        JSONArray array = JSONArray.fromObject(peopleList);
        resultMap.put("peopleList",array);
        return peopleList;
    }

    /**
     * 上行职务数据json
     * @param resultMap
     * @param peoples
     * @param dutyService
     * @return
     */
    public static List<SYS_Duty> getDutyJson( Map<String, Object> resultMap,List<SYS_People> peoples,DutyService dutyService){
        List<SYS_Duty> dutyList=new ArrayList<>();
        for (SYS_People people:peoples){
            List<SYS_Duty> duties=dutyService.selectDutysByPeopleId(people.getId());
            if (duties !=null){
                dutyList.addAll(duties);
            }
        }
        JSONArray array = JSONArray.fromObject(dutyList);
        resultMap.put("dutyList",array);
        return dutyList;
    }

    /**
     * 职级数据json
     * @param resultMap
     * @param peoples
     * @param rankService
     * @return
     */
    public static List<SYS_Rank> getRankJson( Map<String, Object> resultMap,List<SYS_People> peoples,RankService rankService){
        List<SYS_Rank> rankList=new ArrayList<>();
        for (SYS_People people:peoples){
            List<SYS_Rank> ranks=rankService.selectRanksByPeopleId(people.getId());
            if (ranks !=null){
                rankList.addAll(ranks);
            }
        }
        JSONArray array = JSONArray.fromObject(rankList);
        resultMap.put("rankList",array);
        return rankList;
    }

    /**
     * 学历数据json
     * @param resultMap
     * @param peoples
     * @param educationService
     * @return
     */
    public static List<SYS_Education> getEducationJson( Map<String, Object> resultMap,List<SYS_People> peoples,EducationService educationService){
        List<SYS_Education> educationList=new ArrayList<>();
        for (SYS_People people:peoples){
            List<SYS_Education> educations=educationService.selectEducationsByPeopleId(people.getId());
            if (educations !=null){
                educationList.addAll(educations);
            }
        }
        JSONArray array = JSONArray.fromObject(educationList);
        resultMap.put("educationList",array);
        return educationList;
    }

    /**
     * 奖惩数据json
     * @param resultMap
     * @param peoples
     * @param rewardService
     * @return
     */
    public static List<SYS_Reward> getRewardJson( Map<String, Object> resultMap,List<SYS_People> peoples,RewardService rewardService){
        List<SYS_Reward> rewardList=new ArrayList<>();
        for (SYS_People people:peoples){
            List<SYS_Reward> rewards=rewardService.selectRewardsByPeopleId(people.getId());
            if (rewards !=null){
                rewardList.addAll(rewards);
            }
        }
        JSONArray array = JSONArray.fromObject(rewardList);
        resultMap.put("rewardList",array);
        return rewardList;
    }

    /**
     *
     * @param resultMap
     * @param peoples
     * @param assessmentService
     * @return
     */
    public static List<SYS_Assessment> getAssessmentJson( Map<String, Object> resultMap,List<SYS_People> peoples,AssessmentService assessmentService){
        List<SYS_Assessment> assessmentList=new ArrayList<>();
        for (SYS_People people:peoples){
            List<SYS_Assessment> assessments=assessmentService.selectAssessmentsByPeopleId(people.getId());
            if (assessments !=null){
                assessmentList.addAll(assessments);
            }
        }
        JSONArray array = JSONArray.fromObject(assessmentList);
        resultMap.put("assessmentList",array);
        return assessmentList;
    }

    /**
     * 获取单位json数据
     * @param unitList
     * @return
     */
    public static List<SYS_UNIT> saveUnitJsonModel(JSONArray unitList){
        List<SYS_UNIT> units=new ArrayList<>();
        for (int i=0;i<unitList.size();i++){
            SYS_UNIT unit=new SYS_UNIT();
            JSONObject key=(JSONObject) unitList.get(i);
            try {
                EntityUtil.setReflectModelValue(unit, key);
                unit.setReferOfficialDate(DateUtil.stringToDate(String.valueOf(key.get("referOfficialDateStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                units.add(unit);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return units;
    }
    public static List<SYS_USER> saveUserJsonModel(JSONArray userList){
        List<SYS_USER> users=new ArrayList<>();
        for (int i=0;i<userList.size();i++){
            SYS_USER user=new SYS_USER();
            JSONObject key=(JSONObject) userList.get(i);
            try {
                EntityUtil.setReflectModelValue(user, key);
                user.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                user.setLastTime(DateUtil.stringToDate(String.valueOf(key.get("lastTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                users.add(user);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return users;
    }
    /**
     * 获取人员数据json
     * @param peopleList
     * @return
     */
    public static List<SYS_People> savePeopleJsonModel(JSONArray peopleList){
        List<SYS_People> peoples=new ArrayList<>();
        for (int i=0;i<peopleList.size();i++){
            SYS_People people=new SYS_People();
            JSONObject key=(JSONObject) peopleList.get(i);
            try {
                EntityUtil.setReflectModelValue(people, key);
                people.setTurnRankTime(DateUtil.stringToDate(String.valueOf(key.get("turnRankTimeStr"))));
                people.setPositionTime(DateUtil.stringToDate(String.valueOf(key.get("positionTimeStr"))));
                people.setPartyTime(DateUtil.stringToDate(String.valueOf(key.get("partyTimeStr"))));
                people.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                people.setBirthday(DateUtil.stringToDate(String.valueOf(key.get("birthdayStr"))));
                people.setWorkday(DateUtil.stringToDate(String.valueOf(key.get("workdayStr"))));
                people.setPositionLevelTime(DateUtil.stringToDate(String.valueOf(key.get("positionLevelTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                peoples.add(people);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return peoples;
    }

    /**
     * 获取职务json数据
     * @param dutyList
     * @return
     */
    public static List<SYS_Duty> saveDutyJsonModel(JSONArray dutyList){
        List<SYS_Duty> duties=new ArrayList<>();
        for (int i=0;i<dutyList.size();i++){
            SYS_Duty duty=new SYS_Duty();
            JSONObject key=(JSONObject) dutyList.get(i);
            try {
                EntityUtil.setReflectModelValue(duty, key);
                duty.setServeTime(DateUtil.stringToDate(String.valueOf(key.get("serveTimeStr"))));
                duty.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                duties.add(duty);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return duties;
    }

    /**
     * 职级json数据
     * @param rankList
     * @return
     */
    public static List<SYS_Rank> saveRankJsonModel(JSONArray rankList){
        List<SYS_Rank> ranks=new ArrayList<>();
        for (int i=0;i<rankList.size();i++){
            SYS_Rank rank=new SYS_Rank();
            JSONObject key=(JSONObject) rankList.get(i);
            try {
                EntityUtil.setReflectModelValue(rank, key);
                rank.setServeTime(DateUtil.stringToDate(String.valueOf(key.get("serveTimeStr"))));
                rank.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                rank.setApprovalTime(DateUtil.stringToDate(String.valueOf(key.get("approvalTimeStr"))));
                rank.setDutyTime(DateUtil.stringToDate(String.valueOf(key.get("dutyTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                ranks.add(rank);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ranks;
    }

    /**
     * 学历json数据
     * @param educationList
     * @return
     */
    public static List<SYS_Education> saveEducationJsonModel(JSONArray educationList){
        List<SYS_Education> educations=new ArrayList<>();
        for (int i=0;i<educationList.size();i++){
            SYS_Education education=new SYS_Education();
            JSONObject key=(JSONObject) educationList.get(i);
            try {
                EntityUtil.setReflectModelValue(education, key);
                education.setEndTime(DateUtil.stringToDate(String.valueOf(key.get("endTimeStr"))));
                education.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                education.setDegreeTime(DateUtil.stringToDate(String.valueOf(key.get("degreeTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                educations.add(education);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return educations;
    }

    /**
     * 奖惩json数据
     * @param rewardList
     * @return
     */
    public static List<SYS_Reward> saveRewardJsonModel(JSONArray rewardList){
        List<SYS_Reward> rewards=new ArrayList<>();
        for (int i=0;i<rewardList.size();i++){
            SYS_Reward reward=new SYS_Reward();
            JSONObject key=(JSONObject) rewardList.get(i);
            try {
                EntityUtil.setReflectModelValue(reward, key);
                reward.setRevocationDate(DateUtil.stringToDate(String.valueOf(key.get("revocationDateStr"))));
                reward.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                rewards.add(reward);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rewards;
    }
    public static List<SYS_Assessment> saveAssessmentJsonModel(JSONArray assessmentList){
        List<SYS_Assessment> assessments=new ArrayList<>();
        for (int i=0;i<assessmentList.size();i++){
            SYS_Assessment assessment=new SYS_Assessment();
            JSONObject key=(JSONObject) assessmentList.get(i);
            try {
                EntityUtil.setReflectModelValue(assessment, key);
                assessments.add(assessment);
            }catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return assessments;
    }

    /**
     * 保存备份数据
     * @param dataId
     * @param dataType
     * @param unitId
     * @param dataService
     */
    public static SYS_Data saveData(String dataId,String dataType,String unitId,DataService dataService){
        SYS_Data data=new SYS_Data();
        data.setId(dataId);
        data.setType(dataType);
        data.setOpTime(new Date());
        data.setUnitId(unitId);
        data.setDelFlag("0");
        SYS_Data data1=dataService.selectDataById(dataId);
        if (data1!=null){
            dataService.updateData(data);
        }else {
            dataService.inserData(data);
        }
        return data;
    }

    /**
     * 数据
     * @param dataId
     * @param dataType
     * @param unitId
     * @param dataInfoService
     * @return
     */
    public static SYS_DataInfo saveDataInfo(String dataId,String dataType,String unitId,DataInfoService dataInfoService,String table,String param){
        SYS_DataInfo data=new SYS_DataInfo();
        String id=dataId+table;
        data.setId(id);
        data.setType(dataType);
        data.setOpTime(new Date());
        data.setUnitId(unitId);
        data.setDataId(dataId);
        data.setDelFlag("0");
        data.setParam(param);
        SYS_DataInfo data1=dataInfoService.selectDataInfById(id);
        if (data1!=null){
            dataInfoService.updateDataInfo(data);
        }else {
            dataInfoService.inserDataInfo(data);
        }
        return data;
    }
}
