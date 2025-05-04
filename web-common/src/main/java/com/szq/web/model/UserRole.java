package com.szq.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName("t_user_role")
public class UserRole {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String role;
    private Date createTime;
}
