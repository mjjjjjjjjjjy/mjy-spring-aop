package 代理模式.JDK动态代理;


import 代理模式.AdminService;
import 代理模式.AdminServiceImp;

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
        //需要引入一个InvocationHandler实现类
        AdminServiceInvocation adminServiceInvocation = new AdminServiceInvocation(adminServiceImp);

        AdminService o = (AdminService) Proxy.newProxyInstance(adminServiceImp.getClass().getClassLoader(), adminServiceImp.getClass().getInterfaces(), adminServiceInvocation);
        o.find("333");

        //或者可以简单封装一下
        AdminServiceDynamicFactory adminServiceDynamicFactory = new AdminServiceDynamicFactory(adminServiceImp, adminServiceInvocation);
        AdminService proxy = (AdminService) adminServiceDynamicFactory.getProxy();
        proxy.find();

    }
}
