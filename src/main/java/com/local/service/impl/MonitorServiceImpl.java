package com.local.service.impl;

import com.local.entity.sys.SYS_Log;
import com.local.entity.sys.SYS_People;
import com.local.service.MonitorService;
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
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    private Dao dao;
    @Override
    public QueryResult selectLogss(int pageSize, int pageNumber, String name){
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
        cri.getOrderBy().desc("op_Time");
        peopleList = dao.query(SYS_Log.class,cri,pager);
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(SYS_Log.class,cri));
        QueryResult queryResult=new QueryResult(peopleList,pager);
        return queryResult;
    }
}
