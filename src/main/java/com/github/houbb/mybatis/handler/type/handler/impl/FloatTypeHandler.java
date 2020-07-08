package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class FloatTypeHandler extends AbstractTypeHandler<Float> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Float parameter, JdbcType jdbcType) throws SQLException {
        ps.setFloat(i, parameter);
    }

    @Override
    public Float getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getFloat(columnName);
    }

    @Override
    public Float getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getFloat(columnIndex);
    }

}
