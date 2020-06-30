package com.github.houbb.mybatis.plugin;

/**
 * 拦截器
 *
 * 1. 所有实现类必须要有无参构造器
 * @since 0.0.2
 */
public interface Interceptor {

    /**
     * 前置拦截
     * @param invocation 上下文
     * @since 0.0.2
     */
    void before(Invocation invocation);

    /**
     * 后置拦截
     * @param invocation 上下文
     * @param result 执行结果
     * @since 0.0.2
     */
    void after(Invocation invocation, Object result);

}
