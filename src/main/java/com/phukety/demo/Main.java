package com.phukety.demo;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Object> objects = new ArrayList<>();
        while(true) {
            objects.add(new Object());
            Thread.sleep(1);
        }
    }
}
