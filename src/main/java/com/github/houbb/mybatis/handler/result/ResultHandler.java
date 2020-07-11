package com.github.houbb.mybatis.handler.result;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.result.impl.MapResultTypeHandler;
import com.github.houbb.mybatis.handler.result.impl.ObjectRefResultTypeHandler;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;
import com.github.houbb.mybatis.mapper.MapperClass;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.mapper.component.MapperResultMapItem;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
     * 方法返回类型
     * @since 0.1.10
     */
    private final Class<?> methodReturnType;

    /**
     * 配置信息
     * @since 0.0.4
     */
    private final Config config;

    /**
     * 对应的方法信息
     * @since 0.0.12
     */
    private final Method method;

    final MapperMethod mapperMethod;

    public ResultHandler(final MapperMethod mapperMethod, Config config) {
        this.resultType = mapperMethod.getResultType();
        this.methodReturnType = mapperMethod.getMethod().getReturnType();
        this.config = config;

        this.method = mapperMethod.getMethod();

        this.mapperMethod = mapperMethod;
    }

    /**
     * 获取结果映射列表
     * @return 结果
     * @since 0.0.12
     */
    private List<MapperResultMapItem> getResultMapMapping() {
        String resultMap = mapperMethod.getResultMap();
        if(StringUtil.isEmpty(resultMap)) {
            return null;
        }
        final MapperClass mapperClass = mapperMethod.getRefClass();
        for(Map.Entry<String, List<MapperResultMapItem>> entry : mapperClass.getResultMapMapping().entrySet()) {
            String name = entry.getKey();
            if(resultMap.equals(name)) {
                return entry.getValue();
            }
        }

        throw new MybatisException("Not found result map for name: " + resultMap);
    }

    /**
     * 构建结果
     * @param resultSet 结果集合
     * @return 结果
     * @since 0.0.1
     */
    public Object buildResult(final ResultSet resultSet) {
        try {
            List<Object> resultList = new ArrayList<>();

            // 结果大小的判断
            // 为空直接返回，大于1则报错
            if(resultSet.next()) {
                // 分成为两种情况
                // 1. 根据 resultType 反射获取字段
                // 2. 根据 resultMap 获取反射字段信息
                Object value = getValueByResultType(resultSet);
                resultList.add(value);
            }

            // 如果结果为列表，则直接返回。
            // 这里暂时不考虑各种子类的情况
            if(List.class.equals(methodReturnType)) {
                return resultList;
            }

            // 结果集合校验，后期可以优化为提前失败
            if(resultList.size() > 1) {
                throw new MybatisException("Expect one, but found " + resultList.size());
            }
            // 空则直接返回
            if(CollectionUtil.isEmpty(resultList)) {
                return null;
            }

            // 返回第一个
            return resultList.get(0);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 根据结果获取对应的值
     * @param resultSet 结果集
     * @return 结果
     * @since 0.0.10
     */
    private Object getValueByResultType(final ResultSet resultSet) {
        try {
            if(resultSet.wasNull()) {
                return null;
            }

            //1. 基本类型
            TypeHandler<?> typeHandler = config.getTypeHandler(resultType);
            if(ObjectUtil.isNotNull(typeHandler)) {
                // 固定获取第一个元素
                // TODO: 后续可以考虑优化
                return typeHandler.getResult(resultSet, 1);
            }

            ResultHandlerContext context = ResultHandlerContext.newInstance()
                    .config(config)
                    .resultMapId(mapperMethod.getResultMap())
                    .resultMapMapping(getResultMapMapping())
                    .resultSet(resultSet);
            // 特殊处理返回值
            Class<?> actualReturnType = this.resultType;
            if(ObjectUtil.isNull(actualReturnType)) {
                actualReturnType = getResultTypeByMethod();
            }
            context.resultType(actualReturnType);


            //2. map
            if(Map.class.equals(resultType)) {
                return MapResultTypeHandler.getInstance()
                        .buildResult(context);
            }

            //3. 引用对象
            return ObjectRefResultTypeHandler.getInstance()
                    .buildResult(context);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 根绝方法的返回值，获取元素对应的结果
     * @return 结果类型
     * @since 0.0.12
     */
    private Class<?> getResultTypeByMethod() {
        // 列表
        if(Map.class.equals(methodReturnType)) {
            return Map.class;
        }
        if(List.class.equals(methodReturnType)) {
            // 根据泛型对应的元素
            return ReflectMethodUtil.getGenericReturnParamType(method, 0);
        }

        // 返回本身
        return methodReturnType;
    }

}
