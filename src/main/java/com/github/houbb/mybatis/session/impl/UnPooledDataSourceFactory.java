package com.github.houbb.mybatis.session.impl;

import com.github.houbb.mybatis.session.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * 没有池化的实现
 * @author binbin.hou
 * @since 0.0.8
 */
public class UnPooledDataSourceFactory implements DataSourceFactory {

    /**
     * 数据源信息
     * @since 0.0.8
     */
    protected DataSource dataSource;

    /**
     * 设置配置信息
     *
     * @param propertiesMap 配置信息
     * @since 0.0.8
     */
    @Override
    public void setProperties(Properties propertiesMap) {

    }

    /**
     * 获取数据源信息
     *
     * @return 数据源信息
     * @since 0.0.8
     */
    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

}
