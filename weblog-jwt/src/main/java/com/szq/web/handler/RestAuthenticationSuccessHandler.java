package com.szq.web.handler;

import com.szq.web.helper.JwtTokenHelper;
import com.szq.web.model.vo.LoginRspVO;
import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于处理身份验证成功后的逻辑
 */
@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //从authentication获取userDetails实例，获取用户的用户名
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        //通过用户名生成token
        String token = jwtTokenHelper.generateToken(username);
        Log.sdk.info("用户认证成功，token为：{}", token);
        LoginRspVO loginRspVO = LoginRspVO.builder().token(token).build();

        BaseResponse.ok(response,BaseResponse.success(loginRspVO));
    }
}
