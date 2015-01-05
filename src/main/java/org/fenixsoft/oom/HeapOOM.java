package org.fenixsoft.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java堆内存溢出异常测试
 * <br />
 * <b>VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError</b>
 * </p>
 * <p>
 * 要解决这个区域的异常，一般的手段是先通过内存映像分析工具（如Eclipse Memory Analyzer）
 * 对Dump出来的堆转储快照进行分析，重点是确认内存中的对象是否是必要的，
 * 也就是要先分清楚到底是出现了内存泄漏（Memory Leak）还是内存溢出（Memory Overflow）。
 * </p>
 * <p>
 * 如果是内存泄露，可进一步通过工具查看泄露对象到GCRoots的引用链。
 * 于是就能找到泄露对象是通过怎样的路径与GC Roots相关联并导致垃圾收集器无法自动回收它们的。
 * 掌握了泄露对象的类型信息及GC Roots引用链的信息，就可以比较准确地定位出泄露代码的位置。
 * </p>
 * <p>
 * 如果不存在泄露，换句话说，就是内存中的对象确实都还必须存活着，那就应当检查虚拟机的堆参数（-Xmx与-Xms），
 * 与机器物理内存对比看是否还可以调大，从代码上检查是否存在某些对象生命周期过长、持有状态时间过长的情况，尝试减少程序运行期的内存消耗。
 * </p>
 * @author zzm
 */
public class HeapOOM {

    static class OOMObject {
    }

    public static void main(String[] args) {

        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
