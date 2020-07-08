package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class ByteTypeHandler extends AbstractTypeHandler<Byte> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Byte parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter);
    }

    @Override
    public Byte getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getByte(columnName);
    }

    @Override
    public Byte getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getByte(columnIndex);
    }

}
