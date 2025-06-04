package com.szq.web.controller.web;

import com.szq.web.mapper.admin.AdminCategoryMapper;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final AdminCategoryMapper adminCategoryMapper;

    @PostMapping("/list")
    public BaseResponse<Object> list() {
        return BaseResponse.success(adminCategoryMapper.selectList(null));
    }
}
