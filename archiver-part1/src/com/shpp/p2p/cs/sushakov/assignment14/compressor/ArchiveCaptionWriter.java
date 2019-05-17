package com.shpp.p2p.cs.sushakov.assignment14.compressor;

import java.io.IOException;
import java.util.Map;

import static com.shpp.p2p.cs.sushakov.assignment14.compressor.BitsBufferedWriter.BITS_IN_BYTE;

/** Writes archive caption to file. */
public class ArchiveCaptionWriter {

    /** Number of bytes of stored table size */
    private static final int TABLE_SIZE_BYTES = 4;
    /** Number of bytes of stored data size */
    private static final int DATA_SIZE_BYTES = 8;
    /** Table size represent in byte-array */
    private byte[] tableSize;
    /** Data size represent in byte-array */
    private byte[] dataSize;
    /** Writer data to file */
    private BitsBufferedWriter writer;

    public ArchiveCaptionWriter(BitsBufferedWriter writer, Coder coder) {
        tableSize = initTableSize(coder);
        dataSize = initDataSize(coder);
        initCaption(writer, coder);
        this.writer = writer;
    }

    /** Writes caption stored in buffer to file. */
    public void writeToFile() {
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates for some number it representation in byte-array.
     *
     * @param data the some number.
     * @param bytesAmount the amount of bytes in the form of which the number will be represent.
     * @return byte-array representation of number.
     */
    public byte[] toByteArray(long data, int bytesAmount) {
        byte[] result = new byte[bytesAmount];
        byte dataPart;

        for (int i = 0; i < bytesAmount; i++) {
            // Bit-shift, which depends on the number of bytes on which the number is divided into an array. It is always a multiple of 8
            dataPart = (byte) (data >> BitsBufferedWriter.BITS_IN_BYTE * (bytesAmount - 1) - i * BitsBufferedWriter.BITS_IN_BYTE);
            result[i] = dataPart;
        }
        return result;
    }

    /** Initialize size of the table represent in byte-array */
    private byte[] initTableSize(Coder coder) {
        /* Consists of one byte representing the original alphabet and one byte containing
           the number of following bits containing the code for the previous byte. */
        int amountCoding = 2;
        // Amount bits for all codes.
        int codesLength = coder.getAllCodesLength();
        // Minimum necessary bytes for represent all bits of codes.
        int bytesForCodes = codesLength / BITS_IN_BYTE + (1 - (BITS_IN_BYTE - codesLength % BITS_IN_BYTE) / BITS_IN_BYTE);
        int tableSizeInt = amountCoding * coder.getPowerAlphabet() + bytesForCodes;

        return toByteArray(tableSizeInt, TABLE_SIZE_BYTES);
    }

    /** Initialize size of the data represent in byte-array */
    private byte[] initDataSize(Coder coder) {
        return toByteArray(coder.getSrcDataSize(), DATA_SIZE_BYTES);
    }

    /** Writes data of caption to buffer */
    private void initCaption(BitsBufferedWriter writer, Coder coder) {
        try {
            writer.addCode(tableSize);
            writer.addCode(dataSize);

            // Writes a byte-bit sequence association table.
            for (Map.Entry<Byte, String> entry : coder.getCodesAlphabet().entrySet()) {
                writer.addCode(normalizeByte(entry.getKey()));
                writer.addCode(normalizeByte(entry.getValue().length()));
                writer.addCode(entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Leads the resulting number to 8 bits in the line view, regardless of its contents.
     *
     * @param value the number for normalizing.
     * @return normalized number in string representation.
     */
    public static String normalizeByte(int value) {
        StringBuilder byteValue = new StringBuilder(Integer.toBinaryString(value));

        while (byteValue.length() < BITS_IN_BYTE) {
            byteValue.insert(0, "0");
        }

        return byteValue.substring(byteValue.length() - BITS_IN_BYTE, byteValue.length());
    }

}
