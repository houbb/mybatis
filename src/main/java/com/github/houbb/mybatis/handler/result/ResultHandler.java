package com.github.houbb.mybatis.handler.result;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.result.impl.MapResultTypeHandler;
import com.github.houbb.mybatis.handler.result.impl.ObjectRefResultTypeHandler;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;

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

    public ResultHandler(Class<?> resultType, Class<?> methodReturnType, Config config) {
        this.resultType = resultType;
        this.methodReturnType = methodReturnType;
        this.config = config;
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
                // TOOD: 后续可以考虑优化
                return typeHandler.getResult(resultSet, 1);
            }

            //2. map
            if(Map.class.equals(resultType)) {
                return MapResultTypeHandler.getInstance()
                        .buildResult(config, resultSet, resultType);
            }

            //3. 引用对象
            return ObjectRefResultTypeHandler.getInstance()
                    .buildResult(config, resultSet, resultType);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }


}
