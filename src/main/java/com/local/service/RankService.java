package com.local.service;

import com.local.entity.sys.SYS_Assessment;
import com.local.entity.sys.SYS_Rank;
import org.nutz.dao.QueryResult;

import java.util.Date;
import java.util.List;

public interface RankService {
    QueryResult selectRanks(int pageSize, int pageNumber, String pid);
    SYS_Rank selectRankById(String id);
    SYS_Rank selectTurnRankById(String id);
    SYS_Rank selectTurnNotSelfRankById(String pid,String rid);
    SYS_Rank selectRankByPidOrderByTime(String pid);
    SYS_Rank selectEnableRankByPidOrderByTime(String pid);
    SYS_Rank selectNotEnableRankByPidOrderByTime(String pid);
    SYS_Rank selectNowRankByPidOrderByTime(String pid);
    SYS_Rank selectRankByPidAndTimeOrderByTime(String pid,Date date,String duty);
    SYS_Rank selectRankByNameAndTime(String name, String peopleId, Date createTime);
    SYS_Rank selectNotAproRanksByPid(String pid);
    SYS_Rank selectAprodRanksByPid(String pid);
    SYS_Rank selectAprodRanksByPidAndBatch(String pid,String batch);
    List<SYS_Rank> selectRanksByUnitIdAndStates(String unitId,String name,String states);
    List<SYS_Rank> selectRanksByUnitIdAndStatesNotJunZhuan(String unitId,String name,String states);
    List<SYS_Rank> selectRanksByPeopleId(String pid);
    void insertRank(SYS_Rank rank);

    void deleteRank(String id);

    void updateRank(SYS_Rank rank);

    List<SYS_Rank> selectRanksByUnitId(String unitId, String isChild);//根据单位ID查询，是否包含下级单位的 人员1:包含
    List<SYS_Rank> selectRanksByUnitId(String unitId);
    List<SYS_Rank> selectRanksByUnitIds(List<String> unitId);
    List<SYS_Rank> selectRanksFlagByUnitId(String unitId, String flag,String name);//是:套转 否：晋升
    List<SYS_Rank> selectRanksFlagNotTurnByUnitId(String unitId, String flag,String name);//是:套转 否：晋升 不包含军转

}
