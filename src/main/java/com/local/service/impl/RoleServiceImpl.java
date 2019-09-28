package com.local.service.impl;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.elsys.ElSysMenu;
import com.local.entity.elsys.ElSysPermission;
import com.local.entity.elsys.ElSysRole;
import com.local.service.RoleService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private Dao dao;
    @Override
    public QueryResult selectRoles(int pageSize, int pageNumber){
        // 查询Role
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<ElSysRole> roles=new ArrayList<>();
        Criteria cri= Cnd.cri();
        cri.getOrderBy().asc("LEVEL");
        roles = dao.query(ElSysRole.class,cri,pager);
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(ElSysRole.class,cri));
        QueryResult queryResult=new QueryResult(roles,pager);
        return queryResult;
    }

    @Override
    public QueryResult selectAllRoles(){
        // 查询所有Role
        Pager pager = new Pager();
        List<ElSysRole> roles=new ArrayList<>();
        List<ElSysRole> resultList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        cri.getOrderBy().asc("LEVEL");
        roles = dao.query(ElSysRole.class,cri,pager);
        for (ElSysRole role: roles ){
            dao.fetchLinks(role, "permissions");
            dao.fetchLinks(role, "menus");
            dao.fetchLinks(role, "depts");
            resultList.add(role);
        }
        QueryResult queryResult=new QueryResult(resultList,pager);
        return queryResult;
    }
    //以上两个方法可合并为一个方法，加参数调用

    @Override
    public QueryResult selectRolesById(int id){
        // select Role By ID
        Pager pager = new Pager();
        List<ElSysRole> roles=new ArrayList<>();
        List<ElSysRole> resultList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        cri.where().andEquals("ID",id);
        cri.getOrderBy().asc("LEVEL");
        roles = dao.query(ElSysRole.class,cri,pager);
        for (ElSysRole role: roles ){//此处是多对多查询
            //此处可根据需求查询，如只查询其中一个
//            dao.fetchLinks(role, "permissions");
            dao.fetchLinks(role, "menus");
//            dao.fetchLinks(role, "depts");
            resultList.add(role);
        }
        QueryResult queryResult=new QueryResult(roles,pager);
        return queryResult;
    }
}
