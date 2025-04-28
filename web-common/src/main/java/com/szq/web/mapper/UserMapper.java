package com.szq.web.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default User selectByUsername(String username){
        return selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,username));
    }

}
