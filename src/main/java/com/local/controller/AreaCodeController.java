package com.local.controller;

import com.local.entity.sys.SYS_AREA;
import com.local.model.AreaModel;
import com.local.service.CodeService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(value = "地区", description = "地区接口")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class AreaCodeController {

    private final static Logger logger = LoggerFactory.getLogger(AreaCodeController.class);
    @Autowired
    private CodeService codeService;

    @ApiOperation(value = "查询行政区", notes = "修改行政区划", httpMethod = "GET", tags = {"字典管理接口"})
    @GetMapping("/selectArea")
    @ResponseBody
    public String selectAreaCityuCode() {
            List<SYS_AREA> areas = codeService.selectAreaCodeByUpCode("1");
            //将区域信息放入redis
            if (areas != null) {
//                redisUtil.set("areas", new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, areas, null).getJson(), 3600);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, areas, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
    }
}
