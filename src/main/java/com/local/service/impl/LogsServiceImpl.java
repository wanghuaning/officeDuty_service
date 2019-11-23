package com.local.service.impl;

import com.local.entity.sys.SYS_Log;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.LogsService;
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
public class LogsServiceImpl implements LogsService {

    @Autowired
    private Dao dao;
    private static List<SYS_UNIT> cunits=new ArrayList<>();
    @Override
    public QueryResult selectLogs(int pageSize, int pageNumber, String username,String name){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<SYS_Log> peopleList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(name)){
            cri.where().andLike("tag","%"+name+"%");
        }
        cri.where().andEquals("op_Name",username);
        cri.getOrderBy().desc("op_Time");
        peopleList = dao.query(SYS_Log.class,cri,pager);
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(SYS_Log.class,cri));
        QueryResult queryResult=new QueryResult(peopleList,pager);
        return queryResult;
    }

    @Override
    public QueryResult selectLogss(int pageSize, int pageNumber, String name,String unitId){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<SYS_Log> peopleList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(name)){//市
            cri.where().orLike("op_name","%"+name+"%");
            cri.where().orLike("op_by","%"+name+"%");
            cri.where().orLike("src","%"+name+"%");
            cri.where().orLike("tag","%"+name+"%");
            cri.where().orLike("type","%"+name+"%");
        }
        cunits=new ArrayList<>();
        List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("id", "=", unitId));
        getAllChildUnits(cunitList);
        if (cunits.size()>0){
            for (SYS_UNIT unit:cunits){
                cri.where().orEquals("op_by",unit.getId());
            }
            cri.getOrderBy().desc("op_Time");
            peopleList = dao.query(SYS_Log.class,cri,pager);
            if (StrUtils.isBlank(pager)){
                pager=new Pager();
            }
            pager.setRecordCount(dao.count(SYS_Log.class,cri));
            QueryResult queryResult=new QueryResult(peopleList,pager);
            return queryResult;
        }else {
            return null;
        }

    }
    public void getAllChildUnits(List<SYS_UNIT> unitList){
        for (SYS_UNIT unit : unitList)  {
//            System.out.println(unit.getName());
            cunits.add(unit);
            if (countUnit(unit.getId()) > 0) {
                List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()));
                if (!StrUtils.isBlank(cunitList) && cunitList.size() > 0) {
                    getAllChildUnits(cunitList);
                }
            }
        }
    }
    public int countUnit(String pid) {
        int num = dao.count(SYS_UNIT.class, Cnd.where("parent_Id", "=", pid));
        return num;
    }
}
