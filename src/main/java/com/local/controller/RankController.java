package com.local.controller;

import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_People;
import com.local.service.PeopleService;
import com.local.service.RankService;
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
@RequestMapping("/api/rank")
public class RankController {
    private final static Logger logger= LoggerFactory.getLogger(RankController.class);

    @Autowired
    private RankService rankService;

    @Autowired
    private PeopleService peopleService;

    @ApiOperation(value = "职级信息", notes = "职级信息", httpMethod = "GET", tags = "职级信息接口")
    @GetMapping("/rankInof")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "pid", required = false) String pid) {
        try {
            QueryResult queryResult = rankService.selectRanks(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), pid);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "新增职级", notes = "新增职级", httpMethod = "POST", tags = "新增职级接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertPeople(@Validated @RequestBody SYS_Rank rank) {
        try {
            SYS_Rank rankByNameAndTime = rankService.selectRankByNameAndTime(rank.getName(), rank.getPeopleId(),rank.getCreateTime());
            if (rankByNameAndTime != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_RANK_ERROE, null, null).getJson();
            }
            SYS_People people=peopleService.selectPeopleById(rank.getPeopleId());
            if (people!=null){
                String uuid= UUID.randomUUID().toString();
                rank.setId(uuid);
                rank.setPeopleName(people.getName());
                rank.setUnitId(people.getUnitId());
                rankService.insertRank(rank);
                SYS_Rank sys_rank=rankService.selectRankByPidOrderByTime(people.getId());
                if (sys_rank!=null){
                    if (sys_rank.getStatus().contains("在任")){
                        people.setPositionLevel(sys_rank.getName());
                        people.setPositionLevelTime(sys_rank.getCreateTime());
                        peopleService.updatePeople(people);
                    }else {
                        people.setPositionLevel("");
                        people.setPositionLevelTime(null);
                        peopleService.updatePeople(people);
                    }
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, rank, null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "删除职级", notes = "删除职级", httpMethod = "POST", tags = "删除职级接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleterank(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            }else {
                SYS_Rank rank=rankService.selectRankById(id);
                SYS_People people=peopleService.selectPeopleById(rank.getPeopleId());
                if (people!=null){
                    rankService.deleteRank(id);
                    SYS_Rank sys_rank=rankService.selectRankByPidOrderByTime(people.getId());
                    if (sys_rank!=null){
                        people.setPositionLevel(sys_rank.getName());
                        people.setPositionLevelTime(sys_rank.getCreateTime());
                        peopleService.updatePeople(people);
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
                }else {
                    return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
                }
            }
        }catch (Exception e){
            logger.error(ResultMsg.DEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "修改职级", notes = "修改职级", httpMethod = "POST", tags = "修改职级接口")
    @PostMapping(value = "/edit")
    @ResponseBody
    public String updaterank(@Validated @RequestBody SYS_Rank rank) {
        try {
            SYS_Rank rankById = rankService.selectRankById(rank.getId());
            if (rankById != null) {
                rank.setPeopleId(rankById.getPeopleId());
                SYS_People people=peopleService.selectPeopleById(rankById.getPeopleId());
                if (people!=null){
                    rank.setPeopleName(people.getName());
                    rank.setUnitId(people.getUnitId());
                    rankService.updateRank(rank);
                    SYS_Rank sys_rank=rankService.selectRankByPidOrderByTime(people.getId());
                    if (sys_rank!=null){
                        people.setPositionLevel(sys_rank.getName());
                        people.setPositionLevelTime(sys_rank.getCreateTime());
                        peopleService.updatePeople(people);
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, rank, null).getJson();
                }else {
                    return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
}
