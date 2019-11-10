package bg.sofia.uni.fmi.mjt.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheRR<K, V> implements Cache<K, V> {

    private Map<K, V> cache;

    private long capacity;

    private int cacheHits = 0;
    private int totalUsages = 0;

    CacheRR(long capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    @Override
    public V get(K key) {
        totalUsages++;

        if (cache.containsKey(key)) {
            cacheHits++;
            return cache.get(key);
        }

        return null;
    }

    @Override
    public void set(K key, V value) {
        if (key == null || value == null) {
            return;
        }

        if (!cache.containsKey(key) && size() >= capacity) {
            K elementToRemove = cache.keySet().iterator().next();
            cache.remove(elementToRemove);
        }

        cache.put(key, value);

        totalUsages++;
    }

    @Override
    public boolean remove(K key) {
        if (cache.containsKey(key)) {
            cache.remove(key);
            return true;
        }

        return false;
    }

    @Override
    public long size() {
        return this.cache.size();
    }

    @Override
    public void clear() {
        cacheHits = 0;
        totalUsages = 0;
        cache.clear();
    }

    @Override
    public double getHitRate() {
        if (totalUsages == 0) {
            return 0.0;
        }

        return 1.0 * cacheHits / totalUsages;
    }

    @Override
    public long getUsesCount(K key) {
        throw new UnsupportedOperationException();
    }
}
