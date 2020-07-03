package com.github.houbb.mybatis.support.factory.impl;

import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.mybatis.support.factory.ObjectFactory;

/**
 * 默认实现
 *
 * @author binbin.hou
 * @since 0.0.6
 */
public class DefaultObjectFactory implements ObjectFactory {

    /**
     * Creates a new object with default constructor.
     *
     * @param type Object type
     * @return 對象結果
     * @since 0.0.6
     */
    @Override
    public <T> T newInstance(Class<T> type) {
        return ClassUtil.newInstance(type);
    }

}
