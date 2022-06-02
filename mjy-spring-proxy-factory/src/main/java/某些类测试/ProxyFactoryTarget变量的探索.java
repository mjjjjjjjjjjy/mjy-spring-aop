package 某些类测试;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/5/30 下午4:16
 * @Modified By
 */
public class ProxyFactoryTarget变量的探索 {

    interface HelloService {

        void hello(String name);

        void bye(String name);
    }
    public static class HelloServiceImpl implements HelloService {
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

    public static class 传入target对象时的探索{

        public static void main(String[] args) {

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setInterfaces(HelloService.class);
            //传入了被代理对象的实例。在调用代理类的拦截器方法的时候，会换成传入的这个实例。
            HelloServiceImpl target = new HelloServiceImpl();
            target.instanceName = "目标类1";
            proxyFactory.setTarget(target);
            proxyFactory.addAdvice(new TestAfterAdvice());
            //
            proxyFactory.setProxyTargetClass(false);
            HelloService helloService = (HelloService) proxyFactory.getProxy();
            helloService.hello("  jseca ");
        }
    }

    public static class 传入target类而不是对象的探索{

        public static void main(String[] args) {

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setInterfaces(HelloService.class);
            proxyFactory.setTargetClass(HelloServiceImpl.class);
            proxyFactory.addAdvice(new TestAfterAdvice());
            proxyFactory.setProxyTargetClass(false);
            HelloService helloService = (HelloService) proxyFactory.getProxy();
            helloService.hello("  jseca ");
            //会报错，空指针异常，就是没有传入对象
        }
    }

    public static class 传入一个完全没有实现接口的被代理对象这个时候必须要指明targetClass用于强转类型{

        public static void main(String[] args) {

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setInterfaces(HelloService.class);
            //如果传了setTarget,在setTargetClass，target会被覆盖，所以这个值有什么用呢？
            proxyFactory.setTargetClass(TargetClassTest.class);
            proxyFactory.addAdvice(new TestAfterAdvice());
            proxyFactory.setProxyTargetClass(true);
            HelloService helloService = (HelloService) proxyFactory.getProxy();
            helloService.hello("  jseca ");
            //会报错，空指针异常，就是没有传入对象
        }
    }




}
