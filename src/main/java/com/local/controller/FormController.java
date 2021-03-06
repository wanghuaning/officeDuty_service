package com.local.controller;

import com.local.cell.DataManager;
import com.local.cell.FormManager;
import com.local.cell.PeopleManager;
import com.local.cell.UserManager;
import com.local.entity.sys.*;
import com.local.model.FormModel;
import com.local.model.FormRankModel;
import com.local.model.RetireModel;
import com.local.service.*;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/form")
public class FormController {
    private final static Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private UnitService unitService;
    @Autowired
    private UserService userService;
    @Autowired
    private RankService rankService;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private ProcessService processService;

    @Autowired
    private AssessmentService assessmentService;

    @ApiOperation(value = "首页人员查询", notes = "首页人员查询", httpMethod = "POST", tags = "首页人员查询接口")
    @PostMapping(value = "/formInfo")
    @ResponseBody
    public String getFormInfo(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            FormModel model = new FormModel();
            List<SYS_People> incumbents = peopleService.selectIncumbentPeoplesByUnitId(arr, "在职");
            if (incumbents != null) {
                model.setIncumbent(incumbents.size());
            }
            List<SYS_UNIT> unitList=unitService.selectAllUnits(arr);
            if (unitList!=null){
                int officeNum=0;
                for (SYS_UNIT unit:unitList){
                    officeNum+=(unit.getOfficialNum()+unit.getReferOfficialNum());
                }
                model.setBianZhiNum(officeNum);
            }
            List<SYS_People> leavePeoples = peopleService.selectPeoplesByUnitIds(arr,"退休");
            if (leavePeoples != null) {
                model.setLeavePeople(leavePeoples.size());
            }
            List<SYS_People> turnPeople = peopleService.selectTrunPeoplesByUnitId(arr, "在职");
            if (turnPeople != null) {
                model.setTurnPeople(turnPeople.size());
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "首页职级查询", notes = "首页职级查询", httpMethod = "POST", tags = "首页职级查询接口")
    @PostMapping(value = "/formRankInfo")
    @ResponseBody
    public String getFormRankInfo(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
                FormRankModel model = new FormRankModel();
                List<SYS_People> peoples = peopleService.selectPeoplesByUnitIds(arr, "在职");
                if (peoples != null) {
                    FormManager.getApprovalDataCell(model, arr, peoples, rankService, approvalService,unitService);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "首页退休查询", notes = "首页退休查询", httpMethod = "POST", tags = "首页退休查询接口")
    @PostMapping(value = "/retireInfo")
    @ResponseBody
    public String getFormRetireInfo(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            //从请求的header中取出当前登录的登录
            String[] arr;
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (user == null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_OUT, null, null).getJson();
            }
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                arr = new String[]{user.getUnitId()};
            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    arr = new String[]{user.getUnitId()};
                }
            }
            RetireModel model = new RetireModel();
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitIds(arr, "在职");
            if (peoples != null) {
                int nowMonthOverChuRetire = 0, nowMonthChuJiRetire = 0, nowMonthFuChuRetire = 0, nowMonthZhengKeRetire = 0, nowMonthFuKeRetire = 0, nowMonthKeYuanRetire = 0;
                int towMonthOverChuRetire = 0, towMonthChuJiRetire = 0, towMonthFuChuRetire = 0, towMonthZhengKeRetire = 0, towMonthFuKeRetire = 0, towMonthKeYuanRetire = 0;
                int threeMonthOverChuRetire = 0, threeMonthChuJiRetire = 0, threeMonthFuChuRetire = 0, threeMonthZhengKeRetire = 0, threeMonthFuKeRetire = 0, threeMonthKeYuanRetire = 0;
                int fourMonthOverChuRetire = 0, fourMonthChuJiRetire = 0, fourMonthFuChuRetire = 0, fourMonthZhengKeRetire = 0, fourMonthFuKeRetire = 0, fourMonthKeYuanRetire = 0;
                int nowMonthOneTowDiaoRetire=0,nowMonthThreeFourDiaoRetire=0,nowMonthOneTowZhuRenRetire=0,nowMonthThreeFourZhuRenRetire=0,nowMonthOneTowKeYuanRetire=0;
                int towMonthOneTowDiaoRetire=0, towMonthThreeFourDiaoRetire=0,towMonthOneTowZhuRenRetire=0,towMonthThreeFourZhuRenRetire=0,towMonthOneTowKeYuanRetire=0;
                int threeMonthOneTowDiaoRetire=0,threeMonthThreeFourDiaoRetire=0,threeMonthOneTowZhuRenRetire=0,threeMonthThreeFourZhuRenRetire=0,threeMonthOneTowKeYuanRetire=0;
                int fourMonthOneTowDiaoRetire=0, fourMonthThreeFourDiaoRetire=0,fourMonthOneTowZhuRenRetire=0,fourMonthThreeFourZhuRenRetire=0,fourMonthOneTowKeYuanRetire=0;
                for (SYS_People people : peoples) {
                    if (people.getBirthday() != null) {
                        int bmonth=DateUtil.getMonth(people.getBirthday());
                        int nmonth=DateUtil.getMonth(new Date());
                        //获取前月的最后一天
                        Calendar ca = Calendar.getInstance();
                        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
                        int age = DateUtil.getAgeByMonth(people.getBirthday(), ca);

                        Calendar ca1 = Calendar.getInstance();
                        int month = ca1.get(Calendar.MONTH)+1;
                        ca1.set(Calendar.MONTH, month + 1);
                        ca1.set(Calendar.DAY_OF_MONTH, ca1.getActualMaximum(Calendar.DAY_OF_MONTH));
                        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                        int age1 = DateUtil.getAgeByMonth(people.getBirthday(), ca1);

                        Calendar ca2 = Calendar.getInstance();
                        int month2 = ca2.get(Calendar.MONTH)+1;
                        ca2.set(Calendar.MONTH, month2 + 2);
                        ca2.set(Calendar.DAY_OF_MONTH, ca2.getActualMaximum(Calendar.DAY_OF_MONTH));
                        int age2 = DateUtil.getAgeByMonth(people.getBirthday(), ca2);

                        Calendar ca3 = Calendar.getInstance();
                        int month3 = ca3.get(Calendar.MONTH)+1;
                        ca3.set(Calendar.MONTH, month3 + 3);
                        ca3.set(Calendar.DAY_OF_MONTH, ca3.getActualMaximum(Calendar.DAY_OF_MONTH));
                        int age3 = DateUtil.getAgeByMonth(people.getBirthday(), ca3);
                        if (!StrUtils.isBlank((people.getPosition()))){
                            if (people.getPosition().contains("县处级正职")) {
                                if (age == 60 && bmonth == nmonth) {
                                    nowMonthChuJiRetire++;
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    towMonthChuJiRetire++;
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    threeMonthChuJiRetire++;
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    fourMonthChuJiRetire++;
                                }
                            } else if (people.getPosition().contains("县处级副职")) {
                                if (age == 60 && bmonth == nmonth) {
                                    nowMonthFuChuRetire++;
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    towMonthFuChuRetire++;
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    threeMonthFuChuRetire++;
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    fourMonthFuChuRetire++;
                                }
                            } else if (people.getPosition().contains("乡科级正职")) {
                                if (people.getSex().contains("男")) {
                                    if (age == 60 && bmonth == nmonth) {
                                        nowMonthZhengKeRetire++;
                                    }
                                    if (age1 == 60 && bmonth == (nmonth+1)) {
                                        towMonthZhengKeRetire++;
                                    }
                                    if (age2 == 60 && bmonth == (nmonth+2)) {
                                        threeMonthZhengKeRetire++;
                                    }
                                    if (age3 == 60 && bmonth == (nmonth+3)) {
                                        fourMonthZhengKeRetire++;
                                    }
                                } else {
                                    if (age == 55 && bmonth == nmonth) {
                                        nowMonthZhengKeRetire++;
                                    }
                                    if (age1 == 55 && bmonth == (nmonth+1)) {
                                        towMonthZhengKeRetire++;
                                    }
                                    if (age2 == 55 && bmonth == (nmonth+2)) {
                                        threeMonthZhengKeRetire++;
                                    }
                                    if (age3 == 55 && bmonth == (nmonth+3)) {
                                        fourMonthZhengKeRetire++;
                                    }
                                }
                            } else if (people.getPosition().contains("乡科级副职")) {
                                if (people.getSex().contains("男")) {
                                    if (age == 60 && bmonth == nmonth) {
                                        nowMonthFuKeRetire++;
                                    }
                                    if (age1 == 60 && bmonth == (nmonth+1)) {
                                        towMonthFuKeRetire++;
                                    }
                                    if (age2 == 60 && bmonth == (nmonth+2)) {
                                        threeMonthFuKeRetire++;
                                    }
                                    if (age3 == 60 && bmonth == (nmonth+3)) {
                                        fourMonthFuKeRetire++;
                                    }
                                } else {
                                    if (age == 55 && bmonth == nmonth) {
                                        nowMonthFuKeRetire++;
                                    }
                                    if (age1 == 55 && bmonth == (nmonth+1)) {
                                        towMonthFuKeRetire++;
                                    }
                                    if (age2 == 55 && bmonth == (nmonth+2)) {
                                        threeMonthFuKeRetire++;
                                    }
                                    if (age3 == 55 && bmonth == (nmonth+3)) {
                                        fourMonthFuKeRetire++;
                                    }
                                }
                            } else if (people.getPosition().contains("科员")) {
                                if (people.getSex().contains("男")) {
                                    if (age == 60 && bmonth == nmonth) {
                                        nowMonthKeYuanRetire++;
                                    }
                                    if (age1 == 60 && bmonth == (nmonth+1)) {
                                        towMonthKeYuanRetire++;
                                    }
                                    if (age2 == 60 && bmonth == (nmonth+2)) {
                                        threeMonthKeYuanRetire++;
                                    }
                                    if (age3 == 60 && bmonth == (nmonth+3)) {
                                        fourMonthKeYuanRetire++;
                                    }
                                } else {
                                    if (age == 55 && bmonth == nmonth) {
                                        nowMonthKeYuanRetire++;
                                    }
                                    if (age1 == 55 && bmonth == (nmonth+1)) {
                                        towMonthKeYuanRetire++;
                                    }
                                    if (age2 == 55 && bmonth == (nmonth+2)) {
                                        threeMonthKeYuanRetire++;
                                    }
                                    if (age3 == 55 && bmonth == (nmonth+3)) {
                                        fourMonthKeYuanRetire++;
                                    }
                                }
                            } else {
                                if (age == 60 && bmonth == nmonth) {
                                    nowMonthOverChuRetire++;
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    towMonthOverChuRetire++;
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    threeMonthOverChuRetire++;
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    fourMonthOverChuRetire++;
                                }
                            }
                        }else if (!StrUtils.isBlank(people.getPositionLevel())){
                            if (people.getPositionLevel().contains("二级调研员") || people.getPositionLevel().contains("一级调研员")) {
                                if (age == 60 && bmonth == nmonth) {
                                    nowMonthOneTowDiaoRetire++;
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    towMonthOneTowDiaoRetire++;
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    threeMonthOneTowDiaoRetire++;
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    fourMonthOneTowDiaoRetire++;
                                }
                            } else if (people.getPositionLevel().contains("四级调研员") || people.getPositionLevel().contains("三级调研员")) {
                                if (age == 60 && bmonth == nmonth) {
                                    nowMonthThreeFourDiaoRetire++;
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    towMonthThreeFourDiaoRetire++;
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    threeMonthThreeFourDiaoRetire++;
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    fourMonthThreeFourDiaoRetire++;
                                }
                            } else if (people.getPositionLevel().contains("二级主任科员") || people.getPositionLevel().contains("一级主任科员")) {
                                if (people.getSex().contains("男")) {
                                    if (age == 60 && bmonth == nmonth) {
                                        nowMonthOneTowZhuRenRetire++;
                                    }
                                    if (age1 == 60 && bmonth == (nmonth+1)) {
                                        towMonthOneTowZhuRenRetire++;
//                                        System.out.println(people.getName()+"=>"+towMonthOneTowZhuRenRetire+"=>"+people.getSex()+"=>"+age1);
                                    }
                                    if (age2 == 60 && bmonth == (nmonth+2)) {
                                        threeMonthOneTowZhuRenRetire++;
                                    }
                                    if (age3 == 60 && bmonth == (nmonth+3)) {
                                        fourMonthOneTowZhuRenRetire++;
                                    }
                                } else {
                                    if (age == 55 && bmonth == nmonth) {
                                        nowMonthOneTowZhuRenRetire++;
                                    }
                                    if (age1 == 55 && bmonth == (nmonth+1)) {
                                        towMonthOneTowZhuRenRetire++;
//                                        System.out.println(people.getName()+"=>"+towMonthOneTowZhuRenRetire+"=>"+people.getSex()+"=>"+age1);
                                    }
                                    if (age2 == 55 && bmonth == (nmonth+2)) {
                                        threeMonthOneTowZhuRenRetire++;
                                    }
                                    if (age3 == 55 && bmonth == (nmonth+3)) {
                                        fourMonthOneTowZhuRenRetire++;
                                    }
                                }
                            } else if (people.getPositionLevel().contains("四级主任科员") || people.getPositionLevel().contains("三级主任科员")) {
                                if (people.getSex().contains("男")) {
                                    if (age == 60 && bmonth == nmonth) {
                                        nowMonthThreeFourZhuRenRetire++;
                                    }
                                    if (age1 == 60 && bmonth == (nmonth+1)) {
                                        towMonthThreeFourZhuRenRetire++;
                                    }
                                    if (age2 == 60 && bmonth == (nmonth+2)) {
                                        threeMonthThreeFourZhuRenRetire++;
                                    }
                                    if (age3 == 60 && bmonth == (nmonth+3)) {
                                        fourMonthThreeFourZhuRenRetire++;
                                    }
                                } else {
                                    if (age == 55 && bmonth == nmonth) {
                                        nowMonthThreeFourZhuRenRetire++;
                                    }
                                    if (age1 == 55 && bmonth == (nmonth+1)) {
                                        towMonthThreeFourZhuRenRetire++;
                                    }
                                    if (age2 == 55 && bmonth == (nmonth+2)) {
                                        threeMonthThreeFourZhuRenRetire++;
                                    }
                                    if (age3 == 55 && bmonth == (nmonth+3)) {
                                        fourMonthThreeFourZhuRenRetire++;
                                    }
                                }
                            } else if (people.getPositionLevel().contains("一级科员") || people.getPositionLevel().contains("二级科员")) {
                                if (people.getSex().contains("男")) {
                                    if (age == 60 && bmonth == nmonth) {
                                        nowMonthOneTowKeYuanRetire++;
                                    }
                                    if (age1 == 60 && bmonth == (nmonth+1)) {
                                        towMonthOneTowKeYuanRetire++;
                                    }
                                    if (age2 == 60 && bmonth == (nmonth+2)) {
                                        threeMonthOneTowKeYuanRetire++;
                                    }
                                    if (age3 == 60 && bmonth == (nmonth+3)) {
                                        fourMonthOneTowKeYuanRetire++;
                                    }
                                } else {
                                    if (age == 55 && bmonth == nmonth) {
                                        nowMonthOneTowKeYuanRetire++;
                                    }
                                    if (age1 == 55 && bmonth == (nmonth+1)) {
                                        towMonthOneTowKeYuanRetire++;
                                    }
                                    if (age2 == 55 && bmonth == (nmonth+2)) {
                                        threeMonthOneTowKeYuanRetire++;
                                    }
                                    if (age3 == 55 && bmonth == (nmonth+3)) {
                                        fourMonthOneTowKeYuanRetire++;
                                    }
                                }
                            } else {
                                if (age == 60 && bmonth == nmonth) {
                                    nowMonthOverChuRetire++;
                                }
                                if (age1 == 60 && bmonth == (nmonth+1)) {
                                    towMonthOverChuRetire++;
                                }
                                if (age2 == 60 && bmonth == (nmonth+2)) {
                                    threeMonthOverChuRetire++;
                                }
                                if (age3 == 60 && bmonth == (nmonth+3)) {
                                    fourMonthOverChuRetire++;
                                }
                            }
                        }
                    }
                }
                model.setNowMonthOverChuRetire(nowMonthOverChuRetire);
                model.setNowMonthChuJiRetire(nowMonthChuJiRetire);
                model.setNowMonthFuChuRetire(nowMonthFuChuRetire);
                model.setNowMonthZhengKeRetire(nowMonthZhengKeRetire);
                model.setNowMonthFuKeRetire(nowMonthFuKeRetire);
                model.setNowMonthKeYuanRetire(nowMonthKeYuanRetire);
                model.setNowMonthOneTowDiaoRetire(nowMonthOneTowDiaoRetire);
                model.setNowMonthThreeFourDiaoRetire(nowMonthThreeFourDiaoRetire);
                model.setNowMonthOneTowZhuRenRetire(nowMonthOneTowZhuRenRetire);
                model.setNowMonthThreeFourZhuRenRetire(nowMonthThreeFourZhuRenRetire);
                model.setNowMonthOneTowKeYuanRetire(nowMonthOneTowKeYuanRetire);
                model.setNowMonthTotalRetire(nowMonthOverChuRetire + nowMonthChuJiRetire + nowMonthFuChuRetire + nowMonthZhengKeRetire + nowMonthFuKeRetire + nowMonthKeYuanRetire+
                        nowMonthOneTowDiaoRetire+nowMonthThreeFourDiaoRetire+nowMonthOneTowZhuRenRetire+nowMonthThreeFourZhuRenRetire+nowMonthOneTowKeYuanRetire);

                model.setTowMonthOverChuRetire(towMonthOverChuRetire);
                model.setTowMonthChuJiRetire(towMonthChuJiRetire);
                model.setTowMonthFuChuRetire(towMonthFuChuRetire);
                model.setTowMonthZhengKeRetire(towMonthZhengKeRetire);
                model.setTowMonthFuKeRetire(towMonthFuKeRetire);
                model.setTowMonthKeYuanRetire(towMonthKeYuanRetire);
                model.setTowMonthOneTowDiaoRetire(towMonthOneTowDiaoRetire);
                model.setTowMonthThreeFourDiaoRetire(towMonthThreeFourDiaoRetire);
                model.setTowMonthOneTowZhuRenRetire(towMonthOneTowZhuRenRetire);
                model.setTowMonthThreeFourZhuRenRetire(towMonthThreeFourZhuRenRetire);
                model.setTowMonthOneTowKeYuanRetire(towMonthOneTowKeYuanRetire);
                model.setTowMonthTotalRetire(towMonthOverChuRetire + towMonthChuJiRetire + towMonthFuChuRetire + towMonthZhengKeRetire + towMonthFuKeRetire + towMonthKeYuanRetire+
                        towMonthOneTowDiaoRetire+towMonthThreeFourDiaoRetire+towMonthOneTowZhuRenRetire+towMonthThreeFourZhuRenRetire+towMonthOneTowKeYuanRetire);

                model.setThreeMonthOverChuRetire(threeMonthOverChuRetire);
                model.setThreeMonthChuJiRetire(threeMonthChuJiRetire);
                model.setThreeMonthFuChuRetire(threeMonthFuChuRetire);
                model.setThreeMonthZhengKeRetire(threeMonthZhengKeRetire);
                model.setThreeMonthFuKeRetire(threeMonthFuKeRetire);
                model.setThreeMonthKeYuanRetire(threeMonthKeYuanRetire);
                model.setThreeMonthOneTowDiaoRetire(threeMonthOneTowDiaoRetire);
                model.setThreeMonthThreeFourDiaoRetire(threeMonthThreeFourDiaoRetire);
                model.setThreeMonthOneTowZhuRenRetire(threeMonthOneTowZhuRenRetire);
                model.setThreeMonthThreeFourZhuRenRetire(threeMonthThreeFourZhuRenRetire);
                model.setThreeMonthOneTowKeYuanRetire(threeMonthOneTowKeYuanRetire);
                model.setThreeMonthTotalRetire(threeMonthOverChuRetire + threeMonthChuJiRetire + threeMonthFuChuRetire + threeMonthZhengKeRetire + threeMonthFuKeRetire + threeMonthKeYuanRetire+
                        threeMonthOneTowDiaoRetire+threeMonthThreeFourDiaoRetire+threeMonthOneTowZhuRenRetire+threeMonthThreeFourZhuRenRetire+threeMonthOneTowKeYuanRetire);

                model.setFourMonthOverChuRetire(fourMonthOverChuRetire);
                model.setFourMonthChuJiRetire(fourMonthChuJiRetire);
                model.setFourMonthFuChuRetire(fourMonthFuChuRetire);
                model.setFourMonthZhengKeRetire(fourMonthZhengKeRetire);
                model.setFourMonthFuKeRetire(fourMonthFuKeRetire);
                model.setFourMonthKeYuanRetire(fourMonthKeYuanRetire);
                model.setFourMonthOneTowDiaoRetire(fourMonthOneTowDiaoRetire);
                model.setFourMonthThreeFourDiaoRetire(fourMonthThreeFourDiaoRetire);
                model.setFourMonthOneTowZhuRenRetire(fourMonthOneTowZhuRenRetire);
                model.setFourMonthThreeFourZhuRenRetire(fourMonthThreeFourZhuRenRetire);
                model.setFourMonthOneTowKeYuanRetire(fourMonthOneTowKeYuanRetire);
                model.setFourMonthTotalRetire(fourMonthOverChuRetire + fourMonthChuJiRetire + fourMonthFuChuRetire + fourMonthZhengKeRetire + fourMonthFuKeRetire + fourMonthKeYuanRetire+
                        fourMonthOneTowDiaoRetire+fourMonthThreeFourDiaoRetire+fourMonthOneTowZhuRenRetire+fourMonthThreeFourZhuRenRetire+fourMonthOneTowKeYuanRetire);
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "性别统计查询", notes = "性别统计查询", httpMethod = "POST", tags = "性别统计查询接口")
    @PostMapping(value = "/sexInfo")
    @ResponseBody
    public String getSexData(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            FormModel model = new FormModel();
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitIds(arr, "在职");
            if (peoples != null) {
                int man2 = 0, woman2 = 0, man3 = 0, woman3 = 0, man4 = 0, woman4 = 0, man5 = 0, woman5 = 0, man6 = 0, woman6 = 0;
                for (SYS_People people : peoples) {
                    int age = 0;
                    if (people.getBirthday() != null) {
                        age = DateUtil.getAgeByBirth(people.getBirthday());
                    }
                    if (people.getSex().contains("男")) {
                        if (age > 19 && age < 30) {
                            man2++;
                        } else if (age > 29 && age < 40) {
                            man3++;
                        } else if (age > 39 && age < 50) {
                            man4++;
                        } else if (age > 49 && age < 60) {
                            man5++;
                        } else if (age > 59) {
                            man6++;
                        }
                    } else {
                        if (age > 19 && age < 30) {
                            woman2++;
                        } else if (age > 29 && age < 40) {
                            woman3++;
                        } else if (age > 39 && age < 50) {
                            woman4++;
                        } else if (age > 49 && age < 60) {
                            woman5++;
                        } else if (age > 59) {
                            woman6++;
                        }
                    }
                }
                model.setMan2(man2);
                model.setMan3(man3);
                model.setMan4(man4);
                model.setMan5(man5);
                model.setMan6(man6);
                model.setWoman2(woman2);
                model.setWoman3(woman3);
                model.setWoman4(woman4);
                model.setWoman5(woman5);
                model.setWoman6(woman6);
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "党员统计查询", notes = "党员统计查询", httpMethod = "POST", tags = "党员统计查询接口")
    @PostMapping(value = "/partyInfo")
    @ResponseBody
    public String getPartyData(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            FormModel model = new FormModel();
            int notP = 0;
            List<SYS_People> party = peopleService.selectPartyPeoplesByUnitIds(arr, "中共党员", "在职");
            if (party != null) {
                model.setParty(party.size());
                notP += party.size();
            }
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitIds(arr, "在职");
            if (peoples != null) {
                model.setNotParty(peoples.size() - notP);
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "职务统计查询", notes = "职务统计查询", httpMethod = "POST", tags = "职务统计查询接口")
    @PostMapping(value = "/dutyInfo")
    @ResponseBody
    public String getDutyData(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            FormModel model = new FormModel();
            int fukeN=0,zhengKeN=0,zhengChuN=0,fuChuN=0;
            List<SYS_People> fuke = peopleService.selectPeoplesByUnitIdsAndDuty(arr, "乡科级副职", "在职");
            if (fuke != null) {
                model.setFuKe(fuke.size());
                fukeN+=fuke.size();
            }
            List<SYS_People> zhengKe = peopleService.selectPeoplesByUnitIdsAndDuty(arr, "乡科级正职", "在职");
            if (zhengKe != null) {
                model.setZhengKe(zhengKe.size());
                zhengKeN+=zhengKe.size();
            }
            List<SYS_People> fuChu = peopleService.selectPeoplesByUnitIdsAndDuty(arr, "县处级副职", "在职");
            if (fuChu != null) {
                model.setFuChu(fuChu.size());
                fuChuN+=fuChu.size();
            }
            List<SYS_People> zhengChu = peopleService.selectPeoplesByUnitIdsAndDuty(arr, "县处级正职", "在职");
            if (zhengChu != null) {
                model.setZhengChu(zhengChu.size());
                zhengChuN+=zhengChu.size();
            }
            List<SYS_People> all = peopleService.selectPeoplesByUnitIdsAndAllDuty(arr,"在职");
            if (all != null) {
                model.setQita(all.size()-(zhengChuN+fuChuN+zhengKeN+fukeN));
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "考核统计查询", notes = "考核统计查询", httpMethod = "POST", tags = "考核统计查询接口")
    @PostMapping(value = "/assessmentInfo")
    @ResponseBody
    public String getAssessmentData(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            FormModel model = new FormModel();
            int year = DateUtil.getYear(new Date());
            model.setFiveYear(year);
            model.setFourYear((year - 1));
            model.setThreeYear((year - 2));
            model.setTowYear((year - 3));
            model.setOneYear((year - 4));
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitIds(arr, "在职");
            if (peoples != null) {
                int oneYearYouXiu = 0, towYearYouXiu = 0, threeYearYouXiu = 0, fourYearYouXiu = 0, fiveYearYouXiu = 0, oneYearHeGe = 0, towYearHeGe = 0, threeYearHeGe = 0;
                int fourYearHeGe = 0, fiveYearHeGe = 0, oneYearNotGe = 0, towYearNotGe = 0, threeYearNotGe = 0, fourYearNotGe = 0, fiveYearNotGe = 0;
                for (SYS_People people : peoples) {
                    SYS_Assessment assessment1 = assessmentService.selectAssessmentByYear(people.getId(), (year - 4));
                    if (assessment1 != null) {
                        if (assessment1.getName().contains("优秀")) {
                            oneYearYouXiu++;
                        } else if (assessment1.getName().contains("称职") || assessment1.getName().contains("合格") || assessment1.getName().contains("基本合格") || assessment1.getName().contains("基本称职")) {
                            oneYearHeGe++;
                        } else {
                            oneYearNotGe++;
                        }
                    }
                    SYS_Assessment assessment2 = assessmentService.selectAssessmentByYear(people.getId(), (year - 3));
                    if (assessment2 != null) {
                        if (assessment2.getName().contains("优秀")) {
                            towYearYouXiu++;
                        } else if (assessment2.getName().contains("称职") || assessment2.getName().contains("合格") || assessment2.getName().contains("基本合格") || assessment2.getName().contains("基本称职")) {
                            towYearHeGe++;
                        } else {
                            towYearNotGe++;
                        }
                    }
                    SYS_Assessment assessment3 = assessmentService.selectAssessmentByYear(people.getId(), (year - 2));
                    if (assessment3 != null) {
                        if (assessment3.getName().contains("优秀")) {
                            threeYearYouXiu++;
                        } else if (assessment3.getName().contains("称职") || assessment3.getName().contains("合格") || assessment3.getName().contains("基本合格") || assessment3.getName().contains("基本称职")) {
                            threeYearHeGe++;
                        } else {
                            threeYearNotGe++;
                        }
                    }
                    SYS_Assessment assessment4 = assessmentService.selectAssessmentByYear(people.getId(), (year - 1));
                    if (assessment4 != null) {
                        if (assessment4.getName().contains("优秀")) {
                            fourYearYouXiu++;
                        } else if (assessment4.getName().contains("称职") || assessment4.getName().contains("合格") || assessment4.getName().contains("基本合格") || assessment4.getName().contains("基本称职")) {
                            fourYearHeGe++;
                        } else {
                            fourYearNotGe++;
                        }
                    }
                    SYS_Assessment assessment5 = assessmentService.selectAssessmentByYear(people.getId(), (year));
                    if (assessment5 != null) {
                        if (assessment5.getName().contains("优秀")) {
                            fiveYearYouXiu++;
                        } else if (assessment5.getName().contains("称职") || assessment5.getName().contains("合格") || assessment5.getName().contains("基本合格") || assessment5.getName().contains("基本称职")) {
                            fiveYearHeGe++;
                        } else {
                            fiveYearNotGe++;
                        }
                    }
                }
                model.setOneYearYouXiu(oneYearYouXiu);
                model.setOneYearHeGe(oneYearHeGe);
                model.setOneYearNotGe(oneYearNotGe);
                model.setTowYearYouXiu(towYearYouXiu);
                model.setTowYearHeGe(towYearHeGe);
                model.setTowYearNotGe(towYearNotGe);
                model.setThreeYearYouXiu(threeYearYouXiu);
                model.setThreeYearHeGe(threeYearHeGe);
                model.setThreeYearNotGe(threeYearNotGe);
                model.setFourYearYouXiu(fourYearYouXiu);
                model.setFourYearHeGe(fourYearHeGe);
                model.setFourYearNotGe(fourYearNotGe);
                model.setFiveYearYouXiu(fiveYearYouXiu);
                model.setFiveYearHeGe(fiveYearHeGe);
                model.setFiveYearNotGe(fiveYearNotGe);
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "首页消息查询", notes = "首页消息查询", httpMethod = "GET", tags = "首页消息查询接口")
    @GetMapping(value = "/formMessage")
    @ResponseBody
    public String getFormMessage(HttpServletRequest request) {
        try {
            //从请求的header中取出当前登录的登录
            List<SYS_Message> messages = userService.selectMessages();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, messages, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "通知消息", notes = "通知消息", httpMethod = "GET", tags = "通知消息接口")
    @GetMapping(value = "/notice")
    @ResponseBody
    public String getNoticeData() {
        try {
            List<SYS_Message> messages = userService.selectMessages();
            return new Result(ResultCode.SUCCESS.toString(), "'数据库成功备份'", messages, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), "数据库备份失败", null, null).getJson();
        }
    }

    @ApiOperation(value = "审批详情", notes = "审批详情", httpMethod = "POST", tags = "审批详情接口")
    @PostMapping(value = "/processTuoPuLoad")
    @ResponseBody
    public String processTuoPuLoad(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            FormModel model=new FormModel();
            List<Sys_Process> processList=processService.selectProcesssByUnitIds(arr);
            if (user != null) {
                SYS_UNIT approvaUnit = unitService.selectApprovalUnit(user.getUnitId());
                if (approvaUnit != null) {
                    List<Sys_Process> zprocessList = processService.selectProcesssByUnitIdsAndZuZhiBu(arr, approvaUnit.getId());
                    if (zprocessList!=null){
                        model.setZuZhiBuNuM(zprocessList.size());
                    }
                }
            }
            if (processList!=null){
                model.setDanWeiNum(processList.size());
            }
            List<Sys_Process> shangProcessList=processService.selectShangProcesssByUnitIds(arr);
            if (shangProcessList!=null){
                model.setZhuGuanNum(shangProcessList.size());
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "消息队列", notes = "消息队列", httpMethod = "POST", tags = "消息队列接口")
    @PostMapping(value = "/noticeUl")
    @ResponseBody
    public String noticeUl(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            int index=0;
            List<FormModel> modelList=new ArrayList<>();
            String[] statess={"未审批","初审"};
            List<Sys_Process> processList=processService.selectApprProcessByStates(statess);
            if (processList!=null){
                for (Sys_Process process:processList){
                    index++;
                    FormModel model=new FormModel();
                    model.setOrder(index);
                    model.setTitle("流程未审批");
                    model.setUnitName(process.getUnitName());
                    if ("0".equals(process.getFlag())){
                        model.setDetail("备案审批表");
                    }else {
                        model.setDetail("职数审批表");
                    }
                    modelList.add(model);
                }
            }
            List<SYS_People> peopleList = new ArrayList<>();
            PeopleManager.getRetireInfoData(peopleList, arr, "全部",peopleService,unitService);
            if (peopleList.size()>0){
                for (SYS_People people:peopleList){
                    index++;
                    FormModel model=new FormModel();
                    model.setOrder(index);
                    model.setTitle("到期退休");
                    model.setUnitName(people.getUnitName());
                    model.setDetail(people.getName());
                    modelList.add(model);
                }
            }
            List<SYS_Message> messageList=userService.selectMessages();
            if (messageList!=null){
                for (SYS_Message message:messageList){
                    index++;
                    FormModel model=new FormModel();
                    model.setOrder(index);
                    model.setTitle("通知消息");
                    model.setDetail(message.getName());
                    modelList.add(model);
                }
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, modelList, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "审批提醒", notes = "审批提醒", httpMethod = "POST", tags = "审批提醒")
    @PostMapping(value = "/processDetailForm")
    @ResponseBody
    public String processDetailForm(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit) {
        try {
            String[] arr;
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            FormModel model=new FormModel();
            int size=0;
            List<Sys_Process> processListc=processService.selectProcesssByUnitIdsAndFlag(arr,"初审");
            if (processListc!=null){
                size+=processListc.size();
            }
            List<Sys_Process> processList=processService.selectProcesssByUnitIdsAndFlag(arr,"未审批");
            if (processList!=null){
                size+=processList.size();
            }
            model.setNotApprNum(size);
            List<Sys_Process> processList1=processService.selectProcesssByUnitIdsAndFlag(arr,"已审核");
            if (processList1!=null){
                model.setApprEdNum(processList1.size());
            }
            List<Sys_Process> processList2=processService.selectProcesssByUnitIdsAndFlag(arr,"已驳回");
            if (processList2!=null){
                model.setNotApprEdNum(processList2.size());
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "职级详情", notes = "审批提醒", httpMethod = "POST", tags = "审批提醒")
    @PostMapping(value = "/rankDetail")
    @ResponseBody
    public String rankDetail(HttpServletRequest request, @RequestParam(value = "isChild", required = false) String isChild, @RequestParam(value = "childUnit", required = false) String childUnit,
                             @RequestParam(value = "title", required = false) String title,@RequestParam(value = "name", required = false) String name) {
        try {
            String[] arr;
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            List<FormRankModel> modelList = new ArrayList<>();
            if (StrUtils.isBlank(name)){
                for (int i=0; i<arr.length;i++){
                    String unitId=arr[i];
                    SYS_UNIT unit=unitService.selectUnitById(arr[i]);
                    if (unit!=null){
                        FormRankModel model = new FormRankModel();
                        model.setId(unit.getId());
                        model.setUnitName(unit.getName());
                        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0","在职");
                        if (peoples!=null){
                            FormManager.getApprovalDataCellByTitle(model, unit, peoples, rankService,title);
                        }
                        modelList.add(model);
                    }
                }
            }else {
                List<SYS_UNIT> unitList=unitService.selectAllUnitsByName(arr,name);
                if (unitList!=null){
                    for (SYS_UNIT unit:unitList){
                        if (unit!=null){
                            FormRankModel model = new FormRankModel();
                            model.setId(unit.getId());
                            model.setUnitName(unit.getName());
                            List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0","在职");
                            if (peoples!=null){
                                FormManager.getApprovalDataCellByTitle(model, unit, peoples, rankService,title);
                            }
                            modelList.add(model);
                        }
                    }
                }
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, modelList, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
}
