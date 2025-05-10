package com.szq.web.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szq.web.mapper.CategoryMapper;
import com.szq.web.service.AdminCategoryService;
import com.szq.web.model.Category;
import com.szq.web.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private AdminCategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private List<ObjectMapper> objectMappers;

    /**
     * 添加分类
     */
    @PostMapping("/category/add")
    public BaseResponse<Object> addCategory(@RequestBody @Validated Category category) {
        return categoryService.addCategory(category);
    }

    /**
     * 分页查询分类
     */
    @PostMapping("/findCategoryPage")
    public BaseResponse<Object> findCategoryPage(@RequestBody Category category, Integer pageNum, Integer pageSize) {
        Page<Category> page = new Page<>(pageSize, pageNum);
        Page<Category> categoryPage =  categoryService.findCategoryPage(category,page);
        return BaseResponse.success(categoryPage);
    }




}
