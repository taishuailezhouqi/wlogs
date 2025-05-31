package com.szq.web.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szq.web.model.Category;
import com.szq.web.model.Tag;
import com.szq.web.utils.BaseResponse;

import java.util.List;

public interface TagService extends IService<Tag> {

    /**
     * 分页带条件查询
     */
    Page<Tag> list(Tag tag, Page<Tag> tagPage);

    /**
     * 添加标签
     */
    BaseResponse<Object> add(List<String> tag);
}
