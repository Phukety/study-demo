package com.phukety.demo.fundesigns.expiredcachealgorithm;

/**
 * 过期缓存算法 - LRU(Least recently used)
 * 最近最少使用，是目前最常用的缓存算法和设计方案之一，其移除策略为“当缓存（页）满时，优先移除最近最久未使用的数据”— 时间维度
 * 优点是易于设计和使用，适用场景广泛
 * <p>
 * 设计思路====>
 * 基本和FIFO过期缓存算法一致，只是需要把最近访问的key放到最前面
 */
public class LRUCache<T> extends FIFOCache<T> {

    public LRUCache() {
        super();
    }

    public LRUCache(int capacity) {
        super(capacity);
    }

    @Override
    T get(String key) {
        if (key == null) {
            return null;
        }
        if (expire.containsKey(key) && expire.get(key) <= System.currentTimeMillis()) {
            expire.remove(key);
            cache.remove(key);
            return null;
        }
        // 访问过后，代表该key变为最常访问的key，所以，先移除key，再添加key。
        T value = cache.get(key);
        if (value == null) {
            return null;
        }
        cache.remove(key);
        cache.put(key, value);
        return value;
    }
}
