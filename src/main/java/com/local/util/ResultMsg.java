package com.local.util;
/**
 *
 *  返回提示信息
 *
 */
public class ResultMsg {


    /**
     * 返回结果提示语
     */
    public static final String LOGIN_NOTNULL = "用户名/密码为空";
    public static final String LOGIN_ERROR = "用户名/密码错误";
    public static final String LOGIN_USERNOTEXIST = "用户不存在";
    public static final String PASSWORD_NOTNULL = "密码为空";
    public static final String LOGIN_ERROR_PASS = "原密码错误";
    public static final String LOGIN_ERROR_EXP = "账号已过期";
    public static final String LOGIN_ERROR_STOP = "账号已停用";
    public static final String LOGIN_ERROR_CONFIG = "账号待审核";
    public static final String LOGIN_ERROR_DEL = "账号已删除";
    public static final String LOGOUT_ERROR = "账号未登录";
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGOUT_SUCCESS = "注销成功";
    public static final String UPDATE_SUCCESS = "更新成功";
    public static final String UPDATE_ERROR = "更新失败";
    public static final String PARAMS_ERROR = "参数错误";
    public static final String PARAMS_DUPLICATION = "参数重复";
    public static final String MOBILE_ERROR = "该手机号已存在";
    public static final String COMPANY_ERROR = "该企业已注册";
    public static final String REGISTER_ERROR = "注册失败，请重新注册";
    public static final String REGISTER_SUCCESS = "注册成功，请登录";
    public static final String USER_EXIST = "账号已存在";
    public static final String USER_NOREG = "账号未注册";
    public static final String USER_OUT = "账号已过期";
    public static final String COMPANY_TYPE_ERROR = "该企业类型已存在";
    public static final String UNIT_NAME_ERROE="该单位名已存在";
    public static final String UNIT_CODE_ERROE="该编码已存在";

    public static final String PRIV_ERROR = "暂无访问权限";
    public static final String CHOOSE_ERROR = "请选择系统";

    public static final String GET_FIND_SUCCESS = "查询成功";
    public static final String GET_FIND_ERROR = "查询失败";

    public static final String CHECK_SUCCESS = "验证码校验成功";
    public static final String CHECK_ERROR = "验证码校验失败";
    public static final String CHECK_EXPIRE = "验证码过期失效，请刷新页面";

    public static final String GET_LOGOUT_SUCCESS = "退出成功";
    public static final String GET_LOGOUT_ERROR = "退出失败";

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String ADD_SUCCESS = "保存成功";
    public static final String ADD_ERROR = "保存失败";
    public static final String DEL_SUCCESS = "删除成功";
    public static final String DEL_ERROR = "删除失败";
    public static final String RESET_ERROR = "重置失败";
    public static final String UPDATEPASS_SUCCESS = "修改密码成功";

    public static final String COMPANY_TYPE_DEL_ERROR = "该企业类型下有企业信息，不能够进行删除";

    public static final String GET_ERROR = "获取失败";

    public static final String LOGIN_IP_NOT = "当前IP禁止登录";

    public static final String EXP_SUCCESS = "调出成功";
    public static final String EXP_FAIL = "调出失败";
    public static final String EXP_NOT = "非调出人员";

    public static final String COMPANY_DEL_ERROR = "该企业下有其他人员账号，不能注销企业管理员账号";

    public static final String GET_EXCEL_SUCCESS = "导出成功";
    public static final String GET_EXCEL_ERROR = "导出失败";
    public static final String IMPORT_EXCEL_SUCCESS = "导出成功";
    public static final String IMPORT_EXCEL_ERROR = "导出失败";
    public static final String IMPORT_EXCEL_FILE_ERROR = "采集表格式错误";

    public static final String FILE_ERROR = "文件格式错误";

    public static final String PEOPLE_IDCARD_ERROE="身份证已存在";
    public static final String PEOPLE_DUTY_ERROE="职务已存在";
    public static final String PEOPLE_RANK_ERROE="职级已存在";
    public static final String PEOPLE_EDUCATION_ERROE="学历已存在";
    public static final String PEOPLE_REWARD_ERROE="奖惩已存在";
    public static final String PEOPLE_ASSESSMENT_ERROE="当年已考核";
}
