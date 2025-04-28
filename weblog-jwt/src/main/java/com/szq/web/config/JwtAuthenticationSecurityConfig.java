package com.szq.web.config;

import com.szq.web.filter.JwtAuthenticationFilter;
import com.szq.web.handler.RestAuthenticationFailureHandler;
import com.szq.web.handler.RestAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置自定义认证过滤器
 */
@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void configure(HttpSecurity builder) throws Exception {

        //使用自定义 JWT身份认证过滤器
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        //设置登录认证的成功和失败处理类
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        jwtAuthenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);

        // 直接使用 DaoAuthenticationProvider, 它是 Spring Security 提供的默认的身份验证提供者之一
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置 userDetailService，用于获取用户的详细信息
        provider.setUserDetailsService(userDetailsService);

        //设置加密算法
        provider.setPasswordEncoder(passwordEncoder);
        builder.authenticationProvider(provider);
        // 将这个过滤器添加到 UsernamePasswordAuthenticationFilter 之前执行
        builder.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
