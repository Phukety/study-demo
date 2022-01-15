package com.phukety.demo.fundesigns.expiredcachealgorithm;

/**
 * 过期缓存算法 - FIFO(First Input First Output)
 * 先进先出，如果缓存容量满，则优先移出最早加入缓存的数据；其内部可以使用队列实现。
 *
 * 设计思路====>
 * 1. LinkedHashMap保存缓存数据，保证时间顺序
 * 2. 额外的map用来保存key的过期特性，比如TreeMap，将“剩余存活时间”作为key，利用Treemap的排序特性
 *
 */
public class FIFOCache {
}
