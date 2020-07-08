package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class IntegerTypeHandler extends AbstractTypeHandler<Integer> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter);
    }

    @Override
    public Integer getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }

    @Override
    public Integer getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex);
    }

}
