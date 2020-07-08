package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * boolean
 * char
 * short
 * int
 * long
 * float
 * double
 * @author binbin.hou
 * @since 0.0.10
 */
public class DoubleTypeHandler extends AbstractTypeHandler<Double> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Double parameter, JdbcType jdbcType) throws SQLException {
        ps.setDouble(i, parameter);
    }

    @Override
    public Double getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getDouble(columnName);
    }

    @Override
    public Double getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getDouble(columnIndex);
    }

}
