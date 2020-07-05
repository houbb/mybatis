package com.github.houbb.mybatis.datasource;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.mybatis.constant.DataSourceConst;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 抽象实现
 * @since 0.0.8
 */
public abstract class AbstractDataSource implements DataSource {

    /**
     * 驱动
     * @since 0.0.8
     */
    protected final String driver;

    /**
     * 地址
     * @since 0.0.8
     */
    protected final String url;

    /**
     * 用户名
     * @since 0.0.8
     */
    protected final String username;

    /**
     * 密码
     * @since 0.0.8
     */
    protected final String password;

    public AbstractDataSource(final Properties properties) {
        String driver = properties.getProperty(DataSourceConst.DRIVER);
        String url = properties.getProperty(DataSourceConst.URL);
        ArgUtil.notEmpty(driver, "driver");
        ArgUtil.notEmpty(url, "url");

        this.driver = driver;
        this.url = url;
        this.username = properties.getProperty(DataSourceConst.USERNAME);
        this.password = properties.getProperty(DataSourceConst.PASSWORD);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
