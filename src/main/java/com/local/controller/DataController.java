package com.local.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.local.cell.DataManager;
import com.local.cell.UserManager;
import com.local.common.filter.FileUtil;
import com.local.util.ZipUtil;
import com.local.entity.sys.*;
import com.local.model.*;
import com.local.service.*;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.json.entity.JsonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

@RestController
@CrossOrigin
@RequestMapping("/api/data")
public class DataController {
    private final File jsonFile = File.createTempFile("downloadJson", ".json");//创建临时文件
    private final static Logger logger = LoggerFactory.getLogger(DataController.class);
    private final static Gson gson = new Gson();
    @Autowired
    private UnitService unitService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private RankService rankService;

    @Autowired
    private EducationService educationService;
    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private DutyService dutyService;
    @Autowired
    private RewardService rewardService;

    @Autowired
    private DataInfoService dataInfoService;
    @Autowired
    private DataService dataService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProcessService processService;
    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CodeService codeService;

    public DataController() throws IOException {
    }

    @ApiOperation(value = "市级机关公务员职级职数使用审批表", notes = "市级机关公务员职级职数使用审批表", httpMethod = "POST", tags = "市级机关公务员职级职数使用审批表")
    @PostMapping(value = "/approvalData")
    @ResponseBody
    public String getApprovalData(@RequestParam(value = "unitName", required = false) String unitName) {
        SYS_UNIT unit = unitService.selectUnitByName(unitName);
        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0", "在职");
        Sys_Approal approalModel = new Sys_Approal();
        if (peoples != null) {
            Sys_Approal approalNow = approvalService.selectApproval(unit.getId(), "0");
            if (approalNow != null) {
                approalModel = approalNow;
            } else {
                DataManager.getApprovalDataCell(approalModel, unit, peoples, rankService);
            }
            return new Result(ResultCode.SUCCESS.toString(), unitName, approalModel, null).getJson();
        } else {
            return new Result(ResultCode.ERROR.toString(), "无人员！", null, null).getJson();
        }
    }

    @ApiOperation(value = "保存市级机关公务员职级职数使用审批表", notes = "保存市级机关公务员职级职数使用审批表", httpMethod = "POST", tags = "保存市级机关公务员职级职数使用审批表接口")
    @PostMapping(value = "/editApprovalData")
    @ResponseBody
    public String editApprovalData(@Validated @RequestBody Sys_Approal approal) {
        try {
            SYS_UNIT unit = unitService.selectUnitById(approal.getUnitId());
            Sys_Approal approalNow = approvalService.selectApproval(approal.getUnitId(), "0");
            if (approalNow != null) {
                approal.setId(approalNow.getId());
                BeanUtils.copyProperties(approal, approalNow);
                approalNow.setCreateTime(new Date());
                approvalService.updataApproal(approalNow);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, approal, null).getJson();
            } else {
                String uuid = UUID.randomUUID().toString();
                approal.setId(uuid);
                approal.setCreateTime(new Date());
                approvalService.insertApproal(approal);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, approal, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "提交市级机关公务员职级职数使用审批表", notes = "提交市级机关公务员职级职数使用审批表", httpMethod = "POST", tags = "提交市级机关公务员职级职数使用审批表接口")
    @PostMapping(value = "/submitApproval")
    @ResponseBody
    public String submitApproval(@Validated @RequestBody Sys_Approal approal) {
        try {
            SYS_UNIT unit = unitService.selectUnitById(approal.getUnitId());
            String uuid = "";
            Sys_Approal approalNow = approvalService.selectApproval(approal.getUnitId(), "0");
            if (approalNow != null) {
                approal.setId(approalNow.getId());
                BeanUtils.copyProperties(approal, approalNow);
                approalNow.setCreateTime(new Date());
                approvalService.updataApproal(approalNow);
                uuid = approalNow.getId();
                DataManager.setProcessDate(processService, "1", unit, "", gson.toJson(approalNow), unitService);
            } else {
                uuid = UUID.randomUUID().toString();
                approal.setId(uuid);
                approal.setCreateTime(new Date());
                approvalService.insertApproal(approal);
                DataManager.setProcessDate(processService, "1", unit, "", gson.toJson(approal), unitService);
            }
            return new Result(ResultCode.SUCCESS.toString(), "提交成功", approal, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "查询晋升职级人员备案名册", notes = "查询晋升职级人员备案名册", httpMethod = "GET", tags = "查询晋升职级人员备案名册接口")
    @PostMapping(value = "/getRegData")
    @ResponseBody
    public String getRegData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "unitName", required = false) String unitName,
                             @RequestParam(value = "unitIds", required = false) String[] unitIds) {
        try {
            RegModel regDataInfo = DataManager.getRegDataInfo(unitService, unitName, response, peopleService, rankService, dutyService, assessmentService);
            return new Result(ResultCode.SUCCESS.toString(), unitName, regDataInfo, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "查询晋升职级人员备案名册", notes = "查询晋升职级人员备案名册", httpMethod = "GET", tags = "查询晋升职级人员备案名册接口")
    @PostMapping(value = "/getRegDataByRow")
    @ResponseBody
    public String getRegDataByRow(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "rowId", required = false) String rowId) {
        try {
            Sys_Process process = processService.selectProcessById(rowId);
            if (process != null) {
                RegModel sys_processes = gson.fromJson(process.getParam(), new TypeToken<RegModel>() {
                }.getType());
                if (sys_processes != null) {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, sys_processes, null).getJson();
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "提交晋升职级人员备案名册", notes = "提交晋升职级人员备案名册", httpMethod = "POST", tags = "提交晋升职级人员备案名册接口")
    @PostMapping(value = "/submitRegApproval")
    @ResponseBody
    public String submitRegApproval(HttpServletResponse response, @RequestParam(value = "flag", required = false) String flag
            , @RequestParam(value = "unitName", required = false) String unitName, @RequestParam(value = "unitIds", required = false) String[] unitIds,
                                    @RequestParam(value = "month", required = false) String month, @RequestParam(value = "day", required = false) String day,
                                    @RequestParam(value = "peopleName", required = false) String peopleName, @RequestParam(value = "peopleNum", required = false) String peopleNum) {
        try {
            RegModel model = new RegModel();
            model.setMonth(month);
            model.setDay(day);
            model.setPeopleName(peopleName);
            model.setPeopleNum(peopleNum);
            model.setUnitName(unitName);
            List<RankModel> rankModels = DataManager.filingList(unitService, unitName, response, peopleService, rankService, dutyService, assessmentService,
                    model, processService, "提交");
            if (rankModels.size() > 0) {
                return new Result(ResultCode.SUCCESS.toString(), "提交成功", rankModels, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "无审批数据", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出晋升职级人员备案名册", notes = "导出晋升职级人员备案名册", httpMethod = "GET", tags = "导出晋升职级人员备案名册接口")
    @RequestMapping(value = "/exportDataExcel")
    public String exportDataExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "flag", required = false) String flag
            , @RequestParam(value = "unitName", required = false) String unitName, @RequestParam(value = "unitIds", required = false) String[] unitIds,
                                  @RequestParam(value = "month", required = false) String month, @RequestParam(value = "day", required = false) String day,
                                  @RequestParam(value = "peopleName", required = false) String peopleName, @RequestParam(value = "peopleNum", required = false) String peopleNum) {
        try {
            if ("filingList".equals(flag)) {//备案表
                RegModel model = new RegModel();
                model.setMonth(month);
                model.setDay(day);
                model.setPeopleName(peopleName);
                model.setPeopleNum(peopleNum);
                model.setUnitName(unitName);
                List<RankModel> rankModels = DataManager.filingList(unitService, unitName, response, peopleService, rankService,
                        dutyService, assessmentService, model, processService, "导出");
                return new Result(ResultCode.SUCCESS.toString(), unitName, rankModels, null).getJson();
            }
            if ("approval".equals(flag)) {
                Sys_Approal approalModel = DataManager.approvalExport(unitService, unitName, response, peopleService, rankService, approvalService);
                return new Result(ResultCode.SUCCESS.toString(), unitName, approalModel, null).getJson();
            } else {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "OK!", null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导入人员信息", notes = "导入人员信息", httpMethod = "POST", tags = "导入人员信息接口")
    @RequestMapping(value = "/importPeople")
    public String importPeopleExcel(@RequestParam("excelFile") MultipartFile excelFile, @RequestParam(value = "fullImport", required = false) String fullImport) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList = ExcelFileGenerator.readeExcelHeader(excelFile.getInputStream(), 0, 2);
            if (headList.size() > 0) {
                if (!headList.get(2).contains("姓名") && !headList.get(3).contains("身份证号")) {
                    stringBuffer.append(ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.IMPORT_EXCEL_FILE_ERROR, null, null).getJson();
                } else {
                    List<Map<String, Object>> list = ExcelFileGenerator.readeExcelData(excelFile.getInputStream(), 0, 2, 3);
                    List<SYS_People> peopleList = DataManager.getPeopleDataByExcel(list, peopleService, stringBuffer, unitService, fullImport, educationService,
                            dutyService, rankService, rewardService, assessmentService, codeService);
                    if (stringBuffer.length() > 0) {
                        return new Result(ResultCode.SUCCESS.toString(), stringBuffer.toString(), peopleList, null).getJson();
                    } else {
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.IMPORT_EXCEL_SUCCESS, peopleList, null).getJson();
                    }
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), stringBuffer.toString(), null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            if (e.toString().contains("IndexOutOfBoundsException")){
                return new Result(ResultCode.ERROR.toString(), "采集表格式不对，请检查表头", null, null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
            }
        }
    }

    @ApiOperation(value = "批量导入职级、职务", notes = "批量导入职级、职务", httpMethod = "POST", tags = "批量导入职级、职务接口")
    @RequestMapping(value = "/importAllRankPeople")
    public String importAllRankPeople(@RequestParam("excelFile") MultipartFile excelFile, @RequestParam(value = "peopleId", required = false) String peopleId) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList = ExcelFileGenerator.readeExcelHeader(excelFile.getInputStream(), 0, 3);
            if (headList.size() > 0) {
                if (!headList.get(2).contains("姓名") && !headList.get(3).contains("身份证号")) {
                    stringBuffer.append(ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.IMPORT_EXCEL_FILE_ERROR, null, null).getJson();
                } else {
                    List<Map<String, Object>> list = ExcelFileGenerator.readeExcelData(excelFile.getInputStream(), 0, 3, 4);
                    List<SYS_People> peopleList = DataManager.importAllRankPeopleExcel(list, peopleService, stringBuffer, unitService, dutyService, rankService);
                    if (stringBuffer.length() > 0) {
                        return new Result(ResultCode.SUCCESS.toString(), stringBuffer.toString(), peopleList, null).getJson();
                    } else {
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.IMPORT_EXCEL_SUCCESS, peopleList, null).getJson();
                    }
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), stringBuffer.toString(), null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            if (e.toString().contains("IndexOutOfBoundsException")){
                return new Result(ResultCode.ERROR.toString(), "采集表格式不对，请检查表头", null, null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
            }
        }
    }

    @ApiOperation(value = "查询审批差异数据", notes = "查询审批差异数据", httpMethod = "GET", tags = "查询审批差异数据接口")
    @RequestMapping(value = "/getProcessData")
    public String getProcessData(@RequestParam(value = "processId", required = false) String processId,
                                 HttpServletRequest request) {
        if (StrUtils.isBlank(processId)) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
        SYS_Data data = dataService.selectDataByProcessId(processId);
        if (data == null) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
        Sys_Process process = processService.selectProcessById(processId);
        if (process == null) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
        List<Sys_Approal> approals = new ArrayList<>();
        List<SYS_Digest> digests = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        List<SYS_DataInfo> dataInfoList = dataInfoService.selectDataInfosByDataId(data.getId(), "上行");
        Map<String, Object> resultMap = new HashMap<>();
        if (dataInfoList != null) {
            for (SYS_DataInfo dataInfo : dataInfoList) {
//                System.out.println(dataInfo.getTableName()+"=>");
//                if ("1".equals(user.getRoles())) {
                if (dataInfo.getTableName().contains("people")) {
                    List<SYS_People> sys_peoples = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_People>>() {
                    }.getType());
                    if (sys_peoples.size() > 0) {
                        if (dataInfo.getBeforeParam() != null) {
                            List<SYS_People> localPeoples = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_People>>() {
                            }.getType());
                            if (localPeoples.size() > 0) {
                                DataManager.peopleDataCheck(resultMap, sys_peoples, peopleService, localPeoples);
                            }
                        }
                    }
                } else if (dataInfo.getTableName().contains("rank")) {
                    List<SYS_Rank> sys_ranks = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Rank>>() {
                    }.getType());
                    if (sys_ranks.size() > 0) {
                        if (dataInfo.getBeforeParam() != null) {
                            List<SYS_Rank> localRanks = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_Rank>>() {
                            }.getType());
                            if (localRanks.size() > 0) {
                                DataManager.rankDataCheck(resultMap, sys_ranks, rankService, localRanks);
                            }
                        }
                    }
                } else if (dataInfo.getTableName().contains("duty")) {
                    List<SYS_Duty> sys_duties = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Duty>>() {
                    }.getType());
                    if (sys_duties.size() > 0) {
                        if (dataInfo.getBeforeParam() != null) {
                            List<SYS_Duty> localDutys = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_Duty>>() {
                            }.getType());
                            if (localDutys.size() > 0) {
                                DataManager.dutyDataCheck(resultMap, sys_duties, dutyService, localDutys);
                            }
                        }
                    }
                } else if (dataInfo.getTableName().contains("education")) {
                    List<SYS_Education> sys_educations = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Education>>() {
                    }.getType());
                    if (sys_educations.size() > 0) {
                        if (dataInfo.getBeforeParam() != null) {
                            List<SYS_Education> localEducations = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_Education>>() {
                            }.getType());
                            if (localEducations.size() > 0) {
                                DataManager.educationDataCheck(resultMap, sys_educations, educationService, localEducations);
                            }
                        }
                    }
                } else if (dataInfo.getTableName().contains("reward")) {
                    List<SYS_Reward> sys_rewards = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Reward>>() {
                    }.getType());
                    if (sys_rewards.size() > 0) {
                        if (dataInfo.getBeforeParam() != null) {
                            List<SYS_Reward> localRewards = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_Reward>>() {
                            }.getType());
                            if (localRewards.size() > 0) {
                                DataManager.rewardDataCheck(resultMap, sys_rewards, rewardService, localRewards);
                            }
                        }
                    }
                } else if (dataInfo.getTableName().contains("assessment")) {
                    List<SYS_Assessment> sys_assessments = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Assessment>>() {
                    }.getType());
                    if (sys_assessments.size() > 0) {
                        if (dataInfo.getBeforeParam() != null) {
                            List<SYS_Assessment> localAssessments = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_Assessment>>() {
                            }.getType());
                            if (localAssessments.size() > 0) {
                                DataManager.assessmentDataCheck(resultMap, sys_assessments, assessmentService, localAssessments);
                            }
                        }
                    }
                } else if (dataInfo.getTableName().contains("user")) {
                    List<SYS_USER> sys_users = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_USER>>() {
                    }.getType());
                    if (sys_users.size() > 0) {
                        if (dataInfo.getBeforeParam() != null) {
                            List<SYS_USER> localUsers = gson.fromJson(dataInfo.getBeforeParam(), new TypeToken<List<SYS_USER>>() {
                            }.getType());
                            if (localUsers.size() > 0) {
                                DataManager.userDataCheck(resultMap, sys_users, userService, localUsers);
                            }
                        }
                    }
                } else if (dataInfo.getTableName().contains("digest")) {
                    List<SYS_Digest> digestList = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Digest>>() {
                    }.getType());
                    if (digestList.size() > 0) {
                        if ("0".equals(process.getFlag())) {
                            resultMap.put("digest", digestList);
                        }
                    }
                } else if (dataInfo.getTableName().contains("approval")) {
                    List<Sys_Approal> sys_approals = gson.fromJson(dataInfo.getParam(), new TypeToken<List<Sys_Approal>>() {
                    }.getType());
                    if (sys_approals.size() > 0) {
                        objects.add(sys_approals);
                        approals.addAll(sys_approals);
                    }
                }
                resultMap.put("digests", digests);
                if (approals.size() > 0) {
                    DataManager.approvalDataCheck(resultMap, approals, approvalService, unitService,
                            "上行", peopleService, rankService);
                }
//                }
            }
        }
        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, resultMap, null).getJson();
    }

    @ApiOperation(value = "导出上行数据", notes = "导出上行数据", httpMethod = "POST", tags = "导出上行数据接口")
    @RequestMapping(value = "/upstreamData")
    public String upstreamData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "unitId", required = false) String unitId,
                               @RequestParam(value = "dataType", required = false) String dataType,
                               @RequestParam(value = "flag", required = false) String flag,
                               @RequestParam(value = "processId", required = false) String processId) {
        try {
            List<Object> objects = new ArrayList<>();
            if (StrUtils.isBlank(processId)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.IMPORT_EXCEL_ERROR, null, null).getJson();
            }
            Sys_Process process = processService.selectProcessById(processId);
            if (process == null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.IMPORT_EXCEL_ERROR, null, null).getJson();
            }
            unitId = process.getUnitId();
            //从请求的header中取出当前登录的登录
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("note", "成功");
            paramsMap.put("dataId", unitId + process.getId());
            paramsMap.put("dataType", dataType);
            paramsMap.put("flag", flag);
            paramsMap.put("processId", process.getId());
            Map<String, Object> resultMap = new HashMap<>();
            List<Sys_Process> processeList = DataManager.getUpProcessJson(resultMap, process, processService);
            objects.addAll(processeList);
            if ("初审".equals(process.getStates())){
                SYS_Data data=dataService.selectDataByProcessId(processId);
                if (data!=null){
                    DataManager.getDataInfoManager(dataInfoService,data,unitId,unitService,resultMap);
                }
            } else {
                List<SYS_UNIT> unitList = DataManager.getUnitJson(resultMap, unitId, unitService);//单位
                objects.addAll(unitList);
                if (!"职数".equals(flag)) {
                    List<SYS_People> peopleList = DataManager.getPeopleJson(resultMap, unitList, peopleService);
                    List<SYS_USER> userList = DataManager.getUserJson(resultMap, unitList, userService);
                    objects.addAll(userList);
                    List<SYS_Digest> digestList = DataManager.getDigestJson(resultMap, unitList, dataService);
                    objects.addAll(digestList);
                    if (peopleList.size() > 0) {
                        objects.addAll(peopleList);
                        List<SYS_Duty> dutyList = DataManager.getDutyJson(resultMap, peopleList, dutyService);
                        objects.addAll(dutyList);
                        List<SYS_Rank> rankList = DataManager.getRankJson(resultMap, peopleList, rankService);
                        objects.addAll(rankList);
                        List<SYS_Education> educationList = DataManager.getEducationJson(resultMap, peopleList, educationService);
                        objects.addAll(educationList);
                        List<SYS_Reward> rewardList = DataManager.getRewardJson(resultMap, peopleList, rewardService);
                        objects.addAll(rewardList);
                        List<SYS_Assessment> assessmentList = DataManager.getAssessmentJson(resultMap, peopleList, assessmentService);
                        objects.addAll(assessmentList);
                    }
                } else {
                    List<Sys_Approal> approvalList = DataManager.getApproalJson(resultMap, unitList, approvalService, dataType);
                    objects.addAll(approvalList);
                }
            }
            JSONObject resultList = JSONObject.fromObject(resultMap);
            paramsMap.put("result", resultList);
            JSONObject resultJson = JSONObject.fromObject(paramsMap);
            // 加密
//            System.out.println("加密前：" + resultJson.toString());
            byte[] encode = AESUtil.encrypt(resultJson.toString(), AESUtil.privateKey);
            //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
            String paramsCipher = AESUtil.parseByte2HexStr(encode);
//            System.out.println("密文字符串：" + paramsCipher);
//            String paramsCipher = RSAModelUtils.encryptByPublicKey(code, RSAModelUtils.moduleA,RSAModelUtils.puclicKeyA);
//            // 解密
//            byte[] decode = AESUtil.parseHexStr2Byte(paramsCipher);
//            byte[] decryptResult = AESUtil.decrypt(decode,AESUtil.privateKey);
//            System.out.println("解密后：" + new String(decryptResult, "UTF-8")); //不转码会乱码
//            String jsonStr=new String(decryptResult, "UTF-8");
//            System.out.println(jsonStr);
            File file = jsonFile;
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(paramsCipher);
            writer.flush();
            writer.close();
            return paramsCipher;
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        } finally {
            jsonFile.deleteOnExit();//程序结束 删除临时文件
        }
    }

    @ApiOperation(value = "导入上行数据", notes = "导入上行数据", httpMethod = "POST", tags = "导入上行数据接口")
    @RequestMapping(value = "/importUpstreamData")
    public String importUpstreamData(@RequestParam(value = "excelFile", required = true) MultipartFile excelFile, @RequestParam(value = "unitId", required = false) String unitId
            , @RequestParam(value = "childUnit", required = false) String childUnit, @RequestParam(value = "userId", required = false) String userId) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            List<SYS_UNIT> units = new ArrayList<>();
            List<SYS_USER> users = new ArrayList<>();
            List<Sys_Approal> approals = new ArrayList<>();
            List<Sys_Process> processes = new ArrayList<>();
            List<SYS_People> peoples = new ArrayList<>();
            List<SYS_Duty> duties = new ArrayList<>();
            List<SYS_Rank> ranks = new ArrayList<>();
            List<SYS_Reward> rewards = new ArrayList<>();
            List<SYS_Education> educations = new ArrayList<>();
            List<SYS_Assessment> assessments = new ArrayList<>();
            List<SYS_Digest> digests = new ArrayList<>();
            String jsonStrMw = FileUtil.readJsonFile(excelFile.getInputStream());
            // 解密
            byte[] decode = AESUtil.parseHexStr2Byte(jsonStrMw);
            byte[] decryptResult = AESUtil.decrypt(decode, AESUtil.privateKey);
//            System.out.println("解密后：" + new String(decryptResult, "UTF-8")); //不转码会乱码
            String jsonStr = new String(decryptResult, "UTF-8");
//            String jsonStr= RSAModelUtils.decryptByPrivateKey(jsonStrMw,RSAModelUtils.moduleA,RSAModelUtils.privateKeyA);
            JSONObject object = JSONObject.fromObject(jsonStr);
            String note = String.valueOf(object.get("note"));
            String dataId = String.valueOf(object.get("dataId"));
            String dataType = String.valueOf(object.get("dataType"));
            String flag = String.valueOf(object.get("flag"));
            String processId = String.valueOf(object.get("processId"));
            if (!StrUtils.isBlank(note)) {
                if (StrUtils.isBlank(processId)) {
                    return new Result(ResultCode.ERROR.toString(), "数据表错误！", null, null).getJson();
                }
                Sys_Process sys_process1 = processService.selectProcessById(processId);
                if (sys_process1 != null) {
                    return new Result(ResultCode.ERROR.toString(), "此流程已经存在！", null, null).getJson();
                }
                JSONObject key = object.getJSONObject("result");
                String date = String.valueOf(key.get("date"));
                String iunitId = String.valueOf(key.get("unitId"));
                if (!iunitId.equals(unitId)) {
                    if (childUnit.contains(iunitId)) {
                        SYS_USER user = userService.selectUserById(userId);
                        if (user == null) {
                            return new Result(ResultCode.ERROR.toString(), "登录超时！", null, null).getJson();
                        }
                        SYS_UNIT punit = unitService.selectUnitById(user.getUnitId());
//                        List<SYS_UNIT> punitList=unitService.selectAllParentUnits(punit);
                        JSONArray approvalList = new JSONArray();
                        String unitName = String.valueOf(key.get("unitName"));
                        JSONArray unitList = key.getJSONArray("unitList");
                        JSONArray processList = key.getJSONArray("processList");
                        JSONArray userList = new JSONArray(), digestList = new JSONArray(), peopleList = new JSONArray(), rankList = new JSONArray();
                        JSONArray dutyList = new JSONArray(), educationList = new JSONArray(), rewardList = new JSONArray(), assessmentList = new JSONArray();
                        if (!"职数".equals(flag)) {
                            userList = key.getJSONArray("userList");
                            digestList = key.getJSONArray("digestList");
                            peopleList = key.getJSONArray("peopleList");
                            rankList = key.getJSONArray("rankList");
                            dutyList = key.getJSONArray("dutyList");
                            educationList = key.getJSONArray("educationList");
                            rewardList = key.getJSONArray("rewardList");
                            assessmentList = key.getJSONArray("assessmentList");
                        } else {
                            approvalList = key.getJSONArray("approvalList");
                        }
                        SYS_UNIT unit = unitService.selectUnitById(iunitId);
                        if (unit == null) {
                            unit = unitService.selectUnitByName(unitName);
                        }
                        if (unit != null) {
                            if (unit.getId().contains(punit.getId())) {
                                return new Result(ResultCode.ERROR.toString(), "不可审批自己的流程！", null, null).getJson();
                            }
                            if (processList.size() > 0) {
                                Map<String, Object> resultMap = new HashMap<>();
                                processes = DataManager.saveProcessJsonModel(processList, user, "上行", unit, processService);
                                List<Sys_Process> sys_processes = new ArrayList<>();
                                List<Sys_Process> sprocesses = new ArrayList<>();
                                if (processes.size() > 0) {
                                    if (punit.getId().equals(processes.get(0).getApprovalEve())) {
                                        DataManager.saveprocessData(processes, processService, "", "", user, "未审批", unitService, punit);
                                        for (Sys_Process process : processes) {
                                            SYS_Data data = DataManager.saveData(dataId, process.getId(), dataType, iunitId, dataService);
                                            resultMap.put("dataId", data.getId());
                                            Sys_Process process1 = process;
//                                            process.setCreateTime(new Date());
                                            sys_processes.add(process);
                                            DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "processe", gson.toJson(sys_processes), null);
                                            if (process.getChildren() != null) {
                                                List<Sys_Process> cpros = new ArrayList<>();
                                                for (Sys_Process cprocess : process.getChildren()) {
                                                    if ("0".equals(cprocess.getApprovaled())) {
                                                        cpros.add(cprocess);
                                                    }
                                                }
                                                process1.setChildren(new ArrayList<>());
                                                if (cpros.size() > 0) {
                                                    process1.setChildren(cpros);
                                                }
                                            }
                                            sprocesses.add(process1);
                                            if (unitList!=null) {
                                                units = DataManager.saveUnitJsonModel(unitList);
                                                if (units.size() > 0) {
                                                    List<SYS_UNIT> us = new ArrayList<>();
                                                    SYS_UNIT sys_unit = unitService.selectUnitById(iunitId);
                                                    if (sys_unit != null) {
                                                        us.add(sys_unit);
                                                    }
                                                    DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "unit", gson.toJson(units), gson.toJson(us));
                                                }
                                                if (userList!=null){
                                                    users = DataManager.saveUserJsonModel(userList);
                                                    if (users.size() > 0) {
                                                        String beforeparam = null;
                                                        List<SYS_USER> us = userService.selectUsersByUnitId(iunitId);
                                                        if (us != null) {
                                                            beforeparam = gson.toJson(us);
                                                        }
                                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "user", gson.toJson(users), beforeparam);
                                                    }
                                                }
                                                if (digestList!=null){
                                                    digests = DataManager.saveDigestJsonModel(digestList);
                                                    if (digests.size() > 0) {
                                                        String beforeparam = null;
                                                        List<SYS_Digest> us = dataService.selectDigestsByUnitId(iunitId);
                                                        if (us != null) {
                                                            beforeparam = gson.toJson(us);
                                                        }
                                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "digest", gson.toJson(digests), beforeparam);
                                                    }
                                                }
                                                if (approvalList!=null){
                                                    if (approvalList.size() > 0) {
                                                        approals = DataManager.saveApproalJsonModel(approvalList);
                                                        if (approals.size() > 0) {
                                                            String beforeparam = null;
                                                            List<Sys_Approal> us = approvalService.selectApprovals(iunitId);
                                                            if (us != null) {
                                                                beforeparam = gson.toJson(us);
                                                            }
                                                            DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "approval", gson.toJson(approals), beforeparam);
                                                        }
                                                    }
                                                }
                                                if (peopleList!=null){
                                                    peoples = DataManager.savePeopleJsonModel(peopleList);
                                                    if (peoples.size() > 0) {
                                                        String beforeparam = null;
                                                        List<SYS_People> us = peopleService.selectPeoplesByUnitId(iunitId);
                                                        if (us != null) {
                                                            beforeparam = gson.toJson(us);
                                                        }
                                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "people", gson.toJson(peoples), beforeparam);
                                                    }
                                                    if (dutyList!=null){
                                                        duties = DataManager.saveDutyJsonModel(dutyList);
                                                        if (duties.size() > 0) {
                                                            String beforeparam = null;
                                                            List<SYS_Duty> us = dutyService.selectDutysByUnitId(iunitId);
                                                            if (us != null) {
                                                                beforeparam = gson.toJson(us);
                                                            }
                                                            DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "duty", gson.toJson(duties), beforeparam);
                                                        }
                                                    }
                                                    if (rankList!=null){
                                                        ranks = DataManager.saveRankJsonModel(rankList);
                                                        if (ranks.size() > 0) {
                                                            String beforeparam = null;
                                                            List<SYS_Rank> us = rankService.selectRanksByUnitId(iunitId);
                                                            if (us != null) {
                                                                beforeparam = gson.toJson(us);
                                                            }
                                                            DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "rank", gson.toJson(ranks), beforeparam);
                                                        }
                                                    }
                                                    if (rewardList!=null){
                                                        rewards = DataManager.saveRewardJsonModel(rewardList);
                                                        if (rewards.size() > 0) {
                                                            String beforeparam = null;
                                                            List<SYS_Reward> us = rewardService.selectRewardsByUnitId(iunitId);
                                                            if (us != null) {
                                                                beforeparam = gson.toJson(us);
                                                            }
                                                            DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "reward", gson.toJson(rewards), beforeparam);
                                                        }
                                                    }
                                                    if (educationList!=null){
                                                        educations = DataManager.saveEducationJsonModel(educationList);
                                                        if (educations.size() > 0) {
                                                            String beforeparam = null;
                                                            List<SYS_Education> us = educationService.selectEducationsByUnitId(iunitId);
                                                            if (us != null) {
                                                                beforeparam = gson.toJson(us);
                                                            }
                                                            DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "education", gson.toJson(educations), beforeparam);
                                                        }
                                                    }
                                                    if (assessmentList!=null){
                                                        assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                                                        if (assessments.size() > 0) {
                                                            String beforeparam = null;
                                                            List<SYS_Assessment> us = assessmentService.selectAssessmentsByUnitId(iunitId);
                                                            if (us != null) {
                                                                beforeparam = gson.toJson(us);
                                                            }
                                                            DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "assessment", gson.toJson(assessments), beforeparam);
                                                        }
                                                    }
                                                }
                                                Map<String, Object> map = new HashMap<>();
                                                List<SYS_UNIT> localUnits = DataManager.getUnitJson(map, iunitId, unitService);//单位
                                                List<SYS_UNIT> deleteUnitList = new ArrayList<>();
                                                List<SYS_People> localPeoples = peopleService.selectPeoplesByUnitId(iunitId);
                                                List<SYS_Duty> localDutys = dutyService.selectDutysByUnitId(iunitId);
                                                List<SYS_Rank> localRanks = rankService.selectRanksByUnitId(iunitId);
                                                List<SYS_Education> localEducations = educationService.selectEducationsByUnitId(iunitId);
                                                List<SYS_Reward> localRewards = rewardService.selectRewardsByUnitId(iunitId);
                                                List<SYS_Assessment> localAssessments = assessmentService.selectAssessmentsByUnitId(iunitId);
                                                List<SYS_USER> localUsers = userService.selectUsersByUnitId(iunitId);
                                                if (!"职数".equals(flag)) {
                                                    DataManager.peopleDataCheck(resultMap, peoples, peopleService, localPeoples);
                                                    DataManager.dutyDataCheck(resultMap, duties, dutyService, localDutys);
                                                    DataManager.rankDataCheck(resultMap, ranks, rankService, localRanks);
                                                    DataManager.educationDataCheck(resultMap, educations, educationService, localEducations);
                                                    DataManager.rewardDataCheck(resultMap, rewards, rewardService, localRewards);
                                                    DataManager.assessmentDataCheck(resultMap, assessments, assessmentService, localAssessments);
                                                    DataManager.userDataCheck(resultMap, users, userService, localUsers);
                                                    resultMap.put("digests", digests);
                                                }
                                                if (approals.size() > 0) {
                                                    DataManager.approvalDataCheck(resultMap, approals, approvalService, unitService,
                                                            dataType, peopleService, rankService);
                                                }
                                                if (sprocesses.size() > 0) {
                                                    resultMap.put("processList", sprocesses);
                                                }
                                            }
                                        }
                                    } else {
                                        SYS_UNIT sys_unit = unitService.selectUnitById(processes.get(0).getApprovalEve());
                                        return new Result(ResultCode.ERROR.toString(), sys_unit.getName() + "还未审批！", null, null).getJson();
                                    }
                                }
                                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, resultMap, null).getJson();
                            } else {
                                return new Result(ResultCode.ERROR.toString(), "无审批数据！", null, null).getJson();
                            }
                        } else {
                            return new Result(ResultCode.ERROR.toString(), "单位不存在！", null, null).getJson();
                        }
                    } else {
                        return new Result(ResultCode.ERROR.toString(), "只能上行到上级单位！", null, null).getJson();
                    }
                } else {
                    return new Result(ResultCode.ERROR.toString(), "非下级单位上行包！", null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), "上行数据包不全！", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }

    @ApiOperation(value = "执行上行数据", notes = "执行上行数据", httpMethod = "POST", tags = "执行上行数据接口")
    @PostMapping(value = "/agreeImportData")
    public String agreeImportData(@RequestParam(value = "dataId", required = false) String dataId, HttpServletRequest request) throws Exception {
        List<Object> objects = new ArrayList<>();
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (!StrUtils.isBlank(dataId)) {
            if (user == null) {
                return new Result(ResultCode.ERROR.toString(), "账号未登录！", null, null).getJson();
            }
            List<SYS_DataInfo> dataInfos = dataInfoService.selectDataInfosByDataId(dataId, "上行");
            if (dataInfos != null) {
                for (SYS_DataInfo dataInfo : dataInfos) {
                    if (dataInfo.getId().contains("process")) {
                        List<Sys_Process> sys_processes = gson.fromJson(dataInfo.getParam(), new TypeToken<List<Sys_Process>>() {
                        }.getType());
                        if (sys_processes.size() > 0) {
                            String peopleName = "";
                            String approvalUnitName = "";
                            if (user != null) {
                                SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
                                if (unit != null) {
                                    approvalUnitName = unit.getName();
                                }
                                SYS_People people = peopleService.selectPeopleById(user.getPeopleId());
                                if (people != null) {
                                    peopleName = people.getName();
                                }
                                DataManager.saveprocessData(sys_processes, processService, approvalUnitName, peopleName, user, "已审核", unitService, unit);
                                objects.add(sys_processes);
                            }
                        }
                    }
                    if ("1".equals(user.getRoles())) {
                        if (dataInfo.getId().contains("people")) {
                            List<SYS_People> sys_peoples = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_People>>() {
                            }.getType());
                            if (sys_peoples.size() > 0) {
                                DataManager.savePeopleData(sys_peoples, peopleService, dataInfo.getUnitId(), unitService);
                                objects.add(sys_peoples);
                            }
                        } else if (dataInfo.getId().contains("rank")) {
                            List<SYS_Rank> sys_ranks = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Rank>>() {
                            }.getType());
                            if (sys_ranks.size() > 0) {
                                objects.add(sys_ranks);
                                DataManager.saveRankData(sys_ranks, rankService, dataInfo.getUnitId(), peopleService);
                            }
                        } else if (dataInfo.getId().contains("duty")) {
                            List<SYS_Duty> sys_duties = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Duty>>() {
                            }.getType());
                            if (sys_duties.size() > 0) {
                                DataManager.saveDutyData(sys_duties, dutyService, dataInfo.getUnitId(), peopleService);
                                objects.add(sys_duties);
                            }
                        } else if (dataInfo.getId().contains("education")) {
                            List<SYS_Education> sys_educations = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Education>>() {
                            }.getType());
                            if (sys_educations.size() > 0) {
                                DataManager.saveEducationData(sys_educations, educationService, dataInfo.getUnitId(), peopleService);
                                objects.add(sys_educations);
                            }
                        } else if (dataInfo.getId().contains("reward")) {
                            List<SYS_Reward> sys_rewards = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Reward>>() {
                            }.getType());
                            if (sys_rewards.size() > 0) {
                                DataManager.saveRewardData(sys_rewards, rewardService, dataInfo.getUnitId(), peopleService);
                                objects.add(sys_rewards);
                            }
                        } else if (dataInfo.getId().contains("assessment")) {
                            List<SYS_Assessment> sys_assessments = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Assessment>>() {
                            }.getType());
                            if (sys_assessments.size() > 0) {
                                DataManager.saveAssessmentData(sys_assessments, assessmentService, dataInfo.getUnitId(), peopleService);
                                objects.add(sys_assessments);
                            }
                        } else if (dataInfo.getId().contains("user")) {
                            List<SYS_USER> sys_users = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_USER>>() {
                            }.getType());
                            if (sys_users.size() > 0) {
                                DataManager.saveUserData(sys_users, userService, dataInfo.getUnitId());
                                objects.add(sys_users);
                            }
                        } else if (dataInfo.getId().contains("digest")) {
                            List<SYS_Digest> digestList = gson.fromJson(dataInfo.getParam(), new TypeToken<List<SYS_Digest>>() {
                            }.getType());
                            if (digestList.size() > 0) {
                                DataManager.saveDigestData(digestList, dataService, dataInfo.getUnitId());
                                objects.add(digestList);
                            }
                        } else if (dataInfo.getId().contains("approval")) {
                            List<Sys_Approal> sys_approals = gson.fromJson(dataInfo.getParam(), new TypeToken<List<Sys_Approal>>() {
                            }.getType());
                            if (sys_approals.size() > 0) {
                                DataManager.saveApprovalData(sys_approals, approvalService, dataInfo.getUnitId(), unitService, "1");
                                objects.add(sys_approals);
                            }
                        }
                    }
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, objects, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "此数据包为下行数据包", null, null).getJson();
            }
        } else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出下行数据", notes = "导出下行数据", httpMethod = "POST", tags = "导出下行数据接口")
    @RequestMapping(value = "/upstreamDownData")
    public String upstreamDownData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "unitId", required = false) String unitId,
                                   @RequestParam(value = "dataType", required = false) String dataType,
                                   @RequestParam(value = "flag", required = false) String flag,
                                   @RequestParam(value = "processId", required = false) String processId) {
        try {
            List<Object> objects = new ArrayList<>();
            //从请求的header中取出当前登录的登录
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("note", "成功");
            paramsMap.put("dataId", unitId + DateUtil.getDateNum(new Date()));
            paramsMap.put("dataType", dataType);
            paramsMap.put("flag", flag);
            Map<String, Object> resultMap = new HashMap<>();
            List<SYS_UNIT> unitList = DataManager.getUnitJson(resultMap, unitId, unitService);//单位
            objects.addAll(unitList);
            if (!"职数".equals(flag)) {
                List<Sys_Process> processeList = DataManager.getProcessJson(resultMap, unitList, processService, dataType, "0");
                objects.addAll(processeList);
                List<SYS_Message> messageList = DataManager.getMessageBackDataJson(resultMap, unitList, userService);
                objects.addAll(messageList);
                List<SYS_People> peopleList = DataManager.getPeopleJson(resultMap, unitList, peopleService);
                List<SYS_USER> userList = DataManager.getUserJson(resultMap, unitList, userService);
                List<SYS_Digest> digestList = DataManager.getDigestJson(resultMap, unitList, dataService);
                if (peopleList.size() > 0) {
                    objects.addAll(peopleList);
                    List<SYS_Duty> dutyList = DataManager.getDutyJson(resultMap, peopleList, dutyService);
                    objects.addAll(dutyList);
                    List<SYS_Rank> rankList = DataManager.getRankJson(resultMap, peopleList, rankService);
                    objects.addAll(rankList);
                    List<SYS_Education> educationList = DataManager.getEducationJson(resultMap, peopleList, educationService);
                    objects.addAll(educationList);
                    List<SYS_Reward> rewardList = DataManager.getRewardJson(resultMap, peopleList, rewardService);
                    objects.addAll(rankList);
                    List<SYS_Assessment> assessmentList = DataManager.getAssessmentJson(resultMap, peopleList, assessmentService);
                    objects.addAll(assessmentList);
                }
                objects.addAll(userList);
                objects.addAll(digestList);
            } else {
                List<Sys_Approal> approvalList = DataManager.getApproalJson(resultMap, unitList, approvalService, dataType);
                objects.addAll(approvalList);
                List<Sys_Process> processeList = DataManager.getProcessJson(resultMap, unitList, processService, dataType, "1");
                objects.addAll(processeList);
            }
            JSONObject resultList = JSONObject.fromObject(resultMap);
            paramsMap.put("result", resultList);
            JSONObject resultJson = JSONObject.fromObject(paramsMap);
            // 加密
//            System.out.println("加密前：" + resultJson.toString());
            byte[] encode = AESUtil.encrypt(resultJson.toString(), AESUtil.privateKey);
            //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
            String paramsCipher = AESUtil.parseByte2HexStr(encode);
            File file = jsonFile;
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(paramsCipher);
            writer.flush();
            writer.close();
            return paramsCipher;
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        } finally {
            jsonFile.deleteOnExit();//程序结束 删除临时文件
        }
    }

    @ApiOperation(value = "导入下行数据", notes = "导入下行数据", httpMethod = "POST", tags = "导入下行数据接口")
    @RequestMapping(value = "/importDownDatabase")
    public String importDownDatabase(@RequestParam(value = "excelFile", required = true) MultipartFile excelFile, @RequestParam(value = "unitId", required = false) String unitId) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Object> objects = new ArrayList<>();
        try {
            List<SYS_UNIT> units = new ArrayList<>();
            List<SYS_USER> users = new ArrayList<>();
            List<Sys_Approal> approals = new ArrayList<>();
            List<Sys_Process> processes = new ArrayList<>();
            List<SYS_People> peoples = new ArrayList<>();
            List<SYS_Duty> duties = new ArrayList<>();
            List<SYS_Rank> ranks = new ArrayList<>();
            List<SYS_Reward> rewards = new ArrayList<>();
            List<SYS_Education> educations = new ArrayList<>();
            List<SYS_Assessment> assessments = new ArrayList<>();
            String jsonStrMw = FileUtil.readJsonFile(excelFile.getInputStream());
            byte[] decode = AESUtil.parseHexStr2Byte(jsonStrMw);
            byte[] decryptResult = AESUtil.decrypt(decode, AESUtil.privateKey);
            String jsonStr = new String(decryptResult, "UTF-8");
//            String jsonStr = RSAModelUtils.decryptByPrivateKey(jsonStrMw, RSAModelUtils.moduleA, RSAModelUtils.privateKeyA);
//            System.out.println("导入" + jsonStr);
            JSONObject object = JSONObject.fromObject(jsonStr);
            String note = String.valueOf(object.get("note"));
            String dataId = String.valueOf(object.get("dataId"));
            String dataType = String.valueOf(object.get("dataType"));
            String flag = String.valueOf(object.get("flag"));
            if (!StrUtils.isBlank(note)) {
                JSONObject key = object.getJSONObject("result");
                String date = String.valueOf(key.get("date"));
                String unitID = String.valueOf(key.get("unitId"));
                boolean bil = false;
                if (unitID.trim().contains(unitId.trim())) {
                    bil = true;
                }
                List<SYS_UNIT> cunitList = unitService.selectAllChildUnits(unitId);
                if (cunitList.size() > 0) {
                    for (SYS_UNIT unit : cunitList) {
                        if (unitID.trim().contains(unit.getId())) {
                            bil = true;
                        }
                    }
                }
                if (!bil) {
                    return new Result(ResultCode.ERROR.toString(), "非本单位及下级单位下行数据包！", null, null).getJson();
                } else {
                    String unitName = String.valueOf(key.get("unitName"));
                    JSONArray unitList = new JSONArray();
                    JSONArray userList = new JSONArray();
                    JSONArray aprovalList = new JSONArray();
                    JSONArray processList = new JSONArray();
                    JSONArray peopleList = new JSONArray();
                    JSONArray rankList = new JSONArray();
                    JSONArray dutyList = new JSONArray();
                    JSONArray educationList = new JSONArray();
                    JSONArray rewardList = new JSONArray();
                    JSONArray assessmentList = new JSONArray();
                    if (!"职数".equals(flag)) {
                        userList = key.getJSONArray("userList");
                        peopleList = key.getJSONArray("peopleList");
                        rankList = key.getJSONArray("rankList");
                        dutyList = key.getJSONArray("dutyList");
                        educationList = key.getJSONArray("educationList");
                        rewardList = key.getJSONArray("rewardList");
                        assessmentList = key.getJSONArray("assessmentList");
                    } else {
                        unitList = key.getJSONArray("unitList");
                        aprovalList = key.getJSONArray("approvalList");
                    }
                    processList = key.getJSONArray("processList");
                    SYS_UNIT unit = unitService.selectUnitById(unitID);
                    if (unit == null) {
                        unit = unitService.selectUnitByName(unitName);
                    }
                    if (unit != null) {
                        SYS_Data data = DataManager.saveData(dataId, "", dataType, unitID, dataService);
                        Map<String, Object> resultMap = new HashMap<>();
                        resultMap.put("dataId", data.getId());
                        if (unitList != null) {
                            units = DataManager.saveUnitJsonModel(unitList);
                            if (units.size() > 0) {
                                String beforeparam = null;
                                List<SYS_UNIT> sys_units = new ArrayList<>();
                                sys_units.add(unit);
                                beforeparam = gson.toJson(sys_units);
                                DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "unit", gson.toJson(units), beforeparam);
                                DataManager.saveUnitData(units, unitService, unitID);
                                objects.add(units);
                            }
                            if (userList!=null){
                                users = DataManager.saveUserJsonModel(userList);
                                if (users.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "user", gson.toJson(users), null);
                                    DataManager.saveUserData(users, userService, unitID);
                                    objects.add(users);
                                }
                            }
                            if (aprovalList!=null){
                                approals = DataManager.saveApproalJsonModel(aprovalList);
                                if (approals.size() > 0) {
                                    String beforeparam = null;
                                    List<Sys_Approal> us = approvalService.selectApprovals(unitID);
                                    if (us != null) {
                                        beforeparam = gson.toJson(us);
                                    }
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "approval", gson.toJson(approals), beforeparam);
                                    DataManager.saveApprovalData(approals, approvalService, unitID, unitService, "1");
                                    objects.add(approals);
                                }
                            }
                            SYS_UNIT punit = unitService.selectUnitById(unitId);
                            SYS_USER user = new SYS_USER();
                            if (processList!=null){
                                processes = DataManager.saveProcessJsonModel(processList, user, "下行", unit, processService);
                                if (processes.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "process", gson.toJson(processes), null);
                                    DataManager.saveprocessData(processes, processService, "", "", user, "已审核", unitService, unit);
                                    objects.add(processes);
                                }
                            }
                            if (peopleList != null) {
                                peoples = DataManager.savePeopleJsonModel(peopleList);
                                if (peoples.size() > 0) {
                                    String beforeparam = null;
                                    List<SYS_People> us = peopleService.selectPeoplesByUnitId(unitID, "0", "全部");
                                    if (us != null) {
                                        beforeparam = gson.toJson(us);
                                    }
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "people", gson.toJson(peoples), beforeparam);
                                    DataManager.savePeopleData(peoples, peopleService, unitID, unitService);
                                    objects.add(peoples);
                                }
                                if (dutyList!=null){
                                    duties = DataManager.saveDutyJsonModel(dutyList);
                                    if (duties.size() > 0) {
                                        String beforeparam = null;
                                        List<SYS_Duty> us = dutyService.selectDutysByUnitId(unitID, "0");
                                        if (us != null) {
                                            beforeparam = gson.toJson(us);
                                        }
                                        DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "duty", gson.toJson(duties), beforeparam);
                                        DataManager.saveDutyData(duties, dutyService, unitID, peopleService);
                                        objects.add(duties);
                                    }
                                }
                                if (rankList!=null){
                                    ranks = DataManager.saveRankJsonModel(rankList);
                                    if (ranks.size() > 0) {
                                        String beforeparam = null;
                                        List<SYS_Rank> us = rankService.selectRanksByUnitId(unitID, "0");
                                        if (us != null) {
                                            beforeparam = gson.toJson(us);
                                        }
                                        DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "rank", gson.toJson(ranks), beforeparam);
                                        objects.add(ranks);
                                        DataManager.saveRankData(ranks, rankService, unitID, peopleService);
                                    }
                                }
                                if (rewardList!=null){
                                    rewards = DataManager.saveRewardJsonModel(rewardList);
                                    if (rewards.size() > 0) {
                                        String beforeparam = null;
                                        List<SYS_Reward> us = rewardService.selectRewardsByUnitId(unitID, "0");
                                        if (us != null) {
                                            beforeparam = gson.toJson(us);
                                        }
                                        DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "reward", gson.toJson(rewards), beforeparam);
                                        DataManager.saveRewardData(rewards, rewardService, unitID, peopleService);
                                        objects.add(rewards);
                                    }
                                }
                                if (educationList!=null){
                                    educations = DataManager.saveEducationJsonModel(educationList);
                                    if (educations.size() > 0) {
                                        String beforeparam = null;
                                        List<SYS_Education> us = educationService.selectEducationsByUnitId(unitID, "0");
                                        if (us != null) {
                                            beforeparam = gson.toJson(us);
                                        }
                                        DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "education", gson.toJson(educations), beforeparam);
                                        DataManager.saveEducationData(educations, educationService, unitID, peopleService);
                                        objects.add(educations);
                                    }
                                }
                                if (assessmentList!=null){
                                    assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                                    if (assessments.size() > 0) {
                                        String beforeparam = null;
                                        List<SYS_Assessment> us = assessmentService.selectAssessmentsByUnitId(unitID, "0");
                                        if (us != null) {
                                            beforeparam = gson.toJson(us);
                                        }
                                        DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "assessment", gson.toJson(assessments), beforeparam);
                                        DataManager.saveAssessmentData(assessments, assessmentService, unitID, peopleService);
                                        objects.add(assessments);
                                    }
                                }
                                JSONArray digestList = key.getJSONArray("digestList");
                                if (digestList!=null){
                                    List<SYS_Digest> digests = DataManager.saveDigestJsonModel(digestList);
                                    if (digests.size() > 0) {
                                        String beforeparam = null;
                                        List<SYS_Digest> us = dataService.selectDigestsByUnitId(unitId);
                                        if (us != null) {
                                            beforeparam = gson.toJson(us);
                                        }
                                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "digest", gson.toJson(digests), beforeparam);
                                        DataManager.saveBackDigestData(digests, dataService);
                                        objects.add(digests);
                                    }
                                }
                                JSONArray messageList = key.getJSONArray("messageList");
                                if (messageList!=null){
                                    List<SYS_Message> messages = DataManager.saveMessageJsonModel(messageList);
                                    if (messages.size() > 0) {
                                        String beforeparam = null;
                                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "digest", gson.toJson(messages), null);
                                        DataManager.saveBackMessageData(messages, userService);
                                        objects.add(messages);
                                    }
                                }
                            }
                            Map<String, Object> map = new HashMap<>();
                            List<SYS_UNIT> localUnits = DataManager.getUnitJson(map, unitID, unitService);//单位
                            List<SYS_UNIT> deleteUnitList = new ArrayList<>();
                            List<SYS_People> localPeoples = peopleService.selectPeoplesByUnitId(unitID, "1", "在职");
                        }
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, objects, null).getJson();
                    } else {
                        return new Result(ResultCode.ERROR.toString(), "单位不存在！", null, null).getJson();
                    }
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), "上行数据包不全！", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }

    @ApiOperation(value = "审批操作", notes = "审批操作", httpMethod = "POST", tags = "审批操作接口")
    @PostMapping(value = "/rejectImportData")
    public String rejectImportData(@RequestParam(value = "rowid", required = false) String rowid, @RequestParam(value = "flag", required = false) String flag, HttpServletRequest request) {
        List<Object> objects = new ArrayList<>();
        String people = "";
        String approveUnitName = "";
        boolean admin = false;
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (user == null) {
            return new Result(ResultCode.ERROR.toString(), "账号未登录！", null, null).getJson();
        }
        if ("1".equals(user.getRoles())) {
            admin = true;
        }
        SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
        if (unit != null) {
            approveUnitName = unit.getName();
            if (user.getPeopleName() != null) {
                people = user.getPeopleName();
            }
        }
        if (!StrUtils.isBlank(rowid)) {
            Sys_Process process = processService.selectProcessById(rowid);
            if (process == null) {
                return new Result(ResultCode.ERROR.toString(), "审批表不存在！", null, null).getJson();
            }
            if (!"不通过".equals(flag) && !"撤销".equals(flag)) {
                {
                    SYS_Data data = dataService.selectDataByProcessId(process.getId());
                    if (data == null) {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                    }
                    List<SYS_DataInfo> dataInfos = dataInfoService.selectDataInfosByDataId(data.getId(), "上行");
                    if (dataInfos == null) {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                    }
                    if ("驳回".equals(flag)) {
                        if (process.getApprovalUnitName() != null) {
                            if (!process.getApprovalUnitName().contains(approveUnitName)) {
                                return new Result(ResultCode.ERROR.toString(), "权限不足！", null, null).getJson();
                            }
                        }
                        Sys_Process processl = processService.selectProProcessByFlag(process.getUnitId(), process.getFlag());
                        if (processl == null) {
                            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                        }
                        if (!processl.getId().equals(rowid)) {
                            return new Result(ResultCode.ERROR.toString(), "此流程并非最新已审核流程，不可驳回！", null, null).getJson();
                        }
                    }
                    if ("审核".equals(flag)) {
                        if (process.getApprovalUnitName() != null) {
                            if (!process.getApprovalUnitName().contains(approveUnitName)) {
                                return new Result(ResultCode.ERROR.toString(), "权限不足！", null, null).getJson();
                            }
                        }
                        Sys_Process processl = processService.selectProcessByFlagAnd(process.getUnitId(), process.getFlag(), "未审批");
                        if (processl == null) {
                            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                        }
                        Sys_Process process2 = processService.selectProcessByFlagAndDate(process.getUnitId(), process.getFlag(), process.getCreateTime());
                        if (process2 != null) {
                            return new Result(ResultCode.ERROR.toString(), "此后有已审核流程，不可复审！", null, null).getJson();
                        }
                        if (!processl.getId().equals(rowid)) {
                            return new Result(ResultCode.ERROR.toString(), "此流程并非最新已驳回流程，不可驳回！", null, null).getJson();
                        }
                    }
                    process.setPeople(people);
                    if (admin) {
                        for (SYS_DataInfo dataInfo : dataInfos) {
                            String param = dataInfo.getParam();
                            if ("驳回".equals(flag)) {
                                param = dataInfo.getBeforeParam();
                            }
                            if (dataInfo.getId().contains("people")) {
                                List<SYS_People> sys_peoples = gson.fromJson(param, new TypeToken<List<SYS_People>>() {
                                }.getType());
                                if (sys_peoples.size() > 0) {
                                    DataManager.savePeopleData(sys_peoples, peopleService, dataInfo.getUnitId(), unitService);
                                    objects.add(sys_peoples);
                                }
                            } else if (dataInfo.getId().contains("rank")) {
                                List<SYS_Rank> sys_ranks = gson.fromJson(param, new TypeToken<List<SYS_Rank>>() {
                                }.getType());
                                if (sys_ranks.size() > 0) {
                                    objects.add(sys_ranks);
                                    DataManager.saveRankData(sys_ranks, rankService, dataInfo.getUnitId(), peopleService);
                                }
                            } else if (dataInfo.getId().contains("duty")) {
                                List<SYS_Duty> sys_duties = gson.fromJson(param, new TypeToken<List<SYS_Duty>>() {
                                }.getType());
                                if (sys_duties.size() > 0) {
                                    DataManager.saveDutyData(sys_duties, dutyService, dataInfo.getUnitId(), peopleService);
                                    objects.add(sys_duties);
                                }
                            } else if (dataInfo.getId().contains("education")) {
                                List<SYS_Education> sys_educations = gson.fromJson(param, new TypeToken<List<SYS_Education>>() {
                                }.getType());
                                if (sys_educations.size() > 0) {
                                    DataManager.saveEducationData(sys_educations, educationService, dataInfo.getUnitId(), peopleService);
                                    objects.add(sys_educations);
                                }
                            } else if (dataInfo.getId().contains("reward")) {
                                List<SYS_Reward> sys_rewards = gson.fromJson(param, new TypeToken<List<SYS_Reward>>() {
                                }.getType());
                                if (sys_rewards.size() > 0) {
                                    DataManager.saveRewardData(sys_rewards, rewardService, dataInfo.getUnitId(), peopleService);
                                    objects.add(sys_rewards);
                                }
                            } else if (dataInfo.getId().contains("assessment")) {
                                List<SYS_Assessment> sys_assessments = gson.fromJson(param, new TypeToken<List<SYS_Assessment>>() {
                                }.getType());
                                if (sys_assessments.size() > 0) {
                                    DataManager.saveAssessmentData(sys_assessments, assessmentService, dataInfo.getUnitId(), peopleService);
                                    objects.add(sys_assessments);
                                }
                            } else if (dataInfo.getId().contains("user")) {
                                List<SYS_USER> sys_users = gson.fromJson(param, new TypeToken<List<SYS_USER>>() {
                                }.getType());
                                if (sys_users.size() > 0) {
                                    DataManager.saveUserData(sys_users, userService, dataInfo.getUnitId());
                                    objects.add(sys_users);
                                }
                            } else if (dataInfo.getId().contains("digest")) {
                                List<SYS_Digest> digestList = gson.fromJson(param, new TypeToken<List<SYS_Digest>>() {
                                }.getType());
                                if (digestList.size() > 0) {
                                    DataManager.saveDigestData(digestList, dataService, dataInfo.getUnitId());
                                    objects.add(digestList);
                                }
                            } else if (dataInfo.getId().contains("approval")) {
                                List<Sys_Approal> sys_approals = gson.fromJson(param, new TypeToken<List<Sys_Approal>>() {
                                }.getType());
                                if (sys_approals.size() > 0) {
                                    String f = "1";
                                    if ("驳回".equals(flag)) {
                                        f = "0";
                                    }
                                    DataManager.saveApprovalData(sys_approals, approvalService, dataInfo.getUnitId(), unitService, f);
                                    objects.add(sys_approals);
                                }
                            } else if (dataInfo.getId().contains("unit") && "驳回".equals(flag)) {
                                List<SYS_UNIT> sys_units = gson.fromJson(param, new TypeToken<List<SYS_UNIT>>() {
                                }.getType());
                                if (sys_units.size() > 0) {
                                    DataManager.saveRejectUnitData(sys_units, unitService);
                                    objects.add(sys_units);
                                }
                            }
                        }
                    }
                }
            }
            List<Sys_Process> csys_processes = processService.selectProcesssByParentId(process.getId());
            process.setProcessTime(new Date());
            if ("不通过".equals(flag)) {
                editeBoHuiProsessTable(csys_processes, process, unit);
            } else if ("驳回".equals(flag)) {
                editeBoHuiProsessTable(csys_processes, process, unit);
            } else if ("审核".equals(flag)) {
                editeAgreeAgenProcessTable(process, admin, unit);
            } else if ("复审".equals(flag)) {
                editeAgreeProcessTable(process, admin, unit);
            } else if ("撤销".equals(flag)) {
                if ("未审批".equals(process.getStates())) {
                    if (csys_processes.size() > 0) {
                        for (Sys_Process cprocess : csys_processes) {
                            processService.deleteProcess(cprocess.getId());
                        }
                    }
                    processService.deleteProcess(process.getId());
                }

            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, objects, null).getJson();
        } else {
            return new Result(ResultCode.ERROR.toString(), "审批表不存在！", null, null).getJson();
        }
    }

    public void editeAgreeAgenProcessTable(Sys_Process process, boolean admin, SYS_UNIT unit) {
        List<Sys_Process> csys_processes = processService.selectProcesssByParentId(process.getId());
        if (csys_processes != null) {
            for (Sys_Process csys_process : csys_processes) {
                if (admin) {
                    csys_process.setStates("已审核");
                } else {
                    csys_process.setStates("初审");
                }
                if (unit.getId().equals(csys_process.getApprovalUnit())) {
                    csys_process.setApprovaled("0");
                }
                csys_process.setProcessTime(new Date());
                csys_process.setId(csys_process.getOldId());
                csys_process.setParentId(process.getOldId());
//                csys_process.setApprovalUnitName(unit.getName());
                Sys_Process process1 = processService.selectProcessById(csys_process.getId());
                if (process1 != null) {
                    processService.updateProcess(csys_process);
                } else {
                    processService.insertProcess(csys_process);
                }
            }
        }
        if (admin) {
            process.setStates("已审核");
        } else {
            process.setStates("初审");
        }
        SYS_UNIT sys_unit = unitService.selectUnitById(unit.getParentId());
        if (sys_unit != null) {
            if (!"单位".equals(sys_unit.getName())) {
                process.setApprovalEve(sys_unit.getId());
            }
        }
        process.setId(process.getOldId());
        process.setApprovalUnitName(unit.getName());
        Sys_Process process2 = processService.selectProcessById(process.getId());
        if (process2 != null) {
            processService.updateProcess(process);
        } else {
            processService.insertProcess(process);
        }
    }

    public void backAgreeProcessTable(Sys_Process process, SYS_UNIT unit) {
        List<Sys_Process> csys_processes = processService.selectProcesssByParentId(process.getId());
        if (csys_processes != null) {
            for (Sys_Process csys_process : csys_processes) {
                if (unit.getId().equals(csys_process.getApprovalUnit())) {
                    csys_process.setApprovaled("0");
                }
                csys_process.setId(csys_process.getOldId());
                csys_process.setParentId(process.getOldId());
                Sys_Process process1 = processService.selectProcessById(csys_process.getId());
                if (process1 != null) {
                    processService.updateProcess(csys_process);
                } else {
                    processService.insertProcess(csys_process);
                }
            }
        }
        process.setStates("未审批");
        process.setPeople(unit.getName());
        processService.updateProcess(process);
    }

    public void editeAgreeProcessTable(Sys_Process process, boolean admin, SYS_UNIT unit) {
        List<Sys_Process> csys_processes = processService.selectProcesssByParentId(process.getId());
        if (csys_processes != null) {
            for (Sys_Process csys_process : csys_processes) {
                Sys_Process csys_process1 = new Sys_Process();
                BeanUtils.copyProperties(csys_process, csys_process1);
                csys_process1.setStates("初审");
                if (unit.getId().equals(csys_process1.getApprovalUnit())) {
                    csys_process1.setApprovaled("0");
                }
                csys_process1.setProcessTime(new Date());
                csys_process1.setId(csys_process.getOldId());
                csys_process1.setParentId(process.getOldId());
//                csys_process1.setApprovalUnitName(unit.getName());
                Sys_Process process1 = processService.selectProcessById(csys_process1.getId());
                if (process1 != null) {
                    processService.updateProcess(csys_process1);
                } else {
                    processService.insertProcess(csys_process1);
                }
                processService.deleteProcess(csys_process.getId());
            }
        }
        Sys_Process process1 = new Sys_Process();
        BeanUtils.copyProperties(process, process1);
        if (admin) {
            process1.setStates("已审核");
        } else {
            process1.setStates("初审");
        }
        process1.setId(process.getOldId());
        process1.setApprovalUnitName(unit.getName());
        Sys_Process process2 = processService.selectProcessById(process1.getId());
        if (process2 != null) {
            processService.updateProcess(process1);
        } else {
            processService.insertProcess(process1);
        }
        editeDataTable(process.getId(), process1.getId(), process.getUnitId());
        processService.deleteProcess(process.getId());
    }

    public void editeBoHuiProsessTable(List<Sys_Process> csys_processes, Sys_Process process, SYS_UNIT unit) {
        if (csys_processes != null) {
            for (Sys_Process csys_process : csys_processes) {
                Sys_Process csys_process1 = new Sys_Process();
                BeanUtils.copyProperties(csys_process, csys_process1);
                csys_process1.setProcessTime(new Date());
                csys_process1.setStates("已驳回");
                csys_process1.setId(csys_process.getId() + "bohui");
                csys_process1.setParentId(process.getId() + "-bohui");
//                csys_process1.setApprovalUnitName(unit.getName());
                if (unit.getId().equals(csys_process1.getApprovalUnit())) {
                    csys_process1.setApprovaled("0");
                }
                Sys_Process process1 = processService.selectProcessById(csys_process1.getId());
                if (process1 != null) {
                    processService.updateProcess(csys_process1);
                } else {
                    processService.insertProcess(csys_process1);
                }
                processService.deleteProcess(csys_process.getId());
            }
        }
        Sys_Process csys_process1 = new Sys_Process();
        Sys_Process process1 = new Sys_Process();
        BeanUtils.copyProperties(process, process1);
        process1.setStates("已驳回");
        process1.setId(process.getOldId() + "-bohui");
        process1.setApprovalUnitName(unit.getName());
        SYS_UNIT sys_unit = unitService.selectUnitById(unit.getParentId());
        if (sys_unit != null) {
            if (!"单位".equals(sys_unit.getName())) {
                process1.setApprovalEve(sys_unit.getId());
            }
        }
        Sys_Process process2 = processService.selectProcessById(process1.getId());
        if (process2 != null) {
            processService.updateProcess(process1);
        } else {
            processService.insertProcess(process1);
        }
        editeDataTable(process.getId(), process1.getId(), process.getUnitId());
        processService.deleteProcess(process.getId());
    }

    public void editeDataTable(String oldProId, String proId, String unitId) {
        SYS_Data data = dataService.selectDataByProcessId(oldProId);
        if (data != null) {
            SYS_Data newdata = new SYS_Data();
            BeanUtils.copyProperties(data, newdata);
            newdata.setId(unitId + proId);
            newdata.setProcessId(proId);
            SYS_Data sys_data = dataService.selectDataById(newdata.getId());
            if (sys_data != null) {
                dataService.updateData(newdata);
            } else {
                dataService.inserData(newdata);
            }
            List<SYS_DataInfo> dataInfos = dataInfoService.selectDataInfosByDataId(data.getId(), "上行");
            if (dataInfos != null) {
                for (SYS_DataInfo dataInfo : dataInfos) {
                    SYS_DataInfo sys_dataInfo = new SYS_DataInfo();
                    BeanUtils.copyProperties(dataInfo, sys_dataInfo);
                    sys_dataInfo.setId(newdata.getId() + dataInfo.getTableName());
                    sys_dataInfo.setDataId(newdata.getId());
                    SYS_DataInfo sys_dataInfo1 = dataInfoService.selectDataInfById(sys_dataInfo.getId());
                    if (sys_dataInfo1 != null) {
                        dataInfoService.updateDataInfo(sys_dataInfo);
                    } else {
                        dataInfoService.inserDataInfo(sys_dataInfo);
                    }
                    dataInfoService.deleteDataInfo(dataInfo.getId());
                }
            }
            dataService.deleteData(data.getId());
        }
    }

    @ApiOperation(value = "驳回数据", notes = "驳回数据", httpMethod = "POST", tags = "驳回数据接口")
    @PostMapping(value = "/rejectData")
    public String rejectData(@RequestParam(value = "dataId", required = false) String dataId, HttpServletRequest request) {
        List<Object> objects = new ArrayList<>();
        String people = "";
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (user == null) {
            return new Result(ResultCode.ERROR.toString(), "账号未登录！", null, null).getJson();
        }
        SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
        if (unit == null) {
            return new Result(ResultCode.ERROR.toString(), "账号未登录！", null, null).getJson();
        }
        if (user.getPeopleName() != null) {
            people = unit.getName() + "/" + user.getPeopleName();
        } else {
            people = unit.getName();
        }
        if (!StrUtils.isBlank(dataId)) {
            SYS_Data data = dataService.selectDataById(dataId);
            if (data != null) {
                Sys_Process process = processService.selectProcessById(data.getProcessId());
                if (process == null) {
                    return new Result(ResultCode.ERROR.toString(), "驳回失败，数据包出错！", null, null).getJson();
                }
                process.setPeople(people);
                processService.updateProcess(process);
                List<Sys_Process> csys_processes = processService.selectProcesssByParentId(process.getId());
                process.setProcessTime(new Date());
                editeBoHuiProsessTable(csys_processes, process, unit);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, objects, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "此数据包为下行数据包", null, null).getJson();
            }
        } else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "返回审批表", notes = "返回审批表", httpMethod = "POST", tags = "返回审批表接口")
    @PostMapping(value = "/backImportData")
    public String backImportData(@RequestParam(value = "dataId", required = false) String dataId, HttpServletRequest request) {
        List<Object> objects = new ArrayList<>();
        String people = "";
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (user == null) {
            return new Result(ResultCode.ERROR.toString(), "账号未登录！", null, null).getJson();
        }
        SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
        if (unit != null) {
            if (user.getPeopleName() != null) {
                people = unit.getName() + "/" + user.getPeopleName();
            } else {
                people = unit.getName();
            }
        }
        if (!StrUtils.isBlank(dataId)) {
            SYS_Data data = dataService.selectDataById(dataId);
            if (data != null) {
                Sys_Process process = processService.selectProcessById(data.getProcessId());
                if (process == null) {
                    return new Result(ResultCode.ERROR.toString(), "驳回失败，数据包出错！", null, null).getJson();
                }
                backAgreeProcessTable(process, unit);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, objects, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "此数据包为下行数据包", null, null).getJson();
            }
        } else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "审批信息", notes = "审批信息", httpMethod = "GET", tags = "审批信息接口")
    @GetMapping("/process")
    @ResponseBody
    public String getProcess(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitName", required = false) String unitName,
                             @RequestParam(value = "approveFlag", required = false) String approveFlag,
                             @RequestParam(value = "states", required = false) String states, HttpServletRequest request) {
        try {
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (user != null) {
                QueryResult queryResult = processService.selectProcesss(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), user.getUnitId(), unitName, approveFlag, states);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "未结束流程审批信息", notes = "未结束流程审批信息", httpMethod = "GET", tags = "未结束流程审批信息接口")
    @GetMapping("/notEndProcess")
    @ResponseBody
    public String notEndProcess(@RequestParam(value = "size", required = false) String pageSize,
                                @RequestParam(value = "page", required = false) String pageNumber,
                                @RequestParam(value = "unitName", required = false) String unitName,
                                @RequestParam(value = "flag", required = false) String flag, HttpServletRequest request) {
        try {
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (user != null) {
                QueryResult queryResult = processService.selectNotEndProcesss(Integer.parseInt(pageSize), Integer.parseInt(pageNumber),
                        user.getUnitId(), unitName, flag);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "职数审批信息详情", notes = "职数审批信息详情", httpMethod = "POST", tags = "职数审批信息详情接口")
    @PostMapping(value = "/processDetail")
    public String getProcessDetail(@RequestParam(value = "rowid", required = false) String rowid) {
        if (!StrUtils.isBlank(rowid)) {
            Sys_Process process = processService.selectProcessById(rowid);
            if (process != null) {
                Sys_Approal sys_processes = gson.fromJson(process.getParam(), new TypeToken<Sys_Approal>() {
                }.getType());
                if (sys_processes != null) {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, sys_processes, null).getJson();
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        } else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    /**
     * 统计表
     */
    @ApiOperation(value = "领导职务情况统计表", notes = "领导职务情况统计表", httpMethod = "POST", tags = "领导职务情况统计表接口")
    @PostMapping(value = "/count")
    public String countLeaderRank(@RequestParam(value = "unitId", required = false) String unitId) {
        if (!StrUtils.isBlank(unitId)) {
            SYS_UNIT unit = unitService.selectUnitById(unitId);
            if (unit != null) {
                DutyCountModel model = new DutyCountModel();

                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, unit, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        } else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    public void getcountLeaderRank(String name, SYS_UNIT unit) {
        DutyCountModel model = new DutyCountModel();
        model.setHdZhengKe(unit.getMainHallNum().toString());
        model.setHdFuKe(unit.getDeputyHallNum().toString());
        Long total1 = unit.getMainHallNum() + unit.getDeputyHallNum();
        model.setHdTotal(total1.toString());
//        model.setTzqZhengKeGanBu();
    }

    @ApiOperation(value = "完成职级晋升情况统计表", notes = "完成职级晋升情况统计表", httpMethod = "GET", tags = "完成职级晋升情况统计表接口")
    @GetMapping(value = "/complete")
    public String countCompleteRank(HttpServletRequest
                                            request, @RequestParam(value = "name", required = false) String name) {
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        List<SYS_UNIT> units = new ArrayList<>();
        List<CompleteModel> models = new ArrayList<>();
        if (user != null) {
            if (!StrUtils.isBlank(name)) {
                SYS_UNIT unit = unitService.selectLikeUnitByName(name);
                if (unit != null) {
                    units.add(unit);
                }
            } else {
                units.add(user.getUnit());
                List<SYS_UNIT> cunits = unitService.selectAllChildUnits(user.getUnitId());
                units.addAll(cunits);
            }
            if (units != null) {
                CompleteModel totalModel = new CompleteModel();
                int firstBatch = 0, secondBatch = 0, thirdBatch = 0, fourBatch = 0, fiveBatch = 0, sexBatch = 0, total = 0;
                for (SYS_UNIT unit : units) {
                    CompleteModel model = DataManager.getCompleteRank(rankService, peopleService, unit.getId(), models, unitService);
                    firstBatch += StrUtils.strToInt(model.getFirstBatch());
                    secondBatch += StrUtils.strToInt(model.getSecondBatch());
                    thirdBatch += StrUtils.strToInt(model.getThirdBatch());
                    fourBatch += StrUtils.strToInt(model.getFourBatch());
                    fiveBatch += StrUtils.strToInt(model.getFiveBatch());
                    sexBatch += StrUtils.strToInt(model.getSexBatch());
                    total += StrUtils.strToInt(model.getTotal());
                }
                totalModel.setFirstBatch(StrUtils.intToStr(firstBatch));
                totalModel.setSecondBatch(StrUtils.intToStr(secondBatch));
                totalModel.setThirdBatch(StrUtils.intToStr(thirdBatch));
                totalModel.setFourBatch(StrUtils.intToStr(fourBatch));
                totalModel.setFiveBatch(StrUtils.intToStr(fiveBatch));
                totalModel.setSexBatch(StrUtils.intToStr(sexBatch));
                totalModel.setTotal(StrUtils.intToStr(total));
                totalModel.setName("合计");
                models.add(totalModel);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, models, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        } else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出完成职级晋升情况统计表", notes = "导出完成职级晋升情况统计表", httpMethod = "POST", tags = "导出完成职级晋升情况统计表")
    @RequestMapping(value = "/outComplete")
    public String getCompleteRankExcel(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "childUnit", required = false) String childUnit) {
        childUnit = childUnit.substring(1, childUnit.length() - 1);
        String[] unitArr;
        try {
            List<CompleteModel> models = new ArrayList<>();
            if (!StrUtils.isBlank(childUnit)) {
//                    childUnit = childUnit.substring(0, childUnit.length() - 1);
                unitArr = childUnit.split(";");
            } else {
                unitArr = new String[]{""};
            }

//                units = unitService.selectAllChildUnits(user.getUnitId());
            CompleteModel totalModel = new CompleteModel();
            int firstBatch = 0, secondBatch = 0, thirdBatch = 0, fourBatch = 0, fiveBatch = 0, sexBatch = 0, total = 0;
            for (String unitid : unitArr) {
                CompleteModel model = DataManager.getCompleteRank(rankService, peopleService, unitid, models, unitService);
                firstBatch += StrUtils.strToInt(model.getFirstBatch());
                secondBatch += StrUtils.strToInt(model.getSecondBatch());
                thirdBatch += StrUtils.strToInt(model.getThirdBatch());
                fourBatch += StrUtils.strToInt(model.getFourBatch());
                fiveBatch += StrUtils.strToInt(model.getFiveBatch());
                sexBatch += StrUtils.strToInt(model.getSexBatch());
                total += StrUtils.strToInt(model.getTotal());
            }
            totalModel.setFirstBatch(StrUtils.intToStr(firstBatch));
            totalModel.setSecondBatch(StrUtils.intToStr(secondBatch));
            totalModel.setThirdBatch(StrUtils.intToStr(thirdBatch));
            totalModel.setFourBatch(StrUtils.intToStr(fourBatch));
            totalModel.setFiveBatch(StrUtils.intToStr(fiveBatch));
            totalModel.setSexBatch(StrUtils.intToStr(sexBatch));
            totalModel.setTotal(StrUtils.intToStr(total));
            totalModel.setName("合计");
            models.add(totalModel);
            ClassPathResource resource = new ClassPathResource("exportExcel/exportCompleteInfo.xls");
            String path = resource.getFile().getPath();
            String[] arr = {"name", "firstBatch", "secondBatch", "thirdBatch", "ganBuNum", "fourBatch", "fiveBatch", "sexBatch", "total"};
            Workbook temp = ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.createExcelFile(temp.getSheet("职级晋升情况"), 2, models, arr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, models, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出调出数据", notes = "导出调出数据", httpMethod = "GET", tags = "导出调出数据接口")
    @GetMapping(value = "/outPeople")
    public String getOutPeople(@RequestParam(value = "peopleId", required = false) String peopleId) {
        try {
            SYS_People peopleById = peopleService.selectPeopleById(peopleId);
            if (peopleById != null) {
                if (peopleById.getStates().contains("调出")) {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, peopleById, null).getJson();
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.EXP_NOT, null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出调出数据", notes = "导出调出数据", httpMethod = "POST", tags = "导出调出数据接口")
    @RequestMapping(value = "/outPeopleData")
    public String upstreamData(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(value = "peopleId", required = false) String peopleId) {
        try {
            List<Object> objects = new ArrayList<>();
            //从请求的header中取出当前登录的登录
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("note", "调出");
            paramsMap.put("dataId", peopleId + DateUtil.getDateNum(new Date()));
            paramsMap.put("peopleId", peopleId);
            Map<String, Object> resultMap = new HashMap<>();
            List<SYS_People> peopleList = new ArrayList<>();
            SYS_People people = peopleService.selectPeopleById(peopleId);
            if (people != null) {
                if (people.getStates().contains("调出")) {
                    peopleList.add(people);
                    JSONArray peoples = JSONArray.fromObject(peopleList);
                    resultMap.put("peopleList", peoples);
                    objects.add(peoples);
                    List<SYS_Duty> dutyList = DataManager.getOutPeopleDutyJson(resultMap, people, dutyService);
                    objects.addAll(dutyList);
                    List<SYS_Rank> rankList = DataManager.getOutPeopleRankJson(resultMap, people, rankService);
                    objects.addAll(rankList);
                    List<SYS_Education> educationList = DataManager.getOutPeopleEducationJson(resultMap, people, educationService);
                    objects.addAll(educationList);
                    List<SYS_Reward> rewardList = DataManager.getOutPeopleRewardJson(resultMap, people, rewardService);
                    objects.addAll(rankList);
                    List<SYS_Assessment> assessmentList = DataManager.getOutPeopleAssessmentJson(resultMap, people, assessmentService);
                    objects.addAll(assessmentList);
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.EXP_NOT, null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            JSONObject resultList = JSONObject.fromObject(resultMap);
            paramsMap.put("result", resultList);
            JSONObject resultJson = JSONObject.fromObject(paramsMap);
            byte[] encode = AESUtil.encrypt(resultJson.toString(), AESUtil.privateKey);
            String paramsCipher = AESUtil.parseByte2HexStr(encode);
            File file = jsonFile;
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(paramsCipher);
            writer.flush();
            writer.close();
            return paramsCipher;
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        } finally {
            jsonFile.deleteOnExit();//程序结束 删除临时文件
        }
    }

    @ApiOperation(value = "导入调出人员数据", notes = "导入调出人员数据", httpMethod = "POST", tags = "导入调出人员数据接口")
    @RequestMapping(value = "/importOutPeopleData")
    public String importOutPeopleData(@RequestParam(value = "excelFile", required = true) MultipartFile
                                              excelFile, @RequestParam(value = "unitId", required = false) String unitId,
                                      @RequestParam(value = "peopleId", required = false) String peopleId) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Object> objects = new ArrayList<>();
        if (!StrUtils.isBlank(peopleId)) {
            SYS_People sys_people = peopleService.selectPeopleById(peopleId);
            if (sys_people != null) {
                unitId = sys_people.getUnitId();
            }
        }
        SYS_UNIT unit = unitService.selectUnitById(unitId);
        if (unit == null) {
            return new Result(ResultCode.ERROR.toString(), "请选择单位！！", null, null).getJson();
        }
        try {
            List<SYS_People> peoples = new ArrayList<>();
            List<SYS_Duty> duties = new ArrayList<>();
            List<SYS_Rank> ranks = new ArrayList<>();
            List<SYS_Reward> rewards = new ArrayList<>();
            List<SYS_Education> educations = new ArrayList<>();
            List<SYS_Assessment> assessments = new ArrayList<>();
            String jsonStrMw = FileUtil.readJsonFile(excelFile.getInputStream());
            byte[] decode = AESUtil.parseHexStr2Byte(jsonStrMw);
            byte[] decryptResult = AESUtil.decrypt(decode, AESUtil.privateKey);
            String jsonStr = new String(decryptResult, "UTF-8");
            JSONObject object = JSONObject.fromObject(jsonStr);
            String note = String.valueOf(object.get("note"));
            String dataId = String.valueOf(object.get("dataId"));
            String peopleDataId = String.valueOf(object.get("peopleId"));
            String idCard = String.valueOf(object.get("idCard"));
            if (!StrUtils.isBlank(note)) {
                if (!"调出".equals(note)) {
                    return new Result(ResultCode.ERROR.toString(), "非调出数据！", null, null).getJson();
                }
                JSONObject key = object.getJSONObject("result");
                SYS_People lpeople = peopleService.selectPeopleByIdcardAndUnitId(idCard, unitId);
                if (lpeople != null) {
                    return new Result(ResultCode.ERROR.toString(), "单位已存在此人！", null, null).getJson();
                } else {
                    JSONArray peopleList = key.getJSONArray("peopleList");
                    JSONArray rankList = key.getJSONArray("rankList");
                    JSONArray dutyList = key.getJSONArray("dutyList");
                    JSONArray educationList = key.getJSONArray("educationList");
                    JSONArray rewardList = key.getJSONArray("rewardList");
                    JSONArray assessmentList = key.getJSONArray("assessmentList");
                    String rankName = "";
                    boolean sold = false;
                    SYS_Rank sys_rank = new SYS_Rank();
                    if (rankList != null) {
                        ranks = DataManager.saveRankJsonModel(rankList);
                        if (ranks.size() > 0) {
                            for (SYS_Rank rank : ranks) {
                                if ("在任".equals(rank.getStatus()) && !"一级科员".equals(rank.getName()) && !"二级科员".equals(rank.getName())) {
                                    sys_rank = rank;
                                    sold = true;
                                    rankName = rank.getName();
                                }
                            }
                            objects.add(ranks);
                        }
                    }
                    if (sold) {
                        List<SYS_Rank> coutRank = rankService.selectRanksByUnitIdAndStatesNotJunZhuan(unitId, rankName, "在任");
                        int size = 1;
                        if (coutRank != null) {
                            size = coutRank.size();
                        }
                        if (!RankController.checkRankNum(unit, sys_rank, size)) {
                            return new Result(ResultCode.ERROR.toString(), sys_rank.getName() + "：已超过了职数设置！", null, null).getJson();
                        }
                    }
                    if (peopleList != null) {
//                        peopleService.deletePeople(peopleDataId);
                        peoples = DataManager.savePeopleJsonModel(peopleList);
                        if (peoples.size() > 0) {
                            for (SYS_People people : peoples) {
                                SYS_People sys_people = peopleService.selectPeopleByIdcardAndUnitId(people.getIdcard(), unit.getId());
                                if (sys_people == null) {
                                    people.setStates("在职");
                                    String puuid = UUID.randomUUID().toString();
                                    people.setId(puuid);
                                    people.setUnitId(unitId);
                                    people.setUnitName(unit.getName());
                                    peopleService.insertPeoples(people);
                                    duties = DataManager.saveDutyJsonModel(dutyList);
                                    if (duties.size() > 0) {
                                        for (SYS_Duty duty : duties) {
                                            String duuid = UUID.randomUUID().toString();
                                            duty.setId(duuid);
                                            duty.setUnitId(unitId);
                                            duty.setPeopleId(puuid);
                                            if (unit != null) {
                                                people.setUnitName(unit.getName());
                                            }
                                            dutyService.insertDuty(duty);
                                        }
                                        objects.add(duties);
                                    }
                                    if (ranks.size() > 0) {
                                        for (SYS_Rank rank : ranks) {
                                            String ruuid = UUID.randomUUID().toString();
                                            rank.setId(ruuid);
                                            rank.setPeopleId(puuid);
                                            rank.setUnitId(unitId);
                                            rankService.insertRank(rank);
                                        }
                                        objects.add(ranks);
                                    }
                                    rewards = DataManager.saveRewardJsonModel(rewardList);
                                    if (rewards.size() > 0) {
                                        for (SYS_Reward reward : rewards) {
                                            String rwuuid = UUID.randomUUID().toString();
                                            reward.setId(rwuuid);
                                            reward.setUnitId(unitId);
                                            reward.setPeopleId(puuid);
                                            rewardService.insertReward(reward);
                                        }
                                        objects.add(rewards);
                                    }
                                    educations = DataManager.saveEducationJsonModel(educationList);
                                    if (educations.size() > 0) {
                                        for (SYS_Education education : educations) {
                                            String euuid = UUID.randomUUID().toString();
                                            education.setId(euuid);
                                            education.setUnitId(unitId);
                                            education.setPeopleId(puuid);
                                            educationService.insertEducation(education);
                                        }
                                        objects.add(educations);
                                    }
                                    assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                                    if (assessments.size() > 0) {
                                        for (SYS_Assessment assessment : assessments) {
                                            String auuid = UUID.randomUUID().toString();
                                            assessment.setId(auuid);
                                            assessment.setUnitId(unitId);
                                            assessment.setPeopleId(puuid);
                                            assessmentService.insertAssessment(assessment);
                                        }
                                        objects.add(assessments);
                                    }
                                } else {
                                    return new Result(ResultCode.ERROR.toString(), "人员已存在！", null, null).getJson();
                                }
                            }
                            objects.add(peoples);
                        }
                    }
                }
                Map<String, Object> map = new HashMap<>();
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, objects, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "调出数据包不全！", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }


    @ApiOperation(value = "导出单位自定义信息", notes = "导出单位自定义信息", httpMethod = "POST", tags = "导出单位自定义信息接口")
    @PostMapping(value = "/customize")
    @ResponseBody
    public String getCustomizeData(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(value = "transferArr") String transferArr, @RequestParam(value = "unitIds") String
                                           unitIds) {
        try {
            if (!StrUtils.isBlank(unitIds) && !StrUtils.isBlank(transferArr)) {
                List<TransferModel> models = new ArrayList<>();
                String[] strArr = transferArr.split(",");
                String[] unitArr = unitIds.split(",");
                String[] arr = new String[strArr.length + 3];
                List<SYS_People> peopleList = peopleService.selectPeoplesByUnitIds(unitArr);
                for (SYS_People people : peopleList) {
                    TransferModel model = new TransferModel();
                    SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
                    if (unit != null) {
                        model.setUnitName(unit.getName());
                    }
                    model.setName(people.getName());
                    model.setStates(people.getStates());
                    arr[0] = "unitName";
                    arr[1] = "name";
                    arr[2] = "states";
                    for (int i = 3; i < strArr.length + 3; i++) {
                        String modelName = "model" + i;
                        arr[i] = modelName;
                        String value = DataManager.getCustomizeData(people, strArr[i - 3], assessmentService);
                        EntityUtil.setFieldValueByFieldName(modelName, model, value);
                    }
                    models.add(model);
                }
                ClassPathResource resource = new ClassPathResource("exportExcel/exportCustomizeData.xls");
                String path = resource.getFile().getPath();
                Workbook temp = ExcelFileGenerator.getTeplet(path);
                ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
                excelFileGenerator.createExcelHeader(temp.getSheet("单位自定义信息表"), strArr);//表头
                excelFileGenerator.createExcelFile(temp.getSheet("单位自定义信息表"), 1, models, arr);
                temp.write(response.getOutputStream());
                temp.close();
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, transferArr, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "自定义查询表字段", notes = "自定义查询表字段", httpMethod = "GET", tags = "自定义查询表字段接口")
    @GetMapping("/outTableData")
    @ResponseBody
    public String outTableDataProp(@RequestParam(value = "transferArr") String transferArr) {
        try {
            if (!StrUtils.isBlank(transferArr)) {
                List<TableModel> models = new ArrayList<>();
                String[] strArr = transferArr.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    String modelName = "model" + (i + 1);
                    TableModel model = new TableModel();
                    model.setProp(modelName);
                    model.setLabel(strArr[i]);
                    models.add(model);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, models, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "自定义查询表", notes = "自定义查询表", httpMethod = "GET", tags = "自定义查询表接口")
    @GetMapping("/customizeInfo")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "transferArr") String transferArr, @RequestParam(value = "unitIds") String unitIds) {
        try {
            if (!StrUtils.isBlank(unitIds) && !StrUtils.isBlank(transferArr)) {
                List<TransferModel> models = new ArrayList<>();
                String[] strArr = transferArr.split(",");
                String[] unitArr = unitIds.split(",");
                String[] arr = new String[strArr.length];
                List<SYS_People> peopleList = peopleService.selectPeoplesByUnitIdsAndPager(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unitArr);
                List<SYS_People> cpeopleList = peopleService.selectPeoplesByUnitIds(unitArr);
                if (peopleList == null) {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
                }
                if (cpeopleList == null) {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
                }
                for (SYS_People people : peopleList) {
                    TransferModel model = new TransferModel();
                    SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
                    if (unit != null) {
                        model.setUnitName(unit.getName());
                    }
                    model.setName(people.getName());
                    model.setStates(people.getStates());
                    for (int i = 0; i < strArr.length; i++) {
                        String modelName = "model" + (i + 1);
                        arr[i] = modelName;
                        String value = DataManager.getCustomizeData(people, strArr[i], assessmentService);
                        EntityUtil.setFieldValueByFieldName(modelName, model, value);
                    }
                    models.add(model);
                }
                Pager pager = new Pager();
                pager.setPageNumber(Integer.parseInt(pageNumber));
                pager.setPageSize(Integer.parseInt(pageSize));
                pager.setRecordCount(cpeopleList.size());
                QueryResult queryResult = new QueryResult(models, pager);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出公务员职级任免审批表", notes = "导出公务员职级任免审批表", httpMethod = "GET", tags = "导出公务员职级任免审批表接口")
    @RequestMapping(value = "/exportFreePeople")
    public String exportFreePeople(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(value = "peopleId", required = false) String peopleId) {
        try {
            ReimbursementModel model = DataManager.exportFreePeople(response, peopleService, peopleId, rankService, educationService, assessmentService, unitService);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出公务员干部任免审批表", notes = "导出公务员干部任免审批表", httpMethod = "GET", tags = "导出公务员干部任免审批表接口")
    @RequestMapping(value = "/exportDutyFreePeople")
    public String exportDutyFreePeople(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(value = "peopleId", required = false) String peopleId) {
        try {
            ReimbursementModel model = DataManager.exportDutyFreePeople(response, peopleService, peopleId, dutyService, educationService, assessmentService, unitService);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "批量导出公务员职级任免审批表", notes = "批量导出公务员职级任免审批表", httpMethod = "GET", tags = "批量导出公务员职级任免审批表接口")
    @RequestMapping(value = "/exportFreePeoples")
    public String exportFreePeoples(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(value = "peopleIds[]", required = false) String[] peopleIds) throws
            IOException, InvalidClassException, Exception {
        if (StrUtils.isBlank(peopleIds)) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        } else {
            Resource resource = new ClassPathResource("exportExcel/intendeAndDepose.xls");
            String path = resource.getFile().getPath();
            List<Workbook> workBookList = new ArrayList<>();
            List<File> srcfile = new ArrayList<File>();
            String filePathStr = "C:\\RM\\file\\excel";
            for (int i = 0; i < peopleIds.length; i++) {
                SYS_People people = peopleService.selectPeopleById(peopleIds[i]);
                Workbook temp = ExcelFileGenerator.getTeplet(path);
                DataManager.exportFreePeoples(temp, peopleService, peopleIds[i], rankService, educationService, assessmentService, unitService);
//                workBookList.add(temp);
                ZipUtil.getFile(filePathStr);
                String filePath = filePathStr + "\\" + people.getName() + "_公务员职级任免审批表.xls";
                FileOutputStream output = new FileOutputStream(filePath);
                temp.write(output);//写入磁盘
                srcfile.add(new File(filePath));
                output.close();
            }
            ZipUtil.zipFiles(srcfile, response, "批量导出公务员职级任免审批表", request);
            ZipUtil.delFile(new File(filePathStr));
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "ok!", null).getJson();

        }
    }

    @ApiOperation(value = "批量导出公务员干部任免审批表", notes = "批量导出公务员干部任免审批表", httpMethod = "GET", tags = "批量导出公务员干部任免审批表接口")
    @RequestMapping(value = "/exportDutyFreePeoples")
    public String exportDutyFreePeoples(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(value = "peopleIds[]", required = false) String[] peopleIds) throws
            IOException, InvalidClassException, Exception {
        if (StrUtils.isBlank(peopleIds)) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        } else {
            Resource resource = new ClassPathResource("exportExcel/intendeDutyAndDepose.xls");
            String path = resource.getFile().getPath();
            List<Workbook> workBookList = new ArrayList<>();
            List<File> srcfile = new ArrayList<File>();
            String filePathStr = "C:\\RM\\file\\excel";
            for (int i = 0; i < peopleIds.length; i++) {
                SYS_People people = peopleService.selectPeopleById(peopleIds[i]);
                Workbook temp = ExcelFileGenerator.getTeplet(path);
                DataManager.exportDutyFreePeoples(temp, peopleService, peopleIds[i], dutyService, educationService, assessmentService, unitService);
//                workBookList.add(temp);
                ZipUtil.getFile(filePathStr);
                String filePath = filePathStr + "\\" + people.getName() + "_公务员干部任免审批表.xls";
                FileOutputStream output = new FileOutputStream(filePath);
                temp.write(output);//写入磁盘
                srcfile.add(new File(filePath));
                output.close();
            }
            ZipUtil.zipFiles(srcfile, response, "批量导出公务员干部任免审批表", request);
            ZipUtil.delFile(new File(filePathStr));
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "ok!", null).getJson();

        }
    }

    @ApiOperation(value = "超职级职数消化情况表初始化", notes = "超职级职数消化情况表初始化", httpMethod = "GET", tags = "超职级职数消化情况表初始化接口")
    @RequestMapping(value = "/saveDigestData")
    public String saveDigestData(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(value = "unitName") String unitName,
                                 @RequestParam(value = "flag") String flag) {
        try {
            if (!StrUtils.isBlank(unitName)) {
                SYS_UNIT unit = unitService.selectUnitByName(unitName);
                if (unit == null) {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
                String[] arr = {"2020.1", "2020.2", "2020.3", "2020.4", "2021.1", "2021.2", "2021.3", "2021.4", "2022.1", "2022.2", "2022.3", "2022.4"};
                List<SYS_Digest> digestList = new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    String[] vua = arr[i].split("\\.");
                    String quarter = vua[1];
                    String year = vua[0];
                    int startMonth = 0;
                    int endMonth = 0;
                    if (quarter.equals("1")) {
                        startMonth = 1;
                        endMonth = 3;
                    } else if (quarter.equals("2")) {
                        startMonth = 4;
                        endMonth = 6;
                    } else if (quarter.equals("3")) {
                        startMonth = 7;
                        endMonth = 9;
                    } else if (quarter.equals("4")) {
                        startMonth = 10;
                        endMonth = 12;
                    }
                    Date startDate = DateUtil.toDate(Integer.parseInt(year), startMonth, 1);
                    Date endDate = DateUtil.toDate(Integer.parseInt(year), endMonth + 1, 1);
                    SYS_Digest digest = new SYS_Digest();
                    String uuid = unit.getId() + year + quarter;
                    SYS_Digest sys_digest = dataService.selectDigestById(uuid);
                    if ("1".equals(flag) && sys_digest != null) {
                        digest = sys_digest;
                    } else {
                        digest.setCreateTime(new Date());
                        digest.setUnitId(unit.getId());
                        digest.setUnitName(unit.getName());
                        digest.setFlag("0");
                        digest.setYears(year);
                        digest.setQuarter(quarter);
                        digest.setYearAndQuarter(digest.getYears() + "/" + quarter);
                        digest.setOneTowClerkApprove(String.valueOf(unit.getOneTowClerkNum()));
                        digest.setThreeFourClerkApprove(String.valueOf(unit.getThreeFourClerkNum()));
                        List<SYS_Rank> towranks = rankService.selectRanksFlagByUnitId(unit.getId(), "是", "二级主任科员");
                        if (towranks != null) {
                            digest.setTowClerkArbitrage(String.valueOf(towranks.size()));
                        }
                        List<SYS_Rank> fourranks = rankService.selectRanksFlagByUnitId(unit.getId(), "是", "四级主任科员");
                        if (fourranks != null) {
                            digest.setFourClerkArbitrage(String.valueOf(fourranks.size()));
                        }
                        int trueOneTowRanks = 0;
                        int trueThreeFourRanks = 0;
                        int oneTowClerkExceed = 0;
                        int threeFourClerkExceed = 0;
                        List<SYS_Rank> trueOneRanks = rankService.selectRanksFlagNotTurnByUnitId(unit.getId(), "是", "一级主任科员");
                        List<SYS_Rank> trueTowRanks = rankService.selectRanksFlagNotTurnByUnitId(unit.getId(), "是", "二级主任科员");
                        List<SYS_Rank> trueThreeRanks = rankService.selectRanksFlagNotTurnByUnitId(unit.getId(), "是", "三级主任科员");
                        List<SYS_Rank> trueFourRanks = rankService.selectRanksFlagNotTurnByUnitId(unit.getId(), "是", "四级主任科员");
                        if (trueOneRanks != null) {
                            trueOneTowRanks += trueOneRanks.size();
                        }
                        if (trueTowRanks != null) {
                            trueOneTowRanks += trueTowRanks.size();
                        }
                        if (trueThreeRanks != null) {
                            trueThreeFourRanks += trueThreeRanks.size();
                        }
                        if (trueFourRanks != null) {
                            trueThreeFourRanks += trueFourRanks.size();
                        }
                        if ((trueOneTowRanks - unit.getOneTowClerkNum()) > 0) {
                            oneTowClerkExceed = (int) (trueOneTowRanks - unit.getOneTowClerkNum());
                            digest.setOneTowClerkExceed(String.valueOf(trueOneTowRanks - unit.getOneTowClerkNum()));
                            digest.setOneTowClerkRemove(String.valueOf(trueOneTowRanks - unit.getOneTowClerkNum()));
                        }
                        if ((trueThreeFourRanks - unit.getThreeFourClerkNum()) > 0) {
                            threeFourClerkExceed = (int) (trueThreeFourRanks - unit.getThreeFourClerkNum());
                            digest.setThreeFourClerkExceed(String.valueOf(trueThreeFourRanks - unit.getThreeFourClerkNum()));
                            digest.setThreeFourClerkRemove(String.valueOf(trueThreeFourRanks - unit.getThreeFourClerkNum()));
                        }
                        List<SYS_People> peopleList = peopleService.selectPeoplesByUnitId(unit.getId(), "0", "全部");
                        int retirePeople = 0;
                        int onealreadyRetire = 0;
                        int towalreadyRetire = 0;
                        int onejinsheng = 0;
                        int onelingdao = 0;
                        int onetuixiu = 0;
                        int onetiqiantuixiu = 0;
                        int onediaochu = 0;
                        int oneqita = 0;
                        int towjinsheng = 0;
                        int towlingdao = 0;
                        int towtuixiu = 0;
                        int towtiqiantuixiu = 0;
                        int towdiaochu = 0;
                        int towqita = 0;
                        if (peopleList != null) {
                            for (SYS_People people : peopleList) {
                                SYS_Rank rank = rankService.selectTurnRankById(people.getId());
                                if (rank != null) {
                                    if (rank.getName().contains("一级主任科员") || rank.getName().contains("二级主任科员") || rank.getName().contains("三级主任科员") || rank.getName().contains("四级主任科员")) {
                                        if (people.getStates().contains("在职")) {
                                            if (people.getPosition() != null && people.getSex() != null && people.getBirthday() != null) {//到期退休
                                                Date retireTime = DataManager.getRetirTime(people.getPosition(), people.getBirthday(), people.getSex());
                                                if (startDate.compareTo(retireTime) >= 0 && endDate.compareTo(retireTime) < 0) {
                                                    retirePeople++;
                                                }
                                            }
                                            SYS_Rank sys_rank = rankService.selectRankByPidAndTimeOrderByTime(people.getId(), rank.getCreateTime(), rank.getName());
                                            if (sys_rank != null) {
                                                if (sys_rank.getName().contains("一级主任科员") || sys_rank.getName().contains("二级主任科员")) {
                                                    onejinsheng++;
                                                } else if (sys_rank.getName().contains("三级主任科员") || sys_rank.getName().contains("四级主任科员")) {
                                                    towjinsheng++;
                                                }
                                            }
                                            SYS_Rank sys_rank1 = rankService.selectAprodRanksByPid(people.getId());
                                            SYS_Duty sys_duty = dutyService.selectDutyByPidOrderByTime(people.getId());
                                            if (sys_rank1 != null && sys_duty != null) {
                                                if (sys_rank1.getStatus().contains("已免")) {
                                                    if (sys_rank1.getName().contains("一级主任科员") || sys_rank1.getName().contains("二级主任科员")) {
                                                        onelingdao++;
                                                    } else if (sys_rank1.getName().contains("三级主任科员") || sys_rank1.getName().contains("四级主任科员")) {
                                                        towlingdao++;
                                                    }
                                                }
                                            }

                                        } else {//已离职
                                            if (people.getOutTime() != null) {
                                                if (startDate.compareTo(people.getOutTime()) >= 0 && endDate.compareTo(people.getOutTime()) < 0) {
                                                    if (rank.getName().contains("一级主任科员") || rank.getName().contains("二级主任科员")) {
                                                        //离职消化途径
                                                        if (people.getStates() == "退休") {
                                                            onetuixiu++;
                                                        } else if (people.getStates() == "提前退休") {
                                                            onetiqiantuixiu++;
                                                        } else if (people.getStates() == "调出") {
                                                            onediaochu++;
                                                        } else if (people.getStates() == "其他") {
                                                            oneqita++;
                                                        }
                                                    } else if (rank.getName().contains("三级主任科员") || rank.getName().contains("四级主任科员")) {
                                                        //离职消化途径
                                                        if (people.getStates() == "退休") {
                                                            towtuixiu++;
                                                        } else if (people.getStates() == "提前退休") {
                                                            towtiqiantuixiu++;
                                                        } else if (people.getStates() == "调出") {
                                                            towdiaochu++;
                                                        } else if (people.getStates() == "其他") {
                                                            towqita++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        digest.setRetirePlanWay(String.valueOf(retirePeople));
                        digest.setOneTowClerkSituation(String.valueOf(onealreadyRetire));
                        digest.setThreeFourClerkSituation(String.valueOf(towalreadyRetire));
                        digest.setRankUpWay(String.valueOf(onejinsheng + towjinsheng));
                        digest.setLeaderDutyWay(String.valueOf(onelingdao + towlingdao));
                        digest.setRetireWay(String.valueOf(onetuixiu + towtuixiu));
                        digest.setEarlyRetireWay(String.valueOf(onetiqiantuixiu + towtiqiantuixiu));
                        digest.setOutWay(String.valueOf(onediaochu + towdiaochu));
                        digest.setOtherWay(String.valueOf(oneqita + towqita));
                        digest.setOneTowClerkResult(String.valueOf(oneTowClerkExceed - onejinsheng - onelingdao - onetuixiu - onetiqiantuixiu - onediaochu - oneqita));
                        digest.setThreeFourClerkResult(String.valueOf(threeFourClerkExceed - towjinsheng - towlingdao - towtuixiu - towtiqiantuixiu - towdiaochu - towqita));
                        digest.setId(uuid);
                        if (sys_digest != null) {
                            dataService.updateDigest(digest);
                        } else {
                            dataService.insertDigest(digest);
                        }
                    }
                    digestList.add(digest);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, digestList, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "超职级职数消化情况表", notes = "超职级职数消化情况表", httpMethod = "GET", tags = "超职级职数消化情况表接口")
    @GetMapping("/digestData")
    @ResponseBody
    public String getDigestData(@RequestParam(value = "unitName", required = false) String unitName) {
        try {
            if (StrUtils.isBlank(unitName)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            SYS_UNIT unit = unitService.selectUnitByName(unitName);
            if (unit == null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            List<SYS_Digest> queryResult = dataService.selectDigestsByUnitId(unit.getId());
            if (queryResult != null) {
                DigestModel model = new DigestModel();
                model.setUnitId(unit.getId());
                for (SYS_Digest digest : queryResult) {
                    if (digest.getYears().contains("2020") && digest.getQuarter().equals("1")) {
                        model.setOneTowClerkRemove1(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove1(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay1(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay1(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2020") && digest.getQuarter().equals("2")) {
                        model.setOneTowClerkRemove2(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove2(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay2(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay2(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2020") && digest.getQuarter().equals("3")) {
                        model.setOneTowClerkRemove3(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove3(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay3(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay3(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2020") && digest.getQuarter().equals("4")) {
                        model.setOneTowClerkRemove4(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove4(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay4(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay4(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("1")) {
                        model.setOneTowClerkRemove5(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove5(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay5(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay5(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("2")) {
                        model.setOneTowClerkRemove6(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove6(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay6(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay6(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("3")) {
                        model.setOneTowClerkRemove7(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove7(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay7(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay7(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("4")) {
                        model.setOneTowClerkRemove8(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove8(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay8(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay8(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("1")) {
                        model.setOneTowClerkRemove9(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove9(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay9(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay9(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("2")) {
                        model.setOneTowClerkRemove10(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove10(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay10(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay10(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("3")) {
                        model.setOneTowClerkRemove11(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove11(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay11(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay11(Integer.valueOf(digest.getUpPlanWay()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("4")) {
                        model.setOneTowClerkRemove12(Integer.valueOf(digest.getOneTowClerkRemove()));
                        model.setThreeFourClerkRemove12(Integer.valueOf(digest.getThreeFourClerkRemove()));
                        model.setRetirePlanWay12(Integer.valueOf(digest.getRetirePlanWay()));
                        model.setUpPlanWay12(Integer.valueOf(digest.getUpPlanWay()));
                    }
                }
                List<SYS_Digest> digestList = new ArrayList<>();
                for (SYS_Digest digest : queryResult) {
                    digest.setModel(model);
                    digestList.add(digest);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, digestList, null).getJson();
            } else {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, new ArrayList<SYS_Digest>(), null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改超职级职数消化情况表", notes = "修改超职级职数消化情况表", httpMethod = "POST", tags = "修改超职级职数消化情况表接口")
    @PostMapping(value = "/editDigest")
    @ResponseBody
    public String editDigest(@Validated @RequestBody DigestModel model) {
        try {
            List<SYS_Digest> digestList = dataService.selectDigestsByUnitId(model.getUnitId());
            if (digestList != null) {
                for (SYS_Digest digest : digestList) {
                    if (digest.getYears().contains("2020") && digest.getQuarter().equals("1")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove1()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove1()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay1()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay1()));
                    } else if (digest.getYears().contains("2020") && digest.getQuarter().equals("2")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove2()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove2()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay2()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay2()));
                    } else if (digest.getYears().contains("2020") && digest.getQuarter().equals("3")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove3()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove3()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay3()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay3()));
                    } else if (digest.getYears().contains("2020") && digest.getQuarter().equals("4")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove4()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove4()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay4()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay4()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("1")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove5()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove5()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay5()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay5()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("2")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove6()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove6()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay6()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay6()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("3")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove7()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove7()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay7()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay7()));
                    } else if (digest.getYears().contains("2021") && digest.getQuarter().equals("4")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove8()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove8()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay8()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay8()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("8")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove9()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove9()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay9()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay9()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("2")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove10()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove10()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay10()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay10()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("3")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove11()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove11()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay11()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay11()));
                    } else if (digest.getYears().contains("2022") && digest.getQuarter().equals("4")) {
                        digest.setOneTowClerkRemove(String.valueOf(model.getOneTowClerkRemove12()));
                        digest.setThreeFourClerkRemove(String.valueOf(model.getThreeFourClerkRemove12()));
                        digest.setRetirePlanWay(String.valueOf(model.getRetirePlanWay12()));
                        digest.setUpPlanWay(String.valueOf(model.getUpPlanWay12()));
                    }
                    dataService.updateDigest(digest);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, digestList, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    public static SimpleDateFormat dmy_hms = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    @ApiOperation(value = "数据库备份", notes = "数据库备份", httpMethod = "POST", tags = "数据库备份接口")
    @PostMapping(value = "/backUpData")
    @ResponseBody
    public String editDigest() {
        String backName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".sql";
        String backPath = "C:\\RM\\file\\data\\";
        ZipUtil.getFile(backPath);
        String dbName = "officeDuty";
        String root = "root";
        String pwd = "1990";
        try {
            String pathSql = backPath + "backUpData_" + backName;
            File fileSql = new File(pathSql);
            //创建备份sql文件
            if (!fileSql.exists()) {
                fileSql.createNewFile();
            }
            //mysqldump -hlocalhost -uroot -p123456 db > /home/back.sql
            StringBuffer sb = new StringBuffer();
            sb.append("mysqldump");
            sb.append(" -h127.0.0.1");
            sb.append(" -u" + root);
            sb.append(" -p" + pwd);
            sb.append(" " + dbName + " >");
            sb.append(pathSql);
            System.out.println("cmd命令为：" + sb.toString());
            Runtime runtime = Runtime.getRuntime();
            System.out.println("开始备份：" + dbName);
            Process process = runtime.exec("cmd /c" + sb.toString());
            byte[] bytes = new byte[process.getInputStream().available()];
            process.getInputStream().read(bytes);
            process.getErrorStream().read(bytes);
            System.out.println("备份成功!");
            ZipUtil.deleteLogFileMyself(backPath, 1);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, "backUpData_" + backName, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }

    @ApiOperation(value = "单位数据备份/首次注册", notes = "单位数据备份/首次注册", httpMethod = "POST", tags = "单位数据备份/首次注册接口")
    @PostMapping(value = "/backUpUnitData")
    @ResponseBody
    public String backUpUnitData(@RequestParam(value = "unitName", required = false) String unitName, @RequestParam(value = "unitId", required = false) String unitId,
                                 @RequestParam(value = "regDateStr", required = false) String regDateStr,@RequestParam(value = "flag", required = false) String flag) {
        SYS_UNIT unit =new SYS_UNIT();
        try {
            Date regDate=new Date();
        if ("注册".equals(flag)){
            if (unitService.selectUnitById(unitId)!=null){
                unit = unitService.selectUnitById(unitId);
            }
            if (regDateStr != null) {
                regDate = DateUtil.stringToDate(regDateStr);
            }
            String regStr = unit.getId() + ";" + DateUtil.dateToString(regDate);
            String regEncrypt = RSAModelUtils.encryptByPublicKey(regStr, RSAModelUtils.moduleA, RSAModelUtils.puclicKeyA);
            unit.setRegCode(regEncrypt);
        }else {
            if (StrUtils.isBlank(unitName)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            if (unitService.selectUnitByName(unitName)!=null){
                unit = unitService.selectUnitByName(unitName);
            }
        }
        if (unit == null) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
            List<Object> objects = new ArrayList<>();
            //从请求的header中取出当前登录的登录
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("note", "成功");
            paramsMap.put("dataId", unit.getId() + DateUtil.getDateNum(new Date()));
            paramsMap.put("unitName", unit.getName());
            paramsMap.put("flag", flag);
            paramsMap.put("unitId", unit.getId());
            Map<String, Object> resultMap = new HashMap<>();
            List<SYS_UNIT> unitList = DataManager.getBackDataUnitJson(resultMap, unit, unitService);//单位
            objects.addAll(unitList);
            List<SYS_UNIT> units = new ArrayList<>();
            units.add(unit);
            List<SYS_UNIT> cunitList = unitService.selectAllChildUnits(unit.getId());
            if (cunitList != null) {
                units.addAll(cunitList);
            }
            List<Sys_Process> processeList = DataManager.getProcessBackDataJson(resultMap, unitList, processService);
            objects.addAll(processeList);
            List<SYS_Data> dataList = DataManager.getDataBackDataJson(resultMap, unitList, dataService);
            objects.addAll(dataList);
            List<SYS_DataInfo> dataInfoList = DataManager.getDataInfoBackDataJson(resultMap, unitList, dataInfoService);
            objects.addAll(dataInfoList);
            List<Sys_Approal> approvalList = DataManager.getApproalJson(resultMap, unitList, approvalService, "下行");
            objects.addAll(approvalList);
            List<SYS_Digest> digestList = DataManager.getDigestBackDataJson(resultMap, unitList, dataService);
            objects.addAll(digestList);
            List<SYS_Message> messageList = DataManager.getMessageBackDataJson(resultMap, unitList, userService);
            objects.addAll(messageList);
            List<SYS_People> peopleList = DataManager.getPeopleJson(resultMap, unitList, peopleService);
            objects.addAll(peopleList);
            List<SYS_USER> userList = DataManager.getUserJson(resultMap, unitList, userService);
            objects.addAll(userList);
            if (peopleList.size() > 0) {
                List<SYS_Duty> dutyList = DataManager.getDutyJson(resultMap, peopleList, dutyService);
                objects.addAll(dutyList);
                List<SYS_Rank> rankList = DataManager.getRankJson(resultMap, peopleList, rankService);
                objects.addAll(rankList);
                List<SYS_Education> educationList = DataManager.getEducationJson(resultMap, peopleList, educationService);
                objects.addAll(educationList);
                List<SYS_Reward> rewardList = DataManager.getRewardJson(resultMap, peopleList, rewardService);
                objects.addAll(rewardList);
                List<SYS_Assessment> assessmentList = DataManager.getAssessmentJson(resultMap, peopleList, assessmentService);
                objects.addAll(assessmentList);
            }else {
                JSONArray array = JSONArray.fromObject(new ArrayList<>());
                resultMap.put("dutyList", array);
                resultMap.put("rankList", array);
                resultMap.put("educationList", array);
                resultMap.put("rewardList", array);
                resultMap.put("assessmentList", array);
            }
            JSONObject resultList = JSONObject.fromObject(resultMap);
            paramsMap.put("result", resultList);
            JSONObject resultJson = JSONObject.fromObject(paramsMap);
            // 加密
//            System.out.println("加密前：" + resultJson.toString());
            byte[] encode = AESUtil.encrypt(resultJson.toString(), AESUtil.privateKey);
            //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
            String paramsCipher = AESUtil.parseByte2HexStr(encode);
            File file = jsonFile;
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(paramsCipher);
            writer.flush();
            writer.close();
            return paramsCipher;
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        } finally {
            jsonFile.deleteOnExit();//程序结束 删除临时文件
        }
    }

    @ApiOperation(value = "导入单位备份数据", notes = "导入单位备份数据", httpMethod = "POST", tags = "导入单位备份数据")
    @RequestMapping(value = "/importBackDatabase")
    public String importBackDatabase(@RequestParam(value = "excelFile", required = true) MultipartFile excelFile, @RequestParam(value = "loginUnitId", required = false) String loginUnitId) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Object> objects = new ArrayList<>();
        try {
            List<SYS_UNIT> units = new ArrayList<>();
            List<SYS_USER> users = new ArrayList<>();
            List<Sys_Approal> approals = new ArrayList<>();
            List<Sys_Process> processes = new ArrayList<>();
            List<SYS_People> peoples = new ArrayList<>();
            List<SYS_Duty> duties = new ArrayList<>();
            List<SYS_Rank> ranks = new ArrayList<>();
            List<SYS_Reward> rewards = new ArrayList<>();
            List<SYS_Education> educations = new ArrayList<>();
            List<SYS_Assessment> assessments = new ArrayList<>();
            String jsonStrMw = FileUtil.readJsonFile(excelFile.getInputStream());
            byte[] decode = AESUtil.parseHexStr2Byte(jsonStrMw);
            byte[] decryptResult = AESUtil.decrypt(decode, AESUtil.privateKey);
            String jsonStr = new String(decryptResult, "UTF-8");
            JSONObject object = JSONObject.fromObject(jsonStr);
            String note = String.valueOf(object.get("note"));
            String dataId = String.valueOf(object.get("dataId"));
            String unitName = String.valueOf(object.get("unitName"));
            String dataType = String.valueOf(object.get("flag"));
            String unitId = String.valueOf(object.get("unitId"));
            if (!StrUtils.isBlank(note)) {
                JSONObject key = object.getJSONObject("result");
                JSONArray userList = new JSONArray();
                userList = key.getJSONArray("userList");
                users = DataManager.saveUserJsonModel(userList);
                boolean bil = false;
                if (loginUnitId.trim().contains(unitId.trim())) {
                    bil = true;
                }
                List<SYS_UNIT> cunitList = unitService.selectAllChildUnits(unitId);
                if (cunitList.size() > 0) {
                    for (SYS_UNIT unit : cunitList) {
                        if (loginUnitId.trim().contains(unit.getId())) {
                            bil = true;
                        }
                    }
                }
                if (!bil) {
                    return new Result(ResultCode.ERROR.toString(), "非本单位及下级单位备份恢复包！", null, null).getJson();
                }
                List<SYS_USER> sys_users = userService.selectAllUsers();
                String userbeforeparam = "";
                if (sys_users != null) {
                    userbeforeparam = gson.toJson(sys_users);
                }
                SYS_Data data = DataManager.saveData(dataId, "", dataType, unitId, dataService);
                JSONArray peopleList = key.getJSONArray("peopleList");
                JSONArray rankList = key.getJSONArray("rankList");
                JSONArray dutyList = key.getJSONArray("dutyList");
                JSONArray educationList = key.getJSONArray("educationList");
                JSONArray rewardList = key.getJSONArray("rewardList");
                JSONArray assessmentList = key.getJSONArray("assessmentList");
                JSONArray unitList = key.getJSONArray("unitList");
                List<String> unitIds=new ArrayList<>();
                if (unitList != null) {
                    units = DataManager.saveUnitJsonModel(unitList);
                    if (units.size() > 0) {
                        for (SYS_UNIT sysUnit:units){
                            unitIds.add(sysUnit.getId());
                        }
                        String beforeparam = null;
                        List<SYS_UNIT> sys_units =unitService.selectUnitAll();
                        if (sys_units!=null){
                            beforeparam = gson.toJson(sys_units);
                        }
                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "unit", gson.toJson(units), beforeparam);
                        DataManager.saveBackUnitData(units, unitService, unitId);
                        objects.add(units);
                    }
                    DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "user", gson.toJson(users), userbeforeparam);
                    DataManager.saveUserData(users, userService, unitId);
                    objects.add(users);
                    JSONArray aprovalList = key.getJSONArray("approvalList");
                    approals = DataManager.saveApproalJsonModel(aprovalList);
                    if (approals.size() > 0) {
                        String beforeparam = null;
                        List<Sys_Approal> us = approvalService.selectApprovals(unitId);
                        if (us != null) {
                            beforeparam = gson.toJson(us);
                        }
                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "approval", gson.toJson(approals), beforeparam);
                        DataManager.saveApprovalData(approals, approvalService, unitId, unitService, "1");
                        objects.add(approals);
                    }
                    JSONArray processList  = key.getJSONArray("processList");
                    if (processes.size() > 0) {
                        String beforeparam = null;
                        List<Sys_Process> us = processService.selectProcesssByUnitIds(unitIds);
                        if (us != null) {
                            beforeparam = gson.toJson(us);
                        }
                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "process", gson.toJson(processes), null);
                        DataManager.saveBackProcessData(processes, processService);
                        objects.add(processes);
                    }
                    if (peopleList != null) {
                        peoples = DataManager.savePeopleJsonModel(peopleList);
                        if (peoples.size() > 0) {
                            String beforeparam = null;
                            List<SYS_People> us = peopleService.selectPeoplesByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "people", gson.toJson(peoples), beforeparam);
                            DataManager.saveBackPeopleData(peoples, peopleService);
                            objects.add(peoples);
                        }
                        duties = DataManager.saveDutyJsonModel(dutyList);
                        if (duties.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Duty> us = dutyService.selectDutysByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "duty", gson.toJson(duties), beforeparam);
                            DataManager.saveBackDutyData(duties, dutyService);
                            objects.add(duties);
                        }
                        ranks = DataManager.saveRankJsonModel(rankList);
                        if (ranks.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Rank> us = rankService.selectRanksByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "rank", gson.toJson(ranks), beforeparam);
                            objects.add(ranks);
                            DataManager.saveBackRankData(ranks, rankService);
                        }
                        rewards = DataManager.saveRewardJsonModel(rewardList);
                        if (rewards.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Reward> us = rewardService.selectRewardsByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "reward", gson.toJson(rewards), beforeparam);
                            DataManager.saveBackRewardData(rewards, rewardService);
                            objects.add(rewards);
                        }
                        educations = DataManager.saveEducationJsonModel(educationList);
                        if (educations.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Education> us = educationService.selectEducationsByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "education", gson.toJson(educations), beforeparam);
                            DataManager.saveBackEducationData(educations, educationService);
                            objects.add(educations);
                        }
                        assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                        if (assessments.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Assessment> us = assessmentService.selectAssessmentsByUnitId(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "assessment", gson.toJson(assessments), beforeparam);
                            DataManager.saveBackAssessmentData(assessments, assessmentService);
                            objects.add(assessments);
                        }
                        JSONArray digestList = key.getJSONArray("digestList");
                        if (digestList!=null){
                            List<SYS_Digest> digests = DataManager.saveDigestJsonModel(digestList);
                            if (digests.size() > 0) {
                                String beforeparam = null;
                                List<SYS_Digest> us = dataService.selectDigestsByUnitId(unitId);
                                if (us != null) {
                                    beforeparam = gson.toJson(us);
                                }
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "digest", gson.toJson(digests), beforeparam);
                                DataManager.saveBackDigestData(digests, dataService);
                                objects.add(digests);
                            }
                        }
                        JSONArray messageList = key.getJSONArray("messageList");
                        if (messageList!=null){
                            List<SYS_Message> messages = DataManager.saveMessageJsonModel(messageList);
                            if (messages.size() > 0) {
                                String beforeparam = null;
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "digest", gson.toJson(messages), null);
                                DataManager.saveBackMessageData(messages, userService);
                                objects.add(messages);
                            }
                        }
                    }
                    Map<String, Object> map = new HashMap<>();
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, objects, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "备份数据包不全！", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }

}
