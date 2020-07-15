package com.github.houbb.mybatis.config.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.plugin.Interceptor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class ConfigAdaptor implements Config {

    @Override
    public MapperMethod getMapperMethod(Class clazz, String methodName) {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public List<Interceptor> getInterceptorList() {
        return Collections.emptyList();
    }

    @Override
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType) {
        return null;
    }

    @Override
    public String getTypeAlias(String alias) {
        return null;
    }

    @Override
    public <T> T newInstance(Class<T> tClass) {
        return null;
    }
}
