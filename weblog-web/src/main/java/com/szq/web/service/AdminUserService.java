package com.szq.web.service;

import com.szq.web.model.User;
import com.szq.web.model.vo.UpdateAdminUserPasswordReqVO;
import com.szq.web.utils.BaseResponse;

public interface AdminUserService {

    /**
     * 修改密码
     */
    BaseResponse<Object> updatePasswordByUsername(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);

    /**
     * 获取用户登录状态
     */
    User getUserLoginMessage();
}
