package com.szq.web.config.service.impl;

import com.szq.web.config.service.AdminUserService;
import com.szq.web.mapper.UserMapper;
import com.szq.web.model.User;
import com.szq.web.model.vo.UpdateAdminUserPasswordReqVO;
import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.ReturnType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserMapper userMapper;


    @Override
    public User getUserLoginMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userMapper.selectByUsername(username);
    }

    @Override
    public BaseResponse<Object> updatePasswordByUsername(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        int i = userMapper.updatePasswordByUsername(updateAdminUserPasswordReqVO.getUsername(), updateAdminUserPasswordReqVO.getPassword());

        return i == 1 ? BaseResponse.success() : BaseResponse.fail(ReturnType.USERNAME_NOT_FOUND);
    }
}
