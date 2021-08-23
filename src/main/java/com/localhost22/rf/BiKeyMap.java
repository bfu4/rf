package com.localhost22.rf;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

/**
 * The BiKeyMap is a map with two keys.
 * @param <K1> key value (1).
 * @param <K2> key value (2).
 * @param <V>  value.
 */
class BiKeyMap<K1, K2, V> {

    /**
     * The keys.
     */
    private final Map<Pair<K1, K2>, V> entries;

    /**
     * Create a new bi-key map.
     */
    BiKeyMap() {
        this.entries = new TreeMap<>();
    }

    /**
     * Size of the map.
     * @return size of the map.
     */
    public int size() {
        return entries.size();
    }

    /**
     * Get a value from the map.
     * @param initialKey   key (1).
     * @param secondaryKey key (2).
     * @return value or null.
     */
    public V get(final K1 initialKey, final K2 secondaryKey) {
        Pair<K1, K2> pair = new Pair<>(initialKey, secondaryKey);
        return get(pair);
    }

    /**
     * Get a value from a pair.
     * @param pair pair
     * @return value or null.
     */
    public V get(@NotNull final Pair<K1, K2> pair) {
        if (!entries.containsKey(pair)) {
            return null;
        }
        return entries.get(pair);
    }

    /**
     * Add a value to the map.
     * @param initialKey   key (1).
     * @param secondaryKey key (2).
     * @param object       object to add
     */
    public synchronized void add(final K1 initialKey, final K2 secondaryKey, final V object) {
        final Pair<K1, K2> pair = new Pair<>(initialKey, secondaryKey);
        entries.put(pair, object);
    }

}
