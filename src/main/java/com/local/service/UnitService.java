package com.local.service;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.sys.SYS_UNIT;

import java.util.List;

public interface UnitService {
    List<SYS_UNIT> selectUnitsByParam(String name, String enabled);

}
