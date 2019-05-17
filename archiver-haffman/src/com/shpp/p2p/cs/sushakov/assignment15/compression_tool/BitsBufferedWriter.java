package com.shpp.p2p.cs.sushakov.assignment15.compression_tool;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/** Class is intended for bitwise writing of data to a file */
public class BitsBufferedWriter implements AutoCloseable {

    /** Buffer capacity */
    private static final int BUFFER_SIZE = 1024;
    /** Amount bits in one byte */
    public static final int BITS_IN_BYTE = 8;
    /** Character to integer coefficient */
    private static final int CHAR_TO_INT = 48;
    /** Stream for writer data for particular file */
    private final FileOutputStream fileOutputStream;
    /** Buffer for data */
    private byte[] buffer;
    /** Data in byte */
    private byte currentByte;
    /** The pointer to the position to which the next byte will be written */
    private int bufferPosition;
    /** Amount of data contains in byte (amount of bits) */
    private int amountBits;

    public BitsBufferedWriter(String fileOut) throws FileNotFoundException {
        this.fileOutputStream = new FileOutputStream(fileOut);
        initBuffer();
        initByte();
    }

    /**
     * Write to file particular bits-sequence.
     * Data will not be written to the file until the byte buffer is full.
     * To immediately write data to a file, is necessary call the flush method.
     *
     * @param code the string representation of bits-sequence.
     * @throws IOException if problems with writing to a file.
     */
    public void addCode(String code) throws IOException {
        for (int i = 0; i < code.length(); i++) {
            currentByte = (byte) ((currentByte << 1) | (code.charAt(i) - CHAR_TO_INT));
            amountBits++;

            if (amountBits > BITS_IN_BYTE - 1) {
                addByteToBuffer();
                initByte();
            }
        }
    }

    /**
     * Write to file particular data.
     * Data will not be written to the file until the byte buffer is full.
     * To immediately write data to a file, is necessary call the flush method.
     *
     * @param data the byte-array representation of data.
     * @throws IOException if problems with writing to a file.
     */
    public void addCode(byte[] data) throws IOException {
        if (amountBits > 0) {
            completeByte();
            addByteToBuffer();
        }

        for (byte byteData : data) {
            currentByte = byteData;
            addByteToBuffer();
        }
    }

    /** Writes all current data in buffer */
    public void flush() throws IOException {
        if (amountBits > 0) {
            completeByte();
            addByteToBuffer();
        }

        if (bufferPosition != 0) {
            writeBytesToFile();
        }

        fileOutputStream.flush();
        initBuffer();
    }

    /** Adds to byte-buffer completed byte */
    private void addByteToBuffer() throws IOException {
        buffer[bufferPosition] = currentByte;
        bufferPosition++;
        initByte();

        if (bufferPosition > BUFFER_SIZE - 1) {
            writeBytesToFile();
            initBuffer();
        }
    }

    /** Writes current data from buffer */
    private void writeBytesToFile() throws IOException {
        fileOutputStream.write(Arrays.copyOfRange(buffer, 0, bufferPosition));
    }

    /** Shifts significant byte content to the left */
    private void completeByte() {
        while (amountBits < BITS_IN_BYTE) {
            currentByte <<= 1;
            amountBits++;
        }
    }

    /** Initialize clear buffer */
    private void initBuffer() {
        buffer = new byte[BUFFER_SIZE];
        bufferPosition = 0;
    }

    /** Initialize clear byte */
    public void initByte() {
        currentByte = 0;
        amountBits = 0;
    }

    @Override
    public void close() throws Exception {
        fileOutputStream.close();
    }

}
