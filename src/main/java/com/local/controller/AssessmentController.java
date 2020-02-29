package com.local.controller;

import com.local.entity.sys.SYS_Assessment;
import com.local.entity.sys.SYS_People;
import com.local.model.AssessmentModel;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {
    private final static Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private PeopleService peopleService;

    @ApiOperation(value = "考核信息", notes = "考核信息", httpMethod = "GET", tags = "考核信息接口")
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


    @ApiOperation(value = "新增考核", notes = "新增考核", httpMethod = "POST", tags = "新增考核接口")
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


    @ApiOperation(value = "删除考核", notes = "删除考核", httpMethod = "POST", tags = "删除考核接口")
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

    @ApiOperation(value = "修改考核", notes = "修改考核", httpMethod = "POST", tags = "修改考核接口")
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

    @ApiOperation(value = "修改考核", notes = "修改考核", httpMethod = "POST", tags = "修改考核接口")
    @PostMapping(value = "/editKaoHe")
    @ResponseBody
    public String updateAssessments(@Validated @RequestBody AssessmentModel model) {
        try {
            if (!StrUtils.isBlank(model)){
                SYS_Assessment assessmentById = assessmentService.selectAssessmentById(model.getId());
                SYS_Assessment assessmentById1 = assessmentService.selectAssessmentById(model.getId1());
                SYS_Assessment assessmentById2 = assessmentService.selectAssessmentById(model.getId2());
                if (assessmentById != null) {
                    assessmentById.setName(model.getName());
                    assessmentService.updateAssessment(assessmentById);
                }
                if (assessmentById1 != null) {
                    assessmentById1.setName(model.getName1());
                    assessmentService.updateAssessment(assessmentById1);
                }
                if (assessmentById2 != null) {
                    assessmentById2.setName(model.getName2());
                    assessmentService.updateAssessment(assessmentById2);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, model, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "批量添加考核", notes = "批量添加考核", httpMethod = "POST", tags = "批量添加考核接口")
    @PostMapping(value = "/addKaoHeAll")
    @ResponseBody
    public String addKaoHeAll(@RequestParam(value = "unitId", required = false) String unitId,@RequestParam(value = "year", required = false) String year) {
        try {
            if (StrUtils.isBlank(unitId)) {
                return new Result(ResultCode.ERROR.toString(), "登录已过期", null, null).getJson();
            }
            if (StrUtils.isBlank(year)) {
                return new Result(ResultCode.ERROR.toString(), "请选择添加年份", null, null).getJson();
            }
            List<SYS_People> peopleList=peopleService.selectPeoplesByUnitId(unitId,"0","在职");
            if (peopleList==null){
                return new Result(ResultCode.ERROR.toString(), "无人员！", null, null).getJson();
            }
            List<SYS_Assessment> assessments=new ArrayList<>();
            for (SYS_People people:peopleList){
                SYS_Assessment assessment = assessmentService.selectAssessmentByYear(people.getId(),StrUtils.strToInt(year));
                if (assessment==null){
                    SYS_Assessment sys_assessment=new SYS_Assessment();
                    sys_assessment.setId(UUID.randomUUID().toString());
                    sys_assessment.setName("称职");
                    sys_assessment.setPeopleName(people.getName());
                    sys_assessment.setPeopleId(people.getId());
                    sys_assessment.setYear(StrUtils.strToInt(year));
                    sys_assessment.setUnitId(unitId);
                    assessmentService.insertAssessment(sys_assessment);
                    assessments.add(sys_assessment);
                }
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, assessments, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.DEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "批量删除考核", notes = "批量删除考核", httpMethod = "POST", tags = "批量删除考核接口")
    @PostMapping(value = "/deleteKaoHeAll")
    @ResponseBody
    public String deleteKaoHeAll(@RequestParam(value = "unitId", required = false) String unitId,@RequestParam(value = "year", required = false) String year) {
        try {
            if (StrUtils.isBlank(unitId)) {
                return new Result(ResultCode.ERROR.toString(), "登录已过期", null, null).getJson();
            }
            if (StrUtils.isBlank(year)) {
                return new Result(ResultCode.ERROR.toString(), "请选择添加年份", null, null).getJson();
            }
            List<SYS_People> peopleList=peopleService.selectPeoplesByUnitId(unitId,"0","在职");
            if (peopleList==null){
                return new Result(ResultCode.ERROR.toString(), "无人员！", null, null).getJson();
            }
            List<SYS_Assessment> assessments=new ArrayList<>();
            for (SYS_People people:peopleList){
                SYS_Assessment assessment = assessmentService.selectAssessmentByYear(people.getId(),StrUtils.strToInt(year));
                if (assessment!=null){
                    assessments.add(assessment);
                    assessmentService.deleteAssessment(assessment.getId());
                }
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, assessments, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.DEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }
}
