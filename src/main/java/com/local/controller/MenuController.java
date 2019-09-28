package com.local.controller;

import com.local.entity.sys.SYS_Menu;
import com.local.service.MenuService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private static final Logger logger= LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;
    @GetMapping(value = "/build")
    public String buildMenus(){
        List<SYS_Menu> menus =menuService.selectAllMenu();
//        System.out.println("菜单");
        if (menus!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,menus,null).getJson();
        }else {
            logger.error("菜单查询失败！");
            return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
        }
    }
}