package com.shpp.p2p.cs.sushakov.assignment17.collections;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.CollectionP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.ListP2P;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * The collection which stored element as list
 * which is implemented on the basis of an array.
 */
public class ArrayListP2P<E> implements ListP2P<E> {

    /** Initial capacity of array. */
    protected static final int ARRAY_CAPACITY = 10;
    /** Growth factor of array capacity */
    protected static final double GROWTH_FACTOR = 1.5;

    /** Array of elements of current list */
    protected E[] elements;
    /** Amount elements in current list */
    protected int size = 0;
    /** Counter of modifications for current collection */
    protected int modCount = 0;
    /** Object representing the component type of the new array */
    private Class<E> elementType;

    public ArrayListP2P(Class<E> elementType) {
        this(elementType, ARRAY_CAPACITY);
    }

    public ArrayListP2P(Class<E> elementType, int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Illegal capacity " + capacity);
        }

        this.elementType = elementType;
        createNewArray(capacity);
    }

    /**
     * Adds a particular element in the end of current list.
     *
     * @param e particular element which added to list.
     * @return true, if element was added, otherwise - false.
     */
    @Override
    public boolean add(E e) {
        modCount++;

        checkCapacityAndIncreaseAsNeeded();
        elements[size++] = e;

        return true;
    }

    /**
     * Adds to current collection elements of other collection.
     *
     * @param collectionP2P collection of added elements.
     */
    @Override
    public void addAll(CollectionP2P<? extends E> collectionP2P) {
        modCount++;
        int sizeAddedCollection = collectionP2P.size();

        if (size + sizeAddedCollection > elements.length) {
            increaseCapacity(size + collectionP2P.size());
        }

        System.arraycopy(collectionP2P.toArray(), 0, elements, size, sizeAddedCollection);
        size += sizeAddedCollection;
    }

    /**
     * Returns the element which stored under a certain index.
     *
     * @param index the index indicating the item to be returned.
     * @return the element which stored under a certain index.
     */
    @Override
    public E get(int index) {
        checkIndexForCorrectness(index);
        return elements[index];
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
        modCount++;
        checkIndexForCorrectness(index);
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
        checkIndexForCorrectness(index);
        elements[index] = e;
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
        modCount++;
        checkIndexForCorrectness(index);
        checkCapacityAndIncreaseAsNeeded();
        System.arraycopy(elements, index, elements, index + 1, size++ - index);
        elements[index] = e;
    }

    /**
     * Returns array of all elements which stored in current list.
     *
     * @return array of all elements which stored in current list.
     */
    @Override
    public E[] toArray() {
        return Arrays.copyOfRange(elements, 0, size);
    }

    /** Trims the capacity of current collection to it size. */
    public void trimToSize() {
        modCount++;
        if (size < elements.length) {
            if (size == 0) {
                createNewArray(ARRAY_CAPACITY);
            } else {
                elements = Arrays.copyOf(elements, size);
            }
        }
    }

    /**
     * Returns amount element which stored in the list.
     *
     * @return amount elements stored in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns information about whether there are elements on the current list.
     *
     * @return false, if current list contains some elements, otherwise - true (when list is empty).
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns iterator that allow iterate through list.
     *
     * @return iterator for current list.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorP2P();
    }

    /**
     * Iterator that allow iterate through list.
     */
    private class IteratorP2P implements Iterator<E> {

        /** Current iteration element. */
        private int currentIndex = 0;
        /** The number of modifications to the collection at the time the iterator was created */
        private int amountModifications = modCount;

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

            return elements[currentIndex++];
        }
    }

    /* Checks index for correctness */
    protected void checkIndexForCorrectness(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    /**
     * Check the capacity of current list and increase it,
     * if it array is full.
     *
     * @return if capacity was changed - return true, otherwise - false.
     */
    protected boolean checkCapacityAndIncreaseAsNeeded() {
        if (size == elements.length) {
            increaseCapacity((int) (size * GROWTH_FACTOR + 1));
            return true;
        }
        return false;
    }

    /**
     * Increases the array's capacity of current list.
     */
    protected void increaseCapacity(int capacity) {
        E[] tempArray = toArray();
        createNewArray(capacity);
        System.arraycopy(tempArray, 0, elements, 0, tempArray.length);
    }

    /**
     * Initializes array of elements with specified array size.
     *
     * @param capacity the specified array size.
     */
    @SuppressWarnings("unchecked")
    protected void createNewArray(int capacity) {
        elements = (E[]) Array.newInstance(elementType, capacity);
    }

}
