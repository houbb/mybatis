package com.github.houbb.mybatis.plugin;

import java.util.Arrays;

public class SimpleLogInterceptor implements Interceptor{
    @Override
    public void before(Invocation invocation) {
        System.out.println("----param: " + Arrays.toString(invocation.getArgs()));
    }

    @Override
    public void after(Invocation invocation, Object result) {
        System.out.println("----result: " + result);
    }

}
