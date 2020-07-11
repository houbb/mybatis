package com.github.houbb.mybatis.mapper.component;

/**
 * 结果集映射元素
 * @since 0.0.12
 */
public class MapperResultMapItem {

    /**
     * 列名称
     * @since 0.0.12
     */
    private String column;

    /**
     * 属性名称
     * @since 0.0.12
     */
    private String property;

    /**
     * java 类型
     * @since 0.0.12
     */
    private String javaType;

    /**
     * jdbc 类型
     * @since 0.0.12
     */
    private String jdbcType;

    /**
     * 类型处理类
     * @since 0.0.12
     */
    private String typeHandler;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }
}
