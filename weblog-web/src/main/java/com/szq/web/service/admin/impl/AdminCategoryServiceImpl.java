package com.szq.web.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.service.admin.AdminCategoryService;
import com.szq.web.mapper.admin.AdminCategoryMapper;
import com.szq.web.model.Category;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl extends ServiceImpl<AdminCategoryMapper,Category> implements AdminCategoryService {

    private final AdminCategoryMapper categoryMapper;

    /**
     * 分页查询
     */
    @Override
    public Page<Category> findCategoryPage(Category category, Page<Category> page) {
        return  categoryMapper.findCategoryPage(category,page);
//        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>().orderByDesc(Category::getId);
//        this.page(page,wrapper);
    }

    /**
     * 添加分类
     */
    @Override
    public BaseResponse<Object> addCategory(Category category) {
        //判断是否已有
        if (categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .eq(Category::getName,category.getName())) > 0){
            return BaseResponse.fail("已有分类");
        }
        int insert = categoryMapper.insert(category);
        return insert == 1 ? BaseResponse.success() : BaseResponse.fail();

    }
}
