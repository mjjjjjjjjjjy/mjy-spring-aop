package 基础知识.代理模式;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午5:55
 * @Modified By
 */
public class AdminServiceImp implements AdminService{
    @Override
    public void update() {
        System.out.println("实现类 修改");
    }

    @Override
    public Object find() {
        return null;
    }

    @Override
    public Object find(String q) {
        System.out.println("实现类 查询,查询条件="+q);
        return null;
    }
}
