package com.szq.web.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szq.web.mapper.CategoryMapper;
import com.szq.web.service.admin.AdminCategoryService;
import com.szq.web.model.Category;
import com.szq.web.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
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
    @PostMapping("/add")
    public BaseResponse<Object> addCategory(@RequestBody @Validated Category category) {
        return categoryService.addCategory(category);
    }

    /**
     * 分页查询分类
     */
    @PostMapping("/list")
    public BaseResponse<Object> findCategoryPage(@RequestBody Category category) {
        Page<Category> page = new Page<>(category.getCurrent(), category.getSize());
        Page<Category> categoryPage =  categoryService.findCategoryPage(category,page);
        return BaseResponse.success(categoryPage);
    }


    /**
     * 删除分类
     */
    @PostMapping("/delete")
    public BaseResponse<Object> deleteCategory(@RequestBody Category category) {
        Long id = category.getId();
        if (id == null){
            return BaseResponse.fail("id不能为空");
        }
        Category category1 = categoryMapper.selectById(id);
        if (category1 == null){
            return BaseResponse.fail("分类不存在");
        }
        return categoryService.removeById(id) ? BaseResponse.success() : BaseResponse.fail();
    }


    /**
     * 查询所有分类
     */
    @GetMapping("/select/list")
    public BaseResponse<Object> selectList() {
        List<Category> list = categoryMapper.selectList(null);
        return BaseResponse.success(list);
    }



}
