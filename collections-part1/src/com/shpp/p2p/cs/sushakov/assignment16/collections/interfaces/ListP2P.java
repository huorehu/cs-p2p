package com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces;

/**
 * This interface contains methods that allow to perform basic operations
 * on the elements of the list that implements this interface.
 *
 * @param <E> the type of elements which stored in list.
 */
public interface ListP2P<E> extends CollectionP2P<E> {

    /**
     * Adds a particular element in the end of current list.
     *
     * @param e particular element which added to list.
     * @return true, if element was added, otherwise - false.
     */
    boolean add(E e);

    /**
     * Returns the element which stored under a certain index.
     *
     * @param index the index indicating the item to be returned.
     * @return the element which stored under a certain index.
     */
    E get(int index);

    /**
     * Removes the element which stored under a certain index. The items
     * stored to the right of the deleted are moved one position to the left.
     *
     * @param index the index of the element to be deleted.
     * @return removed element.
     */
    E remove(int index);

    /**
     * Replaces the element which stored under a certain index.
     *
     * @param index the index of the replaced element.
     * @param e the substitute element.
     */
    void replace(int index, E e);

    /**
     * Insets the element in particular index-position. All elements, including
     * element which stored under particular index will be moved one position to the right.
     *
     * @param index position on which element will be added.
     * @param e added element.
     */
    void insert(int index, E e);

}
