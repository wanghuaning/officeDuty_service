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
    SYS_Rank selectRankByNameAndTime(String name, String peopleId, Date createTime);
    SYS_Rank selectNotAproRanksByPid(String pid);
    SYS_Rank selectAprodRanksByPid(String pid);
    SYS_Rank selectAprodRanksByPidAndBatch(String pid,String batch);
    List<SYS_Rank> selectRanksByPeopleId(String pid);
    void insertRank(SYS_Rank rank);

    void deleteRank(String id);

    void updateRank(SYS_Rank rank);

    List<SYS_Rank> selectRanksByUnitId(String unitId, String isChild);//根据单位ID查询，是否包含下级单位的 人员1:包含
    List<SYS_Rank> selectRanksFlagByUnitId(String unitId, String flag,String name);//1:套转 0：晋升

}
