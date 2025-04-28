package com.szq.web.service;

import com.szq.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    //查询用户是否存在
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.szq.web.model.User user = userMapper.selectByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("该用户不存在");
        }

        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword()) //验证密码
//                .authorities("ADMIN")
                .build();
    }
}
