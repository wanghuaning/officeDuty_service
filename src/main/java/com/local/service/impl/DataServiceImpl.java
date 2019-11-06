package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Data;
import com.local.entity.sys.SYS_People;
import com.local.service.DataService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private Dao dao;

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增备份数据", type = "C")
    public void inserData(SYS_Data data){
        dao.insert(data);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改备份数据", type = "U")
    public void updateData(SYS_Data data){
        dao.update(data);
    }

    @Override
    public SYS_Data selectDataById(String id){
        List<SYS_Data> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("id",id);
        list=dao.query(SYS_Data.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

}