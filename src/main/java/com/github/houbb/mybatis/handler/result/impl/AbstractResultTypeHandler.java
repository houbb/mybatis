package com.github.houbb.mybatis.handler.result.impl;

import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.result.ResultHandlerContext;
import com.github.houbb.mybatis.handler.result.ResultTypeHandler;

import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public abstract class AbstractResultTypeHandler implements ResultTypeHandler {

    @Override
    public Object buildResult(ResultHandlerContext context) {
        try {
            // 空判断
            if(context.resultSet().wasNull()) {
                return null;
            }

            // 如果是已经定义的基本类型，则直接返回。
            return doBuildResult(context);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 执行结果构建
     * @param context 上下文
     * @return 结果
     * @since 0.0.10
     */
    protected abstract Object doBuildResult(ResultHandlerContext context);

}
