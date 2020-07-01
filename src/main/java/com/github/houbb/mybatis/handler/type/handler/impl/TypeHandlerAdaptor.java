package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public class TypeHandlerAdaptor<T> implements TypeHandler<T> {

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException {
        this.setParameter(ps, i, parameter, null);
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {

        return null;
    }

}
