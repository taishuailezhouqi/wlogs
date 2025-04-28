package com.szq.web.controller;

import com.szq.web.service.UserService;
import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/getUserList")
    public BaseResponse<Object> getUserList(){
        Log.sdk.info("这个是中文日志");
        return userService.getUserList();
    }


}
