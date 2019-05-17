package com.shpp.p2p.cs.sushakov.assignment17.collections;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.CollectionP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.QueueP2P;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Implementation of abstract data type as priority queue. This collection based
 * on binary heap.
 *
 * @param <E> the type of items (any data type) a collection can accept.
 */
public class PriorityQueueP2P<E> implements QueueP2P<E> {

    /** Comparator for defining element position when it added in queue */
    private Comparator<? super E> comparator;
    /** Initial capacity of queue */
    private static final int QUEUE_CAPACITY = 10;
    /** Growth factor of queue capacity */
    private static final double GROWTH_FACTOR = 1.5;
    /** Array of elements of current queue */
    private E[] queueElements;
    /** Amount elements in current queue */
    private int size;
    /** Object representing the component type of the new array */
    private Class<E> elementType;
    /** Counter of modifications for current collection */
    protected int modCount = 0;

    public PriorityQueueP2P(Class<E> elementType) {
        this(elementType, null, QUEUE_CAPACITY);
    }

    public PriorityQueueP2P(Class<E> elementType, int capacity) {
        this(elementType, null, capacity);
    }

    public PriorityQueueP2P(Class<E> elementType, Comparator<? super E> comparator) {
        this(elementType, comparator, QUEUE_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public PriorityQueueP2P(Class<E> elementType, Comparator<? super E> comparator, int capacity) {
        this.elementType = elementType;
        this.comparator = comparator;
        createNewArray(capacity);
    }

    /**
     * Adds an element to the queue at a position that depends
     * on the priority of the element being added
     *
     * @param e particular element which added to queue.
     */
    @Override
    public void offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        modCount++;

        int insertionPointer = size;
        if (insertionPointer == 0) {
            queueElements[0] = e;
            size++;
            return;
        }
        checkCapacityAndIncreaseAsNeeded();
        addElement(insertionPointer, e);
        size++;
    }

    /* Adds element in queue at position that depends on the priority of element */
    @SuppressWarnings("unchecked")
    private void addElement(int insertionPointer, E e) {
        Comparable<? super E> addingElement = null;
        if (comparator == null) {
            addingElement = (Comparable<? super E>) e;
        }

        int parentIndex;
        E parentElement;

        while (insertionPointer > 0) {
            // Calculating the parent node index. The index is calculated as (childNodeIndex - 1) / 2
            parentIndex = (insertionPointer - 1) / 2;
            parentElement = queueElements[parentIndex];

            if (comparator != null && (comparator.compare(e, parentElement) >= 0) ||
                    (addingElement != null && addingElement.compareTo(parentElement) >= 0)) {
                break;
            }
            queueElements[insertionPointer] = parentElement;
            insertionPointer = parentIndex;
        }

        queueElements[insertionPointer] = e;
    }

    /**
     * Returns the element which stored on the head of the queue
     * without removing it from queue.
     *
     * @return the element which stored on the head of the queue.
     */
    @Override
    public E peek() {
        return queueElements[0];
    }

    /**
     * Returns the element which stored on the head of the queue
     * with removing it from queue.
     * After the removal of the element, the queue is rebuilt in such a way
     * that on its head is again the element with the highest priority.
     *
     * @return the element which stored on the head of the queue.
     */
    @Override
    public E poll() {
        modCount++;
        if (size == 0) {
            return null;
        }

        E result = queueElements[0];
        E lastElement = queueElements[--size];
        queueElements[size] = null;

        if (size != 0) {
            queueElements[0] = lastElement;
            sortBinaryTree();
        }

        return result;
    }

    /* Sort the queue in such a way that at its head is the element
     with the highest priority */
    @SuppressWarnings("unchecked")
    private void sortBinaryTree() {
        Comparable<? super E> bubbleDownElement = null;
        if (comparator == null) {
            bubbleDownElement = (Comparable<? super E>) queueElements[0];
        }
        E bubbleDownElementNotCasted = queueElements[0];
        int elementPointer = 0;

        int childIndex;
        int rightChildIndex;
        E childElement;

        while (elementPointer < size / 2) {
            // left child index
            childIndex = elementPointer * 2 + 1;
            childElement = queueElements[childIndex];
            rightChildIndex = childIndex + 1;

            if (rightChildIndex < size && (((Comparable<? super E>) childElement).compareTo(queueElements[rightChildIndex]) > 0 ||
                    comparator != null && comparator.compare(childElement, queueElements[rightChildIndex]) > 0)) {
                // right element is less
                childElement = queueElements[childIndex = rightChildIndex];
            }
            if (comparator != null && comparator.compare(bubbleDownElementNotCasted, childElement) <= 0 ||
                    bubbleDownElement != null && bubbleDownElement.compareTo(childElement) <= 0) {
                break;
            }
            queueElements[elementPointer] = childElement;
            elementPointer = childIndex;
        }
        queueElements[elementPointer] = (E) bubbleDownElement;
    }

    /**
     * Adds to current collection elements of other collection.
     *
     * @param collectionP2P collection of added elements.
     */
    @Override
    public void addAll(CollectionP2P<? extends E> collectionP2P) {
        E[] collectionCopyToArray = collectionP2P.toArray();

        for (E e : collectionCopyToArray) {
            offer(e);
        }
    }

    /** Trims the capacity of current collection to it size. */
    public void trimToSize() {
        modCount++;
        if (size < queueElements.length) {
            if (size == 0) {
                createNewArray(QUEUE_CAPACITY);
            } else {
                queueElements = Arrays.copyOf(queueElements, size);
            }
        }
    }

    /**
     * Returns iterator that allow iterate through queue.
     * The order of enumeration of the elements of the queue is not guaranteed.
     *
     * @return iterator for current queue.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return iterator();
    }

    /**
     * Returns information about whether there are elements on the queue.
     *
     * @return false, if queue contains some elements, otherwise - true (when current queue is empty).
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns amount element which stored in the current queue.
     *
     * @return amount elements stored in the current queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns iterator that allow iterate through queue.
     * The order of enumeration of the elements of the queue is not guaranteed.
     *
     * @return iterator for current queue.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            /** The number of modifications to the collection at the time the iterator was created */
            private int amountModifications = modCount;
            /** Current iteration element. */
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (amountModifications != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (currentIndex >= size) {
                    throw new IndexOutOfBoundsException("No more items");
                }

                return queueElements[currentIndex++];
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        E[] result = (E[]) new Object[size];
        System.arraycopy(queueElements, 0, result, 0, result.length);

        return result;
    }

    /**
     * Check the capacity of current queue and increase it,
     * if it array is full.
     */
    private void checkCapacityAndIncreaseAsNeeded() {
        if (size == queueElements.length) {
            increaseCapacity();
        }
    }

    /**
     * Increases the array's capacity of current queue.
     */
    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        E[] tempArray = toArray();

        createNewArray((int) (queueElements.length * GROWTH_FACTOR) + 1);
        System.arraycopy(tempArray, 0, queueElements, 0, tempArray.length);
    }

    /**
     * Initializes array of elements with specified array size.
     *
     * @param capacity the specified array size.
     */
    @SuppressWarnings("unchecked")
    private void createNewArray(int capacity) {
        queueElements = (E[]) Array.newInstance(elementType, capacity);
    }

}
