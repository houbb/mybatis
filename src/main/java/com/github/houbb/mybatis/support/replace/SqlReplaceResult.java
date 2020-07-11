package com.github.houbb.mybatis.support.replace;

import java.util.List;

/**
 * sql 替换结果
 * @since 0.0.13
 */
public class SqlReplaceResult {

    /**
     * 新的 sql
     * @since 0.0.13
     */
    private String sql;

    /**
     * 按顺序保存的 #{} 字段信息
     * @since 0.0.13
     */
    private List<String> psNames;

    /**
     * 新建对象实例
     * @since 0.0.13
     * @return 结果
     */
    public static SqlReplaceResult newInstance() {
        return new SqlReplaceResult();
    }

    public String sql() {
        return sql;
    }

    public SqlReplaceResult sql(String sql) {
        this.sql = sql;
        return this;
    }

    public List<String> psNames() {
        return psNames;
    }

    public SqlReplaceResult psNames(List<String> psNames) {
        this.psNames = psNames;
        return this;
    }
}
