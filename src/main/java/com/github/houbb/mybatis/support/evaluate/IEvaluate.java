package com.github.houbb.mybatis.support.evaluate;

import java.util.Map;

/**
 * 校验接口
 * @since 0.1.16
 */
public interface IEvaluate {

    /**
     * 进行值及校验
     * @param expression 表达式
     * @return 结果
     * @since 0.0.16
     */
    Object evaluate(final String expression);

}
