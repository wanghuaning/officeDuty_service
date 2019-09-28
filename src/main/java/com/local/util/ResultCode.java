package com.local.util;
/**
 *
 * 返回码
 *
 */
public enum ResultCode {
    SUCCESS("200"),
    ERROR("500"),
    DAMS_ERROR("505"),
    PRIV_ERROR("506"),
    ERROR_USER_301("301"),  //用户未登录
    ERROR_USER_304("304"),  //选择系统
    ERROR_USER_305("305"),  //系统跳转
    ERROR_USER_402("402"),  //未授权
    ERROR_USER_502("502"),  //用户已经删除
    ERROR_USER_501("501");  //用户待审核

    private  String val;

    private ResultCode(String en){
        this.val=en;
    }
    @Override
    public String toString() {
        return this.val;
    }
}
