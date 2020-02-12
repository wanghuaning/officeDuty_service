package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Duty;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_UNIT;
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
        cir.where().andEquals("people_Id", pid);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    @Override
    public SYS_Duty selectNowDutyByPidOrderByTime(String pid){
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andNotEquals("approval_Time",null).andEquals("serve_Approval_Time",null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    @Override
    public SYS_Duty selectProDutyByPidOrderByTime(String pid){
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andNotEquals("approval_Time",null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    @Override
    public SYS_Duty selectNotProDutyByPidOrderByTime(String pid){
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("approval_Time",null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    @Override
    public SYS_Duty selectNotEnableDutyByPidOrderByTime(String pid){
        List<SYS_Duty> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("status", "已免").andEquals("serve_Approval_Time", null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Duty.class, cir);
        if (list.size() > 0) {
            List<SYS_Duty> nowDuty=dao.query(SYS_Duty.class, Cnd.where("people_Id", "=", pid).and("create_Time",">",list.get(0).getCreateTime()).andNot("approval_Time","=",null));
            if (nowDuty.size()>0){
                return null;
            }else {
                return list.get(0);
            }
        } else {
            return null;
        }
    }
    @Override
    public SYS_Duty selectEnableDutyByPidOrderByTime(String pid){
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

    /**
     * ;//根据单位ID查询，是否包含下级单位的 职务1:包含
     * @param unitId
     * @param isChild
     * @return
     */
    @Override
    public List<SYS_Duty> selectDutysByUnitId(String unitId, String isChild){
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unit_Id",unitId);
        List<SYS_Duty> peoples=new ArrayList<>();
        List<SYS_Duty> list=dao.query(SYS_Duty.class,cri);
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
    public void getUnits(List<SYS_UNIT> units, List<SYS_Duty> peoples){
        if (!StrUtils.isBlank(units) && units.size()>0){
//            List<SYS_People> peopleList=new ArrayList<>();
            for (SYS_UNIT unit:units){
                Criteria cri = Cnd.cri();
                cri.where().andEquals("unit_Id",unit.getId());
                List<SYS_Duty> list=dao.query(SYS_Duty.class,cri);
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
