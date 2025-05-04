package com.szq.web.service;

import com.szq.web.mapper.UserMapper;
import com.szq.web.mapper.UserRoleMapper;
import com.szq.web.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    //查询用户是否存在
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.szq.web.model.User user = userMapper.selectByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("该用户不存在");
        }

        List<UserRole> userRoles = userRoleMapper.selectByUsername(username);
        String[] roleArr = null;
        if (!CollectionUtils.isEmpty(userRoles)){
            roleArr = userRoles.stream().map(UserRole::getRole).toArray(String[]::new);
        }


        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword()) //验证密码
                .authorities(roleArr)
                .build();
    }
}
