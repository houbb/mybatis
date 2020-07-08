package com.github.houbb.mybatis.handler.result.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.result.ResultTypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public abstract class AbstractResultTypeHandler implements ResultTypeHandler {

    @Override
    public Object buildResult(Config config, ResultSet resultSet, Class<?> resultType) {
        try {
            // 空判断
            if(resultSet.wasNull()) {
                return null;
            }

            // 如果是已经定义的基本类型，则直接返回。
            return doBuildResult(config, resultSet, resultType);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 执行结果构建
     * @param config 配置
     * @param resultSet 结果集
     * @param resultType 结果类型
     * @return 结果
     * @since 0.0.10
     */
    protected abstract Object doBuildResult(Config config, ResultSet resultSet, Class<?> resultType);

}
