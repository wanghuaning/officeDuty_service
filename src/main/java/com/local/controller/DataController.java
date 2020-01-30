package com.local.controller;


import com.alibaba.fastjson.JSON;
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
import org.nutz.dao.pager.Pager;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.unit.DataUnit;
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
                DataManager.setProcessDate(processService, "1", unit, "", gson.toJson(approalNow));
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
                List<RankModel> rankModels = DataManager.filingList(unitService, unitName, response, peopleService, rankService, dutyService, assessmentService, model, processService);
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
                            dutyService, rankService, rewardService, assessmentService);
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
                               @RequestParam(value = "dataType", required = false) String dataType,@RequestParam(value = "flag", required = false) String flag) {
        try {
            List<Object> objects = new ArrayList<>();
            //从请求的header中取出当前登录的登录
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("note", "成功");
            paramsMap.put("dataId", unitId + DateUtil.getDateNum(new Date()));
            paramsMap.put("dataType", dataType);
            paramsMap.put("flag",flag);
            Map<String, Object> resultMap = new HashMap<>();
            List<SYS_UNIT> unitList = DataManager.getUnitJson(resultMap, unitId, unitService);//单位
            objects.addAll(unitList);
            if (!"职数".equals(flag)){
                List<Sys_Process> processeList = DataManager.getProcessJson(resultMap, unitList, processService, dataType,"0");
                objects.addAll(processeList);
                List<SYS_People> peopleList = DataManager.getPeopleJson(resultMap, unitList, peopleService);
                List<SYS_USER> userList = DataManager.getUserJson(resultMap, unitList, userService);
                List<SYS_Digest> digestList=DataManager.getDigestJson(resultMap, unitList, dataService);
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
            }else {
                List<Sys_Approal> approvalList = DataManager.getApproalJson(resultMap, unitList, approvalService, dataType);
                objects.addAll(approvalList);
                List<Sys_Process> processeList = DataManager.getProcessJson(resultMap, unitList, processService, dataType,"1");
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
            List<SYS_Digest> digests=new ArrayList<>();
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
            String flag=String.valueOf(object.get("flag"));
            if (!StrUtils.isBlank(note)) {
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
                        JSONArray approvalList= new JSONArray();
                        String unitName = String.valueOf(key.get("unitName"));
                        JSONArray unitList = key.getJSONArray("unitList");
                        JSONArray processList = key.getJSONArray("processList");
                        JSONArray userList = new JSONArray(), digestList=new JSONArray(), peopleList = new JSONArray(), rankList = new JSONArray();
                        JSONArray dutyList = new JSONArray(),educationList = new JSONArray(),rewardList = new JSONArray(), assessmentList = new JSONArray();
                        if (!"职数".equals(flag)){
                             userList = key.getJSONArray("userList");
                             digestList = key.getJSONArray("digestList");
                             peopleList = key.getJSONArray("peopleList");
                             rankList = key.getJSONArray("rankList");
                             dutyList = key.getJSONArray("dutyList");
                             educationList = key.getJSONArray("educationList");
                             rewardList = key.getJSONArray("rewardList");
                             assessmentList = key.getJSONArray("assessmentList");
                        }else {
                             approvalList = key.getJSONArray("approvalList");
                        }
                        SYS_UNIT unit = unitService.selectUnitById(iunitId);
                        if (unit == null) {
                            unit = unitService.selectUnitByName(unitName);
                        }
                        if (unit != null) {
                            SYS_Data data = DataManager.saveData(dataId, dataType, iunitId, dataService);
                            Map<String, Object> resultMap = new HashMap<>();
                            resultMap.put("dataId", data.getId());
                            if (unitList.size()>0) {
                                units = DataManager.saveUnitJsonModel(unitList);
                                if (units.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "unit", gson.toJson(units));
                                }
                                users = DataManager.saveUserJsonModel(userList);
                                if (users.size() > 0) {
                                    DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "user", gson.toJson(users));
                                }
                                digests=DataManager.saveDigestJsonModel(digestList);
                                if (digests.size()>0){
                                    DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "digest", gson.toJson(digests));
                                }
                                if (approvalList.size() > 0) {
                                    approals = DataManager.saveApproalJsonModel(approvalList);
                                    if (approals.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "approval", gson.toJson(approals));
                                    }
                                }
                                if (processList.size() > 0) {
                                    processes = DataManager.saveProcessJsonModel(processList, user, "上行", unit, processService);
                                    if (processes.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "processe", gson.toJson(processes));
                                    }
                                }
                                if (peopleList.size()>0) {
                                    peoples = DataManager.savePeopleJsonModel(peopleList);
                                    if (peoples.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "people", gson.toJson(peoples));
                                    }
                                    duties = DataManager.saveDutyJsonModel(dutyList);
                                    if (duties.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "duty", gson.toJson(duties));
                                    }
                                    ranks = DataManager.saveRankJsonModel(rankList);
                                    if (ranks.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "rank", gson.toJson(ranks));
                                    }
                                    rewards = DataManager.saveRewardJsonModel(rewardList);
                                    if (rewards.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "reward", gson.toJson(rewards));
                                    }
                                    educations = DataManager.saveEducationJsonModel(educationList);
                                    if (educations.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "education", gson.toJson(educations));
                                    }
                                    assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                                    if (assessments.size() > 0) {
                                        DataManager.saveDataInfo(dataId, dataType, iunitId, dataInfoService, "assessment", gson.toJson(assessments));
                                    }
                                }
                                Map<String, Object> map = new HashMap<>();
                                List<SYS_UNIT> localUnits = DataManager.getUnitJson(map, iunitId, unitService);//单位
                                List<SYS_UNIT> deleteUnitList = new ArrayList<>();
                                List<SYS_People> localPeoples = peopleService.selectPeoplesByUnitId(iunitId, "1", "在职");
                                if (!"职数".equals(flag)) {
                                    DataManager.peopleDataCheck(resultMap, peoples, peopleService, localPeoples);
                                    DataManager.dutyDataCheck(resultMap, duties, dutyService, iunitId);
                                    DataManager.rankDataCheck(resultMap, ranks, rankService, iunitId);
                                    DataManager.educationDataCheck(resultMap, educations, educationService, iunitId);
                                    DataManager.rewardDataCheck(resultMap, rewards, rewardService, iunitId);
                                    DataManager.assessmentDataCheck(resultMap, assessments, assessmentService, iunitId);
                                    DataManager.userDataCheck(resultMap, users, userService, iunitId);
                                    resultMap.put("digests", digests);
                                }
                                if (approals.size() > 0) {
                                    DataManager.approvalDataCheck(resultMap, approals, approvalService, unitService,
                                            dataType, peopleService, rankService);
                                }
                                if (processes.size() > 0) {
                                    resultMap.put("processList", processes);
                                }
                            }
                            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, resultMap, null).getJson();
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
    public String agreeImportData(@RequestParam(value = "dataId", required = false) String dataId, HttpServletRequest request) {
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
                    }else if (dataInfo.getId().contains("digest")) {
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
                            DataManager.saveApprovalData(sys_approals, approvalService, dataInfo.getUnitId(), unitService);
                            objects.add(sys_approals);
                        }
                    } else if (dataInfo.getId().contains("process")) {
                        List<Sys_Process> sys_processes = gson.fromJson(dataInfo.getParam(), new TypeToken<List<Sys_Process>>() {
                        }.getType());
                        if (sys_processes.size() > 0) {
                            String name = "";
                            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                            if (user != null) {
                                SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
                                if (unit != null) {
                                    name = unit.getName();
                                }
                                SYS_People people = peopleService.selectPeopleById(user.getPeopleId());
                                if (people != null) {
                                    name = name + "/" + people.getName();
                                }
                            }
                            DataManager.saveprocessData(sys_processes, processService, name, user);
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
                if (!unitID.trim().contains(unitId.trim())) {
                    return new Result(ResultCode.ERROR.toString(), "非本单位下行数据包！", null, null).getJson();
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
                    JSONArray assessmentList =  new JSONArray();
                    if (!"职数".equals(flag)){
                         userList = key.getJSONArray("userList");
                         peopleList = key.getJSONArray("peopleList");
                         rankList = key.getJSONArray("rankList");
                         dutyList = key.getJSONArray("dutyList");
                         educationList = key.getJSONArray("educationList");
                         rewardList = key.getJSONArray("rewardList");
                         assessmentList = key.getJSONArray("assessmentList");
                    }else {
                        unitList = key.getJSONArray("unitList");
                        aprovalList = key.getJSONArray("approvalList");
                        processList = key.getJSONArray("processList");
                    }
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
                                DataManager.saveApprovalData(approals, approvalService, unitID, unitService);
                                objects.add(approals);
                            }
                            SYS_UNIT punit = unitService.selectUnitById(unitId);
                            SYS_USER user = new SYS_USER();
                            processes = DataManager.saveProcessJsonModel(processList, user, "下行", unit, processService);
                            if (approals.size() > 0) {
                                DataManager.saveDataInfo(dataId, dataType, unitID, dataInfoService, "process", gson.toJson(processes));
                                DataManager.saveprocessData(processes, processService, "", user);
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
                QueryResult queryResult = processService.selectProcesss(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), user.getUnitId(), unitName, approveFlag,states);
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
    public String countCompleteRank(HttpServletRequest request, @RequestParam(value = "name", required = false) String name) {
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
    public String upstreamData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "peopleId", required = false) String peopleId) {
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
    public String importOutPeopleData(@RequestParam(value = "excelFile", required = true) MultipartFile excelFile, @RequestParam(value = "peopleId", required = false) String peopleId) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Object> objects = new ArrayList<>();
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
            if (!StrUtils.isBlank(note)) {
                if (!"调出".equals(note)) {
                    return new Result(ResultCode.ERROR.toString(), "非调出数据！", null, null).getJson();
                }
                JSONObject key = object.getJSONObject("result");
                SYS_People lpeople = peopleService.selectPeopleById(peopleDataId);
                if (lpeople != null) {
                    return new Result(ResultCode.ERROR.toString(), "单位已存在此人！", null, null).getJson();
                } else {
                    JSONArray peopleList = key.getJSONArray("peopleList");
                    JSONArray rankList = key.getJSONArray("rankList");
                    JSONArray dutyList = key.getJSONArray("dutyList");
                    JSONArray educationList = key.getJSONArray("educationList");
                    JSONArray rewardList = key.getJSONArray("rewardList");
                    JSONArray assessmentList = key.getJSONArray("assessmentList");
                    if (peopleList != null) {
                        peopleService.deletePeople(peopleDataId);
                        peoples = DataManager.savePeopleJsonModel(peopleList);
                        if (peoples.size() > 0) {
                            for (SYS_People people : peoples) {
                                people.setStates("在职");
                                peopleService.insertPeoples(people);
                            }
                            objects.add(peoples);
                        }
                        duties = DataManager.saveDutyJsonModel(dutyList);
                        if (duties.size() > 0) {
                            for (SYS_Duty duty : duties) {
                                dutyService.insertDuty(duty);
                            }
                            objects.add(duties);
                        }
                        ranks = DataManager.saveRankJsonModel(rankList);
                        if (ranks.size() > 0) {
                            for (SYS_Rank rank : ranks) {
                                rankService.insertRank(rank);
                            }
                            objects.add(ranks);
                        }
                        rewards = DataManager.saveRewardJsonModel(rewardList);
                        if (rewards.size() > 0) {
                            for (SYS_Reward reward : rewards) {
                                rewardService.insertReward(reward);
                            }
                            objects.add(rewards);
                        }
                        educations = DataManager.saveEducationJsonModel(educationList);
                        if (educations.size() > 0) {
                            for (SYS_Education education : educations) {
                                educationService.insertEducation(education);
                            }
                            objects.add(educations);
                        }
                        assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                        if (assessments.size() > 0) {
                            for (SYS_Assessment assessment : assessments) {
                                assessmentService.insertAssessment(assessment);
                            }
                            objects.add(assessments);
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
    public String getCustomizeData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "transferArr") String transferArr, @RequestParam(value = "unitIds") String unitIds) {
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
                    arr[1]="name";
                    arr[2]="states";
                    for (int i = 3; i < strArr.length + 3; i++) {
                        String modelName="model" + i;
                        arr[i] = modelName;
                        String value=DataManager.getCustomizeData(people,strArr[i-3],assessmentService);
                        EntityUtil.setFieldValueByFieldName(modelName,model,value);
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
                List<TableModel> models=new ArrayList<>();
                String[] strArr = transferArr.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    String modelName="model" + (i+1);
                    TableModel model=new TableModel();
                    model.setProp(modelName);
                    model.setLabel(strArr[i]);
                    models.add(model);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, models, null).getJson();
            }else {
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
                List<SYS_People> peopleList = peopleService.selectPeoplesByUnitIdsAndPager(Integer.parseInt(pageSize),Integer.parseInt(pageNumber),unitArr);
                List<SYS_People> cpeopleList = peopleService.selectPeoplesByUnitIds(unitArr);
                if (peopleList==null){
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
                }
                if (cpeopleList == null){
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
                        String modelName = "model" + (i+1);
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
            }else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出公务员职级任免审批表", notes = "导出公务员职级任免审批表", httpMethod = "GET", tags = "导出公务员职级任免审批表接口")
    @RequestMapping(value = "/exportFreePeople")
    public String exportFreePeople(HttpServletRequest request, HttpServletResponse response , @RequestParam(value = "peopleId", required = false) String peopleId) {
        try {
            ReimbursementModel model=DataManager.exportFreePeople(response, peopleService,  peopleId,rankService,  educationService,assessmentService,  unitService);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, model, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "超职级职数消化情况表初始化", notes = "超职级职数消化情况表初始化", httpMethod = "GET", tags = "超职级职数消化情况表初始化接口")
    @RequestMapping(value = "/saveDigestData")
    public String saveDigestData(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "unitName") String unitName,
            @RequestParam(value = "flag") String flag) {
        try {
            if (!StrUtils.isBlank(unitName)) {
                SYS_UNIT unit=unitService.selectUnitByName(unitName);
                if (unit==null){
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
                String[] arr={"2020.1","2020.2","2020.3","2020.4","2021.1","2021.2","2021.3","2021.4","2022.1","2022.2","2022.3","2022.4"};
                List<SYS_Digest> digestList=new ArrayList<>();
                for (int i=0;i<arr.length;i++) {
                    String [] vua=arr[i].split("\\.");
                    String quarter=vua[1];
                    String year=vua[0];
                    int startMonth=0;
                    int endMonth=0;
                    if (quarter.equals("1")){
                        startMonth=1;
                        endMonth=3;
                    }else if (quarter.equals("2")){
                        startMonth=4;
                        endMonth=6;
                    }else if (quarter.equals("3")){
                        startMonth=7;
                        endMonth=9;
                    }else if (quarter.equals("4")){
                        startMonth=10;
                        endMonth=12;
                    }
                    Date startDate=DateUtil.toDate(Integer.parseInt(year),startMonth,1);
                    Date endDate=DateUtil.toDate(Integer.parseInt(year),endMonth+1,1);
                    SYS_Digest digest=new SYS_Digest();
                    String uuid=unit.getId()+year+quarter;
                    SYS_Digest sys_digest=dataService.selectDigestById(uuid);
                    if ("1".equals(flag) && sys_digest!=null){
                        digest=sys_digest;
                    }else {
                    digest.setCreateTime(new Date());
                    digest.setUnitId(unit.getId());
                    digest.setUnitName(unit.getName());
                    digest.setFlag("0");
                    digest.setYears(year);
                    digest.setQuarter(quarter);
                    digest.setYearAndQuarter(digest.getYears()+"/"+quarter);
                    digest.setOneTowClerkApprove(String.valueOf(unit.getOneTowClerkNum()));
                    digest.setThreeFourClerkApprove(String.valueOf(unit.getThreeFourClerkNum()));
                    List<SYS_Rank> towranks=rankService.selectRanksFlagByUnitId(unit.getId(),"是","二级主任科员");
                    if (towranks!=null){
                        digest.setTowClerkArbitrage(String.valueOf(towranks.size()));
                    }
                    List<SYS_Rank> fourranks=rankService.selectRanksFlagByUnitId(unit.getId(),"是","四级主任科员");
                    if (fourranks!=null){
                        digest.setFourClerkArbitrage(String.valueOf(fourranks.size()));
                    }
                    int trueOneTowRanks=0;
                    int trueThreeFourRanks=0;
                    int oneTowClerkExceed=0;
                    int threeFourClerkExceed=0;
                    List<SYS_Rank> trueOneRanks=rankService.selectRanksFlagNotTurnByUnitId(unit.getId(),"是","一级主任科员");
                    List<SYS_Rank> trueTowRanks=rankService.selectRanksFlagNotTurnByUnitId(unit.getId(),"是","二级主任科员");
                    List<SYS_Rank> trueThreeRanks=rankService.selectRanksFlagNotTurnByUnitId(unit.getId(),"是","三级主任科员");
                    List<SYS_Rank> trueFourRanks=rankService.selectRanksFlagNotTurnByUnitId(unit.getId(),"是","四级主任科员");
                    if (trueOneRanks!=null){
                        trueOneTowRanks+=trueOneRanks.size();
                    }
                    if (trueTowRanks!=null){
                        trueOneTowRanks+=trueTowRanks.size();
                    }
                    if (trueThreeRanks!=null){
                        trueThreeFourRanks+=trueThreeRanks.size();
                    }
                    if (trueFourRanks!=null){
                        trueThreeFourRanks+=trueFourRanks.size();
                    }
                    if ((trueOneTowRanks-unit.getOneTowClerkNum())>0){
                        oneTowClerkExceed= (int) (trueOneTowRanks-unit.getOneTowClerkNum());
                        digest.setOneTowClerkExceed(String.valueOf(trueOneTowRanks-unit.getOneTowClerkNum()));
                        digest.setOneTowClerkRemove(String.valueOf(trueOneTowRanks-unit.getOneTowClerkNum()));
                    }
                    if ((trueThreeFourRanks-unit.getThreeFourClerkNum())>0){
                        threeFourClerkExceed= (int) (trueThreeFourRanks-unit.getThreeFourClerkNum());
                        digest.setThreeFourClerkExceed(String.valueOf(trueThreeFourRanks-unit.getThreeFourClerkNum()));
                        digest.setThreeFourClerkRemove(String.valueOf(trueThreeFourRanks-unit.getThreeFourClerkNum()));
                    }
                    List<SYS_People> peopleList=peopleService.selectPeoplesByUnitId(unit.getId(),"0","全部" );
                    int retirePeople=0;
                    int onealreadyRetire=0;
                    int towalreadyRetire=0;
                    int onejinsheng=0;
                    int onelingdao=0;
                    int onetuixiu=0;
                    int onetiqiantuixiu=0;
                    int onediaochu=0;
                    int oneqita=0;
                    int towjinsheng=0;
                    int towlingdao=0;
                    int towtuixiu=0;
                    int towtiqiantuixiu=0;
                    int towdiaochu=0;
                    int towqita=0;
                    if (peopleList!=null){
                        for (SYS_People people:peopleList){
                            SYS_Rank rank=rankService.selectTurnRankById(people.getId());
                            if (rank!=null){
                                if (rank.getName().contains("一级主任科员") || rank.getName().contains("二级主任科员") || rank.getName().contains("三级主任科员") || rank.getName().contains("四级主任科员")){
                                    if (people.getStates().contains("在职")){
                                        if (people.getPosition()!=null && people.getSex()!=null && people.getBirthday()!=null){//到期退休
                                            Date retireTime=DataManager.getRetirTime(people.getPosition(),people.getBirthday(),people.getSex());
                                            if (startDate.compareTo(retireTime)>=0 && endDate.compareTo(retireTime)<0){
                                                retirePeople++;
                                            }
                                        }
                                        SYS_Rank sys_rank=rankService.selectRankByPidAndTimeOrderByTime(people.getId(),rank.getCreateTime(),rank.getName());
                                        if (sys_rank!=null){
                                            if (sys_rank.getName().contains("一级主任科员") || sys_rank.getName().contains("二级主任科员")) {
                                                onejinsheng++;
                                            }else if (sys_rank.getName().contains("三级主任科员") || sys_rank.getName().contains("四级主任科员")){
                                                towjinsheng++;
                                            }
                                        }
                                        SYS_Rank sys_rank1=rankService.selectAprodRanksByPid(people.getId());
                                        SYS_Duty sys_duty=dutyService.selectDutyByPidOrderByTime(people.getId());
                                        if (sys_rank1!=null && sys_duty!=null){
                                            if (sys_rank1.getStatus().contains("已免")){
                                                if (sys_rank1.getName().contains("一级主任科员") || sys_rank1.getName().contains("二级主任科员")) {
                                                    onelingdao++;
                                                }else if (sys_rank1.getName().contains("三级主任科员") || sys_rank1.getName().contains("四级主任科员")){
                                                    towlingdao++;
                                                }
                                            }
                                        }

                                    }else {//已离职
                                        if (people.getOutTime()!=null){
                                            if (startDate.compareTo(people.getOutTime())>=0 && endDate.compareTo(people.getOutTime())<0){
                                                    if (rank.getName().contains("一级主任科员") || rank.getName().contains("二级主任科员")){
                                                        //离职消化途径
                                                        if (people.getStates()=="退休"){
                                                            onetuixiu++;
                                                        }else if (people.getStates()=="提前退休"){
                                                            onetiqiantuixiu++;
                                                        }else if (people.getStates()=="调出"){
                                                            onediaochu++;
                                                        }else if (people.getStates()=="其他"){
                                                            oneqita++;
                                                        }
                                                    }else if (rank.getName().contains("三级主任科员") || rank.getName().contains("四级主任科员")){
                                                        //离职消化途径
                                                        if (people.getStates()=="退休"){
                                                            towtuixiu++;
                                                        }else if (people.getStates()=="提前退休"){
                                                            towtiqiantuixiu++;
                                                        }else if (people.getStates()=="调出"){
                                                            towdiaochu++;
                                                        }else if (people.getStates()=="其他"){
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
                    digest.setRankUpWay(String.valueOf(onejinsheng+towjinsheng));
                    digest.setLeaderDutyWay(String.valueOf(onelingdao+towlingdao));
                    digest.setRetireWay(String.valueOf(onetuixiu+towtuixiu));
                    digest.setEarlyRetireWay(String.valueOf(onetiqiantuixiu+towtiqiantuixiu));
                    digest.setOutWay(String.valueOf(onediaochu+towdiaochu));
                    digest.setOtherWay(String.valueOf(oneqita+towqita));
                    digest.setOneTowClerkResult(String.valueOf(oneTowClerkExceed-onejinsheng-onelingdao-onetuixiu-onetiqiantuixiu-onediaochu-oneqita));
                    digest.setThreeFourClerkResult(String.valueOf(threeFourClerkExceed-towjinsheng-towlingdao-towtuixiu-towtiqiantuixiu-towdiaochu-towqita));
                    digest.setId(uuid);
                    System.out.println(digest.getId());
                    if (sys_digest!=null){
                        dataService.updateDigest(digest);
                    }else {
                        dataService.insertDigest(digest);
                    }
                    }
                    digestList.add(digest);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, digestList, null).getJson();
            }else {
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
    public String getDigestData(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitName", required = false) String unitName) {
        try {
            if (StrUtils.isBlank(unitName)){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            SYS_UNIT unit=unitService.selectUnitByName(unitName);
            if (unit==null){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            QueryResult queryResult = dataService.selectDigests(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unit.getId());
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "修改超职级职数消化情况表", notes = "修改超职级职数消化情况表", httpMethod = "POST", tags = "修改超职级职数消化情况表接口")
    @PostMapping(value = "/editDigest")
    @ResponseBody
    public String editDigest(@Validated @RequestBody SYS_Digest digest) {
        try {
            SYS_Digest sys_digest = dataService.selectDigestById(digest.getId());
            if (sys_digest != null) {
                digest.setId(sys_digest.getId());
                digest.setUnitId(sys_digest.getUnitId());
                digest.setYears(sys_digest.getYears());
                digest.setQuarter(sys_digest.getQuarter());
                dataService.updateDigest(digest);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, digest, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
}
