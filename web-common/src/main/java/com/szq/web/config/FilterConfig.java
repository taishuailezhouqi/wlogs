package com.szq.web.config;

import com.szq.web.config.filter.TimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TimeFilter> timeFilter() {
        FilterRegistrationBean<TimeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TimeFilter());
        registrationBean.addUrlPatterns("/*");  // 拦截所有请求
        return registrationBean;
    }
}