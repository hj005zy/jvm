package org.fenixsoft.gc;

/**
 * <p>
 * 动态对象年龄判定
 * <br />
 * <b>VM Args：-XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M
 * -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
 * -XX:+PrintTenuringDistribution</b>
 * </p>
 * <p>
 * 运行结果中Survivor的空间占用仍然为0%，而老年代比预期增加了6%，也就是说，allocation1、allocation2对象都直接进入了老年代，
 * 而没有等到15岁的临界年龄。因为这两个对象加起来已经到达了512KB，并且它们是同年的，满足同年对象达到Survivor空间的一半规则。
 * 我们只要注释掉其中一个对象new操作，就会发现另外一个就不会晋升到老年代中去了。
 * </p>
 * @author zzm
 */
public class TenuringGC2 {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];

        // allocation1+allocation2大于survivor空间一半
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }
}
