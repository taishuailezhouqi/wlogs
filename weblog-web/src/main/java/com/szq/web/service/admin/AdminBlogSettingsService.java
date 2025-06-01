package com.szq.web.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szq.web.model.BlogSettings;
import com.szq.web.utils.BaseResponse;

public interface AdminBlogSettingsService extends IService<BlogSettings> {


    /**
     * 更新或删除
     */
    BaseResponse<Object> updateOrInsert(BlogSettings blogSettings);
}
