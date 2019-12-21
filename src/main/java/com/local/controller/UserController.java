package com.local.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.local.cell.DataManager;
import com.local.cell.PeopleManager;
import com.local.cell.UnitManager;
import com.local.cell.UserManager;
import com.local.common.config.ConfigProperties;
import com.local.common.data.DatabaseTool;
import com.local.common.filter.FileUtil;
import com.local.entity.sys.SYS_Message;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.SYS_USER;
import com.local.model.ImgResult;
import com.local.model.RegCodeModel;
import com.local.service.PeopleService;
import com.local.service.UnitService;
import com.local.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private PeopleService peopleService;

    @GetMapping("/systemUser")
    public String setSystemUser() throws Exception {
        SYS_USER user = new SYS_USER();
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setUserAccount("system");
        SYS_UNIT unit = unitService.selectUnitByName("云南省昆明市");
        user.setUnitId(unit.getId());
        String password = RSAUtils.encrypt("916295778", RSAUtils.PUBLICKEY);
        user.setUserPassword(password);
        user.setRoles("1");
        user.setEnabled("0");
        user.setUnitName("云南省昆明市");
        userService.insertUser(user);
        return user.getUserAccount();
    }

    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST", tags = "登录管理接口")
    @PostMapping("/login")
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
                    return new Result(ResultCode.ERROR.toString(), ResultMsg.USER_NOREG, null, null).getJson();
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
    @GetMapping(value = "/info")
    public String getUserInfo(HttpServletRequest request) {
        //通过token获取中取出当前登录
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (user == null) {
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        } else {
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, user, null).getJson();
        }
    }

    /**
     * 获取验证码
     *
     * @param response
     * @return
     */
    @GetMapping(value = "/vCode")
    public String getCode(HttpServletResponse response) throws IOException {
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);//生产随机字符串
        String uuid = IdUtil.simpleUUID();
        //生产图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            ImgResult im = new ImgResult(Base64.encode(stream.toByteArray()), uuid);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.GET_FIND_SUCCESS, im, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.GET_FIND_ERROR, null, null).getJson();
        } finally {
            stream.close();
        }
    }


    @ApiOperation(value = "修改密码", notes = "修改密码\"", httpMethod = "GET", tags = "修改密码接口")
    @GetMapping("/updatePass")
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
    @GetMapping("/account")
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
    @PostMapping(value = "/add")
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
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS, user, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "修改用户", notes = "修改用户", httpMethod = "POST", tags = "修改用户接口")
    @PostMapping(value = "/update")
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
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.UPDATE_SUCCESS, user, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }

    @ApiOperation(value = "删除用户", notes = "删除用户", httpMethod = "POST", tags = "删除用户接口")
    @PostMapping(value = "/delete")
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
    @RequestMapping(value = "/getReg")
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
                        ClassPathResource resource = new ClassPathResource("exportExcel/exportRegCode.xls");
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

    @ApiOperation(value = " 首次注册码", notes = "首次注册码", httpMethod = "POST", tags = "首次注册码接口")
    @RequestMapping(value = "/getFirstReg")
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
                            RegCodeModel codeModel = new RegCodeModel();
                            codeModel.setId(unit.getId());
                            codeModel.setName(unit.getName());
                            codeModel.setCode(regCode);
                            codeModelList.add(codeModel);
                        }
                        ClassPathResource resource = new ClassPathResource("exportExcel/exportRegCode.xls");
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
    @PostMapping(value = "/reg")
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
                                        unitService.insertUnit(sys_unit);
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

    @Autowired
    private ConfigProperties configProperties;

    @ApiOperation(value = "数据库备份", notes = "数据库备份", httpMethod = "GET", tags = "数据库备份接口")
    @GetMapping(value = "/backup")
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
    @PostMapping(value = "/importDatabase")
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
    @GetMapping("/message")
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
    @PostMapping(value = "/addMessage")
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
    @PostMapping(value = "/editMessage")
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
    @PostMapping(value = "/delMessage")
    @ResponseBody
    public String delMessage(@RequestParam(value = "id", required = true) String id, HttpServletRequest request) {
        try {
            userService.deleteUser(id);
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.DEL_SUCCESS, null, null).getJson();
        } catch (Exception e) {
            logger.error(ResultMsg.GET_FIND_ERROR, e);
            return new Result(ResultCode.ERROR.toString(), ResultMsg.UPDATE_ERROR, null, null).getJson();
        }
    }
}
