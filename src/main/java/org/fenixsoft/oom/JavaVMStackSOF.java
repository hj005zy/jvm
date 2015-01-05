package org.fenixsoft.oom;

/**
 * <p>
 * 虚拟机栈和本地方法栈OOM测试（仅作为第1点测试程序）
 * <br />
 * <b>VM Args：-Xss128k</b>
 * </p>
 * <p>
 * 由于在HotSpot虚拟机中并不区分虚拟机栈和本地方法栈，因此，对于HotSpot来说，
 * 虽然-Xoss参数（设置本地方法栈大小）存在，但实际上是无效的，栈容量只由-Xss参数设定。
 * 关于虚拟机栈和本地方法栈，在Java虚拟机规范中描述了两种异常：
 * <ul>
 * <li>如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError异常。</li>
 * <li>如果虚拟机在扩展栈时无法申请到足够的内存空间，则抛出OutOfMemoryError异常。</li>
 * </ul>
 * </p>
 * <p>
 * 将实验范围限制于单线程中的操作，尝试了下面两种方法均无法让虚拟机产生OutOfMemoryError异常，
 * 尝试的结果都是获得StackOverflowError异常。
 * <ul>
 * <li>使用-Xss参数减少栈内存容量。结果：抛出StackOverflowError异常，异常出现时输出的堆栈深度相应缩小。</li>
 * <li>定义了大量的本地变量，增大此方法帧中本地变量表的长度。结果：抛出StackOverflowError异常时输出的堆栈深度相应缩小。</li>
 * </ul>
 * </p>
 * <p>
 * 实验结果表明：在单个线程下，无论是由于栈帧太大还是虚拟机栈容量太小，
 * 当内存无法分配的时候，虚拟机抛出的都是StackOverflowError异常。
 * </p>
 * @author zzm
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    private void stackLeak() {

        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {

        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
