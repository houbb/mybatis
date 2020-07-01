package com.github.houbb.mybatis.handler.type.handler;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 直接使用 mybatis 的接口
 * @author binbin.hou
 * @since 0.0.4
 */
public interface TypeHandler<T> {

    /**
     * 设置参数类型
     * @param ps 准备信息
     * @param i 下标
     * @param parameter 参数
     * @param jdbcType 类型
     * @throws SQLException 异常
     * @since 0.0.4
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

    /**
     * 设置参数类型
     * @param ps 准备信息
     * @param i 下标
     * @param parameter 参数
     * @throws SQLException 异常
     * @since 0.0.4
     */
    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;

    T getResult(ResultSet rs, String columnName) throws SQLException;

    T getResult(ResultSet rs, int columnIndex) throws SQLException;

    T getResult(CallableStatement cs, int columnIndex) throws SQLException;

}
