package com.local.service.impl;

import com.local.entity.sys.SYS_Menu;
import com.local.service.MenuService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private Dao dao;
    @Override
    public List<SYS_Menu> selectAllMenu(){
        // 查询用户关联的所有菜单
        List<SYS_Menu> menus=new ArrayList<>();
        List<SYS_Menu> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("menu_parent_id","0").andEquals("menu_state","0");
        criteria.getOrderBy().asc("MENU_SORT");
        menus=dao.query(SYS_Menu.class,criteria);
        List<SYS_Menu> cmenus=new ArrayList<>();
        Criteria criteria1= Cnd.cri();
        criteria1.where().andNotEquals("menu_parent_id","0").andEquals("menu_state","0");
        criteria1.getOrderBy().asc("MENU_SORT");
        cmenus=dao.query(SYS_Menu.class,criteria1);
        for(SYS_Menu fmenu:menus){
            for (SYS_Menu menu:cmenus ){
                if (menu.getMenuParentId().equals(fmenu.getMenuId())){
                    if (fmenu.getChildren()==null){
                        fmenu.setChildren(new ArrayList<SYS_Menu>());
                    }
                    for (SYS_Menu menu1:cmenus ) {
                        if (menu1.getMenuParentId().equals(menu.getMenuId())) {
                            if (menu.getChildren() == null) {
                                menu.setChildren(new ArrayList<SYS_Menu>());
                            }
                            menu.getChildren().add(menu1);
                        }
                    }
                    fmenu.getChildren().add(menu);
                }
            }
            list.add(fmenu);
        }
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
}
