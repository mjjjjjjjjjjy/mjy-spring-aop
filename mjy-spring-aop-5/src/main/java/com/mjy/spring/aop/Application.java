package com.mjy.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 下午5:16
 * @Modified By
 */
public class Application {

    @Component
    static class Duck{
        public void run(){
            System.out.println("小鸭快跑");
        }
    }

    @Aspect
    @Component
    @EnableAspectJAutoProxy
    static class Intercepter{

        @Before("execution(* com.mjy.spring.aop.Application..*.*(..))")
        public void before(){
            System.out.println("Aspect: 预备。。。");
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Duck.class, Intercepter.class);
        Duck bean = annotationConfigApplicationContext.getBean(Duck.class);
        bean.run();
    }
}
