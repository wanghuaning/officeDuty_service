package com.local.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.local.cell.UnitManager;
import com.local.common.redis.util.RedisUtil;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.SYS_USER;
import com.local.model.ImgResult;
import com.local.service.PeopleService;
import com.local.service.UnitService;
import com.local.service.UserService;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final static Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private UnitService unitService;
    @Autowired
    private PeopleService peopleService;

    @ApiOperation(value = "登录",notes = "登录",httpMethod = "POST",tags = "登录管理接口")
    @PostMapping("/login")
    public String Login(@Validated @RequestBody SYS_USER user){
        Object code=redisUtil.get(user.getUuid());
//        System.out.println(user.getUserAccount()+"=>"+user.getUuid()+"=>"+code+"->"+user.getCode());
        if (StrUtils.isBlank(code)){
            return new Result(ResultCode.ERROR.toString(),ResultMsg.CHECK_EXPIRE,null,null).getJson();
        }
        if (StrUtils.isBlank(user.getUuid()) || !user.getCode().equalsIgnoreCase(String.valueOf(code))){//不区分大小写equalsIgnoreCase
            return new Result(ResultCode.ERROR.toString(),ResultMsg.CHECK_ERROR,null,null).getJson();
        }
        SYS_USER searchUser=null;
        //如果不是免密登录
        if (StrUtils.isBlank(user.getToken())){
            //查询用户名和密码是否匹配，密码需要先解密（前台），再加密（后台），然后匹配
            user.setUserPassword(MD5Utils.encryptPassword(user.getUserPassword()));
            searchUser=userService.selectUserByModel(user);
            if (searchUser==null){
                return new Result(ResultCode.ERROR.toString(),ResultMsg.LOGIN_ERROR,null,null).getJson();
            }
        }else{
            searchUser=RedisUtil.getUserByKey(user.getToken());
            if (StrUtils.isBlank(searchUser)){
                return new Result(ResultCode.ERROR.toString(),ResultMsg.LOGOUT_ERROR,null,null).getJson();
            }
        }
        //返回前台的对象
        HashMap<String,Object> token=new HashMap<>();
        searchUser=userService.selectUserByName(user.getUserAccount());
        SYS_UNIT unit=unitService.selectUnitById(searchUser.getUnitId());
        if (unit!=null){
            token.put("unit",unit);
            searchUser.setUnit(unit);
        }
        SYS_People people=peopleService.selectPeopleById(searchUser.getPeopleId());
        if (people!=null){
            token.put("people",people);
            searchUser.setPeople(people);
        }
        //查询菜单
//        searchUser=userService.selectRoleMenu(searchUser);
        //将用户id放入redis
        redisUtil.set(searchUser.getId(),searchUser,3600*24);
        //前台去除密码
        searchUser.setUserPassword("");
        token.put("token",searchUser);
        logger.info(ResultMsg.LOGIN_SUCCESS);
        return new Result(ResultCode.SUCCESS.toString(),ResultMsg.LOGIN_SUCCESS,token,null).getJson();
    }
    /**
     * 获取用户信息
     */
    @GetMapping(value = "/info")
    public String getUserInfo(HttpServletRequest request){
        //从请求的header中取出当前登录的登录
        String token=request.getHeader("userToken");
        if (token==null || "".equals(token)){
            //从请求的url中取出当前登录的登录
            token=request.getParameter("userToken");
        }
        //通过token从redis中取出当前登录
        SYS_USER user=redisUtil.getUserByKey(token);
        if (user==null){
            return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
        }else{
            return new Result(ResultCode.SUCCESS.toString(),ResultMsg.GET_FIND_SUCCESS,user,null).getJson();
        }
    }
    /**
     * 获取验证码
     *
     * @param response
     * @return
     */
    @GetMapping(value = "/vCode")
    public String getCode(HttpServletResponse response) throws IOException {
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);//生产随机字符串
        String uuid = IdUtil.simpleUUID();
        redisUtil.set(uuid, verifyCode,60);
        //生产图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            ImgResult im=new ImgResult(Base64.encode(stream.toByteArray()), uuid);
            return new Result(ResultCode.SUCCESS.toString(),ResultMsg.GET_FIND_SUCCESS,im,null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR,e);
            return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
        } finally {
            stream.close();
        }
    }


    @ApiOperation(value = "修改密码",notes = "修改密码\"",httpMethod = "GET",tags = "修改密码接口")
    @GetMapping("/updatePass")
    public String updatePass(@RequestParam(value = "oldPass",required = true) String oldPass,
                             @RequestParam(value = "newPass",required = true) String newPass,HttpServletRequest request){
        //从请求的header中取出当前登录的登录
        String token=request.getHeader("userToken");
        if (token==null || "".equals(token)){
            //从请求的url中取出当前登录的登录
            token=request.getParameter("userToken");
        }
        //通过token从redis中取出当前登录
        SYS_USER user=redisUtil.getUserByKey(token);
        if (user!=null){
            String passStr=MD5Utils.encryptPassword(oldPass);
            if (passStr.equals(user.getUserPassword())){
                user.setUserPassword(MD5Utils.encryptPassword(newPass));
                userService.updateUser(user);
                return new Result(ResultCode.SUCCESS.toString(),ResultMsg.LOGIN_ERROR_PASS,user,null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(),ResultMsg.UPDATE_SUCCESS,null,null).getJson();
            }
        }else{
            return new Result(ResultCode.ERROR.toString(),ResultMsg.UPDATE_ERROR,null,null).getJson();
        }
    }
    @ApiOperation(value = "用户信息", notes = "用户信息", httpMethod = "GET", tags = "用户信息接口")
    @GetMapping("/account")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitId", required = false) String unitId,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "enabled", required = false) String enabled,HttpServletRequest request) {
        try {
            if (StrUtils.isBlank(unitId)){
                String token=request.getHeader("userToken");
                if (token == null || "".equals(token)){
                    token=request.getParameter("userToken");//从请求的url中获取
                }
                SYS_USER user=redisUtil.getUserByKey(token);
                if (user!=null){
                    unitId=user.getUnitId();
                }
            }
            QueryResult queryResult = userService.selectUsersByUnitId(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unitId, name, enabled);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "新增用户", notes = "新增用户", httpMethod = "POST", tags = "新增用户接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String createUser(@Validated @RequestBody SYS_USER user) {
        try {
            SYS_USER unitbyname = userService.selectUserByName(user.getUserAccount());
            if (unitbyname != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_EXIST, null, null).getJson();
            }
            SYS_People people=peopleService.selectPeopleById(user.getPeopleId());
            if (people!=null){
                user.setPeople(people);
                user.setPeopleName(people.getName());
            }
            SYS_UNIT unit=unitService.selectUnitById(user.getUnitId());
            if (unit!=null){
                user.setUnit(unit);
                user.setUnitName(unit.getName());
            }
            user.setUserPassword(MD5Utils.encryptPassword(user.getUserPassword()));
            String uuid= UUID.randomUUID().toString();
            user.setId(uuid);
            user.setEnabled("0");
            userService.insertUser(user);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, user, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "修改用户", notes = "修改用户", httpMethod = "POST", tags = "修改用户接口")
    @PostMapping(value = "/update")
    @ResponseBody
    public String updateUser(@Validated @RequestBody SYS_USER user) {
        try {
            SYS_USER unitbyname = userService.selectUserByNameNotId(user.getUserAccount(),user.getId());
            if (unitbyname != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_EXIST, null, null).getJson();
            }
            SYS_People people=peopleService.selectPeopleById(user.getPeopleId());
            if (people!=null){
                user.setPeople(people);
                user.setPeopleName(people.getName());
            }
            user.setUserPassword(MD5Utils.encryptPassword(user.getUserPassword()));
            userService.updateUser(user);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, user, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "删除用户", notes = "删除用户", httpMethod = "POST", tags = "删除用户接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteUser(@RequestParam(value = "id",required = true) String id) {
        try {
            userService.deleteUser(id);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, null, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
}
