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
     * @param context 上下文
     * @return 结果
     * @since 0.0.10
     */
    Object buildResult(ResultHandlerContext context);
    
}
