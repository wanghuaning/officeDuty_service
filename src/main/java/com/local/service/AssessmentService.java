package com.local.service;

import com.local.entity.sys.SYS_Assessment;
import com.local.entity.sys.SYS_Duty;
import org.nutz.dao.QueryResult;

import java.util.Date;
import java.util.List;

public interface AssessmentService {
    QueryResult selectAssessments(int pageSize, int pageNumber, String pid);
    SYS_Assessment selectAssessmentById(String id);
    SYS_Assessment selectAssessmentByPidOrderByTime(String pid);
    SYS_Assessment selectAssessmentByYear(String peopleId, int year);
    SYS_Assessment selectAssessmentByNameAndTime(String name, String peopleId, int year);
    List<SYS_Assessment> selectAssessmentsByPeopleId(String peopleId);
    void insertAssessment(SYS_Assessment assessment);

    void deleteAssessment(String id);

    void updateAssessment(SYS_Assessment assessment);

    List<SYS_Assessment> selectKaoHeByPidAndResult(String pid,String result);
    List<SYS_Assessment> selectAssessmentsByUnitId(String unitId, String isChild);//根据单位ID查询，是否包含下级单位的 人员1:包含
}
