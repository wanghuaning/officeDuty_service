package com.local.controller;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.UnitService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单位控制器
 */
@RestController
@RequestMapping("/api")
public class UnitConttoller {
   private final static Logger logger= LoggerFactory.getLogger(UnitConttoller.class);

    @Autowired
    private UnitService unitService;

    @ApiOperation(value = "查询单位", notes = "查询单位", httpMethod = "GET", tags = "查询单位接口")
    @GetMapping("/unit")
    @ResponseBody
    public String getDepts(@RequestParam(value = "name", required = false) String name,
             @RequestParam(value = "enabled", required = false) String enabled) {
        try {
            List<SYS_UNIT> queryResult = unitService.selectUnitsByParam(name,enabled);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

}
