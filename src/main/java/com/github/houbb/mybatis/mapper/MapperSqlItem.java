package com.github.houbb.mybatis.mapper;

import com.github.houbb.mybatis.constant.enums.MapperSqlType;

/**
 * sql 元素片段
 * @since 0.0.11
 */
public class MapperSqlItem {

    /**
     * 类别
     * @since 0.0.11
     */
    private MapperSqlType type;

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

    /**
     * 测试的条件
     * @since 0.0.16
     */
    private String testCondition;

    /**
     * 已经处理好，可以直接进行 sql 的拼接
     *
     * 1. 保证原始的信息不变。
     * 2. 便于后期的优化拓展
     *
     * @since 0.0.16
     */
    private boolean readyForSql;

    public MapperSqlType getType() {
        return type;
    }

    public void setType(MapperSqlType type) {
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

    public String getTestCondition() {
        return testCondition;
    }

    public void setTestCondition(String testCondition) {
        this.testCondition = testCondition;
    }

    public boolean isReadyForSql() {
        return readyForSql;
    }

    public void setReadyForSql(boolean readyForSql) {
        this.readyForSql = readyForSql;
    }

    @Override
    public String toString() {
        return "MapperSqlItem{" +
                "type=" + type +
                ", sql='" + sql + '\'' +
                ", refId='" + refId + '\'' +
                ", testCondition='" + testCondition + '\'' +
                ", readyForSql=" + readyForSql +
                '}';
    }

}
