package com.szq.web.controller;

import com.szq.web.exception.BizException;
import com.szq.web.utils.BaseResponse;
import com.szq.web.utils.Log;
import io.jsonwebtoken.JwtParser;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/admin/test")
public class TestController {


    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @PostMapping("/admin/update")
    @ApiOperation(value = "测试更新接口")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<Object> testUpdate() {
        Log.sdk.info("更新成功...");
        return BaseResponse.success();
    }

}
