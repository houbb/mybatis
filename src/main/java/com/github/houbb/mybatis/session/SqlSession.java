package com.github.houbb.mybatis.session;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.mapper.MapperMethod;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface SqlSession {

    /**
     * 查询单个
     * @param mapperMethod 方法
     * @param args 参数
     * @param <T> 泛型
     * @return 结果
     * @since 0.0.1
     */
    <T> T selectOne(final MapperMethod mapperMethod, Object[] args);

    /**
     * Retrieves a mapper.
     * @param <T> the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     * @since 0.0.1
     */
    <T> T getMapper(Class<T> type);

    /**
     * 获取配置信息
     * @return 配置
     * @since 0.0.1
     */
    Config getConfig();

}
