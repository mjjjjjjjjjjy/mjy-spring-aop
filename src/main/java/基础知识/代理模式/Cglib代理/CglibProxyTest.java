package 基础知识.代理模式.Cglib代理;

import net.sf.cglib.proxy.Enhancer;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午10:36
 * @Modified By
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        CglibService cglibService = new CglibService();
        CglibServiceProxy cglibServiceProxy = new CglibServiceProxy(cglibService);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cglibService.getClass());
        enhancer.setCallback(cglibServiceProxy);
        CglibService proxyInstance = (CglibService) enhancer.create();

        proxyInstance.update();
    }
}
