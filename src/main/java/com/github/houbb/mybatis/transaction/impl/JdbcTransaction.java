package com.github.houbb.mybatis.transaction.impl;

import com.github.houbb.mybatis.constant.enums.TransactionIsolationLevel;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理
 *
 * 这里需要添加 xml 中的初始化
 *
 * @since 0.0.18
 */
public class JdbcTransaction implements Transaction {

    /**
     * 数据信息
     * @since 0.0.18
     */
    private final DataSource dataSource;

    /**
     * 隔离级别
     * @since 0.0.18
     */
    private final TransactionIsolationLevel isolationLevel;

    /**
     * 自动提交
     * @since 0.0.18
     */
    private final boolean autoCommit;

    /**
     * 连接信息
     * @since 0.0.18
     */
    private Connection connection;

    public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel isolationLevel, boolean autoCommit) {
        this.dataSource = dataSource;
        this.isolationLevel = isolationLevel;
        this.autoCommit = autoCommit;
    }

    public JdbcTransaction(DataSource dataSource) {
        this(dataSource, TransactionIsolationLevel.READ_COMMITTED, true);
    }

    @Override
    public Connection getConnection(){
        try {
            if(this.connection == null) {
                Connection connection = dataSource.getConnection();
                connection.setTransactionIsolation(isolationLevel.getLevel());
                connection.setAutoCommit(autoCommit);
                this.connection = connection;
            }

            return connection;
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    @Override
    public void commit() {
        try {
            //非自动提交，才执行 commit 操作
            if(connection != null && !this.autoCommit) {
                connection.commit();
            }
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    @Override
    public void rollback() {
        try {
            //非自动提交，才执行 commit 操作
            if(connection != null && !this.autoCommit) {
                connection.rollback();
            }
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

}
