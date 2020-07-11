package com.github.houbb.mybatis.mapper;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class MapperMethod {

    /**
     * select
     * update
     * delete
     * insert
     *
     * 类型
     * @since 0.0.1
     */
    private String type;

    /**
     * sql 本身
     * @since 0.0.1
     */
    private String sql;

    /**
     * sql item 列表
     * @since 0.0.11
     */
    private List<MapperSqlItem> sqlItemList;

    /**
     * 方法名称
     *
     * @since 0.0.1
     */
    private String methodName;

    /**
     * 对应的方法元数据信息
     * @since 0.0.10
     */
    private Method method;

    /**
     * 结果类型
     * @since 0.0.1
     */
    private Class<?> resultType;

    /**
     * 参数类型
     *
     * @since 0.0.1
     */
    private Class<?> paramType;

    /**
     * 映射结果集合
     * @since 0.0.12
     */
    private String resultMap;


    /**
     * 依赖的 class 信息
     *
     * TODO: 后期这里做一些优化，简化这种依赖关系
     * @since 0.0.12
     */
    private MapperClass refClass;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<MapperSqlItem> getSqlItemList() {
        return sqlItemList;
    }

    public void setSqlItemList(List<MapperSqlItem> sqlItemList) {
        this.sqlItemList = sqlItemList;
    }

    public String getResultMap() {
        return resultMap;
    }

    public void setResultMap(String resultMap) {
        this.resultMap = resultMap;
    }

    public MapperClass getRefClass() {
        return refClass;
    }

    public void setRefClass(MapperClass refClass) {
        this.refClass = refClass;
    }

}
