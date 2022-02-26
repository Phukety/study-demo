package com.phukety.demo.concurrent.lock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NoReentrantLock implements Lock, Serializable {

    private final Sync sync;

    public NoReentrantLock() {
        sync = new Sync();
    }

    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        Condition newCondition() {
            return new ConditionObject();
        }

    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public static void testLock(Lock lock) {
        new Thread(() -> {
            lock.lock();
            System.out.println("thread1 lock");
        }).start();

        new Thread(() -> {
            lock.lock();
            System.out.println("thread2 lock");
        }).start();
    }

    public static void testCondition(Lock lock) {
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            System.out.println("thread1 start");
//            try {
//                condition.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("thread1 end");
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("thread2 start");
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2 end");
        }).start();
    }


    public static void main(String[] args) {
        NoReentrantLock lock = new NoReentrantLock();
        ReentrantLock lock1 = new ReentrantLock();
//        testLock(lock1);
        testCondition(lock);
    }
}
