package com.github.houbb.mybatis.constant;

/**
 * mapper 中语句的类型
 * @since 0.0.11
 */
public final class MapperTypeConst {

    private MapperTypeConst(){}

    /**
     * 包含模板
     * @since 0.0.11
     */
    public static final String INCLUDE = "include";

    /**
     * sql 模板
     * @since 0.0.11
     */
    public static final String SQL = "sql";

    /**
     * 结果映射
     * @since 0.0.11
     */
    public static final String RESULT_MAP = "resultMap";

    /**
     * 查询
     * @since 0.0.11
     */
    public static final String SELECT = "select";

    /**
     * 删除
     * @since 0.0.11
     */
    public static final String DELETE = "delete";

    /**
     * 更新
     * @since 0.0.11
     */
    public static final String UPDATE = "update";

    /**
     * 插入
     * @since 0.0.11
     */
    public static final String INSERT = "insert";

}
