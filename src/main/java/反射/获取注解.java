package 反射;

import 反射.dto.AnnotationClass;

import java.lang.annotation.Annotation;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/11 下午5:56
 * @Modified By
 */
public class 获取注解 {

    public static void main(String[] args) {
        Class<AnnotationClass> annotationClassClass = AnnotationClass.class;
        Annotation[] annotations = annotationClassClass.getAnnotations();


    }
}
