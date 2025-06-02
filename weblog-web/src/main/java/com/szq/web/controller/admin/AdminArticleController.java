package com.szq.web.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szq.web.model.Article;
import com.szq.web.model.vo.DeleteArticleReqVO;
import com.szq.web.model.vo.PublishArticleReqVO;
import com.szq.web.model.vo.UpdateArticleReqVO;
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

    /**
     * 删除文章
     */
    @PostMapping("/delete")
    public BaseResponse<Object> delete(@RequestBody @Validated DeleteArticleReqVO vo){
        return adminArticleService.delete(vo);
    }

    /**
     * 分页带条件查询
     */
    @PostMapping("/list")
    public BaseResponse<Object> list(@RequestBody Article article) {
        Page<Article> page = new Page<>(article.getCurrent(), article.getSize());
        Page<Article> pageList= adminArticleService.pageList(article,page);
        return BaseResponse.success(pageList);
    }


    /**
     * 查看详情文章
     */
    @PostMapping("/detail")
    public BaseResponse<Object> detail(@RequestBody @Validated DeleteArticleReqVO vo) {
        return adminArticleService.detail(vo.getId());
    }

    /**
     * 修改文章
     */
    @PostMapping("/update")
    public BaseResponse<Object> update(@RequestBody @Validated UpdateArticleReqVO vo){
        return adminArticleService.updateArticle(vo);
    }


}

























