package reflect.methods;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 上午10:59
 * @Modified By
 */
public class 获取方法 {
    public static void main(String[] args) throws NoSuchMethodException {
        Class<MethodSon> methodSonClass = MethodSon.class;
        Method[] methods = methodSonClass.getMethods();
        System.out.println("获取所有public方法：");
        for (Method method : methods) {
            extracted(method);
        }
        System.out.println("获取所有方法：");
        Method[] declaredMethods = methodSonClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            extracted(declaredMethod);
        }
    }

    private static void extracted(Method method) {
        String parameterTypes = Arrays.stream(method.getParameterTypes()).map(e -> e.getName()).collect(Collectors.joining(","));
        System.out.println("方法名："+ method.getName()+",参数："+parameterTypes);
    }


}



class MethodSon extends MethodParent implements MethodInterface {
    private String name;

    public void sonMethodPublic(String a){
    }

    private void sonMethodPrivate(String a){
    }

    @Override
    public void interfaceMethod() {
    }
}


class MethodParent {
    private String name;
    public void parentMethodPublic(String a){
    }
    private void parentMethodPrivate(String a){
    }
}

interface MethodInterface{
    void interfaceMethod();
}

