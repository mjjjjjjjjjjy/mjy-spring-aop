import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Mo Jianyue
 * @Description
 * @Date: 2022/5/26 下午2:17
 * @Modified By
 */
public class ReferenceQueue队列 {
    /**
     * 当gc（垃圾回收线程）准备回收一个对象时，如果发现它还仅有软引用(或弱引用，或虚引用)指向它，就会在回收该对象之前，把这个软引用（或弱引用，或虚引用）加入到与之关联的引用队列（ReferenceQueue）中。如果一个软引用（或弱引用，或虚引用）对象本身在引用队列中，就说明该引用对象所指向的对象被回收了。
     *
     * 当软引用（或弱引用，或虚引用）对象所指向的对象被回收了，那么这个引用对象本身就没有价值了，如果程序中存在大量的这类对象（注意，我们创建的软引用、弱引用、虚引用对象本身是个强引用，不会自动被gc回收），就会浪费内存。因此我们这就可以手动回收位于引用队列中的引用对象本身。
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //保护方法，不是pulic的方法，在这里不能添加元素，只能被reference类使用
        ReferenceQueue referenceQueue = new ReferenceQueue();

        WeakReference<String> weakReference = new WeakReference<>(new String("弱引用1"), referenceQueue);
        WeakReference<String> weakReference2 = new WeakReference<>(new String("弱引用2"), referenceQueue);
        String s = weakReference.get();
        TimeUnit.SECONDS.sleep(2);
        System.gc();
        System.out.println(weakReference.get());//没有被回收
        System.out.println(weakReference2.get());//已经被回收

        System.out.println(weakReference.isEnqueued()); // false 被强引用了，没有入队列
        System.out.println(weakReference2.isEnqueued());//true

        s = null;

        System.gc();
        System.out.println(weakReference.isEnqueued()); //false 已经被回收了
        int a = 0;
        while (true){
            Reference poll = referenceQueue.poll();
            if (poll != null){
                a++;
            } else {
                break;
            }
        }
        System.out.println("队列元素数量" + a); // a=2 两个都被回收了





    }
}
