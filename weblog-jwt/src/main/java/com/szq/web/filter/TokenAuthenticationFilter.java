package com.szq.web.filter;

import com.szq.web.handler.RestAuthenticationEntryPoint;
import com.szq.web.helper.JwtTokenHelper;
import com.szq.web.utils.Log;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 校验token过滤器
 */

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头信息
        String header = request.getHeader(tokenHeaderKey);
        //获取token
        //判断请求头是否以Bearer 开头
        if (header != null && header.startsWith(tokenPrefix + " ")) {
            //截取token
            String token = header.substring(7);
            Log.sdk.info("登录token为：{}", token);

            if (StringUtils.isNotBlank(token)) {
                try {
                    //校验token是否可用
                    jwtTokenHelper.validateToken(token);
                } catch (SignatureException | MalformedJwtException | UnsupportedJwtException |
                         IllegalArgumentException e) {
                    // 抛出异常，统一让 AuthenticationEntryPoint 处理响应参数
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                    return;
                } catch (ExpiredJwtException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                    return;
                }

                //从token中解析出用户名
                String username = jwtTokenHelper.getUsernameByToken(token);
                //判断是否不为空并且之前是否没登陆过，也就是未登录的情况下
                if (StringUtils.isNotBlank(username)
                        && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //将用户信息存入authentication，方便后面校验
                    //userDetails：是你通过 UserDetailsService 加载出来的用户信息。
                    //null：代表密码，登录时用，现在已经认证成功，不需要再传。
                    //userDetails.getAuthorities()：表示当前用户拥有的角色权限
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //这一步将 HttpServletRequest 中的信息（如 IP、SessionId 等）加入 Authentication 对象中，用于后续判断和记录。
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //将信息存储threadLocal中，方便后续查看
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 继续执行写一个过滤器
        filterChain.doFilter(request, response);
    }
}















