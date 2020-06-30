//package com.github.houbb.mybatis.plugin;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class InterceptorChain {
//
//    private List<Interceptor> interceptorList = new ArrayList<>();
//
//    /**
//     * 插入所有拦截器
//     */
//    public Object pluginAll(Object target) {
//        for (Interceptor interceptor : interceptorList) {
//            target = interceptor.plugin(target);
//        }
//        return target;
//    }
//
//    public void addInterceptor(Interceptor interceptor) {
//        interceptorList.add(interceptor);
//    }
//    /**
//     * 返回一个不可修改集合，只能通过addInterceptor方法添加
//     * 这样控制权就在自己手里
//     */
//    public List<Interceptor> getInterceptorList() {
//        return Collections.unmodifiableList(interceptorList);
//    }
//
//    public static void main(String[] args) {
//        HelloService target = new HelloServiceImpl();
//
//        Interceptor interceptor = new MyLogInterceptor();
//        Interceptor interceptor2 = new MyTransactionInterceptor();
//        InterceptorChain chain = new InterceptorChain();
//        chain.addInterceptor(interceptor);
//        chain.addInterceptor(interceptor2);
//        target = (HelloService) chain.pluginAll(target);
//
//        // 调用
//        target.sayHello();
//    }
//
//}
