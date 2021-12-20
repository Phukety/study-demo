package com.phukety.demo;

import lombok.extern.slf4j.Slf4j;
import org.apdplat.word.WordSegmenter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WordComponentRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("===================================加载分词组件===================================");
            WordSegmenter.segWithStopWords("初始化分词组件");
            log.info("===================================分词组件加载完成===================================");
        } catch (Exception e) {
            log.error("===================================分词组件加载失败===================================");
        }

    }
}
