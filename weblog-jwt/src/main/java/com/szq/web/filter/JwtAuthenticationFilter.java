package com.szq.web.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szq.web.exception.UsernameOrPasswordNullException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 用户处理jwt用户身份验证过程
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * 指定用户登录的访问地址
     */
    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        //解析提交的json数据 转换成树形json再获取
        JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
        JsonNode username = jsonNode.get("username");
        JsonNode password = jsonNode.get("password");

        //判断用户名和密码是否为空
        if (Objects.isNull(username) || Objects.isNull(password)
                || StringUtils.isBlank(username.asText()) || StringUtils.isBlank(password.asText())) {
            throw new UsernameOrPasswordNullException("用户名或密码不能为空");
        }
        String usernameText = username.textValue();
        String passwordText = password.textValue();

        //将用户名和密码封装到对象中校验
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usernameText, passwordText);


        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
