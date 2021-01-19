package com.phukety.demo.concurrent.atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerRefactorDemo implements Runnable {
    private static final AtomicInteger k = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            k.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerRefactorDemo autoIncrease = new AtomicIntegerRefactorDemo();

        Thread thread1 = new Thread(autoIncrease);
        Thread thread2 = new Thread(autoIncrease);

        thread1.start();
        thread2.start();

        Thread.sleep(1000);
        System.out.println(AtomicIntegerRefactorDemo.k.get());
    }
}
