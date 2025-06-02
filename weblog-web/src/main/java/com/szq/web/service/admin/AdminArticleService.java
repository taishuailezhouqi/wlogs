package com.szq.web.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szq.web.model.Article;
import com.szq.web.model.vo.DeleteArticleReqVO;
import com.szq.web.model.vo.FindArticleDetailRspVO;
import com.szq.web.model.vo.PublishArticleReqVO;
import com.szq.web.model.vo.UpdateArticleReqVO;
import com.szq.web.utils.BaseResponse;

import javax.validation.constraints.NotNull;

public interface AdminArticleService extends IService<Article> {
    /**
     * 新建文章
     */
    BaseResponse<Object> publishArticle(PublishArticleReqVO article);

    /**
     * 删除文章
     */
    BaseResponse<Object> delete(DeleteArticleReqVO vo);

    /**
     * 分页查询
     */
    Page<Article> pageList(Article article, Page<Article> page);

    /**
     * 文章详情
     */
    BaseResponse<Object> detail(Long id);

    /**
     * 修改文章
     */
    BaseResponse<Object> updateArticle(UpdateArticleReqVO vo);
}
