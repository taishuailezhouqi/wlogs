package com.szq.web.mapper.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
