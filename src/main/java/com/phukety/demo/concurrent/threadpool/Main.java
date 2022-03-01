package com.phukety.demo.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static List<Task> getTasks(int taskNum) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < taskNum; i++) {
            tasks.add(new Task(i + 1, 3));
        }
        return tasks;
    }

    public static void main(String[] args) {
        int taskNum = 10;
        RetryTaskHandler handler = new RetryTaskHandler(5, getTasks(taskNum));
        List<Task.TaskResult> resultList = handler.handle();
        List<Long> succeedTasks = resultList.stream().filter(result -> result.success).map(result -> result.taskId).collect(Collectors.toList());
        List<Long> failedTasks = resultList.stream().filter(result -> !result.success).map(result -> result.taskId).collect(Collectors.toList());
        System.out.println(taskNum + "个任务执行完成，其中" + succeedTasks + "任务完成," + failedTasks + "任务失败,任务成功率：" + (succeedTasks.size() * 1.0 / taskNum) * 100 + "%");
        handler.close();
    }
}
