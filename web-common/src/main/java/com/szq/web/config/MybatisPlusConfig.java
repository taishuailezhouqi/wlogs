package com.szq.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.szq.web.mapper")
public class MybatisPlusConfig {
}