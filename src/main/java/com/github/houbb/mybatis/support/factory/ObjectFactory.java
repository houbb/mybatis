package com.github.houbb.mybatis.support.factory;

/**
 * @author binbin.hou
 * @since 0.0.6
 */
public interface ObjectFactory {

    /**
     * Creates a new object with default constructor.
     * @param type Object type
     * @return 對象結果
     * @since 0.0.6
     * @param <T> 泛型
     */
    <T> T newInstance(Class<T> type);

}
