package com.phukety.demo.concurrent.threadpool;

import lombok.Data;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 带有返回值的任务
 */
public class Task implements Callable<Task.TaskResult> {

    private final long id;
    private final int maxRetry;
    private final Random random;

    @Data
    static class TaskResult {
        long taskId;
        boolean success;

        public TaskResult(long taskId, boolean success) {
            this.taskId = taskId;
            this.success = success;
        }
    }

    public Task() {
        this(Thread.currentThread().getId(), 3);
    }

    public Task(long id, int maxRetry) {
        this.id = id;
        this.maxRetry = maxRetry;
        random = new Random();
    }

    @Override
    public TaskResult call() throws Exception {
        boolean success = false;
        int retryCount = 0;
        long threadId = Thread.currentThread().getId();
        System.out.println("任务" + id + "开始执行(线程:" + threadId + ")...");
        while (!success && retryCount < maxRetry) {
            // 任务执行随机时间2s-4s
            TimeUnit.SECONDS.sleep(new Random().nextInt(3) + 1);
            success = random.nextInt(4) == 1;
            retryCount++;
            System.out.println("任务" + id + "第" + retryCount + "次重试(线程:" + threadId + ")...");
        }
        System.out.println("任务" + id + "执行" + (success ? "成功" : "失败"));
        // 随机执行失败
        return new TaskResult(id, success);
    }

    public static void main(String[] args) {
        System.out.println(1*1.0/2);
    }
}
