package com.local.service;

import com.local.entity.sys.SYS_Data;
import com.local.entity.sys.SYS_Digest;
import com.local.entity.sys.Sys_Process;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface DataService {
    void inserData(SYS_Data data);
    void updateData(SYS_Data data);
    void deleteData(String id);
    SYS_Data selectDataById(String id);
    SYS_Data selectDataByProcessId(String processId);
    List<SYS_Data> selectDataByUnitId(String unitId);

    List<SYS_Digest> selectDigestsByUnitId(String unitId);
    SYS_Digest selectDigestById(String id);
    void insertDigest(SYS_Digest digest);
    void updateDigest(SYS_Digest digest);

    SYS_Data saveApprovalData(Sys_Process process, String approvalType, String unitId);
}
