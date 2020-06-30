package com.github.houbb.mybatis.mapper;

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
     * 方法名称
     *
     * @since 0.0.1
     */
    private String methodName;

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

    @Override
    public String toString() {
        return "MapperMethod{" +
                "type='" + type + '\'' +
                ", sql='" + sql + '\'' +
                ", methodName='" + methodName + '\'' +
                ", resultType=" + resultType +
                ", paramType=" + paramType +
                '}';
    }

}
