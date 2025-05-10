package com.szq.web.controller.admin;

import com.szq.web.service.AdminUserService;
import com.szq.web.model.User;
import com.szq.web.model.vo.UpdateAdminUserPasswordReqVO;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;



    @PostMapping("/updatePassword")
    public BaseResponse<Object> updatePassword(@RequestBody @Validated  UpdateAdminUserPasswordReqVO reqVO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(reqVO.getPassword());
        reqVO.setPassword(encode);
        return adminUserService.updatePasswordByUsername(reqVO);
    }

    @GetMapping("/getUserLoginMessage")
    public BaseResponse<Object> getUserLoginMessage() {
        User user = adminUserService.getUserLoginMessage();
        return BaseResponse.success(user);
    }


}
