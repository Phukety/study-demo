package com.phukety.demo.concurrent.atomicinteger;

public class AtomicIntegerDemo implements Runnable {
    private static int k = 0;
    private final Object lock = new Object();

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < 10000; i++) {
                k++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo autoIncrease = new AtomicIntegerDemo();

        Thread thread1 = new Thread(autoIncrease);
        Thread thread2 = new Thread(autoIncrease);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(AtomicIntegerDemo.k);
    }
}
