package com.local.controller;

import com.local.cell.UserManager;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.SYS_USER;
import com.local.service.LogsService;
import com.local.service.PeopleService;
import com.local.service.UnitService;
import com.local.service.UserService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import com.local.util.StrUtils;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作日志控制器
 */
@RestController
@RequestMapping("/api/logs")
public class LogsController {
    private final static Logger logger = LoggerFactory.getLogger(LogsController.class);

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserService userService;
    @Autowired
    private LogsService logsService;

    @Autowired
    private PeopleService peopleService;
    @ApiOperation(value = "操作日志", notes = "操作日志", httpMethod = "GET", tags = "操作日志接口")
    @GetMapping("/user")
    @ResponseBody
    public String getLogs(@RequestParam(value = "size", required = false) String pageSize,
                           @RequestParam(value = "page", required = false) String pageNumber,
                           @RequestParam(value = "name", required = false) String name,HttpServletRequest request) {
        try {
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (user!=null){
                QueryResult queryResult = logsService.selectLogs(Integer.parseInt(pageSize), Integer.parseInt(pageNumber),user.getUserAccount(),name);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            }else {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "人员信息", notes = "人员信息", httpMethod = "GET", tags = "人员信息接口")
    @GetMapping("/logs")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "name", required = false) String name,HttpServletRequest request) {
        try {
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (user!=null) {
                QueryResult queryResult = logsService.selectLogss(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), name,user.getUnitId());
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
}
