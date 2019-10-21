package com.local.service;


import com.local.entity.sys.SYS_USER;

public interface UserService {

    SYS_USER selectUserByModel(SYS_USER user);
    // 查询用户
    SYS_USER selectUserByPassword(String loginName);

    // 查询该用户所有角色和菜单信息
    SYS_USER selectRoleMenu(SYS_USER user);

    void updateUser(SYS_USER user);


}
