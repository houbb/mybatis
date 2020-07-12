package com.github.houbb.mybatis.executor.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.mybatis.annotation.Param;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.executor.Executor;
import com.github.houbb.mybatis.handler.param.ParameterHandler;
import com.github.houbb.mybatis.handler.result.ResultHandler;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;
import com.github.houbb.mybatis.support.replace.impl.SimpleSqlReplace;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里可以设置为是否使用插件模式。
 *
 * query 查询
 * create 插入
 * edit 更新
 * remove 删除
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class SimpleExecutor implements Executor {

    /**
     * 查询信息
     *
     * @param config    配置信息
     * @param method    方法信息
     * @param args 参数
     * @param <T>       泛型
     * @return 结果
     * @since 0.0.1
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T query(final Config config,
                       MapperMethod method,
                       Object[] args) {
        //1. 基本信息
        String sql = method.getSql();

        //1.1 直接动态替换掉 ${}
        ISqlReplace sqlReplace = new SimpleSqlReplace();
        Map<String, Object> paramMap = buildParamNameValue(method, args);
        SqlReplaceResult replaceResult = sqlReplace.replace(sql, paramMap);

        //1.2 构建好入参名称和 ? 位置关系，使用 ps 安全替换
        sql = replaceResult.sql();
        System.out.println("【sql】" + sql);
        List<String> psNames = replaceResult.psNames();

        try(Connection connection = config.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            // 2. 处理参数
            ParameterHandler parameterHandler = new ParameterHandler(preparedStatement, config);

            //2.2 对 args 进行一次加工，只保留和顺序一致的，必须的信息。
            parameterHandler.setParams(psNames, paramMap);

            // 3. 执行方法
            preparedStatement.execute();

            // 4. 处理结果
            ResultSet resultSet = preparedStatement.getResultSet();
            ResultHandler resultHandler = new ResultHandler(method, config);
            Object result = resultHandler.buildResult(resultSet);
            return (T) result;
        } catch (SQLException ex) {
            throw new MybatisException(ex);
        }
    }

    @Override
    public int update(Config config, MapperMethod method, Object[] args) {
        //1. 基本信息
        String sql = method.getSql();

        //1.1 直接动态替换掉 ${}
        ISqlReplace sqlReplace = new SimpleSqlReplace();
        Map<String, Object> paramMap = buildParamNameValue(method, args);
        SqlReplaceResult replaceResult = sqlReplace.replace(sql, paramMap);

        //1.2 构建好入参名称和 ? 位置关系，使用 ps 安全替换
        sql = replaceResult.sql();
        System.out.println("【sql】" + sql);
        List<String> psNames = replaceResult.psNames();

        try(Connection connection = config.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            // 2. 处理参数
            ParameterHandler parameterHandler = new ParameterHandler(preparedStatement, config);

            //2.2 对 args 进行一次加工，只保留和顺序一致的，必须的信息。
            parameterHandler.setParams(psNames, paramMap);

            // 3. 执行方法
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new MybatisException(ex);
        }
    }

    /**
     * 构建参数与名称的映射关系
     * @param mapperMethod 映射名称
     * @param args 参数关系
     * @return 结果
     * @since 0.0.13
     */
    private Map<String, Object> buildParamNameValue(final MapperMethod mapperMethod,
                                                    final Object[] args) {
        //1. 保证顺序
        Map<String, Object> resultMap = new LinkedHashMap<>();
        if(ArrayUtil.isEmpty(args)) {
            return resultMap;
        }

        // 获取参数信息
        Method method = mapperMethod.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();

        for(int i = 0; i < annotations.length; i++) {
            String paramName = "arg"+i;

            String annotationValue = getParamValue(annotations[i]);
            if(StringUtil.isNotEmpty(annotationValue)) {
                paramName = annotationValue;
            }

            resultMap.put(paramName, args[i]);
        }

        return resultMap;
    }

    /**
     * 获取参数对应的值
     * @param annotations 注解列表
     * @return 参数名称
     * @since 0.0.13
     */
    private String getParamValue(Annotation[] annotations) {
        if(ArrayUtil.isEmpty(annotations)) {
            return StringUtil.EMPTY;
        }

        for(Annotation annotation : annotations) {
            if(annotation.annotationType().equals(Param.class)) {
                Param param = (Param)annotation;
                return param.value();
            }
        }

        return StringUtil.EMPTY;
    }


}
