package com.szq.web.service.web.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.mapper.web.ArticleCategoryRelMapper;
import com.szq.web.mapper.web.ArticleMapper;
import com.szq.web.mapper.web.ArticleTagRelMapper;
import com.szq.web.model.Article;
import com.szq.web.model.Category;
import com.szq.web.model.Tag;
import com.szq.web.service.web.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleTagRelMapper articleTagRelMapper;
    private final ArticleCategoryRelMapper articleCategoryRelMapper;

    /**
     * 分页查询
     */
    @Override
    public Page<Article> listArticle(Page<Article> page, Article article) {
        Page<Article> articlePage = baseMapper.selectPage(page, null);
        List<Article> records = articlePage.getRecords();
        records.forEach(record->{
            List<Tag> tags = articleTagRelMapper.selectTagByArticleId(record.getId());
            record.setTags(tags);
            Category category = articleCategoryRelMapper.selectCategoryByArticleId(record.getId());
            record.setCategory(category);
        });
        return articlePage;
    }
}
