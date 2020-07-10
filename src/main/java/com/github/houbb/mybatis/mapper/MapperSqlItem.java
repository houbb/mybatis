package com.github.houbb.mybatis.mapper;

/**
 * sql 元素片段
 * @since 0.0.11
 */
public class MapperSqlItem {

    /**
     * 类别
     * @since 0.0.11
     */
    private String type;

    /**
     * 纯 sql
     * @since 0.0.11
     */
    private String sql;

    /**
     * 引用标识
     * @since 0.0.11
     */
    private String refId;

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

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    @Override
    public String toString() {
        return "MapperSqlItem{" +
                "type='" + type + '\'' +
                ", sql='" + sql + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }

}
