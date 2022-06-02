package 某些类测试;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/5/30 下午3:47
 * @Modified By
 */
public class proxyTargetClass属性探索 {

    public static class proxyTargetClass在同时提供了接口和类的时候的表现 {
        interface HelloService {

            void hello(String name);

            void bye(String name);
        }
        public static class HelloServiceImpl implements HelloService{
            @Override
            public void hello(String name) {
                System.out.println("HelloServiceImpl-hello"+name);
            }

            @Override
            public void bye(String name) {
                System.out.println("HelloServiceImpl-bye"+name);
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
        public static void main(String[] args) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setInterfaces(HelloService.class);
            proxyFactory.setTarget(new HelloServiceImpl());
            proxyFactory.addAdvice(new TestAfterAdvice());
            //
            proxyFactory.setProxyTargetClass(false);
            HelloService helloService = (HelloService) proxyFactory.getProxy();
            helloService.hello("  jseca ");
            // 必须要proxyTargetClass设置为true,否则是不会继承HelloServiceImpl的。
            HelloServiceImpl imp = (HelloServiceImpl) proxyFactory.getProxy();
            imp.run();
        }
        /**
         * 当设置为true时，使用了cblig代理，代理类的继承关系为：
         * public class ProxyConfig探索$proxyTargetClass属性探索$HelloServiceImpl$$EnhancerBySpringCGLIB$$c44d04c7 extends HelloServiceImpl implements HelloService, SpringProxy, Advised, Factory {}
         *
         * 不设置时（默认false） 选择了JDK代理 只实现了接口
         * final class $Proxy0 extends Proxy implements HelloService, SpringProxy, Advised, DecoratingProxy {}
         */
    }


    public static class proxyTargetClass属性探索2只有接口没有类但是设置了true {
        interface HelloService {

            void hello(String name);

            void bye(String name);
        }

        public static class TestAfterAdvice implements AfterReturningAdvice {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("after ...");
            }
        }
        public static void main(String[] args) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setInterfaces(HelloService.class);
            proxyFactory.addAdvice(new TestAfterAdvice());
            //
            proxyFactory.setProxyTargetClass(true);
            // 当proxyTargetClass设置为true,只传入接口setInterfaces，会报错，提示找不到指定的类。这个很正常
        }

    }


    public static class proxyTargetClass属性探索当只有类没有接口但是设置了false {

        public static class HelloServiceImpl {

            public void run(){
                System.out.println("HelloServiceImpl = run");
            }


        }
        public static class TestAfterAdvice implements AfterReturningAdvice {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("after ...");
            }
        }
        public static void main(String[] args) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setTarget(new HelloServiceImpl());
            proxyFactory.addAdvice(new TestAfterAdvice());
            //
            proxyFactory.setProxyTargetClass(false);
            // 必须要proxyTargetClass设置为true,否则是不会继承HelloServiceImpl的。
            HelloServiceImpl imp = (HelloServiceImpl) proxyFactory.getProxy();
            imp.run();
        }
        /**
         * proxyTargetClass属性探索当只有类没有接口但是设置了false,自动使用了CGLIB来代理，代理继承关系为
         * public class ProxyConfig探索$proxyTargetClass属性探索当只有类没有接口但是设置了false$HelloServiceImpl$$EnhancerBySpringCGLIB$$903ba198 extends HelloServiceImpl implements SpringProxy, Advised, Factory {}
         * 说明此时proxyTargetClass = false是失效的。
         */

    }

    public static class proxyTargetClass在同时提供了接口和类的但是设置为false的时候 {
        interface HelloService {

            void hello(String name);

            void bye(String name);
        }
        public static class HelloServiceImpl implements HelloService{
            @Override
            public void hello(String name) {
                System.out.println("HelloServiceImpl-hello"+name);
            }

            @Override
            public void bye(String name) {
                System.out.println("HelloServiceImpl-bye"+name);
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
        public static void main(String[] args) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setProxyTargetClass(false);

            proxyFactory.setInterfaces(HelloService.class);
            proxyFactory.setTarget(new HelloServiceImpl());
            proxyFactory.addAdvice(new TestAfterAdvice());
            //
            HelloService helloService = (HelloService) proxyFactory.getProxy();
            // 必须要proxyTargetClass设置为true,否则是不会继承HelloServiceImpl的。
            HelloServiceImpl imp = (HelloServiceImpl) proxyFactory.getProxy();
            imp.run();
        }
        /**
         * 当设置为true时，使用了JDK代理，代理类的继承关系为：
         * public class ProxyConfig探索$proxyTargetClass属性探索$HelloServiceImpl$$EnhancerBySpringCGLIB$$c44d04c7 extends HelloServiceImpl implements HelloService, SpringProxy, Advised, Factory {}
         *
         * 不设置时（默认false） 选择了JDK代理 只实现了接口
         * final class $Proxy0 extends Proxy implements HelloService, SpringProxy, Advised, DecoratingProxy {}
         */
    }




    public static class proxyTargetClass设置了其他实现类 {
        interface HelloService {

            void hello(String name);

            void bye(String name);
        }
        public static class HelloServiceImpl{
            public void hello(String name) {
                System.out.println("HelloServiceImpl-hello"+name);
            }

            public void bye(String name) {
                System.out.println("HelloServiceImpl-bye"+name);
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
        public static void main(String[] args) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");

            ProxyFactory proxyFactory =new ProxyFactory();
            proxyFactory.setInterfaces(HelloService.class);
            proxyFactory.setTarget(new HelloServiceImpl());
            proxyFactory.addAdvice(new TestAfterAdvice());
            //
            proxyFactory.setProxyTargetClass(false);
            HelloService helloService = (HelloService) proxyFactory.getProxy();
            helloService.hello("  jseca ");
            // 必须要proxyTargetClass设置为true,否则是不会继承HelloServiceImpl的。
            HelloServiceImpl imp = (HelloServiceImpl) proxyFactory.getProxy();
            imp.run();
        }

    }
}
