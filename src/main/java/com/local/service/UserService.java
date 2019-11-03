package com.local.service;


import com.local.entity.sys.SYS_USER;
import com.local.util.Result;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface UserService {

    SYS_USER selectUserByModel(SYS_USER user);
    // 查询用户
    SYS_USER selectUserByName(String loginName);

    // 查询该用户所有角色和菜单信息
    SYS_USER selectRoleMenu(SYS_USER user);

    void updateUser(SYS_USER user);
    void insertUser(SYS_USER user);
    // 查询用户
    QueryResult selectUsersByUnitId(int pageSize, int pageNumber, String unitId, String name, String enabled);
    SYS_USER selectUserByNameNotId(String loginName,String id);

    void deleteUser(String id);

    List<SYS_USER> selectUsersByPeopleId(String pid);

    List<SYS_USER> selectUsersByUnitId(String unitId);

    SYS_USER selectUserById(String id);
}
