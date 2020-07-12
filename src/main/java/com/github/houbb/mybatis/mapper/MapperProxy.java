package com.github.houbb.mybatis.mapper;

import com.github.houbb.mybatis.constant.MapperTypeConst;
import com.github.houbb.mybatis.exception.MybatisException;
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
    private final Class<?> clazz;

    /**
     * sql session
     *
     * @since 0.0.1
     */
    private final SqlSession sqlSession;

    public MapperProxy(Class<?> clazz, SqlSession sqlSession) {
        this.clazz = clazz;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //1. 如果是 object 的方法，直接返回。比如 toString() 等等
        if(Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(proxy, args);
        }

        //2. 获取对应的 CRUD 方法
        MapperMethod mapperMethod = this.sqlSession.getConfig()
                .getMapperMethod(clazz, method.getName());
        if (mapperMethod != null) {
            String type = mapperMethod.getType();
            if(MapperTypeConst.SELECT.equals(type)) {
                return this.sqlSession.selectOne(mapperMethod, args);
            }
            if(MapperTypeConst.INSERT.equals(type)) {
                return this.sqlSession.insert(mapperMethod, args);
            }
            if(MapperTypeConst.UPDATE.equals(type)) {
                return this.sqlSession.update(mapperMethod, args);
            }
            if(MapperTypeConst.DELETE.equals(type)) {
                return this.sqlSession.delete(mapperMethod, args);
            }
        }

        //3. 没有找到匹配的方法
        throw new MybatisException("Not found match method for method: " + method.getName());
    }

}
