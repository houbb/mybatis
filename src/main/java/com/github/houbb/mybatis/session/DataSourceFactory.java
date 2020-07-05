package com.github.houbb.mybatis.session;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface DataSourceFactory {

    /**
     * 设置配置信息
     * @param propertiesMap 配置信息
     * @since 0.0.8
     */
    void setProperties(Properties propertiesMap);

    /**
     * 获取数据源信息
     * @return 数据源信息
     * @since 0.0.8
     */
    DataSource getDataSource();

}
