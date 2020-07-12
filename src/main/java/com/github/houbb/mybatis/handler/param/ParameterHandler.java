package com.github.houbb.mybatis.handler.param;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.constant.MapperSqlConst;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 反射设置属性值
 *
 * TODO: 这里应该分为两个部分
 *
 * （1）java to jdbc 类型
 * （2）jdbc to java 类型
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class ParameterHandler {

    /**
     * 语句信息
     * @since 0.0.1
     */
    private final PreparedStatement statement;

    /**
     * 配置信息
     * @since 0.0.4
     */
    private final Config config;

    public ParameterHandler(PreparedStatement statement, Config config) {
        this.statement = statement;
        this.config = config;
    }

    /**
     * 设置参数信息
     *
     * 可以处理的方式：
     *
     * （1）设置处理对应的值
     * （2）设置处理对应的类型
     *
     * @param psNames 参数列表
     * @param paramMap 参数值
     * @since 0.0.1
     */
    @SuppressWarnings("all")
    public void setParams(final List<String> psNames,
                          final Map<String, Object> paramMap) {
        try {
            // 跳过处理
            if(CollectionUtil.isEmpty(psNames)
                || MapUtil.isEmpty(paramMap)) {
                return;
            }

            for(int i = 0; i < psNames.size(); i++) {
                String fieldName = psNames.get(i);
                Object value = this.getParamValue(fieldName, paramMap);

                // 获取对应的处理类
                Class valueType = value.getClass();
                TypeHandler typeHandler = config.getTypeHandler(valueType);

                typeHandler.setParameter(statement, i+1, value);
            }
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 获取参数对应的值
     *
     * 1. 直接根据名称获取，如 orderBy，存在则直接返回。
     *
     * 2. 名称不存在，是否属于多级引用。比如 user.name
     *
     * 暂时只支持2级。
     *
     * 3. 如果只有一个参数
     *
     * 3.1 用户自定义 && 包含 field: 反射当前元素，是否包含这个 field 比如 name
     * 3.2 直接返回当前值   比如直接返回 #{id}
     *
     *
     * @param paramName 参数名称
     * @param paramMap 参数对应的 map
     * @return 结果
     * @since 0.0.14
     */
    private Object getParamValue(final String paramName,
                                 final Map<String, Object> paramMap) {
        //1. 名称是否存在
        if(paramMap.containsKey(paramName)) {
            return paramMap.get(paramName);
        }

        //2. 多级引用
        if(paramName.contains(MapperSqlConst.MULTI_REF)) {
            String[] paramNames = paramName.split("\\.");
            if(paramNames.length != 2) {
                throw new MybatisException("Only support two level reference, but found: " + paramName);
            }

            String firstParam = paramNames[0];
            String secondParam = paramNames[1];

            Object firstRef = paramMap.get(firstParam);
            // 第一层引用如果值为空，则直接报错
            if(ObjectUtil.isNull(firstRef)) {
                throw new MybatisException("Not found match param for name: " + paramName);
            }
            // 获取第二层的值
            return ReflectFieldUtil.getValue(secondParam, firstRef);
        }

        //3. 只有一个参数
        if(paramMap.size() == 1) {
            Map.Entry<String, Object> firstEntry = MapUtil.getFirstEntry(paramMap);
            //3.1 这个值不能为空
            if(ObjectUtil.isNull(firstEntry)
                    || ObjectUtil.isNull(firstEntry.getValue())) {
                throw new MybatisException("The first param value is null for paramName: " + paramName);
            }

            final Object mapValue = firstEntry.getValue();
            Class<?> type = mapValue.getClass();

            //3.2 对应的字段属性
            if(ClassTypeUtil.isBean(type)) {
                return ReflectFieldUtil.getValue(paramName, mapValue);
            }

            //3.3 基本类型
            return mapValue;
        }

        //4. 直接报错
        throw new MybatisException("Not found match param for name: " + paramName);
    }

}
