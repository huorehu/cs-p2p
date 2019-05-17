package com.shpp.p2p.cs.sushakov.assignment17.collections;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.CollectionP2P;

/**
 * The collection which stored elements as stack
 * which is implemented on the basis of an array.
 */
public class StackP2P<E> extends ArrayListP2P<E> implements CollectionP2P<E> {

    public StackP2P(Class<E> elementType) {
        this(elementType, ARRAY_CAPACITY);
    }

    public StackP2P(Class<E> elementType, int capacity) {
        super(elementType, capacity);
    }

    /**
     * Adds a particular element in the top of current stack.
     *
     * @param e particular element which added to stack.
     */
    public void push(E e) {
        add(e);
    }

    /**
     * Returns the element which stored on the top of the stack
     * without removing it from stack.
     *
     * @return the element which stored on the top of the stack.
     */
    public E peek() {
        return get(size - 1);
    }

    /**
     * Returns the element which stored on the top of the stack
     * with removing it from stack.
     *
     * @return the element which stored on the top of the stack.
     */
    public E pop() {
        return remove(size - 1);
    }

}
