package com.local.service;

import com.local.entity.sys.SYS_Reward;
import org.nutz.dao.QueryResult;

import java.util.Date;

public interface RewardService {
    QueryResult selectRewards(int pageSize, int pageNumber, String pid);
    SYS_Reward selectRewardById(String id);
    SYS_Reward selectRewardByPidOrderByTime(String pid);
    SYS_Reward selectRewardByNameAndTime(String name, String peopleId, Date createTime);
    void insertReward(SYS_Reward reward);

    void deleteReward(String id);

    void updateReward(SYS_Reward reward);
}