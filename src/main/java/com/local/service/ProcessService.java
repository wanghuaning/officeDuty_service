package com.local.service;

import com.local.entity.sys.Sys_Process;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface ProcessService {
    void insertProcess(Sys_Process process);
    void updateProcess(Sys_Process process);
    Sys_Process selectProcessById(String id);
    Sys_Process selectProcessByFlag(String unitId,String flag);
    List<Sys_Process> selectNotApprProcess(String unitId);
    List<Sys_Process> selectApprProcess(String unitId);
    QueryResult selectProcesss(int pageSize, int pageNumber, String unitId, String unitName, String approveFlag);
}