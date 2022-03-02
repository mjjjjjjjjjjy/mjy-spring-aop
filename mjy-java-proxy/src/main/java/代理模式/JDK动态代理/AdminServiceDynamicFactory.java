package 代理模式.JDK动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午6:13
 * @Modified By
 */
public class AdminServiceDynamicFactory {
    private Object object;
    private InvocationHandler invocationHandler;

    public AdminServiceDynamicFactory(Object object, InvocationHandler invocationHandler) {
        this.object = object;
        this.invocationHandler = invocationHandler;
    }

    public Object getProxy(){
        Object o = Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), invocationHandler);
        return o;
    }
}
