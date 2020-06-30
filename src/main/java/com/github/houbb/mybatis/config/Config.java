package com.github.houbb.mybatis.config;

import com.github.houbb.mybatis.mapper.MapperClass;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.session.DataSource;

import java.sql.Connection;

/**
 * 配置信息
 * @author binbin.hou
 * @since 0.0.1
 */
public interface Config {

    /**
     * 获取数据源信息
     * @return 数据源配置
     * @since 0.0.1
     */
    DataSource getDataSource();

    /**
     * 获取映射类信息
     * @param clazz 类信息
     * @return 结果
     * @since 0.0.1
     */
    MapperClass getMapperData(final Class clazz);

    /**
     * 获取映射类信息
     * @param clazz 类信息
     * @param methodName 方法名称
     * @return 结果
     * @since 0.0.1
     */
    MapperMethod getMapperMethod(final Class clazz,
                                 final String methodName);

    /**
     * 数据库连接信息
     * @return 连接信息
     * @since 0.0.1
     */
    Connection getConnection();
}
