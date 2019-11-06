package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Assessment;
import com.local.entity.sys.SYS_Duty;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.AssessmentService;
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
public class AssessmentServiceImpl implements AssessmentService {
    @Autowired
    private Dao dao;
    @Override
    public QueryResult selectAssessments(int pageSize, int pageNumber, String pid){
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber + 1);
        pager.setPageSize(pageSize);
        List<SYS_Assessment> peopleList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        if (!StrUtils.isBlank(pid)) {
            cri.where().andEquals("people_Id", pid);
            cri.getOrderBy().desc("year");
            peopleList = dao.query(SYS_Assessment.class, cri, pager);
            if (StrUtils.isBlank(pager)) {
                pager = new Pager();
            }
            pager.setRecordCount(dao.count(SYS_Assessment.class, cri));
        }
        QueryResult queryResult = new QueryResult(peopleList, pager);
        return queryResult;
    }

    @Override
    public SYS_Assessment selectAssessmentByPidOrderByTime(String pid) {
        List<SYS_Assessment> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid);
        cir.getOrderBy().desc("year");
        list = dao.query(SYS_Assessment.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Assessment selectAssessmentByNameAndTime(String name, String peopleId,int year) {
        List<SYS_Assessment> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("name", name).andEquals("people_Id", peopleId).andEquals("year", year);
        list = dao.query(SYS_Assessment.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_Assessment> selectAssessmentsByPeopleId(String peopleId){
        List<SYS_Assessment> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", peopleId);
        list = dao.query(SYS_Assessment.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    @Override
    public List<SYS_Assessment> selectKaoHeByPidAndResult(String pid,String result){
        List<SYS_Assessment> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("name",result);
        list = dao.query(SYS_Assessment.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    @Override
    public SYS_Assessment selectAssessmentById(String id) {
        List<SYS_Assessment> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(SYS_Assessment.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    /**
     * ;//根据单位ID查询，是否包含下级单位的 考核1:包含
     * @param unitId
     * @param isChild
     * @return
     */
    @Override
    public List<SYS_Assessment> selectAssessmentsByUnitId(String unitId, String isChild){
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unit_Id",unitId);
        List<SYS_Assessment> peoples=new ArrayList<>();
        List<SYS_Assessment> list=dao.query(SYS_Assessment.class,cri);
        if ("1".equals(isChild)){//包含下级单位
            Criteria criteria=Cnd.cri();
            criteria.where().andEquals("parent_Id",unitId);
            List<SYS_UNIT> units=dao.query(SYS_UNIT.class,criteria);
            getUnits(units,list);
        }
        if (!StrUtils.isBlank(list) && list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    public void getUnits(List<SYS_UNIT> units, List<SYS_Assessment> peoples){
        if (!StrUtils.isBlank(units) && units.size()>0){
//            List<SYS_People> peopleList=new ArrayList<>();
            for (SYS_UNIT unit:units){
                Criteria cri = Cnd.cri();
                cri.where().andEquals("unit_Id",unit.getId());
                List<SYS_Assessment> list=dao.query(SYS_Assessment.class,cri);
                if (!StrUtils.isBlank(list) && list.size()>0){
                    peoples.addAll(list);
                }
                List<SYS_UNIT> cunits=dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()));
                if (!StrUtils.isBlank(cunits) && cunits.size() > 0) {
                    getUnits(cunits,peoples);
                }
            }
        }
    }
    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增考核", type = "C")
    public void insertAssessment(SYS_Assessment duty) {
        dao.insert(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改考核", type = "U")
    public void updateAssessment(SYS_Assessment duty) {
        dao.update(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除考核", type = "D")
    public void deleteAssessment(String id) {
        dao.delete(SYS_Assessment.class, id);
    }

}
