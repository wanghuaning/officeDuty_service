package com.local.service;

import com.local.entity.sys.Sys_Process;
import org.nutz.dao.QueryResult;

public interface ProcessService {
    void insertProcess(Sys_Process process);
    void updateProcess(Sys_Process process);
    Sys_Process selectProcessByFlag(String flag);
    QueryResult selectProcesss(int pageSize, int pageNumber, String unitId, String unitName, String approveFlag);
}
