package com.local.service;

import com.local.entity.sys.SYS_Education;
import org.nutz.dao.QueryResult;

import java.util.Date;

public interface EducationService {
    QueryResult selectEducations(int pageSize, int pageNumber, String pid);
    SYS_Education selectEducationById(String id);
    SYS_Education selectEducationByPidOrderByTime(String pid);
    SYS_Education selectEducationByNameAndTime(String name, String peopleId, Date createTime);
    void insertEducation(SYS_Education education);

    void deleteEducation(String id);

    void updateEducation(SYS_Education education);
}
