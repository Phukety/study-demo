package com.phukety.demo.gc;

import java.util.ArrayList;
import java.util.List;

public class stw {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Thread thread1 = new Thread(() -> {
            List<Object> objectList = new ArrayList<>();
            while (objectList.size() < 10000) {
                objectList.add(new Object());
            }
            // 触发STW事件
            System.out.println("触发gc");
            System.gc();
        });
        Thread thread2 = new Thread(() -> {
            while (true) {
                try {
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
