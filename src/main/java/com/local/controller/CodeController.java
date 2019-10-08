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
}
