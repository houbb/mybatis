package com.github.houbb.mybatis.datasource.unpooled;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.mybatis.datasource.AbstractDataSource;
import com.github.houbb.mybatis.exception.MybatisException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author binbin.hou
 * @since 0.0.8
 */
public class UnPooledDataSource extends AbstractDataSource {

    public UnPooledDataSource(Properties properties) {
        super(properties);
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

}
