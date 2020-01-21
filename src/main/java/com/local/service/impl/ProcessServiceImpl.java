package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Data;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.Sys_Process;
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
    public List<Sys_Process> selectApprProcess(String unitId){
        List<Sys_Process> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId).andEquals("states","已审批");
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
    private static List<String> cunits=new ArrayList<>();

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
                    cri.where().orEquals("states",states);
                }else {
                    cri.where().andNotEquals("states","已审核");
                }
            }
            cri.getOrderBy().desc("processTime");
            List<Sys_Process> processes=new ArrayList<>();
            peopleList = dao.query(Sys_Process.class,cri,pager);
            if (peopleList.size()>0){
                for (Sys_Process process:peopleList){
                    List<Sys_Process> cprocessList = dao.query(Sys_Process.class, Cnd.where("parent_Id", "=", process.getId()));
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
