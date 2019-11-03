package com.local.service;

import com.local.entity.sys.SYS_Rank;
import org.nutz.dao.QueryResult;

import java.util.Date;
import java.util.List;

public interface RankService {
    QueryResult selectRanks(int pageSize, int pageNumber, String pid);
    SYS_Rank selectRankById(String id);
    SYS_Rank selectRankByPidOrderByTime(String pid);
    SYS_Rank selectRankByNameAndTime(String name, String peopleId, Date createTime);
    SYS_Rank selectNotAproRanksByPid(String pid);
    SYS_Rank selectAprodRanksByPid(String pid);
    List<SYS_Rank> selectRanksByPeopleId(String pid);
    void insertRank(SYS_Rank rank);

    void deleteRank(String id);

    void updateRank(SYS_Rank rank);

}
