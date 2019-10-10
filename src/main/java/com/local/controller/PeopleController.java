package com.local.controller;

import com.local.service.PeopleService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
    private final static Logger logger= LoggerFactory.getLogger(PeopleController.class);

    @Autowired
    private PeopleService peopleService;
    @ApiOperation(value = "人员信息", notes = "人员信息", httpMethod = "GET", tags = "人员信息接口")
    @GetMapping("/info")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                           @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitId",required = false) String unitId) {
        try {
            QueryResult queryResult = peopleService.selectPeoples(Integer.parseInt(pageSize), Integer.parseInt(pageNumber),unitId);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }


}
