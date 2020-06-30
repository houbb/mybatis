//package com.github.houbb.mybatis.plugin;
//
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
//public class MyInvocationHandler implements InvocationHandler {
//
//    private Object target;
//
//    private Interceptor interceptor;
//
//    public MyInvocationHandler(Object target, Interceptor interceptor) {
//        this.target = target;
//        this.interceptor = interceptor;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        Invocation invocation = new Invocation(target, method, args);
//        // 返回的依然是代理类的结果
//        return interceptor.(invocation);
//    }
//
//    public static Object wrap(Object target, Interceptor interceptor) {
//        MyInvocationHandler targetProxy = new MyInvocationHandler(target, interceptor);
//        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
//                target.getClass().getInterfaces(),
//                targetProxy);
//    }
//
//    public static void main(String[] args) {
//        HelloService target = new HelloServiceImpl();
//        //1. 拦截器1
//        Interceptor interceptor = new MyLogInterceptor();
//        target = (HelloService) interceptor.plugin(target);
//
//        //2. 拦截器 2
//        Interceptor interceptor2 = new MyTransactionInterceptor();
//        target = (HelloService) interceptor2.plugin(target);
//
//        // 调用
//        target.sayHello();
//    }
//
//}
