package com.github.houbb.mybatis.handler.result;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.mapper.component.MapperResultMapItem;

import java.sql.ResultSet;
import java.util.List;

/**
 * 结果处理上下文
 * @since 0.0.12
 */
public class ResultHandlerContext {

    /**
     * 结果类型
     * @since 0.0.12
     */
    private Class<?> resultType;

    /**
     * 配置信息
     * @since 0.0.12
     */
    private Config config;

    /**
     * 结果映射标识
     * @since 0.0.12
     */
    private String resultMapId;

    /**
     * 结果映射信息信息
     * @since 0.0.12
     */
    private List<MapperResultMapItem> resultMapMapping;

    /**
     * 结果集
     * @since 0.0.12
     */
    private ResultSet resultSet;

    /**
     * 新建对象实例
     * @since 0.0.12
     * @return 结果
     */
    public static ResultHandlerContext newInstance() {
        return new ResultHandlerContext();
    }

    public Class<?> resultType() {
        return resultType;
    }

    public ResultHandlerContext resultType(Class<?> resultType) {
        this.resultType = resultType;
        return this;
    }

    public Config config() {
        return config;
    }

    public ResultHandlerContext config(Config config) {
        this.config = config;
        return this;
    }

    public List<MapperResultMapItem> resultMapMapping() {
        return resultMapMapping;
    }

    public ResultHandlerContext resultMapMapping(List<MapperResultMapItem> resultMapMapping) {
        this.resultMapMapping = resultMapMapping;
        return this;
    }

    public ResultSet resultSet() {
        return resultSet;
    }

    public ResultHandlerContext resultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
        return this;
    }

    public String resultMapId() {
        return resultMapId;
    }

    public ResultHandlerContext resultMapId(String resultMapId) {
        this.resultMapId = resultMapId;
        return this;
    }
}
