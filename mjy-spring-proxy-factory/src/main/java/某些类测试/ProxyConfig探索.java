package 某些类测试;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description 参考 SpringAop之ProxyConfig  https://blog.csdn.net/z69183787/article/details/81257732
 * @Date: 2022/5/30 上午11:29
 * @Modified By
 */
public class ProxyConfig探索 {



    public static class exposeProxy属性探索 {
        interface HelloService {

            void hello(String name);

            void bye(String name);
        }
        public static class HelloServiceImpl implements HelloService{
            @Override
            public void hello(String name) {
                System.out.println("hello"+name);
                // 注意，这个是在调用代理方法的时候，才可以拿到代理类
                HelloService helloService = (HelloService) AopContext.currentProxy();
                helloService.bye(name);
            }

            @Override
            public void bye(String name) {
                System.out.println("bye"+name);
            }

        }
        public static class TestAfterAdvice implements AfterReturningAdvice {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("after ...");
            }
        }
        public static void main(String[] args) {
            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setInterfaces(HelloService.class);
            proxyFactory.setTarget(new HelloServiceImpl());
            proxyFactory.addAdvice(new TestAfterAdvice());
            // 将代理类加入到AopContext类的本地线程变量中。 必须在被代理类中使用
            proxyFactory.setExposeProxy(true);
            HelloService helloService = (HelloService) proxyFactory.getProxy();
            helloService.bye("scj");
            helloService.hello("scj");
        }
    }




}
