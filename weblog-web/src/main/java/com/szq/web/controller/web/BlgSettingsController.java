package com.szq.web.controller.web;

import com.szq.web.mapper.admin.AdminBlogMapper;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/settings")
@RequiredArgsConstructor
public class BlgSettingsController {

    private final AdminBlogMapper adminBlogMapper;

    @PostMapping("/detail")
    public BaseResponse<Object> detail() {
        return BaseResponse.success(adminBlogMapper.selectList(null).get(0));
    }

}
