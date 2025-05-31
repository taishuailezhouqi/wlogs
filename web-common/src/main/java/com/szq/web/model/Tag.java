package com.szq.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 文章标签表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_tag")
public class Tag {

    /**
     * 标签ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    /**
     * 逻辑删除标志位：0：未删除 1：已删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private Integer current;
    @TableField(exist = false)
    private Integer size;
    @TableField(exist = false)
    private Date startDate;
    @TableField(exist = false)
    private Date endDate;
}
