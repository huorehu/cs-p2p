package com.shpp.p2p.cs.sushakov.assignment16.collections;

import com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces.CollectionP2P;
import com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces.ListP2P;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * The collection which stored element as list
 * which is implemented on the basis of a linked list.
 */
public class LinkedListP2P<E> implements ListP2P<E> {

    /** First node stored link to first element of list */
    private Node<E> firstNode;
    /** Last node stored link to last element of list */
    private Node<E> lastNode;
    /** Amount elements in current list. */
    private int size = 0;
    /** Counter of modifications for current collection */
    private int modCount = 0;

    public LinkedListP2P() {
        this.firstNode = new Node<>(null, null, null);
        this.lastNode = new Node<>(null, null, firstNode);
        this.firstNode.nextNode = lastNode;
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
        lastNode.value = e;
        lastNode.nextNode = new Node<>(null, null, lastNode);
        lastNode = lastNode.nextNode;
        size++;

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
        /* Create a copy of the collection as an array to avoid
         * ConcurrentModificationException when passing to the method itself */
        E[] arrayElements = collectionP2P.toArray();

        for (E e : arrayElements) {
            add(e);
        }
    }

    /**
     * Returns the element which stored under a certain index.
     *
     * @param index the index indicating the item to be returned.
     * @return the element which stored under a certain index.
     */
    @Override
    public E get(int index) {
        return getNodeByIndex(index).value;
    }

    /**
     * Removes the element which stored under a certain index.
     *
     * @param index the index of the element to be deleted.
     * @return removed element.
     */
    @Override
    public E remove(int index) {
        modCount++;
        Node<E> removedNode = getNodeByIndex(index);
        E removedElement = removedNode.value;

        removedNode.previousNode.nextNode = removedNode.nextNode;
        removedNode.nextNode.previousNode = removedNode.previousNode;
        size--;

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
        getNodeByIndex(index).value = e;
    }

    /**
     * Insets the element in particular index-position.
     *
     * @param index position on which element will be added.
     * @param e added element.
     */
    @Override
    public void insert(int index, E e) {
        modCount++;
        Node<E> nodeStoredUnderIndex = getNodeByIndex(index);

        nodeStoredUnderIndex.previousNode.nextNode = new Node<>(e, nodeStoredUnderIndex, nodeStoredUnderIndex.previousNode);
        nodeStoredUnderIndex.previousNode = nodeStoredUnderIndex.previousNode.nextNode;
        size++;
    }

    /**
     * Returns array of all elements which stored in current list.
     *
     * @return array of all elements which stored in current list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        E[] array = (E[]) new Object[size];
        Node<E> currentNode = firstNode.nextNode;

        for (int i = 0; i < size; i++) {
            array[i] = currentNode.value;
            currentNode = currentNode.nextNode;
        }

        return array;
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

        /** Element on which iteration passes */
        private Node<E> currentNode = firstNode.nextNode;
        /** The number of modifications to the collection at the time the iterator was created */
        private int amountModifications = modCount;

        @Override
        public boolean hasNext() {
            return currentNode.nextNode != null;
        }

        @Override
        public E next() {
            if (amountModifications != modCount) {
                throw new ConcurrentModificationException();
            }
            if (currentNode.nextNode == null) {
                throw new IndexOutOfBoundsException("No more items");
            }
            E e = currentNode.value;
            currentNode = currentNode.nextNode;

            return e;
        }
    }

    /**
     * Returns iterator that allows iterate through list in reverse order.
     *
     * @return the iterator that allows iterate through list in revers order.
     */
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {

            /** Element on which iteration passes */
            private Node<E> currentNode = lastNode.previousNode;
            /** The number of modifications to the collection at the time the iterator was created */
            private int amountModifications = modCount;

            @Override
            public boolean hasNext() {
                return currentNode.previousNode != null;
            }

            @Override
            public E next() {
                if (amountModifications != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (currentNode.previousNode == null) {
                    throw new IndexOutOfBoundsException("No more items");
                }
                E e = currentNode.value;
                currentNode = currentNode.previousNode;

                return e;
            }
        };
    }

    /* The node which represents particular element and stored
     * link to the next and previous elements respectively */
    private class Node<T> {

        private T value;
        private Node<T> nextNode;
        private Node<T> previousNode;

        private Node(T value, Node<T> nextNode, Node<T> previousNode) {
            this.value = value;
            this.nextNode = nextNode;
            this.previousNode = previousNode;
        }
    }

    /* Checks index on correctness */
    private void checkIndexOnCorrectness(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    /* Returns particular Node which stored under certain index. */
    private Node<E> getNodeByIndex(int index) {
        checkIndexOnCorrectness(index);

        Node<E> currentNode = firstNode.nextNode;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.nextNode;
        }

        return currentNode;
    }

}
