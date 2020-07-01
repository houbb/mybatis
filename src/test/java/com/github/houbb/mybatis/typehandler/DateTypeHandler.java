package com.github.houbb.mybatis.typehandler;

import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.mybatis.handler.type.JdbcType;
import com.github.houbb.mybatis.handler.type.handler.impl.TypeHandlerAdaptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateTypeHandler extends TypeHandlerAdaptor<Date> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType)
            throws SQLException {
        String dateStr = DateUtil.getDateFormat(parameter, DateUtil.TIMESTAMP_FORMAT_17);

        ps.setString(i, dateStr);
    }

    @Override
    public Date getResult(ResultSet rs, String columnName) throws SQLException {
        String text = rs.getString(columnName);
        return DateUtil.getFormatDate(text, DateUtil.TIMESTAMP_FORMAT_17);
    }

    @Override
    public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
        String text = rs.getString(columnIndex);
        return DateUtil.getFormatDate(text, DateUtil.TIMESTAMP_FORMAT_17);
    }

}
