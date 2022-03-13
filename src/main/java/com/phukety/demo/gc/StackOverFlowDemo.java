package com.phukety.demo.gc;

/**
 * 栈内存溢出demo
 */
public class StackOverFlowDemo {
    static int count = 0;
    // 递归: 方法运行一次，会往虚拟机栈压入一个栈帧
    public static void doCycle() {
        count++;
        doCycle();
    }
    public static void main(String[] args) throws InterruptedException {
        try {
            doCycle();
        } catch (Throwable e) {
            System.out.println(count);
            e.printStackTrace();
        }
    }
}
