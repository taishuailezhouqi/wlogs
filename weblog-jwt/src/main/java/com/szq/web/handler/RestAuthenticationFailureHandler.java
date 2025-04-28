package com.szq.web.handler;

import com.szq.web.exception.UsernameOrPasswordNullException;
import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import com.szq.web.utils.ReturnType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于处理身份验证成功后的逻辑
 */
@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //打印错误信息到日志
        Log.sdk.error(exception.getMessage());
        if (exception instanceof UsernameOrPasswordNullException){
            //用户名或密码为空
            BaseResponse.fail(response, BaseResponse.fail(ReturnType.USERNAME_OR_PWD_NULL));
            return;
        }else if (exception instanceof BadCredentialsException){
            //用户名或密码错误
            BaseResponse.fail(response, BaseResponse.fail(ReturnType.USERNAME_OR_PWD_ERROR));
        }
        // 登录失败
        BaseResponse.fail(response, BaseResponse.fail(ReturnType.LOGIN_FAIL));

    }
}
