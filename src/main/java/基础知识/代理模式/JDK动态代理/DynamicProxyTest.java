package 基础知识.代理模式.JDK动态代理;

import 基础知识.代理模式.AdminService;
import 基础知识.代理模式.AdminServiceImp;
import 基础知识.代理模式.静态代理.AdminServiceProxy;

import java.lang.reflect.Proxy;

/**
 * @Author: Mo Jianyue
 * @Description 为解决静态代理对象必须实现接口的所有方法的问题，Java给出了动态代理，动态代理具有如下特点：
 * @Date: 2022/2/7 下午6:16
 * @Modified By
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        //设置环境变量，保存文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        AdminServiceImp adminServiceImp = new AdminServiceImp();
        //代理对象不需要实现接口
        //但是需要引入一个InvocationHandler实现类
        AdminServiceInvocation adminServiceInvocation = new AdminServiceInvocation(adminServiceImp);
        AdminServiceDynamicProxy adminServiceDynamicProxy = new AdminServiceDynamicProxy(adminServiceImp, adminServiceInvocation);
        AdminService proxy = (AdminService) adminServiceDynamicProxy.getProxy();
        proxy.find();

        //或者不需要AdminServiceDynamicProxy
        AdminService o = (AdminService) Proxy.newProxyInstance(adminServiceImp.getClass().getClassLoader(), adminServiceImp.getClass().getInterfaces(), adminServiceInvocation);

        o.find("333");


    }
}
