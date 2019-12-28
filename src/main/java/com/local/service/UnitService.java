package com.local.service;

import com.local.entity.sys.SYS_UNIT;

import java.util.List;

public interface UnitService {
    List<SYS_UNIT> selectUnitsByParam(String name, String enabled,String parentId);

    void insertUnit(SYS_UNIT unit);

    void updateUnit(SYS_UNIT unit);

    void deleteUnit(String id);

    SYS_UNIT selectUnitByName(String name);//根据名称查询单位

    SYS_UNIT selectUnitByCode(String code);//根据名称查询单位

    SYS_UNIT selectUnitById(String id);

    List<SYS_UNIT> selectUnitAll();

    SYS_UNIT selectUnitByNameAndParent(String name,String pname);

    List<SYS_UNIT> selectAllChildUnits(String parentId);

    List<SYS_UNIT> selectAllParentUnits(SYS_UNIT unit);

    String  selectUnitAndChildUnits(String parentId);

    SYS_UNIT selectLikeUnitByName(String name);//根据名称模糊查询单位
}
