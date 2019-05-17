package com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/** Contains codes of alphabet for compressing data. */
public abstract class AbstractCoder {

    /** Number of byte options */
    protected static final int BYTE_VARIATIONS = 256;

    /** Byte-bit sequence association table */
    protected MapP2P<Byte, String> codesAlphabet;
    /** Sum of bits of all alphabet codes */
    protected int allCodesLength;
    /** Size of non-compressed data in bytes */
    protected long srcDataSize;

    public AbstractCoder(String filePath) throws IOException {
        codesAlphabet = getCodes(filePath);
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
    public MapP2P<Byte, String> getCodesAlphabet() {
        return codesAlphabet;
    }

    /**
     * Creates coding alphabet.
     *
     * @param filePath the path to file based on the contents of which an alphabet will be built
     *                to encode bytes based on the frequency of occurrences of each of the bytes.
     * @return the codes table.
     */
    protected abstract MapP2P<Byte, String> getCodes(String filePath) throws IOException;

    /**
     * Defines occurrence frequency of byte.
     *
     * @param filePath the filename.
     * @return occurrence frequency of byte.
     */
    protected long[] getByteFrequency(String filePath) throws IOException {
        long[] bytesFrequency = new long[BYTE_VARIATIONS];
        srcDataSize = new File(filePath).length();

        try (FileInputStream fin = new FileInputStream(filePath)) {
            while (fin.available() > 0) {
                bytesFrequency[fin.read()]++;
            }
        }

        return bytesFrequency;
    }

    /**
     * Calculates sum of bits of all alphabet codes.
     *
     * @param encodingTable the alphabet for which calculating sum of bits.
     * @return sum of bits.
     */
    private int calculatesBitsInAlphabet(MapP2P<Byte, String> encodingTable) {
        int result = 0;
        for (MapP2P.Node<Byte, String> entry : encodingTable.entrySet()) {
            result += entry.getValue().length();
        }
        return result;
    }

}
