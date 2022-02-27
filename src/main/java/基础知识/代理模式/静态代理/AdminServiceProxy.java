package 基础知识.代理模式.静态代理;

import 基础知识.代理模式.AdminService;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/7 下午5:56
 * @Modified By
 */
public class AdminServiceProxy implements AdminService {

    private AdminService adminService;

    public AdminServiceProxy(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void update() {
        System.out.println("代理类 更新前操作");
        adminService.update();
    }

    @Override
    public Object find() {
        System.out.println("代理类 查询前操作");
        adminService.find();
        return null;
    }

    @Override
    public Object find(String q) {
        System.out.println("代理类 查询前操作");
        adminService.find(q);
        return null;
    }
}
