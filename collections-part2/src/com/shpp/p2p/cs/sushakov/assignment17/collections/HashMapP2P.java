package com.shpp.p2p.cs.sushakov.assignment17.collections;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class implements interface MapP2P and all it methods.
 * It provides methods which allows executes basic operation on a map.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values
 */
public class HashMapP2P<K, V> implements MapP2P<K, V> {

    /** Initial size of array which stored lists that contains Nodes with keys and values */
    private static final int INITIAL_CAPACITY = 16;
    /** Load factor which setting percentage of the fullness of the array storing the lists, before increasing it */
    private static final float LOAD_FACTOR = 0.75f;
    /** Indicates how many times will be increased array */
    private static final int GROWTH_FACTOR = 2;
    /** Simple odd number for calculating hash for node */
    private static final int SIMPLE_ODD = 37;

    /** Array contains list of nodes that contains keys and values */
    private LinkedListP2P<HashNode<K, V>>[] nodes;
    /** Amount of pairs key-value */
    private int size = 0;
    /** Counter of modifications for current collection */
    private int modCount = 0;
    /** The maximum occupancy of the array that stores the lists, after which it size will increase */
    private int sizeThreshold;

    public HashMapP2P() {
        this(INITIAL_CAPACITY, LOAD_FACTOR);
    }

    public HashMapP2P(int capacity) {
        this(capacity, LOAD_FACTOR);
    }

    public HashMapP2P(float loadFactor) {
        this(INITIAL_CAPACITY, loadFactor);
    }

    @SuppressWarnings("unchecked")
    public HashMapP2P(int capacity, float loadFactor) {
        nodes = new LinkedListP2P[capacity];
        sizeThreshold = (int) (loadFactor * capacity);
    }

    /**
     * Adds key and value in associative array. If the array already
     * contains a value with a certain key, it will be overwritten.
     *
     * @return true if pair key-value was added.
     */
    @Override
    public boolean put(K key, V value) {
        if (size == sizeThreshold) {
            increaseTableSize();
        }

        modCount++;
        int indexByKey = getIndexByKey(key);
        if (nodes[indexByKey] != null) {
            handleCollision(nodes[indexByKey], key, value);
            return true;
        }

        HashNode<K, V> node = new HashNode<>(key, value);
        nodes[node.index] = new LinkedListP2P<>();
        nodes[node.index].add(node);
        size++;
        return true;
    }

    /** Increses size of array */
    @SuppressWarnings("unchecked")
    private void increaseTableSize() {
        LinkedListP2P<HashNode<K, V>>[] tempArray = nodes;
        nodes = new LinkedListP2P[nodes.length * GROWTH_FACTOR];
        sizeThreshold = (int) (nodes.length * LOAD_FACTOR);
        size = 0;

        for (LinkedListP2P<HashNode<K, V>> hashNodes : tempArray) {
            if (hashNodes != null) {
                copyNodesFromList(hashNodes);
            }
        }
    }

    /** Copies nodes from list to new table  */
    private void copyNodesFromList(LinkedListP2P<HashNode<K, V>> hashNodes) {
        for (HashNode<K, V> hashNode : hashNodes) {
            put(hashNode.key, hashNode.value);
        }
    }

    /** Handles collision if under the indexed index is already stored pair key-value */
    private void handleCollision(LinkedListP2P<HashNode<K, V>> nodesList, K key, V value) {
        for (HashNode<K, V> checkedNode : nodesList) {
            if (checkedNode.key.equals(key)) {
                checkedNode.value = value;
                return;
            }
        }
        nodesList.add(new HashNode<>(key, value));
        size++;
    }

    /**
     * Gets from associative array particular value which associated with received key.
     *
     * @return value which associated with received key.
     */
    @Override
    public V get(K key) {
        int index = getIndexByKey(key);
        if (!checkOnContainsElements(index)) {
            return null;
        }
        if (index == 0) {
            return nodes[0].get(0).value;
        }

        for (HashNode<K, V> node : nodes[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Removes from associative array particular value which associated with received key
     *
     * @return the value that was removed. If value stored with received key doesn't exist - return null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        int indexRemovedElement = getIndexByKey(key);
        if (!checkOnContainsElements(indexRemovedElement)) {
            return null;
        }
        if (indexRemovedElement == 0) {
            return nodes[0].remove(0).value;
        }

        modCount++;
        V valueRemovedNode;
        for (int i = 0; i < nodes[indexRemovedElement].size(); i++) {
            if (key.equals(nodes[indexRemovedElement].get(i).key)) {
                valueRemovedNode = nodes[indexRemovedElement].remove(i).value;
                size--;
                return valueRemovedNode;
            }
        }

        return null;
    }

    /* Checks is contain under index some elements */
    private boolean checkOnContainsElements(int index) {
        return nodes[index] != null && nodes[index].size() > 0;
    }

    /** Removes all elements (key-value) from current collection */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        modCount++;
        size = 0;
        sizeThreshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
        nodes = new LinkedListP2P[INITIAL_CAPACITY];
    }

    /**
     * Adds in current map other collection which implements interface MapP2P.
     *
     * @return true - if collection was added.
     */
    @Override
    public boolean addAll(MapP2P<K, V> mapP2P) {
        if (mapP2P == null) {
            throw new IllegalArgumentException("mapP2P can't be null");
        }

        Set<Node<K, V>> nodeSet = mapP2P.entrySet();
        for (Node<K, V> node : nodeSet) {
            put(node.getKey(), node.getValue());
        }

        return true;
    }

    /**
     * Returns information informing whether the given key is in the collection.
     *
     * @return true - if key contains, otherwise - false.
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns information informing whether the given value is in the collection.
     *
     * @return true - if value contains, otherwise - false.
     */
    @Override
    public boolean containsValue(Object value) {
        Iterator<HashNode<K, V>> nodeIterator = iterator();
        while (nodeIterator.hasNext()) {
            if (nodeIterator.next().value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Amount pairs (key-value) in current collection.
     *
     * @return amount pairs (key-value).
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns information about whether there are elements on the current associative array
     *
     * @return true - if contains node, otherwise - false.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Implements interface {@link Node<>} which contains methods that
     * allow receive key and value, stored in current node
     */
    private class HashNode<K, V> implements Node<K, V> {

        private K key;
        private V value;
        private int index;

        private HashNode(K key, V value) {
            this.key = key;
            this.value = value;
            index = getIndexByKey(key);
        }

        @Override
        public int hashCode() {
            return Math.abs(SIMPLE_ODD * key.hashCode());
        }

        /**
         * Compare two nodes and if its keys is equals then its equal,
         * otherwise - not equals.
         */
        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            return this == obj ||
                    obj instanceof HashMapP2P.HashNode &&
                    this.hashCode() == obj.hashCode() &&
                    this.key.equals(((HashNode) obj).key);
        }

        /** Returns the key of current node */
        @Override
        public K getKey() {
            return key;
        }

        /** Returns the value of current node */
        @Override
        public V getValue() {
            return value;
        }
    }

    /** Returns index which associated with particular key in current table with it current size */
    private int getIndexByKey(Object key) {
        return key == null ? 0 : Math.abs(SIMPLE_ODD * key.hashCode()) % nodes.length;
    }

    /**
     * Returns set of nodes which contains key-value that stored in current table.
     *
     * @return set of nodes.
     */
    public Set<Node<K, V>> entrySet() {
        Set<Node<K, V>> entries = new HashSet<>();
        Iterator<HashNode<K, V>> nodeIterator = iterator();
        while (nodeIterator.hasNext()) {
            entries.add(nodeIterator.next());
        }
        return entries;
    }

    /** Iterator for iterating through list of list of nodes. */
    private Iterator<HashNode<K, V>> iterator() {
        return new Iterator<HashNode<K, V>>() {

            /** Iterator for list of nodes */
            private Iterator<HashNode<K, V>> iteratorForNodesList;
            /** Amount nodes which was returned */
            private int nodePointer = 0;
            /** Pointer indexing list of nodes */
            private int indexInArrayOfLists = 0;
            /** The number of modifications to the collection at the time the iterator was created */
            private int amountModifications = modCount;

            @Override
            public boolean hasNext() {
                return nodePointer < size;
            }

            @Override
            public HashNode<K, V> next() {
                if (amountModifications != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (nodePointer >= size) {
                    throw new IndexOutOfBoundsException("No more items");
                }
                /* nodePointer ensures that the progress of the program does not fall
                 * into this area, if the elements of the table no longer exist */
                while (indexInArrayOfLists < nodes.length && iteratorForNodesList == null ||
                        iteratorForNodesList != null && !iteratorForNodesList.hasNext()) {
                    if (nodes[indexInArrayOfLists] == null) {
                        indexInArrayOfLists++;
                        continue;
                    }
                    iteratorForNodesList = nodes[indexInArrayOfLists].iterator();
                    indexInArrayOfLists++;
                }
                nodePointer++;

                return iteratorForNodesList != null ? iteratorForNodesList.next() : null;
            }
        };
    }

}
