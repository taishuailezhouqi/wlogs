package com.szq.web.config.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szq.web.utils.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ActionAop {


    @Pointcut("execution(* com.szq.*.controller..*.*(..))")
    public void action() {
    }

    @Before("action()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Log.sdk.info("authinfo:{}",request.getHeader("authinfo"));
        Log.sdk.info("request - {}", Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "action()")
    public void doAfterReturning(Object ret) throws JsonProcessingException {
        //这里不能直接返回，中文会出现乱码的情况，使用objectMapper进行转换
        if (ret == null) {
            Log.sdk.info("response - {}", "null");
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            // 将返回对象转换为 JSON 字符串，确保正确编码
            String responseStr = objectMapper.writeValueAsString(ret);
            Log.sdk.info("response - {}", responseStr);
        }
    }



}