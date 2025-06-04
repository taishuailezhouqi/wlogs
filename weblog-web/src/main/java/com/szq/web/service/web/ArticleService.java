package com.szq.web.service.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szq.web.model.Article;

public interface ArticleService extends IService<Article> {

    /**
     * 分页查询
     */
    Page<Article> listArticle(Page<Article> page, Article article);
}
