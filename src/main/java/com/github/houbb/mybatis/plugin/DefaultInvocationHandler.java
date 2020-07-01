package com.github.houbb.mybatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 默认的代理实现
 * @since 0.0.2
 * @author binbin.hou
 */
public class DefaultInvocationHandler implements InvocationHandler {

    /**
     * 代理类
     * @since 0.0.2
     */
    private final Object target;

    /**
     * 拦截器
     * @since 0.0.2
     */
    private final Interceptor interceptor;

    public DefaultInvocationHandler(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation(target, method, args);
        return interceptor.intercept(invocation);
    }

    /**
     * 构建代理
     * @param target 目标对象
     * @param interceptor 拦截器
     * @return 代理
     * @since 0.0.2
     */
    public static Object proxy(Object target, Interceptor interceptor) {
        DefaultInvocationHandler targetProxy = new DefaultInvocationHandler(target, interceptor);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                targetProxy);
    }

}
