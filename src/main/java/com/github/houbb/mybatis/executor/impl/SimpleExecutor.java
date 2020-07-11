package com.github.houbb.mybatis.executor.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.executor.Executor;
import com.github.houbb.mybatis.handler.param.ParameterHandler;
import com.github.houbb.mybatis.handler.result.ResultHandler;
import com.github.houbb.mybatis.mapper.MapperMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这里可以设置为是否使用插件模式。
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class SimpleExecutor implements Executor {

    /**
     * 查询信息
     *
     * @param config    配置信息
     * @param method    方法信息
     * @param args 参数
     * @param <T>       泛型
     * @return 结果
     * @since 0.0.1
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T query(final Config config,
                       MapperMethod method, Object[] args) {
        try(Connection connection = config.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(method.getSql());) {
            // 2. 处理参数
            ParameterHandler parameterHandler = new ParameterHandler(preparedStatement, config);
            parameterHandler.setParams(args);

            // 3. 执行方法
            preparedStatement.execute();

            // 4. 处理结果
            ResultSet resultSet = preparedStatement.getResultSet();
            ResultHandler resultHandler = new ResultHandler(method, config);
            Object result = resultHandler.buildResult(resultSet);
            return (T) result;
        } catch (SQLException ex) {
            throw new MybatisException(ex);
        }
    }

}
