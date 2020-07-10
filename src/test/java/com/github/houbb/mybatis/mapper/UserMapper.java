package com.github.houbb.mybatis.mapper;

import com.github.houbb.mybatis.domain.User;

import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface UserMapper {

    User selectById(final long id);

    List<User> selectList();

    User selectByIdWithTemplate(final long id);

}
