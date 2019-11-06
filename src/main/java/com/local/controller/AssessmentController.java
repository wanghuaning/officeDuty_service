package com.local.controller;

import com.local.entity.sys.SYS_Assessment;
import com.local.entity.sys.SYS_People;
import com.local.service.AssessmentService;
import com.local.service.PeopleService;
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
@RequestMapping("/api/assessment")
public class AssessmentController {
    private final static Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private PeopleService peopleService;

    @ApiOperation(value = "奖惩信息", notes = "奖惩信息", httpMethod = "GET", tags = "奖惩信息接口")
    @GetMapping("/assessmentInof")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "pid", required = false) String pid) {
        try {
            QueryResult queryResult = assessmentService.selectAssessments(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), pid);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "新增奖惩", notes = "新增奖惩", httpMethod = "POST", tags = "新增奖惩接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertPeople(@Validated @RequestBody SYS_Assessment assessment) {
        try {
            SYS_Assessment assessmentByNameAndTime = assessmentService.selectAssessmentByNameAndTime(assessment.getName(), assessment.getPeopleId(), assessment.getYear());
            if (assessmentByNameAndTime != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_ASSESSMENT_ERROE, null, null).getJson();
            }
            String uuid = UUID.randomUUID().toString();
            assessment.setId(uuid);
            SYS_People people=peopleService.selectPeopleById(assessment.getPeopleId());
            if (people!=null){
                assessment.setPeopleName(people.getName());
                assessment.setUnitId(people.getUnitId());
            }
            assessmentService.insertAssessment(assessment);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, assessment, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "删除奖惩", notes = "删除奖惩", httpMethod = "POST", tags = "删除奖惩接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteAssessment(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            } else {
                SYS_Assessment assessment = assessmentService.selectAssessmentById(id);
                assessmentService.deleteAssessment(id);
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
    public String updateAssessment(@Validated @RequestBody SYS_Assessment assessment) {
        try {
            SYS_Assessment assessmentById = assessmentService.selectAssessmentById(assessment.getId());
            if (assessmentById != null) {
                assessment.setPeopleId(assessmentById.getPeopleId());
                SYS_People people=peopleService.selectPeopleById(assessmentById.getPeopleId());
                if (people!=null){
                    assessment.setPeopleName(people.getName());
                    assessment.setUnitId(people.getUnitId());
                }
                assessmentService.updateAssessment(assessment);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, assessment, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

}
