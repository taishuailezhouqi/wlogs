package com.szq.web.controller.admin;

import com.szq.web.model.BlogSettings;
import com.szq.web.service.admin.AdminBlogSettingsService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/blog/settings")
@RequiredArgsConstructor
public class AdminBlogSettingsController {

    private final AdminBlogSettingsService adminBlogSettingsService;


    /**
     * 修改或新增
     */
    @PostMapping("/update")
    public BaseResponse<Object> update(@RequestBody @Validated BlogSettings blogSettings) {
        return adminBlogSettingsService.updateOrInsert(blogSettings);
    }

    /**
     * 获取详情
     */
    @PostMapping("/detail")
    public BaseResponse<Object> detail() {
        BlogSettings blogSettings = adminBlogSettingsService.getById(1L);
        return BaseResponse.success(blogSettings);
    }



}
