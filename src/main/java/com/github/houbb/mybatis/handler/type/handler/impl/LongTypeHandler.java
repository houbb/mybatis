package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public class LongTypeHandler extends AbstractTypeHandler<Long> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter);
    }

    @Override
    public Long getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getLong(columnName);
    }

    @Override
    public Long getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getLong(columnIndex);
    }

}
