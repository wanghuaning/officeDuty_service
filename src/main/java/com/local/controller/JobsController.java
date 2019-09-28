package com.local.controller;

import com.local.common.redis.util.RedisUtil;
import com.local.entity.elsys.ElSysJob;
import com.local.entity.sys.SYS_USER;
import com.local.service.JobService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Api(value = "岗位信息", description = "岗位信息接口")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class JobsController {
    private final static Logger logger = LoggerFactory.getLogger(RegInfoController.class);
    @Autowired
    private JobService jobService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "岗位信息", notes = "岗位信息", httpMethod = "GET", tags = "岗位信息询接口")
    @GetMapping("/job")
    @ResponseBody
    public String queryAll(@RequestParam(value = "pageSize", required = false) String pageSize,
                           @RequestParam(value = "pageNumber", required = false) String pageNumber,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "enabled", required = false) String enabled) {
        try {
            QueryResult queryResult = jobService.selectAllByParam(name.trim(),Integer.parseInt(enabled),Integer.parseInt(pageSize), Integer.parseInt(pageNumber));
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "新增岗位",notes = "新增岗位",httpMethod = "POST",tags = "新增岗位接口")
    @PostMapping("/job")
    public String add(@Validated @RequestBody ElSysJob job){
        try{
            jobService.create(job);
            job.setCreateTime(new Timestamp(System.currentTimeMillis()));
            System.out.println(job.getCreateTime());
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_SUCCESS, null, null).getJson();
        }catch (Exception e){
            logger.error(ResultMsg.ADD_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }
}

