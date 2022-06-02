package 代理模式.JDK动态代理;

//import sun.misc.ProxyGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/3/9 下午3:19
 * @Modified By
 */
public class ProxyGeneratorTest {

    private static interface TestInf{
        void run();
    }

    public static void main(String[] args) {
        //全限定类名
        String className = "com.sun.$Proxy";

        int accessFlags = Modifier.PUBLIC | Modifier.FINAL;
        //接口
        Class<?>[] cls = new Class<?>[]{TestInf.class};

        byte[] bytes = ProxyGenerator.generateProxyClass(className, cls, accessFlags);

        System.out.println("byteLen: " + bytes.length);
        //生成的字节码地址
        String first = System.getProperty("user.dir") + "/$Proxy.class";
        System.out.println(first);
        Path path = Paths.get(first);
        try {
            Files.createFile(path);
            OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.WRITE);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
