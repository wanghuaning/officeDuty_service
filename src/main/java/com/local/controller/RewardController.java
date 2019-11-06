package com.local.controller;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Reward;
import com.local.service.PeopleService;
import com.local.service.RewardService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import com.local.util.StrUtils;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reward")
public class RewardController {
    private final static Logger logger = LoggerFactory.getLogger(RewardController.class);

    @Autowired
    private RewardService rewardService;
    @Autowired
    private PeopleService peopleService;

    @ApiOperation(value = "奖惩信息", notes = "奖惩信息", httpMethod = "GET", tags = "奖惩信息接口")
    @GetMapping("/rewardInof")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "pid", required = false) String pid) {
        try {
            QueryResult queryResult = rewardService.selectRewards(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), pid);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "新增奖惩", notes = "新增奖惩", httpMethod = "POST", tags = "新增奖惩接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertPeople(@Validated @RequestBody SYS_Reward reward) {
        try {
            SYS_Reward RewardByNameAndTime = rewardService.selectRewardByNameAndTime(reward.getName(), reward.getPeopleId(), reward.getCreateTime());
            if (RewardByNameAndTime != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_REWARD_ERROE, null, null).getJson();
            }
            String uuid = UUID.randomUUID().toString();
            reward.setId(uuid);
            SYS_People people=peopleService.selectPeopleById(reward.getPeopleId());
            if (people!=null) {
                reward.setPeopleName(people.getName());
                reward.setUnitId(people.getUnitId());
            }
            rewardService.insertReward(reward);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, reward, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "删除奖惩", notes = "删除奖惩", httpMethod = "POST", tags = "删除奖惩接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteReward(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            } else {
                SYS_Reward Reward = rewardService.selectRewardById(id);
                rewardService.deleteReward(id);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.DEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改奖惩", notes = "修改奖惩", httpMethod = "POST", tags = "修改奖惩接口")
    @PostMapping(value = "/edit")
    @ResponseBody
    public String updateReward(@Validated @RequestBody SYS_Reward reward) {
        try {
            SYS_Reward RewardById = rewardService.selectRewardById(reward.getId());
            if (RewardById != null) {
                reward.setPeopleId(RewardById.getPeopleId());
                rewardService.updateReward(reward);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, reward, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
}
