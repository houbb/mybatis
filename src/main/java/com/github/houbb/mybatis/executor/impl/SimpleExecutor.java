package com.github.houbb.mybatis.executor.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.executor.Executor;
import com.github.houbb.mybatis.handler.ParameterHandler;
import com.github.houbb.mybatis.handler.ResultHandler;
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
        try(Connection connection = config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(method.getSql());) {
            // 2. 处理参数
            parameterHandle(preparedStatement, args);

            // 3. 执行方法
            preparedStatement.execute();

            // 4. 处理结果
            final Class resultType = method.getResultType();
            ResultSet resultSet = preparedStatement.getResultSet();

            ResultHandler resultHandler = new ResultHandler(resultType);
            Object result = resultHandler.buildResult(resultSet);
            return (T) result;
        } catch (SQLException ex) {
            throw new MybatisException(ex);
        }
    }


    /**
     * 处理参数
     * @param preparedStatement 入参
     * @since 0.0.1
     */
    private void parameterHandle(final PreparedStatement preparedStatement,
                                 final Object[] args) {
        ParameterHandler parameterHandler = new ParameterHandler(preparedStatement);
        parameterHandler.setParams(args);
    }

}
