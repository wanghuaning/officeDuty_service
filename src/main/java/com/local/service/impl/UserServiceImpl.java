package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.REG_User;
import com.local.entity.sys.SYS_App;
import com.local.entity.sys.SYS_Menu;
import com.local.entity.sys.SYS_USER;
import com.local.service.UserService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
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
}
