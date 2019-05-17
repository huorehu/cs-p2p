package com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool;

import java.io.FileInputStream;
import java.io.IOException;

import static com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool.BitsBufferedWriter.BITS_IN_BYTE;

public class BitsBufferedReader {

    /** Amount bytes in int */
    private static final int INT_SIZE = 4;
    /** Amount bytes in long */
    private static final int LONG_SIZE = 8;
    /** Stream for reader data from particular file */
    private FileInputStream fileInputStream;
    /** Current byte in buffer */
    private int currentByte;
    /** Amount offsets for reading next bit */
    private int bitPosition;

    public BitsBufferedReader(String fileIn) throws IOException {
        this.fileInputStream = new FileInputStream(fileIn);
        initByte();
    }

    /** Reads next bit from file
     *  @return read bit from file in integer representation */
    public int nextBit() throws IOException {
        int bit = currentByte >> bitPosition & 1;
        bitPosition--;

        if (bitPosition < 0) {
            initByte();
        }
        return bit;
    }

    /**
     * Reads a certain amount of bits from a file.
     *
     * @param amount the amount of bits that will be read.
     * @return the string representation of bits-sequence.
     * @throws IOException error reading from file.
     */
    public String nextBitsStr(int amount) throws IOException {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            result.append(nextBit());
        }
        return result.toString();
    }

    /** Reads next byte from file. */
    public int nextByte() throws IOException {
        int result = 0;

        for (int i = 0; i < BITS_IN_BYTE; i++) {
            result = result << 1 | nextBit();
        }
        return result;
    }

    /** Reads next int from file */
    public int nextInt() throws IOException {
        return Integer.parseInt(nextBitsStr(INT_SIZE * BITS_IN_BYTE), 2); // 2 is a radix
    }

    /** Reads next long from file */
    public long nextLong() throws IOException {
        return Long.parseLong(nextBitsStr(LONG_SIZE * BITS_IN_BYTE), 2); // 2 is a radix
    }

    /** Reads next byte from file */
    private void initByte() throws IOException {
        currentByte = (byte) fileInputStream.read();
        bitPosition = BITS_IN_BYTE - 1;
    }

}
