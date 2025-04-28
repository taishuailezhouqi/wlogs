package com.szq.web.controller;

import com.szq.web.mapper.UserMapper;
import com.szq.web.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/test")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test2")
    public BaseResponse<Object> test1() {
        return BaseResponse.success(userMapper.selectByUsername("zhouqi"));
    }


}
