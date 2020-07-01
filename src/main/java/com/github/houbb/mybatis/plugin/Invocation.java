package com.github.houbb.mybatis.plugin;

import java.lang.reflect.Method;
import java.util.Map;

public class Invocation {

    /**
     * 目标对象
     * @since 0.0.2
     */
    private Object target;

    /**
     * 执行的方法
     * @since 0.0.2
     */
    private Method method;

    /**
     * 方法的参数
     * @since 0.0.2
     */
    private Object[] args;

    /**
     * 额外的属性
     *
     * 便于用户自行定义
     *
     * TODO: 后期看情况进行优化
     * @since 0.0.2
     */
    private Map<String, Object> extraMap;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    /**
     * 执行方法调用
     * @return 结果
     * @throws Exception 异常
     * @since 0.0.3
     */
    public Object process() throws Exception {
        return method.invoke(target, args);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Map<String, Object> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, Object> extraMap) {
        this.extraMap = extraMap;
    }
}
