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
 * @since 0.0.18
 */
public class ManageTransaction implements Transaction {

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
     * 连接信息
     * @since 0.0.18
     */
    private Connection connection;

    public ManageTransaction(DataSource dataSource, TransactionIsolationLevel isolationLevel) {
        this.dataSource = dataSource;
        this.isolationLevel = isolationLevel;
    }

    public ManageTransaction(DataSource dataSource) {
        this(dataSource, TransactionIsolationLevel.READ_COMMITTED);
    }

    @Override
    public Connection getConnection() {
        try {
            if(this.connection == null) {
                Connection connection = dataSource.getConnection();
                connection.setTransactionIsolation(isolationLevel.getLevel());
                this.connection = connection;
            }

            return connection;
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    @Override
    public void commit() {
        //nothing
    }

    @Override
    public void rollback() {
        //nothing
    }

}
