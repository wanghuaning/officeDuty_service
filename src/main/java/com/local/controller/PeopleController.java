package com.local.controller;

import com.local.cell.PeopleManager;
import com.local.cell.UserManager;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.SYS_USER;
import com.local.service.PeopleService;
import com.local.service.UnitService;
import com.local.service.UserService;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.dao.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/people")
@Component
public class PeopleController {
    private final static Logger logger = LoggerFactory.getLogger(PeopleController.class);

    @Autowired
    private PeopleService peopleService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "人员信息", notes = "人员信息", httpMethod = "GET", tags = "人员信息接口")
    @GetMapping("/info")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitId", required = false) String unitId,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "idcard", required = false) String idcard,
                             @RequestParam(value = "politicalStatus", required = false) String politicalStatus,
                             @RequestParam(value = "states", required = false) String states, HttpServletRequest request) {
        try {
            if (StrUtils.isBlank(unitId)) {
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    unitId = user.getUnitId();
                }
            }
            QueryResult queryResult = peopleService.selectPeoples(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unitId, name, idcard, politicalStatus, states);
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
            excelFileGenerator.createExcelFile(temp.getSheet("人员信息"), 2, peoples, arr);
            temp.write(response.getOutputStream());
            temp.close();
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, peoples, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
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
                             @RequestParam(value = "states", required = false) String states,HttpServletRequest request) {
        try {
            String[] arr;
            if (!"true".equals(isChild)) {
                //从请求的header中取出当前登录的登录
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    arr = new String[]{user.getUnitId()};
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }

            } else {
                if (!StrUtils.isBlank(childUnit)) {
                    childUnit = childUnit.substring(1, childUnit.length() - 1);
                    arr = childUnit.split(";");
                } else {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.UNIT_CODE_ERROE, null, null).getJson();
                }
            }
            List<SYS_People> peopleList =new ArrayList<>();
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitIds(arr, "在职");
            if (peoples != null) {
                for (SYS_People people : peoples) {
                    if (people.getBirthday() != null && people.getPosition() != null) {
                        int bmonth = DateUtil.getMonth(people.getBirthday());
                        int nmonth = DateUtil.getMonth(new Date());
                        int age =0;
                        if ("1".equals(states)){
                            Calendar ca = Calendar.getInstance();
                            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
                            DateUtil.getAgeByMonth(people.getBirthday(), ca);
                        }else  if ("2".equals(states)){
                            Calendar ca1 = Calendar.getInstance();
                            int month = ca1.get(Calendar.MONTH);
                            ca1.set(Calendar.MONTH, month + 1);
                            ca1.set(Calendar.DAY_OF_MONTH, ca1.getActualMaximum(Calendar.DAY_OF_MONTH));
                            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                            age = DateUtil.getAgeByMonth(people.getBirthday(), ca1);
                        }else  if ("3".equals(states)){
                            Calendar ca2 = Calendar.getInstance();
                            int month2 = ca2.get(Calendar.MONTH);
                            ca2.set(Calendar.MONTH, month2 + 2);
                            ca2.set(Calendar.DAY_OF_MONTH, ca2.getActualMaximum(Calendar.DAY_OF_MONTH));
                            age = DateUtil.getAgeByMonth(people.getBirthday(), ca2);
                        }else  if ("3".equals(states)){
                            Calendar ca3 = Calendar.getInstance();
                            int month3 = ca3.get(Calendar.MONTH);
                            ca3.set(Calendar.MONTH, month3 + 3);
                            ca3.set(Calendar.DAY_OF_MONTH, ca3.getActualMaximum(Calendar.DAY_OF_MONTH));
                            age = DateUtil.getAgeByMonth(people.getBirthday(), ca3);
                        }
                        if (people.getPosition().contains("县处级正职")) {
                            if (age == 60) {
                                peopleList.add(people);
                            }
                        } else if (people.getPosition().contains("县处级副职")) {
                            if (age == 60 && bmonth == nmonth) {
                                peopleList.add(people);
                            }
                        } else if (people.getPosition().contains("乡科级正职")) {
                            if (people.getSex().contains("男")) {
                                if (age == 60 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }

                            } else {
                                if (age == 55 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                            }
                        } else if (people.getPosition().contains("乡科级副职")) {
                            if (people.getSex().contains("男")) {
                                if (age == 60 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                            } else {
                                if (age == 55 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                            }
                        } else if (people.getPosition().contains("科员")) {
                            if (people.getSex().contains("男")) {
                                if (age == 60 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                            } else {
                                if (age == 55 && bmonth == nmonth) {
                                    peopleList.add(people);
                                }
                            }
                        } else {
                            if (age == 60 && bmonth == nmonth) {
                                peopleList.add(people);
                            }
                        }
                    }
                }
            }
//            QueryResult queryResult = peopleService.selectPeoples(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unitId, name, idcard, politicalStatus, states);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, null, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }
}
