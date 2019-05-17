package com.shpp.p2p.cs.sushakov.assignment17.collections;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.CollectionP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.QueueP2P;

import java.util.Iterator;

/**
 * Represents abstract data type as a queue. This collection
 * based on linked list.
 */
public class LinkedQueueP2P<E> implements QueueP2P<E> {

    /** List which stored elements of current queue */
    private LinkedListP2P<E> listP2P = new LinkedListP2P<>();

    /**
     * Adds a particular element in the end of the queue.
     *
     * @param e particular element which added to queue.
     */
    @Override
    public void offer(E e) {
        listP2P.add(e);
    }

    /**
     * Returns the element which stored on the head of the queue
     * without removing it from queue.
     *
     * @return the element which stored on the head of the queue.
     */
    @Override
    public E peek() {
        return listP2P.get(0);
    }

    /**
     * Returns the element which stored on the head of the queue
     * with removing it from queue.
     *
     * @return the element which stored on the head of the queue.
     *         If queue is empty - returns null.
     */
    @Override
    public E poll() {
        E element;
        try {
            element = listP2P.remove(0);
        } catch (IndexOutOfBoundsException e) {
            element = null;
        }

        return element;
    }

    /**
     * Returns information about whether there are elements on the current queue.
     *
     * @return false, if current queue contains some elements, otherwise - true (when queue is empty).
     */
    @Override
    public boolean isEmpty() {
        return listP2P.isEmpty();
    }

    /**
     * Returns amount element which stored in the current queue.
     *
     * @return amount elements stored in the current queue.
     */
    @Override
    public int size() {
        return listP2P.size();
    }

    /**
     * Returns iterator that allow iterate through current queue.
     *
     * @return iterator for current current queue.
     */
    @Override
    public Iterator<E> iterator() {
        return listP2P.iterator();
    }

    /**
     * Returns iterator that allows iterate through queue in reverse order.
     *
     * @return the iterator that allows iterate through queue in revers order.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return listP2P.descendingIterator();
    }

    /**
     * Returns array of all elements which stored in current queue.
     *
     * @return array of all elements which stored in current queue.
     */
    @Override
    public E[] toArray() {
        return listP2P.toArray();
    }

    /**
     * Adds to current collection elements of other collection.
     *
     * @param collectionP2P collection of added elements.
     */
    @Override
    public void addAll(CollectionP2P<? extends E> collectionP2P) {
        listP2P.addAll(collectionP2P);
    }

}
