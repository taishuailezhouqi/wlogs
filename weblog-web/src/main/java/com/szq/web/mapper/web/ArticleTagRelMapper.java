package com.szq.web.mapper.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.ArticleTagRel;
import com.szq.web.model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleTagRelMapper extends BaseMapper<ArticleTagRel> {


    List<Tag> selectTagByArticleId(Long articleId);
}
