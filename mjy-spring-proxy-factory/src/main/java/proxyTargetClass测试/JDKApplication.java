package proxyTargetClass测试;

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
        ProxyFactory proxyFactory = new ProxyFactory(duck);
        proxyFactory.addAdvice(new MethodInterceptorC());
        proxyFactory.setProxyTargetClass(true);
        Run proxyDuck = (Run) proxyFactory.getProxy();
        System.out.println(proxyDuck.getClass().getName());
    }
}
