package com.github.houbb.mybatis.constant;

/**
 * mapper 中语句的类型
 * @since 0.0.11
 */
public final class MapperAttrConst {

    private MapperAttrConst(){}

    /**
     * 包含模板
     * @since 0.0.12
     */
    public static final String ID = "id";

    /**
     * 引用标识
     * @since 0.0.12
     */
    public static final String REF_ID = "refid";

    /**
     * if 对应的条件语句
     * @since 0.0.16
     */
    public static final String TEST = "test";

    /**
     * 参数类型
     * @since 0.0.12
     */
    public static final String PARAM_TYPE = "paramType";

    /**
     * 结果类型
     * @since 0.0.12
     */
    public static final String RESULT_TYPE = "resultType";

    /**
     * 结果 MAP
     * @since 0.0.12
     */
    public static final String RESULT_MAP = "resultMap";

    /**
     * java 类型
     * @since 0.0.12
     */
    public static final String JAVA_TYPE = "javaType";

    /**
     * 列
     * @since 0.0.12
     */
    public static final String COLUMN = "column";

    /**
     * 属性
     * @since 0.0.12
     */
    public static final String PROPERTY = "property";

    /**
     * jdbc 类型
     * @since 0.0.12
     */
    public static final String JDBC_TYPE = "jdbcType";

    /**
     * 类型处理类
     * @since 0.0.12
     */
    public static final String TYPE_HANDLER = "typeHandler";

    /**
     * 命名空间
     * @since 0.0.12
     */
    public static final String NAMESPACE = "namespace";

    /**
     * 它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。
     *
     * 当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。
     * 当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。
     * @since 0.0.17
     */
    public static final String ITEM = "item";

    /**
     * 它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。
     *
     * 当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。
     * 当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。
     * @since 0.0.17
     */
    public static final String INDEX = "index";

    /**
     * 集合的名称
     * @since 0.0.17
     */
    public static final String COLLECTION = "collection";

    /**
     * 开始的元素
     * @since 0.0.17
     */
    public static final String OPEN = "open";

    /**
     * 结束的元素
     * @since 0.0.17
     */
    public static final String CLOSE = "close";

    /**
     * 分隔符号
     * @since 0.0.17
     */
    public static final String SEPARATOR = "separator";

}
