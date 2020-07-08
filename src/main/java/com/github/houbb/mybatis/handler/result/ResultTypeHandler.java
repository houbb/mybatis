package com.github.houbb.mybatis.handler.result;

import com.github.houbb.mybatis.config.Config;

import java.sql.ResultSet;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface ResultTypeHandler {

    /**
     * 构建结果
     * @param config 配置信息
     * @param resultSet 结果集
     * @param resultType 结果类
     * @return 结果
     * @since 0.0.10
     */
    Object buildResult(final Config config,
                       final ResultSet resultSet,
                       final Class<?> resultType);
    
}
