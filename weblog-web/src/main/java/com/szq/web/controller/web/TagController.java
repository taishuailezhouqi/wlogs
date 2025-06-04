package com.szq.web.controller.web;

import com.szq.web.mapper.admin.AdminTagMapper;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final AdminTagMapper adminTagMapper;

    @PostMapping("/list")
    public BaseResponse<Object> list() {
        return BaseResponse.success(adminTagMapper.selectList(null));
    }
}
