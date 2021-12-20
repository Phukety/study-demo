package com.phukety.demo;

import com.phukety.demo.concurrent.lock.DeadLockDemo;
import lombok.extern.slf4j.Slf4j;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.tagging.PartOfSpeechTagging;
import org.apdplat.word.tagging.PinyinTagging;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/segWords")
    public List<Word> segWithStopWords(String sentence, boolean withStop, boolean pinyin, boolean pos) {
        List<Word> words = withStop ? WordSegmenter.segWithStopWords(sentence) : WordSegmenter.seg(sentence);
        // 拼音标注
        if (pinyin) {
            PinyinTagging.process(words);
        }
        // 词性标注
        if (pos) {
            PartOfSpeechTagging.process(words);
        }
        return words;
    }
}
