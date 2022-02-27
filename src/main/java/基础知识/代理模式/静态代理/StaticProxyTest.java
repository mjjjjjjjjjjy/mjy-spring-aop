package 基础知识.代理模式.静态代理;

import 基础知识.代理模式.AdminServiceImp;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午5:58
 * @Modified By
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        AdminServiceImp adminServiceImp = new AdminServiceImp();
        AdminServiceProxy adminServiceProxy = new AdminServiceProxy(adminServiceImp);
        adminServiceProxy.find();
        adminServiceProxy.update();

    }
}
