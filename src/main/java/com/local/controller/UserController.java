package com.local.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.local.cell.DataManager;
import com.local.cell.PeopleManager;
import com.local.cell.UnitManager;
import com.local.cell.UserManager;
import com.local.common.config.ConfigProperties;
import com.local.common.data.DatabaseTool;
import com.local.common.filter.FileUtil;
import com.local.entity.sys.*;
import com.local.model.ImgResult;
import com.local.model.RegCodeModel;
import com.local.service.*;
import com.local.util.*;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.dao.QueryResult;
import org.nutz.mvc.annotation.GET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private PeopleService peopleService;

    @Autowired
    private DataService dataService;

    @Autowired
    private ProcessService processService;
    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CodeService codeService;
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

    @Autowired
    private DataInfoService dataInfoService;


    @GetMapping("/api/user/systemUser")
    public String setSystemUser() throws Exception {
        SYS_USER user = new SYS_USER();
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setUserAccount("system");
        SYS_UNIT unit = unitService.selectUnitByName("单位");
        user.setUnitId(unit.getId());
        String password = RSAUtils.encrypt("916295778", RSAUtils.PUBLICKEY);
        user.setUserPassword(password);
        user.setRoles("1");
        user.setEnabled("0");
        user.setUnitName("单位");
        userService.insertUser(user);
        return user.getUserAccount();
    }

    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST", tags = "登录管理接口")
    @PostMapping("/web/user/login")
    public String Login(@Validated @RequestBody SYS_USER user) {
        try {
            SYS_USER searchUser = null;
            //如果不是免密登录
            if (StrUtils.isBlank(user.getToken())) {
                //查询用户名和密码是否匹配，密码需要先解密（前台），再加密（后台），然后匹配
                if ("system".equals(user.getUserAccount())) {
                    searchUser = userService.selectUserByName(user.getUserAccount());
                    if (searchUser != null) {
                        if (!user.getUserPassword().equals(RSAUtils.decrypt(searchUser.getUserPassword(), RSAUtils.PRIVATEKEY))) {
                            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGIN_ERROR, null, null).getJson();
                        }
                    } else {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGIN_ERROR, null, null).getJson();
                    }
                } else {
                    user.setUserPassword(MD5Utils.encryptPassword(user.getUserPassword()));
                    searchUser = userService.selectUserByModel(user);
                    if (searchUser == null) {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGIN_ERROR, null, null).getJson();
                    }
                }
            } else {
//                searchUser = RedisUtil.getUserByKey(user.getToken());
                if (StrUtils.isBlank(searchUser)) {
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
                }
            }
            //返回前台的对象
            HashMap<String, Object> token = new HashMap<>();
            searchUser = userService.selectUserByName(user.getUserAccount());
            String units=searchUser.getUnitId()+";";
            String unitst=unitService.selectUnitAndChildUnits(searchUser.getUnitId());
            if (units!=null){
                units=units+unitst;
                token.put("childUnit",units);
            }
            SYS_UNIT unit = unitService.selectUnitById(searchUser.getUnitId());
            if ("system".equals(user.getUserAccount())) {
                if (unit != null) {
                    token.put("unit", unit);
                    searchUser.setUnit(unit);
                }
                SYS_People people = peopleService.selectPeopleById(searchUser.getPeopleId());
                if (people != null) {
                    token.put("people", people);
                    searchUser.setPeople(people);
                }
                searchUser.setLastTime(new Date());
                userService.updateUser(searchUser);
                //前台去除密码
                searchUser.setUserPassword("");
                token.put("token", searchUser);
                logger.info(ResultMsg.LOGIN_SUCCESS);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.LOGIN_SUCCESS, token, null).getJson();
            } else {
                if (StrUtils.isBlank(unit.getRegCode())) {
                    String serial=SerialNumberUtil.getSerialNumber();
                    if ("0023_0356_3005_4E91D.".equals(serial) || "878B408FKLMU".equals(serial)){//管理员
                        if (unit != null) {
                            token.put("unit", unit);
                            searchUser.setUnit(unit);
                        }
                        SYS_People people = peopleService.selectPeopleById( searchUser.getPeopleId());
                        if (people != null) {
                            token.put("people", people);
                            searchUser.setPeople(people);
                        }
                        //查询菜单
//        searchUser=userService.selectRoleMenu(searchUser);
                        searchUser.setLastTime(new Date());
                        //将用户id放入redis
                        userService.updateUser(searchUser);
                        //前台去除密码
                        searchUser.setUserPassword("");
                        token.put("token", searchUser);
                        logger.info(ResultMsg.LOGIN_SUCCESS);
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.LOGIN_SUCCESS, token, null).getJson();
                    }else {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_NOREG, null, null).getJson();
                    }
                } else {
                    String regCodeStr = RSAModelUtils.decryptByPrivateKey(unit.getRegCode().trim(), RSAModelUtils.moduleA, RSAModelUtils.privateKeyA);
                    String unitId = regCodeStr.split(";")[0];
                    if (unitId.equals(unit.getId())) {
                        //                System.out.println(regCodeStr.split(";")[0]+"=>"+regCodeStr.split(";")[1]);
                        Date date = DateUtil.addMonths(DateUtil.stringToDate(regCodeStr.split(";")[1]), 1);
                        if (date.compareTo(new Date()) < 0) {
                            return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_OUT, null, null).getJson();
                        }
                        if (unit != null) {
                            token.put("unit", unit);
                            searchUser.setUnit(unit);
                        }
                        SYS_People people = peopleService.selectPeopleById( searchUser.getPeopleId());
                        if (people != null) {
                            token.put("people", people);
                            searchUser.setPeople(people);
                        }
                        //查询菜单
//        searchUser=userService.selectRoleMenu(searchUser);
                        searchUser.setLastTime(new Date());
                        //将用户id放入redis
                        userService.updateUser(searchUser);
                        //前台去除密码
                        searchUser.setUserPassword("");
                        token.put("token", searchUser);
                        logger.info(ResultMsg.LOGIN_SUCCESS);
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.LOGIN_SUCCESS, token, null).getJson();
                    } else {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_NOREG, null, null).getJson();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(ResultMsg.ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGIN_ERROR, e, null).getJson();
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping(value = "/api/user/info")
    public String getUserInfo(HttpServletRequest request) {
        //通过token获取中取出当前登录
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (user == null) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        } else {
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, user, null).getJson();
        }
    }


    @ApiOperation(value = "修改密码", notes = "修改密码\"", httpMethod = "GET", tags = "修改密码接口")
    @GetMapping("/api/user/updatePass")
    public String updatePass(@RequestParam(value = "oldPass", required = true) String oldPass,
                             @RequestParam(value = "newPass", required = true) String newPass, HttpServletRequest request) {
        //从请求的header中取出当前登录的登录
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (user != null) {
            String passStr = MD5Utils.encryptPassword(oldPass);
            if (passStr.equals(user.getUserPassword())) {
                user.setUserPassword(MD5Utils.encryptPassword(newPass));
                userService.updateUser(user);
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.LOGIN_ERROR_PASS, user, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_SUCCESS, null, null).getJson();
            }
        } else {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "用户信息", notes = "用户信息", httpMethod = "GET", tags = "用户信息接口")
    @GetMapping("/api/user/account")
    @ResponseBody
    public String getPeoples(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber,
                             @RequestParam(value = "unitId", required = false) String unitId,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "enabled", required = false) String enabled, HttpServletRequest request) {
        try {
            if (StrUtils.isBlank(unitId)) {
                SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
                if (user != null) {
                    unitId = user.getUnitId();
                }
            }
            QueryResult queryResult = userService.selectUsersByUnitId(Integer.parseInt(pageSize), Integer.parseInt(pageNumber), unitId, name, enabled);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "新增用户", notes = "新增用户", httpMethod = "POST", tags = "新增用户接口")
    @PostMapping(value = "/api/user/add")
    @ResponseBody
    public String createUser(@Validated @RequestBody SYS_USER user) {
        try {
            if ("system".equals(user.getUserAccount())) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_EXIST, null, null).getJson();
            }
            SYS_USER unitbyname = userService.selectUserByName(user.getUserAccount());
            if (unitbyname != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_EXIST, null, null).getJson();
            }
            SYS_People people = peopleService.selectPeopleById(user.getPeopleId());
            if (people != null) {
                user.setPeople(people);
                user.setPeopleName(people.getName());
            }
            SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
            if (unit != null) {
                user.setUnit(unit);
                user.setUnitName(unit.getName());
            }
            user.setUserPassword(MD5Utils.encryptPassword(user.getUserPassword()));
            String uuid = UUID.randomUUID().toString();
            user.setId(uuid);
            user.setEnabled("0");
            userService.insertUser(user);
            if ("1".equals(user.getRoles())){
            unit.setApprovalFlag("0");
            unit.setIsEdit("1");
            unitService.updateUnit(unit);
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, user, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改用户", notes = "修改用户", httpMethod = "POST", tags = "修改用户接口")
    @PostMapping(value = "/api/user/update")
    @ResponseBody
    public String updateUser(@Validated @RequestBody SYS_USER user) {
        try {
            if ("system".equals(user.getUserAccount())) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.PRIV_ERROR, null, null).getJson();
            }
            SYS_USER unitbyname = userService.selectUserByNameNotId(user.getUserAccount(), user.getId());
            if (unitbyname != null) {
                return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_EXIST, null, null).getJson();
            }
            SYS_People people = peopleService.selectPeopleById(user.getPeopleId());
            if (people != null) {
                user.setPeople(people);
                user.setPeopleName(people.getName());
            }
            user.setUserPassword(MD5Utils.encryptPassword(user.getUserPassword()));
            userService.updateUser(user);
            SYS_UNIT unit = unitService.selectUnitById(user.getUnitId());
            if ("1".equals(user.getRoles())){
                unit.setApprovalFlag("0");
                unit.setIsEdit("1");
                unitService.updateUnit(unit);
            }
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, user, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "删除用户", notes = "删除用户", httpMethod = "POST", tags = "删除用户接口")
    @PostMapping(value = "/api/user/delete")
    @ResponseBody
    public String deleteUser(@RequestParam(value = "id", required = true) String id, HttpServletRequest request) {
        try {
            SYS_USER user = userService.selectUserById(id);
            SYS_USER toneknUser = UserManager.getUserToken(request, userService, unitService, peopleService);
            if (id.equals(toneknUser.getId())) {
                return new Result(ResultCode.ERROR.toString(), "不可删除自己！", null, null).getJson();
            }
            userService.deleteUser(id);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, null, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    /**
     * /api/user/getReg
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "延期注册码", notes = "延期注册码", httpMethod = "POST", tags = "延期注册码接口")
    @RequestMapping(value = "/api/user/getReg")
    public String getRegInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "regDateStr", required = false) String regDateStr, @RequestParam(value = "unitId", required = false) String unitId) {
        try {
            List<RegCodeModel> codeModelList = new ArrayList<>();
            List<SYS_UNIT> unitList = new ArrayList<>();
            SYS_UNIT unitc = unitService.selectUnitById(unitId);
            if (unitc != null) {
                unitList.add(unitc);
                List<SYS_UNIT> allunits = unitService.selectAllChildUnits(unitId);
                if (allunits.size() > 0) {
                    unitList.addAll(allunits);
                }
                if (unitList.size() > 0) {
                    if (regDateStr != null) {
                        Date regDate = DateUtil.stringToDate(regDateStr);
                        for (SYS_UNIT unit : unitList) {
                            String regStr = unit.getId() + ";" + DateUtil.dateToString(regDate);
                            String regCode = RSAModelUtils.encryptByPublicKey(regStr, RSAModelUtils.moduleA, RSAModelUtils.puclicKeyA);
//                                String regCodeP=RSAModelUtils.decryptByPrivateKey(regCode, RSAModelUtils.moduleA ,RSAModelUtils.privateKeyA);
                            RegCodeModel codeModel = new RegCodeModel();
                            codeModel.setId(unit.getId());
                            codeModel.setName(unit.getName());
                            codeModel.setCode(regCode);
                            codeModelList.add(codeModel);
                        }
                        ClassPathResource resource = new ClassPathResource("exportExcel/exportRegCode.xlsx");
                        String path = resource.getFile().getPath();
                        String[] arr = {"id", "name", "code"};
                        Workbook temp = ExcelFileGenerator.getTeplet(path);
                        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
                        excelFileGenerator.setExcleNAME(response, "导出注册码.xlsx");
                        excelFileGenerator.createExcelFile(temp.getSheet("注册码"), 2, codeModelList, arr);
                        temp.write(response.getOutputStream());
                        temp.close();
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, allunits, null).getJson();
                    } else {
                        return new Result(ResultCode.ERROR.toString(), "参数出错", null, null).getJson();
                    }
                } else {
                    return new Result(ResultCode.ERROR.toString(), "数据出错", null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), "无此权限", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = " 首次注册码", notes = "首次注册码", httpMethod = "POST", tags = "首次注册码接口")
    @RequestMapping(value = "/api/user/getFirstReg")
    public String getFirstRegInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "regDateStr", required = false) String regDateStr, @RequestParam(value = "unitId", required = false) String unitId) {
        try {
            List<RegCodeModel> codeModelList = new ArrayList<>();
            List<SYS_UNIT> unitList = new ArrayList<>();
            SYS_UNIT unitc = unitService.selectUnitById(unitId);
            if (unitc != null) {
                unitList.add(unitc);
                List<SYS_UNIT> allunits = unitService.selectAllChildUnits(unitId);
                if (allunits.size() > 0) {
                    allunits.add(unitc);
                }
                if (unitList.size() > 0) {
                    if (regDateStr != null) {
                        Date regDate = DateUtil.stringToDate(regDateStr);
                        for (SYS_UNIT unit : unitList) {
                            List<SYS_UNIT> units = new ArrayList<>();
                            List<SYS_USER> users = new ArrayList<>();
                            List<SYS_UNIT> parentUnits = unitService.selectAllParentUnits(unit);
                            if (parentUnits.size() > 0) {
                                for (SYS_UNIT parentUnit : parentUnits) {
                                    String regParentStr = parentUnit.getId() + ";" + DateUtil.dateToString(regDate);
                                    String regParentEncrypt = RSAModelUtils.encryptByPublicKey(regParentStr, RSAModelUtils.moduleA, RSAModelUtils.puclicKeyA);
                                    unit.setRegCode(regParentEncrypt);
                                    units.add(parentUnit);
                                }
                            }
                            String regStr = unit.getId() + ";" + DateUtil.dateToString(regDate);
                            String regEncrypt = RSAModelUtils.encryptByPublicKey(regStr, RSAModelUtils.moduleA, RSAModelUtils.puclicKeyA);
                            unit.setRegCode(regEncrypt);
                            units.add(unit);
                            List<SYS_USER> userList = userService.selectUsersByUnitId(unit.getId());
                            if (userList != null) {
                                users.addAll(userList);
                            }
                            List<SYS_UNIT> childUnits = unitService.selectAllChildUnits(unit.getId());
                            if (childUnits.size() > 0) {
                                for (SYS_UNIT childUnit : childUnits) {
                                    String regChildStr = childUnit.getId() + ";" + DateUtil.dateToString(regDate);
                                    String regChildEncrypt = RSAModelUtils.encryptByPublicKey(regStr, RSAModelUtils.moduleA, RSAModelUtils.puclicKeyA);
                                    unit.setRegCode(regEncrypt);
                                    units.add(childUnit);
                                    List<SYS_USER> userChildList = userService.selectUsersByUnitId(childUnit.getId());
                                    if (userChildList != null) {
                                        users.addAll(userChildList);
                                    }
                                }
                            }
                            Map<String, Object> paramsMap = new HashMap<>();
                            Map<String, Object> resultMap = new HashMap<>();
                            JSONArray unitArray = JSONArray.fromObject(units);
                            paramsMap.put("unitList", unitArray);
                            JSONArray userArray = JSONArray.fromObject(users);
                            paramsMap.put("userList", userArray);
                            JSONObject resultList = JSONObject.fromObject(paramsMap);
                            resultMap.put("result", resultList);
                            resultMap.put("unitId", unit.getId());
                            JSONObject resultJson = JSONObject.fromObject(resultMap);
                            String regCode = RSAModelUtils.encryptByPublicKey(resultJson.toString(), RSAModelUtils.moduleA, RSAModelUtils.puclicKeyA);
//                                String regCodeP=RSAModelUtils.decryptByPrivateKey(regCode, RSAModelUtils.moduleA ,RSAModelUtils.privateKeyA);
                            if (regCode.length()>3000){
                              List<String> stringList=StrUtils.getStrList(regCode,3000);
                              for (String str:stringList){
                                  RegCodeModel codeModel = new RegCodeModel();
                                  codeModel.setId(unit.getId());
                                  codeModel.setName(unit.getName());
                                  codeModel.setCode(str);
                                  codeModelList.add(codeModel);
                              }
                            }
                        }
                        ClassPathResource resource = new ClassPathResource("exportExcel/exportRegCode.xlsx");
                        String path = resource.getFile().getPath();
                        String[] arr = {"id", "name", "code"};
                        Workbook temp = ExcelFileGenerator.getTeplet(path);
                        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
                        excelFileGenerator.setExcleNAME(response, "导出注册码.xls");
                        excelFileGenerator.createExcelFile(temp.getSheet("注册码"), 2, codeModelList, arr);
                        temp.write(response.getOutputStream());
                        temp.close();
                        return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_EXCEL_SUCCESS, allunits, null).getJson();
                    } else {
                        return new Result(ResultCode.ERROR.toString(), "参数出错", null, null).getJson();
                    }
                } else {
                    return new Result(ResultCode.ERROR.toString(), "数据出错", null, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), "无此权限", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "系统注册", notes = "系统注册", httpMethod = "POST", tags = "系统注册接口")
    @PostMapping(value = "/web/user/reg")
    @ResponseBody
    public String submitReg(@RequestParam(value = "regName", required = false) String regName, @RequestParam(value = "regPassword", required = false) String regPassword, @RequestParam(value = "regCode", required = false) String regCode,
                            @RequestParam(value = "flag", required = false) String flag) {
        try {
            if (!StrUtils.isBlank(regCode)) {
                if ("延期".equals(flag)) {//延期申请
                    String regCodeStr = RSAModelUtils.decryptByPrivateKey(regCode.trim(), RSAModelUtils.moduleA, RSAModelUtils.privateKeyA);
//                   System.out.println(regCodeStr.split(";")[0]+"=>"+regCodeStr.split(";")[1]);
                    SYS_UNIT unit = unitService.selectUnitById(regCodeStr.split(";")[0]);
                    SYS_USER user = userService.selectUserByName(regName);
                    if (user != null) {
                        user.setUserPassword(MD5Utils.encryptPassword(regPassword));
                        SYS_USER searchUser = userService.selectUserByModel(user);
                        if (searchUser != null) {
                            if (unit != null) {
                                unit.setRegCode(regCode.trim());
                                unitService.updateUnit(unit);
                            }
                            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.REGISTER_SUCCESS, unit, null).getJson();
                        } else {
                            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGIN_ERROR, null, null).getJson();
                        }
                    } else {
                        return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGIN_ERROR, null, null).getJson();
                    }
                } else {
                    String jsonStr = RSAModelUtils.decryptByPrivateKey(regCode.trim(), RSAModelUtils.moduleA, RSAModelUtils.privateKeyA);
                    JSONObject object = JSONObject.fromObject(jsonStr);
                    String unitId = String.valueOf(object.get("unitId"));
                    if (!StrUtils.isBlank(unitId)) {
                        SYS_UNIT unit = unitService.selectUnitById(unitId);
                        if (unit != null) {
                            return new Result(ResultCode.ERROR.toString(), "注册码错误!", null, null).getJson();
                        } else {
                            JSONObject key = object.getJSONObject("result");
                            JSONArray unitList = key.getJSONArray("unitList");
                            if (unitList != null) {
                                List<SYS_UNIT> units = DataManager.saveUnitJsonModel(unitList);
                                if (units.size() > 0) {
                                    for (SYS_UNIT sys_unit : units) {
                                        SYS_UNIT unit1=unitService.selectUnitById(sys_unit.getId());
                                        if (unit1!=null){
                                            unitService.updateUnit(sys_unit);
                                        }else {
                                            unitService.insertUnit(sys_unit);
                                        }
                                    }
                                }
                            }
                            JSONArray userList = key.getJSONArray("userList");
                            if (userList != null) {
                                List<SYS_USER> users = DataManager.saveUserJsonModel(userList);
                                if (users.size() > 0) {
                                    for (SYS_USER sys_user : users) {
                                        userService.insertUser(sys_user);
                                    }
                                }
                            }
                        }
                    } else {
                        return new Result(ResultCode.ERROR.toString(), "注册码错误!", null, null).getJson();
                    }
                    return new Result(ResultCode.SUCCESS.toString(), ResultMsg.REGISTER_SUCCESS, unitId, null).getJson();
                }
            } else {
                return new Result(ResultCode.ERROR.toString(), "注册码为空!", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_EXCEL_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_EXCEL_ERROR, null, null).getJson();
        }
    }
    private final static Gson gson = new Gson();
    @ApiOperation(value = "首次注册导入", notes = "首次注册导入", httpMethod = "POST", tags = "首次注册导入接口")
    @RequestMapping(value = "/web/user/importFirstRegDatabase")
    public String importBackDatabase(@RequestParam(value = "excelFile", required = true) MultipartFile excelFile) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Object> objects = new ArrayList<>();
        try {
            List<SYS_UNIT> units = new ArrayList<>();
            List<SYS_USER> users = new ArrayList<>();
            List<Sys_Approal> approals = new ArrayList<>();
            List<Sys_Process> processes = new ArrayList<>();
            List<SYS_People> peoples = new ArrayList<>();
            List<SYS_Duty> duties = new ArrayList<>();
            List<SYS_Rank> ranks = new ArrayList<>();
            List<SYS_Reward> rewards = new ArrayList<>();
            List<SYS_Education> educations = new ArrayList<>();
            List<SYS_Assessment> assessments = new ArrayList<>();
            String jsonStrMw = FileUtil.readJsonFile(excelFile.getInputStream());
            byte[] decode = AESUtil.parseHexStr2Byte(jsonStrMw);
            byte[] decryptResult = AESUtil.decrypt(decode, AESUtil.privateKey);
            String jsonStr = new String(decryptResult, "UTF-8");
            JSONObject object = JSONObject.fromObject(jsonStr);
            String note = String.valueOf(object.get("note"));
            String dataId = String.valueOf(object.get("dataId"));
            String unitName = String.valueOf(object.get("unitName"));
            String dataType = String.valueOf(object.get("flag"));
            String unitId = String.valueOf(object.get("unitId"));
            if (!StrUtils.isBlank(note)) {
                JSONObject key = object.getJSONObject("result");
                JSONArray userList = new JSONArray();
                userList = key.getJSONArray("userList");
                users = DataManager.saveUserJsonModel(userList);
                boolean bil = false;
                List<SYS_USER> sys_users = userService.selectAllUsers();
                String userbeforeparam = "";
                if (sys_users != null) {
                    userbeforeparam = gson.toJson(sys_users);
                }
                SYS_Data data = DataManager.saveData(dataId, "", dataType, unitId, dataService);
                JSONArray peopleList = key.getJSONArray("peopleList");
                JSONArray rankList = key.getJSONArray("rankList");
                JSONArray dutyList = key.getJSONArray("dutyList");
                JSONArray educationList = key.getJSONArray("educationList");
                JSONArray rewardList = key.getJSONArray("rewardList");
                JSONArray assessmentList = key.getJSONArray("assessmentList");
                JSONArray unitList = key.getJSONArray("unitList");
                List<String> unitIds=new ArrayList<>();
                if (unitList != null) {
                    units = DataManager.saveUnitJsonModel(unitList);
                    if (units.size() > 0) {
                        for (SYS_UNIT sysUnit:units){
                            unitIds.add(sysUnit.getId());
                        }
                        String beforeparam = null;
                        List<SYS_UNIT> sys_units =unitService.selectUnitAll();
                        if (sys_units!=null){
                            beforeparam = gson.toJson(sys_units);
                        }
                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "unit", gson.toJson(units), beforeparam);
                        DataManager.saveBackUnitData(units, unitService, unitId);
                        objects.add(units);
                    }
                    DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "user", gson.toJson(users), userbeforeparam);
                    DataManager.saveUserData(users, userService, unitId);
                    objects.add(users);
                    JSONArray aprovalList = key.getJSONArray("approvalList");
                    approals = DataManager.saveApproalJsonModel(aprovalList);
                    if (approals.size() > 0) {
                        String beforeparam = null;
                        List<Sys_Approal> us = approvalService.selectApprovals(unitId);
                        if (us != null) {
                            beforeparam = gson.toJson(us);
                        }
                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "approval", gson.toJson(approals), beforeparam);
                        DataManager.saveApprovalData(approals, approvalService, unitId, unitService, "1");
                        objects.add(approals);
                    }
                    JSONArray processList  = key.getJSONArray("processList");
                    if (processes.size() > 0) {
                        String beforeparam = null;
                        List<Sys_Process> us = processService.selectProcesssByUnitIds(unitIds);
                        if (us != null) {
                            beforeparam = gson.toJson(us);
                        }
                        DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "process", gson.toJson(processes), null);
                        DataManager.saveBackProcessData(processes, processService);
                        objects.add(processes);
                    }
                    if (peopleList != null) {
                        peoples = DataManager.savePeopleJsonModel(peopleList);
                        if (peoples.size() > 0) {
                            String beforeparam = null;
                            List<SYS_People> us = peopleService.selectPeoplesByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "people", gson.toJson(peoples), beforeparam);
                            DataManager.saveBackPeopleData(peoples, peopleService);
                            objects.add(peoples);
                        }
                        duties = DataManager.saveDutyJsonModel(dutyList);
                        if (duties.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Duty> us = dutyService.selectDutysByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "duty", gson.toJson(duties), beforeparam);
                            DataManager.saveBackDutyData(duties, dutyService);
                            objects.add(duties);
                        }
                        ranks = DataManager.saveRankJsonModel(rankList);
                        if (ranks.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Rank> us = rankService.selectRanksByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "rank", gson.toJson(ranks), beforeparam);
                            objects.add(ranks);
                            DataManager.saveBackRankData(ranks, rankService);
                        }
                        rewards = DataManager.saveRewardJsonModel(rewardList);
                        if (rewards.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Reward> us = rewardService.selectRewardsByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "reward", gson.toJson(rewards), beforeparam);
                            DataManager.saveBackRewardData(rewards, rewardService);
                            objects.add(rewards);
                        }
                        educations = DataManager.saveEducationJsonModel(educationList);
                        if (educations.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Education> us = educationService.selectEducationsByUnitIds(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "education", gson.toJson(educations), beforeparam);
                            DataManager.saveBackEducationData(educations, educationService);
                            objects.add(educations);
                        }
                        assessments = DataManager.saveAssessmentJsonModel(assessmentList);
                        if (assessments.size() > 0) {
                            String beforeparam = null;
                            List<SYS_Assessment> us = assessmentService.selectAssessmentsByUnitId(unitIds);
                            if (us != null) {
                                beforeparam = gson.toJson(us);
                            }
                            DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "assessment", gson.toJson(assessments), beforeparam);
                            DataManager.saveBackAssessmentData(assessments, assessmentService);
                            objects.add(assessments);
                        }
                        JSONArray digestList = key.getJSONArray("digestList");
                        if (digestList!=null){
                            List<SYS_Digest> digests = DataManager.saveDigestJsonModel(digestList);
                            if (digests.size() > 0) {
                                String beforeparam = null;
                                List<SYS_Digest> us = dataService.selectDigestsByUnitId(unitId);
                                if (us != null) {
                                    beforeparam = gson.toJson(us);
                                }
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "digest", gson.toJson(digests), beforeparam);
                                DataManager.saveBackDigestData(digests, dataService);
                                objects.add(digests);
                            }
                        }
                        JSONArray messageList = key.getJSONArray("messageList");
                        if (messageList!=null){
                            List<SYS_Message> messages = DataManager.saveMessageJsonModel(messageList);
                            if (messages.size() > 0) {
                                String beforeparam = null;
                                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "digest", gson.toJson(messages), null);
                                DataManager.saveBackMessageData(messages, userService);
                                objects.add(messages);
                            }
                        }
                    }
                    Map<String, Object> map = new HashMap<>();
                }
                return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, objects, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "备份数据包不全！", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), e.toString(), null, null).getJson();
        }
    }

    @Autowired
    private ConfigProperties configProperties;

    @ApiOperation(value = "数据库备份", notes = "数据库备份", httpMethod = "GET", tags = "数据库备份接口")
    @GetMapping(value = "/api/user/backup")
    @ResponseBody
    public String exportDatabase() {
        try {
            String str = PeopleManager.getGetBatchNumber();
            boolean bs = DatabaseTool.exportDatabase("127.0.0.1", "3306", configProperties.getUsername(), configProperties.getPassword(),
                    "C:\\databaseBackup", str + ".sql", "officeDuty", configProperties.getDataFileUrl());
            if (bs) {
                return new Result(ResultCode.SUCCESS.toString(), "'数据库成功备份'", str + ".sql", null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "数据库备份失败", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), "数据库备份失败", null, null).getJson();
        }
    }

    @ApiOperation(value = "数据库还原", notes = "数据库还原", httpMethod = "GET", tags = "数据库还原接口")
    @PostMapping(value = "/api/user/importDatabase")
    @ResponseBody
    public String importDatabase(HttpServletResponse response, @RequestParam("excelFile") MultipartFile excelFile) {
        try {
            PrintWriter writer = response.getWriter();
            File f = null;
            if (excelFile.equals("") || excelFile.getSize() <= 0) {
                excelFile = null;
            } else {
                InputStream ins = excelFile.getInputStream();
                f = new File(excelFile.getOriginalFilename());
                FileUtil.inputStreamToFile(ins, f);
            }
            writer.flush();
            writer.close();
            File del = new File(f.toURI());
            boolean bs = DatabaseTool.importDatabase("127.0.0.1", "3306", configProperties.getUsername(), configProperties.getPassword(),
                    "C:\\databaseBackup", del.getPath(), "first");
            String path = del.getPath();
            del.delete();
            if (bs) {
                return new Result(ResultCode.SUCCESS.toString(), "'数据库成功备份'", path, null).getJson();
            } else {
                return new Result(ResultCode.ERROR.toString(), "数据库备份失败", null, null).getJson();
            }
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), "数据库备份失败", null, null).getJson();
        }
    }

    @ApiOperation(value = "用户信息", notes = "用户信息", httpMethod = "GET", tags = "用户信息接口")
    @GetMapping("/api/user/message")
    @ResponseBody
    public String getMessages(@RequestParam(value = "size", required = false) String pageSize,
                             @RequestParam(value = "page", required = false) String pageNumber) {
        try {
            QueryResult queryResult = userService.selectMessages(Integer.parseInt(pageSize), Integer.parseInt(pageNumber));
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, queryResult, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.LOGOUT_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "新增消息", notes = "新增消息", httpMethod = "POST", tags = "新增消息接口")
    @PostMapping(value = "/api/user/addMessage")
    @ResponseBody
    public String addMessage(@Validated @RequestBody SYS_Message message) {
        try {
            String uuid = UUID.randomUUID().toString();
            message.setId(uuid);
            userService.insertMessage(message);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, message, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改消息", notes = "修改消息", httpMethod = "POST", tags = "修改消息接口")
    @PostMapping(value = "/api/user/editMessage")
    @ResponseBody
    public String editMessage(@Validated @RequestBody SYS_Message message) {
        try {
            userService.updateMessage(message);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, message, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "删除消息", notes = "删除消息", httpMethod = "POST", tags = "删除消息接口")
    @PostMapping(value = "/api/user/delMessage")
    @ResponseBody
    public String delMessage(@RequestParam(value = "id", required = true) String id, HttpServletRequest request) {
        try {
            userService.deleteMessage(id);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, id, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

}
