package com.szq.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szq.web.model.User;
import com.szq.web.utils.BaseResponse;

public interface UserService extends IService<User> {

    BaseResponse<Object> getUserList();
}
