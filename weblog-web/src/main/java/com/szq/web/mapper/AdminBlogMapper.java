package com.szq.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.BlogSettings;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminBlogMapper extends BaseMapper<BlogSettings> {
}
