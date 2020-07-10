package com.github.houbb.mybatis.mapper;

/**
 * sql 模板
 * @author binbin.hou
 * @since 0.0.11
 */
public class MapperSqlTemplate {

    /**
     * 唯一标识
     * @since 0.0.11
     */
    private String id;

    /**
     * sql 本身
     * @since 0.0.1
     */
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "MapperSqlTemplate{" +
                "id='" + id + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }

}
