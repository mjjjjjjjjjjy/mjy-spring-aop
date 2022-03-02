package reflect.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 下午2:10
 * @Modified By
 */
public class 使用Method对象 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getMethod = MethodClass.class.getMethod("get", String.class);
        MethodClass methodClass = new MethodClass();

        InvokeClass invokeClass = new InvokeClass(methodClass, getMethod);
        Object result = invokeClass.invoke("调用");

        System.out.println(result);
    }

}

//一个包装类
class InvokeClass{
    private Object object;
    private Method method;

    public InvokeClass(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Object invoke(Object ...parms) throws InvocationTargetException, IllegalAccessException {
        Object invoke = method.invoke(object, parms);
        System.out.println("增强。。。。");
        return invoke;
    }


}


class MethodClass{

    public String get(String msg){
        return msg;
    }
}
