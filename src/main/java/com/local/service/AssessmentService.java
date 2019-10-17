package com.local.service;

import com.local.entity.sys.SYS_Assessment;
import org.nutz.dao.QueryResult;

import java.util.Date;

public interface AssessmentService {
    QueryResult selectAssessments(int pageSize, int pageNumber, String pid);
    SYS_Assessment selectAssessmentById(String id);
    SYS_Assessment selectAssessmentByPidOrderByTime(String pid);
    SYS_Assessment selectAssessmentByNameAndTime(String name, String peopleId, int year);
    void insertAssessment(SYS_Assessment assessment);

    void deleteAssessment(String id);

    void updateAssessment(SYS_Assessment assessment);
}
