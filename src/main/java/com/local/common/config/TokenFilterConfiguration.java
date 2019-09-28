package com.local.common.config;

import com.local.common.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TokenFilterConfiguration {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TokenFilter tokenFilter = new TokenFilter();
        registrationBean.setFilter(tokenFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/base/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
