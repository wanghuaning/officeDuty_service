package com.local.service.impl;

import com.local.entity.sys.SYS_Log;
import com.local.entity.sys.SYS_People;
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

    @Override
    public QueryResult selectPeoples(int pageSize, int pageNumber, String userId,String name){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<SYS_Log> peopleList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(name)){
            cri.where().andLike("tag","%"+name+"%");
        }
        cri.where().andEquals("op_By",userId);
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
