package com.phukety.demo;

import com.phukety.demo.concurrent.lock.DeadLockDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {
    @GetMapping("/c")
    public String get() {
        return "ok";
    }

    @GetMapping("/deadLock")
    public String deadLock() {
        log.info("触发死锁...");
        DeadLockDemo.deadLock();
        return "deadLock";
    }
}
