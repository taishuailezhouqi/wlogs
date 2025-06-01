package com.szq.web.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.mapper.ArticleTagRelMapper;
import com.szq.web.model.ArticleTagRel;
import com.szq.web.service.admin.ArticleTagRelService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagRelServiceImpl extends ServiceImpl<ArticleTagRelMapper, ArticleTagRel> implements ArticleTagRelService {
}
