package com.github.houbb.mybatis.executor;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.mapper.MapperMethod;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface Executor {

    /**
     * 查询信息
     * @param config 配置信息
     * @param method 数据
     * @param args 参数
     * @param <T> 泛型
     * @return 结果
     * @since 0.0.1
     */
    <T> T query(final Config config, MapperMethod method, Object[] args);

}
