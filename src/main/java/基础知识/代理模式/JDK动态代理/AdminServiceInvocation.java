package 基础知识.代理模式.JDK动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午6:07
 * @Modified By
 */
public class AdminServiceInvocation implements InvocationHandler {

    private Object object;

    public AdminServiceInvocation(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理类，准备执行代理类。。。");
        Object invoke = method.invoke(object, args);
        System.out.println("代理类，执行完毕。。。");
        return invoke;
    }
}
