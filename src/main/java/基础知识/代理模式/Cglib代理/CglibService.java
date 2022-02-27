package 基础知识.代理模式.Cglib代理;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午10:25
 * @Modified By
 */
public class CglibService {
    public void update(){
        System.out.println("原方法-更新");
    }

    public Object find(){
        System.out.println("原方法-查找");
        return new Object();
    }
}
