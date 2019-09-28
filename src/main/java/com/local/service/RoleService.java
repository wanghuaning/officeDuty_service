package com.local.service;

import com.local.entity.elsys.ElSysRole;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface RoleService {

    QueryResult selectAllRoles();
    QueryResult selectRoles(int pageSize, int pageNumber);
    QueryResult selectRolesById(int id);
}
