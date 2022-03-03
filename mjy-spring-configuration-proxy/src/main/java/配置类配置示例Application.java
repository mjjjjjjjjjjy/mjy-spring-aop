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
public class 配置类配置示例Application {

    /**
     * 主要研究，为什么在@configuration类下，嵌套调用会生效
     */
    static class ClassA {
        int a = 0;

        public ClassA(int a) {
            this.a = a;
        }
        public int getA() {
            return a;
        }
    }

    static class ClassB {
    }

    @Configuration
    static class Configutation{
        @Bean
        public ClassA classA(){
            System.out.println("配置classA到Spring");
            return new ClassA(1);
        }

        @Bean
        public ClassB classB(){
            System.out.println("配置ClassB到Spring");
            ClassA a = classA();
            System.out.println("A的值"+a.getA());
            return new ClassB();
        }
    }

    public static void main(String[] args) {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Configutation.class);
        Map<String, Configutation> beansOfType = annotationConfigApplicationContext.getBeansOfType(Configutation.class);
        for (Map.Entry<String, Configutation> entry : beansOfType.entrySet()) {
            System.out.println(entry.getValue().getClass().getName());
        }
    }
}
