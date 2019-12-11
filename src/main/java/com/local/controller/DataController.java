package com.local.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.local.cell.DataManager;
import com.local.cell.PeopleManager;
import com.local.cell.UnitManager;
import com.local.cell.UserManager;
import com.local.common.config.CompareFileds;
import com.local.common.filter.FileUtil;
import com.local.entity.sys.*;
import com.local.model.*;
import com.local.service.*;
import com.local.util.*;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.dao.QueryResult;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
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
    public DataController() throws IOException {
    }
    @ApiOperation(value = "市级机关公务员职级职数使用审批表", notes = "市级机关公务员职级职数使用审批表", httpMethod = "POST", tags = "市级机关公务员职级职数使用审批表")
    @PostMapping(value = "/approvalData")
    @ResponseBody
     public String getApprovalData(@RequestParam(value = "unitName", required = false) String unitName){
        SYS_UNIT unit = unitService.selectUnitByName(unitName);
        List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unit.getId(), "0");
        Sys_Approal approalModel = new Sys_Approal();
        if (peoples!=null){
            DataManager.getApprovalDataCell(approalModel,unit,peoples, rankService);
            return new Result(ResultCode.SUCCESS.toString(), unitName, approalModel, null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), "无人员！", null, null).getJson();
        }
     }

    @ApiOperation(value = "保存市级机关公务员职级职数使用审批表", notes = "保存市级机关公务员职级职数使用审批表", httpMethod = "POST", tags = "保存市级机关公务员职级职数使用审批表接口")
    @PostMapping(value = "/editApprovalData")
    @ResponseBody
    public String editApprovalData(@Validated @RequestBody Sys_Approal approal) {
        try {
            SYS_UNIT unit=unitService.selectUnitById(approal.getUnitId());
            Sys_Approal approalNow = approvalService.selectApproval(approal.getUnitId(), "0");
            if (approalNow != null) {
                approal.setId(approalNow.getId());
                BeanUtils.copyProperties(approal,approalNow);
                approalNow.setCreateTime(new Date());
                approvalService.updataApproal(approalNow);
                DataManager.setProcessDate(processService,"1",unit,"",gson.toJson(approalNow));
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, approal, null).getJson();
            } else {
                String uuid=UUID.randomUUID().toString();
                approal.setId(uuid);
                approal.setCreateTime(new Date());
                approvalService.insertApproal(approal);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, approal, null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "查询晋升职级人员备案名册", notes = "查询晋升职级人员备案名册", httpMethod = "GET", tags = "查询晋升职级人员备案名册接口")
    @PostMapping(value = "/getRegData")
    @ResponseBody
    public String getRegData(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "unitName", required = false) String unitName,
                             @RequestParam(value = "unitIds", required = false) String[] unitIds) {
        try {
                RegModel regDataInfo = DataManager.getRegDataInfo(unitService, unitName, response, peopleService, rankService,dutyService,assessmentService);
                return new Result(ResultCode.SUCCESS.toString(), unitName, regDataInfo, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "查询晋升职级人员备案名册", notes = "查询晋升职级人员备案名册", httpMethod = "GET", tags = "查询晋升职级人员备案名册接口")
    @PostMapping(value = "/getRegDataByRow")
    @ResponseBody
    public String getRegDataByRow(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "rowId", required = false) String rowId) {
        try {
            Sys_Process process=processService.selectProcessById(rowId);
            if (process!=null){
                RegModel sys_processes = gson.fromJson(process.getParam(), new TypeToken<RegModel>() {
                }.getType());
                if (sys_processes!=null) {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, sys_processes, null).getJson();
                }else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
            }else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "导出晋升职级人员备案名册", notes = "导出晋升职级人员备案名册", httpMethod = "GET", tags = "导出晋升职级人员备案名册接口")
    @RequestMapping(value = "/exportDataExcel")
    public String exportDataExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "flag", required = false) String flag
            , @RequestParam(value = "unitName", required = false) String unitName, @RequestParam(value = "unitIds", required = false) String[] unitIds,
                                  @RequestParam(value = "month", required = false) String month, @RequestParam(value = "day", required = false) String day,
                                  @RequestParam(value = "peopleName", required = false) String peopleName,@RequestParam(value = "peopleNum", required = false) String peopleNum) {
        try {
            if ("filingList".equals(flag)) {//备案表
                RegModel model = new RegModel();
                model.setMonth(month);
                model.setDay(day);
                model.setPeopleName(peopleName);
                model.setPeopleNum(peopleNum);
                List<RankModel> rankModels = DataManager.filingList(unitService, unitName, response, peopleService, rankService,dutyService,assessmentService,model,processService);
                return new Result(ResultCode.SUCCESS.toString(), unitName, rankModels, null).getJson();
            }
            if ("approval".equals(flag)) {
                Sys_Approal approalModel = DataManager.approvalExport(unitService, unitName, response, peopleService, rankService,approvalService);
                return new Result(ResultCode.SUCCESS.toString(), unitName, approalModel, null).getJson();
            } else {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "OK!", null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "导出人员信息", notes = "导出人员信息", httpMethod = "GET", tags = "导出人员信息接口")
    @RequestMapping(value = "/exportPeopleData")
    public String exportPeopleData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "flag", required = false) String flag
            , @RequestParam(value = "peopleId", required = false) String peopleId) {
        try {
            if ("reimbursement".equals(flag)) {
                ReimbursementModel reimbursementModel = DataManager.exportPeopleData(response, peopleService, peopleId,
                        rankService, educationService, assessmentService, unitService);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, reimbursementModel, null).getJson();
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
                            dutyService, rankService, rewardService,assessmentService);
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
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }

    @ApiOperation(value = "导出上行下行数据", notes = "导出上行下行数据", httpMethod = "POST", tags = "导出上行下行数据接口")
    @RequestMapping(value = "/upstreamData")
    public String upstreamData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "unitId", required = false) String unitId,
                               @RequestParam(value = "dataType", required = false) String dataType) {
        try {
            List<Object> objects = new ArrayList<>();
            //从请求的header中取出当前登录的登录
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("note", "成功");
            paramsMap.put("dataId", unitId + DateUtil.getDateNum(new Date()));
            paramsMap.put("dataType", dataType);
            Map<String, Object> resultMap = new HashMap<>();
            List<SYS_UNIT> unitList = DataManager.getUnitJson(resultMap, unitId, unitService);//单位
            objects.addAll(unitList);
            List<SYS_People> peopleList = DataManager.getPeopleJson(resultMap, unitList, peopleService);
            List<SYS_USER> userList = DataManager.getUserJson(resultMap, unitList, userService);
            List<Sys_Approal> approvalList = DataManager.getApproalJson(resultMap, unitList, approvalService,dataType);
            List<Sys_Process>  processeList = DataManager.getProcessJson(resultMap, unitList, processService,dataType);
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
            objects.addAll(approvalList);
            objects.addAll(processeList);
            JSONObject resultList = JSONObject.fromObject(resultMap);
            paramsMap.put("result", resultList);
            JSONObject resultJson = JSONObject.fromObject(paramsMap);
            // 加密
//            System.out.println("加密前：" + resultJson.toString());
            byte[] encode = AESUtil.encrypt(resultJson.toString(),AESUtil.privateKey);
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
    public String importUpstreamData(@RequestParam(value = "excelFile", required = true) MultipartFile excelFile) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            List<SYS_UNIT> units = new ArrayList<>();
            List<SYS_USER> users = new ArrayList<>();
            List<Sys_Approal> approals=new ArrayList<>();
            List<Sys_Process> processes=new ArrayList<>();
            List<SYS_People> peoples = new ArrayList<>();
            List<SYS_Duty> duties = new ArrayList<>();
            List<SYS_Rank> ranks = new ArrayList<>();
            List<SYS_Reward> rewards = new ArrayList<>();
            List<SYS_Education> educations = new ArrayList<>();
            List<SYS_Assessment> assessments = new ArrayList<>();
            String jsonStrMw = FileUtil.readJsonFile(excelFile.getInputStream());
            // 解密
            byte[] decode = AESUtil.parseHexStr2Byte(jsonStrMw);
            byte[] decryptResult = AESUtil.decrypt(decode,AESUtil.privateKey);
//            System.out.println("解密后：" + new String(decryptResult, "UTF-8")); //不转码会乱码
            String jsonStr=new String(decryptResult, "UTF-8");
//            String jsonStr= RSAModelUtils.decryptByPrivateKey(jsonStrMw,RSAModelUtils.moduleA,RSAModelUtils.privateKeyA);
            JSONObject object = JSONObject.fromObject(jsonStr);
            String note = String.valueOf(object.get("note"));
            String dataId = String.valueOf(object.get("dataId"));
            String dataType = String.valueOf(object.get("dataType"));
            if (!StrUtils.isBlank(note)) {
                JSONObject key = object.getJSONObject("result");
                String date = String.valueOf(key.get("date"));
                String unitId = String.valueOf(key.get("unitId"));
                String unitName = String.valueOf(key.get("unitName"));
                JSONArray unitList = key.getJSONArray("unitList");
                JSONArray userList = key.getJSONArray("userList");
                JSONArray peopleList = key.getJSONArray("peopleList");
                JSONArray rankList = key.getJSONArray("rankList");
                JSONArray dutyList = key.getJSONArray("dutyList");
                JSONArray educationList = key.getJSONArray("educationList");
                JSONArray rewardList = key.getJSONArray("rewardList");
                JSONArray assessmentList = key.getJSONArray("assessmentList");
                JSONArray approvalList=key.getJSONArray("approvalList");
                JSONArray processList=key.getJSONArray("processList");
                SYS_UNIT unit = unitService.selectUnitById(unitId);
                if (unit == null) {
                    unit = unitService.selectUnitByName(unitName);
                }
                if (unit != null) {
                    SYS_Data data = DataManager.saveData(dataId, dataType, unitId, dataService);
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("dataId", data.getId());
                    if (unitList != null) {
                        units = DataManager.saveUnitJsonModel(unitList);
                        if (units.size() > 0) {
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "unit", gson.toJson(units));
                        }
                        users = DataManager.saveUserJsonModel(userList);
                        if (users.size() > 0) {
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "user", gson.toJson(users));
                        }
                        if (approvalList.size()>0){
                            approals=DataManager.saveApproalJsonModel(approvalList);
                            if (approals.size()>0){
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "approval", gson.toJson(approals));
                            }
                        }
                        if (processList.size()>0){
                            processes=DataManager.saveProcessJsonModel(processList);
                            if (processes.size()>0){
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "processe", gson.toJson(processes));
                            }
                        }
                        if (peopleList != null) {
                            peoples = DataManager.savePeopleJsonModel(peopleList);
                            if (peoples.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "people", gson.toJson(peoples));
                            }
                            duties = DataManager.saveDutyJsonModel(dutyList);
                            if (duties.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "duty", gson.toJson(duties));
                            }
                            ranks = DataManager.saveRankJsonModel(rankList);
                            if (ranks.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "rank", gson.toJson(ranks));
                            }
                            rewards = DataManager.saveRewardJsonModel(rewardList);
                            if (rewards.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "reward", gson.toJson(rewards));
                            }
                            educations = DataManager.saveEducationJsonModel(educationList);
                            if (educations.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "education", gson.toJson(educations));
                            }
                            assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                            if (assessments.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "assessment", gson.toJson(assessments));
                            }
                        }
                        Map<String, Object> map = new HashMap<>();
                        List<SYS_UNIT> localUnits = DataManager.getUnitJson(map, unitId, unitService);//单位
                        List<SYS_UNIT> deleteUnitList = new ArrayList<>();
                        List<SYS_People> localPeoples = peopleService.selectPeoplesByUnitId(unitId, "1");
                        DataManager.peopleDataCheck(resultMap, peoples, peopleService, localPeoples);
                        DataManager.dutyDataCheck(resultMap, duties, dutyService, unitId);
                        DataManager.rankDataCheck(resultMap, ranks, rankService, unitId);
                        DataManager.educationDataCheck(resultMap, educations, educationService, unitId);
                        DataManager.rewardDataCheck(resultMap, rewards, rewardService, unitId);
                        DataManager.assessmentDataCheck(resultMap, assessments, assessmentService, unitId);
                        DataManager.userDataCheck(resultMap, users, userService, unitId);
                        if (approals.size()>0){
                            DataManager.approvalDataCheck( resultMap,  approals,  approvalService,  unit,
                                    dataType, peopleService, rankService);
                        }
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, resultMap, null).getJson();
                } else {
                    return new Result(ResultCode.ERROR.toString(), "单位不存在！", null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), "上行数据包不全！", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
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
            String jsonStr= RSAModelUtils.decryptByPrivateKey(jsonStrMw,RSAModelUtils.moduleA,RSAModelUtils.privateKeyA);
            System.out.println("导入"+jsonStr);
            JSONObject object = JSONObject.fromObject(jsonStr);
            String note = String.valueOf(object.get("note"));
            String dataId = String.valueOf(object.get("dataId"));
            String dataType = String.valueOf(object.get("dataType"));
            if (!StrUtils.isBlank(note)) {
                JSONObject key = object.getJSONObject("result");
                String date = String.valueOf(key.get("date"));
                String unitID = String.valueOf(key.get("unitId"));
                if (unitID.equals(unitId)){
                    return new Result(ResultCode.ERROR.toString(), "非本单位下行数据包！", null, null).getJson();
                }else {
                    String unitName = String.valueOf(key.get("unitName"));
                    JSONArray unitList = key.getJSONArray("unitList");
                    JSONArray userList = key.getJSONArray("userList");
                    JSONArray aprovalList = key.getJSONArray("approvalList");
                    JSONArray processList = key.getJSONArray("processList");
                    JSONArray peopleList = key.getJSONArray("peopleList");
                    JSONArray rankList = key.getJSONArray("rankList");
                    JSONArray dutyList = key.getJSONArray("dutyList");
                    JSONArray educationList = key.getJSONArray("educationList");
                    JSONArray rewardList = key.getJSONArray("rewardList");
                    JSONArray assessmentList = key.getJSONArray("assessmentList");
                    SYS_UNIT unit = unitService.selectUnitById(unitID);
                    if (unit == null) {
                        unit = unitService.selectUnitByName(unitName);
                    }
                    if (unit != null) {
                        SYS_Data data = DataManager.saveData(dataId, dataType, unitID, dataService);
                        Map<String, Object> resultMap = new HashMap<>();
                        resultMap.put("dataId", data.getId());
                        if (unitList != null) {
                            units = DataManager.saveUnitJsonModel(unitList);
                            if (units.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "unit", gson.toJson(units));
                                DataManager.saveUnitData(units, unitService, unitID);
                                objects.add(units);
                            }
                            users = DataManager.saveUserJsonModel(userList);
                            if (users.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "user", gson.toJson(users));
                                DataManager.saveUserData(users, userService, unitID);
                                objects.add(users);
                            }
                            approals = DataManager.saveApproalJsonModel(aprovalList);
                            if (approals.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "approval", gson.toJson(approals));
                                DataManager.saveApprovalData(approals,  approvalService, unitID);
                                objects.add(approals);
                            }
                            processes = DataManager.saveProcessJsonModel(processList);
                            if (approals.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "process", gson.toJson(processes));
                                DataManager.saveprocessData(processes, processService, "");
                                objects.add(processes);
                            }
                            if (peopleList != null) {
                                peoples = DataManager.savePeopleJsonModel(peopleList);
                                if (peoples.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "people", gson.toJson(peoples));
                                    DataManager.savePeopleData(peoples, peopleService, unitID, unitService);
                                    objects.add(peoples);
                                }
                                duties = DataManager.saveDutyJsonModel(dutyList);
                                if (duties.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "duty", gson.toJson(duties));
                                    DataManager.saveDutyData(duties, dutyService, unitID, peopleService);
                                    objects.add(duties);
                                }
                                ranks = DataManager.saveRankJsonModel(rankList);
                                if (ranks.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "rank", gson.toJson(ranks));
                                    objects.add(ranks);
                                    DataManager.saveRankData(ranks, rankService, unitID, peopleService);
                                }
                                rewards = DataManager.saveRewardJsonModel(rewardList);
                                if (rewards.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "reward", gson.toJson(rewards));
                                    DataManager.saveRewardData(rewards, rewardService, unitID, peopleService);
                                    objects.add(rewards);
                                }
                                educations = DataManager.saveEducationJsonModel(educationList);
                                if (educations.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "education", gson.toJson(educations));
                                    DataManager.saveEducationData(educations, educationService, unitID, peopleService);
                                    objects.add(educations);
                                }
                                assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                                if (assessments.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "assessment", gson.toJson(assessments));
                                    DataManager.saveAssessmentData(assessments, assessmentService, unitID, peopleService);
                                    objects.add(assessments);
                                }
                            }
                            Map<String, Object> map = new HashMap<>();
                            List<SYS_UNIT> localUnits = DataManager.getUnitJson(map, unitID, unitService);//单位
                            List<SYS_UNIT> deleteUnitList = new ArrayList<>();
                            List<SYS_People> localPeoples = peopleService.selectPeoplesByUnitId(unitID, "1");
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

    @ApiOperation(value = "执行上行数据", notes = "执行上行数据", httpMethod = "POST", tags = "执行上行数据接口")
    @PostMapping(value = "/agreeImportData")
    public String agreeImportData(@RequestParam(value = "dataId", required = false) String dataId,HttpServletRequest request) {
        List<Object> objects = new ArrayList<>();
        if (!StrUtils.isBlank(dataId)) {
            List<SYS_DataInfo> dataInfos = dataInfoService.selectDataInfosByDataId(dataId, "上行");
            if (dataInfos != null) {
                for (SYS_DataInfo dataInfo : dataInfos) {
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
                    }else if (dataInfo.getId().contains("approval")) {
                        List<Sys_Approal> sys_approals = gson.fromJson(dataInfo.getParam(), new TypeToken<List<Sys_Approal>>() {
                        }.getType());
                        if (sys_approals.size() > 0) {
                            DataManager.saveApprovalData(sys_approals, approvalService, dataInfo.getUnitId());
                            objects.add(sys_approals);
                        }
                    }else if (dataInfo.getId().contains("process")) {
                        List<Sys_Process> sys_processes = gson.fromJson(dataInfo.getParam(), new TypeToken<List<Sys_Process>>() {
                        }.getType());
                        if (sys_processes.size() > 0) {
                            String name="";
                            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                            if (user != null) {
                                SYS_UNIT unit=unitService.selectUnitById(user.getUnitId());
                                if (unit!=null){
                                    name=unit.getName();
                                }
                                SYS_People people=peopleService.selectPeopleById(user.getPeopleId());
                                if (people!=null){
                                    name=name+"/"+people.getName();
                                }
                            }
                            DataManager.saveprocessData(sys_processes, processService, name);
                            objects.add(sys_processes);
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

    @ApiOperation(value = "审批信息", notes = "审批信息", httpMethod = "GET", tags = "审批信息接口")
    @GetMapping("/process")
    @ResponseBody
    public String getProcess(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitName", required = false) String unitName,
                             @RequestParam(value = "approveFlag", required = false) String approveFlag,HttpServletRequest request) {
        try {
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (user!=null){
                QueryResult queryResult = processService.selectProcesss(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), user.getUnitId(), unitName, approveFlag);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            }else {
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
        if (!StrUtils.isBlank(rowid)){
            Sys_Process process=processService.selectProcessById(rowid);
            if (process!=null){
                Sys_Approal sys_processes = gson.fromJson(process.getParam(), new TypeToken<Sys_Approal>() {
                }.getType());
                if (sys_processes!=null) {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, sys_processes, null).getJson();
                }else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
            }else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
}
