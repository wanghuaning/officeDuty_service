package com.local.controller;

import com.local.cell.DataManager;
import com.local.entity.sys.SYS_UNIT;
import com.local.model.RankModel;
import com.local.model.ReimbursementModel;
import com.local.service.*;
import com.local.util.ExcelFileGenerator;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {
    private final static Logger logger= LoggerFactory.getLogger(DataController.class);
    @Autowired
    private UnitService unitService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private RankService rankService;

    @Autowired
    private EducationService educationService;
    @Autowired
    private AssessmentService assessmentService;

    @ApiOperation(value = "导出晋升职级人员备案名册", notes = "导出晋升职级人员备案名册", httpMethod = "GET", tags = "导出晋升职级人员备案名册接口")
    @RequestMapping(value = "/exportDataExcel")
    public String exportDataExcel(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "flag",required = false) String flag
            ,@RequestParam(value = "unitName",required = false) String unitName,@RequestParam(value = "unitIds",required = false) String[] unitIds){
        try {
            if ("filingList".equals(flag)){
                List<RankModel> rankModels= DataManager.filingList(unitService,unitName,response,peopleService,rankService);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, rankModels, null).getJson();
            }else {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "OK!", null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }


    @ApiOperation(value = "导出人员信息", notes = "导出人员信息", httpMethod = "GET", tags = "导出人员信息接口")
    @RequestMapping(value = "/exportPeopleData")
    public String exportPeopleData(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "flag",required = false) String flag
            ,@RequestParam(value = "peopleId",required = false) String peopleId){
        try {
            if ("reimbursement".equals(flag)){
                ReimbursementModel reimbursementModel= DataManager.exportPeopleData(response,peopleService,peopleId,
                        rankService,educationService,assessmentService,unitService);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, reimbursementModel, null).getJson();
            }else {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "OK!", null).getJson();
            }
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
}
