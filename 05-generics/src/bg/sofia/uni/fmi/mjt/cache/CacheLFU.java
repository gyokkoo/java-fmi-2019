package bg.sofia.uni.fmi.mjt.cache;

import java.util.*;

public class CacheLFU<K, V> implements Cache<K, V> {

    private Map<K, V> cache;
    private Map<K, Integer> keyUsages;

    private long capacity;

    private int cacheHits = 0;
    private int totalUsages = 0;

    CacheLFU(long capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.keyUsages = new LinkedHashMap<>();
    }

    @Override
    public V get(K key) {
        totalUsages++;

        if (cache.containsKey(key)) {
            cacheHits++;
            keyUsages.put(key, keyUsages.get(key) + 1);
        }

        return cache.get(key);
    }

    @Override
    public void set(K key, V value) {
        if (key == null || value == null) {
            return;
        }

        if (!cache.containsKey(key) && size() >= capacity) {
            Map.Entry<K, Integer> leastUsed =
                    Collections.min(keyUsages.entrySet(), Comparator.comparing(Map.Entry::getValue));

            cache.remove(leastUsed.getKey());
            keyUsages.remove(leastUsed.getKey());
        }

        cache.put(key, value);

        if (keyUsages.containsKey(key)) {
            keyUsages.put(key, keyUsages.get(key) + 1);
        } else {
            keyUsages.put(key, 1);
        }
    }

    @Override
    public boolean remove(K key) {
        if (this.cache.containsKey(key)) {
            this.cache.remove(key);
            this.keyUsages.remove(key);
            return true;
        }

        return false;
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public void clear() {
        cacheHits = 0;
        totalUsages = 0;
        cache.clear();
        keyUsages.clear();
    }

    @Override
    public double getHitRate() {
        if (totalUsages == 0) {
            return 0;
        }

        return 1.0 * cacheHits / totalUsages;
    }

    @Override
    public long getUsesCount(K key) {
        if (keyUsages.containsKey(key)) {
            return keyUsages.get(key);
        }

        return 0;
    }
}
