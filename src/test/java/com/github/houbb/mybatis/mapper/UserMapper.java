package com.github.houbb.mybatis.mapper;

import com.github.houbb.mybatis.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface UserMapper {

    User selectById(final long id);

    List<User> selectList();

    User selectByIdWithTemplate(final long id);

    /**
     * 查询 map
     * @param id 标识
     * @return 结果
     * @since 0.0.12
     */
    Map<String, Object> selectMap(final long id);

    /**
     * 查询 map
     * @param id 标识
     * @return 结果
     * @since 0.0.12
     */
    User selectByIdWithResultMap(final long id);

}
