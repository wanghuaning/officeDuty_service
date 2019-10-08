package com.local.controller;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.sys.SYS_AREA;
import com.local.entity.sys.SYS_CODE;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.CodeService;
import com.local.service.UnitService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import com.local.util.StrUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
//            SYS_CODE affiliation=codeService.selectCodeById(unit.getAffiliation());
//            if (affiliation!=null){
//                unit.setAffiliation(affiliation.getCodeName());
//            }
//            SYS_CODE category=codeService.selectCodeById(unit.getCategory());
//            if (category!=null){
//                unit.setCategory(category.getCodeName());
//            }
//            SYS_CODE level=codeService.selectCodeById(unit.getLevel());
//            if (level!=null){
//                unit.setLevel(level.getCodeName());
//            }
            if (!StrUtils.isBlank(unit.getAreaStrs())) {
                if (unit.getAreaStrs().length > 0) {
                    SYS_AREA area = codeService.selectAreaByCode(unit.getAreaStrs()[unit.getAreaStrs().length - 1]);
                }
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
//                SYS_CODE affiliation=codeService.selectCodeById(unit.getAffiliation());
//                if (affiliation!=null){
//                    unit.setAffiliation(affiliation.getCodeName());
//                }
//                SYS_CODE category=codeService.selectCodeById(unit.getCategory());
//                if (category!=null){
//                    unit.setCategory(category.getCodeName());
//                }
//                SYS_CODE level=codeService.selectCodeById(unit.getLevel());
//                if (level!=null){
//                    unit.setLevel(level.getCodeName());
//                }
                if (!StrUtils.isBlank(unit.getAreaStrs())){
                    if (unit.getAreaStrs().length>0){
                        SYS_AREA area=codeService.selectAreaByCode(unit.getAreaStrs()[unit.getAreaStrs().length-1]);
                    }
                }
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
}
