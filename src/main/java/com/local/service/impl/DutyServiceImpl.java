package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Duty;
import com.local.entity.sys.SYS_People;
import com.local.service.DutyService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DutyServiceImpl implements DutyService {
    @Autowired
    private Dao dao;

    @Override
    public QueryResult selectDutys(int pageSize, int pageNumber, String pid) {
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber + 1);
        pager.setPageSize(pageSize);
        List<SYS_Duty> peopleList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        if (!StrUtils.isBlank(pid)) {
            cri.where().andEquals("people_Id", pid);
            cri.getOrderBy().desc("create_Time");
            peopleList = dao.query(SYS_Duty.class, cri, pager);
            if (StrUtils.isBlank(pager)) {
                pager = new Pager();
            }
            pager.setRecordCount(dao.count(SYS_Duty.class, cri));
        }
        QueryResult queryResult = new QueryResult(peopleList, pager);
        return queryResult;
    }

    @Override
    public SYS_Duty selectDutyByPidOrderByTime(String pid) {
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("status","在任");
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Duty selectDutyByNameAndTime(String name, String peopleId, Date createTime) {
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("name", name).andEquals("people_Id", peopleId).andEquals("create_Time", createTime);
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_Duty> selectDutysByPeopleId(String pid){
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid);
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    @Override
    public SYS_Duty selectDutyById(String id) {
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增职务", type = "C")
    public void insertDuty(SYS_Duty duty) {
        dao.insert(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除职务", type = "D")
    public void deleteDuty(String id) {
        dao.delete(SYS_Duty.class, id);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改职务", type = "U")
    public void updateDuty(SYS_Duty duty) {
        dao.update(duty);
    }
}
