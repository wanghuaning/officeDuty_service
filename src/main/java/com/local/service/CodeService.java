package com.local.service;

import com.local.entity.sys.SYS_AREA;
import com.local.entity.sys.SYS_CODE;

import java.util.List;

public interface CodeService {

    List<SYS_AREA> selectAreaCodeByUpCode(String upCode);
    SYS_AREA selectAreaByCode(String code);
    //机构级别
    List<SYS_CODE> selectLevels();

    //机构类别
    List<SYS_CODE> selectCategorys();

    //单位隶属关系
    List<SYS_CODE> selectAffiliations();

    SYS_CODE selectCodeById(String id);
}
