package com.local.service;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.sys.SYS_UNIT;

import java.util.List;

public interface UnitService {
    List<SYS_UNIT> selectUnitsByParam(String name, String enabled);

    void insertUnit(SYS_UNIT unit);

    void updateUnit(SYS_UNIT unit);

    void deleteUnit(String id);

    SYS_UNIT selectUnitByName(String name);//根据名称查询单位

    SYS_UNIT selectUnitByCode(String code);//根据名称查询单位

    SYS_UNIT selectUnitById(String id);
}
