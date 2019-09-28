package com.local.controller;

import com.local.entity.REG_FactorMeasure;
import com.local.entity.REG_InfluencingFactor;
import com.local.entity.REG_PushRecord;
import com.local.entity.REG_Registration;
import com.local.model.FactorModel;
import com.local.service.PushRecordService;
import com.local.service.RegService;
import com.local.util.Response;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(value = "备案信息", description = "备案信息接口")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class RegInfoController {
    private final static Logger logger = LoggerFactory.getLogger(RegInfoController.class);
    @Autowired
    private RegService regService;

    @ApiOperation(value = "备案信息", notes = "查询备案信息", httpMethod = "GET", tags = "备案信息查询接口")
    @GetMapping("/queryTable")
    @ResponseBody
    public String queryRegTablePage(@RequestParam(value = "pageSize",required = false) String pageSize,
                                                   @RequestParam(value = "pageNumber",required = false) String pageNumber ) {
//        logger.info("推送》》》》》》定时任务 开始执行 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
//            System.out.println(pageSize+"->"+pageNumber);
            QueryResult registrations = regService.selectRegistration(Integer.parseInt(pageSize),Integer.parseInt(pageNumber));
//            logger.info("推送》》》》》定时任务 结束执行 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return new Result(ResultCode.SUCCESS.toString(),ResultMsg.GET_FIND_SUCCESS,registrations,null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
        }
    }

    @ApiOperation(value = "备案信息", notes = "查询备案信息", httpMethod = "GET", tags = "备案信息查询接口")
    @GetMapping("/selectReg")
    @ResponseBody
    public String queryRegTableByParam(@RequestParam(value = "pageSize",required = false) String pageSize,
                                                      @RequestParam(value = "pageNumber",required = false) String pageNumber, @RequestParam(value = "city", required = false) String city,
                                                      @RequestParam(value = "block", required = false) String block,
                                                      @RequestParam(value = "proname", required = false) String proname,
                                                      @RequestParam(value = "buildunit", required = false) String buildunit,
                                                      @RequestParam(value = "recordnum", required = false) String recordnum) {
        if(proname.contains("%") || buildunit.contains("%") ||  recordnum.contains("%")){
            return new Result(ResultCode.ERROR.toString(),"输入错误",null,null).getJson();
        }
        try {
            QueryResult registrations = regService.selectRegistrationByParam(Integer.parseInt(pageNumber)+1,Integer.parseInt(pageSize),city, block, proname.trim(), buildunit.trim(), recordnum.trim());
            return new Result(ResultCode.SUCCESS.toString(),ResultMsg.GET_FIND_SUCCESS,registrations,null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
        }
    }

    @ApiOperation(value = "备案信息详情", notes = "查询备案信息详情", httpMethod = "GET", tags = "备案信息详情查询接口")
    @GetMapping("/queryRegDetailInfo")
    @ResponseBody
    public String queryRegDetailInfo(REG_Registration registration, @RequestParam(value = "pid", required = false) String pid) {
        try {
            REG_Registration registrations = regService.selectRegById(pid);
            return new Result(ResultCode.SUCCESS.toString(),ResultMsg.GET_FIND_SUCCESS,registrations,null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
        }
    }

    @ApiOperation(value = "备案信息详情", notes = "查询备案信息详情", httpMethod = "GET", tags = "备案信息详情查询接口")
    @GetMapping("/queryFactorDetailInfo")
    @ResponseBody
    public String queryFactorDetailInfo(REG_InfluencingFactor influencingFactor, @RequestParam(value = "pid", required = false) String pid) {
        try {
            List<FactorModel> list = new ArrayList<>();
//            System.out.println(recordnum+"+++++?");
            List<REG_InfluencingFactor> factors = regService.selcetFactorByPId(pid);
            if (factors!=null) {
                FactorModel factorModel1 = new FactorModel();
                StringBuffer sbm = new StringBuffer();
                StringBuffer sbf = new StringBuffer();
                sbf.append("废水<br/>");
                ArrayList sd=new ArrayList();
                for (REG_InfluencingFactor ifs : factors) {
                    List<REG_FactorMeasure> factorMeasures = regService.selectMeasureListByPolluteId(ifs.getId());
                    if (ifs.getFactor().contains("生活污水") || ifs.getFactor().contains("生产废水")) {
                        factorModel1.setId(ifs.getId());
                        sbf.append(ifs.getFactor() + "<br />");
                        factorModel1.setFactor(sbf.toString());
                        if (factorMeasures != null) {
                            for (REG_FactorMeasure fm : factorMeasures) {
                                sbm.append("<b>" + ifs.getFactor() + "</b><br />");
                                sbm.append(fm.getYnFlag() + ":<br />" + fm.getMeasure() + "<br />");
                                factorModel1.setMeasure(sbm.toString());
//                                System.out.println(sbm.toString());
                            }
                        }
                    }
                    else {
                            FactorModel factorModel = new FactorModel();
                            StringBuffer sb = new StringBuffer();
                            factorModel.setId(ifs.getId());
                            factorModel.setFactor(ifs.getFactor());
                            if (factorMeasures != null) {
                                for (REG_FactorMeasure fm : factorMeasures) {
                                    sb.append(fm.getYnFlag() + ":<br />" + fm.getMeasure() + "<br />");
                                }
//                                System.out.println(sb.toString());
                                factorModel.setMeasure(sb.toString());
                            }
                            list.add(factorModel);
                        }
                }
                if (factorModel1.getFactor()!=null){
                    list.add(factorModel1);
                }


            }
//            System.out.println(registrations.getProname()+"=>"+recordnum);
            return new Result(ResultCode.SUCCESS.toString(),ResultMsg.GET_FIND_SUCCESS,list,null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(),ResultMsg.GET_FIND_ERROR,null,null).getJson();
        }
    }
}
