package com.phukety.demo.fundesigns.expiredcachealgorithm;

import java.util.*;

/**
 * 过期缓存算法 - LFU(Least Frequently Used)
 * 最近最不常用，当缓存容量满时，移除访问次数最少的元素，如果访问次数相同的元素有多个，则移除最久访问的那个 —— 统计维度
 * <p>
 * 设计思路====>
 * 1. 需要一个数据结构存储缓存内容 -> HashMap
 * 2. 需要一个数据结构存储key的访问次数 -> HashMap
 * 3. 需要一个数据结构存储同样多的访问次数的key的顺序列表, 同时按照次数升序排列 -> TreeMap<Integer, LinkedList>
 * <p>
 * 淘汰机制（添加时，若缓存容量已达上限）====>
 * 1. 根据访问次数排序，移除访问次数最少的元素
 * 2. 若访问次数一样，则移除相同访问次数中最久未访问的key，即LinkedList中的第一个
 */
public class LFUCache<T> {
    // 总容量
    private final int capacity;
    // 缓存载体 <key, value>
    private final Map<String, T> cache = new HashMap<>();
    // key的访问次数 <key, count>
    private final Map<String, Integer> keyCount = new HashMap<>();
    // 访问次数相同的key列表 <count, LinkedList<key>>
    private final Map<Integer, LinkedList<String>> countKeys = new TreeMap<>();

    public LFUCache() {
        this(0);
    }

    public LFUCache(int capacity) {
        this.capacity = capacity == 0 ? 16 : capacity;
    }

    /**
     * 返回缓存内容，注意调用一次get方法，需要增加key的访问次数
     *
     * @param key 缓存key
     * @return null：key==null || key不存在
     */
    public T get(String key) {
        if (key == null || !cache.containsKey(key)) {
            return null;
        }
        // 原来key的访问次数
        int count = keyCount.get(key);
        // 移除原来访问次数的列表项
        countKeys.get(count).remove(key);
        // key的新访问次数
        int newCount = count + 1;
        // 增加key的访问次数
        keyCount.put(key, newCount);
        // 添加新的访问次数列表
        LinkedList<String> newCountKeys = countKeys.computeIfAbsent(newCount, k -> new LinkedList<>());
        newCountKeys.addLast(key);
        return cache.get(key);
    }

    /**
     * 添加缓存
     *
     * @param key   键
     * @param value 值
     * @return false: 添加缓存失败
     */
    public boolean put(String key, T value) {
        if (key == null) {
            return false;
        }
        if (cache.containsKey(key)) {
            cache.put(key, value);
            return true;
        }
        if (cache.size() >= capacity) {
            Iterator<Map.Entry<Integer, LinkedList<String>>> entry = countKeys.entrySet().iterator();
            LinkedList<String> minCountKeys = entry.next().getValue();
            while (entry.hasNext() && minCountKeys.isEmpty()) {
                minCountKeys = entry.next().getValue();
            }
            // 如果没找到最小访问次数的元素,则直接返回
            if (minCountKeys == null || minCountKeys.isEmpty()) {
                return false;
            }
            // 移除最久未访问的key
            String removeKey = minCountKeys.removeFirst();
            // 移除访问次数
            keyCount.remove(removeKey);
            // 移除缓存
            cache.remove(removeKey);
        }
        keyCount.put(key, 1);
        LinkedList<String> newCountKeys = countKeys.computeIfAbsent(1, k -> new LinkedList<>());
        newCountKeys.add(key);
        cache.put(key, value);
        return true;
    }

    /**
     * 当前缓存元素总量
     *
     * @return 缓存元素总数
     */
    public int size() {
        return cache.size();
    }
}
