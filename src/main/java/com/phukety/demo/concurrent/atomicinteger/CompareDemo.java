package com.phukety.demo.concurrent.atomicinteger;

public class CompareDemo {
    public static void main(String[] args) throws InterruptedException {
        int times = 100;
        int threadCount = 15;
        long sumTime = 0;
        long avg1, avg2;
        for (int i = 0; i < times; i++) {
            sumTime += AtomicIntegerRefactorDemo.autoIncrease.start(threadCount);
        }
        avg1 = sumTime / times;

        sumTime = 0;
        for (int i = 0; i < times; i++) {
            sumTime += LongAddrRefactorDemo.autoIncrease.start(threadCount);
        }
        avg2 = sumTime / times;

        System.out.println("AtomicInteger平均耗时：" + avg1);
        System.out.println("LongAdder平均耗时：" + avg2);
    }
}
