package com.phukety.demo.concurrent.atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerRefactorDemo implements Runnable {
    public static final AtomicIntegerRefactorDemo autoIncrease = new AtomicIntegerRefactorDemo();
    private static final AtomicInteger k = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            k.incrementAndGet();
        }
    }

    public long start(int threadCount) throws InterruptedException {
        k.set(0);
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
}
