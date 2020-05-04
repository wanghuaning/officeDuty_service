package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.*;
import com.local.service.ProcessService;
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
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private Dao dao;
    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增审批表", type = "C")
    public void insertProcess(Sys_Process process){
        dao.insert(process);
    }
    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改审批表", type = "U")
    public void updateProcess(Sys_Process process){
        dao.update(process);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除审批表", type = "D")
    public void deleteProcess(String id){
        dao.delete(Sys_Process.class, id);
    }
    @Override
    public List<Sys_Process> selectApprProcess(String unitId){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andNotEquals("states","未审批");
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    @Override
    public List<Sys_Process> selectProcessByUnitId(String unitId){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    public List<Sys_Process> selectProcesssByUnitIds(String[] unitIds){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andInStrArray("unit_Id",unitIds).andEquals("parent_Id",null);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    @Override
    public List<Sys_Process> selectProcesssByUnitIds(List<String> unitIds){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andInStrList("unit_Id",unitIds);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    public List<Sys_Process> selectProcesssByUnitIdsAndFlag(String[] unitIds, String states){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andInStrArray("unit_Id",unitIds).andEquals("parent_Id",null).andEquals("states",states);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    public List<Sys_Process> selectProcesssByUnitIdsAndZuZhiBu(String[] unitIds,String zuzhiUnitId){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andInStrArray("unit_Id",unitIds).andEquals("approval_Unit",zuzhiUnitId);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    @Override
    public List<Sys_Process> selectShangProcesssByUnitIds(String[] unitIds){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andInStrArray("approval_Unit",unitIds).andEquals("approvaled","0").andNotEquals("parent_Id",null);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    public List<Sys_Process> selectNotApprProcess(String unitId){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andNotEquals("states","已审核").andEquals("parent_Id",null);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    @Override
    public List<Sys_Process> selectApprProcessByStates(String[] states){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andInStrArray("states",states).andEquals("parent_Id",null);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    @Override
    public List<Sys_Process> selectNotApprProcessByFlag(String unitId,String flag){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andNotEquals("states","已审核").andEquals("parent_Id",null).andEquals("flag",flag);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    public List<Sys_Process> selectProcesssByParentId(String parentId){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("parent_Id",parentId);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    public Sys_Process selectProcessById(String id){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("id",id);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    @Override
    public Sys_Process selectProcessByFlag(String unitId,String flag){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andEquals("flag",flag).andEquals("states","未审批");
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    @Override
    public Sys_Process selectProcessByOrder(String unitId,String orderStr){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andEquals("approval_Order",orderStr).andEquals("states","未审批");
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    @Override
    public Sys_Process selectProcessByFlagAndDate(String unitId, String flag, Date startDate){
        Criteria cir= Cnd.cri();
        List<Sys_Process> nowDuty=dao.query(Sys_Process.class, Cnd.where("unit_Id", "=", unitId).and("parent_Id","is",null).and("create_Time",">",startDate).and("flag","=",flag));
        if (nowDuty.size()>0){
            return nowDuty.get(0);
        }else {
            return null;
        }
    }
    @Override
    public List<Sys_Process> selectProcesssByFlagAndDate(String unitId, String flag, Date startDate){
        Criteria cir= Cnd.cri();
        List<Sys_Process> nowDuty=dao.query(Sys_Process.class, Cnd.where("unit_Id", "=", unitId).and("parent_Id","is",null).and("create_Time",">",startDate).and("flag","=",flag));
        if (nowDuty.size()>0){
            return nowDuty;
        }else {
            return null;
        }
    }
    @Override
    public Sys_Process selectProcessByFlagAnd(String unitId,String flag,String states){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andEquals("flag",flag).andEquals("states",states);
        cir.getOrderBy().desc("create_Time");
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Sys_Process selectProProcessByFlag(String unitId,String flag){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andEquals("flag",flag).andEquals("parent_Id",null).andNotEquals("states","未审批").andNotEquals("states","已驳回");
        cir.getOrderBy().desc("create_Time");
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    @Override
    public Sys_Process selectProcessByParentId(String parentId,String states){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("parent_Id",parentId).andEquals("states",states);
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Sys_Process selectProcessByParentIdAndUnitName(String parentId,String unitName){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("parent_Id",parentId).andLike("people","%"+unitName+"%");
        list=dao.query(Sys_Process.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    private static List<String> cunits=new ArrayList<>();

    @Override
    public QueryResult selectNotEndProcesss(int pageSize, int pageNumber, String unitId, String unitName,String flag){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<Sys_Process> peopleList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        cunits=new ArrayList<>();
        List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("id", "=", unitId));
        getAllChildUnits(cunitList);
        if (cunits.size()>0){
            cri.where().andInStrList("unit_Id",cunits).andEquals("parent_Id",null);
            if (!StrUtils.isBlank(unitName)){//市
                cri.where().andLike("unit_Name","%"+unitName+"%");
            }
            if (!StrUtils.isBlank(flag) && !"all".equals(flag)){
                cri.where().andEquals("flag",flag);
            }
            cri.where().andNotEquals("states","已审核");
            cri.where().andNotEquals("states","已驳回");
            cri.getOrderBy().desc("processTime");
            List<Sys_Process> processes=new ArrayList<>();
            peopleList = dao.query(Sys_Process.class,cri,pager);
            if (peopleList.size()>0){
                for (Sys_Process process:peopleList){
                    List<Sys_Process> cprocessList = dao.query(Sys_Process.class, Cnd.where("parent_Id", "=", process.getId()).and("approvaled", "=", "0").desc("approval_Order"));
                    if (cprocessList.size()>0){
                        process.setChildren(cprocessList);
                    }else {
                        process.setChildren(new ArrayList<>());
                    }
                    processes.add(process);
                }
            }
            if (StrUtils.isBlank(pager)){
                pager=new Pager();
            }
            pager.setRecordCount(dao.count(Sys_Process.class,cri));
            QueryResult queryResult=new QueryResult(processes,pager);
            return queryResult;
        }else {
            return null;
        }
    }
    @Override
    public List<Sys_Process> selectRankProcessAndNotUser( String unitId){
        List<Sys_Process> processList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unit_Id", unitId).andEquals("flag","1").andEquals("used","1");
        cri.getOrderBy().desc("processTime");
        processList = dao.query(Sys_Process.class, cri);
        if (processList.size()>0){
            return processList;
        }else {
            return null;
        }
    }
    @Override
    public QueryResult selectProcesss(int pageSize, int pageNumber, String unitId, String unitName, String approveFlag, String states){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<Sys_Process> peopleList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        cunits=new ArrayList<>();
        List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("id", "=", unitId));
        getAllChildUnits(cunitList);
        if (cunits.size()>0){
            cri.where().andInStrList("unit_Id",cunits).andEquals("parent_Id",null);
            if (!StrUtils.isBlank(unitName)){//市
                cri.where().andLike("unit_Name","%"+unitName+"%");
            }
            if (!StrUtils.isBlank(approveFlag) && !"all".equals(approveFlag)){//市
                cri.where().andEquals("flag",approveFlag);
            }
            if (!StrUtils.isBlank(states) && !"all".equals(states)){//市
                if ("已审核".equals(states)){
                    cri.where().andNotEquals("states","未审批").andNotEquals("states","已驳回");
                }else {
                    cri.where().andEquals("states",states);
                }
            }
            cri.getOrderBy().desc("processTime");
            List<Sys_Process> processes=new ArrayList<>();
            peopleList = dao.query(Sys_Process.class,cri,pager);
            if (peopleList.size()>0){
                for (Sys_Process process:peopleList){
                    List<Sys_Process> cprocessList = dao.query(Sys_Process.class, Cnd.where("parent_Id", "=", process.getId()).desc("approval_Order"));
                    if (cprocessList.size()>0){
                        process.setChildren(cprocessList);
                    }else {
                        process.setChildren(new ArrayList<>());
                    }
                    processes.add(process);
                }
            }
            if (StrUtils.isBlank(pager)){
                pager=new Pager();
            }
            pager.setRecordCount(dao.count(Sys_Process.class,cri));
            QueryResult queryResult=new QueryResult(processes,pager);
            return queryResult;
        }else {
            return null;
        }
    }
    public void getAllChildUnits(List<SYS_UNIT> unitList){
        for (SYS_UNIT unit : unitList)  {
//            System.out.println(unit.getName());
            cunits.add(unit.getId());
            if (countUnit(unit.getId()) > 0) {
                List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()));
                if (!StrUtils.isBlank(cunitList) && cunitList.size() > 0) {
                    getAllChildUnits(cunitList);
                }
            }
        }
    }
    public int countUnit(String pid) {
        int num = dao.count(SYS_UNIT.class, Cnd.where("parent_Id", "=", pid));
        return num;
    }
}
