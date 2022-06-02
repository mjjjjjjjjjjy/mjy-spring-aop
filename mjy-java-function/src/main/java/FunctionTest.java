import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/5/26 上午11:16
 * @Modified By
 */
public class FunctionTest {

    public static class 测试function基础方法 {
        //传入一个function的实现类，这个实现类可以适应lamda表达式
        public static Object apply(Object a, Function function){
            return function.apply(a);
        }

        public static void main(String[] args) {
            Object apply = apply(1, a -> (int) a + 1);
            System.out.println(apply);//打印出了2
        }
    }

    public static class 测试compose方法{
        public static void main(String[] args) {
            //相当于compose的入参作为输入，其实就是多个function的组合
            Function<Integer, Integer> f = a -> a+1;
            Function<Object, Integer> compose = f.compose(b -> (int) b + 2);
            System.out.println(compose.apply(1));//输出了4

            Function<Object, Integer> compose1 = f.compose(a -> (int) a + 1).compose(a -> (int) a + 1);
            System.out.println(compose1.apply(1));
        }


    }

    public static class 测试bigFunction基础方法 {


        public static void main(String[] args) {
            BiFunction<Integer, Integer, String> biFunction = new BiFunction<Integer, Integer, String>() {
                @Override
                public String apply(Integer o, Integer o2) {
                    return (o+o2)+"";
                }
            };
            System.out.println(biFunction.apply(1,3));
        }
    }


}
