package com.github.houbb.mybatis.config.impl;

import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.constant.DataSourceConst;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.session.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 配置信息
 * @author binbin.hou
 * @since 0.0.1
 */
public class PropertiesConfig extends ConfigAdaptor {

    /**
     * 文件配置路径
     * @since 0.0.1
     */
    private final String configPath;

    /**
     * 配置文件信息
     * @since 0.0.1
     */
    private final Properties properties = new Properties();

    private DataSource dataSource;

    public PropertiesConfig(String configPath) {
        this.configPath = configPath;

        // 配置初始化
        initProperties();

        // 初始化数据连接信息
        initDataSource();

        // mapper 信息

    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public Connection getConnection() {
        try {
            Class clazz = Class.forName(dataSource.driver());
            return DriverManager.getConnection(dataSource.url(), dataSource.username(), dataSource.password());
        } catch (ClassNotFoundException | SQLException e) {
            throw new MybatisException(e);
        }
    }

    /**
     * 初始化配置文件信息
     * @since 0.0.1
     */
    private void initProperties() {
        try {
            // 初始化数据库连接信息
            InputStream inputStream = StreamUtil.getInputStream(configPath);
            properties.load(inputStream);
        } catch (IOException e) {
            throw new MybatisException(e);
        }

    }

    private void initDataSource() {
        // 根据配置初始化连接信息
        this.dataSource = new DataSource();
        dataSource.username(properties.getProperty(DataSourceConst.USERNAME))
                .password(properties.getProperty(DataSourceConst.PASSWORD))
                .driver(properties.getProperty(DataSourceConst.DRIVER))
                .url(properties.getProperty(DataSourceConst.URL));
    }

}
