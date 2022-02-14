package com.phukety.demo.concurrent.base;

public class ThreadNotSafeCount {
    private long value;

    public long getValue() {
        return value;
    }

    public void inc() {
        ++value;
    }
}
