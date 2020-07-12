package com.github.houbb.mybatis.mapper.component;

/**
 * foreach 元素信息
 * @since 0.0.17
 */
public class MapperForeachProperty {

    /**
     * 元素别称
     * @since 0.0.17
     */
    private String item;

    /**
     * 元素下标或者是 key
     */
    private String index;

    /**
     * 开始符号
     * @since 0.0.17
     */
    private String open;

    /**
     * 结束符号
     * @since 0.0.17
     */
    private String close;

    /**
     * 集合名称
     *
     * 为了简化，此处必须拥有这个属性信息。
     * @since 0.0.17
     */
    private String collection;

    /**
     * 分隔符号
     * @since 0.0.17
     */
    private String separator;

    public static MapperForeachProperty newInstance() {
        return new MapperForeachProperty();
    }

    public String item() {
        return item;
    }

    public MapperForeachProperty item(String item) {
        this.item = item;
        return this;
    }

    public String index() {
        return index;
    }

    public MapperForeachProperty index(String index) {
        this.index = index;
        return this;
    }

    public String open() {
        return open;
    }

    public MapperForeachProperty open(String open) {
        this.open = open;
        return this;
    }

    public String close() {
        return close;
    }

    public MapperForeachProperty close(String close) {
        this.close = close;
        return this;
    }

    public String collection() {
        return collection;
    }

    public MapperForeachProperty collection(String collection) {
        this.collection = collection;
        return this;
    }

    public String separator() {
        return separator;
    }

    public MapperForeachProperty separator(String separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public String toString() {
        return "MapperForeachProperty{" +
                "item='" + item + '\'' +
                ", index='" + index + '\'' +
                ", open='" + open + '\'' +
                ", close='" + close + '\'' +
                ", collection='" + collection + '\'' +
                ", separator='" + separator + '\'' +
                '}';
    }

}
