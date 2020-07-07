package com.github.houbb.mybatis.handler.result;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class ResultHandler {

    /**
     * 结果类型
     * @since 0.0.1
     */
    private final Class<?> resultType;

    /**
     * 配置信息
     * @since 0.0.4
     */
    private final Config config;

    /**
     * 映射 map
     *
     * 可以优化
     * @since 0.0.9
     */
    private final Map<String, Field> fieldMap;

    public ResultHandler(Class<?> resultType, Config config) {
        this.resultType = resultType;
        this.config = config;
        this.fieldMap = buildClassFieldMap();
    }

    /**
     * 构建结果
     * @param resultSet 结果集合
     * @return 结果
     * @since 0.0.1
     */
    public Object buildResult(final ResultSet resultSet) {
        try {
            // 基本类型，非 java 对象，直接返回即可。

            // 可以进行抽象
            Object instance = config.newInstance(resultType);

            // 结果大小的判断
            // 为空直接返回，大于1则报错
            if(resultSet.next()) {

                // 列数的总数
                int columnCount = resultSet.getMetaData().getColumnCount();

                for(int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Field field = fieldMap.get(columnName);
                    //不存在，则报错
                    if(field == null) {
                        throw new MybatisException("No class field match for column: " + columnName);
                    }

                    Object value = getResult(field, columnName, resultSet);

                    // 不为 null 才进行设置
                    if(ObjectUtil.isNotNull(value)) {
                        ReflectFieldUtil.setValue(field, instance, value);
                    }
                }

                // 返回设置值后的结果
                return instance;
            }

            return null;
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 获取映射字段
     * @return map
     * @since 0.0.9
     */
    private Map<String, Field> buildClassFieldMap() {
        List<Field> fieldList = ClassUtil.getAllFieldList(resultType);

        Map<String, Field> map = new HashMap<>();

        for(Field field : fieldList) {
            String fieldName = field.getName();
            // 驼峰转下划线，后期这里应该可以配置，或者指定注解。
            String columnName = StringUtil.camelToUnderline(fieldName);
            map.put(columnName, field);
        }

        return map;
    }

    /**
     * 获取对应的结果
     *
     * TODO: 这里其实有几种方式优雅的获取 rs 查询的列信息
     *
     * 1. rs.findColumn() 经过测试，发现会报错，不友好
     *
     * 2. 解析 sql，构建 rs 的字段集合。
     *
     * 3. 根据 {@link ResultSet#getMetaData()} 处理信息
     *
     * 此处暂时使用第三种方式。
     * @param field 字段信息
     * @param columnName 列名称
     * @param rs 结果集
     * @return 结果
     * @since 0.0.1
     */
    public Object getResult(Field field, String columnName, ResultSet rs) {
        try {
            Class<?> type = field.getType();

            TypeHandler<?> typeHandler = config.getTypeHandler(type);
            return typeHandler.getResult(rs, columnName);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

}
