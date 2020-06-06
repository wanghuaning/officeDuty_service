package com.local.service;

import com.local.entity.sys.SYS_Duty;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Rank;
import org.nutz.dao.QueryResult;

import java.util.Date;
import java.util.List;

public interface DutyService {
    QueryResult selectDutys(int pageSize, int pageNumber,String pid);
    SYS_Duty selectDutyById(String id);
    SYS_Duty selectNowDutyByPidOrderByTime(String pid);
    SYS_Duty selectDutyByPidOrderByTime(String pid);
    SYS_Duty selectProDutyByPidOrderByTime(String pid);
    SYS_Duty selectEnableDutyByPidOrderByTime(String pid);
    SYS_Duty selectNotEnableDutyByPidOrderByTime(String pid);
    SYS_Duty selectNotProDutyByPidOrderByTime(String pid);
    SYS_Duty selectNotProDutyByPidAndTimeInterval(String pid,Date createTime,Date endTime);
    SYS_Duty selectDutyByNameAndTime(String name, String peopleId, Date createTime);
    SYS_Duty selectBeforeDutyByPidAndTime(String peopleId, Date createTime);
    SYS_Duty selectDutyByNameAndMoreTime(String name, String peopleId, Date createTime);
    void insertDuty(SYS_Duty duty);
    void updateDuty(SYS_Duty duty);
    void deleteDuty(String id);
    List<SYS_Duty> selectDutysByPeopleId(String pid);

    List<SYS_Duty> selectDutysByUnitId(String unitId, String isChild);//根据单位ID查询，是否包含下级单位的 人员1:包含
    List<SYS_Duty> selectDutysByUnitId(String unitId);
    List<SYS_Duty> selectDutysByUnitIds(List<String> unitIds);

    List<SYS_Duty> selectDutysByPids(Date startTime, Date endTime, String pids, String status, String rankInArr, String types);
    List<SYS_Duty> selectBeforeDutysByPids(String pids,String status,String rankInArr,String issm);

    List<SYS_Duty> selectDutysByrankOrder(String rankOrder,String status);
    SYS_Duty selectDutysByPidAndStatus(String pid,String status);
    SYS_Duty selectDutysByPidAndStatusOtherId(String pid,String status,String id);
    SYS_Duty selectDutyByPidAndOverTime(String peopleId, Date createTime);
    SYS_Duty selectDutysByPidsAndDate(Date startTime, Date endTime, String pid,String name);
}
