package com.github.houbb.mybatis.session.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.executor.Executor;
import com.github.houbb.mybatis.executor.impl.SimpleExecutor;
import com.github.houbb.mybatis.plugin.Interceptor;
import com.github.houbb.mybatis.plugin.InterceptorChain;
import com.github.houbb.mybatis.session.SqlSession;
import com.github.houbb.mybatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultSessionFactory implements SqlSessionFactory {

    /**
     * 数据库配置信息
     * @since 0.0.1
     */
    private final Config config;

    public DefaultSessionFactory(Config config) {
        this.config = config;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = new SimpleExecutor();
        //1. 插件
        InterceptorChain interceptorChain = new InterceptorChain();
        List<Interceptor> interceptors = config.getInterceptorList();
        interceptorChain.add(interceptors);

        executor = (Executor) interceptorChain.pluginAll(executor);

        //2. 创建
        return new DefaultSqlSession(config, executor);
    }

    @Override
    public Config getConfig() {
        return config;
    }

}
