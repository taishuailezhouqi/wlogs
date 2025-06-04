package com.szq.web.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.mapper.admin.AdminArticleTagRelMapper;
import com.szq.web.model.ArticleTagRel;
import com.szq.web.service.admin.AdminArticleTagRelService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagRelServiceImpl extends ServiceImpl<AdminArticleTagRelMapper, ArticleTagRel> implements AdminArticleTagRelService {
}
