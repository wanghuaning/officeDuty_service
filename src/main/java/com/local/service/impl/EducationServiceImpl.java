package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Education;
import com.local.entity.sys.SYS_Rank;
import com.local.service.EducationService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    @Autowired
    private Dao dao;
    @Override
    public QueryResult selectEducations(int pageSize, int pageNumber, String pid){
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber + 1);
        pager.setPageSize(pageSize);
        List<SYS_Education> peopleList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        if (!StrUtils.isBlank(pid)) {
            cri.where().andEquals("people_Id", pid);
            cri.getOrderBy().desc("create_Time");
            peopleList = dao.query(SYS_Education.class, cri, pager);
            if (StrUtils.isBlank(pager)) {
                pager = new Pager();
            }
            pager.setRecordCount(dao.count(SYS_Education.class, cri));
        }
        QueryResult queryResult = new QueryResult(peopleList, pager);
        return queryResult;
    }

    @Override
    public SYS_Education selectEducationByPidOrderByTime(String pid) {
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Education selectEducationByNameAndTime(String name, String peopleId, Date createTime) {
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("name", name).andEquals("people_Id", peopleId).andEquals("create_Time", createTime);
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Education selectEducationById(String id) {
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增职级", type = "C")
    public void insertEducation(SYS_Education duty) {
        dao.insert(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改职级", type = "U")
    public void updateEducation(SYS_Education duty) {
        dao.update(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除职级", type = "D")
    public void deleteEducation(String id) {
        dao.delete(SYS_Education.class, id);
    }

}
