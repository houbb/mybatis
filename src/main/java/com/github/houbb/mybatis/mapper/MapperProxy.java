package com.github.houbb.mybatis.mapper;

import com.github.houbb.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class MapperProxy implements InvocationHandler {

    /**
     * 类信息
     *
     * @since 0.0.1
     */
    private final Class clazz;

    /**
     * sql session
     *
     * @since 0.0.1
     */
    private final SqlSession sqlSession;

    public MapperProxy(Class clazz, SqlSession sqlSession) {
        this.clazz = clazz;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperMethod mapperMethod = this.sqlSession.getConfig()
                .getMapperMethod(clazz, method.getName());
        if (mapperMethod != null) {
            return this.sqlSession.selectOne(mapperMethod, args);
        }
        return method.invoke(proxy, args);
    }

}
