package com.phukety.demo.concurrent.base;

public class ThreadNotSafeInteger {
    private final NotSafeInteger notSafeInteger = new NotSafeInteger();

    public static void main(String[] args) throws InterruptedException {
        ThreadNotSafeInteger main = new ThreadNotSafeInteger();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                main.notSafeInteger.addValue();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                main.notSafeInteger.addValue();
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(main.notSafeInteger.getValue());
    }
}

class NotSafeInteger {
    private int value;

    public int getValue() {
        return value;
    }

    public synchronized void addValue() {
        this.value = this.value + 1;
    }
}
