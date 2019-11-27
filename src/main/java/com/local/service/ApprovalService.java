package com.local.service;

import com.local.entity.sys.Sys_Approal;

import java.util.List;

public interface ApprovalService {
    /**
     *职数使用信息
     */
    void insertApproal(Sys_Approal approal);

    void updataApproal(Sys_Approal approal);

    Sys_Approal selectApproval(String unitId,String flag);//1：已审批 0：未审批

    Sys_Approal selectApprovalById(String id);

    List<Sys_Approal> selectApprovals(String unitId);
}
