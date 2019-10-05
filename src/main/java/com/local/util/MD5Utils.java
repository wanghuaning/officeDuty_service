package com.local.util;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        return  DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("admin"));
    }
}
