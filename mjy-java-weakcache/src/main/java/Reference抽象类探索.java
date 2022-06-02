import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/5/26 下午12:09
 * @Modified By
 */
public class Reference抽象类探索 {

    public static void main(String[] args) throws InterruptedException {
        //强引用
        Integer a = 1;
        SoftReference<String> softReference = new SoftReference<>(new String("软引用"));
        //内存不足时会被回收
        System.out.println(softReference.get());

        WeakReference<String> weakReference = new WeakReference<>(new String("弱引用"));
        //当GC发现弱引用时会被，无论内存是否充足都会被回收
        System.out.println(weakReference.get());
        TimeUnit.SECONDS.sleep(5);

        System.gc();

        System.out.println(softReference.get());//照常返回
        System.out.println(weakReference.get());//返回null

        ArrayList<String> strings = new ArrayList<>();
        //设置vm 参数 -Xmx5m
        for (int i = 0; i < 1000; i++) {
            strings.add("ew345te353tee32435efeq4253trgfe45364yrgfer3t");
            boolean b = softReference.get() == null;
            System.out.println("获取弱引用"+softReference.get());//返回了null
            if (b){
                break;
            }
        }


    }
}
