package 代理模式.静态代理;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/2/28 下午2:58
 * @Modified By
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        Printer proxy = new Proxy(new ColorPrinter());
        proxy.printMsg("消息");
        //结果：
        //打印前增强
        //彩色打印：消息

        Printer proxy2 = new Proxy(new BlackPrinter());
        proxy2.printMsg("消息");
        //结果：
        //打印前增强
        //黑白打印：消息
    }
}

interface Printer{
    void printMsg(String msg);
}

class ColorPrinter implements  Printer{
    @Override
    public void printMsg(String msg) {
        System.out.println("彩色打印："+msg);
    }
}

class BlackPrinter implements  Printer{
    @Override
    public void printMsg(String msg) {
        System.out.println("黑白打印："+msg);
    }
}

class Proxy implements Printer{

    private Printer printer;

    public Proxy(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void printMsg(String msg) {
        System.out.println("打印前增强");
        printer.printMsg(msg);
    }
}

