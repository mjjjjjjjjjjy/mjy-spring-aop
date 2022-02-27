package 基础知识.代理模式;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午5:54
 * @Modified By
 */
public interface AdminService {
    void update();
    Object find();
    Object find(String q);
}
