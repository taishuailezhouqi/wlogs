package com.szq.web.mapper.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.ArticleCategoryRel;
import com.szq.web.model.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleCategoryRelMapper extends BaseMapper<ArticleCategoryRel> {

    Category selectCategoryByArticleId(Long articleId);

}
