package com.github.houbb.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链拦截器
 * @since 0.0.2
 */
public class InterceptorChain {

    /**
     * 拦截器列表
     * @since 0.0.2
     */
    private final List<Interceptor> interceptorList = new ArrayList<>();

    /**
     * 添加拦截器
     * @param interceptor 拦截器
     * @return this
     * @since 0.0.2
     */
    public synchronized InterceptorChain add(Interceptor interceptor) {
        interceptorList.add(interceptor);

        return this;
    }

    /**
     * 添加拦截器
     * @param interceptorList 拦截器列表
     * @return this
     * @since 0.0.2
     */
    public synchronized InterceptorChain add(List<Interceptor> interceptorList) {
        for(Interceptor interceptor : interceptorList) {
            this.add(interceptor);
        }

        return this;
    }

    /**
     * 代理所有
     * @param target 目标类
     * @return 结果
     * @since 0.0.2
     */
    public Object pluginAll(Object target) {
        for(Interceptor interceptor : interceptorList) {
            target = DefaultInvocationHandler.proxy(target, interceptor);
        }

        return target;
    }

}
