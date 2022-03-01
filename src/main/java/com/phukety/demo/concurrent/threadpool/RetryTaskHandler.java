package com.phukety.demo.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class RetryTaskHandler {
    // 最大并发执行任务数
    private final int maxTaskSize;
    // 任务列表
    private final List<Task> taskList;
    // 线程池
    private final ThreadPoolExecutor executor;

    public RetryTaskHandler(List<Task> taskList) {
        this(3, taskList);
    }

    public RetryTaskHandler(int maxTaskSize) {
        this(maxTaskSize, new ArrayList<>());
    }

    public RetryTaskHandler(int maxTaskSize, List<Task> taskList) {
        this.maxTaskSize = maxTaskSize;
        this.taskList = taskList;
        executor = new ThreadPoolExecutor(maxTaskSize, maxTaskSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public List<Task.TaskResult> handle() {
        try {
            List<Future<Task.TaskResult>> futures = executor.invokeAll(taskList);
            return futures.stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Task.TaskResult(0, false);
                }
            }).collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void close() {
        executor.shutdownNow();
    }


}
