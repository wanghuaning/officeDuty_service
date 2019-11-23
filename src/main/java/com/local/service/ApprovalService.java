package com.local.service;

import com.local.entity.sys.Sys_Approal;

public interface ApprovalService {
    /**
     *职数使用信息
     */
    void insertApproal(Sys_Approal approal);

    void updataApproal(Sys_Approal approal);

    Sys_Approal selectApproval(String unitId,String flag);
}
