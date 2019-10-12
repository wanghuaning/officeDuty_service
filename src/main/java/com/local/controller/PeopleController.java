package com.local.controller;

import com.local.cell.UnitManager;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.PeopleService;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
    private final static Logger logger = LoggerFactory.getLogger(PeopleController.class);

    @Autowired
    private PeopleService peopleService;

    @ApiOperation(value = "人员信息", notes = "人员信息", httpMethod = "GET", tags = "人员信息接口")
    @GetMapping("/info")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitId", required = false) String unitId,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "idcard", required = false) String idcard,
                             @RequestParam(value = "politicalStatus", required = false) String politicalStatus,
                             @RequestParam(value = "enabled", required = false) String enabled) {
        try {
            QueryResult queryResult = peopleService.selectPeoples(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unitId, name, idcard, politicalStatus, enabled);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改人员", notes = "修改人员", httpMethod = "POST", tags = "修改人员接口")
    @PostMapping(value = "/edit")
    @ResponseBody
    public String updatePeople(@Validated @RequestBody SYS_People people) {
        try {
            SYS_People peopleById = peopleService.selectPeopleById(people.getId());
            if (peopleById != null) {
                SYS_People peopleByIdcardAndNotId = peopleService.selectPeopleByIdcardAndNotId(people.getIdcard(), peopleById.getId());
                if (peopleByIdcardAndNotId != null) {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_IDCARD_ERROE, null, null).getJson();
                }
                people.setPeopleOrder(peopleById.getPeopleOrder());
                people.setUnitId(peopleById.getUnitId());
                peopleService.updatePeople(people);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, people, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "新增人员", notes = "新增人员", httpMethod = "POST", tags = "新增人员接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertPeople(@Validated @RequestBody SYS_People people) {
        try {
                SYS_People peopleByIdcardAndUnitId = peopleService.selectPeopleByIdcardAndUnitId(people.getIdcard(), people.getUnitId());
                if (peopleByIdcardAndUnitId != null) {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_IDCARD_ERROE, null, null).getJson();
                }
                String uuid= UUID.randomUUID().toString();
                people.setId(uuid);
                peopleService.insertPeoples(people);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, people, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "删除人员", notes = "删除人员", httpMethod = "POST", tags = "删除人员接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteUnit(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            }else {
                peopleService.deletePeople(id);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.DEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出人员信息", notes = "导出人员信息", httpMethod = "POST", tags = "导出人员信息接口")
    @RequestMapping(value = "/outExcel")
    public String getUnitExcel(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "unitId", required = false) String unitId,
                               @RequestParam(value = "isChild", required = false) String isChild){
        try {
            List<SYS_People> peoples=peopleService.selectPeoplesByUnitId(unitId,isChild);
            Resource resource=new ClassPathResource("exportExcel/exportUnitInfo.xls");
//            File file= ResourceUtils.getFile("classpath:exportExcel/exportUnitInfo.xls");
//            String path=file.getPath();
            String path=resource.getFile().getPath();
            String[] arr={"name","code","simpleName","parentName","buildProvince","buildCity","buildCounty","affiliation","category","level","standingLeaderNum","voceLeaderNum","standingNotLeaderNum","voceNotLeaderNum",
                    "officialNum","referOfficialNum","enterpriseNum","workerNum","otherNum","internalLeaderStanding","internalLeaderVoce","internalNotLeaderStanding","internalNotLeaderVoce","detail"};
            Workbook temp= ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response,"单位信息表导出.xls");
            excelFileGenerator.createExcelFile(temp.getSheet("单位信息"),2,peoples,arr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, peoples, null).getJson();
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
}
