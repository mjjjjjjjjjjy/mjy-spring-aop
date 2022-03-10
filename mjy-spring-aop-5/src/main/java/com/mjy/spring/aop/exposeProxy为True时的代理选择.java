package com.mjy.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 下午5:16
 * @Modified By
 */
public class exposeProxy为True时的代理选择 {

    private interface Run{
        void run();
    }
    @Component
    static class Duck implements Run {
        @Override
        public void run(){
            System.out.println("小鸭快跑");
        }
    }

    @Aspect
    @Component
    @EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
    static class Intercepter{

        @Before("execution(* com..*.*(..))")
        public void before(){

            System.out.println("Aspect: 预备。。。");
            //使用线程变量获取代理类，必须设置exposeProxy = true
            Object o = AopContext.currentProxy();
            System.out.println(o.getClass().getName());
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Duck.class, Intercepter.class);
        Duck bean = annotationConfigApplicationContext.getBean(Duck.class);
        bean.run();


    }
}
