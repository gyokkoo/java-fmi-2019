package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.enums.EvictionPolicy;

public interface CacheFactory {

    long DEFAULT_CAPACITY = 10000;

    /**
     * Constructs a new Cache<K, V> with the specified maximum capacity and eviction policy
     *
     * @throws IllegalArgumentException if the given capacity is less than or equal to zero
     */
    static <K, V> Cache<K, V> getInstance(long capacity, EvictionPolicy policy) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be null!");
        }

        if (EvictionPolicy.LEAST_FREQUENTLY_USED.equals(policy)) {
            return new CacheLFU<>(capacity);
        }

        if (EvictionPolicy.RANDOM_REPLACEMENT.equals(policy)) {
            return new CacheRR<>(capacity);
        }

        return null;
    }

    /**
     * Constructs a new Cache<K, V> with maximum capacity of 10_000 items and the specified eviction policy
     */
    static <K, V> Cache<K, V> getInstance(EvictionPolicy policy) {
        return CacheFactory.getInstance(DEFAULT_CAPACITY, policy);
    }
}
