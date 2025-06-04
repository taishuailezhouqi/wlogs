package com.szq.web.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.exception.BizException;
import com.szq.web.mapper.admin.AdminArticleMapper;
import com.szq.web.mapper.admin.AdminArticleCategoryRelMapper;
import com.szq.web.mapper.admin.AdminArticleContentMapper;
import com.szq.web.mapper.admin.AdminCategoryMapper;
import com.szq.web.model.*;
import com.szq.web.model.vo.DeleteArticleReqVO;
import com.szq.web.model.vo.FindArticleDetailRspVO;
import com.szq.web.model.vo.PublishArticleReqVO;
import com.szq.web.model.vo.UpdateArticleReqVO;
import com.szq.web.service.admin.AdminArticleService;
import com.szq.web.service.admin.AdminArticleTagRelService;
import com.szq.web.service.admin.AdminTagService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdminArticleServiceImpl extends ServiceImpl<AdminArticleMapper, Article> implements AdminArticleService {


    private final AdminArticleContentMapper articleContentMapper;
    private final AdminArticleCategoryRelMapper articleCategoryRelMapper;
    private final AdminArticleTagRelService articleTagRelService;
    private final AdminTagService tagService;
    private final AdminCategoryMapper categoryMapper;


    /**
     * 修改文章
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse<Object> updateArticle(UpdateArticleReqVO vo) {
        Article article1 = baseMapper.selectById(vo.getId());
        if (Objects.isNull(article1)){
            return BaseResponse.fail("该文章不存在");
        }

        Article article = Article.builder()
                .id(vo.getId())
                .summary(vo.getSummary())
                .title(vo.getTitle())
                .cover(vo.getCover())
                .build();
        baseMapper.updateById(article);
        //文章详情修改
        ArticleContent articleContent = ArticleContent.builder().articleId(vo.getId()).content(vo.getContent()).build();
        articleContentMapper.update(articleContent,new LambdaUpdateWrapper<>(articleContent)
                .eq(ArticleContent::getArticleId,articleContent.getArticleId()));

        //文章与分类修改
        ArticleCategoryRel articleCategoryRel1 = articleCategoryRelMapper.selectOne(new LambdaQueryWrapper<>(ArticleCategoryRel.class).eq(ArticleCategoryRel::getArticleId, vo.getId()));
        ArticleCategoryRel articleCategoryRel = ArticleCategoryRel.builder()
                .articleId(vo.getId()).categoryId(vo.getCategoryId()).build();
        articleCategoryRelMapper.update(articleCategoryRel,new LambdaUpdateWrapper<>(articleCategoryRel1)
                .eq(ArticleCategoryRel::getArticleId,articleCategoryRel.getArticleId()));

        //文章对应标签修改
        //删除所有的标签
        articleTagRelService.remove(new LambdaQueryWrapper<>(ArticleTagRel.class)
                .eq(ArticleTagRel::getArticleId, vo.getId()));
        //调用之前写过的方法
        insertTags(vo.getTags(), vo.getId());
        return BaseResponse.success();
    }

    /**
     * 文章详情
     */
    @Override
    public BaseResponse<Object> detail(Long id) {
        //不存在文章
        Article article = baseMapper.selectById(id);
        if(Objects.isNull(article)){
            return BaseResponse.fail("不存在该文章");
        }
        //查询文章和详情
        ArticleContent articleContent = articleContentMapper.selectOne(new LambdaQueryWrapper<>(ArticleContent.class)
                .eq(ArticleContent::getArticleId, id));

        ArticleCategoryRel articleCategoryRel = articleCategoryRelMapper.selectOne(new LambdaQueryWrapper<>(ArticleCategoryRel.class)
                .eq(ArticleCategoryRel::getArticleId, id));

        List<Long> tagIds = articleTagRelService.list(null).stream().map(ArticleTagRel::getTagId).collect(Collectors.toList());

        FindArticleDetailRspVO build = FindArticleDetailRspVO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .cover(article.getCover())
                .content(articleContent.getContent())
                .categoryId(articleCategoryRel.getCategoryId())
                .tagIds(tagIds).build();


        return BaseResponse.success(build);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Article> pageList(Article article, Page<Article> page){
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper
                .eq(StringUtils.isNotBlank(article.getTitle()), Article::getTitle, article.getTitle())
                .ge(Objects.nonNull(article.getStartDate()), Article::getCreateTime, article.getStartDate())
                .le(Objects.nonNull(article.getEndDate()), Article::getCreateTime, article.getEndDate())
                .orderByDesc(Article::getCreateTime);
        return baseMapper.selectPage(page,articleLambdaQueryWrapper);
    }

    /**
     * 删除文章
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse<Object> delete(DeleteArticleReqVO vo) {
        //删除文章
        baseMapper.deleteById(vo.getId());
        articleContentMapper.delete(new LambdaQueryWrapper<>(ArticleContent.class)
                .eq(ArticleContent::getArticleId, vo.getId()));
        //删除关联表
        articleTagRelService.remove(new LambdaQueryWrapper<>(ArticleTagRel.class)
                .eq(ArticleTagRel::getArticleId, vo.getId()));
        articleCategoryRelMapper.delete(new LambdaQueryWrapper<>(ArticleCategoryRel.class)
                .eq(ArticleCategoryRel::getArticleId, vo.getId()));
        return BaseResponse.success();
    }

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
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (Objects.isNull(category)){
            return BaseResponse.fail("分类不存在");
        }
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
