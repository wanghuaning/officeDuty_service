package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Duty;
import com.local.entity.sys.Sys_Approal;
import com.local.service.ApprovalService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private Dao dao;
    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增职数使用信息", type = "C")
    public  void insertApproal(Sys_Approal approal){
        dao.insert(approal);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改职数使用信息", type = "U")
    public void updataApproal(Sys_Approal approal){
        dao.update(approal);
    }

    @Override
    public  Sys_Approal selectApproval(String unitId,String flag){
        List<Sys_Approal> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("unit_Id", unitId).andEquals("flag",flag);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(Sys_Approal.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    @Override
    public List<Sys_Approal> selectApprovals(String unitId){
        List<Sys_Approal> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("unit_Id", unitId);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(Sys_Approal.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public Sys_Approal selectApprovalById(String id){
        List<Sys_Approal> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(Sys_Approal.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
