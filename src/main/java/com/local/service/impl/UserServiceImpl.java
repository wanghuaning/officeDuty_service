package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.REG_User;
import com.local.entity.sys.*;
import com.local.service.UserService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Dao dao;
    @Override
    public  SYS_USER selectUserByModel(SYS_USER user){
        SqlExpressionGroup e1=Cnd.exps("user_account","=",user.getUserAccount());
        SqlExpressionGroup e2=Cnd.exps("user_password","=",user.getUserPassword());
        Condition condition=Cnd.where(e1).and(e2);
        List<SYS_USER> userList=dao.query(SYS_USER.class,condition);
        if (userList.size()>0){
            return userList.get(0);
        }else {
            return null;
        }
    }
    @Override
    public SYS_USER selectUserByPassword(String loginName){
        List<SYS_USER> list = new ArrayList<>();
        Criteria criteria = Cnd.cri();
        criteria.where().andEquals("user_account", loginName);
        list = dao.query(SYS_USER.class, criteria);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询该用户所有角色和菜单信息
     * @param user
     * @return
     */
    @Override
    @SLog(tag = "查询角色和菜单")
    public SYS_USER selectRoleMenu(SYS_USER user){
        // 查询用户关联的所有菜单
        List<SYS_Menu> menus=new ArrayList<>();
        Criteria criteria=Cnd.cri();
        criteria.getOrderBy().desc("MENU_SORT");
        menus=dao.query(SYS_Menu.class,criteria);
        if (menus.size()>0){
//            user.setMenus(menus);
            return user;
        }else {
//            user.setMenus(new ArrayList<>());
            return user;
        }
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改用户", type = "U")
    public void updateUser(SYS_USER user){
        dao.update(user);
    }

    private static List<String> cunits=new ArrayList<>();
    @Override
    public QueryResult selectUsersByUnitId(int pageSize, int pageNumber,String unitId, String name, String enabled){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<SYS_USER> userList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(name)){//市
            cri.where().andLike("user_account","%"+name+"%");
        }
        if (!StrUtils.isBlank(enabled)){
            if ("enabled".equals(enabled)){
                cri.where().andEquals("enabled","0");
            }else if ("notEnabled".equals(enabled)){
                cri.where().andEquals("enabled","1");
            }
        }
        cri.where().andEquals("unit_Id",unitId);
        userList = dao.query(SYS_USER.class,cri,pager);
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(SYS_USER.class,cri));
        QueryResult queryResult=new QueryResult(userList,pager);
        return queryResult;
    }
}
