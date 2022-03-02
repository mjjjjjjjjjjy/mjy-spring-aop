package 代理模式.JDK动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 下午3:53
 * @Modified By
 */
public class JDK动态代理最简单示例 {
    //定义一个接口
    private interface Printer{
        void printMsg(String msg);
    }
    //定义实现类
    private static class ColorPrinter implements  Printer{
        @Override
        public void printMsg(String msg) {
            System.out.println("彩色打印："+msg);
        }
    }
    //实现InvocationHandler
    private static class PrinterInvocation implements InvocationHandler {

        private Object target;

        public PrinterInvocation(Object target) {
            this.target = target;
        }

        //实现增强的地方
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("代理类，准备执行代理类。。。");
            Object invoke = method.invoke(target, args);
            System.out.println("代理类，执行完毕。。。");
            return invoke;
        }
    }

    public static void main(String[] args) {
        //设置环境变量，将生成的代理类保存成文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //1、先生成一个被代理的实例
        ColorPrinter colorPrinter = new ColorPrinter();
        //2、实现 InvocationHandler
        InvocationHandler printerInvocation = new PrinterInvocation(colorPrinter);
        //3、生成代理类
        Printer printer =(Printer) Proxy.newProxyInstance(colorPrinter.getClass().getClassLoader(), colorPrinter.getClass().getInterfaces(), printerInvocation);
        printer.printMsg("消息");
    }
}
