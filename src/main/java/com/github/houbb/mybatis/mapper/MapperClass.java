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

    @Override
    public String toString() {
        return "MapperData{" +
                "namespace='" + namespace + '\'' +
                ", methodList=" + methodList +
                '}';
    }

}
