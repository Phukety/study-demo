package com.phukety.demo;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static ReentrantLock lock = new ReentrantLock();

    public static void test() throws InterruptedException {
        System.out.println(~1);
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }
}
