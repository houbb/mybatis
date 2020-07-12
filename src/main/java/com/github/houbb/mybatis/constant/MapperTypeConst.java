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
     * 是否满足条件
     * @since 0.0.16
     */
    public static final String IF = "if";

    /**
     * foreach 循环的支持
     *
     * 你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 foreach。
     *
     * @since 0.0.17
     */
    public static final String FOREACH = "foreach";

    /**
     * 结果
     * @since 0.0.12
     */
    public static final String RESULT = "result";

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
