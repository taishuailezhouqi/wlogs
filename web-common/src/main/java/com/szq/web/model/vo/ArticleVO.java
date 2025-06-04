package com.szq.web.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.szq.web.model.Category;
import com.szq.web.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_article")
public class ArticleVO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String cover;

    private String summary;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date updateTime;

    private Boolean isDeleted;

    private Long readNum;

    /**
     * 发布的起始日期
     */
    @TableField(exist = false)
    private LocalDate startDate;

    /**
     * 发布的结束日期
     */
    @TableField(exist = false)
    private LocalDate endDate;

    @TableField(exist = false)
    private Integer current;

    @TableField(exist = false)
    private Integer size;

    /**
     * 文章分类
     */
    @TableField(exist = false)
    private Category category;

    /**
     * 文章标签
     */
    @TableField(exist = false)
    private List<Tag> tags;
}
