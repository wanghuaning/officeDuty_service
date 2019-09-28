package com.local.controller;

import com.local.common.redis.util.RedisUtil;
import com.local.entity.elsys.ElSysDept;
import com.local.service.DeptService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "部门信息", description = "部门信息接口")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class DeptsController {
    private final static Logger logger = LoggerFactory.getLogger(RegInfoController.class);
    @Autowired
    private DeptService deptService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "查询部门", notes = "查询部门", httpMethod = "GET", tags = "查询部门接口")
    @GetMapping("/dept")
    @ResponseBody
    public String getDepts(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "enabled", required = false) String enabled) {
        try {
            List<ElSysDept> queryResult = deptService.selectDeptsByParam(name,enabled);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

}

