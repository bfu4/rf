package com.localhost22.rf;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The Pair object creates a pair-like structure.
 * @param <K> key value. (1)
 * @param <V> value value. (2)
 */
public class Pair<K, V> implements Comparable<Pair<K, V>> {

    /**
     * Key value (1).
     */
    private final K key;

    /**
     * Value value (2).
     */
    private final V value;

    public Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get the key (1).
     * @return key
     */
    public K getKey() {
        return this.key;
    }

    /**
     * Get the value (2).
     * @return value
     */
    public V getValue() {
        return this.value;
    }

    /**
     * Compare a pair to an object.
     * @param o object
     * @return if the objects are equal
     */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.deepEquals(pair.key, key)
                && Objects.deepEquals(pair.value, value);
    }

    /**
     * Get the hashcode of the object.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * The level of comparison between this and another pair.
     * @param o other pair
     * @return how similar the two pairs are.
     */
    @Override
    public int compareTo(final @NotNull Pair<K, V> o) {
        if (o.equals(this)) {
            return 0;
        }
        if (Objects.deepEquals(o.key, key)
                || Objects.deepEquals(o.value, value)) {
            return 1;
        }
        return 2;
    }

}
