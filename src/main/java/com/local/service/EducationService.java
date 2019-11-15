package com.local.service;

import com.local.entity.sys.SYS_Education;
import com.local.entity.sys.SYS_Reward;
import org.nutz.dao.QueryResult;

import java.util.Date;
import java.util.List;

public interface EducationService {
    QueryResult selectEducations(int pageSize, int pageNumber, String pid);
    SYS_Education selectEducationById(String id);
    SYS_Education selectEducationByPidOrderByTime(String pid);
    SYS_Education selectEducationByNameAndTime(String name, String peopleId, Date createTime);
    SYS_Education selectEducationByName(String name, String peopleId);
    List<SYS_Education> selectEducationsByPeopleId(String pid);
    void insertEducation(SYS_Education education);

    void deleteEducation(String id);

    void updateEducation(SYS_Education education);

    SYS_Education selectEducationByPidAndSchoolOrderByTime(String pid,String schoolType);

    List<SYS_Education> selectEducationsByUnitId(String unitId, String isChild);//根据单位ID查询，是否包含下级单位的 人员1:包含
}
