package com.local.controller;

import com.local.common.redis.util.RedisUtil;
import com.local.service.PushRecordService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈铁
 * @Api_注释
 */
@Api(value = "推送记录信息", description = "推送记录信息接口")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class PushRecordsController {
    private final static Logger logger = LoggerFactory.getLogger(PushRecordsController.class);
    @Autowired
    private PushRecordService pushRecordService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "推送记录", notes = "推送记录", httpMethod = "GET", tags = "推送记录查询接口")
    @GetMapping("/pushRecordsPage")
    @ResponseBody
    public String pushRecordsPage(@RequestParam(value = "size", required = false) String pageSize,
                                  @RequestParam(value = "page", required = false) String pageNumber,
                                  @RequestParam(value = "dataType", required = false) String dataType,
                                  @RequestParam(value = "batchNum", required = false) String batchNumber
    ) {
        try {
            QueryResult queryResult = pushRecordService.selectPushRecordPage(Integer.parseInt(pageSize), Integer.parseInt(pageNumber),
                    dataType, batchNumber);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "按批次查询项目", notes = "按批次查询项目", httpMethod = "GET", tags = {"字典管理接口"})
    @GetMapping("/pushRecordDetails")
    @ResponseBody
    public String pushRecordDetails(@RequestParam(value = "size", required = false) String pageSize,
                               @RequestParam(value = "page", required = false) String pageNumber,
                               @RequestParam(value = "dataTable", required = false) String dataTable,
                               @RequestParam(value = "dataType", required = false) String dataType,
                               @RequestParam(value = "batchNum", required = false) String batchNumber) {
        try {
//            System.out.println("推送记录查询开始");
            QueryResult queryResult = pushRecordService.selectPushRecordPageByParam(Integer.parseInt(pageSize), Integer.parseInt(pageNumber),
                    dataTable,dataType, batchNumber);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
}
