package com.szq.web.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szq.web.model.Article;
import com.szq.web.model.vo.PublishArticleReqVO;
import com.szq.web.utils.BaseResponse;

public interface AdminArticleService extends IService<Article> {
    /**
     * 新建文章
     */
    BaseResponse<Object> publishArticle(PublishArticleReqVO article);
}
