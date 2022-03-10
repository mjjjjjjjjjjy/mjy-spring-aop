import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/3/2 下午6:46
 * @Modified By
 */
public class ConfigurationApplication {
    static class ClassA {
        int a = 0;

        public ClassA(int a) {
            this.a = a;
        }
        public int getA() {
            return a;
        }
    }

    @Configuration
    static class Configutation{
        @Bean
        public ClassA classA(){
            System.out.println("配置classA到Spring");
            return new ClassA(1);
        }
    }

    public static void main(String[] args) {
        //打印代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");
        System.out.println("开始。。。");
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Configutation.class);
        ClassA bean = annotationConfigApplicationContext.getBean(ClassA.class);
        System.out.println("ClassA 的属性a="+bean.getA());
        //获取 Configutation 对象
        Configutation configutationBean = annotationConfigApplicationContext.getBean(Configutation.class);
        System.out.println("Configutation对象的Class名称："+configutationBean.getClass().getName());
        ClassA classA = configutationBean.classA();
        System.out.println("结束....");
    }
}
