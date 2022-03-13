package com.phukety.demo;

public class Main {
    static int a = 0;
    public static void doCycle() {
        a++;
        doCycle();
    }
    public static void main(String[] args) throws InterruptedException {
        try {
            doCycle();
        } catch (Throwable e) {
            System.out.println(a);
            e.printStackTrace();
        }
    }
}
