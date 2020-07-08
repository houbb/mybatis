package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class ShortTypeHandler extends AbstractTypeHandler<Short> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Short parameter, JdbcType jdbcType) throws SQLException {
        ps.setShort(i, parameter);
    }

    @Override
    public Short getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getShort(columnName);
    }

    @Override
    public Short getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getShort(columnIndex);
    }

}
