package com.local.service;

import com.local.entity.sys.SYS_Data;
import com.local.entity.sys.SYS_DataInfo;

public interface DataInfoService {

    void inserDataInfo(SYS_DataInfo data);
    void updateDataInfo(SYS_DataInfo data);
    SYS_DataInfo selectDataInfById(String id);
}
