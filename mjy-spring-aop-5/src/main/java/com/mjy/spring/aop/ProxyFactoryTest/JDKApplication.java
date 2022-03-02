package com.mjy.spring.aop.ProxyFactoryTest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 下午8:00
 * @Modified By
 */
public class JDKApplication {
    interface Run{
        void run();
    }
    static class Duck implements Run{
        @Override
        public void run(){
            System.out.println("小鸭快跑");
        }
    }

    static class MethodInterceptorC implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("环绕通知前 ");
            Object proceed = invocation.proceed();
            System.out.println("环绕通知后 ");
            return proceed;
        }
    };

    public static void main(String[] args) {
        Duck duck = new Duck();
        //传入一个即将被代理的类
        MethodBeforeAdvice methodBeforeAdvice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("前置通知");
            }
        };

        AfterReturningAdvice afterReturningAdvice = new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("后置通知");
            }
        };



        ProxyFactory proxyFactory = new ProxyFactory(duck);
        proxyFactory.addAdvice(methodBeforeAdvice);
        proxyFactory.addAdvice(new MethodInterceptorC());
        //addAdvice(Advice advice) 底层是封装成为一个Advisor的，因此也可以使用addAdvisor(Advisor advisor)方法
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(afterReturningAdvice));
        Run proxyDuck = (Run) proxyFactory.getProxy();
        proxyDuck.run();
        //输出：
        //前置通知
        //环绕通知前
        //小鸭快跑
        //后置通知
        //环绕通知后
    }
}
