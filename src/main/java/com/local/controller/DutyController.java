package com.local.controller;

import com.local.entity.sys.SYS_Duty;
import com.local.entity.sys.SYS_People;
import com.local.service.DutyService;
import com.local.service.PeopleService;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/duty")
public class DutyController {
    @Autowired
    private DutyService dutyService;
    @Autowired
    private PeopleService  peopleService;
    private final static Logger logger= LoggerFactory.getLogger(DutyController.class);
    @ApiOperation(value = "职务信息", notes = "职务信息", httpMethod = "GET", tags = "职务信息接口")
    @GetMapping("/info")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "pid", required = false) String pid) {
        try {
            QueryResult queryResult = dutyService.selectDutys(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), pid);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "新增职务", notes = "新增职务", httpMethod = "POST", tags = "新增职务接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertPeople(@Validated @RequestBody SYS_Duty duty) {
        try {
            SYS_Duty dutyByNameAndTime = dutyService.selectDutyByNameAndTime(duty.getName(), duty.getPeopleId(),duty.getCreateTime());
            if (dutyByNameAndTime != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_DUTY_ERROE, null, null).getJson();
            }
            SYS_People people=peopleService.selectPeopleById(duty.getPeopleId());
            if (people!=null){
                String uuid= UUID.randomUUID().toString();
                duty.setId(uuid);
                duty.setPeopleName(people.getName());
                duty.setUnitId(people.getUnitId());
                dutyService.insertDuty(duty);
                SYS_Duty sys_duty=dutyService.selectDutyByPidOrderByTime(people.getId());
                if (sys_duty!=null){
                    if (sys_duty.getStatus().contains("在任")){
                        people.setPosition(sys_duty.getName());
                        people.setPositionTime(sys_duty.getCreateTime());
                        peopleService.updatePeople(people);
                    }else {
                        people.setPosition("");
                        people.setPositionTime(null);
                        peopleService.updatePeople(people);
                    }
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, duty, null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "删除职务", notes = "删除职务", httpMethod = "POST", tags = "删除职务接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteDuty(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            }else {
                SYS_Duty duty=dutyService.selectDutyById(id);
                SYS_People people=peopleService.selectPeopleById(duty.getPeopleId());
                if (people!=null){
                    dutyService.deleteDuty(id);
                    SYS_Duty sys_duty=dutyService.selectDutyByPidOrderByTime(people.getId());
                    if (sys_duty!=null){
                        if (sys_duty.getStatus().contains("在任")){
                            people.setPosition(sys_duty.getName());
                            people.setPositionTime(sys_duty.getCreateTime());
                            peopleService.updatePeople(people);
                        }else {
                            people.setPosition("");
                            people.setPositionTime(null);
                            peopleService.updatePeople(people);
                        }
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
                }else {
                    return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
                }
            }
        }catch (Exception e){
            logger.error(ResultMsg.DEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "修改职务", notes = "修改职务", httpMethod = "POST", tags = "修改职务接口")
    @PostMapping(value = "/edit")
    @ResponseBody
    public String updateDuty(@Validated @RequestBody SYS_Duty duty) {
        try {
            SYS_Duty dutyById = dutyService.selectDutyById(duty.getId());
            if (dutyById != null) {
                duty.setPeopleId(dutyById.getPeopleId());
                SYS_People people=peopleService.selectPeopleById(dutyById.getPeopleId());
                if (people!=null){
                    duty.setPeopleName(people.getName());
                    duty.setUnitId(people.getUnitId());
                    dutyService.updateDuty(duty);
                    SYS_Duty sys_duty=dutyService.selectDutyByPidOrderByTime(people.getId());
                    if (sys_duty!=null) {
                        if (sys_duty.getStatus().contains("在任")) {
                            people.setPosition(sys_duty.getName());
                            people.setPositionTime(sys_duty.getCreateTime());
                            peopleService.updatePeople(people);
                        } else {
                            people.setPosition("");
                            people.setPositionTime(null);
                            peopleService.updatePeople(people);
                        }
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, duty, null).getJson();
                }else {
                    return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
}
