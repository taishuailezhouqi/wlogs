package com.szq.web.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.mapper.AdminBlogMapper;
import com.szq.web.model.BlogSettings;
import com.szq.web.service.admin.AdminBlogSettingsService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBlogSettingsServiceImpl extends ServiceImpl<AdminBlogMapper, BlogSettings> implements AdminBlogSettingsService {


    /**
     * 更新或删除
     */
    @Override
    public BaseResponse<Object> updateOrInsert(BlogSettings blogSettings) {
        boolean saveOrUpdate;
        if (blogSettings.getId() == null) {
            saveOrUpdate = this.save(blogSettings);

        }else {
            saveOrUpdate = this.updateById(blogSettings);
        }

        return saveOrUpdate ? BaseResponse.success() : BaseResponse.fail();

    }
}