package com.local.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * yml文件取值类
 */
@Data
@Component
public class ConfigProperties {

    @Value("${config.dataFile}")
    private String dataFileUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public String getDataFileUrl() {
        return dataFileUrl;
    }

    public void setDataFileUrl(String dataFileUrl) {
        this.dataFileUrl = dataFileUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
