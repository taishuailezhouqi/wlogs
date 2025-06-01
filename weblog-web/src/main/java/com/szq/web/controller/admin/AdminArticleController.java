package com.szq.web.controller.admin;

import com.szq.web.model.Article;
import com.szq.web.model.vo.PublishArticleReqVO;
import com.szq.web.service.admin.AdminArticleService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
@RequiredArgsConstructor
public class AdminArticleController {

    private final AdminArticleService adminArticleService;

    /**
     * 新建文章
     */
    @PostMapping("/publish")
    public BaseResponse<Object> publishArticle(@RequestBody @Validated PublishArticleReqVO article) {
        return adminArticleService.publishArticle(article);
    }
}

























