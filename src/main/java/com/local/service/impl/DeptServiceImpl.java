package com.local.service.impl;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.sys.SYS_Menu;
import com.local.service.DeptService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private Dao dao;
    @Override
    public List<ElSysDept> selectAllDepts(){
        Criteria cri= Cnd.cri();
        cri.where().andEquals("PID","0");
        List<ElSysDept> depts=dao.query(ElSysDept.class,cri);
        getDept(depts);
        return depts;
    }
    public List<ElSysDept> selectDeptsByParam(String name,String enabled){
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(name)){//部门名称不为空
            System.out.println("1111111111");
            cri.where().andLike("NAME","%"+name.trim()+"%");
        }else {//如果部门名称为空
            if (enabled.equals("1")){//状态为正常，那么就初始化根节点
                cri.where().andEquals("PID","0");
            }
        }
        if (!StrUtils.isBlank(enabled)){//状态不为空，以当前满足上面条件的所有节点继续往下查找
            cri.where().andEquals("ENABLED",enabled);
            List<ElSysDept> depts=dao.query(ElSysDept.class,cri);
            getDeptByParam(depts,enabled);
            return depts;
        }else {
            cri.where().andEquals("PID","0");
            System.out.println("状态为空");
            List<ElSysDept> depts=dao.query(ElSysDept.class,cri);
            getDept(depts);
            return depts;
        }
    }
    public void getDept(List<ElSysDept> depts){
            for (ElSysDept dept:depts){
                dept.setLabel(dept.getName());
                if ("1".equals(dept.getMenuHasChildren())){
                    List<ElSysDept> cdepts_=dao.query(ElSysDept.class,Cnd.where("PID","=",dept.getId()));
                    List<ElSysDept> cdepts = new ArrayList<>();
                    for(ElSysDept c:cdepts_){
                        c.setLabel(c.getName());
                        cdepts.add(c);
                    }
                    if (!StrUtils.isBlank(cdepts) && cdepts.size()>0){
                        dept.setChildren(cdepts);
                        getDept(cdepts);
                    }
                }
            }
    }
    public void getDeptByParam(List<ElSysDept> depts,String enabled){
        for (ElSysDept dept:depts){
            dept.setLabel(dept.getName());
            if ("1".equals(dept.getMenuHasChildren())){
                Criteria cri= Cnd.cri();
                cri.where().andEquals("PID",dept.getId());
                cri.where().andEquals("ENABLED",enabled);
                List<ElSysDept> cdepts_=dao.query(ElSysDept.class,cri);
                List<ElSysDept> cdepts = new ArrayList<>();
                for(ElSysDept c:cdepts_){
                    c.setLabel(c.getName());
                    cdepts.add(c);
                }
                if (!StrUtils.isBlank(cdepts) && cdepts.size()>0){
                    dept.setChildren(cdepts);
                    getDept(cdepts);
                }
            }
        }
    }
    @Override
    public ElSysDept findById(long id){
        Criteria cri= Cnd.cri();
        cri.where().andEquals("ID",id);
        ElSysDept dept = dao.fetch(ElSysDept.class,cri);
        return dept;
    }
}
