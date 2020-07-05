package com.github.houbb.mybatis.session.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.mybatis.exception.MybatisException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class UnPooledDataSource implements DataSource {

    private String driver;

    private String url;

    private String username;

    private String password;

    public UnPooledDataSource(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        ArgUtil.notEmpty(username, "username");
        ArgUtil.notEmpty(password, "password");
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new MybatisException(e);
        }
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
