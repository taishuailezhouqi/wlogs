package com.szq.web.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    default List<UserRole> selectByUsername(String username) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUsername, username);

        return selectList(wrapper);
    }
}
