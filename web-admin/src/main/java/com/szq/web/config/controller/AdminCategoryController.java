package com.szq.web.config.controller;

import com.szq.web.config.service.AdminCategoryService;
import com.szq.web.model.Category;
import com.szq.web.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private AdminCategoryService categoryService;

    @PostMapping("/category/add")
    public BaseResponse<Object> addCategory(@RequestBody @Validated Category category) {
        return categoryService.addCategory(category);
    }


}
