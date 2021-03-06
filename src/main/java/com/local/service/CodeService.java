package com.local.service;

import com.local.entity.sys.SYS_AREA;
import com.local.entity.sys.SYS_CODE;

import java.util.List;

public interface CodeService {

    List<SYS_AREA> selectAreaCodeByUpCode(String upCode);
    SYS_AREA selectAreaByCode(String code);
    //机构级别
    List<SYS_CODE> selectLevels();

    //所属序列
    List<SYS_CODE> selectCategorys();

    //单位隶属关系
    List<SYS_CODE> selectAffiliations();

    SYS_CODE selectCodeById(String id);

    //民族
    List<SYS_CODE> selectNationality();

    //政治面貌
    List<SYS_CODE> selectParty();

    //职务层次
    List<SYS_CODE> selectPosition();

    //现职级
    List<SYS_CODE> selectPositionLevel();
    //编制类型
    List<SYS_CODE> selectPoliticalStatus();

    List<SYS_CODE> selectCodesByPid(String pid);

    SYS_CODE selectCodeByName(String name,String pcode);

    Object buildTree(List<SYS_CODE> unitList);
}
