package com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Describes the methods that the collection should contains.
 *
 * @param <E> any specific type of elements.
 */
public interface CollectionP2P<E> extends Iterable<E> {

    /**
     * Returns information about whether there are elements on the collection.
     *
     * @return false, if collection contains some elements, otherwise - true (when collection is empty).
     */
    boolean isEmpty();

    /**
     * Returns amount element which stored in the collection.
     *
     * @return amount elements stored in the collection.
     */
    int size();

    /**
     * Returns iterator that allow iterate through collection.
     *
     * @return iterator for current collection.
     */
    Iterator<E> iterator();

    /**
     * Returns array of all elements which stored in collection.
     *
     * @return array of all elements which stored in collection.
     */
    E[] toArray();

    /**
     * Adds to current collection elements of other collection.
     *
     * @param collectionP2P collection of added elements.
     */
    void addAll(CollectionP2P<? extends E> collectionP2P);

    /** Creates a spliterator over the elements in this collection */
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(iterator(), size(), 0);
    }

    /** Creates sequential from collection's spliterator */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
