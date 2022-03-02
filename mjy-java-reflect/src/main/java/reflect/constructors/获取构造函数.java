package reflect.constructors;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 上午10:59
 * @Modified By
 */
public class 获取构造函数 {
    public static void main(String[] args) throws NoSuchMethodException {
        Class<Son> sonClass = Son.class;
        //public修饰符的构造函数
        Constructor<?>[] constructors = sonClass.getConstructors();
        System.out.println("public修饰符的构造函数：");
        for (Constructor<?> constructor : constructors) {
            extracted(constructor);
        }
        //输出
        //构造函数名：reflect.constructors.Son,参数：java.lang.String,java.lang.String
        //构造函数名：reflect.constructors.Son,参数：java.lang.String

        //所有构造函数
        Constructor<?>[] declaredConstructors = sonClass.getDeclaredConstructors();

        System.out.println("public修饰符的构造函数：");
        for (Constructor<?> constructor : declaredConstructors) {
            extracted(constructor);
        }
        //输出
        //构造函数名：reflect.constructors.Son,参数：java.lang.String,java.lang.String
        //构造函数名：reflect.constructors.Son,参数：java.lang.String
        //构造函数名：reflect.constructors.Son,参数：
    }

    private static void extracted(Constructor<?> constructor) {
        String parameterTypes = Arrays.stream(constructor.getParameterTypes()).map(e -> e.getName()).collect(Collectors.joining(","));
        System.out.println("构造函数名："+ constructor.getName()+",参数："+parameterTypes);
    }
}



class Son {
    private String SonName;

    public Son(String name, String sonName) {
        SonName = sonName;
    }

    public Son(String name) {
        SonName = name;
    }

    private Son(){
    }
}