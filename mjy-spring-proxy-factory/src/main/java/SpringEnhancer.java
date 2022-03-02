import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/3/1 下午8:04
 * @Modified By
 */
public class SpringEnhancer {

    static class Duck{
        public void run(){
            System.out.println("小鸭快跑");
        }
    }

    public static void main(String[] args) {

        MethodBeforeAdvice methodBeforeAdvice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("前置通知");
            }
        };

        Enhancer enhancer = new Enhancer();
//        enhancer.setCallbackType();
    }
}
