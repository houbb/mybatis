package com.github.houbb.mybatis.constant;

/**
 * mapper foreach 常量
 * @since 0.0.17
 */
public final class MapperForeachConst {

    private MapperForeachConst(){}

    /**
     * 开始符号
     * @since 0.0.11
     */
    public static final String OPEN = "(";

    /**
     * 关闭符号
     * @since 0.0.17
     */
    public static final String CLOSE = ")";

    /**
     * 分割符号
     * @since 0.0.17
     */
    public static final String SEPARATOR = ",";

    /**
     * 集合名称
     * @since 0.0.17
     */
    public static final String COLLECTION = "list";

    /**
     * 元素名称
     * @since 0.0.17
     */
    public static final String ITEM = "item";

    /**
     * 下标名称
     *
     * 当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。
     * 当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。
     *
     * @since 0.0.17
     */
    public static final String INDEX = "index";

    /**
     * 分隔符
     * @since 0.0.17
     */
    public static final String SPLITTER = ":";

}
