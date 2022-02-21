package com.phukety.demo.concurrent.atomicinteger;

import java.util.concurrent.atomic.LongAdder;

public class LongAddrRefactorDemo implements Runnable {
    public static final LongAddrRefactorDemo autoIncrease = new LongAddrRefactorDemo();
    private static final LongAdder k = new LongAdder();

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            k.increment();
        }
    }

    public long start(int threadCount) throws InterruptedException {
        k.reset();
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(autoIncrease);
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        long end = System.currentTimeMillis();

        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {
        int times = 100;
        long sumTime = 0;
        for (int i = 0; i < times; i++) {
            sumTime += autoIncrease.start(50);
        }
        System.out.println("平均耗时：" + sumTime / times);
    }
}
