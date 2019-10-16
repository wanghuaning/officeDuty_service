package com.local.controller;

import com.local.entity.sys.SYS_Duty;
import com.local.entity.sys.SYS_People;
import com.local.service.DutyService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
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
            String uuid= UUID.randomUUID().toString();
            duty.setId(uuid);
            dutyService.insertDuty(duty);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, duty, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }
}
