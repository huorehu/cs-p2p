package com.shpp.p2p.cs.sushakov.assignment16.tests;

import com.shpp.p2p.cs.sushakov.assignment16.collections.*;
import com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces.CollectionP2P;
import com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces.ListP2P;
import com.shpp.p2p.cs.sushakov.assignment16.collections.interfaces.QueueP2P;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.shpp.p2p.cs.sushakov.assignment16.tests.TestCases.*;

/** This class performs the testing of collections */
public class CollectionTester {

    /* String representation of test's results */
    private static final Map<Boolean, String> strResultForPrint = new HashMap<>();

    static {
        strResultForPrint.put(true, "OK");
        strResultForPrint.put(false, "FAILED");
    }

    private static final String TEST_STR = "test_";
    private static final String REPLACE_STR = "test_replace_";
    private static final String INSERT_STR = "test_insert_";
    private static final int AMOUNT_ADDED_ELEMENTS = 15;

    public static void main(String[] args) {
        CollectionTester tester = new CollectionTester();

        CollectionP2P<String> arrayListP2P = new ArrayListP2P<>(String.class);
        CollectionP2P<String> linkedListP2P = new LinkedListP2P<>();
        CollectionP2P<String> arrayQueueP2P = new ArrayQueueP2P<>(String.class);
        CollectionP2P<String> linkedQueueP2P = new LinkedQueueP2P<>();
        CollectionP2P<String> stackP2P = new StackP2P<>(String.class);


        tester.printResults(arrayListP2P.getClass().getSimpleName(), tester.testCollection(arrayListP2P));
        tester.printResults(linkedListP2P.getClass().getSimpleName(), tester.testCollection(linkedListP2P));
        tester.printResults(arrayQueueP2P.getClass().getSimpleName(), tester.testCollection(arrayQueueP2P));
        tester.printResults(linkedQueueP2P.getClass().getSimpleName(), tester.testCollection(linkedQueueP2P));
        tester.printResults(stackP2P.getClass().getSimpleName(), tester.testCollection(stackP2P));
    }

    @SuppressWarnings("unchecked")
    private Map<TestCases, Boolean> testCollection(CollectionP2P collectionP2P) {
        Map<TestCases, Boolean> results;

        if (collectionP2P instanceof StackP2P) {
            results = testStack((StackP2P) collectionP2P);
        } else if (collectionP2P instanceof QueueP2P) {
            results = testQueue((QueueP2P) collectionP2P);
        } else if (collectionP2P instanceof ListP2P) {
            results = testList((ListP2P<String>) collectionP2P);
        } else {
            throw new IllegalArgumentException("Tests for collection " + collectionP2P + " does not found");
        }
        return results;
    }

    /* Prints results of testing collection to console */
    private void printResults(String collectionType, Map<TestCases, Boolean> results) {
        System.out.println("Test results for collection <" + collectionType + ">");
        System.out.println("---------------------------------------------");

        for (Map.Entry<TestCases, Boolean> entry : results.entrySet()) {
            System.out.println("[ " + strResultForPrint.get(entry.getValue()) + " ] " + "method: " + entry.getKey().toString().toLowerCase());
        }
        System.out.println('\n');
    }

    /**
     * Tests collections which implements interface {@link ListP2P}.
     *
     * @param listP2P collection which implements interface {@link ListP2P}.
     * @return test results.
     */
    private Map<TestCases, Boolean> testList(ListP2P<String> listP2P) {

        boolean isArrayList = listP2P instanceof ArrayListP2P;

        Map<TestCases, Boolean> resultTests = new HashMap<>();
        // cases for testing
        resultTests.put(IS_EMPTY, true);
        resultTests.put(ADD, true);
        resultTests.put(GET, true);
        resultTests.put(REMOVE, true);
        resultTests.put(REPLACE, true);
        resultTests.put(INSERT, true);
        resultTests.put(SIZE, true);
        resultTests.put(OUT_OF_BOUNDS, true);
        resultTests.put(ITERATOR, true);
        resultTests.put(TO_ARRAY, true);
        resultTests.put(ADD_ALL, true);
        resultTests.put(CONCURRENT_MODIFICATION, true);
        if (isArrayList) {
            resultTests.put(CAPACITY, true);
        }

        // Test is empty
        if (!listP2P.isEmpty()) {
            resultTests.put(IS_EMPTY, false);
        }

        // Adds elements to list
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            listP2P.add(TEST_STR + i);
        }

        // Test add and size
        if (listP2P.size() != AMOUNT_ADDED_ELEMENTS) {
            resultTests.put(ADD, false);
            resultTests.put(SIZE, false);
        }

        // Test get
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            if (!(TEST_STR + i).equals(listP2P.get(i))) {
                resultTests.put(GET, false);
            }
        }

        // Random position for insertion
        int insertionRandomPosition = 5;
        // Test insert
        listP2P.insert(insertionRandomPosition, INSERT_STR + insertionRandomPosition);
        if (listP2P.size() != AMOUNT_ADDED_ELEMENTS + 1 || !listP2P.get(insertionRandomPosition).equals(INSERT_STR + insertionRandomPosition)) {
            resultTests.put(INSERT, false);
        }

        // Test remove
        listP2P.remove(insertionRandomPosition);
        if (listP2P.size() != AMOUNT_ADDED_ELEMENTS || !listP2P.get(5).equals(TEST_STR + insertionRandomPosition)) {
            resultTests.put(REMOVE, false);
        }

        // Test replace
        listP2P.replace(insertionRandomPosition, REPLACE_STR + insertionRandomPosition);
        if (listP2P.size() != AMOUNT_ADDED_ELEMENTS || !listP2P.get(insertionRandomPosition).equals(REPLACE_STR + insertionRandomPosition)) {
            resultTests.put(REPLACE, false);
        }
        listP2P.replace(insertionRandomPosition, TEST_STR + insertionRandomPosition);

        // Test out of bounds exception
        try {
            listP2P.get(listP2P.size());
            resultTests.put(OUT_OF_BOUNDS, false);
        } catch (IndexOutOfBoundsException e) {
            resultTests.put(OUT_OF_BOUNDS, true);
        }

        // Test negative capacity
        if (isArrayList) {
            try {
                new ArrayListP2P<>(Class.class,-1);
                resultTests.put(CAPACITY, false);
            } catch (IllegalArgumentException e) {
                resultTests.put(CAPACITY, true);
            }
        }

        // Test iterator
        Iterator<String> iterator = listP2P.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            if (!iterator.next().equals(TEST_STR + i)) {
                resultTests.put(ITERATOR, false);
                break;
            }
        }

        // Test toArray
        Object[] array = listP2P.toArray();
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals((TEST_STR + i))) {
                resultTests.put(TO_ARRAY, false);
                break;
            }
        }


        // Test addAll
        testAddAll(listP2P, resultTests);

        // Test concurrentModificationException
        try {

            for (String str : listP2P) {
                listP2P.add(str);
            }
            resultTests.put(CONCURRENT_MODIFICATION, false);

        } catch (ConcurrentModificationException e) {
            resultTests.put(CONCURRENT_MODIFICATION, true);
        }

        return resultTests;
    }

    /**
     * Tests the stack collection.
     *
     * @param stackP2P the stack which is tested.
     * @return test results.
     */
    private Map<TestCases, Boolean> testStack(StackP2P<String> stackP2P) {
        Map<TestCases, Boolean> resultTests = new HashMap<>();

        resultTests.put(CAPACITY, true);
        resultTests.put(TO_ARRAY, true);
        resultTests.put(SIZE, true);
        resultTests.put(IS_EMPTY, true);
        resultTests.put(PUSH, true);
        resultTests.put(POP, true);
        resultTests.put(PEEK, true);
        resultTests.put(ITERATOR, true);
        resultTests.put(ADD_ALL, true);
        resultTests.put(CONCURRENT_MODIFICATION, true);

        // Test is empty
        if (!stackP2P.isEmpty()) {
            resultTests.put(IS_EMPTY, false);
        }

        // Test negative capacity
        try {
            new StackP2P<>(String.class, -1);
            resultTests.put(CAPACITY, false);
        } catch (IllegalArgumentException e) {
            resultTests.put(CAPACITY, true);
        }

        // Adds elements to stack
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            stackP2P.push(TEST_STR + i);
        }

        // Test push and size
        if (stackP2P.size() != AMOUNT_ADDED_ELEMENTS) {
            resultTests.put(PUSH, false);
            resultTests.put(SIZE, false);
        }

        // Test peek
        if (!stackP2P.peek().equals(TEST_STR + (stackP2P.size() - 1))) {
            resultTests.put(PEEK, false);
        }

        // Test toArray
        Object[] array = stackP2P.toArray();
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals((TEST_STR + i))) {
                resultTests.put(TO_ARRAY, false);
                break;
            }
        }

        // Test iterator
        Iterator iterator = stackP2P.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            if (!iterator.next().equals(TEST_STR + i)) {
                resultTests.put(ITERATOR, false);
                break;
            }
        }

        // Test pop
        for (int i = AMOUNT_ADDED_ELEMENTS - 1; i >= 0; i--) {
            if (!(TEST_STR + i).equals(stackP2P.pop())) {
                resultTests.put(POP, false);
            }
        }

        // Test addAll
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            stackP2P.push(TEST_STR + i);
        }
        testAddAll(stackP2P, resultTests);

        // Test concurrentModificationException
        try {

            for (String str : stackP2P) {
                stackP2P.push(str);
            }
            resultTests.put(CONCURRENT_MODIFICATION, false);

        } catch (ConcurrentModificationException e) {
            resultTests.put(CONCURRENT_MODIFICATION, true);
        }

        return resultTests;
    }

    /* Tests method addAll */
    @SuppressWarnings("unchecked")
    private void testAddAll(ListP2P collection, Map<TestCases, Boolean> resultTests) {
        collection.addAll(collection);
        int collectionDoubler = 2;

        for (int i = 0; i < collectionDoubler; i++) {

            int offset = i * collection.size() / collectionDoubler;
            for (int j = offset; j < collection.size() / collectionDoubler; j++) {
                if (!collection.get(j).equals(TEST_STR + j)) {
                    resultTests.put(ADD_ALL, false);
                }
            }
        }
    }

    /**
     * Tests the collection which implements interface {@link QueueP2P}.
     *
     * @param queueP2P the collection which implements interface {@link QueueP2P}.
     * @return test results.
     */
    private Map<TestCases, Boolean> testQueue(QueueP2P<String> queueP2P) {
        Map<TestCases, Boolean> resultTests = new HashMap<>();

        resultTests.put(TO_ARRAY, true);
        resultTests.put(SIZE, true);
        resultTests.put(IS_EMPTY, true);
        resultTests.put(OFFER, true);
        resultTests.put(PEEK, true);
        resultTests.put(POLL, true);
        resultTests.put(ITERATOR, true);
        resultTests.put(DESCENDING_ITERATOR, true);
        resultTests.put(ADD_ALL, true);
        resultTests.put(CONCURRENT_MODIFICATION, true);

        // Test is empty
        if (!queueP2P.isEmpty()) {
            resultTests.put(IS_EMPTY, false);
        }

        // Adds elements to queue
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            queueP2P.offer(TEST_STR + i);
        }

        // Test offer and size
        if (queueP2P.size() != AMOUNT_ADDED_ELEMENTS) {
            resultTests.put(OFFER, false);
            resultTests.put(SIZE, false);
        }

        // Test peek
        if (!queueP2P.peek().equals(TEST_STR + (0))) {
            resultTests.put(PEEK, false);
        }

        // Test toArray
        Object[] array = queueP2P.toArray();
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals((TEST_STR + i))) {
                resultTests.put(TO_ARRAY, false);
                break;
            }
        }

        // Test iterator
        Iterator iterator = queueP2P.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            if (!iterator.next().equals(TEST_STR + i)) {
                resultTests.put(ITERATOR, false);
                break;
            }
        }

        // Test descending iterator
        Iterator descendingIterator = queueP2P.descendingIterator();
        for (int i = AMOUNT_ADDED_ELEMENTS - 1; descendingIterator.hasNext(); i--) {
            if (!descendingIterator.next().equals(TEST_STR + i)) {
                resultTests.put(DESCENDING_ITERATOR, false);
                break;
            }
        }

        // Test poll
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            if (!(TEST_STR + i).equals(queueP2P.poll())) {
                resultTests.put(POLL, false);
            }
        }

        // Test addAll
        for (int i = 0; i < AMOUNT_ADDED_ELEMENTS; i++) {
            queueP2P.offer(TEST_STR + i);
        }

        queueP2P.addAll(queueP2P);

        int collectionDoubler = 2;
        for (int i = 0; i < collectionDoubler; i++) {

            int offset = i * queueP2P.size() / collectionDoubler;
            for (int j = offset; j < queueP2P.size() / collectionDoubler; j++) {
                if (!queueP2P.poll().equals(TEST_STR + j)) {
                    resultTests.put(ADD_ALL, false);
                }
            }
        }

        // Test concurrentModificationException
        queueP2P.offer(TEST_STR);
        try {

            for (String str : queueP2P) {
                queueP2P.offer(str);
            }
            resultTests.put(CONCURRENT_MODIFICATION, false);

        } catch (ConcurrentModificationException e) {
            resultTests.put(CONCURRENT_MODIFICATION, true);
        }

        return resultTests;
    }

}
