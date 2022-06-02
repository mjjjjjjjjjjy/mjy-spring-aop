package 代理模式.Cglib代理;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午10:36
 * @Modified By
 */
public class CglibProxyTest {

     static class CglibService {
         int a = 0;

         public void setA(int a) {
             this.a = a;
         }

         public void update(){
            System.out.println("原方法-更新 a="+a);
        }

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
            System.out.println("执行代理方法--前 方法为"+method.getName());
//            Object invoke = method.invoke(target, objects);
            //invoke用于传入被代理类对象
            Object invoke = methodProxy.invoke(target, objects);

            //也可以传入代理的对象，但是必须使用invokeSuper，这样就会直接去调用父类方法，不会引起循环引用
//            Object invoke = methodProxy.invokeSuper(o, objects);

            System.out.println("执行代理方法--后 ");
            return invoke;
        }
    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");
        CglibService cglibService = new CglibService();
        //验证属性
        cglibService.setA(1);
        CglibServiceInterceptor cglibServiceInterceptor = new CglibServiceInterceptor(cglibService);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cglibService.getClass());
        enhancer.setCallback(cglibServiceInterceptor);
        CglibService proxyInstance = (CglibService) enhancer.create();
        proxyInstance.update();
        proxyInstance.find();
    }

}
