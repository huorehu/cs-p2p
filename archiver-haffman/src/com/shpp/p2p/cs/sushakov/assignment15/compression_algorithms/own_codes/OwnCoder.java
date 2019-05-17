package com.shpp.p2p.cs.sushakov.assignment15.compression_algorithms.own_codes;

import com.shpp.p2p.cs.sushakov.assignment15.compression_tool.AbstractCoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/** Contains codes of alphabet for compressing data. */
public class OwnCoder extends AbstractCoder {


    public OwnCoder(String filePath) throws IOException {
        super(filePath);
    }

    /**
     * Creates coding alphabet.
     *
     * @param filePath the path to file based on the contents of which an alphabet will be built
     *                to encode bytes based on the frequency of occurrences of each of the bytes.
     */
    @Override
    protected Map<Byte, String> getCodes(String filePath) throws IOException {
        long[] bytes = getByteFrequency(filePath);

        Map<Byte, Long> byteOccurrences = new HashMap<>();

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] > 0) {
                byteOccurrences.put((byte) i, bytes[i]);
            }
        }

        return createsCodes(sortsCodesAlphabet(byteOccurrences));
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
    private Map<Byte, String> createsCodes(Map<Byte, Long> sortedAlphabet) {
        CodeGenerator generator = new CodeGenerator();
        Map<Byte, String> codesAlphabet = new HashMap<>();

        for (Map.Entry<Byte, Long> entry : sortedAlphabet.entrySet()) {
            codesAlphabet.put(entry.getKey(), generator.getNextCode());
        }

        return codesAlphabet;
    }

}
