package com.local.cell;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.SYS_USER;
import com.local.service.PeopleService;
import com.local.service.UnitService;
import com.local.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserManager {
    public static SYS_USER getUserToken(HttpServletRequest request, UserService userService, UnitService unitService, PeopleService peopleService){
        //从请求的header中取出当前登录的登录
        String token = request.getHeader("userToken");
        if (token == null || "".equals(token)) {
            //从请求的url中取出当前登录的登录
            token = request.getParameter("userToken");
        }
        //通过token从redis中取出当前登录
        SYS_USER user = userService.selectUserById(token);
        if (user != null) {
            SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
            if (unit != null) {
                user.setUnit(unit);
            }
            SYS_People people = peopleService.selectPeopleById(user.getPeopleId());
            if (people != null) {
                user.setPeople(people);
            }
        }
        return user;
    }
}
