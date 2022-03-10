package reflect.annotation;


import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/11 下午5:56
 * @Modified By
 */
public class 获取注解 {

    @Target({ElementType.TYPE,ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface CustomAnnotation {
        String value();
    }

    @CustomAnnotation("测试类注解")
    private static class AnnotationClass {

        @CustomAnnotation("测试方法注解")
        public void test(){
        }
    }

    public static void main(String[] args) {
        Class<AnnotationClass> annotationClassClass = AnnotationClass.class;
        Annotation[] annotations = annotationClassClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CustomAnnotation){
                System.out.println(((CustomAnnotation)annotation).value());
                //输出：  测试类注解
            }
        }

        Method[] methods = annotationClassClass.getMethods();
        for (Method method : methods) {
            Annotation[] methodAnnotations = method.getAnnotations();
            for (Annotation methodAnnotation : methodAnnotations) {
                if (methodAnnotation instanceof CustomAnnotation){
                    System.out.println(((CustomAnnotation)methodAnnotation).value());
                    //输出：  测试方法注解
                }
            }

        }
    }
}
