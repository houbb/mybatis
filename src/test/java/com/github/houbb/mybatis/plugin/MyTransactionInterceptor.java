//package com.github.houbb.mybatis.plugin;
//
//public class MyTransactionInterceptor implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Exception {
//        System.out.println("------tx start-------------");
//        Object result = invocation.process();
//        System.out.println("------tx end-------------");
//        return result;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return MyInvocationHandler.wrap(target, this);
//    }
//
//}
