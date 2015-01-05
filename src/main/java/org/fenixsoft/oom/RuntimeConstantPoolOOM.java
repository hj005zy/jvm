package org.fenixsoft.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 运行时常量池导致的内存溢出异常
 * <br />
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * </p>
 * <p>
 * String.intern()是一个Native方法，它的作用是：如果字符串常量池中已经包含一个等于此String对象的字符串，
 * 则返回代表池中这个字符串的String对象；否则，将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用。
 * 在JDK1.6及之前的版本中，由于常量池分配在永久代内，我们可以通过-XX:PermSize和-XX:MaxPermSize限制方法区大小，从而间接限制其中常量池的容量。
 * <b>而使用JDK 1.7运行这段程序就不会得到相同的结果，while循环将一直进行下去。</b>
 * </p>
 * <p>
 * 从运行结果中可以看到，运行时常量池溢出，在OutOfMemoryError后面跟随的提示信息是“PermGen space”，
 * 说明运行时常量池属于方法区（HotSpot虚拟机中的永久代）的一部分。
 * </p>
 * 
 * @author zzm
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {

        // 使用List保持着常量池引用，避免Full GC回收常量池行为       
        List<String> list = new ArrayList<String>();
        // 10MB的PermSize在integer范围内足够产生OOM了
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
