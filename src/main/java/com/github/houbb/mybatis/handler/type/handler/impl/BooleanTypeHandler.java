package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class BooleanTypeHandler extends AbstractTypeHandler<Boolean> {


    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        ps.setBoolean(i, parameter);
    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBoolean(columnIndex);
    }

}
