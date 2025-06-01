package com.szq.web.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szq.web.model.Category;
import com.szq.web.utils.BaseResponse;

public interface AdminCategoryService extends IService<Category> {
    /**
     * 添加分类
     */
    BaseResponse<Object> addCategory(Category category);

    /**
     * 分页查询
     */
    Page<Category> findCategoryPage(Category category, Page<Category> page);
}