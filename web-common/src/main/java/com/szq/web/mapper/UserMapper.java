package com.szq.web.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default User selectByUsername(String username){
        return selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,username));
    }
    default int updatePasswordByUsername(String username, String password) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        // 设置要更新的字段
        wrapper.set(User::getPassword, password);
        wrapper.set(User::getUpdateTime, LocalDateTime.now());
        // 更新条件
        wrapper.eq(User::getUsername, username);

        return update(null, wrapper);
    }


}
