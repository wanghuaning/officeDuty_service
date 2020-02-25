package com.local.controller;

import com.local.cell.UserManager;
import com.local.entity.sys.*;
import com.local.service.PeopleService;
import com.local.service.RankService;
import com.local.service.UnitService;
import com.local.service.UserService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rank")
public class RankController {
    private final static Logger logger = LoggerFactory.getLogger(RankController.class);

    @Autowired
    private RankService rankService;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private UserService userService;
    @Autowired
    private UnitService unitService;

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
    @ApiOperation(value = "新增职级判断", notes = "新增职级判断", httpMethod = "POST", tags = "新增职级判断接口")
    @PostMapping(value = "/addRank")
    @ResponseBody
    public String insertRankSold(@Validated @RequestBody SYS_Rank rank) {
        try {
            SYS_Rank rankByNameAndTime = rankService.selectRankByNameAndTime(rank.getName(), rank.getPeopleId(), rank.getCreateTime());
            if (rankByNameAndTime != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_RANK_ERROE, null, null).getJson();
            }
            SYS_People people = peopleService.selectPeopleById(rank.getPeopleId());
            if (people != null) {
                String uuid = UUID.randomUUID().toString();
                rank.setId(uuid);
                rank.setPeopleName(people.getName());
                rank.setUnitId(people.getUnitId());
                SYS_Rank rankTurn1 = rankService.selectTurnRankById(people.getId());
                if (rankTurn1 != null && "是".equals(rank.getFlag())) {
                    return new Result(ResultCode.ERROR.toString(), "只能有一个套转职级", null, null).getJson();
                }
                SYS_UNIT unit=unitService.selectUnitById(people.getUnitId());
                List<SYS_Rank> coutRank= rankService.selectRanksByUnitIdAndStates(people.getUnitId(),rank.getName(),"在任");
                int size= 1;
                if (coutRank!=null){
                    size=coutRank.size();
                }
                if (checkRankNum(unit,rank, size)){
                    rankService.insertRank(rank);
                    SYS_Rank sys_rank = rankService.selectNowRankByPidOrderByTime(people.getId());
                    SYS_Rank rankTurn = rankService.selectTurnRankById(people.getId());
                    if (rankTurn != null) {
                        people.setTurnRank(rankTurn.getName());
                        people.setTurnRankTime(rankTurn.getCreateTime());
                    } else {
                        people.setTurnRank("");
                        people.setTurnRankTime(null);
                    }
                    if (sys_rank != null) {
                        if ("是".equals(sys_rank.getLeaders())) {
                            people.setDetail("军转干部");
                        }
                        people.setPositionLevel(sys_rank.getName());
                        people.setPositionLevelTime(sys_rank.getCreateTime());
                        peopleService.updatePeople(people);
                    } else {
                        people.setPositionLevel("");
                        people.setPositionLevelTime(null);

                        peopleService.updatePeople(people);
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, rank, null).getJson();
                }else {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, "false", null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "新增职级", notes = "新增职级", httpMethod = "POST", tags = "新增职级接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertRank(@Validated @RequestBody SYS_Rank rank) {
        try {
            SYS_Rank rankByNameAndTime = rankService.selectRankByNameAndTime(rank.getName(), rank.getPeopleId(), rank.getCreateTime());
            if (rankByNameAndTime != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_RANK_ERROE, null, null).getJson();
            }
            SYS_People people = peopleService.selectPeopleById(rank.getPeopleId());
            if (people != null) {
                String uuid = UUID.randomUUID().toString();
                rank.setId(uuid);
                rank.setPeopleName(people.getName());
                rank.setUnitId(people.getUnitId());
                SYS_Rank rankTurn1 = rankService.selectTurnRankById(people.getId());
                if (rankTurn1 != null && "是".equals(rank.getFlag())) {
                    return new Result(ResultCode.ERROR.toString(), "只能有一个套转职级", null, null).getJson();
                }
                    rankService.insertRank(rank);
                    SYS_Rank sys_rank = rankService.selectNowRankByPidOrderByTime(people.getId());
                    SYS_Rank rankTurn = rankService.selectTurnRankById(people.getId());
                    if (rankTurn != null) {
                        people.setTurnRank(rankTurn.getName());
                        people.setTurnRankTime(rankTurn.getCreateTime());
                    } else {
                        people.setTurnRank("");
                        people.setTurnRankTime(null);
                    }
                    if (sys_rank != null) {
                        if ("是".equals(sys_rank.getLeaders())) {
                            people.setDetail("军转干部");
                        }
                        people.setPositionLevel(sys_rank.getName());
                        people.setPositionLevelTime(sys_rank.getCreateTime());
                        peopleService.updatePeople(people);
                    } else {
                        people.setPositionLevel("");
                        people.setPositionLevelTime(null);

                        peopleService.updatePeople(people);
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, rank, null).getJson();
                }else {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, "false", null).getJson();
                }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    public boolean checkRankNum(SYS_UNIT unit,SYS_Rank sys_rank, int rankSize){
        boolean fas= true;
        if ("一级调研员".equals(sys_rank.getName())){
            if (rankSize>=unit.getOneResearcherNum()){
                fas= false;
            }
        }else if ("二级调研员".equals(sys_rank.getName())){
            if (rankSize>=unit.getTowResearcherNum()){
                fas= false;
            }
        }else if ("三级调研员".equals(sys_rank.getName())){
            if (rankSize>=unit.getThreeResearcherNum()){
                fas= false;
            }
        }else if ("四级调研员".equals(sys_rank.getName())){
            if (rankSize>=unit.getFourResearcherNum()){
                fas= false;
            }
        }else if ("一级主任科员".equals(sys_rank.getName())){
            if (rankSize>=unit.getOneClerkNum()){
                fas= false;
            }
        }else if ("二级主任科员".equals(sys_rank.getName())){
            if (rankSize>=unit.getTowClerkNum()){
                fas= false;
            }
        }else if ("三级主任科员".equals(sys_rank.getName())){
            if (rankSize>=unit.getThreeClerkNum()){
                fas= false;
            }
        }else if ("四级主任科员".equals(sys_rank.getName())){
            if (rankSize>=unit.getFourClerkNum()){
                fas= false;
            }
        }
        return fas;
    }

    @ApiOperation(value = "删除职级", notes = "删除职级", httpMethod = "POST", tags = "删除职级接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleterank(HttpServletRequest request, @RequestParam(value = "id", required = false) String id) {
        try {
            boolean sd = false;
            SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (user != null) {
                if (user.getRoles() == "1") {
                    sd = true;
                }
            }
            if (StrUtils.isBlank(id)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            } else {
                SYS_Rank rank = rankService.selectRankById(id);
                SYS_People people = peopleService.selectPeopleById(rank.getPeopleId());
                SYS_Rank rankTurn = rankService.selectTurnRankById(people.getId());
                if (people != null) {
                    if (rank.getApprovalTime() == null) {
                        sd = true;
                    }
                    if (sd) {
                        rankService.deleteRank(id);
                        SYS_Rank sys_rank = rankService.selectNowRankByPidOrderByTime(people.getId());
                        if (rankTurn != null) {
                            people.setTurnRank(rankTurn.getName());
                            people.setTurnRankTime(rankTurn.getCreateTime());
                        } else {
                            people.setTurnRank("");
                            people.setTurnRankTime(null);
                        }
                        if (sys_rank != null) {
                            if ("是".equals(sys_rank.getLeaders())) {
                                people.setDetail("军转干部");
                            }
                            people.setPositionLevel(sys_rank.getName());
                            people.setPositionLevelTime(sys_rank.getCreateTime());
                            peopleService.updatePeople(people);
                        } else {
                            people.setPositionLevel("");
                            people.setPositionLevelTime(null);
                            peopleService.updatePeople(people);
                        }
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
                    } else {
                        return new Result(ResultCode.ERROR.toString(), "权限不足！", null, null).getJson();
                    }
                } else {
                    return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
                }
            }
        } catch (Exception e) {
            logger.error(ResultMsg.DEL_ERROR, e);
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
                SYS_People people = peopleService.selectPeopleById(rankById.getPeopleId());
                if (people != null) {
                    rank.setPeopleName(people.getName());
                    rank.setUnitId(people.getUnitId());
                    SYS_Rank rank1 = rankService.selectTurnNotSelfRankById(people.getId(), rank.getId());
                    if (rank1 != null && "是".equals(rank.getFlag())) {
                        return new Result(ResultCode.ERROR.toString(), "职级只能有一条！", null, null).getJson();
                    }
                    rankService.updateRank(rank);
                    SYS_Rank sys_rank = rankService.selectNowRankByPidOrderByTime(people.getId());
                    SYS_Rank rankTurn = rankService.selectTurnRankById(people.getId());
                    if (rankTurn != null) {
                        people.setTurnRank(rankTurn.getName());
                        people.setTurnRankTime(rankTurn.getCreateTime());
                    } else {
                        people.setTurnRank("");
                        people.setTurnRankTime(null);
                    }
                    if (sys_rank != null) {
                        if ("是".equals(sys_rank.getLeaders())) {
                            people.setDetail("军转干部");
                        }
                        people.setPositionLevel(sys_rank.getName());
                        people.setPositionLevelTime(sys_rank.getCreateTime());
                        peopleService.updatePeople(people);
                    } else {
                        people.setPositionLevel("");
                        people.setPositionLevelTime(null);
                        peopleService.updatePeople(people);
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, rank, null).getJson();
                } else {
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
    @ApiOperation(value = "修改职级控制", notes = "修改职级控制", httpMethod = "POST", tags = "修改职级控制接口")
    @PostMapping(value = "/editRank")
    @ResponseBody
    public String updateSoldRank(@Validated @RequestBody SYS_Rank rank) {
        try {
            SYS_Rank rankById = rankService.selectRankById(rank.getId());
            if (rankById != null) {
                rank.setPeopleId(rankById.getPeopleId());
                SYS_People people = peopleService.selectPeopleById(rankById.getPeopleId());
                if (people != null) {
                    rank.setPeopleName(people.getName());
                    rank.setUnitId(people.getUnitId());
                    SYS_Rank rank1 = rankService.selectTurnNotSelfRankById(people.getId(), rank.getId());
                    if (rank1 != null && "是".equals(rank.getFlag())) {
                        return new Result(ResultCode.ERROR.toString(), "职级只能有一条！", null, null).getJson();
                    }
                    SYS_UNIT unit=unitService.selectUnitById(people.getUnitId());
                    List<SYS_Rank> coutRank= rankService.selectRanksByUnitIdAndStates(people.getUnitId(),rank.getName(),"在任");
                    int size= 1;
                    if (coutRank!=null){
                        size=coutRank.size();
                    }
                    if (!checkRankNum(unit,rank, size) && !rankById.getName().equals(rank.getName())) {
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, "false", null).getJson();
                    }
                        rankService.updateRank(rank);
                        SYS_Rank sys_rank = rankService.selectNowRankByPidOrderByTime(people.getId());
                        SYS_Rank rankTurn = rankService.selectTurnRankById(people.getId());
                        if (rankTurn != null) {
                            people.setTurnRank(rankTurn.getName());
                            people.setTurnRankTime(rankTurn.getCreateTime());
                        } else {
                            people.setTurnRank("");
                            people.setTurnRankTime(null);
                        }
                        if (sys_rank != null) {
                            if ("是".equals(sys_rank.getLeaders())) {
                                people.setDetail("军转干部");
                            }
                            people.setPositionLevel(sys_rank.getName());
                            people.setPositionLevelTime(sys_rank.getCreateTime());
                            peopleService.updatePeople(people);
                        } else {
                            people.setPositionLevel("");
                            people.setPositionLevelTime(null);
                            peopleService.updatePeople(people);
                        }
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, rank, null).getJson();
                } else {
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

    @ApiOperation(value = "职级权限", notes = "职级权限", httpMethod = "POST", tags = "职级权限接口")
    @PostMapping(value = "/isEdit")
    @ResponseBody
    public String isEdit(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            } else {
                SYS_Rank rank = rankService.selectRankById(id);
                if (rank != null) {
                    if (rank.getApprovalTime() != null) {
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, "true", null).getJson();
                    } else {
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, "false", null).getJson();
                    }
                } else {
                    return new Result(ResultCode.ERROR.toString(), "职级不存在", null, null).getJson();
                }
            }
        } catch (Exception e) {
            logger.error(ResultMsg.DEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }
}
