//package com.github.houbb.mybatis.plugin;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
//public class JdkDynamicProxy {
//
//    /**
//     * 一个接口
//     */
//    public interface HelloService{
//        void sayHello();
//    }
//
//    /**
//     * 目标类实现接口
//     */
//    static class HelloServiceImpl implements HelloService{
//
//        @Override
//        public void sayHello() {
//            System.out.println("sayHello......");
//        }
//
//    }
//
//    /**
//     * 自定义代理类需要实现InvocationHandler接口
//     */
//    static  class HelloInvocationHandler implements InvocationHandler {
//
//        private Object target;
//
//        public HelloInvocationHandler(Object target){
//            this.target = target;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println("------插入前置通知代码-------------");
//            //执行相应的目标方法
//            Object rs = method.invoke(target,args);
//            System.out.println("------插入后置处理代码-------------");
//            return rs;
//        }
//
//        public static Object wrap(Object target) {
//            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
//                    target.getClass().getInterfaces(),new HelloInvocationHandler(target));
//        }
//    }
//
//    public static void main(String[] args)  {
//        HelloService proxyService = (HelloService) HelloInvocationHandler.wrap(new HelloServiceImpl());
//        proxyService.sayHello();
//    }
//
//}
