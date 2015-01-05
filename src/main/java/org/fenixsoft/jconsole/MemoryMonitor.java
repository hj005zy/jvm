package org.fenixsoft.jconsole;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 内存监控
 * <br />
 * <b>VM Args：-XX:+UseSerialGC -Xms100M -Xmx100M</b>
 * </p>
 * <p>
 * JConsole的“内存”页签相当于可视化的jstat命令，用于监视受收集器管理的虚拟机内存（Java堆和永久代）的变化趋势。
 * 本代码的作用是以64KB/50毫秒的速度往Java堆中填充数据，一共填充1000次，使用JConsole的“内存”页签进行监视，观察曲线和柱状指示图的变化。
 * </p>
 * @author zzm
 */
public class MemoryMonitor {

    /*** 内存占位符对象，一个OOMObject大约占64KB*/
    static class OOMObject {

        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {

        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) { // 稍作延时，令监视曲线的变化更加明显      
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception {

        fillHeap(1000);
    }
}
