package com.github.houbb.mybatis.plugin;

/**
 * 拦截器
 *
 * 1. 所有实现类必须要有无参构造器
 * 2. 统一为一个接口，便于执行 Tx 管理等。
 * @since 0.0.2
 * @author binbin.hou
 */
public interface Interceptor {

    /**
     * 执行拦截
     * @param invocation 上下文
     * @return 结果
     * @throws Exception 异常
     * @since 0.0.3
     */
    Object intercept(Invocation invocation) throws Exception;


}
