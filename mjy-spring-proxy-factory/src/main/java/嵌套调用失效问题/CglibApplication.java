package 嵌套调用失效问题;

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
public class CglibApplication {
    static class Duck{
        public void run(){
            System.out.println("小鸭快跑");
        }

        public void eat(){
            System.out.println("小鸭干饭");
            run();
        }
    }
    public static void main(String[] args) {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");

        Duck duck = new Duck();
        //传入一个即将被代理的类
        MethodBeforeAdvice methodBeforeAdvice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("方法名"+method.getName()+"--嵌套调用-前置通知");
            }
        };
        ProxyFactory proxyFactory = new ProxyFactory(duck);
        proxyFactory.addAdvice(methodBeforeAdvice);
        proxyFactory.setExposeProxy(true);
        //addAdvice(Advice advice) 底层是封装成为一个Advisor的，因此也可以使用addAdvisor(Advisor advisor)方法
        Object proxy = proxyFactory.getProxy();
        Duck proxyDuck = (Duck) proxy;
        System.out.println(proxyDuck.getClass().getName());
        proxyDuck.eat();
    }
}
