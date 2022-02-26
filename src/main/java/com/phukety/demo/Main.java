package com.phukety.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static ReentrantLock lock = new ReentrantLock();

    public static void test() throws InterruptedException {
        lock.lock();
        Condition condition = lock.newCondition();
        condition.await();
        condition.await();
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }
}
