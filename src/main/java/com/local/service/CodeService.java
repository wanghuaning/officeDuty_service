package com.local.service;

import com.local.entity.sys.SYS_AREA;

import java.util.List;

public interface CodeService {

    List<SYS_AREA> selectAreaCodeByUpCode(String upCode);

}
