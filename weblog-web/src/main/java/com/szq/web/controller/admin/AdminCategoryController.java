package com.szq.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szq.web.mapper.ArticleCategoryRelMapper;
import com.szq.web.mapper.CategoryMapper;
import com.szq.web.model.ArticleCategoryRel;
import com.szq.web.service.admin.AdminCategoryService;
import com.szq.web.model.Category;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService categoryService;

    private final CategoryMapper categoryMapper;

    private final ArticleCategoryRelMapper articleCategoryRelMapper;


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
        Long count = articleCategoryRelMapper.selectCount(new LambdaQueryWrapper<>(ArticleCategoryRel.class)
                .eq(ArticleCategoryRel::getCategoryId, id));
        if (count > 0){
            return BaseResponse.fail("分类还有关联不可以删除");
        }

        return categoryService.removeById(id) ? BaseResponse.success() : BaseResponse.fail();
    }


    /**
     * 查询所有分类
     */
    @PostMapping("/select/list")
    public BaseResponse<Object> selectList() {
        List<Category> list = categoryMapper.selectList(null);
        return BaseResponse.success(list);
    }



}
