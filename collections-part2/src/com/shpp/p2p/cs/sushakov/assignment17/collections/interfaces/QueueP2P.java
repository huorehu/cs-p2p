package com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces;

import java.util.Iterator;

/**
 * This interface contains methods that allow to perform basic operations
 * on the elements of the queue that implements this interface.
 *
 * @param <E> the type of elements which stored in queue.
 */
public interface QueueP2P<E> extends CollectionP2P<E> {

    /**
     * Adds a particular element in the end of the queue.
     *
     * @param e particular element which added to queue.
     */
    void offer(E e);

    /**
     * Returns the element which stored on the head of the queue
     * without removing it from queue.
     *
     * @return the element which stored on the head of the queue.
     */
    E peek();

    /**
     * Returns the element which stored on the head of the queue
     * with removing it from queue.
     *
     * @return the element which stored on the head of the queue.
     */
    E poll();

    /**
     * Returns iterator that allows iterate through queue in reverse order.
     *
     * @return the iterator that allows iterate through queue in revers order.
     */
    Iterator<E> descendingIterator();

}
