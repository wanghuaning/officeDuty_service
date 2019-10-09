package com.local.controller;

import com.local.cell.UnitManager;
import com.local.entity.sys.SYS_AREA;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.CodeService;
import com.local.service.UnitService;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
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
    @ApiOperation(value = "查询单位", notes = "查询单位", httpMethod = "GET", tags = "查询单位接口")
    @GetMapping("/unit")
    @ResponseBody
    public String getDepts(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "enabled", required = false) String enabled ) {
        try {
            List<SYS_UNIT> queryResult = unitService.selectUnitsByParam(name, enabled);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
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
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.DEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "导出单位", notes = "导出单位", httpMethod = "GET", tags = "导出单位接口")
    @GetMapping(value = "/unit/outExcel")
    @ResponseBody
    public String getUnitExcel(HttpServletRequest request, HttpServletResponse response){
        System.out.println("导出");
        try {
            List<SYS_UNIT> unitList=unitService.selectUnitAll();
//            Resource  resource=new ClassPathResource("exportExcel/exportUnitInfo.xlsl");
            File file= ResourceUtils.getFile("classpath:exportExcel/exportUnitInfo.xlsx");
            String path=file.getPath();
            String[] arr={"name","code","simpleName","parentName","area","affiliation","category","level","standingLeaderNum","voceLeaderNum","standingNotLeaderNum","voceNotLeaderNum",
            "officialNum","referOfficialNum","enterpriseNum","workerNum","otherNum","internalLeaderStanding","internalLeaderVoce","internalNotLeaderStanding","internalNotLeaderVoce","detail"};
            Workbook temp=ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response,"单位信息表导出.xlsx");
            excelFileGenerator.createExcelFile(temp.getSheet("单位信息"),2,unitList,arr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, unitList, null).getJson();
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
}
