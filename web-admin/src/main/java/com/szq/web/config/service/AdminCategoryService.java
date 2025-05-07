package com.szq.web.config.service;

import com.szq.web.model.Category;
import com.szq.web.utils.BaseResponse;

public interface AdminCategoryService {
    /**
     * 添加分类
     */
    BaseResponse<Object> addCategory(Category category);
}