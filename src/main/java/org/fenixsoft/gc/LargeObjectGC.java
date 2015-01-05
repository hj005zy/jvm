package org.fenixsoft.gc;

/**
 * <p>
 * 大对象直接在老年代分配
 * <br />
 * <b>VM Args：-XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M
 * -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728</b>
 * </p>
 * <p>
 * 执行代码后可以看到Eden空间几乎没有被使用，而老年代的10MB空间被使用了40%，也就是4MB的allocation对象直接就分配在老年代中，
 * 这是因为PretenureSizeThreshold被设置为3MB（就是3145728，这个参数不能像-Xmx之类的参数一样直接写3MB），因此超过3MB的对象都会直接在老年代进行分配。
 * 注意PretenureSizeThreshold参数只对Serial和ParNew两款收集器有效，Parallel Scavenge收集器不认识这个参数，
 * Parallel Scavenge收集器一般并不需要设置。如果遇到必须使用此参数的场合，可以考虑ParNew加CMS的收集器组合。
 * </p>
 * @author zzm
 */
public class LargeObjectGC {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }
}
