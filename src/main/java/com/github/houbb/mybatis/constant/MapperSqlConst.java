package com.github.houbb.mybatis.constant;

/**
 * mapper 中语句的类型
 * @since 0.0.13
 */
public final class MapperSqlConst {

    private MapperSqlConst(){}

    /**
     * 替换前缀
     * @since 0.0.13
     */
    public static final String REPLACE_PREFIX = "${";

    /**
     * 替换后缀
     * @since 0.0.13
     */
    public static final String REPLACE_SUFFIX = "}";

    /**
     * 需要 ps 替换的前缀
     * @since 0.0.13
     */
    public static final String PS_PREFIX = "#{";

    /**
     * 需要 ps 替换的后缀
     * @since 0.0.13
     */
    public static final String PS_SUFFIX = "}";

    /**
     * 占位符
     * @since 0.0.13
     */
    public static final String PLACEHOLDER = "?";

    /**
     * 单引号
     *
     * 针对数据库的 char 字段。
     * @since 0.0.13
     */
    public static final String SINGLE_CHAR = "'";

}
