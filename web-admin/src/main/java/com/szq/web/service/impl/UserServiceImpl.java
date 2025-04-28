package com.szq.web.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.mapper.UserMapper;
import com.szq.web.model.User;
import com.szq.web.service.UserService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;


    @Override
    public BaseResponse<Object> getUserList() {
        return BaseResponse.success(userMapper.selectList(null));
    }
}
