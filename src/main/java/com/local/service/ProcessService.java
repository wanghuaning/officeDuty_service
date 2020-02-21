package com.local.service;

import com.local.entity.sys.Sys_Process;
import org.nutz.dao.QueryResult;

import java.util.Date;
import java.util.List;

public interface ProcessService {
    void insertProcess(Sys_Process process);
    void updateProcess(Sys_Process process);
    void deleteProcess(String id);
    Sys_Process selectProcessById(String id);
    Sys_Process selectProcessByFlag(String unitId,String flag);
    Sys_Process selectProcessByFlagAndDate(String unitId, String flag, Date startDate);
    Sys_Process selectProcessByFlagAnd(String unitId,String flag,String states);
    Sys_Process selectProProcessByFlag(String unitId,String flag);//初审和审批完成
    Sys_Process selectProcessByParentId(String parentId,String states);
    Sys_Process selectProcessByParentIdAndUnitName(String parentId,String unitName);
    List<Sys_Process> selectProcesssByParentId(String parentId);
    List<Sys_Process> selectNotApprProcess(String unitId);
    List<Sys_Process> selectNotApprProcessByFlag(String unitId,String flag);
    List<Sys_Process> selectApprProcess(String unitId);
    QueryResult selectProcesss(int pageSize, int pageNumber, String unitId, String unitName, String approveFlag,String states);
    QueryResult selectNotEndProcesss(int pageSize, int pageNumber, String unitId, String unitName, String approveFlag);
}
