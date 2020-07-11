package com.github.houbb.mybatis.handler.result.impl;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.result.ResultHandlerContext;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * map 的处理
 *
 * @author binbin.hou
 * @since 0.0.10
 */
public class MapResultTypeHandler extends AbstractResultTypeHandler {

    /**
     * 静态内部类
     *
     * @since 0.0.10
     */
    private static class Holder {
        private static final MapResultTypeHandler INSTANCE = new MapResultTypeHandler();
    }

    /**
     * @return 实现
     * @since 0.0.10
     */
    public static MapResultTypeHandler getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Object doBuildResult(ResultHandlerContext context) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            // 基本信息
            final ResultSet resultSet = context.resultSet();
            final ResultSetMetaData metaData = resultSet.getMetaData();

            // 为空直接返回，大于1则报错
            // 列数的总数
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                //TODO: 这里如果给字段起别名，获取到的是原始的字段名称
                Object value = resultSet.getObject(columnName);

                resultMap.put(columnName, value);
            }

            // 返回设置值后的结果
            return resultMap;
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }

    }

}
