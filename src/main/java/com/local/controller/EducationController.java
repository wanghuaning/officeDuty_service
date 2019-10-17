package com.local.controller;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Education;
import com.local.service.EducationService;
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
@RequestMapping("/api/education")
public class EducationController {
    private final static Logger logger= LoggerFactory.getLogger(EducationController.class);

    @Autowired
    private EducationService educationService;

    @Autowired
    private PeopleService peopleService;

    @ApiOperation(value = "学历信息", notes = "学历信息", httpMethod = "GET", tags = "学历信息接口")
    @GetMapping("/educationInof")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "pid", required = false) String pid) {
        try {
            QueryResult queryResult = educationService.selectEducations(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), pid);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "新增学历", notes = "新增学历", httpMethod = "POST", tags = "新增学历接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertPeople(@Validated @RequestBody SYS_Education education) {
        try {
            SYS_Education educationByNameAndTime = educationService.selectEducationByNameAndTime(education.getName(), education.getPeopleId(),education.getCreateTime());
            if (educationByNameAndTime != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_EDUCATION_ERROE, null, null).getJson();
            }
            SYS_People people=peopleService.selectPeopleById(education.getPeopleId());
            if (people!=null){
                String uuid= UUID.randomUUID().toString();
                education.setId(uuid);
                educationService.insertEducation(education);
                SYS_Education sys_education=educationService.selectEducationByPidOrderByTime(people.getId());
                if (sys_education!=null){
                    people.setEducation(sys_education.getName());
                    peopleService.updatePeople(people);
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, education, null).getJson();
            }else {
                return new Result(ResultCode.ERROR.toString(), "人员不存在", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "删除学历", notes = "删除学历", httpMethod = "POST", tags = "删除学历接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteEducation(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)){
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            }else {
                SYS_Education education=educationService.selectEducationById(id);
                SYS_People people=peopleService.selectPeopleById(education.getPeopleId());
                if (people!=null){
                    educationService.deleteEducation(id);
                    SYS_Education sys_education=educationService.selectEducationByPidOrderByTime(people.getId());
                    if (sys_education!=null){
                        people.setEducation(sys_education.getName());
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
    @ApiOperation(value = "修改学历", notes = "修改学历", httpMethod = "POST", tags = "修改学历接口")
    @PostMapping(value = "/edit")
    @ResponseBody
    public String updateEducation(@Validated @RequestBody SYS_Education Education) {
        try {
            SYS_Education EducationById = educationService.selectEducationById(Education.getId());
            if (EducationById != null) {
                Education.setPeopleId(EducationById.getPeopleId());
                SYS_People people=peopleService.selectPeopleById(EducationById.getPeopleId());
                if (people!=null){
                    educationService.updateEducation(Education);
                    SYS_Education sys_education=educationService.selectEducationByPidOrderByTime(people.getId());
                    if (sys_education!=null){
                        people.setEducation(sys_education.getName());
                        peopleService.updatePeople(people);
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, Education, null).getJson();
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
