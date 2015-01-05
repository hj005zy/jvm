package org.fenixsoft.gc;

/**
 * <p>
 * 长期存活的对象进入老年代
 * <br />
 * <b>VM Args：-XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M
 * -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1(15)
 * -XX:+PrintTenuringDistribution</b>
 * </p>
 * <p>
 * 分别以-XX:MaxTenuringThreshold=1和-XX:MaxTenuringThreshold=15两种设置来执行代码，代码中的allocation1对象需要256KB内存，
 * Survivor空间可以容纳。当MaxTenuringThreshold=1时，allocation1对象在第二次GC发生时进入老年代，
 * 新生代已使用的内存GC后非常干净地变成0KB。而MaxTenuringThreshold=15时，第二次GC发生后，
 * allocation1对象则还留在新生代Survivor空间，这时新生代仍然有404KB被占用。
 * </p>
 * @author zzm
 */
public class TenuringGC {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4]; // 什么时候进入老年代取决于XX:MaxTenuringThreshold设置  
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }
}
