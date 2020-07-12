package com.github.houbb.mybatis.mapper;

import com.github.houbb.mybatis.annotation.Param;
import com.github.houbb.mybatis.domain.User;
import com.github.houbb.mybatis.domain.UserExample;

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

    /**
     * 查询 map
     * @param orderBy 字段
     * @return 结果
     * @since 0.0.13
     */
    User selectWithReplace(@Param("orderBy") String orderBy);

    /**
     * 查询 map
     * @param id  ID 标识
     * @param orderBy 字段
     * @return 结果
     * @since 0.0.13
     */
    User selectByIdWithReplace(@Param("id") long id, @Param("orderBy") String orderBy);

    /**
     * 查询 map
     * @param id  ID 标识
     * @param name 名称
     * @return 结果
     * @since 0.0.13
     */
    User selectByIdAndName(@Param("id") long id, @Param("name") String name);

    /**
     * 查询对应的值
     * @param id  ID 标识
     * @return 结果
     * @since 0.0.14
     */
    User selectByIdPlaceHolder(long id);

    /**
     * 查询对应的值
     * @param example 查询对象
     * @return 结果
     * @since 0.0.14
     */
    User selectByExample(UserExample example);

    /**
     * 查询对应的值
     * @param example 查询对象
     * @param orderBy 排序信息
     * @return 结果
     * @since 0.0.14
     */
    User selectByExampleOrderBy(@Param("ex") UserExample example, @Param("orderBy") String orderBy);

}
