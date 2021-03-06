package com.local.cell;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.local.common.config.CompareFileds;
import com.local.controller.RankController;
import com.local.entity.sys.*;
import com.local.model.*;
import com.local.service.*;
import com.local.util.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.Rank;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.lang.random.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.unit.DataUnit;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;
import javax.xml.crypto.Data;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DataManager {
    private final static Logger logger = LoggerFactory.getLogger(PeopleManager.class);

    public static Sys_Process saveProcessData(Sys_Process process, UnitService unitService, SYS_UNIT unit, List<SYS_DataInfo> dataInfos,
                                              DataInfoService dataInfoService, Date approvalDate, RankService rankService, DutyService dutyService, PeopleService peopleService) {
        String beforeparam = null;
        List<String> peopleIds = gson.fromJson(process.getPeopleIds(), new TypeToken<String>() {
        }.getType());
        for (SYS_DataInfo dataInfo : dataInfos) {
            if (dataInfo.getTableName().contains("unit")) {
                beforeparam = gson.toJson(unit);
                dataInfo.setBeforeParam(beforeparam);
            } else if (dataInfo.getTableName().contains("people")) {
                if (peopleIds != null) {
                    List<SYS_People> peopleList = peopleService.selectPeoplesByPids(peopleIds);
                    beforeparam = gson.toJson(peopleList);
                    dataInfo.setBeforeParam(beforeparam);
                }
            } else if (dataInfo.getTableName().contains("rank")) {
                List<SYS_Rank> rankList = rankService.selectRanksByUnitId(unit.getId());
                beforeparam = gson.toJson(rankList);
                dataInfo.setBeforeParam(beforeparam);
            } else if (dataInfo.getTableName().contains("duty")) {
                List<SYS_Duty> dutyList = dutyService.selectDutysByUnitId(unit.getId());
                beforeparam = gson.toJson(dutyList);
                dataInfo.setBeforeParam(beforeparam);
            }
            dataInfoService.updateDataInfo(dataInfo);
        }
        if ("0".equals(process.getFlag())) {
            RegModel regModel = gson.fromJson(process.getParam(), new TypeToken<RegModel>() {
            }.getType());
            if (regModel != null) {
                List<String> peopleIdsL=regModel.getPeopleIds();
                List<RankModel> rankModels = regModel.getRankModels();
                if (rankModels.size() > 0) {
                    saveRankAndDutyData(rankModels, approvalDate, rankService, dutyService, peopleService,peopleIdsL);
                }
            }
        } else {
            Sys_Approal sys_approals = gson.fromJson(process.getParam(), new TypeToken<Sys_Approal>() {
            }.getType());
            if (sys_approals != null) {
                saveUnitData(unitService, unit, sys_approals);
            }
        }
        return process;
    }

    public static void rejectApprovalData(UnitService unitService, SYS_UNIT unit, List<SYS_DataInfo> dataInfos,
                                          RankService rankService, DutyService dutyService, PeopleService peopleService) {
        for (SYS_DataInfo dataInfo : dataInfos) {
            if (dataInfo.getTableName().contains("unit")) {
                if (dataInfo.getBeforeParam() != null) {
                    SYS_UNIT sys_unit = GsonUtil.gsonToBean(dataInfo.getBeforeParam(), SYS_UNIT.class);
                    if (sys_unit != null) {
                        unitService.updateUnit(sys_unit);
                    } else {
                        List<SYS_UNIT> sys_units = GsonUtil.gsonToList(dataInfo.getBeforeParam(), SYS_UNIT.class);
                        if (sys_units != null) {
                            SYS_UNIT sysUnit=new SYS_UNIT();
                            BeanUtils.copyProperties(sys_units.get(0),sysUnit);
                            unitService.updateUnit(sysUnit);
                        }
                    }
                }
            }

            if (dataInfo.getTableName().contains("people")) {
                List<SYS_People> peopleList = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_People>>() {
                }.getType());
                if (peopleList != null) {
                    for (SYS_People people : peopleList) {
                        SYS_People sys_people=new SYS_People();
                        BeanUtils.copyProperties(people,sys_people);
                        peopleService.updatePeople(sys_people);
                    }
                }
            }
            if (dataInfo.getId().contains("rank")) {
                List<SYS_Rank> rankList = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_Rank>>() {
                }.getType());
                if (rankList != null) {
                    for (SYS_Rank rank : rankList) {
                        SYS_Rank sys_rank=new SYS_Rank();
                        BeanUtils.copyProperties(rank,sys_rank);
                        rankService.updateRank(sys_rank);
                    }
                }
            }
            if (dataInfo.getTableName().contains("duty")) {
                List<SYS_Duty> dutyList = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_Duty>>() {
                }.getType());
                if (dutyList != null) {
                    for (SYS_Duty duty : dutyList) {
                        SYS_Duty sys_duty=new SYS_Duty();
                        BeanUtils.copyProperties(duty,sys_duty);
                        dutyService.updateDuty(sys_duty);
                    }
                }
            }
        }
    }

    public static void saveRankAndDutyData(List<RankModel> rankModels, Date approvalDate, RankService rankService, DutyService dutyService,
                                           PeopleService peopleService,List<String> peopleIds) {
        for (RankModel rankModel : rankModels) {
            List<SYS_Rank> rankList = rankService.selectRanksByPeopleId(rankModel.getPeopleId());
            List<SYS_Duty> dutyList = dutyService.selectDutysByPeopleId(rankModel.getPeopleId());
            SYS_People people = peopleService.selectPeopleById(rankModel.getPeopleId());
            if (rankList != null) {
                for (SYS_Rank rank : rankList) {
                    if (peopleIds.size()>0){
                        if (peopleIds.contains(rank.getPeopleId())){
                            if (rank.getStatus().contains("在任")) {
                                if (StrUtils.isBlank(rank.getApprovalTime())) {
                                    rank.setApprovalTime(approvalDate);
                                }
                            } else {
                                if (StrUtils.isBlank(rank.getApprovalTime())) {
                                    rank.setApprovalTime(approvalDate);
                                }
                                if (StrUtils.isBlank(rank.getServeApprovalTime())) {
                                    rank.setServeApprovalTime(approvalDate);
                                }
                            }
                            rankService.updateRank(rank);
                        }
                    }
                }
            }
            if (dutyList != null) {
                for (SYS_Duty duty : dutyList) {
                    if (duty.getStatus().contains("在任")) {
                        if (StrUtils.isBlank(duty.getApprovalTime())) {
                            duty.setApprovalTime(approvalDate);
                        }
                    } else {
                        if (StrUtils.isBlank(duty.getApprovalTime())) {
                            duty.setApprovalTime(approvalDate);
                        }
                        if (StrUtils.isBlank(duty.getServeApprovalTime())) {
                            duty.setServeApprovalTime(approvalDate);
                        }
                    }
                    dutyService.updateDuty(duty);
                }
            }
            if (people != null) {
                SYS_Rank rank = rankService.selectRankByPidOrderByTime(people.getId());
                SYS_Duty duty = dutyService.selectDutyByPidOrderByTime(people.getId());
                if (rank != null) {
                    if (rank.getStatus().contains("在任")) {
                        people.setPositionLevel(rank.getName());
                        people.setPositionLevelTime(rank.getCreateTime());
                    } else {
                        people.setPositionLevelTime(null);
                        people.setPositionLevel("");
                    }
                }
                if (duty != null) {
                    if (duty.getStatus().contains("在任")) {
                        people.setPosition(duty.getName());
                        people.setPositionTime(duty.getCreateTime());
                    } else {
                        people.setPositionTime(null);
                        people.setPosition("");
                    }
                }
                peopleService.updatePeople(people);
            }
        }
    }

    public static Sys_Approal getApprovalDataCell(Sys_Approal approalModel, SYS_UNIT unit, List<SYS_People> peoples, RankService rankService) {
        approalModel.setUnitId(unit.getId());
        approalModel.setUnitName(unit.getName());
        approalModel.setUnitType(unit.getUnitType());
        approalModel.setLevel(unit.getLevel());
        approalModel.setOfficialNum(String.valueOf(unit.getOfficialNum() + unit.getReferOfficialNum()));
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
        int oneResearcherUserNum = 0;//一级调研员职数使用
        int towResearcherUserNum = 0;//二级调研员职数使用
        int threeResearcherUserNum = 0;//三级调研员职数使用
        int fourResearcherUserNum = 0;//四级调研员职数使用
        int oneClerkUserNum = 0;//一级主任科员职数使用
        int towClerkUserNum = 0;//二级主任科员职数使用
        int threeClerkUserNum = 0;//三级主任科员职数使用
        int fourClerkUserNum = 0;//四级主任科员职数使用
        int userClerkTotal = 0, userResearcherTotal = 0;//使用合计
        int oneResearcherJunNum = 0, towResearcherJunNum = 0, threeResearcherJunNum = 0, fourResearcherJunNum = 0;//调研员军转
        int oneClerkJunNum = 0, towClerkJunNum = 0, threeClerkJunNum = 0, fourClerkJunNum = 0;//主任科员军转
        for (SYS_People people : peoples) {
            SYS_Rank rank = rankService.selectAprodRanksByPid(people.getId());
            if (rank != null) {
                if ("一级主任科员".equals(rank.getName())) {
                    oneClerkUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        oneClerkJunNum++;
                    }
                } else if ("二级主任科员".equals(rank.getName())) {
                    towClerkUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        towClerkJunNum++;
                    }
                } else if ("三级主任科员".equals(rank.getName())) {
                    threeClerkUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        threeClerkJunNum++;
                    }
                } else if ("四级主任科员".equals(rank.getName())) {
                    fourClerkUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        fourClerkJunNum++;
                    }
                } else if ("一级调研员".equals(rank.getName())) {
                    oneResearcherUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        oneResearcherJunNum++;
                    }
                } else if ("二级调研员".equals(rank.getName())) {
                    towResearcherUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        towResearcherJunNum++;
                    }
                } else if ("三级调研员".equals(rank.getName())) {
                    threeResearcherUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        threeResearcherJunNum++;
                    }
                } else if ("四级调研员".equals(rank.getName())) {
                    fourResearcherUserNum += 1;
                    if ("是".equals(people.getDetail())) {
                        fourResearcherJunNum++;
                    }
                }
            }
        }
        userClerkTotal = oneClerkUserNum + towClerkUserNum + threeClerkUserNum + fourClerkUserNum;
        approalModel.setUserTotal(String.valueOf(userClerkTotal));
        userResearcherTotal = oneResearcherUserNum + towResearcherUserNum + threeResearcherUserNum + fourResearcherUserNum;
        approalModel.setResearcherUserTotal(String.valueOf(userResearcherTotal));
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
        if (oneResearcherUserNum > 0) {
            approalModel.setOneResearcherUserNum(String.valueOf(oneResearcherUserNum));
        }
        if (towResearcherUserNum > 0) {
            approalModel.setTowResearcherUserNum(String.valueOf(towResearcherUserNum));
        }
        if (threeResearcherUserNum > 0) {
            approalModel.setThreeResearcherUserNum(String.valueOf(threeResearcherUserNum));
        }
        if (fourResearcherUserNum > 0) {
            approalModel.setFourResearcherUserNum(String.valueOf(fourResearcherUserNum));
        }
        approalModel.setOneClerkJunNum(String.valueOf(oneClerkJunNum));
        approalModel.setTowClerkJunNum(String.valueOf(towClerkJunNum));
        approalModel.setThreeClerkJunNum(String.valueOf(threeClerkJunNum));
        approalModel.setFourClerkJunNum(String.valueOf(fourClerkJunNum));
        approalModel.setOneResearcherJunNum(String.valueOf(oneResearcherJunNum));
        approalModel.setTowResearcherJunNum(String.valueOf(towResearcherJunNum));
        approalModel.setThreeResearcherJunNum(String.valueOf(threeResearcherJunNum));
        approalModel.setFourResearcherJunNum(String.valueOf(fourResearcherJunNum));
        //空缺数
        long oneClerkUserNums = unit.getOneClerkNum() - oneClerkUserNum + oneClerkJunNum;
        approalModel.setOneClerkVacancyNum(oneClerkUserNums + "");
        long towClerkUserNums = unit.getTowClerkNum() - towClerkUserNum + towClerkJunNum;
        approalModel.setTowClerkVacancyNum(towClerkUserNums + "");
        long threeClerkUserNums = unit.getThreeClerkNum() - threeClerkUserNum + threeClerkJunNum;
        approalModel.setThreeClerkVacancyNum(threeClerkUserNums + "");
        long fourClerkUserNums = unit.getFourClerkNum() - fourClerkUserNum + fourClerkJunNum;
        approalModel.setFourClerkVacancyNum(fourClerkUserNums + "");
        long vacancyTotal = oneClerkUserNums + towClerkUserNums + threeClerkUserNums + fourClerkUserNums;
        approalModel.setVacancyTotal(vacancyTotal + "");
        long oneResearcherUserNums = unit.getOneResearcherNum() - oneResearcherUserNum + oneResearcherJunNum;
        approalModel.setOneResearcherVacancyNum(oneResearcherUserNums + "");
        long towResearcherUserNums = unit.getTowResearcherNum() - towResearcherUserNum + towResearcherJunNum;
        approalModel.setTowResearcherVacancyNum(towResearcherUserNums + "");
        long threeResearcherUserNums = unit.getThreeResearcherNum() - threeResearcherUserNum + threeResearcherJunNum;
        approalModel.setThreeResearcherVacancyNum(threeResearcherUserNums + "");
        long fourResearcherUserNums = unit.getFourResearcherNum() - fourResearcherUserNum + fourResearcherJunNum;
        approalModel.setFourResearcherVacancyNum(fourResearcherUserNums + "");
        long vacancyResearcherTotal = oneResearcherUserNums + towResearcherUserNums + threeResearcherUserNums + fourResearcherUserNums;
        approalModel.setVacancyResearcherTotal(vacancyResearcherTotal + "");
        return approalModel;
    }

    public static List<RankModel> filingList(UnitService unitService, String unitName, HttpServletResponse response,
                                             PeopleService peopleService, RankService rankService, DutyService dutyService, AssessmentService assessmentService,
                                             RegModel model, ProcessService processService, String flag, EducationService educationService) throws Exception {
        List<RankModel> rankModels = new ArrayList<>();
        SYS_UNIT unit = unitService.selectUnitByName(unitName);
        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0", "在职");
        getRegDataInfoModel(unit, model, peopleService, rankService, dutyService, rankModels, assessmentService, educationService,peoples);
        if ("导出".equals(flag)) {
            return filingDataList(rankModels, response, unit, model);
        } else {
            setProcessDate(processService, "0", unit, "", gson.toJson(model), unitService);
        }
        return rankModels;
    }

    public static List<RankModel> filingExcleByProcess(UnitService unitService, HttpServletResponse response,
                                                       PeopleService peopleService, RankService rankService, DutyService dutyService, AssessmentService assessmentService,
                                                       RegModel model, EducationService educationService) throws Exception {
        List<RankModel> rankModels = model.getRankModels();
        SYS_UNIT unit = unitService.selectUnitByName(model.getUnitName());
//        getRegDataInfoModel(unit,model,peopleService, rankService,  dutyService,rankModels, assessmentService,educationService);
        return filingDataList(rankModels, response, unit, model);
    }

    public static void getRegDataInfoModel(SYS_UNIT unit, RegModel model, PeopleService peopleService, RankService rankService, DutyService dutyService,
                                           List<RankModel> rankModels, AssessmentService assessmentService, EducationService educationService,
                                           List<SYS_People> peoples) {
        model.setPeopleNums(Long.toString(unit.getOfficialNum() + unit.getReferOfficialNum()));//编制数
        model.setHdzhengke(Long.toString(unit.getMainHallNum()));//核定正科领导数
        model.setHdfuke(Long.toString(unit.getDeputyHallNum()));//核定副科领导数
        model.setUnitName(unit.getName());
        boolean hasNiRen=false;
        List<String> peopleIds = new ArrayList<>();
        if (peoples != null) {
            int order = 1;
            Long xianyouZhengke = 0L, xianyouFuke = 0L, xianyouGanbu = 0L, xianyouOne = 0L, xianyouTow = 0L;
            Long xianyouOneTowTurn = 0L, xianyouThree = 0L, xianyouFour = 0L, xianyouThreeFourTurn = 0L;
            Long nidingZhengke = 0L, nidingFuKe = 0L, nidingGanbu = 0L, nidingOne = 0L, nidingTow = 0L, nidingThree = 0L, nidingFour = 0L;
            Long jianRenZhengKeOne = 0L, jianRenFukeTow = 0L, jianRenFukeThree = 0L, mianZhengKe = 0L, mianFuKe = 0l, mianGanBu = 0l;
            Long zhengKeGaiTow = 0L, fukeGaiFour = 0L, mianOne = 0l, mianTow = 0l, mianThree = 0l, mianFour = 0l, mianOneTowTurn = 0l, mianThreeFourTurn = 0l;
            Long tiaozhengZhengKe = 0L, tiaozhengFuke = 0L, tiaozhengGanbu = 0L, tiaozhengOneTow = 0L, tiaozhengOne = 0L, tiaozhengTow = 0L, tiaozhengOneTowTurn = 0L, tiaozhengThreeFour = 0L, tiaozhengThree = 0L, tiaozhengFour = 0L, tiaozhengThreeFourTurn = 0L;
            for (SYS_People people : peoples) {
                SYS_Rank nowRank = rankService.selectNowRankByPidOrderByTime(people.getId());
                SYS_Duty nowduty = dutyService.selectNowDutyByPidOrderByTime(people.getId());
                if (nowduty != null) {
                    if ("乡科级正职".equals(nowduty.getName())) {
                        xianyouZhengke++;
                        if (people.getDetail() != null) {
                            if ("实名制管理领导干部".equals(people.getDetail())) {
                                xianyouGanbu++;
                            }
                        }
                    }
                    if ("乡科级副职".equals(nowduty.getName())) {
                        xianyouFuke++;
                        if (people.getDetail() != null) {
                            if ("实名制管理领导干部".equals(people.getDetail())) {
                                xianyouGanbu++;
                            }
                        }
                    }
                } else {
                    SYS_Rank mianRank = rankService.selectNotEnableRankByPidOrderByTime(people.getId());
                    if (mianRank != null) {
                        if (nowduty != null) {
                            if ("二级主任科员".equals(mianRank.getName()) && "乡科级正职".equals(nowduty.getName())) {
                                zhengKeGaiTow++;
                            }
                            if ("四级主任科员".equals(mianRank.getName()) && "乡科级副职".equals(nowduty.getName())) {
                                fukeGaiFour++;
                            }
                        }
                    }
                }
                if (nowRank != null) {
                    if ("一级主任科员".equals(nowRank.getName())) {
                        if ("是".equals(nowRank.getLeaders())) {
                            xianyouOneTowTurn++;
                        }
                        xianyouOne++;
                    }
                    if ("二级主任科员".equals(nowRank.getName())) {
                        if ("是".equals(nowRank.getLeaders())) {
                            xianyouOneTowTurn++;
                        }
                        xianyouTow++;
                    }

                    if ("三级主任科员".equals(nowRank.getName())) {
                        if ("是".equals(nowRank.getLeaders())) {
                            xianyouThreeFourTurn++;
                        }
                        xianyouThree++;
                    }
                    if ("四级主任科员".equals(nowRank.getName())) {
                        if ("是".equals(nowRank.getLeaders())) {
                            xianyouThreeFourTurn++;
                        }
                        xianyouFour++;

                    }
                }
                SYS_Duty niDuty = dutyService.selectNotProDutyByPidOrderByTime(people.getId());
                if (niDuty != null) {
                    if ("乡科级正职".equals(niDuty.getName())) {
                        nidingZhengke++;
                        if (people.getDetail() != null) {
                            if ("实名制管理领导干部".equals(people.getDetail())) {
                                nidingGanbu++;
                            }
                        }
                    }
                    if ("乡科级副职".equals(niDuty.getName())) {
                        nidingFuKe++;
                        if (people.getDetail() != null) {
                            if ("实名制管理领导干部".equals(people.getDetail())) {
                                nidingGanbu++;
                            }
                        }
                    }
                }
                SYS_Rank nirenrank = rankService.selectNotAproRanksByPid(people.getId());
                RankModel rankModel = new RankModel();
                rankModel.setName(people.getName());
                rankModel.setOrder(order);
                rankModel.setSex(people.getSex());
                rankModel.setIdcard(people.getIdcard());
                SYS_Education education = educationService.selectEducationByPidOrderByTime(people.getId());
                if (education != null) {
                    rankModel.setEducation(education.getName());
                } else {
                    rankModel.setEducation(people.getEducation());
                }
                rankModel.setBirthday(DateUtil.dateToString(people.getBirthday()));
                rankModel.setWorkday(DateUtil.dateToString(people.getWorkday()));
                if (nowduty != null) {
                    rankModel.setRenzhibumen(nowduty.getDutyType());
                    rankModel.setNowDuty(nowduty.getName());
                    rankModel.setTongzhiwudate(DateUtil.dateToString(nowduty.getDutyTime()));
                }
                rankModel.setJunzhuanganbu(people.getDetail());
                if (nirenrank != null) {//拟任
                    if ("一级主任科员".equals(nirenrank.getName())) {
                        nidingOne++;
                        if (nowduty != null) {
                            if ("乡科级正职".equals(nowduty.getName())) {
                                jianRenZhengKeOne++;
                            }
                        }
                    }
                    if ("二级主任科员".equals(nirenrank.getName())) {
                        nidingTow++;
                        if (nowduty != null) {
                            if ("乡科级副职".equals(nowduty.getName())) {
                                jianRenFukeTow++;
                            }
                        }
                    }
                    if ("三级主任科员".equals(nirenrank.getName())) {
                        nidingThree++;
                        if (nowduty != null) {
                            if ("乡科级副职".equals(nowduty.getName())) {
                                jianRenFukeThree++;
                            }
                        }
                    }
                    if ("四级主任科员".equals(nirenrank.getName())) {
                        nidingFour++;
                    }
                    //上报名册
                    order++;
                    int ayear = 2019;
                    peopleIds.add(people.getId());
                    if (nowRank != null) {
                        rankModel.setNowRank(nowRank.getName());
                        rankModel.setTongzhijiDate(DateUtil.dateToString(nowRank.getRankTime()));
                        ayear = DateUtil.getYear(nowRank.getCreateTime());
                    }
                    List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByNameAndTime("优秀", people.getId(), ayear);
                    if (assessments != null) {
                        StringBuffer sb = new StringBuffer();
                        for (SYS_Assessment assessment : assessments) {
                            sb.append(assessment.getYear() + ";");
                        }
                        if (sb.length() > 0) {
                            rankModel.setKaoheyouxiu(sb.toString());
                        }
                    }
                    if (niDuty != null) {
                        rankModel.setNirenbumen(niDuty.getDutyType());
                        rankModel.setNirenduty(niDuty.getName());
                        SYS_Duty niMianDuty = dutyService.selectNotEnableDutyByPidOrderByTime(people.getId());
                        if (niMianDuty != null) {
                            rankModel.setNimianduty(niMianDuty.getName());
                        }
                    }
                    rankModel.setNirenrank(nirenrank.getName());
                    SYS_Rank niMianRank = rankService.selectNotEnableRankByPidOrderByTime(people.getId());
                    if (niMianRank != null) {
                        rankModel.setNimianrank(niMianRank.getName());
                        if ("一级主任科员".equals(nowRank.getName())) {
                            if ("是".equals(niMianRank.getLeaders())) {
                                mianOneTowTurn++;
                            }
                            mianOne++;
                        }
                        if ("二级主任科员".equals(nowRank.getName())) {
                            if ("是".equals(niMianRank.getLeaders())) {
                                mianOneTowTurn++;
                            }
                            mianTow++;
                        }

                        if ("三级主任科员".equals(nowRank.getName())) {
                            if ("是".equals(niMianRank.getLeaders())) {
                                mianThreeFourTurn++;
                            }
                            mianThree++;
                        }
                        if ("四级主任科员".equals(nowRank.getName())) {
                            if ("是".equals(niMianRank.getLeaders())) {
                                mianThreeFourTurn++;
                            }
                            mianFour++;
                        }
                    }
                    SYS_Duty niMianDuty = dutyService.selectNotEnableDutyByPidOrderByTime(people.getId());
                    if (niMianDuty != null) {
                        rankModel.setNimianduty(niMianDuty.getName());
                        if ("乡科级正职".equals(niMianDuty.getName())) {
                            mianZhengKe++;
                        }
                        if ("乡科级副职".equals(niMianDuty.getName())) {
                            mianFuKe++;
                        }
                        if ("实名制管理领导干部".equals(people.getDetail())) {
                            mianGanBu++;
                        }
                    }
                    rankModel.setPeopleId(people.getId());
                    rankModel.setUnitId(unit.getId());
                    rankModels.add(rankModel);
                    hasNiRen=true;
                }else {
                    SYS_Rank niMianRank = rankService.selectNotEnableRankByPidEndTime(people.getId());
                    if (niMianRank!=null){
                        SYS_Duty duty=dutyService.selectNotProDutyByPidOrderByTime(people.getId());
                        if (duty!=null){
                            rankModel.setNimianrank(niMianRank.getName());
                            rankModel.setPeopleId(people.getId());
                            rankModel.setUnitId(unit.getId());
                            rankModel.setNirenbumen(niDuty.getDutyType());
                            rankModel.setNirenduty(niDuty.getName());
                            rankModels.add(rankModel);
                        }
                    }
                }
            }
            model.setXianyouzhengke(Long.toString(xianyouZhengke));//现有正科
            model.setXianyoufuke(Long.toString(xianyouFuke));//现有副科
            model.setXianyouganbu(Long.toString(xianyouGanbu));//现有实名制干部
            model.setHezhunoneTowClerkNum(Long.toString(unit.getOneTowClerkNum()));//核准职级职数一级、二级
            model.setHezhunthreeFourClerkNum(Long.toString(unit.getThreeFourClerkNum()));//核准职级职数三级、四级
            model.setXianyouoneTowClerkNum(Long.toString(xianyouOne + xianyouTow));//现有职级一级、二级合计
            model.setXianyouoneClerkNum(Long.toString(xianyouOne));//现有职级一级
            model.setXianyoutowClerkNum(Long.toString(xianyouTow));//现有职级二级
            model.setXianyouOneTowJunZhuanNum(Long.toString(xianyouOneTowTurn));//现有职级一级、二级军转
            model.setXianyouthreeFourClerkNum(Long.toString(xianyouThree + xianyouFour));//现有职级三级、四级合计
            model.setXianyouThreeClerkNum(Long.toString(xianyouThree));//现有职级三级
            model.setXianyouFourClerkNum(Long.toString(xianyouFour));//现有职级四级
            model.setXianyouThreeFourJunZhuanNum(Long.toString(xianyouThreeFourTurn));//现有职级三级、四级军转
            model.setNijinshengzhengke(Long.toString(nidingZhengke));//拟晋升正科
            model.setNijinshengfuke(Long.toString(nidingFuKe));//拟晋升副科
            model.setNijinshengganbu(Long.toString(nidingGanbu));//拟晋升干部
            model.setNijinshengoneClerkNum(Long.toString(nidingOne));//拟晋升一级主任科员
            model.setNijinshengtowClerkNum(Long.toString(nidingTow));//拟晋升二级主任科员
            model.setNijinshengThreeClerkNum(Long.toString(nidingThree));//拟晋升三级主任科员
            model.setNijinshengFourClerkNum(Long.toString(nidingFour));//拟晋升四级主任科员
            model.setNijinshengJianZhioneClerkNum(Long.toString(jianRenZhengKeOne));//拟晋升兼职一级主任科员
            model.setNijinshengJiantowClerkNum(Long.toString(jianRenFukeTow));//拟晋升兼职二级主任科员
            model.setNijinshengJianThreeClerkNum(Long.toString(jianRenFukeThree));//拟晋升兼职三级主任科员
            model.setZhengkeGaitowClerkNum(Long.toString(zhengKeGaiTow));//正科改任二级主任科员
            model.setFukeGaiFourClerkNum(Long.toString(fukeGaiFour));//副科改任四级主任科员
            model.setTiaozhengzhengke(Long.toString(xianyouZhengke + nidingZhengke - mianZhengKe));//调整后正科
            model.setTiaozhengfuke(Long.toString(xianyouFuke + nidingFuKe - mianFuKe));//调整后副科
            model.setTiaozhengganbu(Long.toString(xianyouGanbu + nidingGanbu - mianGanBu));//调整后干部
            model.setTiaozhengoneTowClerkNum(Long.toString(xianyouOne + nidingOne + jianRenZhengKeOne + xianyouTow + nidingTow + jianRenFukeTow - mianOne - mianTow));//调整后一级和二级主任科员合计
            model.setTiaozhengoneClerkNum(Long.toString(xianyouOne + nidingOne + jianRenZhengKeOne - mianOne));//调整后一级主任科员
            model.setTiaozhengtowClerkNum(Long.toString(xianyouTow + nidingTow + jianRenFukeTow - mianTow));//调整后二级主任科员
            model.setTiaozhengOneTowJunZhuanNum(Long.toString(xianyouOneTowTurn - mianOneTowTurn));//调整后一级和二级主任科员首次套转不占职数军转干部数
            model.setTiaozhenghreeFourClerkNum(Long.toString(xianyouThree + nidingThree + jianRenFukeThree + xianyouFour + nidingFour - mianThree - mianFour));//调整后三级和四级主任科员合计
            model.setTiaozhengThreeClerkNum(Long.toString(xianyouThree + nidingThree + jianRenFukeThree - mianThree));//调整后三级主任科员
            model.setTiaozhengFourClerkNum(Long.toString(xianyouFour + nidingFour - mianFour));//调整后四级主任科员
            model.setTiaozhengThreeFourJunZhuanNum(Long.toString(xianyouThreeFourTurn - mianThreeFourTurn));//调整后三级和四级主任科员首次套转不占职数军转干部数
            model.setContact(unit.getContact());
            model.setContactNumber(unit.getContactNumber());
            model.setNowDateStr(DateUtil.dateToString(new Date()));
        }

        String contact = "";
        if (model.getContact() != null) {
            contact = model.getContact();
        }
        String contactNum = "";
        if (model.getContactNumber() != null) {
            contactNum = model.getContactNumber();
        }
        String nowDate = "";
        if (model.getNowDateStr() != null) {
            nowDate = model.getNowDateStr();
        }
        model.setContact(contact);
        model.setContactNumber(contactNum);
        model.setNowDateStr(nowDate);
        model.setRankModels(rankModels);
        model.setPeopleIds(peopleIds);
        if (hasNiRen){
            model.setHasNiRen("是");
        }
        if (rankModels.size() > 0) {
            model.setPeopleNum(rankModels.size() + "");
            model.setPeopleName(rankModels.get(0).getName());
        }
    }

    private final static Gson gson = new Gson();

    public static Sys_Process saveRegProcess(SYS_UNIT unit, RegModel model, ProcessService processService, UnitService unitService) {
        Sys_Process process = processService.selectProcessByFlag(unit.getId(), "0");
        if (process != null) {
            SYS_UNIT sys_unit = unitService.selectUnitById(unit.getParentId());
            process.setCreateTime(new Date());
            process.setFlag("0");
            process.setParam(gson.toJson(model));
            processService.updateProcess(process);
        } else {
            process = new Sys_Process();
            process.setCreateTime(new Date());
            String uuid = UUID.randomUUID().toString();
            process.setId(uuid);
            process.setFlag("0");
            process.setUnitId(unit.getId());
            process.setUnitName(unit.getName());
            process.setStates("未审批");
            process.setParam(gson.toJson(model));
            processService.insertProcess(process);
        }
        return process;
    }

    public static RegModel getRegDataInfo(PeopleService peopleService, RankService rankService, DutyService dutyService,
                                          AssessmentService assessmentService, EducationService educationService,SYS_UNIT unit,List<SYS_People> peoples) throws Exception {
        List<RankModel> rankModels = new ArrayList<>();
        RegModel model = new RegModel();
        getRegDataInfoModel(unit, model, peopleService, rankService, dutyService, rankModels, assessmentService, educationService,peoples);
        return model;
    }

    public static List<RankModel> filingDataList(List<RankModel> rankModels, HttpServletResponse response, SYS_UNIT unit, RegModel model) throws Exception {
        if (rankModels.size() > 0) {
            String[] arr = {"order", "name", "sex", "education", "idcard", "workday", "renzhibumen", "nowDuty", "tongzhiwudate",
                    "nowRank", "tongzhijiDate", "nirenbumen", "nirenduty", "nirenrank", "nimianduty", "nimianrank", "kaoheyouxiu", "junzhuanganbu"};
            ClassPathResource resource = new ClassPathResource("exportExcel/filingListExport.xls");
            String path = resource.getFile().getPath();
            String[] arr1 = new String[]{unit.getName()};
            Workbook temp = ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response, "公务员晋升职级人员备案名册.xls");
//                String name = unit.getName() + "公务员晋升职级人员备案名册";
//                excelFileGenerator.createTitleExcel(temp.getSheet("备案名册"), name);
            excelFileGenerator.createExcelFileFixedRow(temp.getSheet("备案名册"), 2, new int[]{4}, arr1);
            excelFileGenerator.createRankApprovalExcelFile(temp.getSheet("备案名册"), 11, rankModels, arr);
            excelFileGenerator.createRegReimbursementExcel(temp.getSheet("备案名册"), model);
            excelFileGenerator.createExcelFileFixedMergeAreaRow(temp.getSheet("备案名册"), rankModels.size() + 11, new int[]{0}, new String[]{"说明"}, rankModels.size() + 11, rankModels.size() + 11, 0, 40, HorizontalAlignment.LEFT);
            excelFileGenerator.createExcelFileFixedMergeRow(temp.getSheet("备案名册"), rankModels.size() + 12, new int[]{0}, new String[]{"呈报单位意见:经" + model.getYear() + "年" + model.getMonth() + "月" + model.getDay() + "日党组（党委）会议研究决定，" + model.getPeopleName() + "等" + model.getPeopleNum() + "名同志职级晋升符合规定的资格条件，同意晋升。"}, rankModels.size() + 12, rankModels.size() + 13, 0, 19);
            excelFileGenerator.createExcelFileFixedMergeRow(temp.getSheet("备案名册"), rankModels.size() + 14, new int[]{0}, new String[]{"（签章）"}, rankModels.size() + 14, rankModels.size() + 14, 0, 19);
            excelFileGenerator.createExcelFileFixedMergeRow(temp.getSheet("备案名册"), rankModels.size() + 15, new int[]{0}, new String[]{" 年   月  日"}, rankModels.size() + 15, rankModels.size() + 15, 0, 19);
            excelFileGenerator.createExcelFileFixedMergeAreaRow(temp.getSheet("备案名册"), rankModels.size() + 12, new int[]{20}, new String[]{"公务员主管部门审核备案意见："}, rankModels.size() + 12, rankModels.size() + 13, 20, 40, HorizontalAlignment.LEFT);
            excelFileGenerator.createExcelFileFixedMergeRow(temp.getSheet("备案名册"), rankModels.size() + 14, new int[]{20}, new String[]{"（签章）"}, rankModels.size() + 14, rankModels.size() + 14, 20, 40);
            excelFileGenerator.createExcelFileFixedMergeRow(temp.getSheet("备案名册"), rankModels.size() + 15, new int[]{20}, new String[]{" 年   月  日"}, rankModels.size() + 15, rankModels.size() + 15, 20, 40);
            excelFileGenerator.createExcelFileFixedMergeAreaRow(temp.getSheet("备案名册"), rankModels.size() + 16, new int[]{0}, new String[]{"填表人：" + model.getContact()}, rankModels.size() + 16, rankModels.size() + 16, 0, 19, HorizontalAlignment.LEFT);
            excelFileGenerator.createExcelFileFixedMergeAreaRow(temp.getSheet("备案名册"), rankModels.size() + 16, new int[]{20}, new String[]{"联系电话：" + model.getContactNumber()}, rankModels.size() + 16, rankModels.size() + 16, 20, 32, HorizontalAlignment.LEFT);
            excelFileGenerator.createExcelFileFixedMergeAreaRow(temp.getSheet("备案名册"), rankModels.size() + 16, new int[]{33}, new String[]{"填表时间：" + model.getNowDateStr()}, rankModels.size() + 16, rankModels.size() + 16, 33, 40, HorizontalAlignment.LEFT);
            temp.write(response.getOutputStream());
            temp.close();
            return rankModels;
        } else {
            return null;
        }
    }

    public static ReimbursementModel exportFreePeople(HttpServletResponse response, PeopleService peopleService, String peopleId,
                                                      RankService rankService, EducationService educationService,
                                                      AssessmentService assessmentService, UnitService unitService) throws Exception {
        SYS_People people = peopleService.selectPeopleById(peopleId);
        if (people != null) {
            ReimbursementModel reimbursementModel = getRenMianShenPi(peopleService, peopleId,
                    rankService, educationService, assessmentService, unitService, people);
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

    public static ReimbursementModel exportDutyFreePeople(HttpServletResponse response, PeopleService peopleService, String peopleId,
                                                          DutyService dutyService, EducationService educationService,
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
            String years = startYear + "." + startMonth + "\n(" + year + ")";
            reimbursementModel.setYears(years);
            reimbursementModel.setBirthplace(people.getBirthplace());
            reimbursementModel.setNationality(people.getNationality());
            if ("中共党员".equals(people.getParty()) && people.getPartyTime() != null) {
                reimbursementModel.setParty(people.getParty());
                reimbursementModel.setPartyTime(DateUtil.dateToString(people.getPartyTime()));
            }
            if (people.getWorkday() != null) {
                reimbursementModel.setWorkday(DateUtil.dateToString(people.getWorkday()));
            }
            SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
            SYS_Duty nowDuty = dutyService.selectNowDutyByPidOrderByTime(peopleId);
            if (nowDuty != null) {
                reimbursementModel.setNowDuty(nowDuty.getName());
            }
            SYS_Duty niRenDuty = dutyService.selectNotProDutyByPidOrderByTime(peopleId);
            if (niRenDuty != null) {
                reimbursementModel.setNiRenDuty(niRenDuty.getName());
            }
            SYS_Duty niMianDuty = dutyService.selectNotEnableDutyByPidOrderByTime(peopleId);
            if (niMianDuty != null) {
                reimbursementModel.setNiMianDuty(niMianDuty.getName());
            }
            SYS_Education education = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "全日制教育");
            SYS_Education education1 = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "在职教育");
            if (education != null) {
                reimbursementModel.setFullTimeEducation(education.getName());
                reimbursementModel.setFullTimeSchool(StrUtils.toNullStr(education.getSchool()) + "\n" + StrUtils.toNullStr(education.getProfession()));
            }
            if (education1 != null) {
                reimbursementModel.setWorkEducation(education1.getName());
                reimbursementModel.setWorkSchool(education1.getSchool() + "\n" + education1.getProfession());
            }
            List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByPeopleId(peopleId);
            int youxiu = 0, hege = 0, buhege = 0;
            if (assessments != null) {
                for (SYS_Assessment assessment : assessments) {
                    if ("优秀".equals(assessment.getName()) && assessment.getYear() > 2018) {
                        youxiu++;
                    }
                    if ("不称职".equals(assessment.getName()) || "不合格".equals(assessment.getName())) {
                        buhege++;
                    }
                    if (!"优秀".equals(assessment.getName()) && !"不称职".equals(assessment.getName()) && "不合格".equals(assessment.getName())) {
                        hege++;
                    }
                }
            }
            if (youxiu > 0) {
                reimbursementModel.setSuperYears(String.valueOf(youxiu));
            } else {
                reimbursementModel.setSuperYears("");
            }
            if (hege > 0) {
                reimbursementModel.setCompetentYears(String.valueOf(hege));
            } else {
                reimbursementModel.setCompetentYears("");
            }
            if (buhege > 0) {
                reimbursementModel.setNotCompetentYears(String.valueOf(buhege));
            } else {
                reimbursementModel.setNotCompetentYears("");
            }
            ClassPathResource resource = new ClassPathResource("exportExcel/intendeDutyAndDepose.xls");
            String path = resource.getFile().getPath();
            Workbook temp = ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response, "公务员干部任免审批表.xls");
            excelFileGenerator.createDutyReimbursementExcel(temp.getSheet("干部任免审批表"), reimbursementModel);
            temp.write(response.getOutputStream());
            temp.close();
            return reimbursementModel;
        } else {
            return null;
        }
    }

    public static void exportDutyFreePeoples(Workbook temp, PeopleService peopleService, String peopleId,
                                             DutyService dutyService, EducationService educationService,
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
            String years = startYear + "." + startMonth + "\n(" + year + ")";
            reimbursementModel.setYears(years);
            reimbursementModel.setBirthplace(people.getBirthplace());
            reimbursementModel.setNationality(people.getNationality());
            if ("中共党员".equals(people.getParty()) && people.getPartyTime() != null) {
                reimbursementModel.setParty(people.getParty());
                reimbursementModel.setPartyTime(DateUtil.dateToString(people.getPartyTime()));
            }
            if (people.getWorkday() != null) {
                reimbursementModel.setWorkday(DateUtil.dateToString(people.getWorkday()));
            }
            SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
            SYS_Duty nowDuty = dutyService.selectNowDutyByPidOrderByTime(peopleId);
            if (nowDuty != null) {
                reimbursementModel.setNowDuty(nowDuty.getName());
            }
            SYS_Duty niRenDuty = dutyService.selectNotProDutyByPidOrderByTime(peopleId);
            if (niRenDuty != null) {
                reimbursementModel.setNiRenDuty(niRenDuty.getName());
            }
            SYS_Duty niMianDuty = dutyService.selectNotEnableDutyByPidOrderByTime(peopleId);
            if (niMianDuty != null) {
                reimbursementModel.setNiMianDuty(niMianDuty.getName());
            }
            SYS_Education education = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "全日制教育");
            SYS_Education education1 = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "在职教育");
            if (education != null) {
                reimbursementModel.setFullTimeEducation(education.getName());
                reimbursementModel.setFullTimeSchool(StrUtils.toNullStr(education.getSchool()) + "\n" + StrUtils.toNullStr(education.getProfession()));
            }
            if (education1 != null) {
                reimbursementModel.setWorkEducation(education1.getName());
                reimbursementModel.setWorkSchool(education1.getSchool() + "\n" + education1.getProfession());
            }
            List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByPeopleId(peopleId);
            int youxiu = 0, hege = 0, buhege = 0;
            if (assessments != null) {
                for (SYS_Assessment assessment : assessments) {
                    if ("优秀".equals(assessment.getName()) && assessment.getYear() > 2018) {
                        youxiu++;
                    }
                    if ("不称职".equals(assessment.getName()) || "不合格".equals(assessment.getName())) {
                        buhege++;
                    }
                    if (!"优秀".equals(assessment.getName()) && !"不称职".equals(assessment.getName()) && "不合格".equals(assessment.getName())) {
                        hege++;
                    }
                }
            }
            if (youxiu > 0) {
                reimbursementModel.setSuperYears(String.valueOf(youxiu));
            } else {
                reimbursementModel.setSuperYears("");
            }
            if (hege > 0) {
                reimbursementModel.setCompetentYears(String.valueOf(hege));
            } else {
                reimbursementModel.setCompetentYears("");
            }
            if (buhege > 0) {
                reimbursementModel.setNotCompetentYears(String.valueOf(buhege));
            } else {
                reimbursementModel.setNotCompetentYears("");
            }
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.createDutyReimbursementExcel(temp.getSheet("干部任免审批表"), reimbursementModel);
        }
    }

    public static void exportFreePeople(Workbook temp, HttpServletResponse response, PeopleService peopleService, String peopleId,
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
            List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByPeopleId(peopleId);
            int youxiu = 0, hege = 0, buhege = 0;
            if (assessments != null) {
                for (SYS_Assessment assessment : assessments) {
                    if ("优秀".equals(assessment.getName()) && assessment.getYear() > 2018) {
                        youxiu++;
                    }
                    if ("不称职".equals(assessment.getName()) || "不合格".equals(assessment.getName())) {
                        buhege++;
                    }
                    if (!"优秀".equals(assessment.getName()) && !"不称职".equals(assessment.getName()) && "不合格".equals(assessment.getName())) {
                        hege++;
                    }
                }
            }
            if (youxiu > 0) {
                reimbursementModel.setSuperYears(String.valueOf(youxiu));
            } else {
                reimbursementModel.setSuperYears("");
            }
            if (hege > 0) {
                reimbursementModel.setCompetentYears(String.valueOf(hege));
            } else {
                reimbursementModel.setCompetentYears("");
            }
            if (buhege > 0) {
                reimbursementModel.setNotCompetentYears(String.valueOf(buhege));
            } else {
                reimbursementModel.setNotCompetentYears("");
            }
            SYS_Rank rank1 = rankService.selectNotAproRanksByPid(peopleId);
            if (rank1 != null) {
                reimbursementModel.setIntendedRank(rank1.getName());
            }
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response, "公务员职级任免审批表.xls");
            excelFileGenerator.createReimbursementExcel(temp.getSheet("任免审批表"), reimbursementModel);
            temp.write(response.getOutputStream());
            temp.close();
        }
    }

    public static ReimbursementModel getRenMianShenPi(PeopleService peopleService, String peopleId,
                                                      RankService rankService, EducationService educationService,
                                                      AssessmentService assessmentService, UnitService unitService, SYS_People people) {
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
        String years = startYear + "." + startMonth + "\n(" + year + ")";
        reimbursementModel.setYears(years);
        reimbursementModel.setBirthplace(people.getBirthplace());
        reimbursementModel.setNationality(people.getNationality());
        reimbursementModel.setParty(people.getParty());
        SYS_Rank rank = rankService.selectAprodRanksByPid(peopleId);
        SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
        int youxiuYear = 2019;
        if (rank != null) {
            String unitAndDuty = unit.getName() + rank.getName();
            reimbursementModel.setUnitAndDuty(unitAndDuty);
            reimbursementModel.setWorkday(DateUtil.dateToString(DateUtil.parseDateYMD(people.getWorkday())));
            reimbursementModel.setDutyAndRank(rank.getName());
            reimbursementModel.setDutyAndRankTime(DateUtil.dateToString(rank.getCreateTime()));
            reimbursementModel.setDeposeRank(rank.getName());
            if ("否".equals(rank.getFlag())) {
                youxiuYear = DateUtil.getYear(rank.getCreateTime());
            }
        }
        SYS_Education education = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "全日制教育");
        SYS_Education education1 = educationService.selectEducationByPidAndSchoolOrderByTime(peopleId, "在职教育");
        if (education != null) {
            reimbursementModel.setFullTimeEducation(education.getName());
            reimbursementModel.setFullTimeSchool(StrUtils.toNullStr(education.getSchool()) + "\n" + StrUtils.toNullStr(education.getProfession()));
        }
        if (education1 != null) {
            reimbursementModel.setWorkEducation(education1.getName());
            reimbursementModel.setWorkSchool(education1.getSchool() + "\n" + education1.getProfession());
        }
        List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByPeopleId(peopleId);
        int youxiu = 0, hege = 0, buhege = 0;
        double convertYears = 0;
        if (assessments != null) {
            for (SYS_Assessment assessment : assessments) {
                if ("优秀".equals(assessment.getName()) && assessment.getYear() >= youxiuYear) {
                    youxiu++;
                    convertYears += 0.5;
                }
                if ("不称职".equals(assessment.getName()) || "不合格".equals(assessment.getName())) {
                    buhege++;
                }
                if (!"优秀".equals(assessment.getName()) && !"不称职".equals(assessment.getName()) && !"不合格".equals(assessment.getName())) {
                    hege++;
                }
            }
        }
        if (youxiu > 0) {
            reimbursementModel.setConvertYears(String.valueOf(convertYears));
            reimbursementModel.setSuperYears(String.valueOf(youxiu));
        } else {
            reimbursementModel.setSuperYears("");
        }
        if (hege > 0) {
            reimbursementModel.setCompetentYears(String.valueOf(hege));
        } else {
            reimbursementModel.setCompetentYears("");
        }
        if (buhege > 0) {
            reimbursementModel.setNotCompetentYears(String.valueOf(buhege));
        } else {
            reimbursementModel.setNotCompetentYears("");
        }
        SYS_Rank rank1 = rankService.selectNotAproRanksByPid(peopleId);
        if (rank1 != null) {
            reimbursementModel.setIntendedRank(rank1.getName());
        }
        return reimbursementModel;
    }

    public static void exportFreePeoples(Workbook temp, PeopleService peopleService, String peopleId,
                                         RankService rankService, EducationService educationService,
                                         AssessmentService assessmentService, UnitService unitService) throws Exception {
        SYS_People people = peopleService.selectPeopleById(peopleId);
        if (people != null) {
            ReimbursementModel reimbursementModel = getRenMianShenPi(peopleService, peopleId,
                    rankService, educationService, assessmentService, unitService, people);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.createReimbursementExcel(temp.getSheet("任免审批表"), reimbursementModel);
        }

    }

    public static Sys_Approal approvalExport(UnitService unitService, String unitName, HttpServletResponse response,
                                             PeopleService peopleService, RankService rankService, ApprovalService approvalService) throws Exception {
        SYS_UNIT unit = unitService.selectUnitByName(unitName);
        Sys_Approal approalModel = new Sys_Approal();
        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0", "在职");
        approalModel.setUnitName(unitName);
        Sys_Approal approal = approvalService.selectApproval(unit.getId(), "0");
        if (approal != null) {
            approalModel = approal;
        } else {
            getApprovalDataByData(approalModel, unit, peoples, rankService);
        }
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

    public static Sys_Approal approvalExportByApproval(UnitService unitService, Sys_Approal approalModel, HttpServletResponse response,
                                                       PeopleService peopleService, RankService rankService, ApprovalService approvalService) throws Exception {
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

    public static void getApprovalDataByData(Sys_Approal approalModel, SYS_UNIT unit, List<SYS_People> peoples, RankService rankService) {
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
    }

    public static List<SYS_People> importAllRankPeopleExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                            UnitService unitService, DutyService dutyService,
                                                            RankService rankService,String flag) throws Exception {
        List<SYS_People> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(StrUtils.toNullStr((map.get("单位"))));
            if (unit != null) {
                SYS_People people = service.selectPeopleByIdcardAndUnitIdAndName(StrUtils.toNullStr((map.get("身份证号"))), StrUtils.toNullStr((map.get("姓名"))), unit.getId());
                if (people != null) {
                    saveAllDutyDataByExcel(map, list, people, stringBuffer, dutyService, service,flag);
                    saveAllMianDutyDataByExcel(map, list, people, dutyService, service, stringBuffer,flag);
                    getPeopleAllRankDataByExcel(map, list, people, stringBuffer, rankService, service,flag);
                    getPeopleMianAllRankDataByExcel(map, list, people, stringBuffer, rankService, service,flag);
                } else {
                    logger.error("职级、职务表：第" + list.indexOf(map) + "行;人员信息不正确！");
                    stringBuffer.append("职级、职务表：第" + list.indexOf(map) + "行;人员信息不正确！");
                }
            } else {
                String name = StrUtils.toNullStr(map.get("姓名"));
                if (!StrUtils.isBlank(name)) {
                    logger.error("职级、职务表：第" + list.indexOf(map) + "行;单位不存在！");
                    stringBuffer.append("职级、职务表：第" + list.indexOf(map) + "行;单位不存在！");
                }
            }
        }
        return peopleList;
    }

    public static SYS_Duty saveAllDutyDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                  DutyService dutyService, PeopleService peopleService,String flag) throws Exception {
        SYS_Duty duty = new SYS_Duty();
        String dutyName = StrUtils.toNullStr((map.get("拟任职务名称")));
        String dutyTime = StrUtils.toNullStr((map.get("任职时间")));
        duty.setPeopleId(people.getId());
        duty.setPeopleName(people.getName());
        if (!StrUtils.isBlank(dutyTime)) {
            duty.setCreateTime(DateUtil.stringToDate(dutyTime));
        }
        duty.setName(dutyName);
        duty.setDutyType(StrUtils.toNullStr(map.get("任职部门及职务")));
        String approvalTime = String.valueOf(map.get("审批时间"));
        if (!StrUtils.isBlank(approvalTime)) {
            duty.setApprovalTime(DateUtil.stringToDate(approvalTime));
        }
        duty.setUnitId(people.getUnitId());
        duty.setSelectionMethod(StrUtils.toNullStr(map.get("任职方式")));
        duty.setStatus(StrUtils.toNullStr(map.get("任免状态")));
        String ranTime = String.valueOf(map.get("同职级层次任职时间"));
        if (!StrUtils.isBlank(ranTime)) {
            duty.setDutyTime(DateUtil.stringToDate(ranTime));
        }
        duty.setDjunct(StrUtils.toNullStr(map.get("是否兼任")));
        Date startDate=DateUtil.addDates(duty.getCreateTime(),-1);
        Date endDate=DateUtil.addDates(duty.getCreateTime(),1);
        if (!StrUtils.isBlank(dutyName) && !StrUtils.isBlank(dutyTime)) {
            SYS_Duty sys_duty=dutyService.selectDutysByPidsAndDate(startDate,endDate,people.getId(),duty.getName());
            if (sys_duty != null) {
                if (StrUtils.isBlank(sys_duty.getApprovalTime())) {
                    duty.setId(sys_duty.getId());
                    dutyService.updateDuty(duty);
                } else {
                    if ("已免".equals(duty.getStatus()) && "fugai".equals(flag)){
                        duty.setId(sys_duty.getId());
                        dutyService.updateDuty(duty);
                    }else {
                        stringBuffer.append("职务、职级表：第" + list.indexOf(map) + "行;该职务已审批，请勿重复导入！");
                        logger.error("职务、职级表：第" + list.indexOf(map) + "行;该职务已审批，请勿重复导入！");
                    }
                }
            } else {
                String uuid = UUID.randomUUID().toString();
                duty.setId(uuid);
                dutyService.insertDuty(duty);
            }
            dutyService.selectNowDutyByPidOrderByTime(people.getId());
            SYS_Duty sysDuty = dutyService.selectDutyByNameAndTime(duty.getName(), people.getId(), duty.getCreateTime());
            if (sysDuty != null) {
                people.setPosition(sysDuty.getName());
            } else {
                people.setPosition("");
            }
            peopleService.updatePeople(people);
        } else {
            if (!StrUtils.isBlank(dutyName)) {
                stringBuffer.append("职务、职级表：第" + list.indexOf(map) + "行;职务名称或者任职时间为空！");
                logger.error("职务、职级表：第" + list.indexOf(map) + "行;职务名称或者任职时间为空！");
            }
        }
        return duty;
    }

    public static SYS_Duty saveAllMianDutyDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people,
                                                      DutyService dutyService, PeopleService peopleService, StringBuffer stringBuffer,String flag) throws Exception {
        SYS_Duty duty = new SYS_Duty();
        String dutyName = StrUtils.toNullStr((map.get("拟免职务名称")));
        String dutyTime = StrUtils.toNullStr((map.get("拟免职务任职时间")));
        if (!StrUtils.isBlank(dutyName) && !StrUtils.isBlank(dutyTime)) {
            Date startDate=DateUtil.addDates(DateUtil.stringToDate(dutyTime),-1);
            Date endDate=DateUtil.addDates(DateUtil.stringToDate(dutyTime),1);
            SYS_Duty sys_duty=dutyService.selectDutysByPidsAndDate(startDate,endDate,people.getId(),dutyName);
//            SYS_Duty sys_duty = dutyService.selectDutyByNameAndTime(dutyName, people.getId(), duty.getCreateTime());
            if (sys_duty != null) {
                sys_duty.setPeopleId(people.getId());
                sys_duty.setPeopleName(people.getName());
                sys_duty.setCreateTime(DateUtil.stringToDate(dutyTime));
                sys_duty.setName(dutyName);
                sys_duty.setDutyType(StrUtils.toNullStr(map.get("拟免职务任职部门及职务")));
                sys_duty.setUnitId(people.getUnitId());
                sys_duty.setRealName(StrUtils.toNullStr(map.get("实名制（单列）管理")));
                String serveTime = String.valueOf(map.get("免职时间"));
                if (!StrUtils.isBlank(serveTime)) {
                    sys_duty.setServeTime(DateUtil.stringToDate(serveTime));
                }
                sys_duty.setDocumentNumber(StrUtils.toNullStr(map.get("免职文号")));
                sys_duty.setServeFlag(StrUtils.toNullStr(map.get("免职事由")));
                sys_duty.setStatus(StrUtils.toNullStr(map.get("拟免职务任免状态")));
                if ("已免".equals(duty.getStatus()) && "fugai".equals(flag)){
                    duty.setId(sys_duty.getId());
                    dutyService.updateDuty(duty);
                }else {
                    stringBuffer.append("职务、职级表：第" + list.indexOf(map) + "行;该职务已审批，请勿重复导入！");
                    logger.error("职务、职级表：第" + list.indexOf(map) + "行;该职务已审批，请勿重复导入！");
                }
                dutyService.updateDuty(sys_duty);
            } else {
                duty.setPeopleId(people.getId());
                duty.setPeopleName(people.getName());
                if (!StrUtils.isBlank(dutyTime)) {
                    duty.setCreateTime(DateUtil.stringToDate(dutyTime));
                }
                duty.setName(dutyName);
                duty.setDutyType(StrUtils.toNullStr(map.get("拟免职务任职部门及职务")));
                duty.setUnitId(people.getUnitId());
                duty.setRealName(StrUtils.toNullStr(map.get("实名制（单列）管理")));
                String serveTime = String.valueOf(map.get("免职时间"));
                if (!StrUtils.isBlank(serveTime)) {
                    duty.setServeTime(DateUtil.stringToDate(serveTime));
                }
                duty.setDocumentNumber(StrUtils.toNullStr(map.get("免职文号")));
                duty.setServeFlag(StrUtils.toNullStr(map.get("免职事由")));
                duty.setStatus(StrUtils.toNullStr(map.get("拟免职务任免状态")));
                String uuid = UUID.randomUUID().toString();
                duty.setId(uuid);
                dutyService.insertDuty(duty);
            }
            dutyService.selectNowDutyByPidOrderByTime(people.getId());
            SYS_Duty sysDuty = dutyService.selectDutyByNameAndTime(duty.getName(), people.getId(), duty.getCreateTime());
            if (sysDuty != null) {
                people.setPosition(sysDuty.getName());
                people.setPositionTime(sysDuty.getCreateTime());
            } else {
                people.setPosition("");
                people.setPositionTime(null);
            }
            peopleService.updatePeople(people);
        } else {
            if (!StrUtils.isBlank(dutyName)) {
                stringBuffer.append("职务、职级表：第" + list.indexOf(map) + "行;拟免职务名称或者任职时间为空！");
                logger.error("职务、职级表：第" + list.indexOf(map) + "行;拟免职务名称或者任职时间为空！");
            }
        }
        return duty;
    }

    public static SYS_Rank getPeopleAllRankDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                       RankService rankService, PeopleService peopleService,String flag) throws Exception {
        SYS_Rank rank = new SYS_Rank();
        String name = StrUtils.toNullStr(map.get("拟任职级名称"));
        String creatTime = String.valueOf(map.get("任职级时间"));
        if (!StrUtils.isBlank(name) && !StrUtils.isBlank(creatTime)) {
            rank.setPeopleId(people.getId());
            rank.setPeopleName(people.getName());
            rank.setUnitId(people.getUnitId());
            rank.setName(name);
            if (!StrUtils.isBlank(creatTime)) {
                rank.setCreateTime(DateUtil.stringToDate(creatTime));
            }
            String rankTime = String.valueOf(map.get("同级职级任职时间"));
            if (!StrUtils.isBlank(rankTime)) {
                rank.setRankTime(DateUtil.stringToDate(rankTime));
            }
            rank.setStatus(StrUtils.toNullStr(map.get("任免状态")));
            rank.setBatch(StrUtils.toNullStr(map.get("批次")));
            rank.setDetail(StrUtils.toNullStr(map.get("任职级事由")));
            String democracy=StrUtils.toNullStr(map.get("民主测评结果"));
            if (StrUtils.isNumeric(democracy)){
                rank.setDemocracy((StrUtils.strToDouble(democracy)*100)+"%");
            }else {
                rank.setDemocracy(democracy);
            }
            rank.setLeaders(StrUtils.toNullStr(map.get("是否军转干部首次确定职级不占职数")));
            String approvalTime = String.valueOf(map.get("审批日期"));
            if (!StrUtils.isBlank(approvalTime)) {
                rank.setApprovalTime(DateUtil.stringToDate(approvalTime));
            }
            Date startDate=DateUtil.addDates(DateUtil.stringToDate(creatTime),-1);
            Date endDate=DateUtil.addDates(DateUtil.stringToDate(creatTime),1);
            SYS_Rank rank1 = rankService.selectRanksByPidsAndDate(startDate,endDate, people.getId(), rank.getName());
            if (rank1 != null) {
                if (StrUtils.isBlank(rank1.getApprovalTime())) {
                    rank.setId(rank1.getId());
                    rankService.updateRank(rank);
                } else {
                    if ("已免".equals(rank.getStatus()) && "fugai".equals(flag)){
                        rank.setId(rank1.getId());
                        rankService.updateRank(rank);
                    }else {
                        stringBuffer.append("职级表：第" + list.indexOf(map) + "行;该职级已审批，请勿重复导入！");
                        logger.error("职级表：第" + list.indexOf(map) + "行;该职级已审批，请勿重复导入！");
                    }
                }
            } else {
                String uuid = UUID.randomUUID().toString();
                rank.setId(uuid);
                rankService.insertRank(rank);
            }
            SYS_Rank sysRank = rankService.selectNowRankByPidOrderByTime(people.getId());
            if (sysRank != null) {
                people.setPositionLevel(rank.getName());
                people.setPositionLevelTime(rank.getCreateTime());
                peopleService.updatePeople(people);
            } else {
                people.setPositionLevel("");
                people.setPositionLevelTime(null);
                peopleService.updatePeople(people);
            }
        } else {
            if (!StrUtils.isBlank(name)) {
                stringBuffer.append("职级表：第" + list.indexOf(map) + "行;职级名称或者任职级时间为空！");
                logger.error("职级表：第" + list.indexOf(map) + "行;职级名称或者任职级时间为空！");
            }
        }
        return rank;
    }

    public static SYS_Rank getPeopleMianAllRankDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                           RankService rankService, PeopleService peopleService,String flag) throws Exception {
        SYS_Rank rank = new SYS_Rank();
        String name = StrUtils.toNullStr(map.get("拟免职级"));
        String creatTime = String.valueOf(map.get("拟免职级任职时间"));
        if (!StrUtils.isBlank(name) && !StrUtils.isBlank(creatTime)) {
                rank.setCreateTime(DateUtil.stringToDate(creatTime));
            Date startDate=DateUtil.addDates(DateUtil.stringToDate(creatTime),-1);
            Date endDate=DateUtil.addDates(DateUtil.stringToDate(creatTime),1);
//            rankService.selectRanksByPidsAndDate(startDate,endDate, people.getId(), rank.getName());
            SYS_Rank rank1 =rankService.selectRanksByPidsAndDate(startDate,endDate, people.getId(), name);
            if (rank1 != null) {
                rank1.setPeopleId(people.getId());
                rank1.setPeopleName(people.getName());
                rank1.setUnitId(people.getUnitId());
                rank1.setName(name);
                if (!StrUtils.isBlank(creatTime)) {
                    rank1.setCreateTime(DateUtil.stringToDate(creatTime));
                }
                rank1.setStatus(StrUtils.toNullStr(map.get("拟免任免状态")));
                rank1.setBatch(StrUtils.toNullStr(map.get("批次")));
                rank1.setDeposeRank(StrUtils.toNullStr(map.get("免职级事由")));
                String deposeTime = String.valueOf(map.get("免职级时间"));
                if (!StrUtils.isBlank(deposeTime)) {
                    rank1.setDeposeTime(DateUtil.stringToDate(deposeTime));
                }
                rank1.setId(rank1.getId());
                if ("已免".equals(rank1.getStatus()) && "fugai".equals(flag)){
                    rankService.updateRank(rank1);
                }else {
                    stringBuffer.append("职级表：第" + list.indexOf(map) + "行;该职级已审批，请勿重复导入！");
                    logger.error("职级表：第" + list.indexOf(map) + "行;该职级已审批，请勿重复导入！");
                }
            } else {
                rank.setPeopleId(people.getId());
                rank.setPeopleName(people.getName());
                rank.setUnitId(people.getUnitId());
                rank.setName(name);
                if (!StrUtils.isBlank(creatTime)) {
                    rank.setCreateTime(DateUtil.stringToDate(creatTime));
                }
                rank.setStatus(StrUtils.toNullStr(map.get("拟免任免状态")));
                rank.setBatch(StrUtils.toNullStr(map.get("批次")));
                rank.setDeposeRank(StrUtils.toNullStr(map.get("免职级事由")));
                String deposeTime = String.valueOf(map.get("免职级时间"));
                if (!StrUtils.isBlank(deposeTime)) {
                    rank.setDeposeTime(DateUtil.stringToDate(deposeTime));
                }
                String uuid = UUID.randomUUID().toString();
                rank.setId(uuid);
                rankService.insertRank(rank);
            }
            SYS_Rank sysRank = rankService.selectNowRankByPidOrderByTime(people.getId());
            if (sysRank != null) {
                people.setPositionLevel(rank.getName());
                people.setPositionLevelTime(rank.getCreateTime());
                peopleService.updatePeople(people);
            } else {
                people.setPositionLevel("");
                people.setPositionLevelTime(null);
                peopleService.updatePeople(people);
            }
        } else {
            if (!StrUtils.isBlank(name)) {
                stringBuffer.append("职级表：第" + list.indexOf(map) + "行;拟免职级名称或者任职级时间为空！");
                logger.error("职级表：第" + list.indexOf(map) + "行;拟免职级名称或者任职级时间为空！");
            }
        }
        return rank;
    }

    public static List<SYS_People> getPeopleDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                        UnitService unitService, String fullImport, EducationService educationService, DutyService dutyService,
                                                        RankService rankService, RewardService rewardService, AssessmentService assessmentService,
                                                        CodeService codeService) throws Exception {
        List<SYS_People> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_People people1 = savePeopleExcle(map, unit, service, fullImport, stringBuffer, list, peopleList);
                if (people1 != null) {
                    SYS_People people = service.selectPeopleById(people1.getId());
                    if (people != null) {
                        saveDutyDataByExcel(map, list, people, stringBuffer, unitService, fullImport, dutyService, service);
                        saveRealDutyDataByExcel(map, list, people, stringBuffer, unitService, fullImport, dutyService, service);//实名制（单列）管理信息
                        getPeopleTaoRankDataByExcel(map, list, people, stringBuffer, unitService, fullImport, rankService, service);
                        getPeopleRankDataByExcel(map, list, people, stringBuffer, unitService, fullImport, rankService, service);
                        saveEducationDataByExcel(map, unit, educationService, fullImport, stringBuffer, list, people, service, codeService);
                        saveEducationDataByExcel2(map, unit, educationService, fullImport, stringBuffer, list, people, codeService);
                        getPeopleRewardDataByExcel(map, list, people, stringBuffer, unitService, fullImport, rewardService);
                        getPeopleAssessmentDataByExcel(map, list, people, stringBuffer, unitService, fullImport, assessmentService);
                        SYS_Education education = educationService.selectEducationByPidOrderByTime(people.getId());
                        if (education != null) {
                            people.setEducation(education.getName());
                        }
                        service.updatePeople(people);
                        saveNowRank(people.getId(),rankService);
                    }
                }
            } else {
                String name = StrUtils.toNullStr(map.get("姓名"));
                if (!StrUtils.isBlank(name)) {
                    logger.error("人员表：第" + list.indexOf(map) + "行;单位不存在！");
                    stringBuffer.append("人员表：第" + list.indexOf(map) + "行;单位不存在！");
                }
            }
        }
        return peopleList;
    }
    public static void saveNowRank(String pid,RankService rankService){
        SYS_Rank rank=rankService.selectRankByPidOrderByTime(pid);
        if (rank!=null){
            rank.setRankOrder(1);
            rankService.updateRank(rank);
            List<SYS_Rank> rankList=rankService.selectRanksByPeopleId(pid);
            for (SYS_Rank rank1:rankList){
                if (!rank.getId().equals(rank1.getId())){
                    rank1.setRankOrder(0);
                    rankService.updateRank(rank1);
                }
            }
        }
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
                    people.setSex(StrUtils.toNullStr(map.get("性别")));
                    people.setBirthplace(StrUtils.toNullStr(map.get("籍贯")));
                    people.setNationality(StrUtils.toNullStr(map.get("民族 ")));
                    String workdayStr = String.valueOf(map.get("参加工作时间"));
                    if (!StrUtils.isBlank(workdayStr)) {
                        people.setWorkday(DateUtil.stringToDate(workdayStr));
                    }
                    people.setParty(StrUtils.toNullStr(map.get("政治面貌")));
                    String partyTimeStr = String.valueOf(map.get("入党（团）时间"));
                    if (!StrUtils.isBlank(partyTimeStr)) {
                        people.setPartyTime(DateUtil.stringToDate(partyTimeStr));
                    }
                    people.setSecondParty(StrUtils.toNullStr(map.get("第二党派")));
                    people.setThirdParty(StrUtils.toNullStr(map.get("第三党派")));
                    people.setPosition(StrUtils.toNullStr(map.get("职务名称")));
                    String positionTimeStr = String.valueOf(map.get("现任职务时间"));
                    if (!StrUtils.isBlank(positionTimeStr)) {
                        people.setPositionTime(DateUtil.stringToDate(positionTimeStr));
                    }
                    people.setPositionLevel(StrUtils.toNullStr(map.get("现任职级（晋升后）")));
                    String positionLevelTimeStr = String.valueOf(map.get("任职级时间"));
                    if (!StrUtils.isBlank(positionLevelTimeStr)) {
                        people.setPositionLevelTime(DateUtil.stringToDate(positionLevelTimeStr));
                    }
                    people.setBaseWorker(StrUtils.toNullStr(map.get("是否具有两年以上基层工作经历")));
                    people.setPoliticalStatus(StrUtils.toNullStr(map.get("编制类型")));
                    people.setEducation(StrUtils.toNullStr(map.get("全日制学历")));
                    String createTimeStr = String.valueOf(map.get("公务员登记时间"));
                    if (!StrUtils.isBlank(createTimeStr)) {
                        people.setPositionLevelTime(DateUtil.stringToDate(createTimeStr));
                    }
                    people.setTurnRank(StrUtils.toNullStr(map.get("套转职级")));
                    String turnRankTime = String.valueOf(map.get("套转职级时间"));
                    if (!StrUtils.isBlank(turnRankTime)) {
                        people.setTurnRankTime(DateUtil.stringToDate(turnRankTime));
                    }
                    String enableStr = StrUtils.toNullStr(map.get("是否兼任"));
                    people.setIsEnable(enableStr);
                    people.setRealName(StrUtils.toNullStr(map.get("单列管理事由")));
                    String jun = StrUtils.toNullStr(map.get("是否军转干部首次套转不占职数"));
                    String danlei = StrUtils.toNullStr(map.get("实名制职务名称"));
                    if (!StrUtils.isBlank(jun)) {
                        people.setDetail(jun);
                    } else {
                        people.setDetail("否");
                    }
                    if (!StrUtils.isBlank(danlei)) {
                        people.setRealName(danlei);
                    } else {
                        people.setRealName("否");
                    }
                    SYS_People people1 = service.selectPeopleByIdcardAndUnitId(people.getIdcard(), unit.getId());
                    people.setChineseEncoder(HanyuPinyinUtil.toHanyuPinyin(people.getName()));
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
     * 全日制学历导入
     *
     * @param list
     * @param stringBuffer
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static SYS_Education saveEducationDataByExcel(Map<String, Object> map, SYS_UNIT unit, EducationService educationService, String fullImport, StringBuffer stringBuffer,
                                                         List<Map<String, Object>> list, SYS_People people, PeopleService peopleService,
                                                         CodeService codeService) throws Exception {
        SYS_Education education = new SYS_Education();
        String name = StrUtils.toNullStr(map.get("全日制学历"));
        if (!StrUtils.isBlank(name)) {
            education.setPeopleId(people.getId());
            education.setPeopleName(people.getName());
            education.setUnitId(people.getUnitId());
            education.setName(name);
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
            education.setSchoolType("全日制教育");
            education.setSchool(StrUtils.toNullStr(map.get("毕业学校")));
            education.setProfession(StrUtils.toNullStr(map.get("所学专业")));
            SYS_Education education1 = educationService.selectEducationByName(education.getName(), people.getId());
            people.setEducation(education.getName());
            peopleService.updatePeople(people);
            SYS_CODE code = codeService.selectCodeByName(education.getName(), "250");
            if (code != null) {
                education.setEducationOrder(String.valueOf(code.getId()));
            }
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
        }
        return education;
    }

    /**
     * 在职学历导入
     *
     * @param list
     * @param stringBuffer
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static SYS_Education saveEducationDataByExcel2(Map<String, Object> map, SYS_UNIT unit, EducationService educationService, String fullImport, StringBuffer stringBuffer,
                                                          List<Map<String, Object>> list, SYS_People people, CodeService codeService) throws Exception {
        SYS_Education education = new SYS_Education();
        String name = StrUtils.toNullStr(map.get("在职学历"));
        if (!StrUtils.isBlank(name)) {
            education.setPeopleId(people.getId());
            education.setPeopleName(people.getName());
            education.setUnitId(people.getUnitId());
            education.setName(name);
            String creatTime = String.valueOf(map.get("入学时间"));
            if (!StrUtils.isBlank(creatTime)) {
                education.setCreateTime(DateUtil.stringToDate(creatTime));
            }
            education.setDegree(StrUtils.toNullStr(map.get("在职学位名称")));
            String serveTime = String.valueOf(map.get("毕（肄）业时间"));
            if (!StrUtils.isBlank(serveTime)) {
                education.setEndTime(DateUtil.stringToDate(serveTime));
            }
            String degreeTime = String.valueOf(map.get("学位授予时间"));
            if (!StrUtils.isBlank(degreeTime)) {
                education.setDegreeTime(DateUtil.stringToDate(degreeTime));
            }
            education.setSchoolType("在职教育");
            education.setSchool(StrUtils.toNullStr(map.get("在职毕业学校")));
            education.setProfession(StrUtils.toNullStr(map.get("在职所学专业")));
            SYS_Education education1 = educationService.selectEducationByNameAndTime(education.getName(), people.getId(), education.getCreateTime());
            SYS_CODE code = codeService.selectCodeByName(education.getName(), "250");
            if (code != null) {
                education.setEducationOrder(String.valueOf(code.getId()));
            }
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
                                               UnitService unitService, String fullImport, DutyService dutyService, PeopleService peopleService) throws Exception {
        SYS_Duty duty = new SYS_Duty();
        String creatTime = String.valueOf(map.get("现任职务时间"));
        String name = StrUtils.toNullStr(map.get("职务名称"));
        if (!StrUtils.isBlank(name) ) {
            if (!StrUtils.isBlank(creatTime)){
                duty.setPeopleId(people.getId());
                duty.setPeopleName(people.getName());
                duty.setUnitId(people.getUnitId());
                if (!StrUtils.isBlank(creatTime)) {
                    duty.setCreateTime(DateUtil.stringToDate(creatTime));
                    duty.setApprovalTime(DateUtil.stringToDate(creatTime));
                }
                duty.setDutyType(StrUtils.toNullStr(map.get("现任职务")));
                duty.setName(StrUtils.toNullStr(map.get("职务名称")));
                duty.setSelectionMethod(StrUtils.toNullStr(map.get("任职方式")));
//            duty.setStatus(StrUtils.toNullStr(map.get("任职状态")));
                duty.setStatus("在任");
                duty.setDjunct(StrUtils.toNullStr(map.get("是否兼任")));
                duty.setDocumentduty(StrUtils.toNullStr(map.get("任职文号")));
                duty.setRealName("否");
                String serveTime = String.valueOf(map.get("免职时间"));
                if (!StrUtils.isBlank(serveTime)) {
                    duty.setServeTime(DateUtil.stringToDate(serveTime));
                }
                String dutyTime = String.valueOf(map.get("同级职务任职时间"));
                if (!StrUtils.isBlank(dutyTime)) {
                    duty.setDutyTime(DateUtil.stringToDate(dutyTime));
                }
                String approvalTime = String.valueOf(map.get("职务审批通过时间"));
                if (!StrUtils.isBlank(approvalTime)) {
                    duty.setApprovalTime(DateUtil.stringToDate(approvalTime));
                }
                duty.setDocumentNumber(StrUtils.toNullStr(map.get("免职文号")));
                people.setPosition(duty.getName());
                people.setPositionTime(duty.getCreateTime());
                peopleService.updatePeople(people);
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
            }else {
                stringBuffer.append("职务表：第" + list.indexOf(map) + "行;职务时间为空！");
                logger.error("职务表：第" + list.indexOf(map) + "行;职务时间为空！");
            }
        }
        return duty;
    }

    public static SYS_Duty saveRealDutyDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                   UnitService unitService, String fullImport, DutyService dutyService, PeopleService peopleService) throws Exception {
        SYS_Duty duty = new SYS_Duty();
        String name = StrUtils.toNullStr(map.get("实名制职务名称"));
        String creatTime = String.valueOf(map.get("实名制现任职务时间"));
        if (!StrUtils.isBlank(name) && !"".equals(name)) {
            if (!StrUtils.isBlank(creatTime) && !"".equals(creatTime)) {
                duty.setPeopleId(people.getId());
                duty.setPeopleName(people.getName());
                duty.setUnitId(people.getUnitId());
                if (!StrUtils.isBlank(creatTime)) {
                    duty.setCreateTime(DateUtil.stringToDate(creatTime));
                    duty.setApprovalTime(DateUtil.stringToDate(creatTime));
                }
                duty.setDutyType(StrUtils.toNullStr(map.get("现任职务")));
                duty.setName(StrUtils.toNullStr(map.get("实名制职务名称")));
                duty.setSelectionMethod(StrUtils.toNullStr(map.get("单列管理事由")));
//            duty.setStatus(StrUtils.toNullStr(map.get("任职状态")));
                duty.setStatus("在任");
                duty.setDjunct(StrUtils.toNullStr(map.get("是否兼任")));
                duty.setDocumentduty(StrUtils.toNullStr(map.get("任职文号")));
                duty.setRealName("是");
                String serveTime = String.valueOf(map.get("免职时间"));
                if (!StrUtils.isBlank(serveTime)) {
                    duty.setServeTime(DateUtil.stringToDate(serveTime));
                }
                String dutyTime = String.valueOf(map.get("实名制同职务层次时间"));
                if (!StrUtils.isBlank(dutyTime)) {
                    duty.setDutyTime(DateUtil.stringToDate(dutyTime));
                }
                String approvalTime = String.valueOf(map.get("实名制审批通过时间"));
                if (!StrUtils.isBlank(approvalTime)) {
                    duty.setApprovalTime(DateUtil.stringToDate(approvalTime));
                }
                duty.setDocumentNumber(StrUtils.toNullStr(map.get("免职文号")));
                people.setPosition(duty.getName());
                people.setPositionTime(duty.getCreateTime());
                people.setRealName("是");
                peopleService.updatePeople(people);
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
            }else {
                stringBuffer.append("职务表：第" + list.indexOf(map) + "行;该职务任职时间为空！");
                logger.error("职务表：第" + list.indexOf(map) + "行;该职务任职时间为空！");
            }
        }
        return duty;
    }

    /**
     * 套转职级表导入
     *
     * @param list
     * @param stringBuffer
     * @param unitService
     * @param fullImport
     * @return
     * @throws Exception
     */
    public static SYS_Rank getPeopleTaoRankDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                       UnitService unitService, String fullImport, RankService rankService, PeopleService peopleService) throws Exception {
        SYS_Rank rank = new SYS_Rank();
        String name = StrUtils.toNullStr(map.get("套转职级"));
        String creatTime = String.valueOf(map.get("套转职级时间"));
        if (!StrUtils.isBlank(name)) {
            rank.setPeopleId(people.getId());
            rank.setPeopleName(people.getName());
            rank.setUnitId(people.getUnitId());
            rank.setUnitName(people.getUnitName());
            rank.setName(name);
            if (!StrUtils.isBlank(creatTime)) {
                rank.setCreateTime(DateUtil.stringToDate(creatTime));
            } else {
                rank.setCreateTime(DateUtil.stringToDate("2019-06-01"));
            }
            String rankTime = String.valueOf(map.get("同级职级任职时间"));
            if (!StrUtils.isBlank(rankTime)) {
                rank.setRankTime(DateUtil.stringToDate(rankTime));
            }
            rank.setLeaders(StrUtils.toNullStr(map.get("是否军转干部首次套转不占职数")));
            if ("是".equals(StrUtils.toNullStr(map.get("是否军转干部首次套转不占职数")))) {
                people.setDetail("是");
            }
            rank.setStatus("在任");
            rank.setFlag("是");
            String approvalTime = String.valueOf(map.get("套改职级审批通过时间"));
            if (!StrUtils.isBlank(approvalTime)) {
                rank.setApprovalTime(DateUtil.stringToDate(approvalTime));
            } else {
                rank.setApprovalTime(DateUtil.stringToDate("2019-06-01"));
            }
            SYS_Rank rank1 = rankService.selectRankByNameAndTime(rank.getName(), people.getId(), rank.getCreateTime());
            people.setPositionLevel(rank.getName());
            people.setPositionLevelTime(rank.getCreateTime());
            peopleService.updatePeople(people);
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
        }
        return rank;
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
                                                    UnitService unitService, String fullImport, RankService rankService, PeopleService peopleService) throws Exception {
        SYS_Rank rank = new SYS_Rank();
        String name = StrUtils.toNullStr(map.get("现任职级（晋升后）"));
        String creatTime = String.valueOf(map.get("任职级时间"));
        if (!StrUtils.isBlank(name)) {
            if (!StrUtils.isBlank(creatTime)) {
                rank.setPeopleId(people.getId());
                rank.setPeopleName(people.getName());
                rank.setUnitId(people.getUnitId());
                rank.setUnitName(people.getUnitName());
                rank.setName(name);
                if (!StrUtils.isBlank(creatTime)) {
                    rank.setCreateTime(DateUtil.stringToDate(creatTime));
                }
                String rankTime = String.valueOf(map.get("同级职级任职时间"));
                if (!StrUtils.isBlank(rankTime)) {
                    rank.setRankTime(DateUtil.stringToDate(rankTime));
                }
                rank.setStatus("在任");
                rank.setBatch(StrUtils.toNullStr(map.get("批次")));
                rank.setDetail(StrUtils.toNullStr(map.get("任职级事由")));
                rank.setDemocracy(StrUtils.toNullStr(map.get("民主测评结果")));
                rank.setDocumentNumber(StrUtils.toNullStr(map.get("批准文号")));
                String approvalTime = String.valueOf(map.get("任职级审批通过日期"));
                if (!StrUtils.isBlank(approvalTime)) {
                    rank.setApprovalTime(DateUtil.stringToDate(approvalTime));
                }
                rank.setDeposeRank(StrUtils.toNullStr(map.get("免职级事由")));
                rank.setFlag("否");
                String deposeTime = String.valueOf(map.get("免职级时间"));
                if (!StrUtils.isBlank(deposeTime)) {
                    rank.setDeposeTime(DateUtil.stringToDate(deposeTime));
                }
                String serveApprovalTime = String.valueOf(map.get("免职级审批通过日期"));
                if (!StrUtils.isBlank(serveApprovalTime)) {
                    rank.setApprovalTime(DateUtil.stringToDate(serveApprovalTime));
                }
                SYS_Rank rank1 = rankService.selectRankByNameAndTime(rank.getName(), people.getId(), rank.getCreateTime());
                people.setPositionLevel(rank.getName());
                people.setPositionLevelTime(rank.getCreateTime());
                peopleService.updatePeople(people);
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
            }else {
                stringBuffer.append("职级表：第" + list.indexOf(map) + "行;该职级任职级时间为空！");
                logger.error("职级表：第" + list.indexOf(map) + "行;该职级任职级时间为空！");
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
        String name = StrUtils.toNullStr(map.get("奖惩名称"));
        if (!StrUtils.isBlank(name)) {
            reward.setPeopleId(people.getId());
            reward.setPeopleName(people.getName());
            reward.setUnitId(people.getUnitId());
            reward.setName(name);
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
            reward.setDuty(StrUtils.toNullStr(map.get("受奖惩时职务层次")));
            reward.setUnitType(StrUtils.toNullStr(map.get("批准机关性质")));
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
        }
        return reward;
    }

    public static void getPeopleAssessmentDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                      UnitService unitService, String fullImport, AssessmentService assessmentService) throws Exception {
        getPeopleAssessmentDataByExcel(map, list, people, stringBuffer, unitService, fullImport, assessmentService, "年份1", "结果1");
        getPeopleAssessmentDataByExcel(map, list, people, stringBuffer, unitService, fullImport, assessmentService, "年份2", "结果2");
        getPeopleAssessmentDataByExcel(map, list, people, stringBuffer, unitService, fullImport, assessmentService, "年份3", "结果3");
        getPeopleAssessmentDataByExcel(map, list, people, stringBuffer, unitService, fullImport, assessmentService, "年份4", "结果4");
        getPeopleAssessmentDataByExcel(map, list, people, stringBuffer, unitService, fullImport, assessmentService, "年份5", "结果5");
    }

    public static SYS_Assessment getPeopleAssessmentDataByExcel(Map<String, Object> map, List<Map<String, Object>> list, SYS_People people, StringBuffer stringBuffer,
                                                                UnitService unitService, String fullImport, AssessmentService assessmentService, String year, String name) throws Exception {
        SYS_Assessment assessment = new SYS_Assessment();
        if (!StrUtils.isBlank(StrUtils.toNullStr(map.get(name)))) {
            assessment.setPeopleId(people.getId());
            assessment.setPeopleName(people.getName());
            assessment.setUnitId(people.getUnitId());
            assessment.setName(StrUtils.toNullStr(map.get(name)));
            assessment.setYear(StrUtils.strToInt(StrUtils.toNullStr(map.get(year))));
            SYS_Assessment assessment1 = assessmentService.selectAssessmentByYear(people.getId(), assessment.getYear());
            if ("1".equals(fullImport)) {//覆盖导入
                if (assessment1 != null) {
                    assessment.setId(assessment1.getId());
                    assessmentService.updateAssessment(assessment);
                } else {
                    String uuid = UUID.randomUUID().toString();
                    assessment.setId(uuid);
                    assessmentService.insertAssessment(assessment);
                }
            } else {
                if (assessment1 != null) {
                    stringBuffer.append("考核表：第" + list.indexOf(map) + "行;该考核已存在，请勿重复导入！");
                    logger.error("考核表：第" + list.indexOf(map) + "行;该考核已存在，请勿重复导入！");
                } else {
                    String uuid = UUID.randomUUID().toString();
                    assessment.setId(uuid);
                    assessmentService.insertAssessment(assessment);
                }
            }
        }
        return assessment;
    }

    /**
     * 单位数据封装
     *
     * @param resultMap
     * @param unitId
     * @param unitService
     */

    public static List<SYS_UNIT> getUnitJson(Map<String, Object> resultMap, String unitId, UnitService unitService) {
        SYS_UNIT unit = unitService.selectUnitById(unitId);
        List<SYS_UNIT> unitList = new ArrayList<>();
        unitList.add(unit);
//        List<SYS_UNIT> cunitList = unitService.selectAllChildUnits(unitId);
//        unitList.addAll(cunitList);
        resultMap.put("unitId", unit.getId());
        resultMap.put("date", DateUtil.dateToString(new Date()));
        resultMap.put("unitName", unit.getName());
        JSONArray array = JSONArray.fromObject(unitList);
        resultMap.put("unitList", array);
        return unitList;
    }

    public static List<SYS_USER> getUserJson(Map<String, Object> resultMap, List<SYS_UNIT> units, UserService userService) {
        List<SYS_USER> userList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<SYS_USER> users = userService.selectUsersByUnitId(unit.getId());
            if (users != null) {
                userList.addAll(users);
            }
        }
        JSONArray array = JSONArray.fromObject(userList);
        resultMap.put("userList", array);
        return userList;
    }

    public static List<SYS_Digest> getDigestJson(Map<String, Object> resultMap, List<SYS_UNIT> units, DataService dataService) {
        List<SYS_Digest> userList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<SYS_Digest> users = dataService.selectDigestsByUnitId(unit.getId());
            if (users != null) {
                userList.addAll(users);
            }
        }
        JSONArray array = JSONArray.fromObject(userList);
        resultMap.put("digestList", array);
        return userList;
    }

    /**
     * 人员数据json
     *
     * @param resultMap
     * @param units
     * @param peopleService
     */
    public static List<SYS_People> getPeopleJson(Map<String, Object> resultMap, List<SYS_UNIT> units, PeopleService peopleService) {
        List<SYS_People> peopleList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0", "全部");
            if (peoples != null) {
                peopleList.addAll(peoples);
            }
        }
        JSONArray array = JSONArray.fromObject(peopleList);
        resultMap.put("peopleList", array);
        return peopleList;
    }

    /**
     * 上行职务数据json
     *
     * @param resultMap
     * @param peoples
     * @param dutyService
     * @return
     */
    public static List<SYS_Duty> getDutyJson(Map<String, Object> resultMap, List<SYS_People> peoples, DutyService dutyService) {
        List<SYS_Duty> dutyList = new ArrayList<>();
        for (SYS_People people : peoples) {
            List<SYS_Duty> duties = dutyService.selectDutysByPeopleId(people.getId());
            if (duties != null) {
                dutyList.addAll(duties);
            }
        }
        JSONArray array = JSONArray.fromObject(dutyList);
        resultMap.put("dutyList", array);
        return dutyList;
    }

    /**
     * 职级数据json
     *
     * @param resultMap
     * @param peoples
     * @param rankService
     * @return
     */
    public static List<SYS_Rank> getRankJson(Map<String, Object> resultMap, List<SYS_People> peoples, RankService rankService) {
        List<SYS_Rank> rankList = new ArrayList<>();
        for (SYS_People people : peoples) {
            List<SYS_Rank> ranks = rankService.selectRanksByPeopleId(people.getId());
            if (ranks != null) {
                rankList.addAll(ranks);
            }
        }
        JSONArray array = JSONArray.fromObject(rankList);
        resultMap.put("rankList", array);
        return rankList;
    }

    /**
     * 学历数据json
     *
     * @param resultMap
     * @param peoples
     * @param educationService
     * @return
     */
    public static List<SYS_Education> getEducationJson(Map<String, Object> resultMap, List<SYS_People> peoples, EducationService educationService) {
        List<SYS_Education> educationList = new ArrayList<>();
        for (SYS_People people : peoples) {
            List<SYS_Education> educations = educationService.selectEducationsByPeopleId(people.getId());
            if (educations != null) {
                educationList.addAll(educations);
            }
        }
        JSONArray array = JSONArray.fromObject(educationList);
        resultMap.put("educationList", array);
        return educationList;
    }

    /**
     * 奖惩数据json
     *
     * @param resultMap
     * @param peoples
     * @param rewardService
     * @return
     */
    public static List<SYS_Reward> getRewardJson(Map<String, Object> resultMap, List<SYS_People> peoples, RewardService rewardService) {
        List<SYS_Reward> rewardList = new ArrayList<>();
        for (SYS_People people : peoples) {
            List<SYS_Reward> rewards = rewardService.selectRewardsByPeopleId(people.getId());
            if (rewards != null) {
                rewardList.addAll(rewards);
            }
        }
        JSONArray array = JSONArray.fromObject(rewardList);
        resultMap.put("rewardList", array);
        return rewardList;
    }

    /**
     * @param resultMap
     * @param peoples
     * @param assessmentService
     * @return
     */
    public static List<SYS_Assessment> getAssessmentJson(Map<String, Object> resultMap, List<SYS_People> peoples, AssessmentService assessmentService) {
        List<SYS_Assessment> assessmentList = new ArrayList<>();
        for (SYS_People people : peoples) {
            List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByPeopleId(people.getId());
            if (assessments != null) {
                assessmentList.addAll(assessments);
            }
        }
        JSONArray array = JSONArray.fromObject(assessmentList);
        resultMap.put("assessmentList", array);
        return assessmentList;
    }

    public static List<Sys_Approal> getApproalJson(Map<String, Object> resultMap, List<SYS_UNIT> units, ApprovalService approvalService, String dataType) {
        List<Sys_Approal> approvalList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            if ("上行".equals(dataType)) {
                Sys_Approal approals = approvalService.selectApproval(unit.getId(), "0");
                if (approals != null) {
                    approvalList.add(approals);
                }
            } else {
                Sys_Approal approal = approvalService.selectApproval(unit.getId(), "1");
                if (approal != null) {
                    approvalList.add(approal);
                }
            }
        }
        JSONArray array = JSONArray.fromObject(approvalList);
        resultMap.put("approvalList", array);
        return approvalList;
    }

    /**
     * 获取上行审批表jsan
     *
     * @param resultMap
     * @param processService
     * @return
     */
    public static List<Sys_Process> getUpProcessJson(Map<String, Object> resultMap, Sys_Process process, ProcessService processService) {
        List<Sys_Process> processList = new ArrayList<>();
        List<Sys_Process> cprocesses = processService.selectProcesssByParentId(process.getId());
        if (cprocesses != null) {
            process.setChildren(cprocesses);
        } else {
            process.setChildren(new ArrayList<>());
        }
        processList.add(process);
        JSONArray array = JSONArray.fromObject(processList);
        resultMap.put("processList", array);
        return processList;
    }

    /**
     * 获取下行审批表jsan
     *
     * @param resultMap
     * @param units
     * @param processService
     * @param dataType
     * @return
     */
    public static List<Sys_Process> getProcessJson(Map<String, Object> resultMap, List<SYS_UNIT> units, ProcessService processService, String dataType, String flag) {
        List<Sys_Process> processList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            if ("上行".equals(dataType)) {
                List<Sys_Process> approals = processService.selectNotApprProcessByFlag(unit.getId(), flag);
                if (approals != null) {
                    for (Sys_Process process : approals) {
                        List<Sys_Process> cprocesses = processService.selectProcesssByParentId(process.getId());
                        if (cprocesses != null) {
                            process.setChildren(cprocesses);
                        } else {
                            process.setChildren(new ArrayList<>());
                        }
                        processList.add(process);
                    }
                }
            } else {
                List<Sys_Process> processes = processService.selectApprProcess(unit.getId());
                if (processes != null) {
                    for (Sys_Process process : processes) {
                        if (process.getParentId() == null) {
                            List<Sys_Process> cprocesses = new ArrayList<>();
                            for (Sys_Process cprocess : processes) {
                                if (process.getId().equals(cprocess.getParentId())) {
                                    cprocesses.add(cprocess);
                                }
                            }
                            process.setChildren(cprocesses);
                            processList.add(process);
                        }
                    }
                }
            }
        }
        JSONArray array = JSONArray.fromObject(processList);
        resultMap.put("processList", array);
        return processList;
    }

    /**
     * 获取单位json数据
     *
     * @param unitList
     * @return
     */
    public static List<SYS_UNIT> saveUnitJsonModel(JSONArray unitList) {
        List<SYS_UNIT> units = new ArrayList<>();
        for (int i = 0; i < unitList.size(); i++) {
            SYS_UNIT unit = new SYS_UNIT();
            JSONObject key = (JSONObject) unitList.get(i);
            try {
                EntityUtil.setReflectModelValue(unit, key);
                unit.setReferOfficialDate(DateUtil.stringToDate(String.valueOf(key.get("referOfficialDateStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                units.add(unit);
            } catch (SecurityException e) {
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

    public static List<SYS_USER> saveUserJsonModel(JSONArray userList) {
        List<SYS_USER> users = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            SYS_USER user = new SYS_USER();
            JSONObject key = (JSONObject) userList.get(i);
            try {
                EntityUtil.setReflectModelValue(user, key);
                user.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                user.setLastTime(DateUtil.stringToDate(String.valueOf(key.get("lastTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                users.add(user);
            } catch (SecurityException e) {
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

    public static List<SYS_Digest> saveDigestJsonModel(JSONArray digestList) {
        List<SYS_Digest> users = new ArrayList<>();
        for (int i = 0; i < digestList.size(); i++) {
            SYS_Digest user = new SYS_Digest();
            JSONObject key = (JSONObject) digestList.get(i);
            try {
                EntityUtil.setReflectModelValue(user, key);
                user.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                users.add(user);
            } catch (SecurityException e) {
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

    public static List<SYS_Message> saveMessageJsonModel(JSONArray messagetList) {
        List<SYS_Message> users = new ArrayList<>();
        for (int i = 0; i < messagetList.size(); i++) {
            SYS_Message user = new SYS_Message();
            JSONObject key = (JSONObject) messagetList.get(i);
            try {
                EntityUtil.setReflectModelValue(user, key);
                users.add(user);
            } catch (SecurityException e) {
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

    public static List<Sys_Approal> saveApproalJsonModel(JSONArray approalList) {
        List<Sys_Approal> approals = new ArrayList<>();
        for (int i = 0; i < approalList.size(); i++) {
            Sys_Approal approal = new Sys_Approal();
            JSONObject key = (JSONObject) approalList.get(i);
            try {
                EntityUtil.setReflectModelValue(approal, key);
                approal.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                approals.add(approal);
            } catch (SecurityException e) {
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
        return approals;
    }

    /**
     * 保存审批表数据
     *
     * @return
     */
    public static List<Sys_Process> saveProcessJsonModel(JSONArray processList, SYS_USER user, String flag, SYS_UNIT unit, ProcessService processService) {
        List<Sys_Process> processs = new ArrayList<>();
        for (int i = 0; i < processList.size(); i++) {
            Sys_Process process = new Sys_Process();
            JSONObject key = (JSONObject) processList.get(i);
            try {
                EntityUtil.setReflectModelValue(process, key);
                JSONArray cprocessList = key.getJSONArray("children");
                if (cprocessList.size() > 0) {
                    List<Sys_Process> cprocesss = new ArrayList<>();
                    for (int j = 0; j < cprocessList.size(); j++) {
                        Sys_Process cprocess = new Sys_Process();
                        JSONObject ckey = (JSONObject) cprocessList.get(j);
                        EntityUtil.setReflectModelValue(cprocess, ckey);
                        cprocess.setCreateTime(DateUtil.stringToDate(String.valueOf(ckey.get("createTimeStr"))));
                        cprocess.setProcessTime(DateUtil.stringToDate(String.valueOf(ckey.get("processTimeStr"))));
                        cprocesss.add(cprocess);
                    }
                    process.setChildren(cprocesss);
                }
                process.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                process.setProcessTime(DateUtil.stringToDate(String.valueOf(key.get("processTimeStr"))));
//                if ("上行".equals(flag)) {
//                    if (!process.getStates().contains("已审核")) {
//                        if (user.getRoles().contains("0")) {
//                            List<Sys_Process> cprocesss = new ArrayList<>();
//                            Sys_Process cprocess = new Sys_Process();
//                            BeanUtils.copyProperties(process, cprocess);
//                            Sys_Process cprocess2 = processService.selectProcessByParentId(process.getId(), "初审");
//                            if (cprocess2 != null) {
//                                cprocess.setId(cprocess2.getId());
//                            } else {
//                                String uuid = UUID.randomUUID().toString();
//                                cprocess.setId(uuid);
//                            }
//                            cprocess.setStates("初审");
//                            cprocess.setCreateTime(new Date());
//                            cprocess.setUnitName(unit.getName());
//                            cprocess.setParentId(process.getId());
//                            cprocesss.add(cprocess);
//                            process.setStates("初审");
//                            process.setChildren(cprocesss);
//                        }
//                    }
//                }
                processs.add(process);
            } catch (SecurityException e) {
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
        return processs;
    }
//    public static List<Sys_Process> saveProcessJsonModel(JSONArray processList, SYS_USER user, String flag, SYS_UNIT unit, ProcessService processService) {
//        List<Sys_Process> processs = new ArrayList<>();
//        for (int i = 0; i < processList.size(); i++) {
//            Sys_Process process = new Sys_Process();
//            JSONObject key = (JSONObject) processList.get(i);
//            try {
//                EntityUtil.setReflectModelValue(process, key);
//                JSONArray cprocessList = key.getJSONArray("children");
//                if (cprocessList.size() > 0) {
//                    List<Sys_Process> cprocesss = new ArrayList<>();
//                    for (int j = 0; j < cprocessList.size(); j++) {
//                        Sys_Process cprocess = new Sys_Process();
//                        JSONObject ckey = (JSONObject) cprocessList.get(j);
//                        EntityUtil.setReflectModelValue(cprocess, ckey);
//                        cprocess.setCreateTime(DateUtil.stringToDate(String.valueOf(ckey.get("createTimeStr"))));
//                        cprocesss.add(cprocess);
//                    }
//                    process.setChildren(cprocesss);
//                }
//                process.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
//                if ("上行".equals(flag)) {
//                    if (!process.getStates().contains("已审核")) {
//                        if (user.getRoles().contains("0")) {
//                            List<Sys_Process> cprocesss = new ArrayList<>();
//                            Sys_Process cprocess = new Sys_Process();
//                            BeanUtils.copyProperties(process, cprocess);
//                            Sys_Process cprocess2 = processService.selectProcessByParentId(process.getId(), "初审");
//                            if (cprocess2 != null) {
//                                cprocess.setId(cprocess2.getId());
//                            } else {
//                                String uuid = UUID.randomUUID().toString();
//                                cprocess.setId(uuid);
//                            }
//                            cprocess.setStates("初审");
//                            cprocess.setCreateTime(new Date());
//                            cprocess.setUnitName(unit.getName());
//                            cprocess.setParentId(process.getId());
//                            cprocesss.add(cprocess);
//                            process.setStates("初审");
//                            process.setChildren(cprocesss);
//                        }
//                    }
//                }
////                if (process.getChildren().size()>0){
////                    processs.addAll(process.getChildren());
////                }
//                processs.add(process);
//            } catch (SecurityException e) {
//                e.printStackTrace();
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (processs.size() > 0) {
//            DataManager.saveprocessData(processs, processService, "", null, "未审批");
//        }
//        return processs;
//    }

    /**
     * 获取人员数据json
     *
     * @param peopleList
     * @return
     */
    public static List<SYS_People> savePeopleJsonModel(JSONArray peopleList) {
        List<SYS_People> peoples = new ArrayList<>();
        for (int i = 0; i < peopleList.size(); i++) {
            SYS_People people = new SYS_People();
            JSONObject key = (JSONObject) peopleList.get(i);
            try {
                EntityUtil.setReflectModelValue(people, key);
                people.setTurnRankTime(DateUtil.stringToDate(String.valueOf(key.get("turnRankTimeStr"))));
                people.setPositionTime(DateUtil.stringToDate(String.valueOf(key.get("positionTimeStr"))));
                people.setPartyTime(DateUtil.stringToDate(String.valueOf(key.get("partyTimeStr"))));
                people.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                people.setBirthday(DateUtil.stringToDate(String.valueOf(key.get("birthdayStr"))));
                people.setWorkday(DateUtil.stringToDate(String.valueOf(key.get("workdayStr"))));
                people.setPositionLevelTime(DateUtil.stringToDate(String.valueOf(key.get("positionLevelTimeStr"))));
                people.setOutTime(DateUtil.stringToDate(String.valueOf(key.get("outTimeStr"))));
                people.setInsertTime(DateUtil.stringToDate(String.valueOf(key.get("insertTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                peoples.add(people);
            } catch (SecurityException e) {
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
     *
     * @param dutyList
     * @return
     */
    public static List<SYS_Duty> saveDutyJsonModel(JSONArray dutyList) {
        List<SYS_Duty> duties = new ArrayList<>();
        for (int i = 0; i < dutyList.size(); i++) {
            SYS_Duty duty = new SYS_Duty();
            JSONObject key = (JSONObject) dutyList.get(i);
            try {
                EntityUtil.setReflectModelValue(duty, key);
                duty.setServeTime(DateUtil.stringToDate(String.valueOf(key.get("serveTimeStr"))));
                duty.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                duty.setDutyTime(DateUtil.stringToDate(String.valueOf(key.get("dutyTimeStr"))));
                duty.setApprovalTime(DateUtil.stringToDate(String.valueOf(key.get("approvalTimeStr"))));
                duty.setServeApprovalTime(DateUtil.stringToDate(String.valueOf(key.get("serveApprovalTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                duties.add(duty);
            } catch (SecurityException e) {
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
     *
     * @param rankList
     * @return
     */
    public static List<SYS_Rank> saveRankJsonModel(JSONArray rankList) {
        List<SYS_Rank> ranks = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            SYS_Rank rank = new SYS_Rank();
            JSONObject key = (JSONObject) rankList.get(i);
            try {
                EntityUtil.setReflectModelValue(rank, key);
                rank.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                rank.setApprovalTime(DateUtil.stringToDate(String.valueOf(key.get("approvalTimeStr"))));
                rank.setDutyTime(DateUtil.stringToDate(String.valueOf(key.get("dutyTimeStr"))));
                rank.setRankTime(DateUtil.stringToDate(String.valueOf(key.get("rankTimeStr"))));
                rank.setDeposeTime(DateUtil.stringToDate(String.valueOf(key.get("deposeTimeStr"))));
                ranks.add(rank);
            } catch (SecurityException e) {
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
     *
     * @param educationList
     * @return
     */
    public static List<SYS_Education> saveEducationJsonModel(JSONArray educationList) {
        List<SYS_Education> educations = new ArrayList<>();
        for (int i = 0; i < educationList.size(); i++) {
            SYS_Education education = new SYS_Education();
            JSONObject key = (JSONObject) educationList.get(i);
            try {
                EntityUtil.setReflectModelValue(education, key);
                education.setEndTime(DateUtil.stringToDate(String.valueOf(key.get("endTimeStr"))));
                education.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
                education.setDegreeTime(DateUtil.stringToDate(String.valueOf(key.get("degreeTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                educations.add(education);
            } catch (SecurityException e) {
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
     *
     * @param rewardList
     * @return
     */
    public static List<SYS_Reward> saveRewardJsonModel(JSONArray rewardList) {
        List<SYS_Reward> rewards = new ArrayList<>();
        for (int i = 0; i < rewardList.size(); i++) {
            SYS_Reward reward = new SYS_Reward();
            JSONObject key = (JSONObject) rewardList.get(i);
            try {
                EntityUtil.setReflectModelValue(reward, key);
                reward.setRevocationDate(DateUtil.stringToDate(String.valueOf(key.get("revocationDateStr"))));
                reward.setCreateTime(DateUtil.stringToDate(String.valueOf(key.get("createTimeStr"))));
//                System.out.println(unit.getName()+"=>"+unit.getReferOfficialDate()+"-->"+unit.getOneClerkNum()+"==>"+String.valueOf(key.get("oneClerkNum")));
                rewards.add(reward);
            } catch (SecurityException e) {
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

    public static List<SYS_Assessment> saveAssessmentJsonModel(JSONArray assessmentList) {
        List<SYS_Assessment> assessments = new ArrayList<>();
        for (int i = 0; i < assessmentList.size(); i++) {
            SYS_Assessment assessment = new SYS_Assessment();
            JSONObject key = (JSONObject) assessmentList.get(i);
            try {
                EntityUtil.setReflectModelValue(assessment, key);
                assessments.add(assessment);
            } catch (SecurityException e) {
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
     *
     * @param dataId
     * @param dataType
     * @param unitId
     * @param dataService
     */
    public static SYS_Data saveData(String dataId, String processId, String dataType, String unitId, DataService dataService) {
        SYS_Data data = new SYS_Data();
        data.setId(dataId);
        data.setType(dataType);
        data.setOpTime(new Date());
        data.setUnitId(unitId);
        data.setDelFlag("0");
        data.setProcessId(processId);
        SYS_Data data1 = dataService.selectDataById(dataId);
        if (data1 != null) {
            dataService.updateData(data);
        } else {
            dataService.inserData(data);
        }
        return data;
    }

    /**
     * 数据
     *
     * @param dataId
     * @param dataType
     * @param unitId
     * @param dataInfoService
     * @return
     */
    public static SYS_DataInfo saveDataInfo(String dataId, String dataType, String unitId, DataInfoService dataInfoService, String table, String param, String beforeparam) {
        SYS_DataInfo data = new SYS_DataInfo();
        String id = dataId + table;
        data.setId(id);
        data.setType(dataType);
        data.setOpTime(new Date());
        data.setUnitId(unitId);
        data.setDataId(dataId);
        data.setDelFlag("0");
        data.setParam(param);
        data.setBeforeParam(beforeparam);
        data.setTableName(table);
        SYS_DataInfo data1 = dataInfoService.selectDataInfById(id);
        if (data1 != null) {
            dataInfoService.updateDataInfo(data);
        } else {
            dataInfoService.inserDataInfo(data);
        }
        return data;
    }

    /**
     * 人员数据上行对比
     *
     * @param resultMap
     * @param peoples
     * @param peopleService
     * @param localPeoples
     */
    public static void peopleDataCheck(Map<String, Object> resultMap, List<SYS_People> peoples, PeopleService peopleService, List<SYS_People> localPeoples) {
        //人员信息
        List<List<SYS_People>> peopleLists = new ArrayList<>();
        List<SYS_People> addPeoples = new ArrayList<>();
        List<SYS_People> deletePeoples = new ArrayList<>();
        List<DataModel> peopleModels = new ArrayList<>();
        for (SYS_People people : peoples) {
            //新增
            SYS_People people1 = peopleService.selectPeopleById(people.getId());
            if (people1 == null) {
                addPeoples.add(people);
            } else {
                //修改
                StringBuffer peopleStr = new StringBuffer();
                Map<String, List<Object>> map1 = CompareFileds.compareFields(people, people1, CompareFileds.PEOPLEARR);
                Map<String, String> peopleMap = CompareFileds.getPeopleMaps();
                DataModel dataModel = new DataModel();
                for (int i = 0; i < CompareFileds.PEOPLEARR.length; i++) {
                    if (map1.get(CompareFileds.PEOPLEARR[i]) != null) {
                        peopleStr.append(peopleMap.get(CompareFileds.PEOPLEARR[i]) + "：" + map1.get(CompareFileds.PEOPLEARR[i]) + "<br/>");
                    }
                }
                if (peopleStr.length() > 0) {
                    dataModel.setId(people.getId());
                    dataModel.setPeopleName(people.getName());
                    dataModel.setName(people.getName());
                    dataModel.setValue(peopleStr.toString());
                    peopleModels.add(dataModel);
                }
            }
        }
        //人员删除
        if (localPeoples != null) {
            if (localPeoples.size() > 0) {
                for (SYS_People people : localPeoples) {
                    boolean isdelete = true;
                    for (SYS_People people1 : peoples) {
                        if (people.getId().equals(people1.getId())) {
                            isdelete = false;
                        }
                    }
                    if (isdelete) {
                        deletePeoples.add(people);
                    }
                }
            }

        }
        if (deletePeoples.size() > 0) {
            resultMap.put("peopleDelete", deletePeoples);
        }
        if (addPeoples.size() > 0) {
            resultMap.put("peopleAdd", addPeoples);
        }
        if (peopleModels.size() > 0) {
            resultMap.put("peopleEdit", peopleModels);
        }
    }

    /**
     * 职务数据上行对比
     *
     * @param resultMap
     */
    public static void dutyDataCheck(Map<String, Object> resultMap, List<SYS_Duty> duties, DutyService dutyService, List<SYS_Duty> localDutys) {
        List<SYS_Duty> addPeoples = new ArrayList<>();
        List<SYS_Duty> deletePeoples = new ArrayList<>();
        List<DataModel> peopleModels = new ArrayList<>();
        for (SYS_Duty duty : duties) {
            //新增
            SYS_Duty duty1 = dutyService.selectDutyById(duty.getId());
            if (duty1 == null) {
                addPeoples.add(duty);
            } else {
                //修改
                StringBuffer peopleStr = new StringBuffer();
                Map<String, List<Object>> map1 = CompareFileds.compareFields(duty, duty1, CompareFileds.DUTYARR);
                Map<String, String> peopleMap = CompareFileds.getDutyMaps();
                DataModel dataModel = new DataModel();
                for (int i = 0; i < CompareFileds.DUTYARR.length; i++) {
                    if (map1.get(CompareFileds.DUTYARR[i]) != null) {
                        peopleStr.append(peopleMap.get(CompareFileds.DUTYARR[i]) + "：" + map1.get(CompareFileds.DUTYARR[i]) + "<br/>");
                    }
                }
                if (peopleStr.length() > 0) {
                    dataModel.setId(duty.getId());
                    dataModel.setName(duty.getName() + "/" + DateUtil.dateToString(duty.getCreateTime()));
                    dataModel.setValue(peopleStr.toString());
                    dataModel.setPeopleName(duty.getPeopleName());
                    peopleModels.add(dataModel);
                }
            }
        }
        //人员删除
        if (localDutys != null) {
            for (SYS_Duty people : localDutys) {
                boolean isdelete = true;
                for (SYS_Duty people1 : duties) {
                    if (people.getId().equals(people1.getId())) {
                        isdelete = false;
                    }
                }
                if (isdelete) {
                    deletePeoples.add(people);
                }
            }
        }
        if (deletePeoples.size() > 0) {
            resultMap.put("dutyDelete", deletePeoples);
        }
        if (addPeoples.size() > 0) {
            resultMap.put("dutyAdd", addPeoples);
        }
        if (peopleModels.size() > 0) {
            resultMap.put("dutyEdit", peopleModels);
        }
    }

    /**
     * 职级数据上行对比
     *
     * @param resultMap
     */
    public static void rankDataCheck(Map<String, Object> resultMap, List<SYS_Rank> duties, RankService rankService, List<SYS_Rank> localDutys) {
        List<SYS_Rank> addPeoples = new ArrayList<>();
        List<SYS_Rank> deletePeoples = new ArrayList<>();
        List<DataModel> peopleModels = new ArrayList<>();
        for (SYS_Rank duty : duties) {
            //新增
            SYS_Rank duty1 = rankService.selectRankById(duty.getId());
            if (duty1 == null) {
                addPeoples.add(duty);
            } else {
                //修改
                StringBuffer peopleStr = new StringBuffer();
                Map<String, List<Object>> map1 = CompareFileds.compareFields(duty, duty1, CompareFileds.RANKARR);
                Map<String, String> peopleMap = CompareFileds.getRankMaps();
                DataModel dataModel = new DataModel();
                for (int i = 0; i < CompareFileds.RANKARR.length; i++) {
                    if (map1.get(CompareFileds.RANKARR[i]) != null) {
                        peopleStr.append(peopleMap.get(CompareFileds.RANKARR[i]) + "：" + map1.get(CompareFileds.RANKARR[i]) + "<br/>");
                    }
                }
                if (peopleStr.length() > 0) {
                    dataModel.setId(duty.getId());
                    dataModel.setPeopleName(duty.getPeopleName());
                    dataModel.setName(duty.getName() + "/" + DateUtil.dateToString(duty.getCreateTime()));
                    dataModel.setValue(peopleStr.toString());
                    peopleModels.add(dataModel);
                }
            }
        }
        //人员删除
        if (localDutys != null) {
            if (localDutys.size() > 0) {
                for (SYS_Rank people : localDutys) {
                    boolean isdelete = true;
                    for (SYS_Rank people1 : duties) {
                        if (people.getId().equals(people1.getId())) {
                            isdelete = false;
                        }
                    }
                    if (isdelete) {
                        deletePeoples.add(people);
                    }
                }
            }
        }
        if (deletePeoples.size() > 0) {
            resultMap.put("rankDelete", deletePeoples);
        }
        if (addPeoples.size() > 0) {
            resultMap.put("rankAdd", addPeoples);
        }
        if (peopleModels.size() > 0) {
            resultMap.put("rankEdit", peopleModels);
        }
    }

    /**
     * 学历数据上行对比
     *
     * @param resultMap
     */
    public static void educationDataCheck(Map<String, Object> resultMap, List<SYS_Education> duties, EducationService educationService,
                                          List<SYS_Education> localEducations) {
        List<SYS_Education> addPeoples = new ArrayList<>();
        List<SYS_Education> deletePeoples = new ArrayList<>();
        List<DataModel> peopleModels = new ArrayList<>();
        for (SYS_Education duty : duties) {
            //新增
            SYS_Education duty1 = educationService.selectEducationById(duty.getId());
            if (duty1 == null) {
                addPeoples.add(duty);
            } else {
                //修改
                StringBuffer peopleStr = new StringBuffer();
                Map<String, List<Object>> map1 = CompareFileds.compareFields(duty, duty1, CompareFileds.EDUCATIONARR);
                Map<String, String> peopleMap = CompareFileds.getEducationMaps();
                DataModel dataModel = new DataModel();
                for (int i = 0; i < CompareFileds.EDUCATIONARR.length; i++) {
                    if (map1.get(CompareFileds.EDUCATIONARR[i]) != null) {
                        peopleStr.append(peopleMap.get(CompareFileds.EDUCATIONARR[i]) + "：" + map1.get(CompareFileds.EDUCATIONARR[i]) + "<br/>");
                    }
                }
                if (peopleStr.length() > 0) {
                    dataModel.setId(duty.getId());
                    dataModel.setPeopleName(duty.getPeopleName());
                    dataModel.setName(duty.getName() + "/" + DateUtil.dateToString(duty.getCreateTime()));
                    dataModel.setValue(peopleStr.toString());
                    peopleModels.add(dataModel);
                }
            }
        }
        //人员删除
        if (localEducations != null) {
            if (localEducations.size() > 0) {
                for (SYS_Education people : localEducations) {
                    boolean isdelete = true;
                    for (SYS_Education people1 : duties) {
                        if (people.getId().equals(people1.getId())) {
                            isdelete = false;
                        }
                    }
                    if (isdelete) {
                        deletePeoples.add(people);
                    }
                }
            }
        }
        if (deletePeoples.size() > 0) {
            resultMap.put("educationDelete", deletePeoples);
        }
        if (addPeoples.size() > 0) {
            resultMap.put("educationAdd", addPeoples);
        }
        if (peopleModels.size() > 0) {
            resultMap.put("educationEdit", peopleModels);
        }
    }

    /**
     * 奖惩数据上行对比
     *
     * @param resultMap
     */
    public static void rewardDataCheck(Map<String, Object> resultMap, List<SYS_Reward> duties, RewardService rewardService,
                                       List<SYS_Reward> localRewards) {
        List<SYS_Reward> addPeoples = new ArrayList<>();
        List<SYS_Reward> deletePeoples = new ArrayList<>();
        List<DataModel> peopleModels = new ArrayList<>();
        for (SYS_Reward duty : duties) {
            //新增
            SYS_Reward duty1 = rewardService.selectRewardById(duty.getId());
            if (duty1 == null) {
                addPeoples.add(duty);
            } else {
                //修改
                StringBuffer peopleStr = new StringBuffer();
                Map<String, List<Object>> map1 = CompareFileds.compareFields(duty, duty1, CompareFileds.REWARDARR);
                Map<String, String> peopleMap = CompareFileds.getRewardMaps();
                DataModel dataModel = new DataModel();
                for (int i = 0; i < CompareFileds.REWARDARR.length; i++) {
                    if (map1.get(CompareFileds.REWARDARR[i]) != null) {
                        peopleStr.append(peopleMap.get(CompareFileds.REWARDARR[i]) + "：" + map1.get(CompareFileds.REWARDARR[i]) + "<br/>");
                    }
                }
                if (peopleStr.length() > 0) {
                    dataModel.setId(duty.getId());
                    dataModel.setPeopleName(duty.getPeopleName());
                    dataModel.setName(duty.getName() + "/" + DateUtil.dateToString(duty.getCreateTime()));
                    dataModel.setValue(peopleStr.toString());
                    peopleModels.add(dataModel);
                }
            }
        }
        //人员删除
        if (localRewards != null) {
            if (localRewards.size() > 0) {
                for (SYS_Reward people : localRewards) {
                    boolean isdelete = true;
                    for (SYS_Reward people1 : duties) {
                        if (people.getId().equals(people1.getId())) {
                            isdelete = false;
                        }
                    }
                    if (isdelete) {
                        deletePeoples.add(people);
                    }
                }
            }
        }
        if (deletePeoples.size() > 0) {
            resultMap.put("rewardDelete", deletePeoples);
        }
        if (addPeoples.size() > 0) {
            resultMap.put("rewardAdd", addPeoples);
        }
        if (peopleModels.size() > 0) {
            resultMap.put("rewardEdit", peopleModels);
        }
    }

    /**
     * 考核数据上行对比
     *
     * @param resultMap
     */
    public static void assessmentDataCheck(Map<String, Object> resultMap, List<SYS_Assessment> duties, AssessmentService assessmentService,
                                           List<SYS_Assessment> localAssessments) {
        //人员信息
        List<SYS_Assessment> addPeoples = new ArrayList<>();
        List<SYS_Assessment> deletePeoples = new ArrayList<>();
        List<DataModel> peopleModels = new ArrayList<>();
        for (SYS_Assessment duty : duties) {
            //新增
            SYS_Assessment duty1 = assessmentService.selectAssessmentById(duty.getId());
            if (duty1 == null) {
                addPeoples.add(duty);
            } else {
                //修改
                StringBuffer peopleStr = new StringBuffer();
                Map<String, List<Object>> map1 = CompareFileds.compareFields(duty, duty1, CompareFileds.ASSESSMENTARR);
                Map<String, String> peopleMap = CompareFileds.getAssessmentMaps();
                DataModel dataModel = new DataModel();
                for (int i = 0; i < CompareFileds.ASSESSMENTARR.length; i++) {
                    if (map1.get(CompareFileds.ASSESSMENTARR[i]) != null) {
                        peopleStr.append(peopleMap.get(CompareFileds.ASSESSMENTARR[i]) + "：" + map1.get(CompareFileds.ASSESSMENTARR[i]) + "<br/>");
                    }
                }
                if (peopleStr.length() > 0) {
                    dataModel.setId(duty.getId());
                    dataModel.setPeopleName(duty.getPeopleName());
                    dataModel.setName(duty.getName() + "/" + duty.getYear());
                    dataModel.setValue(peopleStr.toString());
                    peopleModels.add(dataModel);
                }
            }
        }
        //人员删除
        if (localAssessments != null) {
            for (SYS_Assessment people : localAssessments) {
                boolean isdelete = true;
                for (SYS_Assessment people1 : duties) {
                    if (people.getId().equals(people1.getId())) {
                        isdelete = false;
                    }
                }
                if (isdelete) {
                    deletePeoples.add(people);
                }
            }
        }
        if (deletePeoples.size() > 0) {
            resultMap.put("assessmentDelete", deletePeoples);
        }
        if (addPeoples.size() > 0) {
            resultMap.put("assessmentAdd", addPeoples);
        }
        if (peopleModels.size() > 0) {
            resultMap.put("assessmentEdit", peopleModels);
        }
    }

    /**
     * 用户数据上行对比
     *
     * @param resultMap
     */
    public static void userDataCheck(Map<String, Object> resultMap, List<SYS_USER> duties, UserService userService, List<SYS_USER> localUsers) {
        //人员信息
        List<SYS_USER> addPeoples = new ArrayList<>();
        List<SYS_USER> deletePeoples = new ArrayList<>();
        List<DataModel> peopleModels = new ArrayList<>();
        for (SYS_USER duty : duties) {
            //新增
            SYS_USER duty1 = userService.selectUserById(duty.getId());
            if (duty1 == null) {
                addPeoples.add(duty);
            } else {
                //修改
                StringBuffer peopleStr = new StringBuffer();
                Map<String, List<Object>> map1 = CompareFileds.compareFields(duty, duty1, CompareFileds.USERARR);
                Map<String, String> peopleMap = CompareFileds.getUserMaps();
                DataModel dataModel = new DataModel();
                for (int i = 0; i < CompareFileds.USERARR.length; i++) {
                    if (map1.get(CompareFileds.USERARR[i]) != null) {
                        peopleStr.append(peopleMap.get(CompareFileds.USERARR[i]) + "：" + map1.get(CompareFileds.USERARR[i]) + "<br/>");
                    }
                }
                if (peopleStr.length() > 0) {
                    dataModel.setId(duty.getId());
                    dataModel.setName(duty.getUserAccount());
                    dataModel.setValue(peopleStr.toString());
                    peopleModels.add(dataModel);
                }
            }
        }
        if (localUsers != null) {
            //人员删除
            for (SYS_USER people : localUsers) {
                boolean isdelete = true;
                for (SYS_USER people1 : duties) {
                    if (people.getId().equals(people1.getId())) {
                        isdelete = false;
                    }
                }
                if (isdelete) {
                    deletePeoples.add(people);
                }
            }
        }
        if (deletePeoples.size() > 0) {
            resultMap.put("userDelete", deletePeoples);
        }
        if (addPeoples.size() > 0) {
            resultMap.put("userAdd", addPeoples);
        }
        if (peopleModels.size() > 0) {
            resultMap.put("userEdit", peopleModels);
        }
    }

    public static void approvalDataCheck(Map<String, Object> resultMap, List<Sys_Approal> approals, ApprovalService approvalService, UnitService unitService,
                                         String dataType, PeopleService peopleService, RankService rankService) {
        //人员信息
        List<Sys_Approal> approalList = new ArrayList<>();
        List<Sys_Approal> capproalList = new ArrayList<>();
        for (Sys_Approal approal : approals) {
            SYS_UNIT unit = unitService.selectUnitById(approal.getUnitId());
            if (unit != null) {
                if ("上行".equals(dataType)) {
                    Sys_Approal sys_approal = new Sys_Approal();
                    sys_approal.setUnitName(approal.getUnitName());
                    sys_approal.setCreateTime(approal.getCreateTime());
                    sys_approal.setDataFlag("变动数据");
                    Sys_Approal localApproval = approvalService.selectApproval(unit.getId(), "1");
                    if (localApproval != null) {
                        localApproval.setDataFlag("上行前");
                    } else {
                        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0", "在职");
                        localApproval = new Sys_Approal();
                        if (peoples != null) {
                            DataManager.getApprovalDataCell(localApproval, unit, peoples, rankService);
                        }
                        localApproval.setDataFlag("上行前");
                        String luid = UUID.randomUUID().toString();
                        localApproval.setId(luid);
                    }
                    capproalList.add(localApproval);
                    getApproalDataTow(sys_approal, approal, localApproval);
                    approal.setDataFlag("上行后");
                    String uid = UUID.randomUUID().toString();
                    approal.setId(uid);
                    capproalList.add(approal);
                    Sys_Approal approalDetail = new Sys_Approal();
                    approalDetail.setUnitName(approal.getUnitName());
                    approalDetail.setOneResearcherDraftingNum(approal.getOneResearcherDraftingNumDetail());
                    approalDetail.setTowResearcherDraftingNum(approal.getTowResearcherDraftingNumDetail());
                    approalDetail.setThreeResearcherDraftingNum(approal.getThreeResearcherDraftingNumDetail());
                    approalDetail.setFourResearcherDraftingNum(approal.getFourResearcherDraftingNumDetail());
                    approalDetail.setOneClerkDraftingNum(approal.getOneClerkDraftingNumDetail());
                    approalDetail.setTowClerkDraftingNum(approal.getTowClerkDraftingNumDetail());
                    approalDetail.setThreeClerkDraftingNum(approal.getThreeClerkDraftingNumDetail());
                    approalDetail.setFourClerkDraftingNum(approal.getFourClerkDraftingNumDetail());
                    approalDetail.setDataFlag("不占职级数人员");
                    String zuid = UUID.randomUUID().toString();
                    approalDetail.setId(zuid);
                    capproalList.add(approalDetail);
                    sys_approal.setChildren(capproalList);
                    String sysuid = UUID.randomUUID().toString();
                    sys_approal.setId(sysuid);
                    approalList.add(sys_approal);
                } else {
                    Sys_Approal localApproval = approvalService.selectApproval(unit.getId(), "0");
                    if (localApproval != null) {
                        localApproval.setDataFlag("下行前");
                        approalList.add(localApproval);
                    }
                    approal.setDataFlag("下行后");
                    approalList.add(approal);
                }
            }
        }
        if (approalList.size() > 0) {
            resultMap.put("aprovalList", approalList);
        }
    }

    public static void getApproalDataTow(Sys_Approal sys_approal, Sys_Approal approal, Sys_Approal localApproval) {
        if ((Integer.valueOf(approal.getOneResearcherDraftingNum()) - Integer.valueOf(localApproval.getOneResearcherDraftingNum())) != 0) {
            sys_approal.setOneResearcherDraftingNum(String.valueOf(Integer.valueOf(approal.getOneResearcherDraftingNum()) - Integer.valueOf(localApproval.getOneResearcherDraftingNum())));
        } else {
            sys_approal.setOneResearcherDraftingNum("");
        }
        if ((Integer.valueOf(approal.getTowResearcherDraftingNum()) - Integer.valueOf(localApproval.getTowResearcherDraftingNum())) != 0) {
            sys_approal.setTowResearcherDraftingNum(String.valueOf(Integer.valueOf(approal.getTowResearcherDraftingNum()) - Integer.valueOf(localApproval.getTowResearcherDraftingNum())));
        } else {
            sys_approal.setTowResearcherDraftingNum("");
        }
        if ((Integer.valueOf(approal.getThreeResearcherDraftingNum()) - Integer.valueOf(localApproval.getThreeResearcherDraftingNum())) != 0) {
            sys_approal.setThreeResearcherDraftingNum(String.valueOf(Integer.valueOf(approal.getThreeResearcherDraftingNum()) - Integer.valueOf(localApproval.getThreeResearcherDraftingNum())));
        } else {
            sys_approal.setThreeResearcherDraftingNum("");
        }
        if ((Integer.valueOf(approal.getFourResearcherDraftingNum()) - Integer.valueOf(localApproval.getFourResearcherDraftingNum())) != 0) {
            sys_approal.setFourResearcherDraftingNum(String.valueOf(Integer.valueOf(approal.getFourResearcherDraftingNum()) - Integer.valueOf(localApproval.getFourResearcherDraftingNum())));
        } else {
            sys_approal.setFourResearcherDraftingNum("");
        }
        if ((Integer.valueOf(approal.getOneClerkDraftingNum()) - Integer.valueOf(localApproval.getOneClerkDraftingNum())) != 0) {
            sys_approal.setOneClerkDraftingNum(String.valueOf(Integer.valueOf(approal.getOneClerkDraftingNum()) - Integer.valueOf(localApproval.getOneClerkDraftingNum())));
        } else {
            sys_approal.setOneClerkDraftingNum("");
        }
        if ((Integer.valueOf(approal.getTowClerkDraftingNum()) - Integer.valueOf(localApproval.getTowClerkDraftingNum())) != 0) {
            sys_approal.setTowClerkDraftingNum(String.valueOf(Integer.valueOf(approal.getTowClerkDraftingNum()) - Integer.valueOf(localApproval.getTowClerkDraftingNum())));
        } else {
            sys_approal.setTowClerkDraftingNum("");
        }
        if ((Integer.valueOf(approal.getThreeClerkDraftingNum()) - Integer.valueOf(localApproval.getThreeClerkDraftingNum())) != 0) {
            sys_approal.setThreeClerkDraftingNum(String.valueOf(Integer.valueOf(approal.getThreeClerkDraftingNum()) - Integer.valueOf(localApproval.getThreeClerkDraftingNum())));
        } else {
            sys_approal.setThreeClerkDraftingNum("");
        }
        if ((Integer.valueOf(approal.getFourClerkDraftingNum()) - Integer.valueOf(localApproval.getFourClerkDraftingNum())) != 0) {
            sys_approal.setFourClerkDraftingNum(String.valueOf(Integer.valueOf(approal.getFourClerkDraftingNum()) - Integer.valueOf(localApproval.getFourClerkDraftingNum())));
        } else {
            sys_approal.setFourClerkDraftingNum("");
        }
        if ((Integer.valueOf(approal.getDrafting()) - Integer.valueOf(localApproval.getDrafting())) != 0) {
            sys_approal.setDrafting(String.valueOf(Integer.valueOf(approal.getDrafting()) - Integer.valueOf(localApproval.getDrafting())));
        } else {
            sys_approal.setDrafting("");
        }
        if ((Integer.valueOf(approal.getOneTowResearcherNum()) - Integer.valueOf(localApproval.getOneTowResearcherNum())) != 0) {
            sys_approal.setOneTowResearcherNum(String.valueOf(Integer.valueOf(approal.getOneTowResearcherNum()) - Integer.valueOf(localApproval.getOneTowResearcherNum())));
        } else {
            sys_approal.setOneTowResearcherNum("");
        }
        if ((Integer.valueOf(approal.getOneResearcherNum()) - Integer.valueOf(localApproval.getOneResearcherNum())) != 0) {
            sys_approal.setOneResearcherNum(String.valueOf(Integer.valueOf(approal.getOneResearcherNum()) - Integer.valueOf(localApproval.getOneResearcherNum())));
        } else {
            sys_approal.setOneResearcherNum("");
        }
        if ((Integer.valueOf(approal.getTowResearcherNum()) - Integer.valueOf(localApproval.getTowResearcherNum())) != 0) {
            sys_approal.setTowResearcherNum(String.valueOf(Integer.valueOf(approal.getTowResearcherNum()) - Integer.valueOf(localApproval.getTowResearcherNum())));
        } else {
            sys_approal.setTowResearcherNum("");
        }
        if ((Integer.valueOf(approal.getThreeFourResearcherNum()) - Integer.valueOf(localApproval.getThreeFourResearcherNum())) != 0) {
            sys_approal.setThreeFourResearcherNum(String.valueOf(Integer.valueOf(approal.getThreeFourResearcherNum()) - Integer.valueOf(localApproval.getThreeFourResearcherNum())));
        } else {
            sys_approal.setThreeFourResearcherNum("");
        }
        if ((Integer.valueOf(approal.getThreeResearcherNum()) - Integer.valueOf(localApproval.getThreeResearcherNum())) != 0) {
            sys_approal.setThreeResearcherNum(String.valueOf(Integer.valueOf(approal.getThreeResearcherNum()) - Integer.valueOf(localApproval.getThreeResearcherNum())));
        } else {
            sys_approal.setThreeResearcherNum("");
        }
        if ((Integer.valueOf(approal.getFourResearcherNum()) - Integer.valueOf(localApproval.getFourResearcherNum())) != 0) {
            sys_approal.setFourResearcherNum(String.valueOf(Integer.valueOf(approal.getFourResearcherNum()) - Integer.valueOf(localApproval.getFourResearcherNum())));
        } else {
            sys_approal.setFourResearcherNum("");
        }
        if ((Integer.valueOf(approal.getResearcherTotal()) - Integer.valueOf(localApproval.getResearcherTotal())) != 0) {
            sys_approal.setResearcherTotal(String.valueOf(Integer.valueOf(approal.getResearcherTotal()) - Integer.valueOf(localApproval.getResearcherTotal())));
        } else {
            sys_approal.setResearcherTotal("");
        }
        if ((Integer.valueOf(approal.getOneTowClerkNum()) - Integer.valueOf(localApproval.getOneTowClerkNum())) != 0) {
            sys_approal.setOneTowClerkNum(String.valueOf(Integer.valueOf(approal.getOneTowClerkNum()) - Integer.valueOf(localApproval.getOneTowClerkNum())));
        } else {
            sys_approal.setOneTowClerkNum("");
        }
        if ((Integer.valueOf(approal.getOneClerkNum()) - Integer.valueOf(localApproval.getOneClerkNum())) != 0) {
            sys_approal.setOneClerkNum(String.valueOf(Integer.valueOf(approal.getOneClerkNum()) - Integer.valueOf(localApproval.getOneClerkNum())));
        } else {
            sys_approal.setOneClerkNum("");
        }
        if ((Integer.valueOf(approal.getTowClerkNum()) - Integer.valueOf(localApproval.getTowClerkNum())) != 0) {
            sys_approal.setTowClerkNum(String.valueOf(Integer.valueOf(approal.getTowClerkNum()) - Integer.valueOf(localApproval.getTowClerkNum())));
        } else {
            sys_approal.setTowClerkNum("");
        }
        if ((Integer.valueOf(approal.getThreeFourClerkNum()) - Integer.valueOf(localApproval.getThreeFourClerkNum())) != 0) {
            sys_approal.setThreeFourClerkNum(String.valueOf(Integer.valueOf(approal.getThreeFourClerkNum()) - Integer.valueOf(localApproval.getThreeFourClerkNum())));
        } else {
            sys_approal.setThreeFourClerkNum("");
        }
        if ((Integer.valueOf(approal.getThreeClerkNum()) - Integer.valueOf(localApproval.getThreeClerkNum())) != 0) {
            sys_approal.setThreeClerkNum(String.valueOf(Integer.valueOf(approal.getThreeClerkNum()) - Integer.valueOf(localApproval.getThreeClerkNum())));
        } else {
            sys_approal.setThreeClerkNum("");
        }
        if ((Integer.valueOf(approal.getFourClerkNum()) - Integer.valueOf(localApproval.getFourClerkNum())) != 0) {
            sys_approal.setFourClerkNum(String.valueOf(Integer.valueOf(approal.getFourClerkNum()) - Integer.valueOf(localApproval.getFourClerkNum())));
        } else {
            sys_approal.setFourClerkNum("");
        }
        if ((Integer.valueOf(approal.getClerkTotal()) - Integer.valueOf(localApproval.getClerkTotal())) != 0) {
            sys_approal.setClerkTotal(String.valueOf(Integer.valueOf(approal.getClerkTotal()) - Integer.valueOf(localApproval.getClerkTotal())));
        } else {
            sys_approal.setClerkTotal("");
        }
    }

    /**
     * 插入单位下行数据
     *
     * @param units
     * @param unitService
     * @param unitId
     * @return
     */
    public static List<SYS_UNIT> saveUnitData(List<SYS_UNIT> units, UnitService unitService, String unitId) {
        List<SYS_UNIT> unitList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            unitList.add(unit);
            SYS_UNIT unit1 = unitService.selectUnitById(unit.getId());
            if (unit1 != null) {
                unitService.updateUnit(unit);
            } else {
                unitService.insertUnit(unit);
            }
        }
        SYS_UNIT unit2 = unitService.selectUnitById(unitId);
        List<SYS_UNIT> unitss = unitService.selectAllChildUnits(unitId);
        if (unit2 != null) {
            unitss.add(unit2);
        }
        if (unitss != null) {
            for (SYS_UNIT unit : unitss) {
                boolean isd = true;
                for (SYS_UNIT unit1 : units) {
                    if (unit.getId().equals(unit1.getId())) {
                        isd = false;
                    }
                }
                if (isd) {
                    unitService.deleteUnit(unit.getId());
                }
            }
        }
        return unitList;
    }

    /**
     * 上行人员数据恢复
     *
     * @param peoples
     * @param peopleService
     * @param unitId
     * @return
     */
    public static List<SYS_People> savePeopleData(List<SYS_People> peoples, PeopleService peopleService, String unitId, UnitService unitService) {
        List<SYS_People> peopleList = new ArrayList<>();
        for (SYS_People people : peoples) {
            peopleList.add(people);
            SYS_People people1 = peopleService.selectPeopleById(people.getId());
            if (people1 != null) {
                peopleService.updatePeople(people);
            } else {
                SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
                if (unit != null) {
                    peopleService.insertPeoples(people);
                }
            }
        }
        List<SYS_People> peopless = peopleService.selectPeoplesByUnitId(unitId, "1", "在职");
        if (peopless != null) {
            for (SYS_People people : peopless) {
                boolean isd = true;
                for (SYS_People people1 : peoples) {
                    if (people.getId().equals(people1.getId())) {
                        isd = false;
                    }
                }
                if (isd) {
                    peopleService.deletePeople(people.getId());
                }
            }
        }
        return peopleList;
    }

    /**
     * 上行职级数据恢复
     *
     * @param ranks
     * @param rankService
     * @param unitId
     * @return
     */
    public static List<SYS_Rank> saveRankData(List<SYS_Rank> ranks, RankService rankService, String unitId, PeopleService peopleService,
                                              Date processDate,List<String> peopleIds) {
        List<SYS_Rank> rankList = new ArrayList<>();
        for (SYS_Rank rank : ranks) {
            if (rank.getApprovalTime() == null) {
                if (peopleIds.size()>0){
                    if (peopleIds.contains(rank.getPeopleId())){
                        rank.setApprovalTime(processDate);
                    }
                }
            }
            SYS_People people = peopleService.selectPeopleById(rank.getPeopleId());
            rankList.add(rank);
            if ("已免".equals(rank.getStatus()) && StrUtils.isBlank(rank.getServeApprovalTime())) {
                if (peopleIds.size()>0){
                    if (peopleIds.contains(rank.getPeopleId())){
                        rank.setServeApprovalTime(processDate);
                    }
                }
            }
            SYS_Rank rank1 = rankService.selectRankById(rank.getId());
            if (rank1 != null) {
                rankService.updateRank(rank);
            } else {
                if (people != null) {
                    rankService.insertRank(rank);
                }
            }
            SYS_Rank proRank = rankService.selectAprodRanksByPid(rank.getPeopleId());
            if (proRank != null) {
                people.setPositionLevel(proRank.getName());
                people.setPositionLevelTime(proRank.getCreateTime());
                peopleService.updatePeople(people);
            }
        }
        List<SYS_Rank> rankss = rankService.selectRanksByUnitId(unitId, "1");
        if (rankss != null) {
            for (SYS_Rank rank : rankss) {
                boolean isd = true;
                for (SYS_Rank rank1 : ranks) {
                    if (rank.getId().equals(rank1.getId())) {
                        isd = false;
                    }
                }
                if (isd) {
                    rankService.deleteRank(rank.getId());
                }
                SYS_Rank proRank = rankService.selectAprodRanksByPid(rank.getPeopleId());
                SYS_People people = peopleService.selectPeopleById(rank.getPeopleId());
                if (proRank != null) {
                    people.setPositionLevel(proRank.getName());
                    people.setPositionLevelTime(proRank.getCreateTime());
                    peopleService.updatePeople(people);
                }
            }
        }
        return rankList;
    }

    /**
     * 上行职务数据恢复
     *
     * @param dutys
     * @param dutyService
     * @param unitId
     * @return
     */
    public static List<SYS_Duty> saveDutyData(List<SYS_Duty> dutys, DutyService dutyService, String unitId, PeopleService peopleService) {
        List<SYS_Duty> dutyList = new ArrayList<>();
        for (SYS_Duty duty : dutys) {
            if (duty.getApprovalTime() == null) {
                duty.setApprovalTime(new Date());
            }
            dutyList.add(duty);
            if ("已免".equals(duty.getStatus()) && StrUtils.isBlank(duty.getServeApprovalTime())) {
                duty.setServeApprovalTime(new Date());
            }
            SYS_Duty duty1 = dutyService.selectDutyById(duty.getId());
            SYS_People people = peopleService.selectPeopleById(duty.getPeopleId());
            if (duty1 != null) {
                if (duty.getApprovalTime() == null) {
                    duty.setApprovalTime(new Date());
                }
                dutyService.updateDuty(duty);
            } else {
                if (people != null) {
                    dutyService.insertDuty(duty);
                }
            }
            SYS_Duty produty = dutyService.selectProDutyByPidOrderByTime(duty.getPeopleId());
            if (produty != null) {
                people.setPosition(duty.getName());
                people.setPositionTime(duty.getCreateTime());
                peopleService.updatePeople(people);
            }
        }
        List<SYS_Duty> dutyss = dutyService.selectDutysByUnitId(unitId, "1");
        if (dutyss != null) {
            for (SYS_Duty duty : dutyss) {
                boolean isd = true;
                for (SYS_Duty duty1 : dutys) {
                    if (duty.getId().equals(duty1.getId())) {
                        isd = false;
                    }
                }
                if (isd) {
                    dutyService.deleteDuty(duty.getId());
                }
                SYS_People people = peopleService.selectPeopleById(duty.getPeopleId());
                SYS_Duty produty = dutyService.selectProDutyByPidOrderByTime(duty.getPeopleId());
                if (produty != null) {
                    people.setPosition(duty.getName());
                    people.setPositionTime(duty.getCreateTime());
                    peopleService.updatePeople(people);
                }
            }
        }
        return dutyList;
    }

    /**
     * 上行学历数据恢复
     *
     * @param educations
     * @param educationService
     * @param unitId
     * @return
     */
    public static List<SYS_Education> saveEducationData(List<SYS_Education> educations, EducationService educationService, String unitId, PeopleService peopleService) {
        List<SYS_Education> educationList = new ArrayList<>();
        for (SYS_Education education : educations) {
            educationList.add(education);
            SYS_Education education1 = educationService.selectEducationById(education.getId());
            if (education1 != null) {
                educationService.updateEducation(education);
            } else {
                SYS_People people = peopleService.selectPeopleById(education.getPeopleId());
                if (people != null) {
                    educationService.insertEducation(education);
                }
            }
        }
        List<SYS_Education> educationss = educationService.selectEducationsByUnitId(unitId, "1");
        if (educationss != null) {
            for (SYS_Education education : educationss) {
                boolean isd = true;
                for (SYS_Education education1 : educations) {
                    if (education.getId().equals(education1.getId())) {
                        isd = false;
                    }
                }
                if (isd) {
                    educationService.deleteEducation(education.getId());
                }
            }
        }
        return educationList;
    }

    /**
     * 上行奖惩数据恢复
     *
     * @param rewards
     * @param rewardService
     * @param unitId
     * @return
     */
    public static List<SYS_Reward> saveRewardData(List<SYS_Reward> rewards, RewardService rewardService, String unitId, PeopleService peopleService) {
        List<SYS_Reward> rewardList = new ArrayList<>();
        for (SYS_Reward reward : rewards) {
            rewardList.add(reward);
            SYS_Reward reward1 = rewardService.selectRewardById(reward.getId());
            if (reward1 != null) {
                rewardService.updateReward(reward);
            } else {
                SYS_People people = peopleService.selectPeopleById(reward.getPeopleId());
                if (people != null) {
                    rewardService.insertReward(reward);
                }
            }
        }
        List<SYS_Reward> rewardss = rewardService.selectRewardsByUnitId(unitId, "1");
        if (rewardss != null) {
            for (SYS_Reward reward : rewardss) {
                boolean isd = true;
                for (SYS_Reward reward1 : rewards) {
                    if (reward.getId().equals(reward1.getId())) {
                        isd = false;
                    }
                }
                if (isd) {
                    rewardService.deleteReward(reward.getId());
                }
            }
        }
        return rewardList;
    }

    /**
     * 上行考核数据恢复
     *
     * @param assessments
     * @param assessmentService
     * @param unitId
     * @return
     */
    public static List<SYS_Assessment> saveAssessmentData(List<SYS_Assessment> assessments, AssessmentService assessmentService, String unitId, PeopleService peopleService) {
        List<SYS_Assessment> assessmentList = new ArrayList<>();
        for (SYS_Assessment assessment : assessments) {
            assessmentList.add(assessment);
            SYS_Assessment assessment1 = assessmentService.selectAssessmentByYear(assessment.getPeopleId(), assessment.getYear());
            if (assessment1 != null) {
                assessmentService.updateAssessment(assessment);
            } else {
                SYS_People people = peopleService.selectPeopleById(assessment.getPeopleId());
                if (people != null) {
                    assessmentService.insertAssessment(assessment);
                }
            }
        }
        List<SYS_Assessment> assessmentss = assessmentService.selectAssessmentsByUnitId(unitId, "1");
        if (assessmentss != null) {
            for (SYS_Assessment assessment : assessmentss) {
                boolean isd = true;
                for (SYS_Assessment assessment1 : assessments) {
                    if (assessment.getPeopleId().equals(assessment1.getPeopleId()) && assessment.getYear().equals(assessment1.getYear())) {
                        isd = false;
                    }
                }
                if (isd) {
                    assessmentService.deleteAssessment(assessment.getId());
                }
            }
        }
        return assessmentList;
    }

    /**
     * 上行用户数据
     *
     * @param users
     * @param userService
     * @param unitId
     * @return
     */
    public static List<SYS_USER> saveUserData(List<SYS_USER> users, UserService userService, String unitId) {
        List<SYS_USER> userList = new ArrayList<>();
        for (SYS_USER user : users) {
            userList.add(user);
            SYS_USER user1 = userService.selectUserByName(user.getUserAccount());
            if (user1 != null) {
                userService.updateUser(user);
            } else {
                userService.insertUser(user);
            }
        }
        List<SYS_USER> userss = userService.selectUsersByUnitId(unitId);
        if (userss != null) {
            for (SYS_USER user : userss) {
                boolean isd = true;
                for (SYS_USER user1 : users) {
                    if (user.getId().equals(user1.getId())) {
                        isd = false;
                    }
                }
                if (isd) {
                    userService.deleteUser(user.getId());
                }
            }
        }
        return userList;
    }

    /**
     * 上行消化表数据
     *
     * @param unitId
     * @return
     */
    public static List<SYS_Digest> saveDigestData(List<SYS_Digest> digests, DataService dataService, String unitId) {
        List<SYS_Digest> userList = new ArrayList<>();
        for (SYS_Digest user : digests) {
            userList.add(user);
            SYS_Digest user1 = dataService.selectDigestById(user.getId());
            if (user1 != null) {
                dataService.updateDigest(user);
            } else {
                dataService.insertDigest(user);
            }
        }
        return userList;
    }

    /**
     * 上行消化表数据
     *
     * @return
     */
    public static List<SYS_UNIT> saveRejectUnitData(List<SYS_UNIT> units, UnitService unitService) {
        List<SYS_UNIT> unitList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            unitList.add(unit);
            SYS_UNIT user1 = unitService.selectUnitById(unit.getId());
            if (user1 != null) {
                unitService.updateUnit(unit);
            } else {
                unitService.insertUnit(unit);
            }
        }
        return unitList;
    }

    /**
     * 上行或下行
     *
     * @param approals
     * @param approvalService
     * @param unitId
     * @return
     */
    public static List<Sys_Approal> saveApprovalData(List<Sys_Approal> approals, ApprovalService approvalService, String unitId, UnitService unitService, String flag) {
        List<Sys_Approal> approalList = new ArrayList<>();
        for (Sys_Approal approal : approals) {
            approalList.add(approal);
            Sys_Approal approal1 = approvalService.selectApprovalById(approal.getId());
            if (approal1 != null) {
                if ("1".equals(flag)) {
                    approal.setFlag("1");
                } else {
                    approal.setFlag("0");
                }
                approal.setId(approal1.getId());
                approvalService.updataApproal(approal);
            } else {
                if ("1".equals(flag)) {
                    approal.setFlag("1");
                } else {
                    approal.setFlag("0");
                }
                approvalService.insertApproal(approal);
            }
            if ("1".equals(flag)) {
                SYS_UNIT unit = unitService.selectUnitById(approal.getUnitId());
                if (unit != null) {
                    saveUnitData(unitService, unit, approal);
                }
            }
        }
        return approalList;
    }

    public static SYS_UNIT saveUnitData(UnitService unitService, SYS_UNIT unit, Sys_Approal approal) {
        unit.setOneResearcherNum(StrUtils.strToLong(approal.getOneResearcherNum()));
        unit.setTowResearcherNum(StrUtils.strToLong(approal.getTowResearcherNum()));
        unit.setOneTowResearcherNum(StrUtils.strToLong(approal.getOneTowResearcherNum()));
        unit.setThreeResearcherNum(StrUtils.strToLong(approal.getThreeResearcherNum()));
        unit.setFourResearcherNum(StrUtils.strToLong(approal.getFourResearcherNum()));
        unit.setThreeFourResearcherNum(StrUtils.strToLong(approal.getThreeFourResearcherNum()));
        unit.setOneClerkNum(StrUtils.strToLong(approal.getOneClerkNum()));
        unit.setTowClerkNum(StrUtils.strToLong(approal.getTowClerkNum()));
        unit.setOneTowClerkNum(StrUtils.strToLong(approal.getOneTowClerkNum()));
        unit.setThreeClerkNum(StrUtils.strToLong(approal.getThreeClerkNum()));
        unit.setFourClerkNum(StrUtils.strToLong(approal.getFourClerkNum()));
        unit.setThreeFourClerkNum(StrUtils.strToLong(approal.getThreeFourClerkNum()));
        unitService.updateUnit(unit);
        return unit;
    }

    public static List<Sys_Process> saveprocessInfoData(List<Sys_Process> processes, ProcessService processService) throws Exception {
        List<Sys_Process> approalList = new ArrayList<>();
        for (Sys_Process process : processes) {
            if (!StrUtils.isBlank(process.getCreateTimeStr())) {
                process.setCreateTime(DateUtil.stringToDateMM(process.getCreateTimeStr()));
            }
            if (!StrUtils.isBlank(process.getProcessTimeStr())) {
                process.setProcessTime(DateUtil.stringToDateMM(process.getProcessTimeStr()));
            }
            Sys_Process approal1 = processService.selectProcessById(process.getId());
            if (approal1 != null) {
                processService.updateProcess(process);
            } else {
                processService.insertProcess(process);
            }
            if (process.getParentId() == null) {
                if (process.getChildren() != null) {
                    for (Sys_Process cprocess : process.getChildren()) {
                        if (!StrUtils.isBlank(cprocess.getCreateTimeStr())) {
                            cprocess.setCreateTime(DateUtil.stringToDateMM(cprocess.getCreateTimeStr()));
                        }
                        if (!StrUtils.isBlank(cprocess.getProcessTimeStr())) {
                            cprocess.setProcessTime(DateUtil.stringToDateMM(cprocess.getProcessTimeStr()));
                        }
                        Sys_Process capproal1 = processService.selectProcessById(cprocess.getId());
                        if (capproal1 != null) {
                            processService.updateProcess(cprocess);
                        } else {
                            processService.insertProcess(cprocess);
                        }
                    }
                }
            }
            approalList.add(process);
        }
        return approalList;
    }

    public static Sys_Process saveProcessDate(Sys_Process process,SYS_UNIT unit,
                                              Date createDate, Date processDate, String detail)throws Exception{
        if (unit.getId().equals(process.getApprovalUnit())) {
            if (StrUtils.isBlank(createDate)) {
                process.setCreateTime(DateUtil.stringToDate(process.getCreateTimeStr()));
                if (!StrUtils.isBlank(process.getProcessTimeStr())) {
                    process.setProcessTime(DateUtil.stringToDate(process.getProcessTimeStr()));
                }
                process.setDetail(detail);
            } else {
                process.setCreateTime(createDate);
                process.setCreateTimeStr(DateUtil.dateMMToString(createDate));
                if (!StrUtils.isBlank(processDate)){
                    process.setProcessTime(processDate);
                    process.setProcessTimeStr(DateUtil.dateMMToString(processDate));
                }else {
                    process.setProcessTime(new Date());
                    process.setProcessTimeStr(DateUtil.dateMMToString(new Date()));
                }
                process.setDetail(detail);
            }
        }else {
            process.setCreateTime(DateUtil.stringToDate(process.getCreateTimeStr()));
            if (!StrUtils.isBlank(process.getProcessTimeStr())){
                process.setProcessTime(DateUtil.stringToDate(process.getProcessTimeStr()));
            }
        }
        return process;
    }
    public static List<Sys_Process> saveprocessData(List<Sys_Process> processes, ProcessService processService,
                                                    String approvalUnitName, String peopleName, SYS_USER user,
                                                    String states, UnitService unitService, SYS_UNIT unit, String flag,
                                                    Date createDate, Date processDate, String detail) throws Exception {
        List<Sys_Process> approalList = new ArrayList<>();
        for (Sys_Process process : processes) {
            process.setApproveFlag("0");
            saveProcessDate(process,unit,createDate, processDate, detail);
            Sys_Process approal1 = processService.selectProcessById(process.getId());
            if (user != null) {
                if ("1".equals(user.getRoles()) && !"下行".equals(flag)) {
                    process.setStates(states);
//                        process.setProcessTime(new Date());
                    process.setApprovalUnit(user.getUnitId());
                    process.setApprovaled("0");
                    process.setPeople(peopleName);
                    updateProcessUsed(process,processService,"0");
                }
            } else {
                if ("".equals(peopleName)) {
                    peopleName = process.getPeople();
                }
            }
            if (approal1 != null) {
                process.setPeople(peopleName);
                process.setApprovalUnitName(unit.getName());
                processService.updateProcess(process);
            } else {
                process.setPeople(peopleName);
                process.setApprovalUnitName(unit.getName());
                processService.insertProcess(process);
            }
            if (process.getParentId() == null) {
                if (process.getChildren() != null) {
                    boolean sts = false;
                    int sd = 0;
                    for (Sys_Process cprocess : process.getChildren()) {
                        cprocess.setApproveFlag("0");
                        if (!StrUtils.isBlank(process.getApprovalEve()) && user != null) {
                            if (cprocess.getApprovalUnit().equals(user.getUnitId())) {
                                cprocess.setStates("初审");
                                cprocess.setApprovaled("0");
                                cprocess.setPeople(peopleName);
                                cprocess.setApprovalUnitName(approvalUnitName);
                                cprocess.setProcessTime(new Date());
                                cprocess.setProcessTimeStr(DateUtil.dateMMToString(new Date()));
                                sd = Integer.valueOf(cprocess.getApprovalOrder());
                                sts = true;
                            }
                        }
                        saveProcessDate(cprocess,unit,createDate, processDate, detail);
                        Sys_Process capproal1 = processService.selectProcessById(cprocess.getId());
                        if (capproal1 != null) {
                            processService.updateProcess(cprocess);
                        } else {
                            processService.insertProcess(cprocess);
                        }
                    }
                    if (sts) {
                        sd = sd + 1;
                        Sys_Process sys_process = processService.selectProcessById(process.getId() + sd);
                        if (sys_process != null) {
                            process.setApprovalEve(sys_process.getApprovalUnit());
                        } else {
                            SYS_UNIT sys_unit = unitService.selectUnitById(unit.getParentId());
                            if (sys_unit != null) {
                                if ("0".equals(sys_unit.getApprovalFlag()) && !"单位".equals(sys_unit.getName())) {
                                    process.setApprovalEve(sys_unit.getId());
                                }
                            }
                        }
                        process.setStates("初审");
                        processService.updateProcess(process);
                    }
                }
            }
            approalList.add(process);
        }
        return approalList;
    }

    public static void updateProcessUsed(Sys_Process process,ProcessService processService,String used){
        if ("0".equals(process.getFlag())){
            RegModel regModel= gson.fromJson(process.getParam(), new TypeToken<RegModel>() {
            }.getType());
            if (regModel!=null){
                if (!StrUtils.isBlank(regModel.getRankProcessId())){
                    Sys_Process sys_process=processService.selectProcessById(regModel.getRankProcessId());
                    if (sys_process!=null){
                        sys_process.setUsed(used);
                        processService.updateProcess(sys_process);
                    }
                }
            }
        }
    }
    /**
     * 下行审批表
     *
     * @param processes
     * @param processService
     * @param unitService
     * @param unit
     * @return
     * @throws Exception
     */
    public static List<Sys_Process> saveAprovaledProcessData(List<Sys_Process> processes, ProcessService processService,
                                                             UnitService unitService, SYS_UNIT unit) throws Exception {
        List<Sys_Process> approalList = new ArrayList<>();
        for (Sys_Process process : processes) {
            if (!StrUtils.isBlank(process.getCreateTimeStr())) {
                process.setCreateTime(DateUtil.stringToDateMM(process.getCreateTimeStr()));
            }
            if (!StrUtils.isBlank(process.getProcessTimeStr())) {
                process.setProcessTime(DateUtil.stringToDateMM(process.getProcessTimeStr()));
            }
            Sys_Process approal1 = processService.selectProcessById(process.getId());
            if (approal1 != null) {
                processService.updateProcess(process);
            } else {
                processService.insertProcess(process);
            }
            if (process.getChildren().size() > 0) {
                for (Sys_Process cprocess : process.getChildren()) {
                    if (!StrUtils.isBlank(cprocess.getCreateTimeStr())) {
                        cprocess.setCreateTime(DateUtil.stringToDateMM(cprocess.getCreateTimeStr()));
                    }
                    if (!StrUtils.isBlank(cprocess.getProcessTimeStr())) {
                        cprocess.setProcessTime(DateUtil.stringToDateMM(cprocess.getProcessTimeStr()));
                    }
                    Sys_Process capproal1 = processService.selectProcessById(cprocess.getId());
                    if (capproal1 != null) {
                        processService.updateProcess(cprocess);
                    } else {
                        processService.insertProcess(cprocess);
                    }
                }
            }
            approalList.add(process);
        }
        return approalList;
    }

    public static Sys_Process setProcessDate(ProcessService processService, String flag, SYS_UNIT unit, String name, String param,
                                             UnitService unitService) {
        Sys_Process process = processService.selectProcessByFlag(unit.getId(), flag);
        String uuid = "";
        if (process != null) {
            process.setCreateTime(new Date());
            process.setCreateTimeStr(DateUtil.dateMMToString(new Date()));
            process.setUnitId(unit.getId());
            process.setUnitName(unit.getName());
            process.setPeople(name);
            process.setParam(param);
            process.setOldId(process.getId());
            processService.updateProcess(process);
            uuid = process.getId();
        } else {
            process = new Sys_Process();
            uuid = UUID.randomUUID().toString();
            process.setCreateTime(new Date());
            process.setCreateTimeStr(DateUtil.dateMMToString(new Date()));
            process.setId(uuid);
            process.setFlag(flag);
            process.setUnitId(unit.getId());
            process.setUnitName(unit.getName());
            process.setPeople(name);
            process.setParam(param);
            process.setStates("未审批");
            process.setOldId(uuid);
            process.setApprovaled("1");
            processService.insertProcess(process);
        }
        SYS_UNIT punit = unitService.selectUnitById(unit.getParentId());
        if (punit != null) {
            process.setApprovalEve(punit.getId());
            processService.updateProcess(process);
            if (!"0".equals(punit.getApprovalFlag())) {
                Sys_Process cprocess1 = saveChildProcessDate(processService, flag, unit, punit.getName(), param, punit.getId(), "1", uuid, process.getId() + "1");
                SYS_UNIT punit1 = unitService.selectUnitById(punit.getParentId());
                if (punit1 != null) {
                    if (!"0".equals(punit1.getApprovalFlag())) {
                        Sys_Process cprocess2 = saveChildProcessDate(processService, flag, unit, punit1.getName(), param, punit1.getId(), "2", uuid, process.getId() + "2");
                        SYS_UNIT punit2 = unitService.selectUnitById(punit1.getParentId());
                        if (punit2 != null) {
                            if (!"0".equals(punit2.getApprovalFlag())) {
                                Sys_Process cprocess3 = saveChildProcessDate(processService, flag, unit, punit2.getName(), param, punit2.getId(), "3", uuid, process.getId() + "3");
                                SYS_UNIT punit3 = unitService.selectUnitById(punit2.getParentId());
                                if (punit3 != null) {
                                    if (!"0".equals(punit3.getApprovalFlag())) {
                                        Sys_Process cprocess4 = saveChildProcessDate(processService, flag, unit, punit3.getName(), param, punit3.getId(), "4", uuid, process.getId() + "4");
                                        SYS_UNIT punit4 = unitService.selectUnitById(punit3.getParentId());
                                        if (punit4 != null) {
                                            if (!"0".equals(punit4.getApprovalFlag())) {
                                                Sys_Process cprocess5 = saveChildProcessDate(processService, flag, unit, punit4.getName(), param, punit4.getId(), "5", uuid, process.getId() + "5");
                                            } else {
                                                process.setApprovalOrder("5");
                                                process.setApprovalUnitName(punit4.getName());
                                                process.setApprovalUnit(punit4.getId());
                                                cprocess4.setApproveLink("0");
                                                processService.updateProcess(cprocess4);
                                            }
                                        }
                                    } else {
                                        process.setApprovalOrder("4");
                                        process.setApprovalUnitName(punit3.getName());
                                        process.setApprovalUnit(punit3.getId());
                                        cprocess3.setApproveLink("0");
                                        processService.updateProcess(cprocess3);
                                    }
                                }
                            } else {
                                process.setApprovalOrder("3");
                                process.setApprovalUnitName(punit2.getName());
                                process.setApprovalUnit(punit2.getId());
                                cprocess2.setApproveLink("0");
                                processService.updateProcess(cprocess2);
                            }
                        }
                    } else {
                        process.setApprovalOrder("2");
                        process.setApprovalUnitName(punit1.getName());
                        process.setApprovalUnit(punit1.getId());
                        cprocess1.setApproveLink("0");
                        processService.updateProcess(cprocess1);
                    }
                }
            } else {
                process.setApproveLink("0");
                process.setApprovalOrder("1");
                process.setApprovalUnitName(punit.getName());
                process.setApprovalUnit(punit.getId());
            }
            processService.updateProcess(process);
        }
        return process;
    }

    public static Sys_Process saveChildProcessDate(ProcessService processService, String flag, SYS_UNIT unit, String name, String param,
                                                   String approvalUnitId, String order, String pprocessId, String proId) {
        Sys_Process process = processService.selectProcessById(proId);
        String uuid = "";
        if (process != null) {
            process.setCreateTime(new Date());
            process.setCreateTimeStr(DateUtil.dateMMToString(new Date()));
            process.setUnitId(unit.getId());
            process.setUnitName(unit.getName());
            process.setPeople(name);
            process.setParam(param);
            process.setOldId(process.getId());
            process.setApprovalUnit(approvalUnitId);
            process.setApprovalOrder(order);
            process.setApprovaled("1");
            process.setApprovalUnitName(name);
            processService.updateProcess(process);
            process.setParentId(pprocessId);
            uuid = process.getId();
        } else {
            process = new Sys_Process();
            uuid = pprocessId + order;
            process.setId(uuid);
            process.setFlag(flag);
            process.setCreateTime(new Date());
            process.setCreateTimeStr(DateUtil.dateMMToString(new Date()));
            process.setUnitId(unit.getId());
            process.setUnitName(unit.getName());
            process.setPeople(name);
            process.setParam(param);
            process.setStates("未审批");
            process.setApprovalUnit(approvalUnitId);
            process.setApprovalOrder(order);
            process.setApprovaled("1");
            processService.updateProcess(process);
            process.setParentId(pprocessId);
            process.setOldId(uuid);
            process.setApprovalUnitName(name);
            processService.insertProcess(process);
        }
        return process;
    }

    public static CompleteModel getCompleteRank(RankService rankService, PeopleService peopleService, String unitId, List<CompleteModel> models, UnitService unitService) {
        int firstBatch = 0, secondBatch = 0, thirdBatch = 0, fourBatch = 0, fiveBatch = 0, sexBatch = 0, total = 0;
        CompleteModel model = new CompleteModel();
        List<SYS_People> peopleList = peopleService.selectPeoplesByUnitId(unitId, "0", "在职");
        if (peopleList != null) {
            for (SYS_People people : peopleList) {
                SYS_Rank rank = rankService.selectAprodRanksByPidAndBatch(people.getId(), "第1批");
                if (rank != null) {
                    firstBatch++;
                }
                SYS_Rank rank1 = rankService.selectAprodRanksByPidAndBatch(people.getId(), "第2批");
                if (rank1 != null) {
                    secondBatch++;
                }
                SYS_Rank rank2 = rankService.selectAprodRanksByPidAndBatch(people.getId(), "第3批");
                if (rank2 != null) {
                    thirdBatch++;
                }
                SYS_Rank rank3 = rankService.selectAprodRanksByPidAndBatch(people.getId(), "第4批");
                if (rank3 != null) {
                    fourBatch++;
                }
                SYS_Rank rank4 = rankService.selectAprodRanksByPidAndBatch(people.getId(), "第5批");
                if (rank4 != null) {
                    fiveBatch++;
                }
                SYS_Rank rank5 = rankService.selectAprodRanksByPidAndBatch(people.getId(), "第6批");
                if (rank5 != null) {
                    sexBatch++;
                }
            }
        }
        SYS_UNIT unit = unitService.selectUnitById(unitId);
        total = firstBatch + secondBatch + thirdBatch + firstBatch + fiveBatch + sexBatch;
        model.setFirstBatch(StrUtils.intToStr(firstBatch));
        model.setSecondBatch(StrUtils.intToStr(secondBatch));
        model.setThirdBatch(StrUtils.intToStr(thirdBatch));
        model.setFourBatch(StrUtils.intToStr(fourBatch));
        model.setFiveBatch(StrUtils.intToStr(fiveBatch));
        model.setSexBatch(StrUtils.intToStr(sexBatch));
        model.setTotal(StrUtils.intToStr(total));
        if (unit != null) {
            model.setName(unit.getName());
            models.add(model);
        }
        return model;
    }

    /*
     * 调出人员备份json
     */

    /**
     * 调出人员职务数据json
     *
     * @param resultMap
     * @param people
     * @param dutyService
     * @return
     */
    public static List<SYS_Duty> getOutPeopleDutyJson(Map<String, Object> resultMap, SYS_People people, DutyService dutyService) {
        List<SYS_Duty> dutyList = new ArrayList<>();
        List<SYS_Duty> duties = dutyService.selectDutysByPeopleId(people.getId());
        if (duties != null) {
            dutyList.addAll(duties);
        }
        JSONArray array = JSONArray.fromObject(dutyList);
        resultMap.put("dutyList", array);
        return dutyList;
    }

    /**
     * 调出人员 职级数据json
     *
     * @param resultMap
     * @param people
     * @param rankService
     * @return
     */
    public static List<SYS_Rank> getOutPeopleRankJson(Map<String, Object> resultMap, SYS_People people, RankService rankService) {
        List<SYS_Rank> rankList = new ArrayList<>();
        List<SYS_Rank> ranks = rankService.selectRanksByPeopleId(people.getId());
        if (ranks != null) {
            rankList.addAll(ranks);
        }
        JSONArray array = JSONArray.fromObject(rankList);
        resultMap.put("rankList", array);
        return rankList;
    }

    /**
     * 调出人员学历数据json
     *
     * @param resultMap
     * @param people
     * @param educationService
     * @return
     */
    public static List<SYS_Education> getOutPeopleEducationJson(Map<String, Object> resultMap, SYS_People people, EducationService educationService) {
        List<SYS_Education> educationList = new ArrayList<>();
        List<SYS_Education> educations = educationService.selectEducationsByPeopleId(people.getId());
        if (educations != null) {
            educationList.addAll(educations);
        }
        JSONArray array = JSONArray.fromObject(educationList);
        resultMap.put("educationList", array);
        return educationList;
    }

    /**
     * 调出人员奖惩数据json
     *
     * @param resultMap
     * @param people
     * @param rewardService
     * @return
     */
    public static List<SYS_Reward> getOutPeopleRewardJson(Map<String, Object> resultMap, SYS_People people, RewardService rewardService) {
        List<SYS_Reward> rewardList = new ArrayList<>();
        List<SYS_Reward> rewards = rewardService.selectRewardsByPeopleId(people.getId());
        if (rewards != null) {
            rewardList.addAll(rewards);
        }
        JSONArray array = JSONArray.fromObject(rewardList);
        resultMap.put("rewardList", array);
        return rewardList;
    }

    /**
     * 调出人员考核
     *
     * @param resultMap
     * @param people
     * @param assessmentService
     * @return
     */
    public static List<SYS_Assessment> getOutPeopleAssessmentJson(Map<String, Object> resultMap, SYS_People people, AssessmentService assessmentService) {
        List<SYS_Assessment> assessmentList = new ArrayList<>();
        List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByPeopleId(people.getId());
        if (assessments != null) {
            assessmentList.addAll(assessments);
        }
        JSONArray array = JSONArray.fromObject(assessmentList);
        resultMap.put("assessmentList", array);
        return assessmentList;
    }

    public static String getCustomizeData(SYS_People people, String name, AssessmentService assessmentService) throws Exception {
        String value = "";
        if ("性别".equals(name)) {
            value = people.getSex();
        } else if ("民族".equals(name)) {
            value = people.getNationality();
        } else if ("出生日期".equals(name)) {
            if (people.getBirthday() != null) {
                value = DateUtil.dateToString(people.getBirthday());
            }
        } else if ("年龄".equals(name)) {
            if (people.getBirthday() != null) {
                int age = DateUtil.getAgeByBirth(people.getBirthday());
                value = String.valueOf(age);
            }
        } else if ("参加工作时间".equals(name)) {
            if (people.getBirthday() != null) {
                value = DateUtil.dateToString(people.getWorkday());
            }
        } else if ("入党（团）时间".equals(name)) {
            if (people.getBirthday() != null) {
                value = DateUtil.dateToString(people.getPartyTime());
            }
        } else if ("政治面貌".equals(name)) {
            value = people.getParty();
        } else if ("现任职级".equals(name)) {
            value = people.getPositionLevel();
        } else if ("套转职级".equals(name)) {
            value = people.getTurnRank();
        } else if ("现任职务".equals(name)) {
            value = people.getPosition();
        } else if ("公务员登记时间".equals(name)) {
            if (people.getBirthday() != null) {
                value = DateUtil.dateToString(people.getCreateTime());
            }
        } else if ("军转干部首次确定职级不占职数".equals(name)) {
            if ("是".equals(people.getDetail())) {
                value = "是";
            } else {
                value = "否";
            }
        } else if ("实名制（单列）管理干部".equals(name)) {
            if ("是".equals(people.getRealName())) {
                value = "是";
            } else {
                value = "否";
            }
        } else if ("籍贯".equals(name)) {
            value = people.getBirthplace();
        } else if ("是否基层工作两年以上".equals(name)) {
            value = people.getBaseWorker();
        } else if ("学历".equals(name)) {
            value = people.getEducation();
        } else if (name.contains("考核")) {
            String[] kao = name.split("年");
            SYS_Assessment assessment = assessmentService.selectAssessmentByYear(people.getId(), Integer.parseInt(kao[0]));
            if (assessment != null) {
                value = assessment.getName();
            }
        }
        return value;
    }

    /**
     * 到期退休
     *
     * @return
     */
    public static Date getRetirTime(String position, Date birthday, String sex) {
        Date retirTime = new Date();
        if ("男".equals(sex)) {
            retirTime = DateUtil.addYears(birthday, 60);
        } else {
            if (position.contains("县处级正职") || position.contains("县处级副职") || position.contains("厅局级正职") || position.contains("厅局级副职")) {
                retirTime = DateUtil.addYears(birthday, 60);
            } else {
                retirTime = DateUtil.addYears(birthday, 55);
            }
        }
        return retirTime;
    }

    /**
     * 单位备份数据
     *
     * @param resultMap
     * @param unit
     * @param unitService
     * @return
     */
    public static List<SYS_UNIT> getBackDataUnitJson(Map<String, Object> resultMap, SYS_UNIT unit, UnitService unitService) {
        List<SYS_UNIT> unitList = new ArrayList<>();
        if (unit != null) {
            unitList.add(unit);
        }
        List<SYS_UNIT> cunitList = unitService.selectAllChildUnits(unit.getId());
        if (cunitList != null) {
            unitList.addAll(cunitList);
        }
        resultMap.put("date", DateUtil.dateToString(new Date()));
        JSONArray array = JSONArray.fromObject(unitList);
        resultMap.put("unitList", array);
        return unitList;
    }

    /**
     * 获取审批表备份jsan
     *
     * @param resultMap
     * @param units
     * @param processService
     * @return
     */
    public static List<Sys_Process> getProcessBackDataJson(Map<String, Object> resultMap, List<SYS_UNIT> units, ProcessService processService) {
        List<Sys_Process> processList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<Sys_Process> processes = processService.selectProcessByUnitId(unit.getId());
            if (processes != null) {
                processList.addAll(processes);
            }
        }
        JSONArray array = JSONArray.fromObject(processList);
        resultMap.put("processList", array);
        return processList;
    }

    /**
     * 获取审批表备份jsan
     *
     * @param resultMap
     * @param units
     * @return
     */
    public static List<SYS_Data> getDataBackDataJson(Map<String, Object> resultMap, List<SYS_UNIT> units, DataService dataService) {
        List<SYS_Data> dataList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<SYS_Data> sys_datas = dataService.selectDataByUnitId(unit.getId());
            if (sys_datas != null) {
                dataList.addAll(sys_datas);
            }
        }
        JSONArray array = JSONArray.fromObject(dataList);
        resultMap.put("dataList", array);
        return dataList;
    }

    /**
     * 备份dataInfo
     *
     * @param resultMap
     * @param units
     * @param dataInfoService
     * @return
     */
    public static List<SYS_DataInfo> getDataInfoBackDataJson(Map<String, Object> resultMap, List<SYS_UNIT> units, DataInfoService dataInfoService) {
        List<SYS_DataInfo> dataList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<SYS_DataInfo> sys_datas = dataInfoService.selectDataInfosByUnitId(unit.getId());
            if (sys_datas != null) {
                dataList.addAll(sys_datas);
            }
        }
        JSONArray array = JSONArray.fromObject(dataList);
        resultMap.put("dataInfoList", array);
        return dataList;
    }

    /**
     * 备份Digest
     *
     * @param resultMap
     * @param units
     * @return
     */
    public static List<SYS_Digest> getDigestBackDataJson(Map<String, Object> resultMap, List<SYS_UNIT> units, DataService dataService) {
        List<SYS_Digest> dataList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<SYS_Digest> sys_datas = dataService.selectDigestsByUnitId(unit.getId());
            if (sys_datas != null) {
                dataList.addAll(sys_datas);
            }
        }
        JSONArray array = JSONArray.fromObject(dataList);
        resultMap.put("digestList", array);
        return dataList;
    }

    /**
     * 备份Digest
     *
     * @param resultMap
     * @param units
     * @return
     */
    public static List<SYS_Message> getMessageBackDataJson(Map<String, Object> resultMap, List<SYS_UNIT> units, UserService userService) {
        List<SYS_Message> dataList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            List<SYS_Message> sys_datas = userService.selectMessages();
            if (sys_datas != null) {
                dataList.addAll(sys_datas);
            }
        }
        JSONArray array = JSONArray.fromObject(dataList);
        resultMap.put("messageList", array);
        return dataList;
    }

    /**
     * 导入备份数据
     *
     * @param units
     * @param unitService
     * @param unitId
     * @return
     */
    public static List<SYS_UNIT> saveBackUnitData(List<SYS_UNIT> units, UnitService unitService, String unitId) {
        List<SYS_UNIT> unitList = new ArrayList<>();
        for (SYS_UNIT unit : units) {
            unitList.add(unit);
            SYS_UNIT unit1 = unitService.selectUnitById(unit.getId());
            if (unit1 != null) {
                unitService.updateUnit(unit);
            } else {
                unitService.insertUnit(unit);
            }
        }
        return unitList;
    }

    /**
     * 导入审批表备份数据
     *
     * @return
     */
    public static List<Sys_Process> saveBackProcessData(List<Sys_Process> processList, ProcessService processService) {
        List<Sys_Process> unitList = new ArrayList<>();
        for (Sys_Process process : processList) {
            unitList.add(process);
            Sys_Process unit1 = processService.selectProcessById(process.getId());
            if (unit1 != null) {
                processService.updateProcess(process);
            } else {
                processService.insertProcess(process);
            }
        }
        return unitList;
    }

    /**
     * 备份人员数据导入
     *
     * @param peoples
     * @param peopleService
     * @return
     */
    public static List<SYS_People> saveBackPeopleData(List<SYS_People> peoples, PeopleService peopleService) {
        List<SYS_People> peopleList = new ArrayList<>();
        for (SYS_People people : peoples) {
            peopleList.add(people);
            SYS_People people1 = peopleService.selectPeopleById(people.getId());
            if (people1 != null) {
                peopleService.updatePeople(people);
            } else {
                peopleService.insertPeoples(people);
            }
        }
        return peopleList;
    }

    /**
     * 备份职务数据导入
     *
     * @param dutys
     * @param dutyService
     * @return
     */
    public static List<SYS_Duty> saveBackDutyData(List<SYS_Duty> dutys, DutyService dutyService) {
        List<SYS_Duty> dutyList = new ArrayList<>();
        for (SYS_Duty duty : dutys) {
            if (duty.getApprovalTime() == null) {
                duty.setApprovalTime(new Date());
            }
            dutyList.add(duty);
            SYS_Duty duty1 = dutyService.selectDutyById(duty.getId());
            if (duty1 != null) {
                if (duty.getApprovalTime() == null) {
                    duty.setApprovalTime(new Date());
                }
                dutyService.updateDuty(duty);
            } else {
                dutyService.insertDuty(duty);
            }
        }
        return dutyList;
    }

    /**
     * 备份职级数据恢复
     *
     * @param ranks
     * @param rankService
     * @return
     */
    public static List<SYS_Rank> saveBackRankData(List<SYS_Rank> ranks, RankService rankService) {
        List<SYS_Rank> rankList = new ArrayList<>();
        for (SYS_Rank rank : ranks) {
            rankList.add(rank);
            SYS_Rank rank1 = rankService.selectRankById(rank.getId());
            if (rank1 != null) {
                rankService.updateRank(rank);
            } else {
                rankService.insertRank(rank);
            }
        }
        return rankList;
    }

    /**
     * 上行奖惩数据恢复
     *
     * @param rewards
     * @param rewardService
     * @return
     */
    public static List<SYS_Reward> saveBackRewardData(List<SYS_Reward> rewards, RewardService rewardService) {
        List<SYS_Reward> rewardList = new ArrayList<>();
        for (SYS_Reward reward : rewards) {
            rewardList.add(reward);
            SYS_Reward reward1 = rewardService.selectRewardById(reward.getId());
            if (reward1 != null) {
                rewardService.updateReward(reward);
            } else {
                rewardService.insertReward(reward);
            }
        }
        return rewardList;
    }

    /**
     * 上行学历数据恢复
     *
     * @param educations
     * @param educationService
     * @return
     */
    public static List<SYS_Education> saveBackEducationData(List<SYS_Education> educations, EducationService educationService) {
        List<SYS_Education> educationList = new ArrayList<>();
        for (SYS_Education education : educations) {
            educationList.add(education);
            SYS_Education education1 = educationService.selectEducationById(education.getId());
            if (education1 != null) {
                educationService.updateEducation(education);
            } else {
                educationService.insertEducation(education);
            }
        }
        return educationList;
    }

    /**
     * 备份考核数据导入
     *
     * @param assessments
     * @param assessmentService
     * @return
     */
    public static List<SYS_Assessment> saveBackAssessmentData(List<SYS_Assessment> assessments, AssessmentService assessmentService) {
        List<SYS_Assessment> assessmentList = new ArrayList<>();
        for (SYS_Assessment assessment : assessments) {
            assessmentList.add(assessment);
            SYS_Assessment assessment1 = assessmentService.selectAssessmentByYear(assessment.getPeopleId(), assessment.getYear());
            if (assessment1 != null) {
                assessmentService.updateAssessment(assessment);
            } else {
                assessmentService.insertAssessment(assessment);
            }
        }
        return assessmentList;
    }

    /**
     * 备份考核数据导入
     *
     * @return
     */
    public static List<SYS_Digest> saveBackDigestData(List<SYS_Digest> digests, DataService dataService) {
        List<SYS_Digest> digestList = new ArrayList<>();
        for (SYS_Digest digest : digests) {
            digestList.add(digest);
            SYS_Digest digest1 = dataService.selectDigestById(digest.getId());
            if (digest1 != null) {
                dataService.updateDigest(digest);
            } else {
                dataService.insertDigest(digest);
            }
        }
        return digestList;
    }

    /**
     * 备份考核数据导入
     *
     * @return
     */
    public static List<SYS_Message> saveBackMessageData(List<SYS_Message> messages, UserService userService) {
        List<SYS_Message> digestList = new ArrayList<>();
        for (SYS_Message message : messages) {
            digestList.add(message);
            SYS_Message digest1 = userService.selectMessageById(message.getId());
            if (digest1 != null) {
                userService.updateMessage(message);
            } else {
                userService.insertMessage(message);
            }
        }
        return digestList;
    }

    /**
     * 获取 备份数据
     */
    public static void getDataInfoManager(DataInfoService dataInfoService, SYS_Data data, String unitId, UnitService unitService, Map<String, Object> resultMap) {
        List<SYS_DataInfo> sys_dataInfo = dataInfoService.selectDataInfosByDataId(data.getId(), "上行");
        if (sys_dataInfo != null) {
            Map<String, String> map = new HashMap<>();
            for (SYS_DataInfo dataInfo : sys_dataInfo) {
                if ("unit".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("unit", dataInfo.getParam());
                } else if ("people".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("people", dataInfo.getParam());
                } else if ("user".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("user", dataInfo.getParam());
                } else if ("duty".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("duty", dataInfo.getParam());
                } else if ("rank".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("rank", dataInfo.getParam());
                } else if ("education".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("education", dataInfo.getParam());
                } else if ("reward".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("reward", dataInfo.getParam());
                } else if ("assessment".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("assessment", dataInfo.getParam());
                } else if ("approal".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("approal", dataInfo.getParam());
                } else if ("digest".equals(dataInfo.getTableName()) && !StrUtils.isBlank(dataInfo.getParam())) {
                    map.put("digest", dataInfo.getParam());
                }
            }
            JSONArray arrayNull = JSONArray.fromObject(new ArrayList<>());
            if (map.containsKey("unit")) {
                List<SYS_UNIT> sys_units = gson.fromJson(map.get("unit"), new TypeToken<List<SYS_UNIT>>() {
                }.getType());
                SYS_UNIT unit = unitService.selectUnitById(unitId);
                resultMap.put("unitId", unit.getId());
                resultMap.put("date", DateUtil.dateToString(new Date()));
                resultMap.put("unitName", unit.getName());
                JSONArray array = JSONArray.fromObject(sys_units);
                resultMap.put("unitList", array);
            } else {
                resultMap.put("unitList", arrayNull);
            }
            if (map.containsKey("people")) {
                List<SYS_People> sys_peoples = gson.fromJson(map.get("people"), new TypeToken<List<SYS_People>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_peoples);
                resultMap.put("peopleList", array);
            } else {
                resultMap.put("peopleList", arrayNull);
            }
            if (map.containsKey("user")) {
                List<SYS_USER> sys_userss = gson.fromJson(map.get("user"), new TypeToken<List<SYS_USER>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_userss);
                resultMap.put("userList", array);
            } else {
                resultMap.put("userList", arrayNull);
            }
            if (map.containsKey("duty")) {
                List<SYS_Duty> sys_duties = gson.fromJson(map.get("duty"), new TypeToken<List<SYS_Duty>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_duties);
                resultMap.put("dutyList", array);
            } else {
                resultMap.put("dutyList", arrayNull);
            }
            if (map.containsKey("rank")) {
                List<SYS_Rank> sys_ranks = gson.fromJson(map.get("rank"), new TypeToken<List<SYS_Rank>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_ranks);
                resultMap.put("rankList", array);
            } else {
                resultMap.put("rankList", arrayNull);
            }
            if (map.containsKey("education")) {
                List<SYS_Education> sys_educations = gson.fromJson(map.get("education"), new TypeToken<List<SYS_Education>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_educations);
                resultMap.put("educationList", array);
            } else {
                resultMap.put("educationList", arrayNull);
            }
            if (map.containsKey("reward")) {
                List<SYS_Reward> sys_rewards = gson.fromJson(map.get("reward"), new TypeToken<List<SYS_Reward>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_rewards);
                resultMap.put("rewardList", array);
            } else {
                resultMap.put("rewardList", arrayNull);
            }
            if (map.containsKey("assessment")) {
                List<SYS_Assessment> sys_assessments = gson.fromJson(map.get("assessment"), new TypeToken<List<SYS_Assessment>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_assessments);
                resultMap.put("assessmentList", array);
            } else {
                resultMap.put("assessmentList", arrayNull);
            }
            if (map.containsKey("approval")) {
                List<Sys_Approal> sys_approals = gson.fromJson(map.get("approval"), new TypeToken<List<Sys_Approal>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_approals);
                resultMap.put("approvalList", array);
            } else {
                resultMap.put("approvalList", arrayNull);
            }
            if (map.containsKey("digest")) {
                List<SYS_Digest> sys_digests = gson.fromJson(map.get("digest"), new TypeToken<List<SYS_Digest>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(sys_digests);
                resultMap.put("digestList", array);
            } else {
                resultMap.put("digestList", arrayNull);
            }
        }
    }


    public static void saveRegModelData(RegModel model,List<String> regList,List<Map<String, Object>> rankList,PeopleService peopleService,SYS_UNIT unit){
        model.setPeopleNums(regList.get(0));
        model.setHdzhengke(regList.get(1));
        model.setHdfuke(regList.get(2));
        model.setXianyouzhengke(regList.get(3));
        model.setXianyoufuke(regList.get(4));
        model.setXianyouganbu(regList.get(5));
        model.setHezhunoneTowClerkNum(regList.get(6));
        model.setHezhunthreeFourClerkNum(regList.get(8));
        model.setXianyouoneTowClerkNum(regList.get(10));
        model.setXianyouoneClerkNum(regList.get(11));
        model.setXianyoutowClerkNum(regList.get(12));
        model.setXianyouOneTowJunZhuanNum(regList.get(13));
        model.setXianyouthreeFourClerkNum(regList.get(14));
        model.setXianyouThreeClerkNum(regList.get(15));
        model.setXianyouFourClerkNum(regList.get(16));
        model.setXianyouThreeFourJunZhuanNum(regList.get(17));
        model.setNijinshengzhengke(regList.get(18));
        model.setNijinshengfuke(regList.get(19));
        model.setNijinshengganbu(regList.get(20));
        model.setNijinshengoneClerkNum(regList.get(21));
        model.setNijinshengtowClerkNum(regList.get(22));
        model.setNijinshengThreeClerkNum(regList.get(23));
        model.setNijinshengFourClerkNum(regList.get(24));
        model.setNijinshengJianZhioneClerkNum(regList.get(25));
        model.setNijinshengJiantowClerkNum(regList.get(26));
        model.setNijinshengJianThreeClerkNum(regList.get(27));
        model.setZhengkeGaitowClerkNum(regList.get(28));
        model.setFukeGaiFourClerkNum(regList.get(29));
        model.setTiaozhengzhengke(regList.get(30));
        model.setTiaozhengfuke(regList.get(31));
        model.setTiaozhengganbu(regList.get(32));
        model.setTiaozhengoneTowClerkNum(regList.get(33));
        model.setTiaozhengoneClerkNum(regList.get(34));
        model.setTiaozhengtowClerkNum(regList.get(35));
        model.setTiaozhengOneTowJunZhuanNum(regList.get(36));
        model.setTiaozhenghreeFourClerkNum(regList.get(37));
        model.setTiaozhengThreeClerkNum(regList.get(38));
        model.setTiaozhengFourClerkNum(regList.get(39));
        model.setTiaozhengThreeFourJunZhuanNum(regList.get(40));
        List<RankModel> rankModels=new ArrayList<>();
        List<String> peopleIds=new ArrayList<>();
        for (Map<String, Object> map:rankList){
            RankModel rankModel=new RankModel();
            rankModel.setName(StrUtils.toNullStr(map.get("2")));
            rankModel.setSex(StrUtils.toNullStr(map.get("4")));
            rankModel.setEducation(StrUtils.toNullStr(map.get("6")));
            rankModel.setIdcard(StrUtils.toNullStr(map.get("8")));
            rankModel.setWorkday(StrUtils.toNullStr(map.get("10")));
            rankModel.setRenzhibumen(StrUtils.toNullStr(map.get("12")));
            rankModel.setNowDuty(StrUtils.toNullStr(map.get("14")));
            rankModel.setTongzhiwudate(StrUtils.toNullStr(map.get("17")));
            rankModel.setNowRank(StrUtils.toNullStr(map.get("20")));
            rankModel.setTongzhijiDate(StrUtils.toNullStr(map.get("23")));
            rankModel.setNirenbumen(StrUtils.toNullStr(map.get("25")));
            rankModel.setNirenduty(StrUtils.toNullStr(map.get("27")));
            rankModel.setNirenrank(StrUtils.toNullStr(map.get("29")));
            rankModel.setNimianduty(StrUtils.toNullStr(map.get("31")));
            rankModel.setNimianrank(StrUtils.toNullStr(map.get("33")));
            rankModel.setKaoheyouxiu(StrUtils.toNullStr(map.get("35")));
            rankModel.setJunzhuanganbu(StrUtils.toNullStr(map.get("38")));
            SYS_People people=peopleService.selectPeopleByIdcardAndUnitIdAndName(rankModel.getIdcard(),rankModel.getName(),unit.getId());
            if (people!=null){
                peopleIds.add(people.getId());
                rankModel.setPeopleId(people.getId());
                rankModel.setUnitId(unit.getId());
                rankModels.add(rankModel);
            }
        }
        model.setPeopleIds(peopleIds);
        model.setRankModels(rankModels);
    }
}
