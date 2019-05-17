package com.shpp.p2p.cs.sushakov.assignment16.collections;

import com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces.CollectionP2P;
import com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces.QueueP2P;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Represents data structure as a queue. This collection
 * based on an array.
 */
public class ArrayQueueP2P<E> extends ArrayListP2P<E> implements QueueP2P<E> {

    /** Pointer that point on head of queue */
    private int headPointer;

    public ArrayQueueP2P(Class<E> elementType) {
        this(elementType, ARRAY_CAPACITY);
    }

    public ArrayQueueP2P(Class<E> elementType, int capacity) {
        super(elementType, capacity);
    }

    /**
     * Adds a particular element in the end of the queue.
     *
     * @param e particular element which added to queue.
     */
    @Override
    public void offer(E e) {
        modCount++;
        if (checkCapacityAndIncreaseAsNeeded()) {
            headPointer = 0;
        }
        elements[(headPointer + size++) % elements.length] = e;
    }

    /**
     * Adds a particular element in the current stack.
     *
     * @param e particular element which added to stack.
     * @return true, if element was added, otherwise - false.
     */
    @Override
    public boolean add(E e) {
        offer(e);
        return true;
    }

    /**
     * Returns the element which stored on the head of the queue
     * without removing it from queue.
     *
     * @return the element which stored on the head of the queue.
     */
    @Override
    public E peek() {
        return elements[headPointer];
    }

    /**
     * Returns the element which stored under a certain index
     * without removing it from queue.
     *
     * @param index the index indicating the item to be returned.
     * @return the element which stored under a certain index.
     */
    @Override
    public E get(int index) {
        checkIndexForCorrectness(index);
        return elements[getRelativeIndex(index)];
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
        modCount++;
        E topElement = peek();
        elements[headPointer] = null;
        headPointer = ++headPointer % elements.length;
        size--;

        return topElement;
    }

    /**
     * Adds to current collection elements of other collection.
     *
     * @param collectionP2P collection of added elements.
     */
    @Override
    public void addAll(CollectionP2P<? extends E> collectionP2P) {
        trimToSize();
        int sizeAddedCollection = collectionP2P.size();
        increaseCapacity(size + sizeAddedCollection);

        System.arraycopy(collectionP2P.toArray(), 0, elements, size, sizeAddedCollection);
        size += sizeAddedCollection;

    }

    /**
     * Insets the element in particular index-position. All elements, including
     * element which stored under particular index will be moved one position to the right.
     *
     * @param index position on which element will be added.
     * @param e added element.
     */
    @Override
    public void insert(int index, E e) {
        checkIndexForCorrectness(getRelativeIndex(index));
        trimToSize();
        checkCapacityAndIncreaseAsNeeded();
        System.arraycopy(elements, index, elements, index + 1, size++ - index);
        elements[index] = e;
    }

    /**
     * Removes the element which stored under a certain index. The items
     * stored to the right of the deleted are moved one position to the left.
     *
     * @param index the index of the element to be deleted.
     * @return removed element.
     */
    @Override
    public E remove(int index) {
        checkIndexForCorrectness(getRelativeIndex(index));
        trimToSize();
        E removedElement = elements[index];
        System.arraycopy(elements, index + 1, elements, index, --size - index);
        elements[size] = null;

        return removedElement;
    }

    /**
     * Replaces the element which stored under a certain index.
     *
     * @param index the index of the replaced element.
     * @param e the substitute element.
     */
    @Override
    public void replace(int index, E e) {
        modCount++;
        int relativeIndex = getRelativeIndex(index);
        checkIndexForCorrectness(relativeIndex);
        elements[relativeIndex] = e;
    }

    /** Trims the capacity of current collection to it size. */
    @Override
    public void trimToSize() {
        modCount++;
        // Eliminating the loopback of an queue in array
        elements = toArray();
        headPointer = 0;
    }

    /* Returns relative index position of element for current queue. */
    private int getRelativeIndex(int index) {
        return (headPointer + index) % elements.length;
    }

    /**
     * Returns array of all elements which stored in current queue.
     *
     * @return array of all elements which stored in current queue.
     */
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        E[] result = (E[]) new Object[size];

        int queuePointer = headPointer;
        for (int i = 0; i < result.length; i++) {
            result[i] = elements[queuePointer];
            queuePointer = ++queuePointer % elements.length;
        }

        return result;
    }

    /**
     * Iterator that allow iterate through queue.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int amountModifications = modCount;
            private int pointer = 0;

            @Override
            public boolean hasNext() {
                return pointer < size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public E next() {
                if (amountModifications != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (pointer >= size) {
                    throw new IndexOutOfBoundsException("No more items");
                }
                return elements[(headPointer + pointer++) % elements.length];
            }
        };
    }

    /**
     * Returns iterator that allows iterate through queue in reverse order.
     *
     * @return the iterator that allows iterate through queue in revers order.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {

            private int amountModifications = modCount;
            private int pointer = size - 1;
            private E[] elementsForIterating = toArray();

            @Override
            public boolean hasNext() {
                return pointer >= 0;
            }

            @SuppressWarnings("unchecked")
            @Override
            public E next() {
                if (amountModifications != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (pointer < 0) {
                    throw new IndexOutOfBoundsException("No more items");
                }
                return elementsForIterating[(headPointer + pointer--) % elements.length];
            }
        };
    }

}
