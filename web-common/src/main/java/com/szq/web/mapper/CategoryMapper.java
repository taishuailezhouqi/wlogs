package com.szq.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szq.web.model.Category;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper extends BaseMapper<Category> {


    Page<Category> findCategoryPage(@Param("category") Category category, Page<Category> page);
}