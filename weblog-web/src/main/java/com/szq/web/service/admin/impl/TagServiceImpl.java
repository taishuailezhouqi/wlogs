package com.szq.web.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szq.web.exception.BizException;
import com.szq.web.mapper.TagMapper;
import com.szq.web.model.Tag;
import com.szq.web.service.admin.TagService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper,Tag> implements TagService{


    private final TagMapper tagMapper;


    /**
     * 分页带条件查询
     */
    @Override
    public Page<Tag> list(Tag tag, Page<Tag> tagPage) {

        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(tag.getName()), Tag::getName, tag.getName())
                .le(Objects.nonNull(tag.getEndDate()), Tag::getCreateTime, tag.getEndDate())
                .ge(Objects.nonNull(tag.getStartDate()), Tag::getCreateTime, tag.getStartDate())
                .orderByDesc(Tag::getId);
        return this.page(tagPage, tagLambdaQueryWrapper);
    }

    /**
     * 添加标签
     *
     */
    @Override
    @Transactional
    public BaseResponse<Object> add(List<String> tag) {
        //判断是否已有
        List<Tag> tags = tagMapper.selectList(null);
        List<String> names = tags.stream().map(Tag::getName).collect(Collectors.toList());
        names.forEach(name->{
            if (tag.contains(name)){
                throw new BizException("已有分类");
            }
        });
        List<Tag> tags1 = tag.stream()
                .map(t -> {
                    Tag tag1 = new Tag();
                    tag1.setName(t);
                    return tag1;
                })
                .collect(Collectors.toList());

        boolean b = this.saveBatch(tags1);
        return b ? BaseResponse.success() : BaseResponse.fail();
    }
}
