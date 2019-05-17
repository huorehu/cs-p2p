package com.shpp.p2p.cs.sushakov.assignment16.tests;

/**
 * Contains tests name for collection.
 */
public enum TestCases {

    /** Test name for testing adding elements to collection */
    ADD,

    /** Test name for testing getting elements from collection */
    GET,

    /** Test name for testing removing elements from collection */
    REMOVE,

    /** Test name for testing replacing elements in collection */
    REPLACE,

    /** Test name for testing inserting elements to collection */
    INSERT,

    /** Test name for testing size-control in collection */
    SIZE,

    /** Test name for testing throwing exceptions, if index of requested element is out of bounds of collection */
    OUT_OF_BOUNDS,

    /** Test name for testing initializing size of collection capacity */
    CAPACITY,

    /** Test name for testing collection's iterator */
    ITERATOR,

    /** Test name for testing collection's descending iterator */
    DESCENDING_ITERATOR,

    /** Test name for testing empty collection */
    IS_EMPTY,

    /** Test name for testing method toArray */
    TO_ARRAY,

    /** Test name for testing method push */
    PUSH,

    /** Test name for testing method pop */
    POP,

    /** Test name for testing method peek */
    PEEK,

    /** Test name for testing method offer */
    OFFER,

    /** Test name for testing method poll */
    POLL,

    /** Test name for method addAll */
    ADD_ALL,

    /** Test name for testing concurrentModificationException */
    CONCURRENT_MODIFICATION

}
