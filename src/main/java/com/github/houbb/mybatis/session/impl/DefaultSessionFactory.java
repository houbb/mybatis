package com.github.houbb.mybatis.session.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.executor.impl.SimpleExecutor;
import com.github.houbb.mybatis.session.SqlSession;
import com.github.houbb.mybatis.session.SqlSessionFactory;

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
        return new DefaultSqlSession(config, new SimpleExecutor());
    }

    @Override
    public Config getConfig() {
        return config;
    }

}
