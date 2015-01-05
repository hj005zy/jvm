package org.fenixsoft.oom;

import java.lang.reflect.Field;

/**
 * <p>
 * 使用unsafe分配本机内存
 * <br />
 * <b>VM Args： -Xmx20M -XX:MaxDirectMemorySize=10M</b>
 * </p>
 * <p>
 * DirectMemory容量可通过-XX:MaxDirectMemorySize指定，如果不指定，则默认与Java堆最大值（-Xmx指定）一样，
 * 代码越过了DirectByteBuffer类，直接通过反射获取Unsafe实例进行内存分配
 * （Unsafe类的getUnsafe()方法限制了只有引导类加载器才会返回实例，也就是设计者希望只有rt.jar中的类才能使用Unsafe的功能）。
 * 因为，虽然使用DirectByteBuffer分配内存也会抛出内存溢出异常，但它抛出异常时并没有真正向操作系统申请分配内存，
 * 而是通过计算得知内存无法分配，于是手动抛出异常，真正申请分配内存的方法是unsafe.allocateMemory()。
 * </p>
 * <p>
 * 由DirectMemory导致的内存溢出，一个明显的特征是在Heap Dump文件中不会看见明显的异常，
 * 如果读者发现OOM之后Dump文件很小，而程序中又直接或间接使用了NIO，那就可以考虑检查一下是不是这方面的原因。
 * </p>
 * 
 * @author zzm
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("restriction")
    public static void main(String[] args) throws Exception {

        Field unsafeField = sun.misc.Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
