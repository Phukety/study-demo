package com.phukety.demo.concurrent.base;

public class Wait {
    public static void main(String[] args) {
        String lock = "lock";
        Thread thread1 = new Thread(() -> {
            try {
                synchronized (lock) {
                    lock.wait();
                }
                System.out.println(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                synchronized (lock) {
                    lock.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }
}
