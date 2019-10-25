package com.local.controller;

import com.local.cell.PeopleManager;
import com.local.cell.UnitManager;
import com.local.common.redis.util.RedisUtil;
import com.local.entity.sys.*;
import com.local.service.*;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 单位控制器
 */
@RestController
@RequestMapping("/api")
public class UnitConttoller {
    private final static Logger logger = LoggerFactory.getLogger(UnitConttoller.class);

    @Autowired
    private UnitService unitService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private RankService rankService;

    @Autowired
    private RewardService rewardService;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private EducationService educationService;
    @ApiOperation(value = "查询单位", notes = "查询单位", httpMethod = "GET", tags = "查询单位接口")
    @GetMapping("/unit")
    @ResponseBody
    public String getUnits(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "enabled", required = false) String enabled,HttpServletRequest request ) {
        try {
            //从请求的header中取出当前登录的登录
            String token=request.getHeader("userToken");
            if (token == null || "".equals(token)){
                token=request.getParameter("userToken");//从请求的url中获取
            }
            SYS_USER user=redisUtil.getUserByKey(token);
            if (user!=null){
                String parentId=user.getUnitId();
                if (!StrUtils.isBlank(parentId)){
                    List<SYS_UNIT> queryResult = unitService.selectUnitsByParam(name, enabled,parentId);
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, user).getJson();
                }else {
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
                }

            }else {
                return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "新增单位", notes = "新增单位", httpMethod = "POST", tags = "新增单位接口")
    @PostMapping(value = "/unit/add")
    @ResponseBody
    public String createUnit(@Validated @RequestBody SYS_UNIT unit) {
        try {
            SYS_UNIT unitbyname = unitService.selectUnitByName(unit.getName());
            if (unitbyname != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_NAME_ERROE, null, null).getJson();
            }
            SYS_UNIT unitbycode = unitService.selectUnitByCode(unit.getCode());
            if (unitbycode!=null){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
            }
            String uuid= UUID.randomUUID().toString();
            unit.setId(uuid);
            unit.setEnabled("0");
            UnitManager.setUnitArea(unit);
            SYS_UNIT punit=unitService.selectUnitById(unit.getParentId());
            if (punit!=null){
                unit.setParentName(punit.getName());
            }
            unitService.insertUnit(unit);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, unit, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改单位", notes = "修改单位", httpMethod = "POST", tags = "修改单位接口")
    @PostMapping(value = "/unit/edit")
    @ResponseBody
    public String updateUnit(@Validated @RequestBody SYS_UNIT unit) {
        try {
            SYS_UNIT unitById=unitService.selectUnitById(unit.getId());
            if (unitById!=null){
                if (!unitById.getName().trim().equals(unit.getName().trim())){
                    SYS_UNIT unitbyname = unitService.selectUnitByName(unit.getName());
                    if (unitbyname != null) {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_NAME_ERROE, null, null).getJson();
                    }
                    SYS_UNIT unitbycode = unitService.selectUnitByCode(unit.getCode());
                    if (unitbycode!=null){
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                    }
                }
                UnitManager.setUnitArea(unit);
                unit.setUnitOrder(unitById.getUnitOrder());
                unitService.updateUnit(unit);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, unit, null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "删除单位", notes = "删除单位", httpMethod = "POST", tags = "删除单位接口")
    @PostMapping(value = "/unit/delete")
    @ResponseBody
    public String deleteUnit(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            }else {
                unitService.deleteUnit(id);
                List<SYS_UNIT> cunits=unitService.selectAllChildUnits(id);
                if (cunits.size()>0){
                    for (SYS_UNIT unit:cunits){
                        unitService.deleteUnit(unit.getId());
                    }
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.DEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "导出单位", notes = "导出单位", httpMethod = "GET", tags = "导出单位接口")
    @RequestMapping(value = "/unit/outExcel")
    public String getUnitExcel(HttpServletRequest request, HttpServletResponse response){
        System.out.println("导出");
        try {
            List<SYS_UNIT> unitList=unitService.selectUnitAll();
            ClassPathResource  resource=new ClassPathResource("exportExcel/exportUnitInfo.xls");
//            File file= ResourceUtils.getFile("classpath:exportExcel/exportUnitInfo.xls");
//            String path=file.getPath();
            String path=resource.getFile().getPath();
            String[] arr={"name","code","parentName","buildProvince","buildCity","buildCounty","affiliation","category","level","officialNum","referOfficialNum","referOfficialDate","referOfficialDocument","mainHallNum","deputyHallNum",
            "rightPlaceNum","deputyPlaceNum","oneInspectorNum","towInspectorNum","oneResearcherNum","towResearcherNum","threeResearcherNum"
                    ,"fourResearcherNum","oneClerkNum","towClerkNum","threeClerkNum","fourClerkNum","detail"};
            Workbook temp=ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response,"单位信息表导出.xls");
            excelFileGenerator.createExcelFile(temp.getSheet("单位信息"),2,unitList,arr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, unitList, null).getJson();
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "导出Excel模板", notes = "导出Excel模板", httpMethod = "GET", tags = "导出Excel模板接口")
    @RequestMapping(value = "/unit/outExcelModel")
    public String getUnitExcelModel(HttpServletRequest request, HttpServletResponse response,@RequestParam("flag") String flag){
        try {
            ClassPathResource  resource=null;
            String excelName="全部信息采集表.xls";
            String sheetName="填报说明";
            if ("all".equals(flag)){
                resource=new ClassPathResource("exportExcel/exportAllInfo.xls");
            }else if ("unit".equals(flag)){
                resource=new ClassPathResource("exportExcel/exportUnitInfo.xls");
                excelName="单位信息采集表.xls";
                sheetName="单位信息";
            }else if ("people".equals(flag)){
                resource=new ClassPathResource("exportExcel/exportPeopleInfo.xls");
                excelName="人员信息采集表.xls";
                sheetName="人员信息";
            }
            String path=resource.getFile().getPath();
            String[] arr=null;
            Workbook temp=ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response,excelName);
            excelFileGenerator.createExcelFile(temp.getSheet(sheetName),2,new ArrayList<>(),arr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, excelName, null).getJson();
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "导入单位", notes = "导入单位", httpMethod = "POST", tags = "导入单位接口")
    @RequestMapping(value = "/unit/import")
    public String importUnitExcel(@RequestParam("excelFile") MultipartFile excelFile){
        StringBuffer stringBuffer=new StringBuffer();
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeader(excelFile.getInputStream(),0,1);
            if (headList.size()>0){
                if (!headList.get(0).contains("单位名称") && !headList.get(1).contains("组织机构编码")){
                    stringBuffer.append(ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelData(excelFile.getInputStream(),0,1,2);
                    List<SYS_UNIT> unitList=UnitManager.getUnitDataByExcel(list,unitService,stringBuffer);
                    if (unitList.size()>0){
                        for (SYS_UNIT unit:unitList){
                            SYS_UNIT sys_unit=unitService.selectUnitById(unit.getId());
                            if (sys_unit!=null){
                                SYS_UNIT punit=unitService.selectUnitByName(unit.getParentName());
                                if (punit!=null){
                                    unit.setEnabled("0");
                                    unit.setUnitOrder(sys_unit.getUnitOrder());
                                    unit.setParentName(punit.getParentName());
                                    unit.setParentId(punit.getId());
                                    unitService.updateUnit(unit);
                                }else {
                                    stringBuffer.append(unit.getName()+":上级单位不存在，请核查！");
                                    logger.error(unit.getName()+":上级单位不存在，请核查！");
                                    unitService.deleteUnit(unit.getId());
                                }
                            }
                        }
                    }
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),unitList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,unitList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }

    @ApiOperation(value = "导入全部信息", notes = "导入全部信息", httpMethod = "POST", tags = "导入全部信息接口")
    @RequestMapping(value = "/unit/importAll")
    public String importUnitAllInfoExcel(@RequestParam("excelFile") MultipartFile excelFile,@RequestParam(value = "fullImport", required = false) String fullImport){
        StringBuffer stringBuffer=new StringBuffer();
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeaderBySheetName(excelFile.getInputStream(),"B01单位",1);
            if (headList.size()>0){
                if (!headList.get(0).contains("单位名称") && !headList.get(1).contains("组织机构编码")){
                    stringBuffer.append("单位表："+ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelDataBySheetName(excelFile.getInputStream(),"B01单位",1,2);
                    List<SYS_UNIT> unitList=UnitManager.getUnitDataByExcel(list,unitService,stringBuffer);
                    if (unitList.size()>0){
                        for (SYS_UNIT unit:unitList){
                            SYS_UNIT sys_unit=unitService.selectUnitById(unit.getId());
                            if (sys_unit!=null){
                                SYS_UNIT punit=unitService.selectUnitByName(unit.getParentName());
                                if (punit!=null){
                                    unit.setEnabled("0");
                                    unit.setUnitOrder(sys_unit.getUnitOrder());
                                    unit.setParentName(punit.getParentName());
                                    unit.setParentId(punit.getId());
                                    unitService.updateUnit(unit);
                                }else {
                                    stringBuffer.append(unit.getName()+":上级单位不存在，请核查！");
                                    logger.error(unit.getName()+":上级单位不存在，请核查！");
                                    unitService.deleteUnit(unit.getId());
                                }
                            }
                        }
                    }
                    importPeopleExcel(excelFile,fullImport,stringBuffer);
                    importPeopleDutyExcel(excelFile,fullImport,stringBuffer);
                    importPeopleRankExcel(excelFile,fullImport,stringBuffer);
                    importPeopleEducationExcel(excelFile,fullImport,stringBuffer);
                    importPeopleRewardExcel(excelFile,fullImport,stringBuffer);
                    importPeopleAssessmentExcel(excelFile,fullImport,stringBuffer);
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),unitList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,unitList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }


    public String importPeopleExcel(MultipartFile excelFile,String fullImport,StringBuffer stringBuffer){
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeaderBySheetName(excelFile.getInputStream(),"A01人员",1);
            if (headList.size()>0){
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")){
                    stringBuffer.append("人员表："+ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelDataBySheetName(excelFile.getInputStream(),"A01人员",1,2);
                    List<SYS_People> peopleList= PeopleManager.getPeopleDataByExcel(list,peopleService,stringBuffer,unitService,fullImport);
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),peopleList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,peopleList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }
    public String importPeopleDutyExcel(MultipartFile excelFile,String fullImport,StringBuffer stringBuffer){
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeaderBySheetName(excelFile.getInputStream(),"A02职务",1);
            if (headList.size()>0){
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")){
                    stringBuffer.append("职务表："+ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelDataBySheetName(excelFile.getInputStream(),"A02职务",1,2);
                    List<SYS_Duty> dutyList= PeopleManager.getPeopleDutyDataByExcel(list,peopleService,stringBuffer,unitService,fullImport,dutyService);
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),dutyList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,dutyList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }

    public String importPeopleRankExcel(MultipartFile excelFile,String fullImport,StringBuffer stringBuffer){
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeaderBySheetName(excelFile.getInputStream(),"A03职级",1);
            if (headList.size()>0){
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")){
                    stringBuffer.append("职级表："+ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelDataBySheetName(excelFile.getInputStream(),"A03职级",1,2);
                    List<SYS_Rank> dutyList= PeopleManager.getPeopleRankDataByExcel(list,peopleService,stringBuffer,unitService,fullImport,rankService);
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),dutyList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,dutyList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }
    public String importPeopleEducationExcel(MultipartFile excelFile,String fullImport,StringBuffer stringBuffer){
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeaderBySheetName(excelFile.getInputStream(),"A04学历学位",1);
            if (headList.size()>0){
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")){
                    stringBuffer.append("学历表："+ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelDataBySheetName(excelFile.getInputStream(),"A04学历学位",1,2);
                    List<SYS_Education> dutyList= PeopleManager.getPeopleEducationDataByExcel(list,peopleService,stringBuffer,unitService,fullImport,educationService);
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),dutyList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,dutyList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }

    public String importPeopleRewardExcel(MultipartFile excelFile,String fullImport,StringBuffer stringBuffer){
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeaderBySheetName(excelFile.getInputStream(),"A05奖惩",1);
            if (headList.size()>0){
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")){
                    stringBuffer.append("奖惩表："+ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelDataBySheetName(excelFile.getInputStream(),"A05奖惩",1,2);
                    List<SYS_Reward> dutyList= PeopleManager.getPeopleRewardDataByExcel(list,peopleService,stringBuffer,unitService,fullImport,rewardService);
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),dutyList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,dutyList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }
    public String importPeopleAssessmentExcel(MultipartFile excelFile,String fullImport,StringBuffer stringBuffer){
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList=ExcelFileGenerator.readeExcelHeaderBySheetName(excelFile.getInputStream(),"A06考核",1);
            if (headList.size()>0){
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")){
                    stringBuffer.append("考核表："+ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(),ResultMsg.IMPORT_EXCEL_FILE_ERROR,null,null).getJson();
                }else {
                    List<Map<String, Object>> list=ExcelFileGenerator.readeExcelDataBySheetName(excelFile.getInputStream(),"A06考核",1,2);
                    List<SYS_Assessment> dutyList= PeopleManager.getPeopleAssessmentDataByExcel(list,peopleService,stringBuffer,unitService,fullImport,assessmentService);
                    if (stringBuffer.length()>0){
                        return new Result(ResultCode.SUCCESS.toString(),stringBuffer.toString(),dutyList,null).getJson();
                    }else {
                        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.IMPORT_EXCEL_SUCCESS,dutyList,null).getJson();
                    }
                }
            }else {
                return new Result(ResultCode.ERROR.toString(),stringBuffer.toString(),null,null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),e.toString(),null,null).getJson();
        }
    }
}
