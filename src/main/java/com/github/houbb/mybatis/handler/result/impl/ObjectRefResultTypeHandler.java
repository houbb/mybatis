package com.github.houbb.mybatis.handler.result.impl;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.result.ResultHandlerContext;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;
import com.github.houbb.mybatis.mapper.component.MapperResultMapItem;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 引用对象的处理
 *
 * @author binbin.hou
 * @since 0.0.10
 */
public class ObjectRefResultTypeHandler extends AbstractResultTypeHandler {

    /**
     * 静态内部类
     *
     * @since 0.0.10
     */
    private static class Holder {
        private static final ObjectRefResultTypeHandler INSTANCE = new ObjectRefResultTypeHandler();
    }

    /**
     * @return 实现
     * @since 0.0.10
     */
    public static ObjectRefResultTypeHandler getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Object doBuildResult(final ResultHandlerContext context) {
        try {
            // 基本信息
            final ResultSet resultSet = context.resultSet();
            final ResultSetMetaData metaData = resultSet.getMetaData();
            final Class<?> resultType = context.resultType();
            final Config config = context.config();

            //2. 映射集合
            Map<String, Field> fieldMap = new HashMap<>();
            final List<MapperResultMapItem> mapItems = context.resultMapMapping();
            Map<String, String> metaColumnMapping = buildMetaMapping(mapItems);
            if(CollectionUtil.isNotEmpty(mapItems)) {
                //2.1 如果不为空，优先以执行的结果映射为准
                fieldMap = buildClassFieldMap(resultType, mapItems);
            } else {
                fieldMap = buildClassFieldMap(resultType);
            }

            // 基本类型，非 java 对象，直接返回即可。
            // 可以进行抽象
            Object instance = config.newInstance(resultType);

            // 列数的总数
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                // 这里获取的是原始列名称
                String columnName = metaData.getColumnName(i);

                // 需要做一次转换
                String mappingColumnName = metaColumnMapping.get(columnName);
                if(StringUtil.isNotEmpty(mappingColumnName)) {
                    columnName = mappingColumnName;
                }

                Field field = fieldMap.get(columnName);
                //不存在，则报错
                if (field == null) {
                    String msg = String.format("Class %s not found field for column <%s>.",
                            resultType.getName(), columnName);
                    throw new MybatisException(msg);
                }

                Object value = getResult(field, columnName, resultSet,
                        config);

                // 不为 null 才进行设置
                if (ObjectUtil.isNotNull(value)) {
                    ReflectFieldUtil.setValue(field, instance, value);
                }
            }

            // 返回设置值后的结果
            return instance;
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 获取映射字段
     *
     * @return map
     * @since 0.0.9
     */
    private Map<String, Field> buildClassFieldMap(final Class<?> resultType) {
        List<Field> fieldList = ClassUtil.getAllFieldList(resultType);

        Map<String, Field> map = new HashMap<>();

        for (Field field : fieldList) {
            String fieldName = field.getName();
            // 驼峰转下划线，后期这里应该可以配置，或者指定注解。
            String columnName = StringUtil.camelToUnderline(fieldName);
            map.put(columnName, field);
        }

        return map;
    }

    /**
     * 构建元数据对应的映射关系
     *
     * ps: 其实还有一种思路，那就是通过 resultMap 中的 key 遍历映射，从而从正面获取字段。
     *
     * 对于不存在的值采用忽略的方式。
     *
     * 但是这个对于查询的结果预期可能不太友好。
     * @param mapItems 元素
     * @return 结果
     * @since 0.0.12
     */
    private Map<String, String> buildMetaMapping(final List<MapperResultMapItem> mapItems) {
        if(CollectionUtil.isEmpty(mapItems)) {
            return Collections.emptyMap();
        }

        Map<String, String> resultMap = new HashMap<>(mapItems.size());

        for(MapperResultMapItem mapItem : mapItems) {
            // 这里不够严谨
            // TODO: 后续还是需要调整为根据返回的 metadata 处理
            // 针对不同的数据库，有不同的实现即可。
            String originalColumn = StringUtil.camelToUnderline(mapItem.getProperty());
            resultMap.put(originalColumn, mapItem.getColumn());
        }
        return resultMap;
    }

    /**
     * 获取映射字段
     *
     * @return resultType 结果类型
     * @param mapItems 映射元素
     * @since 0.0.12
     */
    private Map<String, Field> buildClassFieldMap(final Class<?> resultType,
                                                  final List<MapperResultMapItem> mapItems) {
        Map<String, Field> resultMap = new HashMap<>();

        Map<String, Field> fieldMap = ClassUtil.getAllFieldMap(resultType);
        for (MapperResultMapItem mapItem : mapItems) {
            String propertyName = mapItem.getProperty();
            String columnName = mapItem.getColumn();

            Field field = fieldMap.get(propertyName);
            if(ObjectUtil.isNull(field)) {
                String msg = String.format("Class %s not found field for column <%s>.",
                        resultType.getName(), columnName);
                throw new MybatisException(msg);
            }

            resultMap.put(columnName, field);
        }

        return resultMap;
    }


    /**
     * 获取对应的结果
     * <p>
     * TODO: 这里其实有几种方式优雅的获取 rs 查询的列信息
     * <p>
     * 1. rs.findColumn() 经过测试，发现会报错，不友好
     * <p>
     * 2. 解析 sql，构建 rs 的字段集合。
     * <p>
     * 3. 根据 {@link ResultSet#getMetaData()} 处理信息
     * <p>
     * 此处暂时使用第三种方式。
     *
     * @param field      字段信息
     * @param columnName 列名称
     * @param rs         结果集
     * @param config     配置信息
     * @return 结果
     * @since 0.0.1
     */
    public Object getResult(Field field, String columnName, ResultSet rs,
                            final Config config) {
        try {
            Class<?> type = field.getType();

            TypeHandler<?> typeHandler = config.getTypeHandler(type);
            return typeHandler.getResult(rs, columnName);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

}
