package com.github.houbb.mybatis.handler.type.register.impl;

import com.github.houbb.mybatis.handler.type.handler.TypeHandler;
import com.github.houbb.mybatis.handler.type.handler.impl.*;
import com.github.houbb.mybatis.handler.type.register.TypeHandlerRegister;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的注册实现
 *
 * 1. 不支持移除，直接覆盖即可。
 * @author binbin.hou
 * @since 0.0.4
 */
public class DefaultTypeHandlerRegister implements TypeHandlerRegister {

    /**
     * 注册类
     * @since 0.0.4
     */
    private static final Map<Class<?>, TypeHandler<?>> HANDLER_MAP = new HashMap<>();

    static {
        HANDLER_MAP.put(byte.class, new ByteTypeHandler());
        HANDLER_MAP.put(Byte.class, new ByteTypeHandler());
        HANDLER_MAP.put(boolean.class, new BooleanTypeHandler());
        HANDLER_MAP.put(Boolean.class, new BooleanTypeHandler());
        HANDLER_MAP.put(char.class, new CharacterTypeHandler());
        HANDLER_MAP.put(Character.class, new CharacterTypeHandler());
        HANDLER_MAP.put(short.class, new ShortTypeHandler());
        HANDLER_MAP.put(Short.class, new ShortTypeHandler());
        HANDLER_MAP.put(int.class, new IntegerTypeHandler());
        HANDLER_MAP.put(Integer.class, new IntegerTypeHandler());
        HANDLER_MAP.put(long.class, new LongTypeHandler());
        HANDLER_MAP.put(Long.class, new LongTypeHandler());
        HANDLER_MAP.put(float.class, new FloatTypeHandler());
        HANDLER_MAP.put(Float.class, new FloatTypeHandler());
        HANDLER_MAP.put(double.class, new DoubleTypeHandler());
        HANDLER_MAP.put(Double.class, new DoubleTypeHandler());
        HANDLER_MAP.put(String.class, new StringTypeHandler());
    }

    /**
     * 注册处理类
     *
     * @param javaType java 类型
     * @param handler  处理类
     * @param <T>      泛型
     * @return 结果
     * @since 0.0.4
     */
    @Override
    public <T> DefaultTypeHandlerRegister register(final Class<T> javaType,
                                                   final TypeHandler<? extends T> handler) {
        HANDLER_MAP.put(javaType, handler);

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeHandler<? extends T> getTypeHandler(Class<T> javaType) {
        return (TypeHandler<? extends T>) HANDLER_MAP.get(javaType);
    }

}
