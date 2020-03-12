package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Education;
import com.local.entity.sys.SYS_Education;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_UNIT;
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
            cri.getOrderBy().asc("education_Order");
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
        cir.getOrderBy().asc("education_Order");
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Education selectEducationByPidAndSchoolOrderByTime(String pid,String schoolType){
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("school_Type",schoolType);
        cir.getOrderBy().asc("education_Order");
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
    public SYS_Education selectEducationByName(String name, String peopleId) {
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("name", name).andEquals("people_Id", peopleId);
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    @Override
    public List<SYS_Education> selectEducationsByPeopleId(String pid){
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid);
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list;
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
    public List<SYS_Education> selectEducationsByUnitId(String unitId){
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("unit_Id", unitId);
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_Education> selectEducationsByUnitIds(List<String> unitIds){
        List<SYS_Education> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andInStrList("unit_Id", unitIds);
        list = dao.query(SYS_Education.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    /**
     * ;//根据单位ID查询，是否包含下级单位的 学历1:包含
     * @param unitId
     * @param isChild
     * @return
     */
    @Override
    public List<SYS_Education> selectEducationsByUnitId(String unitId, String isChild){
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unit_Id",unitId);
        List<SYS_Education> peoples=new ArrayList<>();
        List<SYS_Education> list=dao.query(SYS_Education.class,cri);
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
    public void getUnits(List<SYS_UNIT> units, List<SYS_Education> peoples){
        if (!StrUtils.isBlank(units) && units.size()>0){
//            List<SYS_People> peopleList=new ArrayList<>();
            for (SYS_UNIT unit:units){
                Criteria cri = Cnd.cri();
                cri.where().andEquals("unit_Id",unit.getId());
                List<SYS_Education> list=dao.query(SYS_Education.class,cri);
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
    @SLog(tag = "新增学历", type = "C")
    public void insertEducation(SYS_Education duty) {
        dao.insert(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改学历", type = "U")
    public void updateEducation(SYS_Education duty) {
        dao.update(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除学历", type = "D")
    public void deleteEducation(String id) {
        dao.delete(SYS_Education.class, id);
    }

}
