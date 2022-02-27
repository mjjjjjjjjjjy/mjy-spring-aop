package 基础知识.代理模式.Cglib代理;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午10:26
 * @Modified By
 */
public class CglibServiceProxy implements MethodInterceptor {

    public Object target;

    public CglibServiceProxy(Object target) {
        this.target = target;
    }

//    public Object getProxyInstance(){
//
//    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行代理方法--前 方法为"+method.getName());
        Object invoke = method.invoke(target, objects);
        System.out.println("执行代理方法--后 ");
        return invoke;
    }
}
