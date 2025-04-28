package com.szq.web.handler;

import com.szq.web.exception.UsernameOrPasswordNullException;
import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import com.szq.web.utils.ResultUtil;
import com.szq.web.utils.ReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Log.sdk.warn("AuthenticationException: ", exception);
        if (exception instanceof UsernameOrPasswordNullException) {
            // 用户名或密码为空
            ResultUtil.fail(response, BaseResponse.fail(exception.getMessage()));
			return;
        } else if (exception instanceof BadCredentialsException) {
            // 用户名或密码错误
            ResultUtil.fail(response, BaseResponse.fail(ReturnType.USERNAME_OR_PWD_ERROR));
			return;
        }

        // 登录失败
        ResultUtil.fail(response, BaseResponse.fail(ReturnType.LOGIN_FAIL));
    }
}
