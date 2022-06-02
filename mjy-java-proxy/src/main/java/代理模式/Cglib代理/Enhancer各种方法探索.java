package 代理模式.Cglib代理;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/5/27 上午10:19
 * @Modified By
 */
public class Enhancer各种方法探索 {

    interface Animal {
         Object eat();
    }

    static class Duck {
        public Object find(){
            System.out.println("原方法-查找");
            return new Object();
        }
    }

    static class CglibServiceInterceptor implements MethodInterceptor {

        public Object target;

        public CglibServiceInterceptor(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String name = method.getName();
            System.out.println("CglibServiceInterceptor 执行代理方法--前 方法为"+ name);
            Object invoke = null;
            if (target != null){
                invoke = methodProxy.invoke(target, objects);
            } else {
                invoke = methodProxy.invokeSuper(o, objects);
            }
            System.out.println("CglibServiceInterceptor 执行代理方法--后 ");
            return invoke;
        }
    }

    static class CglibServiceInterceptor2 implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String name = method.getName();
            System.out.println("CglibServiceInterceptor2 执行代理方法--前 方法为"+ name);
            Object invoke = null;
            System.out.println("CglibServiceInterceptor2 执行接口的方法");

            System.out.println("CglibServiceInterceptor2 执行代理方法--后 ");
            return invoke;
        }
    }




    public static class 添加过滤器 {
        public static void main(String[] args) {
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");
            CallbackFilter filter = new CallbackFilter() {
                //返回过滤数组的下标
                @Override
                public int accept(Method method) {
                    if (method.getName().equals("eat")){
                        return 1;
                    }
                    return 0;
                }
            };

            Duck duck = new Duck();
            //验证属性
            Enhancer各种方法探索.CglibServiceInterceptor cglibServiceInterceptor = new Enhancer各种方法探索.CglibServiceInterceptor(duck);
            Enhancer各种方法探索.CglibServiceInterceptor2 cglibServiceInterceptor2 = new Enhancer各种方法探索.CglibServiceInterceptor2();

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Duck.class);
            //提供两个callbacks
            enhancer.setCallbacks(new Callback[]{cglibServiceInterceptor, cglibServiceInterceptor2});
            //添加代理方法过滤器
            enhancer.setCallbackFilter(filter);
            //添加接口
            enhancer.setInterfaces(new Class[]{Animal.class});
            //使用接口的方法
            Animal proxyInstance = (Animal) enhancer.create();
            proxyInstance.eat();
            //父类方法
            Duck d = (Duck) proxyInstance;
            d.find();
        }
    }



    public static class 传入的回调是类而不是实例 {
        public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");
            CallbackFilter filter = new CallbackFilter() {
                //返回过滤数组的下标
                @Override
                public int accept(Method method) {
                    if (method.getName().equals("eat")){
                        return 1;
                    }
                    return 0;
                }
            };

            Duck duck = new Duck();
            //验证属性
            Enhancer各种方法探索.CglibServiceInterceptor cglibServiceInterceptor = new Enhancer各种方法探索.CglibServiceInterceptor(duck);
            Enhancer各种方法探索.CglibServiceInterceptor2 cglibServiceInterceptor2 = new Enhancer各种方法探索.CglibServiceInterceptor2();

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Duck.class);
            //提供两个callbacks
            enhancer.setCallbackTypes(new Class[]{CglibServiceInterceptor.class, CglibServiceInterceptor2.class});
            //添加代理方法过滤器
            enhancer.setCallbackFilter(filter);
            //添加接口
            enhancer.setInterfaces(new Class[]{Animal.class});
            //使用接口的方法
            Class aClass = enhancer.createClass();
            Object o1 = aClass.newInstance();
            Factory factory = (Factory) o1;
            Object o = factory.newInstance(new Callback[]{cglibServiceInterceptor, cglibServiceInterceptor2});
            Animal proxyInstance = (Animal) o;
            proxyInstance.eat();
            //父类方法
            Duck d = (Duck) proxyInstance;
            d.find();
            // 从而可以知道，如果只需要生成代理类，还不需要实例，可以使用setCallbackTypes代替setCallbacks，当需要实例化的时候，才传入具体的拦截器。
            // 而且 本身GCLIB的代理类实现了Factory，可以用来生成具体的实例。
        }
    }
}
