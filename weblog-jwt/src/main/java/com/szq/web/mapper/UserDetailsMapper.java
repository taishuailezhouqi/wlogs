package com.szq.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szq.web.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper extends BaseMapper<User> {

}
