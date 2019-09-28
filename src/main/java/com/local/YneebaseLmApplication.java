package com.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching//启动类中添加缓存支持
@ServletComponentScan
@EnableTransactionManagement
public class YneebaseLmApplication {
    public static void main(String[] args) {
        SpringApplication.run(YneebaseLmApplication.class, args);
    }
}
