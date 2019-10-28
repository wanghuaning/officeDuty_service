package com.local.controller;

import com.local.entity.sys.SYS_UNIT;
import com.local.service.UnitService;
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

    @ApiOperation(value = "导出晋升职级人员备案名册", notes = "导出晋升职级人员备案名册", httpMethod = "GET", tags = "导出晋升职级人员备案名册接口")
    @RequestMapping(value = "/exportDataExcel")
    public String exportDataExcel(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "flag",required = false) String flag
            ,@RequestParam(value = "unitName",required = false) String unitName,@RequestParam(value = "unitIds",required = false) String[] unitIds){
        try {
            List<SYS_UNIT> unitList=unitService.selectUnitAll();
            ClassPathResource resource=new ClassPathResource("exportExcel/filingListExport.xls");
            String path=resource.getFile().getPath();
            SYS_UNIT unit=unitService.selectUnitByName(unitName);
            String[] arr1=new String[]{unit.getName(),unit.getContactNumber(),unit.getContact()};
            Workbook temp= ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response,"公务员晋升职级人员备案名册.xls");
            excelFileGenerator.createExcelFileFixedRow(temp.getSheet("备案名册"),1,new int[]{2,6,11},arr1);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "公务员晋升职级人员备案名册", null).getJson();
        }catch (Exception e){
            logger.error(ResultMsg.GET_EXCEL_ERROR,e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
}
