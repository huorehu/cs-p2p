package com.shpp.p2p.cs.sushakov.assignment14.compressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/** Contains codes of alphabet for compressing data. */
public class Coder {

    /** Number of byte options */
    private static final int BYTE_VARIATIONS = 256;
    /** Minimum elements in list for divide on left and right branch */
    private static final int MIN_ELEMENTS_DIVIDE = 2;

    /** Byte-bit sequence association table */
    private Map<Byte, String> codesAlphabet;
    /** Sum of bits of all alphabet codes */
    private int allCodesLength;
    /** Size of non-compressed data in bytes */
    private long srcDataSize;

    public Coder(String filePath) throws IOException {
        createCodesAlphabet(filePath);
        allCodesLength = calculatesBitsInAlphabet(codesAlphabet);
    }

    /**
     * Returns code for received byte.
     *
     * @param particularByte the particular byte.
     * @return the code for particular byte in string representation.
     */
    public String getCode(byte particularByte) {
        return codesAlphabet.get(particularByte);
    }

    /**
     * Returns size of data.
     *
     * @return the size of non-compressed data.
     */
    public long getSrcDataSize() {
        return srcDataSize;
    }

    /**
     * Sum of bits for particular alphabet.
     *
     * @return sum of bits.
     */
    public int getAllCodesLength() {
        return allCodesLength;
    }

    /** @return the power of current alphabet. */
    public int getPowerAlphabet() {
        return codesAlphabet.size();
    }

    /** @return current codes alphabet. */
    public Map<Byte, String> getCodesAlphabet() {
        return codesAlphabet;
    }

    /**
     * Creates coding alphabet.
     *
     * @param filePath the path to file based on the contents of which an alphabet will be built
     *                to encode bytes based on the frequency of occurrences of each of the bytes.
     */
    private void createCodesAlphabet(String filePath) throws IOException {
        long[] bytes = new long[BYTE_VARIATIONS];
        codesAlphabet = new HashMap<>();
        srcDataSize = new File(filePath).length();

        try (FileInputStream fin = new FileInputStream(filePath)) {
            while (fin.available() > 0) {
                bytes[fin.read()]++;
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + filePath + " not found");
        } catch (IOException e) {
            throw new IOException("1");
        }

        Map<Byte, Long> byteOccurrences = new HashMap<>();

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] > 0) {
                byteOccurrences.put((byte) i, bytes[i]);
            }
        }

        createsCodes(sortsCodesAlphabet(byteOccurrences));
    }

    /**
     * Sorts alphabet codes in non-ascending order
     *
     * @param byteOccurrences an array contains the number of occurrences of each byte
     *                       mapped to the numbers of the array cells.
     * @return sorted codes alphabet in non-ascending order.
     */
    private Map<Byte, Long> sortsCodesAlphabet(Map<Byte, Long> byteOccurrences) {
        Map<Byte, Long> sortedAlphabet = new LinkedHashMap<>();

        byteOccurrences.entrySet().stream().
                sorted(Map.Entry.<Byte, Long>comparingByValue().reversed()).
                forEach(entry -> sortedAlphabet.put(entry.getKey(), entry.getValue()));

        return sortedAlphabet;
    }

    /**
     * Creates disjoint bit sequences for a finite set of non-repeating bytes.
     *
     * @param sortedAlphabet the finite set of non-repeating bytes.
     */
    private void createsCodes(Map<Byte, Long> sortedAlphabet) {
        if (sortedAlphabet.size() < MIN_ELEMENTS_DIVIDE) {
            return;
        }

        if (sortedAlphabet.size() == MIN_ELEMENTS_DIVIDE) {
            int i = 0;
            for (Map.Entry<Byte, Long> entry : sortedAlphabet.entrySet()) {
                codesAlphabet.put(entry.getKey(), codesAlphabet.get(entry.getKey()) + i);
                i++;
            }
            return;
        }

        Map<Byte, Long> zeroPart = new LinkedHashMap<>();
        Map<Byte, Long> onePart = new LinkedHashMap<>();

        long averageBytesWeight = getAverageWeight(sortedAlphabet);
        long currentBytesWeight = 0;

        for (Map.Entry<Byte, Long> entry : sortedAlphabet.entrySet()) {
            currentBytesWeight += entry.getValue();

            if (currentBytesWeight <= averageBytesWeight || entry.getValue() > averageBytesWeight) {
                zeroPart.put(entry.getKey(), entry.getValue());
                addBitToCodeTable(entry.getKey(), "0");
            } else {
                onePart.put(entry.getKey(), entry.getValue());
                addBitToCodeTable(entry.getKey(), "1");
            }
        }
        createsCodes(zeroPart);
        createsCodes(onePart);
    }

    /**
     * Adds particular bit (0 or 1) to bit-sequence.
     *
     * @param particularByte the byte to the bit-sequence of which a bit adds.
     * @param bit particular bit (0 or 1).
     */
    private void addBitToCodeTable(byte particularByte, String bit) {
        if (codesAlphabet.containsKey(particularByte)) {
            codesAlphabet.put(particularByte, codesAlphabet.get(particularByte) + bit);
        } else {
            codesAlphabet.put(particularByte, bit);
        }
    }

    /**
     * Calculates the average frequency of occurrence of a finite set of bytes.
     *
     * @param sortedAlphabet the finite set of bytes.
     * @return the average frequency of occurrence of a finite set of bytes.
     */
    private long getAverageWeight(Map<Byte, Long> sortedAlphabet) {
        long averageBytesWeight = 0;

        for (Map.Entry<Byte, Long> entry : sortedAlphabet.entrySet()) {
            averageBytesWeight += entry.getValue();

            if (!codesAlphabet.containsKey(entry.getKey())) {
                codesAlphabet.put(entry.getKey(), "");
            }
        }
        return averageBytesWeight / 2;
    }

    /**
     * Calculates sum of bits of all alphabet codes.
     *
     * @param encodingTable the alphabet for which calculating sum of bits.
     * @return sum of bits.
     */
    private int calculatesBitsInAlphabet(Map<Byte, String> encodingTable) {
        int result = 0;
        for (Map.Entry<Byte, String> entry : encodingTable.entrySet()) {
            result += entry.getValue().length();
        }
        return result;
    }

}
