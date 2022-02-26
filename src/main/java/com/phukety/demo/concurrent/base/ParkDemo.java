package com.phukety.demo.concurrent.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkDemo {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("park begin");
            LockSupport.park(Thread.currentThread());
            System.out.println("park end");
        });
        thread1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        LockSupport.unpark(thread1);
//        thread1.interrupt();

    }
}
