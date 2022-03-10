package reflect.annotation;


import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/11 下午5:56
 * @Modified By
 */
public class 反射在实际项目中的应用 {

    @Target({ElementType.TYPE,ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface CustomAnnotation {
        String value();
    }

    private static class AnnotationClass {
        @CustomAnnotation("测试方法注解")
        public void test(){
            System.out.println("执行成功。。。。");
        }

    }

    private static class ClassA {
    }

    public static List<Object> objects = new ArrayList();

    static {
        objects.add(new ClassA());
        objects.add(new AnnotationClass());
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        //从一个类的集合中获取标注了某个注解的方法，并执行。
        for (Object object : objects) {
            Method[] methods = object.getClass().getMethods();
            for (Method method : methods) {
                boolean annotationPresent = method.isAnnotationPresent(CustomAnnotation.class);
                if (annotationPresent){
                    method.invoke(object);
                    //输出： 执行成功。。。。
                }
            }
        }
    }
}
