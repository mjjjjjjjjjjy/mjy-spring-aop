import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 下午5:16
 * @Modified By
 */
public class Aspect配置的代理Application {

    @Component
    static class Duck{
        public void run(){
            System.out.println("小鸭快跑");
        }
    }

    @Aspect
    @Component
    @EnableAspectJAutoProxy
    static class Intercepter{

        @Before("execution(* Aspect配置的代理Application..*.*(..))")
        public void before(){
            System.out.println("Aspect: 预备。。。");
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Duck.class, Intercepter.class);
        Duck bean = annotationConfigApplicationContext.getBean(Duck.class);
        bean.run();

        Map<String, Duck> beansOfType = annotationConfigApplicationContext.getBeansOfType(Duck.class);
        for (Map.Entry<String, Duck> entry : beansOfType.entrySet()) {
            System.out.println(entry.getValue().getClass().getName());
        }
    }
}
