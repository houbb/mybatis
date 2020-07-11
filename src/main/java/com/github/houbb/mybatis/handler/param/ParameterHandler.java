package com.github.houbb.mybatis.handler.param;

import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.mybatis.config.Config;
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
                Object value = paramMap.get(fieldName);

                // 获取对应的处理类
                Class valueType = value.getClass();
                TypeHandler typeHandler = config.getTypeHandler(valueType);

                typeHandler.setParameter(statement, i+1, value);
            }
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

}
