package com.github.houbb.mybatis.executor.impl;

import com.github.houbb.heaven.support.pipeline.Pipeline;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.mybatis.annotation.Param;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.executor.Executor;
import com.github.houbb.mybatis.handler.param.ParameterHandler;
import com.github.houbb.mybatis.handler.result.ResultHandler;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.mapper.MapperSqlItem;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;
import com.github.houbb.mybatis.support.replace.impl.IfSqlReplace;
import com.github.houbb.mybatis.support.replace.impl.PlaceholderSqlReplace;
import com.github.houbb.mybatis.support.replace.impl.SqlReplaceChains;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        final Map<String, Object> paramMap = buildParamNameValue(method, args);

        //2. 进行参数的替换
        SqlReplaceResult replaceResult = doReplace(method, paramMap);
        //2.1
        List<String> psNames = replaceResult.psNames();
        //2.2 sql
        String sql = buildPsSql(replaceResult.dynamicSqlItems());
        System.out.println("【sql】" + sql);

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
        final Map<String, Object> paramMap = buildParamNameValue(method, args);

        //2. 进行参数的替换
        SqlReplaceResult replaceResult = doReplace(method, paramMap);
        //2.1
        List<String> psNames = replaceResult.psNames();
        //2.2 sql
        String sql = buildPsSql(replaceResult.dynamicSqlItems());
        System.out.println("【sql】" + sql);

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

    /**
     * 构建 ps SQL
     * @param sqlItems 对应的 sql 信息
     * @return 结果
     * @since 0.0.16
     */
    private String buildPsSql(final List<MapperSqlItem> sqlItems) {
        StringBuilder sqlBuffer = new StringBuilder();

        for(MapperSqlItem mapperSqlItem : sqlItems) {
            if(mapperSqlItem.isReadyForSql()) {
                String sqlTrim = mapperSqlItem.getSql().trim();
                sqlBuffer.append(sqlTrim).append(StringUtil.BLANK);
            }
        }
        return sqlBuffer.toString().trim();
    }

    /**
     * 执行替换
     *
     * TODO: 动态替换会导致不同的条件值不对的问题。
     * 避免对原来的 mapper 产生影响。
     *
     * 1. 这里还存在一个问题，如果 test 中包含 include 依赖的话，那么顺序应该再次调整下。
     *
     * include 这个可以先替换掉。
     *
     * test
     * placeholder
     *
     * @param mapperMethod 方法信息
     * @param paramMap 参数信息
     * @return 替换结果
     * @since 0.0.16
     */
    private SqlReplaceResult doReplace(final MapperMethod mapperMethod,
                                       final Map<String, Object> paramMap) {

        List<MapperSqlItem> dynamicSqlItems = deepCopySqlItems(mapperMethod);
        SqlReplaceResult replaceResult = SqlReplaceResult.newInstance()
                .mapperMethod(mapperMethod)
                .paramMap(paramMap)
                .dynamicSqlItems(dynamicSqlItems);

        ISqlReplace replace = new SqlReplaceChains() {
            @Override
            protected void init(Pipeline<ISqlReplace> pipeline) {
                //1. 首先处理 if 判断
                pipeline.addLast(new IfSqlReplace());
                //2. 然后处理 占位符
                pipeline.addLast(new PlaceholderSqlReplace());
            }
        };

        return replace.replace(replaceResult);
    }

    /**
     * 深度拷贝
     *
     * @param mapperMethod 方法
     * @return 结果
     * @since 0.0.16
     */
    private List<MapperSqlItem> deepCopySqlItems(final MapperMethod mapperMethod) {
        List<MapperSqlItem> originalSqlItems = mapperMethod.getSqlItemList();
        List<MapperSqlItem> dynamicSqlItems = new ArrayList<>(originalSqlItems.size());
        for(MapperSqlItem old : originalSqlItems) {
            MapperSqlItem dynamic = new MapperSqlItem();
            dynamic.setType(old.getType());
            dynamic.setSql(old.getSql());
            dynamic.setReadyForSql(old.isReadyForSql());
            dynamic.setTestCondition(old.getTestCondition());
            dynamic.setRefId(old.getRefId());
            dynamicSqlItems.add(dynamic);
        }

        return dynamicSqlItems;
    }

}
