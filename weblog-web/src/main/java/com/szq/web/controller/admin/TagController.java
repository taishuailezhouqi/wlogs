package com.szq.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szq.web.mapper.ArticleTagRelMapper;
import com.szq.web.model.ArticleTagRel;
import com.szq.web.model.Tag;
import com.szq.web.service.admin.ArticleTagRelService;
import com.szq.web.service.admin.TagService;
import com.szq.web.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private  final ArticleTagRelMapper articleTagRelMapper;

    /**
     * 分页带条件查询
     */
    @PostMapping("/list")
    public BaseResponse<Object> list(@RequestBody Tag tag) {
        Page<Tag> tagPage = new Page<>(tag.getCurrent(), tag.getSize());
        Page<Tag> page = tagService.list(tag,tagPage);
        return BaseResponse.success(page);
    }

    /**
     * 添加标签
     */
    @PostMapping("add")
    public BaseResponse<Object> addTag(@RequestBody List<String> name) {
        return tagService.add(name);
    }

    /**
     * 删除标签
     */
    @PostMapping("/delete")
    public BaseResponse<Object> delete(@RequestBody Tag tag){
        Long id = tag.getId();
        if (id == null){
            return BaseResponse.fail("id不能为空");
        }
        Tag tag1= tagService.getById(id);
        if (tag1 == null){
            return BaseResponse.fail("标签不存在");
        }
        Long count = articleTagRelMapper.selectCount(new LambdaQueryWrapper<>(ArticleTagRel.class)
                .eq(ArticleTagRel::getTagId, id));
        if (count > 0){
            return BaseResponse.fail("标签还有关联不可以删除");
        }
        return tagService.removeById(id) ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 根据标签名模糊查询
     */
    @PostMapping("/search")
    public BaseResponse<Object> search(@RequestBody @Validated Tag tag){
        List<Tag> tags = tagService.search(tag);
        return BaseResponse.success(tags);
    }

    @PostMapping("/select/list")
    public BaseResponse<Object> selectList() {
        List<Tag> list = tagService.list();
        return BaseResponse.success(list);
    }


}
