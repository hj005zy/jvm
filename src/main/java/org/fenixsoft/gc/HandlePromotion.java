package org.fenixsoft.gc;

/**
 * <p>
 * 空间分配担保
 * <br />
 * <b>VM Args：-XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M
 * -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:HandlePromotionFailure</b>
 * </p>
 * <p>
 * 请在JDK 6 Update24之前的版本中运行。
 * </p>
 * @author zzm
 */
public class HandlePromotion {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }
}
