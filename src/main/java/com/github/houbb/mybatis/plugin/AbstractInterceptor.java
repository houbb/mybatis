package com.github.houbb.mybatis.plugin;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
public abstract class AbstractInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        before(invocation);

        Object result = invocation.process();

        after(invocation, result);

        return result;
    }


    /**
     * 前置拦截
     * @param invocation 上下文
     * @since 0.0.2
     */
    abstract void before(Invocation invocation);

    /**
     * 后置拦截
     * @param invocation 上下文
     * @param result 执行结果
     * @since 0.0.2
     */
    abstract void after(Invocation invocation, Object result);

}
