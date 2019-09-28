package com.local.controller;

import com.local.common.redis.util.RedisUtil;
import com.local.service.RoleService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "角色信息", description = "角色信息接口")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class RolesController {
    private final static Logger logger = LoggerFactory.getLogger(RegInfoController.class);
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "角色信息", notes = "角色信息", httpMethod = "GET", tags = "角色信息询接口")
    @GetMapping("/roles/all")
    @ResponseBody
    public String getAll() {

        try {
            QueryResult queryResult = roleService.selectAllRoles();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "角色信息", notes = "角色信息", httpMethod = "GET", tags = "角色信息询接口")
    @GetMapping("/roles")
    @ResponseBody
    public String getRoles(@RequestParam(value = "pageSize", required = false) String pageSize,
                         @RequestParam(value = "pageNumber", required = false) String pageNumber) {
        try {
            QueryResult queryResult = roleService.selectRoles(Integer.parseInt(pageSize), Integer.parseInt(pageNumber));
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "角色信息", notes = "角色信息", httpMethod = "GET", tags = "角色信息询接口")
    @GetMapping("/roles/id")
    @ResponseBody
    public String getRolesById(@RequestParam(value = "id", required = false) String id) {
        try {
            QueryResult queryResult = roleService.selectRolesById(Integer.parseInt(id));
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

}

