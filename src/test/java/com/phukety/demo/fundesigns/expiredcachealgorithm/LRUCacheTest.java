package com.phukety.demo.fundesigns.expiredcachealgorithm;


import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class LRUCacheTest {
    @Test
    public void testLRUCache() {
        LRUCache<String> cache = new LRUCache<>(5);

        cache.put("1", "1");
        cache.put("2", "2");
        assertTrue(cache.put("3", "3"));

        assertEquals(cache.size(), 3);
        assertEquals(cache.get("1"), "1");

        cache.put("4", "4");
        assertTrue(cache.put("5", "5"));

        assertEquals(cache.size(), 5);
        assertEquals(cache.get("5"), "5");

        assertTrue(cache.put("6", "6"));

        assertEquals(cache.size(), 5);
        assertEquals(cache.get("5"), "5");
        assertEquals(cache.get("6"), "6");
        assertEquals(cache.get("1"), "1");
        assertNull(cache.get("2"));

        assertFalse(cache.put("6", "7"));
        assertEquals(cache.size(), 5);
        assertEquals(cache.get("6"), "6");
    }

    @Test
    public void testExpiredLRUCache() throws InterruptedException {
        LRUCache<String> cache = new LRUCache<>(5);

        cache.put("1", "1", 1L, TimeUnit.SECONDS);
        cache.put("2", "2", 2L, TimeUnit.SECONDS);
        cache.put("3", "3", 2L, TimeUnit.SECONDS);
        cache.put("4", "4");
        cache.put("5", "5");

        assertEquals(cache.size(), 5);
        assertEquals(cache.get("1"), "1");
        assertEquals(cache.get("5"), "5");

        Thread.sleep(1000);
        assertTrue(cache.put("6", "6", 2L, TimeUnit.SECONDS));
        assertEquals(cache.size(), 5);
        assertNull(cache.get("1"));
        assertEquals(cache.get("2"), "2");

        Thread.sleep(1000);

        assertTrue(cache.put("7", "7"));
        assertEquals(cache.size(), 4);
        assertTrue(cache.put("8", "8"));
        assertEquals(cache.size(), 5);
        assertNull(cache.get("1"));
        assertNull(cache.get("2"));
        assertNull(cache.get("3"));
        assertEquals(cache.get("4"), "4");

        Thread.sleep(1000);
        assertTrue(cache.put("9", "9"));
        assertEquals(cache.size(), 5);
        assertNull(cache.get("6"));
    }

    @Test
    public void testBoundary() {
        LRUCache<String> cache = new LRUCache<>();
        int i = 0;
        int capacity = cache.size();
        while (cache.put(String.valueOf(i), String.valueOf(i)) && capacity != cache.size()) {
            i++;
            capacity = cache.size();
        }
        assertEquals(cache.size(), 16);

        assertNull(cache.get(null));
        assertNull(cache.get(""));
    }

    @Test
    public void testGenerics() throws InterruptedException, ExecutionException {
        LRUCache<Integer> integerCache = new LRUCache<>();
        integerCache.put("1", 1);
        integerCache.put("2", 2);
        integerCache.put("3", 3);
        assertEquals(integerCache.size(), 3);
        assertEquals(integerCache.get("3").intValue(), 3);

        LRUCache<ExecutorService> executorCache = new LRUCache<>();
        executorCache.put("cached", Executors.newCachedThreadPool());
        executorCache.put("fixed", Executors.newFixedThreadPool(2));
        executorCache.put("single", Executors.newSingleThreadExecutor());
        assertEquals(executorCache.size(), 3);

        ExecutorService executorService = executorCache.get("single");
        Boolean result = executorService.invokeAny(new ArrayList<Callable<Boolean>>() {{
            add(Executors.callable(() -> System.out.println("task is running"), true));
        }});
        assertEquals(result, true);
    }
}