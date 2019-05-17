package com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

/** Interface for abstract data type aka associative array */
public interface MapP2P<K, V> {

    /** Adds key and value in associative array */
    boolean put(K key, V value);

    /** Gets from associative array particular value which associated with received key */
    V get(K key);

    /** Removes from associative array particular value which associated with received key */
    V remove(K key);

    /** Adds in current map other collection which implements interface MapP2P */
    boolean addAll(MapP2P<K, V> mapP2P);

    /** Returns information informing whether the given key is in the collection */
    boolean containsKey(K key);

    /** Returns information informing whether the given value is in the collection */
    boolean containsValue(Object value);

    /** Returns amount element which stored in the associative array */
    int size();

    /** Removes all elements (key-value) from collection */
    void clear();

    /** Returns set of nodes which contains key-value */
    Set<Node<K, V>> entrySet();

    /** Returns information about whether there are elements on the current associative array */
    boolean isEmpty();

    /** Interface for Node that contains key and value */
    interface Node<K, V> {
        /** Returns the key of current node */
        K getKey();
        /** Returns the value of current node */
        V getValue();
    }

    /** Performs the given action for each node in this map */
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Node<K, V> node : entrySet()) {
            action.accept(node.getKey(), node.getValue());
        }
    }

}
