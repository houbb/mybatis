package com.github.houbb.mybatis.mapper;

import com.github.houbb.mybatis.domain.User;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface UserMapper {

    User selectById(final long id);

}
