package com.github.houbb.mybatis.handler;

import com.github.houbb.mybatis.exception.MybatisException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 反射设置属性值
 *
 * TODO: 这里应该分为两个部分
 *
 * （1）java==>jdbc 类型
 * （2）jdbc==>java 类型
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class ParameterHandler {

    private final PreparedStatement statement;


    public ParameterHandler(PreparedStatement statement) {
        this.statement = statement;
    }

    /**
     * 设置参数信息
     *
     * 可以处理的方式：
     *
     * （1）设置处理对应的值
     * （2）设置处理对应的类型
     *
     * @param objects 对象列表
     * @since 0.0.1
     */
    public void setParams(final Object[] objects) {
        try {
            for(int i = 0; i < objects.length; i++) {
                Object value = objects[i];

                // 目标类型，这个后期可以根据 jdbcType 获取
                // jdbc 下标从1开始
                statement.setObject(i+1, value);
            }
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

}
