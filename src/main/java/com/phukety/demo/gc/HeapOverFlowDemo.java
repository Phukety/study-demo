package com.phukety.demo.gc;

/**
 * 堆内存溢出demo
 * 设置虚拟机堆最大内存30M: -Xms30m -Xmx30m
 * -XX: +HeapDumpOnOutOfMemoryError -- 当程序发生堆内存溢出时，生成dump文件，便于日后排错
 */
public class HeapOverFlowDemo {
    public static void main(String[] args) {
        // 35M的数组
        String[] array = new String[35 * 1000 * 1000];
    }
}
