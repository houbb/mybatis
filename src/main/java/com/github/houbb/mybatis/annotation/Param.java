package com.github.houbb.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数信息
 *
 * TODO: 这里主要是针对 jdk7 及其以前的反射无法直接获取 parameter 名称处理。
 *
 * 后期可以优化为使用 asm 直接获取。
 * @author binbin.hou
 * @since 0.0.13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {

    /**
     * 值信息
     * @return 值
     * @since 0.0.13
     */
    String value();

}
