package com.local.service;

import com.local.entity.sys.SYS_DataInfo;
import com.local.entity.sys.Sys_Approal;

import java.util.List;

public interface DataInfoService {

    void inserDataInfo(SYS_DataInfo data);
    void updateDataInfo(SYS_DataInfo data);
    SYS_DataInfo selectDataInfById(String id);

    List<SYS_DataInfo> selectDataInfosByDataId(String dataId,String type);

}
