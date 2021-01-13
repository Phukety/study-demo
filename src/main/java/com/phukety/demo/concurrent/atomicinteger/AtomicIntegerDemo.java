package com.phukety.demo.concurrent.atomicinteger;

public class AtomicIntegerDemo implements Runnable {
    private static int k = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            k++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo autoIncrease = new AtomicIntegerDemo();

        Thread thread1 = new Thread(autoIncrease);
        Thread thread2 = new Thread(autoIncrease);

        thread1.start();
        thread2.start();

        Thread.sleep(1000);
        System.out.println(AtomicIntegerDemo.k);
    }
}
