package reflect.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 上午10:59
 * @Modified By
 */
public class 使用Constractor对象创建实例 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<ClassA> classAClass = ClassA.class;
        Constructor<ClassA> constructor = classAClass.getConstructor(String.class);
        ClassA classA = constructor.newInstance("使用Constructor获取实例");
        classA.printName();
        //输出： 名称为：使用Constructor获取实例
    }
}



class ClassA {
    private String msg;

    public ClassA(String msg) {
        this.msg = msg;
    }

    public void printName(){
        System.out.println("消息："+msg);
    }

}