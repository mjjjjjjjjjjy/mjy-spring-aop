package 反射;

import 反射.dto.Cat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/11 下午4:37
 * @Modified By
 */
public class ReflectTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        //
        Class<Cat> catClass = Cat.class;
        Cat cat = catClass.newInstance();
        cat.run();

        Class<?> aClass = Class.forName("反射.dto.Cat");
        Cat catForName = (Cat) aClass.newInstance();
        catForName.print("通过forName获取");

        Cat catForReflect = new Cat();
        Class<? extends Cat> catForReflectClass = catForReflect.getClass();
        Method print = catForReflectClass.getMethod("print", String.class);
        print.invoke(catForReflect,"通过Method调用实例方法");

        Constructor<Cat> constructor = catClass.getConstructor();
        Cat cat1 = constructor.newInstance();
        cat1.run();

        Constructor<Cat> constructor1 = catClass.getConstructor(String.class);
        Cat cat2 = constructor1.newInstance("通过Constructor，指定构造函数参数获取对象");
        cat2.run();

        Cat cat3 = new Cat();
        Method[] methods = catClass.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName()+" 参数类型："+ Arrays.stream(method.getParameterTypes()).map(e->e.getName()).collect(Collectors.joining(",")));
        }

    }
}
