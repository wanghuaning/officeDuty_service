package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Data;
import com.local.entity.sys.SYS_DataInfo;
import com.local.service.DataInfoService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataInfoServiceImpl implements DataInfoService {
    @Autowired
    private Dao dao;

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增备份数据", type = "C")
    public void inserDataInfo(SYS_DataInfo data){
        dao.insert(data);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改备份数据", type = "U")
    public void updateDataInfo(SYS_DataInfo data){
        dao.update(data);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除备份数据", type = "U")
    public void deleteDataInfo(String id){
        dao.delete(SYS_DataInfo.class,id);
    }
    @Override
    public SYS_DataInfo selectDataInfById(String id){
        List<SYS_DataInfo> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("id",id);
        list=dao.query(SYS_DataInfo.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<SYS_DataInfo> selectDataInfosByDataId(String dataId,String type){
        List<SYS_DataInfo> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("data_Id",dataId).andEquals("type",type);
        list=dao.query(SYS_DataInfo.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
}
