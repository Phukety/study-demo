package com.phukety.demo.fundesigns.expiredcachealgorithm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class LFUCacheTest {
    @Test
    public void testLFUCache() {
        LFUCache<String> cache = new LFUCache<>(5);

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

        assertTrue(cache.put("7", "7"));
        assertNull(cache.get("3"));

        assertTrue(cache.put("7", "8"));
        assertEquals(cache.get("7"), "8");
        assertEquals(cache.get("4"), "4");
        assertEquals(cache.get("6"), "6");
        assertEquals(cache.get("5"), "5");
        assertEquals(cache.get("1"), "1");
    }
}