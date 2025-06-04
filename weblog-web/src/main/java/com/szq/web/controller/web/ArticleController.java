package com.szq.web.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szq.web.model.Article;
import com.szq.web.service.web.ArticleService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/list")
    public BaseResponse<Object> list(@RequestBody Article article) {
        Page<Article> page = new Page<>(article.getCurrent(), article.getSize());
        Page<Article> articlePage = articleService.listArticle(page,article);
        return BaseResponse.success(articlePage);
    }

}
