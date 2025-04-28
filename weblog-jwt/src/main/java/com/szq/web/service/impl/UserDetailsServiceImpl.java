package com.szq.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.szq.web.mapper.UserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsMapper userDetailsMapper;

    /**
     * 根据用户名加载用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 从数据库中查询
        com.szq.web.model.User user = userDetailsMapper.selectOne(new LambdaUpdateWrapper<com.szq.web.model.User>()
                .eq(com.szq.web.model.User::getUsername, username));
        if (user == null){
            throw new UsernameNotFoundException("该用户不存在");
        }

        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("admin")
                .build();
    }
}
