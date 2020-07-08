package com.github.houbb.mybatis.handler.type.handler.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.mybatis.handler.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class CharacterTypeHandler extends AbstractTypeHandler<Character> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Character parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Character getResult(ResultSet rs, String columnName) throws SQLException {
        String text = rs.getString(columnName);
        if(StringUtil.isEmpty(text)) {
            return null;
        }

        return text.charAt(0);
    }

    @Override
    public Character getResult(ResultSet rs, int columnIndex) throws SQLException {
        String text = rs.getString(columnIndex);
        if(StringUtil.isEmpty(text)) {
            return null;
        }

        return text.charAt(0);
    }

}
