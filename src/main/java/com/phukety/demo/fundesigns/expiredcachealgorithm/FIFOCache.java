package com.phukety.demo.fundesigns.expiredcachealgorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 过期缓存算法 - FIFO(First Input First Output)
 * 先进先出，如果缓存容量满，则优先移出最早加入缓存的数据；其内部可以使用队列实现。
 * <p>
 * 设计思路====>
 * 1. LinkedHashMap保存缓存数据，保证时间顺序。需要频繁查找节点，且需要断链和接链
 * 2. 额外的map用来保存key的过期特性，比如TreeMap，将“剩余存活时间”作为key，利用Treemap的排序特性
 * <p>
 * 淘汰机制（添加时，若缓存容量已达上限）====>
 * 1. 先移除缓存队列中已经满足过期的键
 * 2. 若还不够，则再根据想先进先出的原则，移除最早加入缓存的数据
 */
public class FIFOCache<T> {
    // 缓存容量
    private final int capacity;
    // 缓存载体<key,value>
    private final Map<String, T> cache = new LinkedHashMap<>();
    // 有过期时间的key载体<key,expireTime>
    private final Map<String, Long> expire = new HashMap<>();

    public FIFOCache() {
        this(0);
    }

    public FIFOCache(int capacity) {
        this.capacity = capacity == 0 ? 16 : capacity;
    }


    /**
     * 返回缓存内容
     *
     * @param key 键
     * @return null:当key已经过期
     */
    T get(String key) {
        if (key == null) {
            return null;
        }
        if (expire.containsKey(key) && expire.get(key) <= System.currentTimeMillis()) {
            expire.remove(key);
            return null;
        }
        return cache.get(key);
    }

    /**
     * 不带过期时间的缓存
     *
     * @param key   键
     * @param value 值
     * @return true:成功, false:失败
     */
    boolean put(String key, T value) {
        return put(key, value, -1, TimeUnit.MILLISECONDS);
    }

    /**
     * 添加带有过期时间的缓存
     *
     * @param key         键
     * @param value       值
     * @param expiredTime 过期时间
     * @param unit        时间单位
     * @return true:成功, false:失败
     */
    boolean put(String key, T value, long expiredTime, TimeUnit unit) {
        if (cache.containsKey(key)) {
            return false;
        }
        // 若容量上限，先移除过期key
        if (cache.size() >= capacity) {
            for (Map.Entry<String, Long> entry : expire.entrySet()) {
                // 未过期则跳过该键
                if (entry.getValue() > System.currentTimeMillis()) {
                    continue;
                }
                // 移除过期key
                cache.remove(entry.getKey());
            }
        }
        // 仍然容量上限，则移除最早加入缓存的数据
        if (cache.size() >= capacity) {
            Iterator<Map.Entry<String, T>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext() && cache.size() >= capacity) {
                // 移除过期key
                expire.remove(iterator.next().getKey());
                // 移除元素
                iterator.remove();
            }
        }
        // 此时容量一定是足够的,再进行添加
        if (expiredTime >= 0) {
            expire.put(key, System.currentTimeMillis() + unit.toMillis(expiredTime));
        }
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
