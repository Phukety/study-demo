package com.phukety.demo.concurrent.lock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;

public class ProducerAndConsumer {
    private final static NoReentrantLock lock = new NoReentrantLock();
    private final static Condition producerCondition = lock.newCondition();
    private final static Condition consumerCondition = lock.newCondition();
    private final static Queue<String> queue = new LinkedBlockingDeque<>();
    private final static int MAX = 10;

    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() >= MAX) {
                        producerCondition.await();
                    }
                    queue.add("ele");
                    System.out.println("producer: ele");
                    consumerCondition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        consumerCondition.await();
                    }
                    System.out.println("consumer: " + queue.poll());
                    producerCondition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
