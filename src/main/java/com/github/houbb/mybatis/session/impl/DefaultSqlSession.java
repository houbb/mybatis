package com.github.houbb.mybatis.session.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.executor.Executor;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.mapper.MapperProxy;
import com.github.houbb.mybatis.session.SqlSession;

import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultSqlSession implements SqlSession {

    /**
     * 配置信息
     * @since 0.0.1
     */
    private final Config config;

    /**
     * 执行实现类
     * @since 0.0.1
     */
    private final Executor executor;

    public DefaultSqlSession(Config config, Executor executor) {
        this.config = config;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(final MapperMethod mapperMethod, Object[] args) {
        return executor.query(config, mapperMethod, args);
    }

    @Override
    public int insert(MapperMethod mapperMethod, Object[] args) {
        return executor.update(config, mapperMethod, args);
    }

    @Override
    public int update(MapperMethod mapperMethod, Object[] args) {
        return executor.update(config, mapperMethod, args);
    }

    @Override
    public int delete(MapperMethod mapperMethod, Object[] args) {
        return executor.update(config, mapperMethod, args);
    }

    @Override
    @SuppressWarnings("all")
    public <T> T getMapper(Class<T> clazz) {
        MapperProxy proxy = new MapperProxy(clazz, this);
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, proxy);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public void close() throws IOException {
        //TODO: 实现关闭 session
    }

}
