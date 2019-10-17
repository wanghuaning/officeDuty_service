package com.local.controller;

import com.local.entity.sys.SYS_CODE;
import com.local.service.CodeService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Api(value = "通用字典", description = "字典接口")
@RestController
@RequestMapping("/api/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @ApiOperation(value = "查询机构级别", notes = "机构级别列表", httpMethod = "GET", tags = {"字典管理接口"})
    @GetMapping("/level")
    @ResponseBody
    public String getLevels(){
        List<SYS_CODE> codes=codeService.selectLevels();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "查询机构类别", notes = "机构类别表", httpMethod = "GET", tags = {"字典管理接口"})
    @GetMapping("/category")
    @ResponseBody
    public String getCategorys(){
        List<SYS_CODE> codes=codeService.selectCategorys();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "查询机构隶属关系", notes = "机构隶属关系", httpMethod = "GET", tags = {"字典管理接口"})
    @GetMapping("/affiliation")
    @ResponseBody
    public String getAffiliations(){
        List<SYS_CODE> codes=codeService.selectAffiliations();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "民族", notes = "民族", httpMethod = "GET", tags = {"民族查询接口"})
    @GetMapping("/nationality")
    @ResponseBody
    public String getNationalitys(){
        List<SYS_CODE> codes=codeService.selectNationality();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "政治面貌", notes = "政治面貌", httpMethod = "GET", tags = {"政治面貌查询接口"})
    @GetMapping("/party")
    @ResponseBody
    public String getPartys(){
        List<SYS_CODE> codes=codeService.selectParty();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "现职务层次", notes = "现职务层次", httpMethod = "GET", tags = {"现职务层次查询接口"})
    @GetMapping("/position")
    @ResponseBody
    public String getPositions(){
        List<SYS_CODE> codes=codeService.selectPosition();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "现职级", notes = "现职级", httpMethod = "GET", tags = {"现职级查询接口"})
    @GetMapping("/positionLevel")
    @ResponseBody
    public String getPositionLevels(){
        List<SYS_CODE> codes=codeService.selectPositionLevel();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "编制类型", notes = "编制类型", httpMethod = "GET", tags = {"编制类型查询接口"})
    @GetMapping("/politicalStatus")
    @ResponseBody
    public String getPoliticalStatuss(){
        List<SYS_CODE> codes=codeService.selectPoliticalStatus();
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "选拔任用方式", notes = "选拔任用方式", httpMethod = "GET", tags = {"选拔任用方式查询接口"})
    @GetMapping("/selectionMethod")
    @ResponseBody
    public String getSelectionMethods(){
        List<SYS_CODE> codes=codeService.selectCodesByPid("210");
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "批次", notes = "批次", httpMethod = "GET", tags = {"批次查询接口"})
    @GetMapping("/bacth")
    @ResponseBody
    public String getBacths(){
        List<SYS_CODE> codes=codeService.selectCodesByPid("395");
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "学历", notes = "学历", httpMethod = "GET", tags = {"学历查询接口"})
    @GetMapping("/education")
    @ResponseBody
    public String getEducations(){
        List<SYS_CODE> codes=codeService.selectCodesByPid("250");
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "奖惩名称代码", notes = "奖惩名称代码", httpMethod = "GET", tags = {"奖惩名称代码查询接口"})
    @GetMapping("/rewardType")
    @ResponseBody
    public String getRewardType(){
        List<SYS_CODE> codes=codeService.selectCodesByPid("285");
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "批准机关性质", notes = "批准机关性质", httpMethod = "GET", tags = {"批准机关性质查询接口"})
    @GetMapping("/unitType")
    @ResponseBody
    public String getUnitType(){
        List<SYS_CODE> codes=codeService.selectCodesByPid("345");
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "考核年份", notes = "考核年份", httpMethod = "GET", tags = {"考核年份查询接口"})
    @GetMapping("/year")
    @ResponseBody
    public String getYears(){
        Calendar calendar=Calendar.getInstance();
        int year_now=calendar.get(Calendar.YEAR);
        List<SYS_CODE> codes=new ArrayList<>();
        for (int i=year_now;i>1992;i--){
            System.out.println(i);
            SYS_CODE code=new SYS_CODE();
            code.setCodeName(String.valueOf(i));
            code.setLabel(String.valueOf(i));
            code.setValue(String.valueOf(i));
            codes.add(code);
        }
        if (codes.size()>0){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "考核结论", notes = "考核结论", httpMethod = "GET", tags = {"考核结论查询接口"})
    @GetMapping("/assessment")
    @ResponseBody
    public String getAssessments(){
        List<SYS_CODE> codes=codeService.selectCodesByPid("370");
        if (codes!=null){
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS,codes,null).getJson();
        }else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
}
