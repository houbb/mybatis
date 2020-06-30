package com.github.houbb.mybatis.config.impl;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.mapper.MapperClass;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.session.DataSource;

import java.sql.Connection;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class ConfigAdaptor implements Config {
    @Override
    public DataSource getDataSource() {
        return null;
    }

    @Override
    public MapperClass getMapperData(Class clazz) {
        return null;
    }

    @Override
    public MapperMethod getMapperMethod(Class clazz, String methodName) {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
