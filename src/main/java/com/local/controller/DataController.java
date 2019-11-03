package com.local.controller;

import com.local.cell.DataManager;
import com.local.cell.PeopleManager;
import com.local.cell.UnitManager;
import com.local.common.filter.FileUtil;
import com.local.common.redis.util.RedisUtil;
import com.local.entity.sys.*;
import com.local.model.ApproalModel;
import com.local.model.RankModel;
import com.local.model.ReimbursementModel;
import com.local.model.ResultModel;
import com.local.service.*;
import com.local.util.*;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/api/data")
public class DataController {
    private final File jsonFile=File.createTempFile("downloadJson",".json");//创建临时文件
    private final static Logger logger = LoggerFactory.getLogger(DataController.class);
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
    private RedisUtil redisUtil;

    public DataController() throws IOException {
    }

    @ApiOperation(value = "导出晋升职级人员备案名册", notes = "导出晋升职级人员备案名册", httpMethod = "GET", tags = "导出晋升职级人员备案名册接口")
    @RequestMapping(value = "/exportDataExcel")
    public String exportDataExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "flag", required = false) String flag
            , @RequestParam(value = "unitName", required = false) String unitName, @RequestParam(value = "unitIds", required = false) String[] unitIds) {
        try {
            if ("filingList".equals(flag)) {
                List<RankModel> rankModels = DataManager.filingList(unitService, unitName, response, peopleService, rankService);
                return new Result(ResultCode.SUCCESS.toString(), unitName, rankModels, null).getJson();
            }
            if ("approval".equals(flag)) {
                ApproalModel approalModel = DataManager.approvalExport(unitService, unitName, response, peopleService, rankService);
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
            List<String> headList = ExcelFileGenerator.readeExcelHeader(excelFile.getInputStream(), 0, 1);
            if (headList.size() > 0) {
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")) {
                    stringBuffer.append(ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.IMPORT_EXCEL_FILE_ERROR, null, null).getJson();
                } else {
                    List<Map<String, Object>> list = ExcelFileGenerator.readeExcelData(excelFile.getInputStream(), 0, 1, 2);
                    List<SYS_People> peopleList = DataManager.getPeopleDataByExcel(list, peopleService, stringBuffer, unitService, fullImport, educationService,
                            dutyService, rankService, rewardService);
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

    @ApiOperation(value = "导出上行数据", notes = "导出上行数据", httpMethod = "POST", tags = "导出上行数据接口")
    @RequestMapping(value = "/upstreamData")
    public String upstreamData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "unitId", required = false) String unitId) {
        System.out.println("导出json");
        try {
            List<Object> objects = new ArrayList<>();
            //从请求的header中取出当前登录的登录
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("note", "成功");
            Map<String, Object> resultMap = new HashMap<>();
            List<SYS_UNIT> unitList = DataManager.getUnitJson(resultMap, unitId, unitService);//单位
            objects.addAll(unitList);
            List<SYS_People> peopleList = DataManager.getPeopleJson(resultMap, unitList, peopleService);
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
            JSONObject resultList = JSONObject.fromObject(resultMap);
            paramsMap.put("result", resultList);
            JSONObject resultJson = JSONObject.fromObject(paramsMap);
            System.out.println(resultJson.toString());
            File file = jsonFile;
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(resultJson.toString());
            writer.flush();
            writer.close();
            return resultJson.toString();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }finally {
            jsonFile.deleteOnExit();//程序结束 删除临时文件
        }
    }

    @ApiOperation(value = "导入上行数据", notes = "导入上行数据", httpMethod = "POST", tags = "导入上行数据接口")
    @RequestMapping(value = "/importUpstreamData")
    public String importUpstreamData(@RequestParam(value = "excelFile",required = true) MultipartFile excelFile){
        StringBuffer stringBuffer=new StringBuffer();
        try {
            String jsonStr=FileUtil.readJsonFile(excelFile.getInputStream());
            JSONObject object=JSONObject.fromObject(jsonStr);
            String note=String.valueOf(object.get("note"));
            if (!StrUtils.isBlank(note)){
                JSONObject key=object.getJSONObject("result");
                String date=String.valueOf(key.get("date"));
                String unitId=String.valueOf(key.get("unitId"));
                String unitName=String.valueOf(key.get("unitName"));
                JSONArray unitList=key.getJSONArray("unitList");
                JSONArray peopleList=key.getJSONArray("peopleList");
                JSONArray rankList=key.getJSONArray("rankList");
                JSONArray dutyList=key.getJSONArray("dutyList");
                JSONArray educationList=key.getJSONArray("educationList");
                JSONArray rewardList=key.getJSONArray("rewardList");
                JSONArray assessmentList=key.getJSONArray("assessmentList");
                SYS_UNIT unit=unitService.selectUnitById(unitId);
                if (unit!=null){
                    if (unitList!=null){

                    }
                }else {
                    SYS_UNIT unit1=unitService.selectUnitByName(unitName);
                    if (unit1!=null){

                    }else {

                    }
                }
            } else {
                return new Result(ResultCode.ERROR.toString(),"上行数据包不全！",null,null).getJson();
            }
            return jsonStr;
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }
}
