package com.local.service;

import com.local.entity.sys.SYS_Data;

public interface DataService {
    void inserData(SYS_Data data);
    void updateData(SYS_Data data);
    SYS_Data selectDataById(String id);
}
