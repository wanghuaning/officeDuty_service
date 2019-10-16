package com.local.service;

import com.local.entity.sys.SYS_Duty;
import org.nutz.dao.QueryResult;

import java.util.Date;
import java.util.List;

public interface DutyService {
    QueryResult selectDutys(int pageSize, int pageNumber,String pid);

    SYS_Duty selectDutyByNameAndTime(String name, String peopleId, Date createTime);
    void insertDuty(SYS_Duty duty);
}
