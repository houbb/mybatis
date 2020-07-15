package com.github.houbb.mybatis.transaction;

import java.sql.Connection;

/**
 * 事务管理
 *
 * @since 0.0.18
 */
public interface Transaction {

    /**
     * Retrieve inner database connection
     * @return DataBase connection
     */
    Connection getConnection();

    /**
     * Commit inner database connection.
     */
    void commit();

    /**
     * Rollback inner database connection.
     */
    void rollback();

}
