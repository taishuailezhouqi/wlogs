package com.szq.web.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.exception.BizException;
import com.szq.web.mapper.AdminArticleMapper;
import com.szq.web.mapper.ArticleCategoryRelMapper;
import com.szq.web.mapper.ArticleContentMapper;
import com.szq.web.model.*;
import com.szq.web.model.vo.PublishArticleReqVO;
import com.szq.web.service.admin.AdminArticleService;
import com.szq.web.service.admin.ArticleTagRelService;
import com.szq.web.service.admin.TagService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdminArticleServiceImpl extends ServiceImpl<AdminArticleMapper, Article> implements AdminArticleService {


    private final ArticleContentMapper articleContentMapper;
    private final ArticleCategoryRelMapper articleCategoryRelMapper;
    private final ArticleTagRelService articleTagRelService;
    private final TagService tagService;

    /**
     * 新建文章
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse<Object> publishArticle(PublishArticleReqVO article) {
        //文章类
        Article build = Article.builder()
                .title(article.getTitle())
                .cover(article.getCover())
                .summary(article.getSummary())
                .build();
        baseMapper.insert(build);

        //文章详情类
        ArticleContent articleContent = ArticleContent.builder()
                .articleId(build.getId())
                .content(article.getContent()).build();
        articleContentMapper.insert(articleContent);

        //文章与分类关联表
        ArticleCategoryRel articleCategoryRel = ArticleCategoryRel.builder()
                .articleId(build.getId())
                .categoryId(article.getCategoryId()).build();
        articleCategoryRelMapper.insert(articleCategoryRel);

        //文章与标签关联表
        insertTags(article.getTags(), build.getId());

        return BaseResponse.success();
    }

    /**
     * 保存关联标签
     */
    private void insertTags(List<String> tags, Long articleId) {
        //查找标签表
        List<Tag> list = tagService.list(null);
        List<String> ids = list.stream().map(tag->String.valueOf(tag.getId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            //判断是否存在过此标签 获取标签名
            List<String> names = list.stream().map(Tag::getName).collect(Collectors.toList());
            List<String> isExistName = names.stream().filter(tags::contains).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(isExistName)) {
                throw new BizException("已存在:" + String.join(",", isExistName));
            }
            //存在的id
            List<String> existTag = tags.stream().filter(ids::contains).collect(Collectors.toList());
            //不存在的tag
            List<String> noExistTag = tags.stream().filter(tag -> !ids.contains(tag)).collect(Collectors.toList());
            //不为空则新增
            List<Long> tagIds = new ArrayList<>();
            if (!CollectionUtils.isEmpty(noExistTag)){
                tagIds = insertTag(noExistTag);
            }
            List<Long> existIds = existTag.stream().map(Long::valueOf).collect(Collectors.toList());
            //完整的tag id
            List<Long> completeIds = Stream.concat(existIds.stream(), tagIds.stream()).collect(Collectors.toList());
            insertArticleTag(completeIds, articleId);
        }else {
            //全部新增
            List<Long> tagIds = insertTag(tags);
            insertArticleTag(tagIds, articleId);
        }



    }

    /**
     * 需要新增tag
     */
    public List<Long> insertTag(List<String> tags) {
        List<Tag> tagList = tags.stream()
                .map(t -> Tag.builder().name(t).build())
                .collect(Collectors.toList());
        tagService.saveBatch(tagList);
        return tagList.stream().map(Tag::getId).collect(Collectors.toList());

    }

    /**
     * 新增中间表
     */
    public void insertArticleTag(List<Long> tagIds, Long articleId) {
        List<ArticleTagRel> articleTagRels = tagIds.stream().map(id -> ArticleTagRel.builder()
                        .articleId(articleId).tagId(id).build())
                .collect(Collectors.toList());
        articleTagRelService.saveBatch(articleTagRels);
    }
}
