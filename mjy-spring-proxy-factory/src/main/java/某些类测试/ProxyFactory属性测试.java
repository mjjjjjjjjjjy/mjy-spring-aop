package 某些类测试;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/5/30 下午4:52
 * @Modified By
 */
public class ProxyFactory属性测试 {

    interface HelloService {

        void hello(String name);

        void bye(String name);
    }
    public static class HelloServiceImpl implements ProxyFactoryTarget变量的探索.HelloService {
        public String instanceName = "默认实例名aa";

        @Override
        public void hello(String name) {
            System.out.println("HelloServiceImpl-hello"+name+",实例名为"+instanceName);
        }

        @Override
        public void bye(String name) {
            System.out.println("HelloServiceImpl-bye"+name+",实例名为"+instanceName);
        }

        public void run(){
            System.out.println("run");
        }

    }

    public static class TargetClassTest {
        public String instanceName = "默认实例名aa";

        public void hello(String name) {
            System.out.println("TargetClassTest-hello"+name+",实例名为"+instanceName);
        }

        public void bye(String name) {
            System.out.println("TargetClassTest-bye"+name+",实例名为"+instanceName);
        }

        public void run(){
            System.out.println("run");
        }

    }

    public static class TestAfterAdvice implements AfterReturningAdvice {
        @Override
        public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
            System.out.println("after ...");
        }
    }

    public static class 直接使用构造函数传入参数{

        public static void main(String[] args) {
            ProxyFactory proxyFactory = new ProxyFactory(HelloService.class, new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation invocation) throws Throwable {
                    System.out.println("指定代理方法");
                    return invocation.proceed();
                }
            });
            HelloService proxy = (HelloService)proxyFactory.getProxy();
            proxy.bye("代理");
        }
    }
}
