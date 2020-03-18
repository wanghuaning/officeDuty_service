package com.local.controller;

import com.local.cell.DataManager;
import com.local.cell.PeopleManager;
import com.local.cell.UserManager;
import com.local.model.AssessmentModel;
import com.local.util.ZipUtil;
import com.local.entity.sys.*;
import com.local.service.*;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/people")
@Component
public class PeopleController {
    private final File jsonFile = File.createTempFile("downloadJson", ".json");//创建临时文件
    private final static Logger logger = LoggerFactory.getLogger(PeopleController.class);

    @Autowired
    private PeopleService peopleService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private UserService userService;
    @Autowired
    private RankService rankService;

    @Autowired
    private EducationService educationService;
    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private DutyService dutyService;
    @Autowired
    private RewardService rewardService;

    public PeopleController() throws IOException {
    }

    @ApiOperation(value = "人员信息", notes = "人员信息", httpMethod = "GET", tags = "人员信息接口")
    @GetMapping("/info")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitId", required = false) String unitId,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "idcard", required = false) String idcard,
                             @RequestParam(value = "politicalStatus", required = false) String politicalStatus,
                             @RequestParam(value = "states", required = false) String states,
                             @RequestParam(value = "detail", required = false) String detail,
                             @RequestParam(value = "unitName", required = false) String unitName, HttpServletRequest request) {
        try {
            if (StrUtils.isBlank(unitId)) {
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    unitId = user.getUnitId();
                }
            }
            if (!StrUtils.isBlank(unitName)){
                SYS_UNIT unit=unitService.selectUnitByName(unitName);
                if (unit!=null){
                    unitId=unit.getId();
                }
            }
            QueryResult queryResult = peopleService.selectPeoples(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unitId, name, idcard, politicalStatus, states,detail);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改人员", notes = "修改人员", httpMethod = "POST", tags = "修改人员接口")
    @PostMapping(value = "/edit")
    @ResponseBody
    public String updatePeople(@Validated @RequestBody SYS_People people) {
        try {
            SYS_People peopleById = peopleService.selectPeopleById(people.getId());
            if (peopleById != null) {
                SYS_People peopleByIdcardAndNotId = peopleService.selectPeopleByIdcardAndNotId(people.getIdcard(), peopleById.getId());
                if (peopleByIdcardAndNotId != null) {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_IDCARD_ERROE, null, null).getJson();
                }
                SYS_UNIT unit = unitService.selectUnitById(peopleById.getUnitId());
                if (unit != null) {
                    people.setUnitName(unit.getName());
                }
                people.setPeopleOrder(peopleById.getPeopleOrder());
                people.setUnitId(peopleById.getUnitId());
                peopleService.updatePeople(people);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, people, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "新增人员", notes = "新增人员", httpMethod = "POST", tags = "新增人员接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public String insertPeople(@Validated @RequestBody SYS_People people) {
        try {
            SYS_People peopleByIdcardAndUnitId = peopleService.selectPeopleByIdcardAndUnitId(people.getIdcard(), people.getUnitId());
            if (peopleByIdcardAndUnitId != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PEOPLE_IDCARD_ERROE, null, null).getJson();
            }
            String uuid = UUID.randomUUID().toString();
            people.setId(uuid);
            SYS_UNIT unit = unitService.selectUnitById(people.getUnitId());
            if (unit != null) {
                people.setUnitName(unit.getName());
            }
            peopleService.insertPeoples(people);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, people, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.ADD_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "删除人员", notes = "删除人员", httpMethod = "POST", tags = "删除人员接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deletePeople(@RequestParam(value = "id", required = false) String id) {
        try {
            if (StrUtils.isBlank(id)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
            } else {
                peopleService.deletePeople(id);
                List<SYS_USER> userList = userService.selectUsersByPeopleId(id);
                if (userList != null) {
                    for (SYS_USER user : userList) {
                        userService.deleteUser(user.getId());
                    }
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.DEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.DEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出人员信息", notes = "导出人员信息", httpMethod = "POST", tags = "导出人员信息接口")
    @RequestMapping(value = "/outExcel")
    public String getPeopleExcel(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "unitId", required = false) String unitId,
                                 @RequestParam(value = "isChild", required = false) String isChild) {
        try {
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unitId, isChild, "在职");
            ClassPathResource resource = new ClassPathResource("exportExcel/exportPeopleInfo.xls");
            String path = resource.getFile().getPath();
            String[] arr = {"name", "unitName", "birthday", "idcard", "sex", "birthplace", "nationality", "workday", "party", "partyTime", "secondParty", "thirdParty",
                    "position", "positionTime", "positionLevel", "positionLevelTime", "baseWorker", "politicalStatus", "createTime", "isEnable", "detail"};
            Workbook temp = ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response, "人员信息表导出.xls");
            excelFileGenerator.createExcelFile(temp.getSheet("人员信息"), 3, peoples, arr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, peoples, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
    private final String filePathStr="C:\\RM\\file\\json";
  @ApiOperation(value = "导出调出人员信息", notes = "导出调出人员信息", httpMethod = "POST", tags = "导出调出人员信息接口")
  @RequestMapping(value = "/outOutPeopleExcel")
  public String outOutPeopleExcel(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "unitId", required = false) String unitId,
                               @RequestParam(value = "peopleIds[]", required = false) String[] peopleIds) {
      try {
          if (StrUtils.isBlank(peopleIds)){
              return new Result(ResultCode.ERROR.toString(), ResultMsg.EXP_NOT, null, null).getJson();
          }
          SYS_UNIT unit=unitService.selectUnitById(unitId);
          List<Object> objects = new ArrayList<>();
          //从请求的header中取出当前登录的登录
          List<File> srcfile = new ArrayList<File>();
          for (String peopleId: peopleIds){
              List<SYS_People> peopleList = new ArrayList<>();
              SYS_People people = peopleService.selectPeopleById(peopleId);
              if (people != null) {
                  if (people.getStates().contains("调出")) {
                      Map<String, Object> paramsMap = new HashMap<>();
                      paramsMap.put("note", "调出");
                      paramsMap.put("dataId", peopleId + DateUtil.getDateNum(new Date()));
                      paramsMap.put("peopleId", peopleId);
                      paramsMap.put("idCard", people.getIdcard());
                      Map<String, Object> resultMap = new HashMap<>();
                      peopleList.add(people);
                      JSONArray peoples = JSONArray.fromObject(peopleList);
                      resultMap.put("peopleList", peoples);
                      objects.add(peoples);
                      List<SYS_Duty> dutyList = DataManager.getOutPeopleDutyJson(resultMap, people, dutyService);
                      objects.addAll(dutyList);
                      List<SYS_Rank> rankList = DataManager.getOutPeopleRankJson(resultMap, people, rankService);
                      objects.addAll(rankList);
                      List<SYS_Education> educationList = DataManager.getOutPeopleEducationJson(resultMap, people, educationService);
                      objects.addAll(educationList);
                      List<SYS_Reward> rewardList = DataManager.getOutPeopleRewardJson(resultMap, people, rewardService);
                      objects.addAll(rankList);
                      List<SYS_Assessment> assessmentList = DataManager.getOutPeopleAssessmentJson(resultMap, people, assessmentService);
                      objects.addAll(assessmentList);
                      JSONObject resultList = JSONObject.fromObject(resultMap);
                      paramsMap.put("result", resultList);
                      JSONObject resultJson = JSONObject.fromObject(paramsMap);
                      byte[] encode = AESUtil.encrypt(resultJson.toString(), AESUtil.privateKey);
                      String paramsCipher = AESUtil.parseByte2HexStr(encode);
                      ZipUtil.getFile(filePathStr);
                      String filePath=filePathStr+"\\"+people.getName()+"_调出信息.json";
                      File file = new File(filePath);
                      Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                      writer.write(paramsCipher);
                      srcfile.add(file);
                      writer.flush();
                      writer.close();
                  }
              } else {
                  return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
              }
          }
          ZipUtil.zipFiles(srcfile,response,"批量导出调出人员信息",request);
          return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, "ok!", null).getJson();
      } catch (Exception e) {
          logger.error(ResultMsg.GET_ERROR, e);
          return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
      } finally {
          ZipUtil.delFile(new File(filePathStr));
      }
  }
  /**
   * 已取消使用
   *
   * @param excelFile
   * @param fullImport
   * @return
   */
    @ApiOperation(value = "导入人员", notes = "导入人员", httpMethod = "POST", tags = "导入人员接口")
    @RequestMapping(value = "/import")
    public String importPeopleExcel(@RequestParam("excelFile") MultipartFile excelFile, @RequestParam(value = "fullImport", required = false) String fullImport) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            // TODO 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
            List<String> headList = ExcelFileGenerator.readeExcelHeader(excelFile.getInputStream(), 0, 1);
            if (headList.size() > 0) {
                if (!headList.get(0).contains("姓名") && !headList.get(3).contains("身份证号")) {
                    stringBuffer.append(ResultMsg.IMPORT_EXCEL_FILE_ERROR);
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.IMPORT_EXCEL_FILE_ERROR, null, null).getJson();
                } else {
                    List<Map<String, Object>> list = ExcelFileGenerator.readeExcelData(excelFile.getInputStream(), 0, 1, 2);
                    List<SYS_People> peopleList = PeopleManager.getPeopleDataByExcel(list, peopleService, stringBuffer, unitService, fullImport);
                    if (stringBuffer.length() > 0) {
                        return new Result(ResultCode.SUCCESS.toString(), stringBuffer.toString(), peopleList, null).getJson();
                    } else {
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.IMPORT_EXCEL_SUCCESS, peopleList, null).getJson();
                    }
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), stringBuffer.toString(), null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }

    @ApiOperation(value = "人员列表", notes = "人员列表", httpMethod = "GET", tags = "人员列表接口")
    @GetMapping(value = "/peoples")
    @ResponseBody
    public String selectPeople(@RequestParam(value = "unitId", required = false) String unitId) {
        try {
            if (StrUtils.isBlank(unitId)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            } else {
                List<SYS_People> peopleList = peopleService.selectPeoplesByUnitId(unitId, "0", "在职");
                if (peopleList != null) {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, peopleList, null).getJson();
                } else {
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, new ArrayList<SYS_People>(), null).getJson();
                }
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "到期退休", notes = "到期退休", httpMethod = "GET", tags = "到期退休接口")
    @GetMapping("/retireInfo")
    @ResponseBody
    public String getRetireInfo(@RequestParam(value = "size", required = false) String pageSize,
                                @RequestParam(value = "page", required = false) String pageNumber,
                                @RequestParam(value = "isChild", required = false) String isChild,
                                @RequestParam(value = "childUnit", required = false) String childUnit,
                                @RequestParam(value = "unitId", required = false) String unitId,
                                @RequestParam(value = "states", required = false) String states, HttpServletRequest request) {
        try {
            if (!"全部".equals(states)&& !StrUtils.isBlank(states)){
                int month=DateUtil.getMonth(new Date());
                int index=Integer.valueOf(states)-month+1;
                states=String.valueOf(index);
            }
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                arr = new String[]{unitId};
            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            Pager pager = new Pager();
            List<SYS_People> peopleList = new ArrayList<>();
            PeopleManager.getRetireInfoData(peopleList, arr, states,peopleService,unitService);
            pager.setPageNumber(Integer.parseInt(pageNumber)+1);
            pager.setPageSize(Integer.parseInt(pageSize));
            pager.setRecordCount(peopleList.size());
            QueryResult queryResult = new QueryResult(peopleList, pager);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出人员信息", notes = "导出人员信息", httpMethod = "POST", tags = "导出人员信息接口")
    @RequestMapping(value = "/outRetirExcel")
    public String outRetirePeopleExcel(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "isChild", required = false) String isChild,
                                       @RequestParam(value = "childUnit", required = false) String childUnit,
                                       @RequestParam(value = "unitId", required = false) String unitId,
                                       @RequestParam(value = "states", required = false) String states) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                arr = new String[]{unitId};
            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            List<SYS_People> peopleList = new ArrayList<>();
            PeopleManager.getRetireInfoData(peopleList, arr, states,peopleService,unitService);
            ClassPathResource resource = new ClassPathResource("exportExcel/exportRetirePeopleInfo.xls");
            String path = resource.getFile().getPath();
            String[] dataArr = {"name", "unitName", "idcard", "birthday", "retireDate", "sex", "nationality", "workday", "party",
                    "position", "positionLevel", "politicalStatus"};
            Workbook temp = ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.setExcleNAME(response, "到期退休人员信息表导出.xls");
            excelFileGenerator.createExcelFile(temp.getSheet("到期退休"), 2, peopleList, dataArr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, peopleList, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "人员详情", notes = "人员详情", httpMethod = "GET", tags = "人员详情接口")
    @GetMapping(value = "/peopleDetailInfo")
    @ResponseBody
    public String peopleDetailInfo(@RequestParam(value = "size", required = false) String pageSize,
                                   @RequestParam(value = "page", required = false) String pageNumber,
                                   @RequestParam(value = "isChild", required = false) String isChild,
                                   @RequestParam(value = "childUnit", required = false) String childUnit,
                                   @RequestParam(value = "unitId", required = false) String unitId,
                                   @RequestParam(value = "sex", required = false) String sex,
                                   @RequestParam(value = "party", required = false) String party,
                                   @RequestParam(value = "age", required = false) String age,
                                   @RequestParam(value = "duty", required = false) String duty,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "unitName", required = false) String unitName, HttpServletRequest request) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                arr = new String[]{unitId};
            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
            }
            QueryResult queryResult = peopleService.selectPeopleDetailInfo(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), arr, sex, party, age, duty,name,unitName);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "导出人员详情信息", notes = "导出人员详情信息", httpMethod = "POST", tags = "导出人员详情信息接口")
    @RequestMapping(value = "/outPeopleDetailExcel")
    public String outPeopleDetailExcel(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "isChild", required = false) String isChild,
                                       @RequestParam(value = "childUnit", required = false) String childUnit,
                                       @RequestParam(value = "unitId", required = false) String unitId,
                                       @RequestParam(value = "sex", required = false) String sex,
                                       @RequestParam(value = "age", required = false) String age,
                                       @RequestParam(value = "party", required = false) String party,
                                       @RequestParam(value = "duty", required = false) String duty) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                arr = new String[]{unitId};
            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
                }
            }
            List<SYS_People> peopleList = peopleService.selectPeopleDetailInfos(arr, sex, party, age, duty);
            if (peopleList == null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            ClassPathResource resource = new ClassPathResource("exportExcel/exportPeopleDetailInfo.xls");
            String path = resource.getFile().getPath();
            String[] dataArr = {"name", "unitName", "idcard", "birthday", "age", "sex", "nationality", "workday", "party",
                    "position", "positionLevel", "politicalStatus"};
            Workbook temp = ExcelFileGenerator.getTeplet(path);
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
            excelFileGenerator.createExcelFile(temp.getSheet("人员详情"), 2, peopleList, dataArr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, peopleList, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "人员信息", notes = "人员信息", httpMethod = "GET", tags = "人员信息接口")
    @GetMapping(value = "/peopleInfo")
    @ResponseBody
    public String getPeopleInfo(@RequestParam(value = "peopleId", required = false) String peopleId) {
        try {
            if (StrUtils.isBlank(peopleId)) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
            }
            SYS_People people = peopleService.selectPeopleById(peopleId);
            if (people != null) {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, people, null).getJson();
            } else {
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, new ArrayList<SYS_People>(), null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        }
    }
    @ApiOperation(value = "考核批量添加查询", notes = "考核批量添加查询", httpMethod = "GET", tags = "考核批量添加查询接口")
    @GetMapping("/addAssessmentInfo")
    @ResponseBody
    public String addAssessmentInfo(@RequestParam(value = "size", required = false) String pageSize,
                                    @RequestParam(value = "page", required = false) String pageNumber,
                                    @RequestParam(value = "unitId", required = false) String unitId,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "year", required = false) String year,
                                    @RequestParam(value = "unitName", required = false) String unitName, HttpServletRequest request) {
        try {
            if (StrUtils.isBlank(unitId)) {
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    unitId = user.getUnitId();
                }
            }
            if (!StrUtils.isBlank(unitName)){
                SYS_UNIT unit=unitService.selectUnitByName(unitName);
                if (unit!=null){
                    unitId=unit.getId();
                }
            }
            Pager pager = new Pager();
            pager.setPageNumber(Integer.valueOf(pageNumber) + 1);
            pager.setPageSize(Integer.valueOf(pageSize));
            if (StrUtils.isBlank(pager)) {
                pager = new Pager();
            }
            List<SYS_Assessment>  sys_assessmentList = assessmentService.selectAssessmentsByYears(unitId, year,name);
            if (sys_assessmentList!=null){
                List<AssessmentModel> modelList=PeopleManager.getAssessmentModel(sys_assessmentList);
                pager.setRecordCount(modelList.size());
                QueryResult queryResult = new QueryResult(modelList, pager);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            }else {
                QueryResult queryResult = new QueryResult(new ArrayList<AssessmentModel>(), pager);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
}
