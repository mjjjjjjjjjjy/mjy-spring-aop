package reflect.dto;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/11 下午4:19
 * @Modified By
 */
public class Cat {

    private String catName;

    public Cat() {
    }

    public Cat(String catName) {
        this.catName = catName;
        System.out.println(catName);
    }

    public void run(){
        System.out.println("跑起来");
    }

    public void eat(String food){
        System.out.println("吃"+food);
    }

    public void print(String msg){
        System.out.println("提示："+msg);
    }
}
