//package com.github.houbb.mybatis.plugin;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.ArrayList;
//import java.util.List;
//
//public class InterfaceProxy implements InvocationHandler {
//
//    private Object target;
//
//    private List<InterceptorV1> interceptorList = new ArrayList<>();
//
//    public InterfaceProxy(Object target, List<InterceptorV1> interceptorList) {
//        this.target = target;
//        this.interceptorList = interceptorList;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        //处理多个拦截器
//        for (InterceptorV1 interceptor : interceptorList) {
//            interceptor.intercept();
//        }
//        return method.invoke(target, args);
//    }
//
//    public static Object wrap(Object target, List<InterceptorV1> interceptorList) {
//        InterfaceProxy targetProxy = new InterfaceProxy(target, interceptorList);
//        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
//                target.getClass().getInterfaces(), targetProxy);
//    }
//
//    public static void main(String[] args) {
//        List<InterceptorV1> interceptorList = new ArrayList<>();
//        interceptorList.add(new LogInterceptor());
//
//        HelloService target = new HelloServiceImpl();
//        HelloService targetProxy = (HelloService) InterfaceProxy.wrap(target, interceptorList);
//        targetProxy.sayHello();
//    }
//
//}
