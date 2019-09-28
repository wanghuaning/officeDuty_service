package com.local.service;

import com.local.entity.REG_RegionCode;

import java.util.List;

public interface CodeService {

    List<REG_RegionCode> selectAreaCodeByUpCode(String upCode);

}
