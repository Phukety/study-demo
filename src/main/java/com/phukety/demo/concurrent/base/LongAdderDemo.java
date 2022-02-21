package com.phukety.demo.concurrent.base;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {
    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        adder.add(1);
        System.out.println("adder result: " + adder.longValue());
        adder.add(1);
        System.out.println("adder result: " + adder.longValue());

        LongAccumulator accumulator = new LongAccumulator(Long::sum, 1);
        accumulator.accumulate(1);
        System.out.println("accumulator result: " + accumulator.longValue());
        accumulator.accumulate(1);
        System.out.println("accumulator result: " + accumulator.longValue());
    }
}
