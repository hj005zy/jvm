package org.fenixsoft.gc;

/**
 * <p>
 * 对象优先在Eden分配
 * <br />
 * <b>VM Args：-XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8</b>
 * </p>
 * <p>
 * 代码中尝试分配3个2MB大小和1个4MB大小的对象，在运行时通过-Xms20M、-Xmx20M、-Xmn10M这3个参数限制了Java堆大小为20MB，
 * 不可扩展，其中10MB分配给新生代，剩下的10MB分配给老年代。-XX:SurvivorRatio=8决定了新生代中Eden区与一个Survivor区的空间比例是8:1，
 * 从输出的结果也可以清晰地看到“eden space 8192K、from space 1024K、to space 1024K”的信息，
 * 新生代总可用空间为9216KB（Eden区+1个Survivor区的总容量）。
 * </p>
 * <p>
 * 代码中分配allocation4对象的语句时会发生一次Minor GC，这次GC的结果是新生代6651KB变为148KB，
 * 而总内存占用量则几乎没有减少（因为allocation1、allocation2、allocation3三个对象都是存活的，虚拟机几乎没有找到可回收的对象）。
 * 这次GC发生的原因是给allocation4分配内存的时候，发现Eden已经被占用了6MB，剩余空间已不足以分配allocation4所需的4MB内存，因此发生Minor GC。
 * GC期间虚拟机又发现已有的3个2MB大小的对象全部无法放入Survivor空间（Survivor空间只有1MB大小），所以只好通过分配担保机制提前转移到老年代去。
 * </p>
 * <p>
 * 这次GC结束后，4MB的allocation4对象顺利分配在Eden中，因此程序执行完的结果是Eden占用4MB（被allocation4占用），
 * Survivor空闲，老年代被占用6MB（被allocation1、allo-cation2、allocation3占用）。通过GC日志可以证实这一点。
 * </p>
 * @author zzm
 */
public class MinorGC {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; // 出现一次Minor GC
    }
}
