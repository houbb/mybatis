package com.github.houbb.mybatis.support.replace;

import com.github.houbb.mybatis.mapper.MapperMethod;

import java.util.List;
import java.util.Map;

/**
 * sql 替换结果
 * @since 0.0.13
 */
public class SqlReplaceResult {

    /**
     * 按顺序保存的 #{} 字段信息
     * @since 0.0.13
     */
    private List<String> psNames;

    /**
     * 对应的方法信息
     * @since 0.0.16
     */
    private MapperMethod mapperMethod;

    /**
     * 参数集合
     * @since 0.0.16
     */
    private Map<String, Object> paramMap;

    /**
     * 新建对象实例
     * @since 0.0.13
     * @return 结果
     */
    public static SqlReplaceResult newInstance() {
        return new SqlReplaceResult();
    }

    public List<String> psNames() {
        return psNames;
    }

    public SqlReplaceResult psNames(List<String> psNames) {
        this.psNames = psNames;
        return this;
    }

    public Map<String, Object> paramMap() {
        return paramMap;
    }

    public SqlReplaceResult paramMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
        return this;
    }

    public MapperMethod mapperMethod() {
        return mapperMethod;
    }

    public SqlReplaceResult mapperMethod(MapperMethod mapperMethod) {
        this.mapperMethod = mapperMethod;
        return this;
    }
}
