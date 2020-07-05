package com.github.houbb.mybatis.constant;

/**
 *
 * https://www.jianshu.com/p/8b95cef31a53
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public final class DataSourceConst {

    private DataSourceConst(){}

    public static final String JDBC_PREFIX = "jdbc.";

    public static final String URL = "jdbc.url";

    public static final String DRIVER = "jdbc.driver";

    public static final String USERNAME = "jdbc.username";

    public static final String PASSWORD = "jdbc.password";

    /**
     * mapper 的扫描路径
     * @since 0.0.1
     */
    public static final String MAPPER_SCAN = "mapper.scan";

}
