package calculator.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple LRU cache for storing expression evaluation results
 * Improves performance by caching frequently used calculations
 */
public class ExpressionCache {
    
    private static final int MAX_CACHE_SIZE = 100;
    private static final Map<String, Double> cache = new LinkedHashMap<String, Double>(MAX_CACHE_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    };

    /**
     * Retrieves a cached result for an expression
     * @param expression The expression key
     * @return The cached result, or null if not found
     */
    public static Double get(String expression) {
        synchronized (cache) {
            return cache.get(expression);
        }
    }

    /**
     * Stores a calculation result in the cache
     * @param expression The expression key
     * @param result The result to cache
     */
    public static void put(String expression, Double result) {
        synchronized (cache) {
            cache.put(expression, result);
        }
    }

    /**
     * Clears all cached entries
     */
    public static void clear() {
        synchronized (cache) {
            cache.clear();
        }
    }

    /**
     * Gets the current number of cached entries
     * @return The cache size
     */
    public static int getSize() {
        synchronized (cache) {
            return cache.size();
        }
    }

    /**
     * Checks if a key exists in the cache
     * @param expression The expression key
     * @return true if the key exists, false otherwise
     */
    public static boolean contains(String expression) {
        synchronized (cache) {
            return cache.containsKey(expression);
        }
    }
}
