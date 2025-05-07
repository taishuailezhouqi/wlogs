package com.szq.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;
}
