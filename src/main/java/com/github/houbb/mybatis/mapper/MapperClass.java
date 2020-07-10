package com.github.houbb.mybatis.mapper;

import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class MapperClass {

    /**
     * 接口名称（命名空间）
     * @since 0.0.1
     */
    private String namespace;

    /**
     * 方法列表
     * @since 0.0.1
     */
    private List<MapperMethod> methodList;

    /**
     * sql 模板列表
     * @since 0.0.11
     */
    private List<MapperSqlTemplate> sqlTemplateList;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<MapperMethod> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<MapperMethod> methodList) {
        this.methodList = methodList;
    }

    public List<MapperSqlTemplate> getSqlTemplateList() {
        return sqlTemplateList;
    }

    public void setSqlTemplateList(List<MapperSqlTemplate> sqlTemplateList) {
        this.sqlTemplateList = sqlTemplateList;
    }

    @Override
    public String toString() {
        return "MapperClass{" +
                "namespace='" + namespace + '\'' +
                ", methodList=" + methodList +
                ", sqlTemplateList=" + sqlTemplateList +
                '}';
    }

}
