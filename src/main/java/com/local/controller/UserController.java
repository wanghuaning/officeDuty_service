package com.local.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.local.common.redis.util.RedisUtil;
import com.local.entity.sys.SYS_USER;
import com.local.model.ImgResult;
import com.local.service.UserService;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
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

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final static Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @ApiOperation(value = "登录",notes = "登录",httpMethod = "POST",tags = "登录管理接口")
    @PostMapping("/login")
    public String Login(@Validated @RequestBody SYS_USER user){
        Object code=redisUtil.get(user.getUuid());
//        System.out.println(user.getUserAccount()+"=>"+user.getUuid()+"=>"+code+"->"+user.getCode());
        if (StrUtils.isBlank(code)){
            return new Result(ResultCode.ERROR.toString(),ResultMsg.CHECK_EXPIRE,null,null).getJson();
        }
        if (StrUtils.isBlank(user.getUuid()) || !user.getCode().equals(code)){
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
        searchUser=userService.selectUserByPassword(user.getUserAccount());
        //查询菜单
//        searchUser=userService.selectRoleMenu(searchUser);
        //将用户id放入redis
        redisUtil.set(searchUser.getUserId(),searchUser,3600*24);
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
}
