package com.shpp.p2p.cs.sushakov.assignment17.tests;

import com.shpp.p2p.cs.sushakov.assignment17.collections.HashMapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.PriorityQueueP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.QueueP2P;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static com.shpp.p2p.cs.sushakov.assignment17.tests.TestCases.*;

/** This class performs the testing of collections */
public class CollectionTester {

    /* String representation of test's results */
    private static final MapP2P<Boolean, String> strResultForPrint = new HashMapP2P<>();

    static {
        strResultForPrint.put(true, "OK");
        strResultForPrint.put(false, "FAILED");
    }

    private static final String TEST_STR = "test_";
    private static final int AMOUNT_ADDED_ELEMENTS = 10;

    public static void main(String[] args) {
        CollectionTester tester = new CollectionTester();

        QueueP2P<String> queueP2P = new PriorityQueueP2P<>(String.class);
        MapP2P<String, String> mapP2P = new HashMapP2P<>();

        tester.printResults(queueP2P.getClass().getSimpleName(), tester.testQueue(queueP2P));
        tester.printResults(mapP2P.getClass().getSimpleName(), tester.testMap(mapP2P));
    }

    /** Tests class that implements interface {@link QueueP2P} */
    private MapP2P<TestCases, Boolean> testQueue(QueueP2P<String> queueP2P) {
        MapP2P<TestCases, Boolean> results = new HashMapP2P<>();

        results.put(TO_ARRAY, true);
        results.put(SIZE, true);
        results.put(IS_EMPTY, true);
        results.put(OFFER, true);
        results.put(PEEK, true);
        results.put(POLL, true);
        results.put(ITERATOR, true);
        results.put(DESCENDING_ITERATOR, true);
        results.put(ADD_ALL, true);
        results.put(CONCURRENT_MODIFICATION, true);
        results.put(PRIORITY_SORT, true);

        // Test is empty
        if (!queueP2P.isEmpty()) {
            results.put(IS_EMPTY, false);
        }

        // Adds elements to queue
        for (int i = AMOUNT_ADDED_ELEMENTS - 1; i >= 0; i--) {
            queueP2P.offer(TEST_STR + i);
        }

        // Test offer and size
        if (queueP2P.size() != AMOUNT_ADDED_ELEMENTS) {
            results.put(OFFER, false);
            results.put(SIZE, false);
        }

        // Test peek and sorts elements depending on the priority
        if (!queueP2P.peek().equals(TEST_STR + (0))) {
            results.put(PEEK, false);
            results.put(PRIORITY_SORT, false);
        }

        // Test toArray and iterator/descending iterator
        Object[] array = queueP2P.toArray();
        Iterator iterator = queueP2P.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            if (!iterator.next().equals(array[i])) {
                results.put(TO_ARRAY, false);
                results.put(ITERATOR, false);
                results.put(DESCENDING_ITERATOR, true);
                break;
            }
        }

        // Test poll
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            if (!(TEST_STR + i).equals(queueP2P.poll())) {
                results.put(POLL, false);
            }
        }

        // Test addAll
        int collectionDoubler = 2;
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS / collectionDoubler; i++) {
            queueP2P.offer(TEST_STR + i);
        }

        queueP2P.addAll(queueP2P);

        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS / collectionDoubler; i++) {
            for (int j = 0; j < collectionDoubler; j++) {
                if (!queueP2P.poll().equals(TEST_STR + i)) {
                    results.put(ADD_ALL, false);
                }
            }
        }

        // Test concurrentModificationException
        queueP2P.offer(TEST_STR);
        try {

            for (String str : queueP2P) {
                queueP2P.offer(str);
            }
            results.put(CONCURRENT_MODIFICATION, false);

        } catch (ConcurrentModificationException e) {
            results.put(CONCURRENT_MODIFICATION, true);
        }

        return results;
    }

    /** Tests class that implements interface {@link MapP2P} */
    private MapP2P<TestCases, Boolean> testMap(MapP2P<String, String> mapP2P) {
        MapP2P<TestCases, Boolean> results = new HashMapP2P<>();

        results.put(PUT, true);
        results.put(GET, true);
        results.put(IS_EMPTY, true);
        results.put(SIZE, true);
        results.put(REMOVE, true);
        results.put(CLEAR, true);
        results.put(ADD_ALL, true);
        results.put(CONTAINS_KEY, true);
        results.put(CONTAINS_VALUE, true);
        results.put(ENTRY_SET, true);

        // Test is empty
        if (!mapP2P.isEmpty()) {
            results.put(IS_EMPTY, false);
        }

        // Adds elements to map
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            mapP2P.put(TEST_STR + i, TEST_STR + i);
        }

        // Test put and size
        if (mapP2P.size() != AMOUNT_ADDED_ELEMENTS) {
            results.put(OFFER, false);
            results.put(SIZE, false);
        }

        // Test get and entrySet
        for (MapP2P.Node<String, String> node : mapP2P.entrySet()) {
            if (!node.getKey().equals(node.getValue())) {
                results.put(GET, false);
                results.put(ENTRY_SET, false);
            }
        }

        String testKeyStr = TEST_STR + 0;
        String testStrWhichNotContains = "Not_contains";

        // Test contains 'key/value'
        if (!mapP2P.containsKey(testKeyStr) || mapP2P.containsKey(testStrWhichNotContains) ||
                !mapP2P.containsValue(testKeyStr) || mapP2P.containsValue(testStrWhichNotContains)) {
            results.put(CONTAINS_KEY, false);
            results.put(CONTAINS_VALUE, false);
        }

        // Test remove
        if (!mapP2P.remove(testKeyStr).equals(testKeyStr) || mapP2P.get(testKeyStr) != null) {
            results.put(REMOVE, false);
        }

        // Test clear
        mapP2P.clear();
        if (!mapP2P.isEmpty()) {
            results.put(CLEAR, false);
        }

        // Test addAll
        MapP2P<String, String> addedMap = new HashMapP2P<>();
        addedMap.put("0", "0");
        addedMap.put("1", "1");
        addedMap.put("2", "2");
        mapP2P.addAll(addedMap);

        int amountAddedElements = 3;
        for (int i = 0; i < amountAddedElements; i++) {
            if (mapP2P.get(i + "") == null) {
                results.put(ADD_ALL, false);
            }
        }

        return results;
    }

    /* Prints results of testing collection to console */
    private void printResults(String collectionType, MapP2P<TestCases, Boolean> results) {
        System.out.println("Test results for collection <" + collectionType + ">");
        System.out.println("---------------------------------------------");

        for (MapP2P.Node<TestCases, Boolean> entry : results.entrySet()) {
            System.out.println("[ " + strResultForPrint.get(entry.getValue()) + " ] " + "method: " + entry.getKey().toString().toLowerCase());
        }
        System.out.println('\n');
    }

}
