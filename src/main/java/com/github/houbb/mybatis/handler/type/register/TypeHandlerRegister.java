package com.github.houbb.mybatis.handler.type.register;

import com.github.houbb.mybatis.handler.type.handler.TypeHandler;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public interface TypeHandlerRegister {

    /**
     * 注册处理类
     * @param javaType java 类型
     * @param handler 处理类
     * @param <T> 泛型
     * @return 结果
     * @since 0.0.4
     */
    <T> TypeHandlerRegister register(final Class<T> javaType,
                                     final TypeHandler<? extends T> handler);

    /**
     * 获取类型处理类
     * @param javaType 类型
     * @param <T> 泛型
     * @return 结果
     * @since 0.0.4
     */
    <T> TypeHandler<? extends T> getTypeHandler(final Class<T> javaType);

}
